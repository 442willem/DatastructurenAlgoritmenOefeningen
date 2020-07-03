import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static int oneindig=9999999;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//inlezen van het aantal gevallen
		int aantalGevallen=sc.nextInt();
		for(int gevalNummer=0;gevalNummer<aantalGevallen;gevalNummer++) {
			int rijen=sc.nextInt();
			int kolommen=sc.nextInt();
			int aantalVertices=(rijen*(kolommen+1))+(kolommen*(rijen+1));

			int z=kolommen+1;//aantal verticale edges per  rij
			ArrayList<int[]> cost=new ArrayList<int[]>();//slechts halve matrix opslaan
			ArrayList<ArrayList<Integer>> buren = new ArrayList<ArrayList<Integer>>();
			for(int i=0;i<aantalVertices;i++) {
				int[] rij=new int[i];
				for(int j=0;j<i;j++) {//j is altijd kleiner dan i
					rij[j]=oneindig;
				}
				cost.add(i, rij);
			}
			for(int i=0;i<rijen;i++) {
				for(int j=0;j<kolommen;j++) {
					int boven=(rijen*(kolommen+1))+((rijen+1)*j)+i;
					int onder=boven+1;							
					int links=((kolommen+1)*i)+j;
					int rechts=links+1;
					int waarde=sc.nextInt();//hulpwaardes voor het inlezen

					cost.get(rechts)[links]=waarde;//rechts altijd>links				
					cost.get(boven)[rechts]=waarde;//boven altijd>rechts				
					cost.get(boven)[links]=waarde;//boven altijd>links				
					cost.get(onder)[boven]=waarde;//onder altijd>boven
					cost.get(onder)[rechts]=waarde;//onder altijd>rechts
					cost.get(onder)[links]=waarde;//onder altijd>links	
				}
			}
			for(int i=0;i<aantalVertices;i++)buren.add(new ArrayList<Integer>());
			for(int i=0;i<aantalVertices;i++) {
				for(int j=0;j<i;j++) {
					if(cost.get(i)[j]!=oneindig) {
						buren.get(i).add(j);
						buren.get(j).add(i);
					}
				}
			}
			//array voor oplossing per startpositie
			int[][] oplossingen=new int[rijen][2];//per startpositie 2 kolommen, aantal mijnen en aantal zones
			for(int startpositie=0;startpositie<(rijen*z);startpositie+=z){
				//dijkstra om kortste weg naar iedere eindpos te bepalen
				int oplossing=oneindig;
				Set<Integer> q = new HashSet<Integer>(); 
				int[] dist=new int[aantalVertices];
				int[] prev=new int[aantalVertices];
				for(int i=0;i<aantalVertices;i++) {            
					dist[i]=oneindig;                  
					prev[i]=-1;                 
					q.add(i);  
				}
				dist[startpositie]=0;

				while(!q.isEmpty()) {
					int u=kies(dist,aantalVertices,q);
					q.remove(u);					
					for(int w=0;w<buren.get(u).size();w++) {
						int buur=buren.get(u).get(w);
						int kost;
						if(u>buur)kost=cost.get(u)[buur];
						else if(buur>u)kost=cost.get(buur)[u];
						else kost=0;

						int alt=dist[u]+kost;
						if(alt<dist[buur]) {						
							dist[buur]=alt;
							prev[buur]=u;
						}
					}
				}

				//optimale eindpositie voor deze startpositie
				ArrayList<Integer> eindposities=new ArrayList<Integer>();
				for(int i=(z-1);i<(rijen*z);i+=z) {
					if(dist[i]<oplossing) {
						eindposities.clear();
						oplossing=dist[i];
						eindposities.add(i);
					}
					else if(dist[i]==oplossing)eindposities.add(i);
				}
				//aantal mijnen en zones bepalen voor deze optimale eindpositie
				oplossingen[(startpositie/z)][0]=oplossing;//aantal mijnen	
				oplossingen[(startpositie/z)][1]=oneindig;
				for(int i=0;i<eindposities.size();i++) {
					int eindpositie=eindposities.get(i);
					int huidigAantalZones=0;
					while(eindpositie!=startpositie) {
						eindpositie=prev[eindpositie];
						huidigAantalZones++;//aantal zones
					}
					if(huidigAantalZones<oplossingen[(startpositie/z)][1])oplossingen[(startpositie/z)][1]=huidigAantalZones;
				}
			}
			//optimale startpositie/oplossing
			int minimumMijnen=oneindig;
			int minimumZones=oneindig;
			for(int i=0;i<oplossingen.length;i++) {
				if(oplossingen[i][0]<minimumMijnen) {//minder mijnen
					minimumZones=oplossingen[i][1];
					minimumMijnen=oplossingen[i][0];
				}
				else if(oplossingen[i][0]==minimumMijnen&&oplossingen[i][1]<minimumZones) {//minder zones
					minimumZones=oplossingen[i][1];
					minimumMijnen=oplossingen[i][0];
				}
			}
			//oplossing uitprinten
			System.out.println((gevalNummer+1)+" "+minimumZones+" "+minimumMijnen);
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
}