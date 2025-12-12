package SAIU_java;

import javax.swing.*;
import java.awt.*;
import java.beans.*;

public class Printzipala implements PropertyChangeListener {
    private Modeloa modeloa; // datuena
    private Kontrolatzailea kontrolatzailea; // logika

    // Pantailako konfigurazioa
    private int zabaleraPantalla = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private int altueraPantalla = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private int kokapenXPantaila = 0;
    private int kokapenYPantaila = 0;

    // Lehioak eta konponenteak:
    private JFrame loginFrame; // logineko lehioa
    private JFrame mainFrame; // lehio printzipala admin/udala/pantaila

    private JTextField izenaField; // erabiltzaile textua
    private JPasswordField pasahitzaField; // pasahitza textua
    public JButton loginBotoia; // logineko botoia

    public Printzipala(Modeloa modeloa, Kontrolatzailea kontrolatzailea) {
        this.modeloa = modeloa;
        this.kontrolatzailea = kontrolatzailea;
        this.modeloa.addPropertyChangeListener(this); // obserbadore bezala jarri (notifikazioak jasotzeko)

        loginPantailaErakutsi(); // login pantaila erakutsi
    }

    private void loginPantailaErakutsi() {
        loginFrame = new JFrame("SAIU - Sistema Alerta Intregala Uholdetarako"); // sortu lehio bat tituluarekin
        loginFrame.setLocation(kokapenXPantaila, kokapenYPantaila); // pantailako kokapena
        loginFrame.setSize(zabaleraPantalla, altueraPantalla); // pantailara ajustatu
        loginFrame.setIconImage(new ImageIcon("argazkiak/logoa/SAIULogoa.png").getImage()); // lehiko ikonoa jarri
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // itxi lehioa amaitutakoan programa

        JPanel panela = new JPanel(new GridBagLayout());
        panela.setBackground(Koloreak.URDINA);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // espazioak
        gbc.fill = GridBagConstraints.HORIZONTAL; // horizontalki bete

        // Logo
        ImageIcon logoIcon = new ImageIcon("argazkiak/logoa/SAIULogoIzenarekin.png");
        JLabel logoLabel = new JLabel(logoIcon);
        gbc.gridx = 0; // Kolumna 0
        gbc.gridy = 0; // Fila 0
        gbc.gridwidth = 2; // 2 kolumna okupatu
        panela.add(logoLabel, gbc);

        // Izena
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel izenaLabel = new JLabel("Erabiltzailea:");
        izenaLabel.setForeground(Color.WHITE);
        panela.add(izenaLabel, gbc);

        gbc.gridx = 1;
        izenaField = new JTextField(15); // 15 kolumna zabaletara
        izenaField.addKeyListener(kontrolatzailea); // entzuten ditu sakatutako teklak
        panela.add(izenaField, gbc);

        // Pasahitza
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel pasahitzaLabel = new JLabel("Pasahitza:");
        pasahitzaLabel.setForeground(Color.WHITE);
        panela.add(pasahitzaLabel, gbc);

        gbc.gridx = 1;
        pasahitzaField = new JPasswordField(15); // testua ezkutatu
        pasahitzaField.addKeyListener(kontrolatzailea);
        panela.add(pasahitzaField, gbc);

        // Botoia
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginBotoia = new JButton("Hasi Saioa");
        loginBotoia.setActionCommand("LOGIN"); // Komandoa akzioa identifikatzeko
        loginBotoia.addActionListener(kontrolatzailea); // Kontrolatzaileak manejatuko du klik-a
        loginBotoia.setBackground(Color.WHITE);
        loginBotoia.setForeground(Color.BLACK);
        panela.add(loginBotoia, gbc);

        // Lehioa erakutsi
        loginFrame.setContentPane(panela);
        loginFrame.setVisible(true);
    }

    // Metodo generikoa pantailak sortzeko
    private JPanel pantailaSortu(String tituloa) {
        mainFrame = new JFrame("SAIU - " + tituloa);
        mainFrame.setLocation(kokapenXPantaila, kokapenYPantaila);
        mainFrame.setSize(zabaleraPantalla, altueraPantalla);
        mainFrame.setIconImage(new ImageIcon("argazkiak/logoa/SAIULogoa.png").getImage()); // lehiko ikonoa jarri
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelNagusia = new JPanel(new BorderLayout());
        panelNagusia.setBackground(Koloreak.URDINA);

        mainFrame.setContentPane(panelNagusia);
        mainFrame.setVisible(true);

        return panelNagusia;
    }

    private void adminPantaila() {
        JPanel panelNagusia = pantailaSortu("Admin Pantaila");

        // Goiko panela sortu
        panelNagusia.add(goikoPanelaSortu(), BorderLayout.NORTH);

        // Gipuzkoako mapa erdian jarri
        ImageIcon mapaIcon = new ImageIcon("argazkiak/gipuzkoakoMapa.png");
        JLabel mapaLabel = new JLabel(mapaIcon, SwingConstants.CENTER);
        panelNagusia.add(mapaLabel, BorderLayout.CENTER);
    }

    private void udalaPantaila() {
        JPanel panelNagusia = pantailaSortu("Udala Pantaila");
        panelNagusia.add(goikoPanelaSortu(), BorderLayout.NORTH);
    }

    private void pantailaPantaila() {
        JPanel panelNagusia = pantailaSortu("Pantaila");
        panelNagusia.add(goikoPanelaSortu(), BorderLayout.NORTH);

        // Panela ordua zentratzeko
        JPanel orduPanela = new JPanel(new GridBagLayout()); // Elementuak zentratzeko errazagoa
        orduPanela.setBackground(Koloreak.URDINA);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Kolumna 0
        gbc.gridy = 0; // Fila 0
        gbc.anchor = GridBagConstraints.CENTER; // Zentratua

        // Orduarentzeko Labela
        JLabel orduLabel = new JLabel("--:--");
        orduLabel.setFont(new Font("Arial", Font.BOLD, 150));
        orduLabel.setForeground(Color.WHITE);
        orduPanela.add(orduLabel, gbc);

        // Datarentzako Labela
        gbc.gridy = 1; // hurrengoko fila
        gbc.insets = new Insets(20, 0, 0, 0); // margena
        JLabel dataLabel = new JLabel("----------");
        dataLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        dataLabel.setForeground(Color.WHITE);
        orduPanela.add(dataLabel, gbc);

        panelNagusia.add(orduPanela, BorderLayout.CENTER);

        // Timer-a eguneratzeko ordua segunduro
        Timer timer = new Timer(1000, e -> {
            java.time.LocalDateTime orain = java.time.LocalDateTime.now(); // Oraingo ordua hartzen du
            // Formatoa aldatu
            java.time.format.DateTimeFormatter orduFormatua = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
            java.time.format.DateTimeFormatter dataFormatua = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Testua eguneratu
            orduLabel.setText(orain.format(orduFormatua));
            dataLabel.setText(orain.format(dataFormatua));
        });
        timer.start(); // Timer-a iniziatu

        // Eguneratu bereahala
        timer.getActionListeners()[0].actionPerformed(null);
    }

    // goiko panela (MOMENTUZ BAKARRIK ATZERAKO BOTOIA DAGO)
    private Container goikoPanelaSortu() {
        JPanel goikoPanela = new JPanel(new FlowLayout(FlowLayout.LEFT)); // ezkerrean jarri konponenteak
        goikoPanela.setBackground(Color.BLACK);
        JButton atzeraBotoia = atzeraBotoiaSortu(); // atzerako botoia sortu
        goikoPanela.add(atzeraBotoia);
        return goikoPanela;
    }

    private JButton atzeraBotoiaSortu() {
        JButton botoia = new JButton("← Atzera");
        botoia.setFont(new Font("Arial", Font.BOLD, 16));
        botoia.setFocusPainted(false);
        botoia.setBackground(Color.WHITE);
        botoia.setForeground(Color.BLACK);
        botoia.addActionListener(e -> {
            if (mainFrame != null) {
                mainFrame.dispose(); // itxi bentala printzipala
            }
            loginPantailaErakutsi(); // login-era bueltatu
        });
        return botoia;
    }

    public String getIzena() {
        return izenaField.getText(); // hartzen du testua izenarena
    }

    public String getPasahitza() {
        return new String(pasahitzaField.getPassword()); // hartzen du pasahitza (konbertituta char[] String-era)
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propietatea = evt.getPropertyName();

        // Login-a
        if (Modeloa.LOGIN_EGOKIA.equals(propietatea)) {
            Erabiltzailea erabiltzailea = (Erabiltzailea) evt.getNewValue();
            loginFrame.dispose();

            // Zabaltzen du pantaila desberdin bat erabiltzailearen arabera
            switch (erabiltzailea.getPantailaMota()) {
                case "admin":
                    adminPantaila();
                    break;
                case "udala":
                    udalaPantaila();
                    break;
                case "pantaila":
                    pantailaPantaila();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Pantaila ez dago sortua");
            }
        } // Logina ez badago ondo...
        else if (Modeloa.LOGIN_OKERRA.equals(propietatea)) {
            String mezua = (String) evt.getNewValue();
            JOptionPane.showMessageDialog(loginFrame, mezua, "Errorea", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Modeloa modeloa = new Modeloa();
        Kontrolatzailea kontrolatzailea = new Kontrolatzailea(modeloa);
        Printzipala bista = new Printzipala(modeloa, kontrolatzailea);
        kontrolatzailea.setBista(bista);
    }
    //hello
}