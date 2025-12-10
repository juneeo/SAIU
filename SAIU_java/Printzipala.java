package SAIU_java;

import javax.swing.*;
import java.awt.*;

public class Printzipala {

    public Printzipala() {
        JFrame leihoa = new JFrame("SAIU");
        leihoa.setLocation(500, 200);
        leihoa.setSize(400, 600);

        leihoa.setContentPane(panelaSortu());
        leihoa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        leihoa.setVisible(true);
    }

    private Container panelaSortu() {
        JPanel panela = new JPanel(new BorderLayout());
        panela.setBackground(Color.BLUE);   // fondo azul
        return panela;
    }

    public static void main(String[] args) {
        new Printzipala();
    }
}
