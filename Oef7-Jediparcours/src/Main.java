import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//inlezen van het aantal gevallen
		int aantalGevallen=sc.nextInt();
		for(int gevalNummer=0;gevalNummer<aantalGevallen;gevalNummer++) {
			int aantalVertices=sc.nextInt();
			int aantalEdges=sc.nextInt();
			//gebruik van BellmanFord algoritme
			int[][] edges=new int[aantalEdges][3];
			for(int i=0;i<aantalEdges;i++) {
				edges[i][0]=(sc.nextInt())-1;
				edges[i][1]=(sc.nextInt())-1;
				edges[i][2]=sc.nextInt();
			}
			//2 arrays voor oplossen
			int[] afstand=new int[aantalVertices];
			int[] voorganger=new int[aantalVertices];
			afstand[0]=0;
			for(int i=1;i<aantalVertices;i++) {
				afstand[i]=999999;
				voorganger[i]=-1;
			}
			//maximum aantal vertices -1 iteraties
			for(int i=0;i<aantalVertices;i++) {
				//alle edges overlopen
				//if v.d>u.d+w(u,v)->v.d=u.d+w(u,v)&v.p=u
				for(int edgeNummer=0;edgeNummer<aantalEdges;edgeNummer++) {
					if(afstand[edges[edgeNummer][1]]>(afstand[edges[edgeNummer][0]]+edges[edgeNummer][2])) {
						afstand[edges[edgeNummer][1]]=(afstand[edges[edgeNummer][0]]+edges[edgeNummer][2]);
						voorganger[edges[edgeNummer][1]]=edges[edgeNummer][0];
					}
				}
			}
			//laatste iteratie als check voor negatieve lus
			boolean negatieveLus=false;
			int[] afstandVooraf=new int[aantalVertices];
			for(int j=0;j<aantalVertices;j++)afstandVooraf[j]=afstand[j];
			for(int edgeNummer=0;edgeNummer<aantalEdges;edgeNummer++) {
				if(afstand[edges[edgeNummer][1]]>(afstand[edges[edgeNummer][0]]+edges[edgeNummer][2])) {
					afstand[edges[edgeNummer][1]]=(afstand[edges[edgeNummer][0]]+edges[edgeNummer][2]);
					voorganger[edges[edgeNummer][1]]=edges[edgeNummer][0];
				}
			}
			for(int i=0;i<aantalVertices;i++)if(afstand[i]!=afstandVooraf[i])negatieveLus=true;
			//oplossing uitprinten
			if(negatieveLus)System.out.println((gevalNummer+1)+" min oneindig");
			else if(afstand[aantalVertices-1]==999999)System.out.println((gevalNummer+1)+" plus oneindig");
			else System.out.println((gevalNummer+1)+" "+afstand[aantalVertices-1]);
		}
		sc.close();
	}

}
