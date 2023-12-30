class Slange {
    private char merke; // slange bitens markør
    private int rad, kolonne; // slange bitens posisjon

    // konstruktør som intialiserer variablene
    public Slange(int r, int k, char m){
        rad = r;
        kolonne = k;
        merke = m;
    }

    // Returnerer merke
    public char giMerke(){
        return merke;
    }

    // returnerer rad og kolonne som int array
    public int[] giRadKolonne(){
        int[] radOgKolonne = new int[]{rad,kolonne};
        return radOgKolonne;
    }

    // Metode som bytter rad og kolonne
    public void byttRadOgKolonne(int nyRad, int nyKolonne){
        rad = nyRad;
        kolonne = nyKolonne;
    }

    // Overrider equals metoden til å returnere true hvis posisjonene er like
    @Override
    public boolean equals(Object o){
        Slange sjekk = (Slange) o;
        int[] sjekkPosisjon = sjekk.giRadKolonne();
        return sjekkPosisjon[0] == rad && sjekkPosisjon[1] == kolonne;
    }
}
