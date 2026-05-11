
public class Gielda {

	public static void main(String[] args) {
		//System.out.println("Gielda 03. 10.2020");
		//RSI RSIWsk= new RSI();//utworzenie nowego obiektu klasy RSI
		//obiekt, to "�yj�ce" wyst�pienie klasy.
		//Klasa jest tylko bytem abstrakcyjnym - nie mo�e by� uruchomiona
		//tylko obiekty mog� dzia�a� jako uruchomione procesy
		//proces to uruchomiony program
		//RSIWsk.RunRSI();//Wywo�anie metody RunRSI klasy RSI
		//System.out.println(RSIWsk.RunRSI());
		RSI RSIWsk = new RSI();
		RSIWsk.RunRSI();
		ROC ROCWsk = new ROC();
		ROCWsk.RunROC();

	}

}
