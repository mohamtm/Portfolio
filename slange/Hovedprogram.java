public class Hovedprogram {
    public static void main(String args[]){
        SlangeKontroll kontroll = new SlangeKontroll(); // Oppretter kontroll objektet som opretter GUI
        kontroll.startSpillet(); // starter spillet
        // Beveger slangen hvert halv sekund inntill spillet er over.
        try{
            while(!kontroll.erSpilletFerdig()){
                Thread.sleep(500);
                kontroll.bevegSlange();
            }  
        }catch(InterruptedException e){System.out.println("Klokke traad i main avbrutt.");}
    }
}
