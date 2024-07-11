package de.hrw.swep.votingservice.persistence.dbunit;

/**
 * 
 * @author andriesc
 *
 */
public final class Exporter {

    private Exporter() {
        // vermeiden, dass der Konstruktor dieser Hilfsklasse aufgerufen wird:
        // Deshalb Sichtbarkeit private
        // In diesem Konstruktor gibt es nichts zu tun
    }

    private static final String CONNECTION_STRING = "jdbc:hsqldb:file:../db-layer/database/votesdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    /**
     * Exports current database into XML file for DBUnit.
     * 
     * @param args
     *            none expected
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO A4b): Verwenden Sie die Klasse Exporter im Package
        // de.hrw.swep.votingservice.persistence.dbunit, um einen XML-Schnappschuss der
        // initialisierten Datenbank unter dem Namen full.xml zu erzeugen. Dazu müssen
        // Sie die Implementierung der Klasse fertigstellen. Verwenden Sie für die Initialisierung
        // die bereits vorbereiteten Attribute CONNECTION_STRING, DB_USER und DB_PASSWORD.

    }
}
