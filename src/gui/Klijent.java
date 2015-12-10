package gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Klijentska klasa aplikacije koja sadrzi metodu izracunaj i salje valute serveru na konverziju
 * @author neverne bede
 * @version 1.0
 * 
 */
public class Klijent {
	
	
	
	/**
	 * Definisanje tokova i soketa koji ce se koristiti
	 */
	BufferedReader ulazniTokOdServera =null;
	PrintStream izlazniTokKaServeru = null;
	Socket soketZaKomunikaciju = null;
	
	/**
	 * Predstavlja port preko kojeg ce se obavljati komunikacija
	 */
	int brojPorta = 6969;
	
	
	
	/**
	 * Koristeci parametre otvara konekciju sa serverom i salje parametre dobijene iz GUI-a serveru,
	 * nakon obrade od strane servera, vraca rezultat u obliku String-a i prosledjuje ga GUI-u
	 * @param valuta - izabrana valuta koju zelimo da konvertujemo
	 * @param valutaKonvert - izabrana valuta u koju zelimo da konvertujemo "valuta"
	 * @param iznos - kolicinu novca izabrane valute koju zelimo da pretvorimu u neku drugu valutu
	 * @return - String koji sadrzi rezultat obrade servera
	 * @throws Exception - baca Exception u slucaju IO i Socket Exceptiona
	 */
	public  String izracunaj(String valuta, String valutaKonvert,String iznos) throws Exception {
		
		/**
		 * Inicijalizacija tokova i soketa preko kojih ce se slati i primati podatke od servera
		 */
		soketZaKomunikaciju = new Socket("localhost",brojPorta);
		ulazniTokOdServera = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
		izlazniTokKaServeru = new PrintStream(soketZaKomunikaciju.getOutputStream());
		
		/**
		 * Slanje serveru izabrane valute, valute u koju zelimo da konvertujemo, i iznosa
		 */
		izlazniTokKaServeru.println(valuta);
		izlazniTokKaServeru.println(valutaKonvert);
		izlazniTokKaServeru.println(iznos);
		
		/**
		 * Citanje odgovora od servera
		 */
		String rezultat = ulazniTokOdServera.readLine();
		
		/**
		 * Ukoliko server ne uspe iz nekog razloga da izvrsi konverziju vrati ce ovaj String
		 */
		if(rezultat.startsWith("Morate uneti"))
			return "Morate uneti";
		
		/**
		 * Zatvaranje socketa za komunikaciju
		 */
		soketZaKomunikaciju.close();
		return rezultat;
	}
}
