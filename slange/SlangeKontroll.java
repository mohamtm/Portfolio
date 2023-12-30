class SlangeKontroll {
    // Klassens instansvariabler
    private SlangeGUI gui;
    private SlangeModell modell;

    // Konstruktør som initialiserer instansvariablene
    public SlangeKontroll(){
        gui = new SlangeGUI(this);
        modell = new SlangeModell(gui);
    }

    // metode som setter opp start statet til spille
    public void startSpillet(){
        modell.settOppBrettet();
    }

    // Metode for å bytte slangens vei
    public void byttVei(String vei){
        modell.byttVei(vei);
    }

    // Returnererer bool variabelen som bestemmer om spillet er ferdig.
    public boolean erSpilletFerdig(){
        return modell.erSpilletFerdig();
    }

    // Metode som beveger slangen, sjekker om spillet er over,
    // også oppdaterer brettet hvis nødvendig
    public void bevegSlange(){
        modell.beveg(); // Beveger slangen
        // Hvis vi ikke har tapt oppdater brettet
        if(!modell.harTapt()){
            modell.oppdaterBrett();
        }
    }

    // Avslutter spillet
    public void avsluttSpill(){
        System.exit(1);
    }
}
