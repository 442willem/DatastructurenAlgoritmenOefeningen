import java.util.ArrayList;
import java.util.Scanner;

class Subsets{//klasse voor het gebruik van subsets
	int aantalElementen;//aantal elementen in de subset
	int[] hoofdset;
	ArrayList<Integer> subsetSom = new ArrayList<Integer>();//list van alle mogelijke voorkomende sommen binnen de set

	public Subsets(int aantalE, int[] hoofd) {
		aantalElementen=aantalE;
		hoofdset=hoofd;
		int[] subset=new int[aantalElementen];
		vulSubset(0,subset,0);
		verwijderDuplicates();
	}
	public void vulSubset(int index, int[] subset, int i)	{ //recursie om de set te vullen
		if (index == aantalElementen) { 
			int som=0;
			for (int j = 0; j < aantalElementen; j++)som=som+subset[j];
			subsetSom.add(som);
			return; 
		} 
		if (i >= hoofdset.length)return; 
		subset[index] = hoofdset[i]; 
		vulSubset(index + 1,subset, i + 1); 
		vulSubset(index, subset, i + 1); 
	} 
	public void verwijderDuplicates() {//verwijderen van dubbels om minder te moeten testen in hoofdprogramma(vb. invoer 2)
		ArrayList<Integer> tijdelijkeSet = new ArrayList<Integer>();
		for(int i=0;i<subsetSom.size();i++) {
			if(!tijdelijkeSet.contains(subsetSom.get(i)))tijdelijkeSet.add(subsetSom.get(i));
		}
		subsetSom=tijdelijkeSet;
	}
}
class Dwerg{
	private static ArrayList<int[]> dwergen = new ArrayList<int[]>();
	private static ArrayList<Subsets> subsets = new ArrayList<Subsets>();
	public static int oplossing;

	public static void reset() {
		dwergen.clear();
	}
	public static void voegToe(int[] a) {
		dwergen.add(a);
	}
	public static int getOplossing() {
		return oplossing;
	}
	public static void setOplossing(int a) {
		oplossing=a;
	}
	public static void vindOplossing(int teBetalen) {
		boolean oplossingGevonden=false;
		int aantalDiamanten=1;
		while(!oplossingGevonden&&aantalDiamanten<=dwergen.get(0).length) {//zoeken van een oplossing per aantal diamanten
			subsets.clear();
			for(int dwergNummer=0;dwergNummer<dwergen.size();dwergNummer++) {//vullen van de list subsets
				subsets.add(new Subsets(aantalDiamanten,dwergen.get(dwergNummer)));
			}
			oplossingGevonden=oplossingRecursie(aantalDiamanten,teBetalen,0,0);//recursie om een oplossing te vinden
			if(!oplossingGevonden)aantalDiamanten++;//geen oplossing bij dit aantal diamanten
			else oplossing=aantalDiamanten;//oplossing gevonden
		}
	}

	public static boolean oplossingRecursie(int aantalDiamanten, int resterendBedrag, int dwergNummer, int subsetNummer) {
		if(dwergNummer==dwergen.size())return false;
		if(subsetNummer==subsets.get(dwergNummer).subsetSom.size())return false;
		//betaaldBedrag is de som van de huidige subset van de huidige dwerg->gebruik list subsetSom
		int betaaldBedrag=subsets.get(dwergNummer).subsetSom.get(subsetNummer);
		
		if (betaaldBedrag>resterendBedrag)if(oplossingRecursie(aantalDiamanten,resterendBedrag,dwergNummer,subsetNummer+1))return true;//bedrag is te groot->volgende subset
		if (betaaldBedrag<resterendBedrag) {
			if(oplossingRecursie(aantalDiamanten,resterendBedrag-betaaldBedrag,dwergNummer+1,0)) return true;//zoeken van oplossingen via volgende dwerg
			else if(oplossingRecursie(aantalDiamanten,resterendBedrag, dwergNummer, subsetNummer+1)) return true;//zoeken van oplossingen via volgende subset
			else return false;//geen oplossingen gevonden
		}
		else if (betaaldBedrag==resterendBedrag&&dwergNummer==dwergen.size()-1)return true;//oplossing gevonden
		else return false;
	}
}

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//inlezen van het aantal gevallen
		int aantalGevallen=sc.nextInt();
		for(int gevalNummer=0;gevalNummer<aantalGevallen;gevalNummer++) {
			int teBetalen=sc.nextInt();
			int aantalDwergen=sc.nextInt();
			Dwerg.reset();//alles clearen
			for(int dwergNummer=0;dwergNummer<aantalDwergen;dwergNummer++) {
				int aantalDiamanten=sc.nextInt();
				int[] diamanten=new int[aantalDiamanten];
				for(int diamantNummer=0;diamantNummer<aantalDiamanten;diamantNummer++) {
					diamanten[diamantNummer]=sc.nextInt();
				}
				Dwerg.voegToe(diamanten);//info toevoegen
			}
			Dwerg.setOplossing(-1);
			Dwerg.vindOplossing(teBetalen);//oplossing vinden en uitprinten
			if(Dwerg.getOplossing()!=-1)System.out.println((gevalNummer+1)+" "+Dwerg.getOplossing());
			else System.out.println((gevalNummer+1)+" ONMOGELIJK");
		}
		sc.close();
	}
}
