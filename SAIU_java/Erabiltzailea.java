package SAIU_java;

public class Erabiltzailea {
    private String izena;
    private String pasahitza;
    private String pantailaMota; // "admin", "udala", "pantaila"

    public Erabiltzailea(String izena, String pasahitza) {
        this.izena = izena;
        this.pasahitza = pasahitza;
        this.pantailaMota = izena;
    }

    public String getIzena() {
        return izena;
    }

    public String getPasahitza() {
        return pasahitza;
    }

    public String getPantailaMota() {
        return pantailaMota;
    }
}