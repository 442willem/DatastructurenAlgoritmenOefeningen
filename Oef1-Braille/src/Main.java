import java.util.*;

class brailleLetter {
	static Map< String,Character> alfabet= new HashMap< String,Character>();   //map met alle letters A-Z
	

	private String brailleString;

	public brailleLetter (){ //Default constructor
		char[][] brailleMatrix=new char[3][2]; 
		for (int i=0; i<3;i++) {
			for (int j=0; j<2;j++) {
				brailleMatrix[i][j] = 0;
			}
		}
		brailleString = decodeMatrix(brailleMatrix);
	}
	public brailleLetter (char[][] matrix){
		brailleString = decodeMatrix(matrix);		
	}
	
	public void setMatrix (char[][] matrix){
		brailleString = decodeMatrix(matrix);
	}
	
	static private String decodeMatrix(char[][] matrix) { //3x2 matrix omzetten naar een string van 6 karakters. 
		if(matrix[0].length!=2 || matrix.length!=3) return "error";
		else {
			String brailleWoord="";
			for (int i=0; i<3;i++) {
				for (int j=0; j<2;j++) {
					brailleWoord=brailleWoord + matrix[i][j];
				}
			}
			return brailleWoord;
		}
	}
	private char decodeString(String brailleWoord) { // String van 6 karakters vergelijken met de strings uit het alfabet en de bijhorende letter returnen
		return alfabet.get(brailleWoord);
	}
	public char getLetter() {
		return this.decodeString(this.brailleString);
	}
	
	public static void updateAlfabet(char[][] matrix, int plaats) { // letter toevoegen aan het alfabet
		alfabet.put(brailleLetter.decodeMatrix(matrix), (char)(65+plaats));
	}
	
	public static void printAlfabet() { // alfabet uitprinten (gebruikt ter controle)
		Set< Map.Entry< String,Character> > alfabetPaar = alfabet.entrySet();
		for (Map.Entry<String,Character> alfabetLetter:alfabetPaar) 
	       { 
	           System.out.print(alfabetLetter.getKey()+":"); 
	           System.out.println(alfabetLetter.getValue()); 
	       } 
	}
}

public class Main {
	public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	
// Eerste 3 lijnen inlezen en opslaan in strings. Strings per 2x3 matrix opslaan in het alfabet
	// System.out.println("3 lijnen alfabet");
	String alfabetLijn1 = sc.nextLine();
	String alfabetLijn2 = sc.nextLine();
	String alfabetLijn3 = sc.nextLine();
	char[][] brailleLetterInvoer=new char[3][2];
	
	for(int letterPlaats=0; letterPlaats<26; letterPlaats++) {
		for(int j=0;j<2;j++) {		
			int i=0;
			brailleLetterInvoer[i][j]=alfabetLijn1.charAt(j+(letterPlaats*2));
			i++;
			brailleLetterInvoer[i][j]=alfabetLijn2.charAt(j+(letterPlaats*2));
			i++;
			brailleLetterInvoer[i][j]=alfabetLijn3.charAt(j+(letterPlaats*2));		
		}	
		brailleLetter.updateAlfabet(brailleLetterInvoer,letterPlaats);
	}
		//	brailleLetter.printAlfabet();
	
//	aantal lijnen te vertalen input opslaan en output array opstellen
	//  System.out.println("aantal lijnen input");
	int aantalLijnen = Integer.parseInt(sc.nextLine());
	String[] outputArray= new String[aantalLijnen];
	
//	per 3 lijnen inlezen en vertalen van de letters, resultaat opslaan in output array
	for (int lijnNummer=0; lijnNummer<aantalLijnen; lijnNummer++) {
	//	System.out.println("Volgende 3 lijnen input");
		String invoerLijn1 = sc.nextLine();
		String invoerLijn2 = sc.nextLine();
		String invoerLijn3 = sc.nextLine();
		String output = "";
		
		for(int letterPlaats=0; letterPlaats<(invoerLijn1.length()/2); letterPlaats++) {
			for(int j=0;j<2;j++) {		
				int i=0;
				brailleLetterInvoer[i][j]=invoerLijn1.charAt(j+(letterPlaats*2));
				i++;
				brailleLetterInvoer[i][j]=invoerLijn2.charAt(j+(letterPlaats*2));
				i++;
				brailleLetterInvoer[i][j]=invoerLijn3.charAt(j+(letterPlaats*2));		
			}	
			brailleLetter outputBrailleLetter = new brailleLetter(brailleLetterInvoer);
			char outputLetter = outputBrailleLetter.getLetter();
			output = output + outputLetter;
		}
		outputArray[lijnNummer] = output;
	}
	
// output array uitprinten	
	for (int lijnNummer=0; lijnNummer<aantalLijnen; lijnNummer++) {
		System.out.println((lijnNummer+1) + " " + outputArray[lijnNummer]);
	}
	
	sc.close();
	}
}	