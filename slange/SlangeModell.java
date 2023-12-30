import java.util.ArrayList;

class SlangeModell {
    // Klassens instansvariabler
    private SlangeGUI gui;
    private ArrayList<Slange> slangebitene = new ArrayList<>();
    private boolean erSpilletFerdig;
    private int poengscore;
    private char[][] rutene;
    private String hvilkenVei;
    private int[] sisteBitPosisjon;
    
    // Konstruktør som initialiserer instansvariablene
    // og legger hode til slange til listen vår.
    public SlangeModell(SlangeGUI gui){
        this.gui = gui;
        erSpilletFerdig = false;
        slangebitene.add(new Slange(6,6,'O'));
        poengscore = 1;
        rutene = new char[12][12];
        hvilkenVei = "VENSTRE";
        sisteBitPosisjon = new int[]{0,0};
    }
    // Returnererer bool variabelen som bestemmer om spillet er ferdig.
    public boolean erSpilletFerdig(){
        return erSpilletFerdig;
    }

    // Metode for å bytte slangens vei
    public void byttVei(String vei){
        hvilkenVei = vei;
    }

    // Metode som finner et tilfeldig tall mellom a og b. Begge inkludert.
    private int trekk(int a, int b){
        return (int)(Math.random()*(b-a+1))+a;
    }

    // metode som legger 10 skatter på brettet.
    private void leggTilS10Skatter(){
        int a; // nedre grense
        int b; // øvre grense
        int teller = 0; // antall skatter som er lagt på brettet
        // Looper til 10 har blitt lagt på brettet
        while(teller < 10){
            a = trekk(0,11);
            b = trekk(0,11);
            // Hvis rutene er ledige sett en skatt der
            if(rutene[a][b] != '$' && rutene[a][b] != 'O'){
                rutene[a][b] = '$';
                teller += 1;
            }
        }
    }

    // Metode som setter opp brettet ved start
    public void settOppBrettet(){
        int[] hodePosisjon = slangebitene.get(0).giRadKolonne(); // Koordinatene til hodet;
        int rad = hodePosisjon[0];
        int kolonne = hodePosisjon[1];
        rutene[rad][kolonne] = slangebitene.get(0).giMerke(); // forteller brettet hvor hodet ligger
        leggTilS10Skatter(); // legger til de 10 skattene
        gui.tegnBrett(rutene, poengscore); // ber gui om å tegne brettet.
    }

    // Metode som beveger slangen i veien hvilkenVei bestemmer.
    public void beveg(){
        sisteBitPosisjon = slangebitene.get(slangebitene.size() - 1).giRadKolonne(); // Lagrer posisjon til biten som ligger bakerst
        // Looper gjennom alle bitene, unntatt hode.
        // Dytter biten til posisjonen biten foran ligger
        for(int i = slangebitene.size() - 1; i > 0; i--){
            int[] oppdatertPosisjon = slangebitene.get(i - 1).giRadKolonne();
            slangebitene.get(i).byttRadOgKolonne(oppdatertPosisjon[0], oppdatertPosisjon[1]);
        }
        // Switch som beveger hodet i retningen som hvilkenVei bestemmer.
        int[] radOgKolonne = slangebitene.get(0).giRadKolonne();;
        int rad;
        int kol;
        switch(hvilkenVei){
            case "HOYRE":
            kol = radOgKolonne[1] + 1;
            rad = radOgKolonne[0];
            slangebitene.get(0).byttRadOgKolonne(rad, kol);
            break;

            case "VENSTRE":
            kol = radOgKolonne[1] - 1;
            rad = radOgKolonne[0];
            slangebitene.get(0).byttRadOgKolonne(rad, kol);
            break;

            case "OPP":
            kol = radOgKolonne[1];
            rad = radOgKolonne[0] - 1;
            slangebitene.get(0).byttRadOgKolonne(rad, kol);
            break;

            case "NED":
            kol = radOgKolonne[1];
            rad = radOgKolonne[0] + 1;
            slangebitene.get(0).byttRadOgKolonne(rad, kol);
            break;
        }
    }

    // Metode som bestemmer om slangen fant en skatt
    private boolean skattFunnet(){
        int[] hodePosisjon = slangebitene.get(0).giRadKolonne(); // Hodets posisjon
        int rad = hodePosisjon[0];
        int kolonne = hodePosisjon[1];
        // Hvis hodets posisjon samsvarer med en rute som har en skatt
        if(rutene[rad][kolonne] == '$'){
            poengscore += 1;
            return true;
        }
        return false;
    }
    
    // Metode som legger til en ny skatt
    private void nySkatt(){
        ArrayList<int[]> gyldigePosisjoner = new ArrayList<>(); // ArrayList som skal holde gyldige posisjoner
        // Loopen går gjennom brettet for å finne ledige ruter
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                if(rutene[i][j] == '\0'){
                    gyldigePosisjoner.add(new int[]{i,j});
                }
            }
        }
        // Hvis ledig rute funnet,
        // velg en tilfeldig rute,
        // og legg til en skatt der
        if(gyldigePosisjoner.size() > 0){
            int a = 0;
            int b = gyldigePosisjoner.size() - 1;
            int posisjon = trekk(a,b);
            int[] posisjoner = gyldigePosisjoner.get(posisjon);
            rutene[posisjoner[0]][posisjoner[1]] = '$';
        }
    }

    public void oppdaterBrett(){
        // Sjekker om en skatt ble funnet
        if(skattFunnet()){
            // Hvis skatten ble funnet så legger vi til en bit i enden av slangen
            // Deretter så legges det til en ny skatt i en ledig rute.
            slangebitene.add(new Slange(sisteBitPosisjon[0],sisteBitPosisjon[1], 'X'));
            nySkatt();
        }
        else{
            // Hvis skatten ikke ble funnet så nuller vi ut der halen var
            rutene[sisteBitPosisjon[0]][sisteBitPosisjon[1]] = '\0';
        }
        // Setter slangens nye posisjoner på brettet
        for(int i = 0; i < slangebitene.size(); i++){
            int[] posisjon = slangebitene.get(i).giRadKolonne();
            int rad = posisjon[0];
            int kolonne = posisjon[1];
            rutene[rad][kolonne] = slangebitene.get(i).giMerke();
        }
        gui.tegnBrett(rutene, poengscore); // ber gui om å tegne det nye brettet
    }

    // metode som sjekker om kondisjonene for å tape har blitt møtt
    public boolean harTapt(){
        int[] hodePosisjon = slangebitene.get(0).giRadKolonne(); // Posisjonen til hodet
        // sjekker om hodet ligger utenfor brettets grenser
        if(hodePosisjon[0] < 0 || hodePosisjon[0] > 11 || hodePosisjon[1] < 0 || hodePosisjon[1] > 11){
            erSpilletFerdig = true;
            return true;
        }
        // sjekker om hodet ligger inni kroppen sin
        for(int i = 1; i < slangebitene.size(); i++){
            if(slangebitene.get(0).equals(slangebitene.get(i))){
                erSpilletFerdig = true;
                return true;
            }
        }
        return false; // returnerer false hvis kondisjonene ikke er møtt
    }
}
