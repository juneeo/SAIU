package SAIU_java;

import java.io.*;
import java.util.*;
import java.beans.*;

public class Modeloa {
    private Map<String, Erabiltzailea> erabiltzaileak;
    private PropertyChangeSupport konektorea;
    
    public static final String LOGIN_EGOKIA = "loginEgokia";
    public static final String LOGIN_OKERRA = "loginOkerra";

    public Modeloa() {
        erabiltzaileak = new HashMap<>();
        konektorea = new PropertyChangeSupport(this);
        erabiltzaileakIrakurri();
    }

    private void erabiltzaileakIrakurri() {
        try (BufferedReader br = new BufferedReader(new FileReader("erabiltzaileak.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length >= 2) {
                    String izena = partes[0].trim();
                    String pasahitza = partes[1].trim();
                    Erabiltzailea erabiltzailea = new Erabiltzailea(izena, pasahitza);
                    erabiltzaileak.put(izena, erabiltzailea);
                }
            }
            System.out.println("Erabiltzaileak kargatuta: " + erabiltzaileak.keySet());
        } catch (IOException e) {
            System.err.println("Errorea fitxategia irakurtzean: " + e.getMessage());
        }
    }

    public void autentifikatu(String izena, String pasahitza) {
        if (izena == null || izena.isEmpty() || pasahitza == null || pasahitza.isEmpty()) {
            konektorea.firePropertyChange(LOGIN_OKERRA, null, "Izena eta pasahitza bete behar dira");
            return;
        }

        Erabiltzailea erabiltzailea = erabiltzaileak.get(izena);
        if (erabiltzailea != null && erabiltzailea.getPasahitza().equals(pasahitza)) {
            konektorea.firePropertyChange(LOGIN_EGOKIA, null, erabiltzailea);
        } else {
            konektorea.firePropertyChange(LOGIN_OKERRA, null, "Erabiltzaile edo pasahitz okerra");
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        konektorea.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        konektorea.removePropertyChangeListener(listener);
    }
}