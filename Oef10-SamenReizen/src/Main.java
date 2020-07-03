import java.util.*;

public class Main {
	public static int aantalOvernachtingen;
	public static boolean[][] edges;	
	public static int[] alicesteden;
	public static Set<Integer> stadia = new HashSet<Integer>();
	public static int maxGelijkeSteden;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int aantalGevallen=sc.nextInt();
		for(int gevalNummer=0;gevalNummer<aantalGevallen;gevalNummer++) {
			int aantalSteden=sc.nextInt();
			int startplaats=sc.nextInt()-1;
			int eindplaats=sc.nextInt()-1;
			aantalOvernachtingen=sc.nextInt();
			alicesteden=new int[aantalOvernachtingen+2];
			alicesteden[0]=startplaats;
			for(int i=1;i<aantalOvernachtingen+1;i++)alicesteden[i]=sc.nextInt()-1;
			alicesteden[aantalOvernachtingen+1]=eindplaats;
			int aantalVoetbalstadia=sc.nextInt();
			int[] voetbalsteden=new int[aantalVoetbalstadia];
			for(int i=0;i<aantalVoetbalstadia;i++)voetbalsteden[i]=sc.nextInt()-1;
			for(int i=0;i<aantalVoetbalstadia;i++)stadia.add(voetbalsteden[i]);
			int L=sc.nextInt();
			edges=new boolean[aantalSteden][aantalSteden];
			for(int i=0;i<aantalSteden;i++)edges[i][i]=false;			
			for(int i=0;i<L;i++) {
				int j=sc.nextInt()-1;
				int k=sc.nextInt()-1;
				edges[j][k]=true;
				edges[k][j]=true;
			}
			//weg die bob aflegt
			int[] bobspad=new int[aantalOvernachtingen+2];
			//alle mogelijke wegen die Bob kan afleggen, te beginnen in start, langs alle stadia en einde in eind genereren			 
			maxGelijkeSteden=-1;
			vindBobsWegen(startplaats, eindplaats, 0, bobspad);	
			//uitprinten
			if(maxGelijkeSteden!=-1)System.out.println((gevalNummer+1)+" "+maxGelijkeSteden);
			else System.out.println((gevalNummer+1)+" onmogelijk");
			stadia.clear();

		}
		sc.close();
	}
	public static void vindBobsWegen(int start, int eind, int huidigeDag, int[] pad) {		
		if(huidigeDag>=pad.length) {//einde van de reis
			if(pad[pad.length-1]==eind) {//op eindbestemming geraakt
				Set<Integer> hulp = new HashSet<Integer>();
				hulp.addAll(stadia);
				for(int j=0;j<pad.length;j++)hulp.remove(pad[j]);	

				if(hulp.size()==0) {//alle stadia bezocht
					int aantalGelijkeSteden=controleerArrays(alicesteden,pad)-2;
					if(aantalGelijkeSteden>maxGelijkeSteden)maxGelijkeSteden=aantalGelijkeSteden;	
				}
			}
			return;
		}
		pad[huidigeDag]=start;

		for(int i=0;i<edges.length;i++) {
			if(edges[pad[huidigeDag]][i]==true) {
				vindBobsWegen(i,eind,huidigeDag+1,pad);
			}
		}
	}
	public static int controleerArrays(int[] a, int[] b) {//functie ter controle arrays
		int gelijk=0;		
		if(a.length==b.length) {
			for(int i=0;i<a.length;i++)if(a[i]==b[i])gelijk++;
		}else return -1;		
		return gelijk;
	}
}
