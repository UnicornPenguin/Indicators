//import klas biblioteki Jsoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import klas obs³ugi wyj¹tków wejœcia/wyjœcia.
import java.io.IOException;

public class ReadData {//klasa obs³  uguj¹ca odczyt danych 
	//notowania ze strony internetowej
double getQuotation() throws IOException //metoda pobieraj¹ca wartoœæ notowania 
//ze strony internetowej z obs³ug¹ wyj¹tków - ta obs³uga musi byæ w ka¿dej klasie, która korzysta z tej metody 
{
	double QuotationValue;//wartoœæ notowania
	String tmpQuotation="";//tymczasowa zmienna typu string, poniewa¿ na stronie internetowej mamy ci¹g znaków -
	//patrz dokumentacja
	
	//utworzenie dokumentu i po³¹czenie ze stron¹ internetow¹ i pobranie jej zawartoœci
	Document doc =Jsoup.connect("https://biznes.interia.pl/gieldy/notowania-gpw/profil-akcji-cdr,wId,1815").get();
	
	//dokonanie selekcji znaczników div, poniewa¿ notowanie na stronie internetowej zawiera siê 
	//w znaczniku div - patrz dokumentacja
	Elements Quotation = doc.select("span");
	
	//znajdowanie klasy profilLast, która zawiera wartoœæ notowania na stronie www.bankier.pl - patrz dokumentacja
	// wykorzystana zostanie pêtla
	for (Element src :Quotation) // sprawdzony zostanie ka¿dy element div
	{
		if(src.attr("class").equals("typography ids-typeset-title ids-typeset-title--preset-large ids-typeset-title--preset-large-strong"))//je¿eli atrybut class = profilLast, to nastêpuje zapis wartoœci
			//znajduj¹cej siê w znaczniku div i klasie profilLast do zmiennej tmpQuotation
		{
			tmpQuotation=src.text();
		}
		//odczytana wartoæ posiada przecinek  jako separator dziesiêtny, a separatorem dziesiêtnym zmiennej typu
		//double jest kropka - trzeba wiêc zamieniæ przecinek na kropkê
		//odczytana wartoœæ tekstowa zawira równie¿ spacjê i tekst z³ - nale¿y je usun¹æ
		tmpQuotation = tmpQuotation.replace(",", ".");//zamiana przecinka na kropkê 
		//(w odczytanym notowaniu by³ przecinek)
		
		tmpQuotation = tmpQuotation.replace(" ", "");//usuniêcie spacji
		tmpQuotation = tmpQuotation.replace("PLN", ""); //usuniêcie tekstu z³ - otrzymujemy
		//tekst zawieraj¹cy tylko znaki liczbowe
		
	}
		
		
	
	//System.out.println(tmpQuotation);
	QuotationValue=Double.parseDouble(tmpQuotation);//wartoœæ typu tekstowego 
	//zostanie skonwertowana na wartoœæ typu double 
	return QuotationValue;
	
}
	

}
