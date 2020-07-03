import java.util.*;
class huis{
	public static huis[][] buurt;
	public static int[] startpositie;
	public static int aantalVissen;
	public static int gegetenVissen;
	public static ArrayList<Integer> oplossingen = new ArrayList<Integer>();
	public static int[] positie;

	private boolean links;
	private boolean rechts;
	private boolean boven;
	private boolean onder;
	private boolean eten;
	private boolean wasErEten;
	private boolean alGeweest;

	public huis(boolean l, boolean r, boolean b, boolean o, boolean e){
		links=l;
		rechts=r;
		boven=b;
		onder=o;
		eten=e;
		if(e)aantalVissen++;
		wasErEten=false;
		alGeweest=false;
	}
	public void reset() {
		if (this.wasErEten) {
			this.eten=true;
			this.wasErEten=false;
			gegetenVissen--;
		} 
	}
	public static void voegHuisToe(huis h, int rij, int kolom) {
		buurt[rij][kolom]=h;
	}
	public static int optimaleOplossing() {
		int optimaleOplossing=0;
		for(int i=0;i<oplossingen.size();i++) {
			if(oplossingen.get(i)>optimaleOplossing)optimaleOplossing=oplossingen.get(i);
		}
		return optimaleOplossing;
	}
	public static void clear() {
		oplossingen.clear();
	}
	public static void preCheck(int aantalMinuten) {
		if(aantalMinuten>((aantalVissen+1)*(buurt[0].length+buurt.length)))oplossingen.add(aantalVissen);
	}
	public static void zoekBesteOplossing(int aantalMinuten) {
		buurt[positie[0]][positie[1]].alGeweest=true;		
		if (buurt[positie[0]][positie[1]].eten){
			buurt[positie[0]][positie[1]].eten=false;
			buurt[positie[0]][positie[1]].wasErEten=true;
			gegetenVissen++;
			huis.zoekBesteOplossing(aantalMinuten-1);
		}
		else { 
			if (aantalMinuten>=0&&positie==startpositie&&!oplossingen.contains(gegetenVissen))oplossingen.add(gegetenVissen);	
			if (aantalMinuten==(Math.abs(positie[0]-startpositie[0])+Math.abs(positie[1]-startpositie[1]))&&!oplossingen.contains(gegetenVissen))oplossingen.add(gegetenVissen);
			if (aantalMinuten>(Math.abs(positie[0]-startpositie[0])+Math.abs(positie[1]-startpositie[1]))) {
				if (gegetenVissen==aantalVissen) {
					if(!oplossingen.contains(gegetenVissen))oplossingen.add(gegetenVissen);
				}
				else {
					if (buurt[positie[0]][positie[1]].boven && !buurt[positie[0]-1][positie[1]].alGeweest) {
						positie[0]--;
						huis.zoekBesteOplossing(aantalMinuten-1);
						buurt[positie[0]][positie[1]].reset();
						positie[0]++;
					}
					if (buurt[positie[0]][positie[1]].rechts && !buurt[positie[0]][positie[1]+1].alGeweest) {
						positie[1]++;
						huis.zoekBesteOplossing(aantalMinuten-1);
						buurt[positie[0]][positie[1]].reset();
						positie[1]--;
					}
					if (buurt[positie[0]][positie[1]].links && !buurt[positie[0]][positie[1]-1].alGeweest) {
						positie[1]--;
						huis.zoekBesteOplossing(aantalMinuten-1);
						buurt[positie[0]][positie[1]].reset();
						positie[1]++;
					}
					if (buurt[positie[0]][positie[1]].onder && !buurt[positie[0]+1][positie[1]].alGeweest) {
						positie[0]++;
						huis.zoekBesteOplossing(aantalMinuten-1);
						buurt[positie[0]][positie[1]].reset();
						positie[0]--;
					}

					if (buurt[positie[0]][positie[1]].boven) {
						positie[0]--;
						huis.zoekBesteOplossing(aantalMinuten-1);
						buurt[positie[0]][positie[1]].reset();
						positie[0]++;
					}
					if (buurt[positie[0]][positie[1]].rechts) {
						positie[1]++;
						huis.zoekBesteOplossing(aantalMinuten-1);
						buurt[positie[0]][positie[1]].reset();
						positie[1]--;
					}
					if (buurt[positie[0]][positie[1]].links) {
						positie[1]--;
						huis.zoekBesteOplossing(aantalMinuten-1);
						buurt[positie[0]][positie[1]].reset();
						positie[1]++;
					}
					if (buurt[positie[0]][positie[1]].onder) {
						positie[0]++;
						huis.zoekBesteOplossing(aantalMinuten-1);
						buurt[positie[0]][positie[1]].reset();
						positie[0]--;
					}
				}
			}
		}		
	}
}

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//inlezen van het aantal gevallen
		int aantalGevallen=Integer.parseInt(sc.nextLine());
		for(int gevalNummer=0;gevalNummer<aantalGevallen;gevalNummer++) {
			String invoer=sc.nextLine();
			String[] lijnArray = invoer.split(" ");
			int b=Integer.parseInt(lijnArray[0]);
			int h=Integer.parseInt(lijnArray[1]);
			int aantalMinuten=Integer.parseInt(lijnArray[2]);

			huis.startpositie=new int[2];
			huis.positie=new int[2];
			huis.buurt=new huis[h][b];
			huis.aantalVissen=0;
			huis.gegetenVissen=0;

			for(int lijnNummer=0; lijnNummer<h;lijnNummer++) {
				invoer=sc.nextLine();
				for(int kolomNummer=0; kolomNummer<b; kolomNummer++) {
					if(kolomNummer==0) {//tegen linkerwand
						if(lijnNummer==0) {//tegen bovenkant
							if(invoer.charAt(kolomNummer)=='E')huis.voegHuisToe(new huis(false, true, false, true, true), lijnNummer, kolomNummer);
							else huis.voegHuisToe(new huis(false, true, false, true, false), lijnNummer, kolomNummer);
						}
						else if(lijnNummer==h-1) {//tegen onderkant
							if(invoer.charAt(kolomNummer)=='E')huis.voegHuisToe(new huis(false, true, true, false, true), lijnNummer, kolomNummer);
							else huis.voegHuisToe(new huis(false, true, true, false, false), lijnNummer, kolomNummer);
						}
						else {//verticaal vrij
							if(invoer.charAt(kolomNummer)=='E')huis.voegHuisToe(new huis(false, true, true, true, true), lijnNummer, kolomNummer);
							else huis.voegHuisToe(new huis(false, true, true, true, false), lijnNummer, kolomNummer);
						}
					}
					else if(kolomNummer==b-1) {//tegen rechterwand
						if(lijnNummer==0) {//tegen bovenkant
							if(invoer.charAt(kolomNummer)=='E')huis.voegHuisToe(new huis(true, false, false, true, true), lijnNummer, kolomNummer);
							else huis.voegHuisToe(new huis(true, false, false, true, false), lijnNummer, kolomNummer);
						}
						else if(lijnNummer==h-1) {//tegen onderkant
							if(invoer.charAt(kolomNummer)=='E')huis.voegHuisToe(new huis(true, false, true, false, true), lijnNummer, kolomNummer);
							else huis.voegHuisToe(new huis(true, false, true, false, false), lijnNummer, kolomNummer);
						}
						else {//verticaal vrij
							if(invoer.charAt(kolomNummer)=='E')huis.voegHuisToe(new huis(true, false, true, true, true), lijnNummer, kolomNummer);
							else huis.voegHuisToe(new huis(true, false, true, true, false), lijnNummer, kolomNummer);
						}
					}
					else {//horizontaal vrij
						if(lijnNummer==0) {//tegen bovenkant
							if(invoer.charAt(kolomNummer)=='E')huis.voegHuisToe(new huis(true, true, false, true, true), lijnNummer, kolomNummer);
							else huis.voegHuisToe(new huis(true, true, false, true, false), lijnNummer, kolomNummer);
						}
						else if(lijnNummer==h-1) {//tegen onderkant
							if(invoer.charAt(kolomNummer)=='E')huis.voegHuisToe(new huis(true, true, true, false, true), lijnNummer, kolomNummer);
							else huis.voegHuisToe(new huis(true, true, true, false, false), lijnNummer, kolomNummer);
						}
						else {//verticaal vrij
							if(invoer.charAt(kolomNummer)=='E')huis.voegHuisToe(new huis(true, true, true, true, true), lijnNummer, kolomNummer);
							else huis.voegHuisToe(new huis(true, true, true, true, false), lijnNummer, kolomNummer);
						}
					}
					if(invoer.charAt(kolomNummer)=='G') {//startpositie Garfield
						huis.startpositie[0]=lijnNummer;
						huis.startpositie[1]=kolomNummer;
					}
				}
			}//einde inlezen

			huis.positie[0]=huis.startpositie[0];
			huis.positie[1]=huis.startpositie[1];
			huis.preCheck(aantalMinuten);
			if(huis.oplossingen.size()==0)huis.zoekBesteOplossing(aantalMinuten);
			System.out.println((gevalNummer+1)+" "+ huis.optimaleOplossing());
			huis.clear();
		}
		sc.close();
	}
}
