import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

class patroon  {
	private int aantalRijen;
	private int aantalKolommen;
	
    private	char[][] design;
	
	public patroon(basisPatroon kopiePatroon) {
		aantalRijen = kopiePatroon.getAantalRijen();
		aantalKolommen = kopiePatroon.getAantalKolommen();
		
		design = kopiePatroon.getDesign();
	}
	public patroon(int rijen, int kolommen, char[][] patroon) {
		aantalRijen = rijen;
		aantalKolommen = kolommen;
		design = patroon;
	}
	public patroon() {
		aantalRijen = 0;
		aantalKolommen = 0;
		design = new char[0][0];
	}
	
	// instructies: patroon toevoegen, naai, draai, teken, stop, 
	public patroon naai(patroon rechtsPatroon) {
		// naaien: checken of aantal rijen gelijk is, kolommen optellen, design samenvoegen
		if (this.aantalRijen!=rechtsPatroon.aantalRijen) {
			System.out.println("aantal rijen niet gelijk");
			patroon genaaidPatroon = new patroon();
			return genaaidPatroon;
		}
		else {
			int rijen = this.aantalRijen;
			int kolommen = this.aantalKolommen + rechtsPatroon.aantalKolommen;
			char[][] nieuwDesign = new char[rijen][kolommen];
			
			for (int rijNummer=0; rijNummer<rijen; rijNummer++) {
				for (int kolomNummer=0; kolomNummer<kolommen; kolomNummer++) {
					// als kolom kleiner is dan links patroon aantal kolommen, kopieer links patroon, als het groter is, kopieer rechts patroon
					if(kolomNummer<this.aantalKolommen)	nieuwDesign[rijNummer][kolomNummer] = this.design[rijNummer][kolomNummer];
					else nieuwDesign[rijNummer][kolomNummer]=rechtsPatroon.design[rijNummer][kolomNummer-this.aantalKolommen];				
				}
			}
			patroon genaaidPatroon = new patroon(rijen, kolommen, nieuwDesign);
			return genaaidPatroon;
			
		}	
	}
	
	public patroon draai() {
		// draaien: aantal rijen en kolommen verwisselen, design draaien(eerste rij wordt laatste kolom, laatste rij wordt eerste kolom, karakters aanpassen
		int rijen = this.aantalKolommen;
		int kolommen = this.aantalRijen;
		char[][] nieuwDesign = new char[rijen][kolommen];	
		
		for (int rijNummer=0; rijNummer<rijen; rijNummer++) {
			for (int kolomNummer=0; kolomNummer<kolommen; kolomNummer++) {
				// oud rijnr = oud aantal rijen - 1 - nieuw kolomnr
				// oud kolomnr = nieuw rijnr
				nieuwDesign[rijNummer][kolomNummer]=this.design[this.aantalRijen-1-kolomNummer][rijNummer];
				
				// tekens aanpassen: + wordt +, - wordt |, \ wordt /, / wordt \, | wordt -
				switch(nieuwDesign[rijNummer][kolomNummer]) {
				case '-':
					nieuwDesign[rijNummer][kolomNummer] = '|';
					break;
				case '|':
					nieuwDesign[rijNummer][kolomNummer] = '-';
					break;
				case '/':
					nieuwDesign[rijNummer][kolomNummer] = '\\';
					break;
				case '\\':
					nieuwDesign[rijNummer][kolomNummer] = '/';
					break;
				}
			}
		}	
		patroon gedraaidPatroon = new patroon(rijen, kolommen, nieuwDesign);
		return gedraaidPatroon;		
	}
	
	public void teken() {
		for (int rijNummer=0; rijNummer<this.aantalRijen; rijNummer++) {
			for (int kolomNummer=0; kolomNummer<this.aantalKolommen; kolomNummer++) {
				System.out.print(this.design[rijNummer][kolomNummer]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
class basisPatroon {
	private int volgNummer;
	private char[][] patroon;	
	
	public basisPatroon(int nummer, char[][] patr) {
		volgNummer = nummer;
		if (patr.length!=2 || patr[0].length!=2) System.out.println("patroon verkeerde grootte");
		else patroon=patr;
	}
	
	public int getAantalRijen() {
		return patroon.length;
	}
	public int getAantalKolommen() {
		return patroon[0].length;
	}
	public char[][] getDesign(){
		return patroon;
	}
	
}

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//aantal opgaves inlezen, for lus om iedere opgave uit te voeren
		int aantalOpgaves=Integer.parseInt(sc.nextLine());
		for (int opgaveNummer=0; opgaveNummer<aantalOpgaves; opgaveNummer++) {
			
			//Stack aanmaken met behulp van de Stack class
			Stack<patroon> stapel = new Stack<>();			
			
			//aantal patronen inlezen, for lus om ieder patroon aan te maken en toe te voegen aan array van de basispatronen
			int aantalBasisPatronen=Integer.parseInt(sc.nextLine());
			List<basisPatroon> patroonLijst = new ArrayList<basisPatroon>();
			
			for (int basisPatroonNummer=0; basisPatroonNummer<aantalBasisPatronen; basisPatroonNummer++) {
				char[][] patroon = new char[2][2];
				
				// 2 keer een lijn inlezen en toevoegen aan de matrix patroon
				for (int i=0; i<2; i++) {
					String inputLijn = sc.nextLine();
					patroon[i][0]=inputLijn.charAt(0);
					patroon[i][1]=inputLijn.charAt(1); 
				}
				
				//nieuw basispatroon toevoegen aan de lijst
				patroonLijst.add(new basisPatroon(basisPatroonNummer+1, patroon));
			}
			
			//aantal bevelen inlezen, for lus om ieder bevel na elkaar uit te voeren
			int aantalBevelen=Integer.parseInt(sc.nextLine());
			for (int bevelNummer=0; bevelNummer<aantalBevelen; bevelNummer++) {
				
				
				// bevel inlezen en kijken of het een integer is of een string
				String bevel = sc.nextLine();
				if(Character.isDigit(bevel.charAt(0))) {
					
					// nieuw patroon(kopie van basispatroon) toevoegen aan stapel
					patroon p= new patroon(patroonLijst.get(Character.getNumericValue(bevel.charAt(0))-1));
					stapel.push(p);
				}
				else {
					
					// instructie uitvoeren
					switch (bevel) {
					case "naai":  
						patroon naaiPatroon1 = stapel.pop();
						patroon naaiPatroon2 = stapel.pop();
						patroon genaaidPatroon=naaiPatroon1.naai(naaiPatroon2);
						stapel.push(genaaidPatroon);
						break; // haalt de twee bovenste lappen van de stapel, naait ze aan elkaar
					
					case "draai":
						patroon teDraaienPatroon = stapel.pop();
						patroon gedraaidPatroon = teDraaienPatroon.draai();
						stapel.push(gedraaidPatroon);
						break; // draait de lap bovenaan de stapel in wijzerzin.
					
					case "teken":
						patroon teTekenenPatroon = stapel.pop();
						teTekenenPatroon.teken();
						stapel.push(teTekenenPatroon);						
						break; // doet de huidige top verschijnen in de output en laat dan een regel open
					
					case "stop":
						bevelNummer = aantalBevelen;
						break; // beëindigt het programma.
					}	//switch				
				}	//if else
			}	//for bevelen
		}	// for opgaves	
		sc.close();
	}
}
