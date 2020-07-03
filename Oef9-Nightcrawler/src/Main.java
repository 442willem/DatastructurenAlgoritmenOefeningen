import java.util.*;
public class Main {
	static int[][] subsets;
	static int teller;
	public static int oneindig=9999999;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//inlezen van het aantal gevallen
		int aantalGevallen=sc.nextInt();
		for(int gevalNummer=0;gevalNummer<aantalGevallen;gevalNummer++) {
			int M=sc.nextInt();
			int aantalKruispunten=sc.nextInt();
			int[] kruispunten=new int[aantalKruispunten];
			for(int i=0;i<aantalKruispunten;i++)kruispunten[i]=i;
			int aantalStraten=sc.nextInt();
			int[][] cost=new int[aantalKruispunten][aantalKruispunten];
			for(int i=0;i<aantalKruispunten;i++) {
				cost[i][i]=0;
				for(int j=(i+1);j<aantalKruispunten;j++) {
					cost[i][j]=999999;
					cost[j][i]=999999;
				}
			}
			for(int k=0;k<aantalStraten;k++) {
				int i=sc.nextInt()-1;
				int j=sc.nextInt()-1;
				cost[i][j]=sc.nextInt();
				cost[j][i]=cost[i][j];
			}
			//twee lists, een voor de geldige sets, de andere voor de respectievelijke totale afstand
			ArrayList<int[]> mwachtpostselectie=new ArrayList<int[]>();
			ArrayList<Integer> totaleAfstandselectie=new ArrayList<Integer>(); 
			//mwachtpostselectie vullen, eerst met 1 kruispunt, dan 2,...
			int wachtpostAantal=0;
			boolean gevonden=false;		
			while(wachtpostAantal<aantalKruispunten&&!gevonden) {
				wachtpostAantal++;
				int aantalCombinaties=(int)((faculteit(aantalKruispunten))/(faculteit(wachtpostAantal)*faculteit(aantalKruispunten-wachtpostAantal)));
				subsets=new int[aantalCombinaties][wachtpostAantal];
				teller=0;
				int[] subset=new int[wachtpostAantal];
				vulSubset(kruispunten,kruispunten.length,wachtpostAantal,0,subset,0);
				//voor iedere mogelijke subset->bepalen maximale afstand en totale afstand
				for(int i=0;i<subsets.length;i++) {
					int[] minimaleAfstand=new int[aantalKruispunten];
					for(int j=0;j<aantalKruispunten;j++)minimaleAfstand[j]=oneindig;
					//per wachtpost afstand naar alle andere punten berekenen->dijkstra
					for(int wachtpostNummer=0;wachtpostNummer<subsets[i].length;wachtpostNummer++) {
						int huidigePost=subsets[i][wachtpostNummer];
						minimaleAfstand[huidigePost]=0;
						Set<Integer> q = new HashSet<Integer>(); 
						int[] afstand=new int[aantalKruispunten];
						int[] prev=new int[aantalKruispunten];
						for(int index=0;index<aantalKruispunten;index++) {            
							afstand[index]=oneindig;                  
							prev[index]=-1;                 
							q.add(index);  
						}
						afstand[huidigePost]=0;

						while(!q.isEmpty()) {
							int u=kies(afstand,aantalKruispunten,q);
							q.remove(u);					
							for(int w=0;w<aantalKruispunten;w++) {
								int kost=cost[u][w];
								int alt=afstand[u]+kost;
								if(alt<afstand[w]) {						
									afstand[w]=alt;
									prev[w]=u;
								}
							}
						}
						//minimaleAfstand vullen aan de hand van huidige wachtpost
						for(int index=0;index<aantalKruispunten;index++) {
							if(afstand[index]<minimaleAfstand[index])minimaleAfstand[index]=afstand[index];
						}
					}
					//minimaleAfstand bevat de afstand van elk punt naar de dichtsbijzijnde wachtpost
					//checken of afstand telkens kleiner is dan M
					boolean kleinerDanM=true;
					for(int j=0;j<aantalKruispunten;j++) {
						if(minimaleAfstand[j]>M)kleinerDanM=false;
					}
					//indien dit het geval is->in mwachtpostselectie, totale afstand in totaleAfstandselectie
					if(kleinerDanM) {
						mwachtpostselectie.add(subsets[i]);
						int totaleAfstand=0;
						for(int j=0;j<aantalKruispunten;j++)totaleAfstand+=minimaleAfstand[j];
						totaleAfstandselectie.add(totaleAfstand);
					}
				}
				if(mwachtpostselectie.size()!=0)gevonden=true;
			}
			//er is minstens 1 geldige selectie gevonden->optimale bepalen
			ArrayList<int[]> optimaleMWachtpostSelectie=new ArrayList<int[]>();
			int minimumTotaleAfstand=oneindig;	
			for(int i=0;i<mwachtpostselectie.size();i++) {
				if(totaleAfstandselectie.get(i)<minimumTotaleAfstand) {
					minimumTotaleAfstand=totaleAfstandselectie.get(i);
				}
			}
			//alle optimale toevoegen
			for(int i=0;i<mwachtpostselectie.size();i++) {
				if(totaleAfstandselectie.get(i)==minimumTotaleAfstand)optimaleMWachtpostSelectie.add(mwachtpostselectie.get(i));
			}
			ArrayList<String> uitvoerStrings=new ArrayList<String>();
			for(int i=0;i<optimaleMWachtpostSelectie.size();i++) {
				StringBuilder sb=new StringBuilder();
				sb.append((gevalNummer+1)+" ");
				for(int j=0;j<optimaleMWachtpostSelectie.get(i).length;j++) {
					sb.append(optimaleMWachtpostSelectie.get(i)[j]+1);
					if(j<optimaleMWachtpostSelectie.get(i).length-1)sb.append(" ");
				}
				uitvoerStrings.add(sb.toString());
			}
			//sorteren van uitvoer
			Collections.sort(uitvoerStrings);
			//			System.out.println("tot hier "+uitvoerStrings.size());
			//uitvoer
			for(int i=0;i<uitvoerStrings.size();i++)System.out.println(uitvoerStrings.get(i));
		}
		sc.close();
	}
	public static int kies(int[] d,int n,Set<Integer> q) {
		int minimum=oneindig;
		int w=0;
		Iterator<Integer> itr = q.iterator();

		while(itr.hasNext()){
			int i=itr.next();
			if(d[i]<=minimum) {
				minimum=d[i];
				w=i;
			}
		}
		return w;
	}
	public static void vulSubset(int arr[], int n, int r,int index, int[] subset, int i)	{ //recursie om de set te vullen	
		if (index == r) { 
			for(int k=0;k<subset.length;k++) {
				subsets[teller][k]=subset[k];
			}
			teller++;
			return; 
		} 
		if (i >= n)return; 
		subset[index] = arr[i]; 
		vulSubset(arr,n,r,(index+1),subset,(i+1)); 
		vulSubset(arr,n,r,index, subset,(i+1)); 
	}
	public static long faculteit(int n) {
		long fac=1;
		for (int i=2; i<=n; i++)fac*=i;
		return fac;
	}
	public static boolean allesTrue(boolean[] array) {
		for(int i=0;i<array.length;i++) if(!array[i]) return false;
		return true;
	}
}
