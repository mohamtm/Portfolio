import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SlangeGUI {
    // deklarerer alle instans variablene
    private SlangeKontroll kontroll;
    private JFrame vindu;
    private JPanel hovedpanel;
    private JPanel poeng;
    private JPanel bevegelser;
    private JPanel exit;
    private JPanel rutenett;
    private JButton opp, venstre, hoyre, ned, slutt;
    private JButton[][] ruter = new JButton[12][12]; 
    private JLabel antallPoeng;

    // Konstruktør som setter opp det grafiske interfacet
    public SlangeGUI(SlangeKontroll kontroll){
        this.kontroll = kontroll;
        try{
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        }catch(Exception e) {System.exit(1);}
        
        // Setter opp vinduet
        vindu = new JFrame("Slangespillet");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setter opp panelen hvor alle delene skal ligge
        hovedpanel = new JPanel();
        hovedpanel.setLayout(new BorderLayout());
        vindu.add(hovedpanel);

        // Setter opp panelen hvor poeng scoren er satt opp
        poeng = new JPanel();
        poeng.setLayout(new BorderLayout());
        antallPoeng = new JLabel("Lengde: 1");
        poeng.add(antallPoeng,BorderLayout.CENTER);
        hovedpanel.add(poeng, BorderLayout.WEST);

        // Setter opp panelen hvor knappene for bevegelse er satt opp

        // Klassen som implementerer funksjonalitet for å bytte vei
        class Beveging implements ActionListener{
            private String vei;

            public Beveging(String vei){
                this.vei = vei;
            }

            @Override
            public void actionPerformed (ActionEvent e){
                kontroll.byttVei(vei);
            }
        }
        bevegelser = new JPanel();
        bevegelser.setLayout(new BorderLayout());

        // Lager knappene opp, ned, høyre og venstre
        opp = new JButton("Opp");
        opp.addActionListener(new Beveging("OPP"));
        hoyre = new JButton("Hoyre");
        hoyre.addActionListener(new Beveging("HOYRE"));
        venstre = new JButton("Venstre");
        venstre.addActionListener(new Beveging("VENSTRE"));
        ned = new JButton("Ned");
        ned.addActionListener(new Beveging("NED"));
        
        // Legger knapene til panelet
        bevegelser.add(opp,BorderLayout.NORTH);
        bevegelser.add(hoyre,BorderLayout.EAST);
        bevegelser.add(venstre,BorderLayout.WEST);
        bevegelser.add(ned,BorderLayout.SOUTH);
        bevegelser.add(new JPanel(), BorderLayout.CENTER);
        hovedpanel.add(bevegelser,BorderLayout.CENTER);

        // Setter opp panelen hvor slutt knappen er satt opp

        // klassen som implementerer funksjonalitet for å slutte spillet
        class Stopper implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                kontroll.avsluttSpill();
            }
        }
        exit = new JPanel();
        exit.setLayout(new BorderLayout());
        slutt = new JButton("Slutt");
        slutt.addActionListener(new Stopper());
        exit.add(slutt,BorderLayout.CENTER);
        hovedpanel.add(exit,BorderLayout.EAST);

        // setter opp panelen hvor rutenettet er satt opp
        rutenett = new JPanel();
        rutenett.setLayout(new GridLayout(12,12));
        // Loopen lager 12x12 jbuttons og legger det til panelet
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                JButton b = new JButton();
                ruter[i][j] = b;
                b.setPreferredSize(new Dimension(25,25));
                b.setOpaque(true);
                b.setBackground(Color.WHITE);
                b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                rutenett.add(b);
            }
        }
        hovedpanel.add(rutenett, BorderLayout.SOUTH);

        // Packer vinduet og viser det frem
        vindu.pack();
        vindu.setVisible(true);
    }

    // Metode som tegner brettet
    public void tegnBrett(char[][] tegnRutene, int poengscore){
        // nøsta loop, looper gjennom alle rutene i brettet
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){

                // Switch for hva slags type rute det skal være
                // '$' betyr skatt
                // 'O' betyr Hode
                // 'X' betyr kroppsdel
                // default betyr vanlig rute 
                switch(tegnRutene[i][j]){
                    case 'O':
                    ruter[i][j].setBackground(Color.GREEN);
                    ruter[i][j].setForeground(Color.BLACK);
                    ruter[i][j].setText("O");
                    break;

                    case 'X':
                    ruter[i][j].setBackground(Color.GREEN);
                    ruter[i][j].setForeground(Color.BLACK);
                    ruter[i][j].setText("X");
                    break;

                    case '$':
                    ruter[i][j].setBackground(Color.WHITE);
                    ruter[i][j].setForeground(Color.RED);
                    ruter[i][j].setText("$");
                    break;

                    default:
                    ruter[i][j].setBackground(Color.WHITE);
                    ruter[i][j].setText("");
                }
            }
        }
        antallPoeng.setText("Lengde: " + poengscore); // Opddater poengscoren hvis aktuelt
    }
}
