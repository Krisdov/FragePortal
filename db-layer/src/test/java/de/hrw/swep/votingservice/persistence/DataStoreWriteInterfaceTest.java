package de.hrw.swep.votingservice.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TODO A4c): Identifizieren Sie die dafür passende Methode in der Klasse
 * DataStoreWriteInterfaceTest und initialisieren Sie dort DBUnit so, dass vor der Erzeugung des
 * Datenbankobjekts und der Ausführung jedes Tests die Datenbank mit dem Inhalt von full.xml
 * überschrieben wird. Verwenden Sie für die Initialisierung die bereits vorbereiteten Attribute
 * CONNECTION_STRING, DB_USER und DB_PASSWORD.
 * 
 * @author Andriessens
 *
 */
public class DataStoreWriteInterfaceTest {
    private static final String STR_NEUE_FRAGE = "Neue Frage";
    private static final String FILE_NAME_FULL_XML = "full.xml";
    private static final String CONNECTION_STRING = "jdbc:hsqldb:file:../db-layer/database/votesdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private RealDatabase db;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        db = new RealDatabase();
    }

    @Test
    public void testAddVote() throws SQLException, Exception {
        assertEquals(3, db.getVotesForQuestion(2).size());
        db.addVote(2, 1);
        assertEquals(4, db.getVotesForQuestion(2).size());
    }

    /**
     * TODO A4d): In der Klasse DataStoreWriteInterfaceTest wird im Test testAddVoteWrong()versucht,
     * fälschlich eine Antwort von 7 (bei eigentlich erlaubten Werten von 0 bis 5) abzuspeichern.
     * Die Datenbankschicht soll dies durch eine Exception vom Typ PersistenceException abwehren.
     * Der Test ist noch nicht ganz vollständig: Vervollständigen Sie ihn so, dass er durchläuft,
     * wenn die Datenbank wie vorgesehen den richtigen Exception-Typ tatsächlich wirft und ansonsten
     * fehlschlägt. Erweitern Sie ihn anschließend so, dass Sie mit DBUnit überprüfen, ob trotz
     * ausgelöster Exception bei diesem Zugriff die Datenbank tatsächlich nicht verändert wurde.
     * 
     * 
     * @throws SQLException
     * @throws Exception
     */
    @Test
    public void testAddVoteWrong() throws SQLException, Exception {
        try {
            db.addVote(1, 7);
        } catch (PersistenceException e) {
        }
    }

    @Test
    public void testUpsertQuestion() {
        int id = db.getAllQuestions().iterator().next();
        boolean status = db.getStatusOfQuestion(id);
        List<Integer> bewertungen = db.getVotesForQuestion(id);
        db.upsertQuestion(id, STR_NEUE_FRAGE, status, bewertungen);

        // Update prüfen
        assertEquals(STR_NEUE_FRAGE, db.getTextOfQuestion(id));
        assertEquals(status, db.getStatusOfQuestion(id));

        // Insert prüfen
        bewertungen = new ArrayList<Integer>();
        bewertungen.add(5);
        bewertungen.add(3);
        db.upsertQuestion(-1, "Sollen die Briten die EU verlassen?", false, bewertungen);
    }

    @Test
    public void testDeleteVotes() {
        List<Integer> votes = db.getVotesForQuestion(2);
        assertEquals(3, votes.size());
        db.deleteVotes(2);
        votes = db.getVotesForQuestion(2);
        assertEquals(0, votes.size());
    }

    @Test
    public void testDeleteQuestion() {
        assertEquals("Mögen Sie Wassersport?", db.getTextOfQuestion(5));
        db.deleteQuestion(5);
        assertNull(db.getTextOfQuestion(5));
    }

}
