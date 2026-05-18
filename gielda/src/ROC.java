import java.io.IOException; //import biblioteki obslugi wyjatkow
import java.util.Timer; //import klasy timera
import java.util.Calendar; //import kalendarza
//(klasa pomocnicza do timera
import java.util.TimerTask; //klasa pomocnicza do timera

public class ROC { 

    //deklaracja zmiennych glownych
    int N = 14; //liczba sesji historycznych
    int seconds = 60; //czas agregowania notowań 
    /*
     * Dla ROC z N okresów potrzebujemy N + 1 wartości:
     *
     * vecNotowan[0] = aktualna cena
     * vecNotowan[N] = cena sprzed N okresów
     *
     * Dla N = 14 potrzebujemy indeksów od 0 do 14.
     */
    double[] vecNotowan = new double[N + 1];

    int decyzja = 0; //1 - kup; -1 - sprzedaj; 0 - nic nie rob

    void RunROC() { //obliczanie wskaznika ROC
        Timer timer = new Timer();//utworzenie obiektu timer
        timer.schedule(new TimerRun(), 0, seconds*1000);
        //konfiguracja timera
    }

    class TimerRun extends TimerTask {


        public void run() {

            try {
                
                loadTestData();

                Double roc = calculateROC();

                if (roc == null) {
                    decyzja = 0;

                    System.out.println("--------");
                    System.out.println("Nie można obliczyć ROC.");
                    System.out.println("Cena sprzed " + N + " okresów wynosi 0.0");
                    System.out.println("Decyzja: " + decyzja);

                    return;
                }

                decyzja = calculateDecision(roc);

                System.out.println("--------");
                System.out.println("Aktualna cena: " + vecNotowan[0]);
                System.out.println("Cena sprzed " + N + " okresów: " + vecNotowan[N]);
                System.out.println("ROC: " + roc);
                System.out.println("Decyzja: " + decyzja);
 
            }
            catch(Exception e) {
                e.printStackTrace(); // dla verbosity procesu - potem można wyłączyć

            } 
        }
    }

    private void loadTestData() {

        vecNotowan[0] = 16.0;  // aktualna cena
        vecNotowan[1] = 10.0;
        vecNotowan[2] = 12.5;
        vecNotowan[3] = 11.5;
        vecNotowan[4] = 13.5;
        vecNotowan[5] = 11.5;
        vecNotowan[6] = 10.5;
        vecNotowan[7] = 14.0;
        vecNotowan[8] = 16.0;
        vecNotowan[9] = 16.5;
        vecNotowan[10] = 15.5;
        vecNotowan[11] = 12.0;
        vecNotowan[12] = 14.5;
        vecNotowan[13] = 15.5;
        vecNotowan[14] = 13.0; // cena sprzed 14 okresów
    }

    private Double calculateROC() {
        
        double cenaAktualna = vecNotowan[0];
        double cenaSprzedNOkresow = vecNotowan[N];

        if (cenaSprzedNOkresow == 0.0) {
            return null;
        }

        return ((cenaAktualna - cenaSprzedNOkresow) / cenaSprzedNOkresow) * 100.0;
    }

    private int calculateDecision(double roc) {

        if (roc > 0.0) {
            return 1;
        }

        if (roc < 0.0) {
            return -1;
        }

        return 0;
    }
}

