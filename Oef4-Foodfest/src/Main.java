import java.util.*;

class foodfest{
	private static ArrayList<int[]> foodtrucks = new ArrayList<int[]>();
	private static ArrayList<Integer> oplossingen = new ArrayList<Integer>();

	public static void voegPrijzenToe(int[] prijs) {
		foodtrucks.add(prijs);
	}
	public static void voegOplossingToe(int budget) {
		oplossingen.add(budget);
	}
	//zoeken van oplossingen met recursie
	public static boolean zoekOplossingen(int resterendBudget, int foodtruckNummer, int gerechtNummer) {
		if (foodtruckNummer==foodtrucks.size())return false;//stoppen als foodtrucknummer te groot wordt
		if (gerechtNummer==foodtrucks.get(foodtruckNummer).length) return false;//stoppen als gerechtnummer te groot wordt
		int gespendeerdBudget=foodtrucks.get(foodtruckNummer)[gerechtNummer];//gespendeerd budget bij deze stap

		if (gespendeerdBudget<resterendBudget) {
			if(zoekOplossingen(resterendBudget-gespendeerdBudget,foodtruckNummer+1,0)) return true;//zoeken van oplossingen via volgende foodtruck
			else if(zoekOplossingen(resterendBudget,foodtruckNummer, gerechtNummer+1)) return true;//zoeken van oplossingen via volgende gerecht
			else return false;//geen oplossingen gevonden
		}
		else if(gespendeerdBudget==resterendBudget && foodtruckNummer==foodtrucks.size()-1)return true;//oplossing gevonden
		else return false;//oplossing bevat niet alle trucks
	}	
	public static String naarString() {//afprinten van oplossing
		String oplossing = "";
		if (oplossingen.size()==0) oplossing = " GEEN";
		else {
			for(int i=0;i<oplossingen.size();i++) {
				oplossing = oplossing + " " + oplossingen.get(i).toString();
			}
		}
		return oplossing;
	}
	public static void clear() {
		oplossingen.clear();
		foodtrucks.clear();
	}
}

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		//inlezen van het aantal gerechten
		int aantalGevallen=Integer.parseInt(sc.nextLine());
		for(int gevalNummer=0;gevalNummer<aantalGevallen;gevalNummer++) {
			foodfest.clear();//gegevens wissen
			//inlezen van het aantal budgetten
			int aantalBudgetten=sc.nextInt();
			int[] budgetten=new int[aantalBudgetten];
			for(int budgetNummer=0;budgetNummer<aantalBudgetten;budgetNummer++) {
				budgetten[budgetNummer]=sc.nextInt();				
			}

			//inlezen van het aantal food trucks
			int aantalFoodtrucks=sc.nextInt();

			// voor iedere foodtruck aantal gerechten inlezen en ze toevoegen
			for(int foodtruckNummer=0;foodtruckNummer<aantalFoodtrucks;foodtruckNummer++) {
				int aantalGerechten=sc.nextInt();
				int[] prijzen = new int[aantalGerechten];
				for(int gerechtNummer=0;gerechtNummer<aantalGerechten;gerechtNummer++) {
					prijzen[gerechtNummer]=sc.nextInt();				
				}
				foodfest.voegPrijzenToe(prijzen);
			}

			//kloppende budgetten zoeken en opslaan
			//voor elk budget boom doorlopen, stoppen bij een juiste opl		
			for(int budgetNummer=0;budgetNummer<aantalBudgetten;budgetNummer++) {
				if(foodfest.zoekOplossingen(budgetten[budgetNummer], 0, 0))foodfest.voegOplossingToe(budgetten[budgetNummer]);
			}			
			System.out.println((gevalNummer+1) + foodfest.naarString());
		}		
		sc.close();
	}
}	
