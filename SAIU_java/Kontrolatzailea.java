package SAIU_java;

import java.awt.event.*;

public class Kontrolatzailea implements ActionListener, KeyListener {
    private Modeloa modeloa;
    private Printzipala vista;

    public Kontrolatzailea(Modeloa modeloa) {
        this.modeloa = modeloa;
    }

    public void setBista(Printzipala vista) {
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("LOGIN".equals(e.getActionCommand())) {
            String izena = vista.getIzena();
            String pasahitza = vista.getPasahitza();
            modeloa.autentifikatu(izena, pasahitza);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            vista.loginBotoia.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}