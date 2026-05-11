import java.io.IOException; //import biblioteki obslugi wyjatkow
import java.util.Timer; //import klasy timera
import java.util.Calendar; //import kalendarza
//(klasa pomocnicza do timera
import java.util.TimerTask; //klasa pomocnicza do timera

public class ROC { //klasa obslugi wskaznika RSI
    //deklaracja zmiennych glownych
    int N = 14; //liczba sesji historycznych
    int seconds = 60; //czas agregowania notowań (uruchamianie timera w zależności od przedziału czasu notowań, np. minutowe, godzinne)
    int decyzja = 0; //1 - kup; -1 - sprzedaj; 0 - nic nie rob
    double[] vecNotowan = new double[N];
    //wektor notowan - biezace notowanie + 13 poprzednich

    void RunRSI() { //obliczanie wskaznika RSI
        Timer timer1 = new Timer();//utworzenie obiektu timer
        timer1.schedule(new TimerRun(), 0, seconds*1000);
        //konfiguracja timera
    }

    class TimerRun extends TimerTask {
        public void run()
        {
            try {//wyłapywanie wyjątków
                //kod znajdujący się pomiędzy
                //nawiasami klamrowymi try
                //jest analizowany pod kątem
                //wystąpienia wyjątków
                ReadData RD = new ReadData();

//utworzenie obiektu klasy ReadData
//(odczytujacej biezaca watrtosc notowania)

                for (int i=N-1; i>0; i--)
                //pętla przesuwająca wartości notowań
                //tak, aby zawsze było 14 ostatnich
                //notowań w wektorze
                {
                    vecNotowan[i] = vecNotowan[i-1];
                }

                vecNotowan[0] = RD.getQuotation();
                System.out.println(RD.getQuotation());
                //przepisanie do pierwszego elementu
                //wektora aktualnej wartości notowania
                //odczytanej za pomocą metody
                //getQuotation (klasa ReadData)

                vecNotowan[1] = 10.0;
                vecNotowan[2] =	12.5;
                vecNotowan[3] = 13.5;
                vecNotowan[4] = 11.0;
                vecNotowan[5] =	10.5;
                vecNotowan[6] = 14.0;
                vecNotowan[7] = 14.0;
                vecNotowan[8] = 12.5;
                vecNotowan[9] = 11.5;
                vecNotowan[10] = 10.5;
                vecNotowan[11] = 12.0;
                vecNotowan[12] = 15.0;
                vecNotowan[13] = 15.5;

                //np. 15- 13 bedzie jako wzrost notowane
                //Sprawdzanie wartości ( od n odejmujemy n+1) - od mniejszej wiekszosc??

					/*System.out.println(vecNotowan[0]);
					System.out.println(vecNotowan[1]);
					System.out.println(vecNotowan[2]);
					System.out.println(vecNotowan[3]);
					System.out.println(vecNotowan[4]);
					System.out.println(vecNotowan[5]);
					System.out.println(vecNotowan[6]);
					System.out.println(vecNotowan[7]);
					System.out.println(vecNotowan[8]);
					System.out.println(vecNotowan[9]);
					System.out.println("--------"); */

                double wzrosty = 0.0; //srednia wartosc wzrostu z N sesji
                double spadki = 0.0; //srednia wartosc spadku z N sesji
                double RS = 0.0;//wartość RS ze wzoru (patrz dokumentacja)
                double ROCValue = 0.0;//w tej zmiennej przechowywana
                //jest wartość wskaznika RSI (patrz dokumentacja) .
                int licz_wzrosty = 0;//licznik wzrostów w celu
                //obliczenia średniej wzrostów
                int licz_spadki = 0;//licznik spadków w celu
                //obliczenia średniej spadków

                for (int i=0; i<N-1; i++) {//obliczanie wzrostów
                    if (vecNotowan[i] > vecNotowan[i+1]) {
                        //jeśli wartość następna jest większa od poprzedniej
                        //to jest wzrost

                        wzrosty = wzrosty + (vecNotowan[i] - vecNotowan[i+1]);
                        //do aktualnej wartości wzrostów dodajemy
                        //różnicę pomiędzy wartością następną, a poprzednią
                        licz_wzrosty++;//zwiększenie wartości licznika
                        //wzrostów o 1

                    }
                    //obliczanie spadków
                    if ((vecNotowan[i] < vecNotowan[i+1])) {
                        //jeśli wartość następna jest mniejsza od poprzedniej
                        //to jest spadek
                        spadki = spadki - (vecNotowan[i] - vecNotowan[i+1]);
                        //od aktualnej wartości spadków odejmujemy
                        //różnicę pomiędzy wartością następną, a poprzednią
                        licz_spadki++;//zwiększenie wartości licznika
                        //spadków o 1

                    }
                }


                //System.out.println(wzrosty);
                //System.out.println(spadki);

                if (licz_wzrosty > 0)//sprawdzanie, czy
                //nie będzie dzielenia przez 0
                {

                    wzrosty = wzrosty/licz_wzrosty;
                    //obliczamy średnią wzrostów

                }
                if (licz_spadki > 0) //sprawdzanie, czy
                //nie będzie dzielenia przez 0
                {

                    spadki = spadki/licz_spadki;
                    //obliczamy średnią spadków
                }

                if (spadki > 0) RS = wzrosty/spadki;//sprawdzenie,
                //czy nie będzie dzielenia przez 0
                ROCValue = 100 - (100/(1+RS));//obliczanie wskaznika RSI

                System.out.println("--------");
                System.out.println(ROCValue);

                decyzja = 0;
                if (ROCValue >= 70) decyzja = -1;//decyzja sprzedaży
                //(patrz dokumentacja)
                if (ROCValue <= 30) decyzja = 1;//decyzja kupna
                //(patrz dokumentacja)
            }

            catch(Exception e) {} //obsluga wyjatkow
        }
    }
}
