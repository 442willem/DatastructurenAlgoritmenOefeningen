import java.util.Scanner;
import java.lang.String;
import java.util.*; 

class weging{
	private int resultaat; //0=evenwicht,1=omlaag,2=omhoog
	private String linkerKant;
	private String rechterKant;
	
	public weging(String result, String links, String rechts) {
		if(result.equals("evenwicht"))resultaat=0;
		else if(result.equals("omlaag"))resultaat=1;
		else resultaat=2;
		
		linkerKant=links;
		rechterKant=rechts;
	}
	
	public int getResultaat() {
		return this.resultaat;
	}	
	public String getRechts() {
		return this.rechterKant;
	}	
	public String getLinks() {
		return this.linkerKant;
	}
}	

public class Main {
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		
		//aantal opgaves inlezen, for lus om iedere opgave uit te voeren
		int aantalSets=Integer.parseInt(sc.nextLine());
		for (int opgaveNummer=0; opgaveNummer<aantalSets; opgaveNummer++) {
			
			//aantal wegingen inlezen
			int aantalWegingen=Integer.parseInt(sc.nextLine());
			Set<weging> wegingSet = new HashSet<weging>();
			
			//booleans voor output te bepalen
			boolean	tegenSprekendeWeging=false;
			boolean dubbelStuk = false;
			
			//wegingen inlezen en in een set steken
			for (int wegingNummer=0; wegingNummer<aantalWegingen; wegingNummer++) {
				String nieuweLijn=sc.nextLine();
				String[] lijnArray = nieuweLijn.split(" ");
				wegingSet.add(new weging(lijnArray[2],lijnArray[0],lijnArray[1]));
			}
			
			//3 sets, echt, lichter en zwaarder
			Set<Character> echtGeld = new HashSet<Character>();
			Set<Character> lichter = new HashSet<Character>();
			Set<Character> zwaarder = new HashSet<Character>();
			
			//evenwichten bekijken, alles toevoegen aan set echt
			for(Iterator<weging> it = wegingSet.iterator(); it.hasNext();){
				weging huidigeWeging = it.next();  
				if(huidigeWeging.getResultaat()==0) {
					   	//toevoegen
					   	int aantalLetters=huidigeWeging.getLinks().length();
					   	for(int index=0;index<aantalLetters;index++) {
					   		echtGeld.add(huidigeWeging.getLinks().charAt(index));
					   		echtGeld.add(huidigeWeging.getRechts().charAt(index));
					   	}
					   	// weging verwijderen uit set
					   	it.remove();
				   }				   
			}			
			
			//omhoog/omlaag bekijken, als de letters nog niet in echt geld zitten-> toevoegen aan lichter of zwaarder
			for(Iterator<weging> it = wegingSet.iterator(); it.hasNext();){
				weging huidigeWeging = it.next();
				boolean onmogelijkeWeging = true;
				
				//alle rechtse toevoegen, als er geen toegevoegd worden -> onmogelijkeWeging blijft true
				int aantalLetters=huidigeWeging.getRechts().length();
				for (int i=0;i<aantalLetters;i++) {
					if (!echtGeld.contains(huidigeWeging.getRechts().charAt(i))) {
						onmogelijkeWeging=false;
						if(huidigeWeging.getResultaat()==1) {
							zwaarder.add(huidigeWeging.getRechts().charAt(i));
						}
						else {
							lichter.add(huidigeWeging.getRechts().charAt(i));
						}
					}
				}
				
				//alle linkse toevoegen, als er geen toegevoegd worden -> onmogelijkeWeging blijft true
				for (int i=0;i<aantalLetters;i++) {
					if (!echtGeld.contains(huidigeWeging.getLinks().charAt(i))) {
						onmogelijkeWeging=false;
						if(huidigeWeging.getResultaat()==1) {
							lichter.add(huidigeWeging.getLinks().charAt(i));
						}
						else {
							zwaarder.add(huidigeWeging.getLinks().charAt(i));
						}
					}
				}
				
				//als onmogelijkeWeging nog steeds true is->huidige weging spreekt vorige tegen
				if(onmogelijkeWeging) tegenSprekendeWeging=true ;		
			}		
			
		// testen of een muntstuk zowel lichter als zwaarder is (sectie is niet leeg)-> inconsistent	
		Set<Character> sectie = new HashSet<Character>(lichter);
		sectie.retainAll(zwaarder);
		if (sectie.size()!=0)dubbelStuk=true;		
		
		if(echtGeld.size()==26 || tegenSprekendeWeging || dubbelStuk || (lichter.size()==zwaarder.size() && lichter.size()>1 && zwaarder.size()>1)) {
			System.out.println("Inconsistente gegevens.");
		}
		else if(lichter.size()==1 && zwaarder.size()!=1) {
			System.out.println("Het valse geldstuk " + lichter.toString().charAt(1) + " is lichter.");
		}
		else if(lichter.size()!=1 && zwaarder.size()==1) {
			System.out.println("Het valse geldstuk " + zwaarder.toString().charAt(1) + " is zwaarder.");
		}
		else System.out.println("Te weinig gegevens.");
		
		}
		sc.close();
	}
}	