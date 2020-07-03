package auto;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

class zoekCriteria {
	private final Map<String, Predicate<Auto>> map=new HashMap<>();
	
    public zoekCriteria() {
    	Predicate<Auto> alleAutos=p->p.getAlle().equals("alle");
    	map.put("alleAutos", alleAutos);//om tussen 0 en 3 criteria in te kunnen geven
    	
    	Predicate<Auto> prijsCat4=p->p.getPrijs().equals("vhigh");
    	map.put("aankoopprijs=vhigh", prijsCat4);
    	Predicate<Auto> prijsCat3=p->p.getPrijs().equals("high");
    	map.put("aankoopprijs=high", prijsCat3);
    	Predicate<Auto> prijsCat2=p->p.getPrijs().equals("med");
    	map.put("aankoopprijs=med", prijsCat2);
    	Predicate<Auto> prijsCat1=p->p.getPrijs().equals("low");
    	map.put("aankoopprijs=low", prijsCat1);
    	
    	Predicate<Auto> onderhoudCat4=p->p.getOnderhoud().equals("vhigh");
    	map.put("onderhoudsPrijs=vhigh", onderhoudCat4);
    	Predicate<Auto> onderhoudCat3=p->p.getOnderhoud().equals("high");
    	map.put("onderhoudsPrijs=high", onderhoudCat3);
    	Predicate<Auto> onderhoudCat2=p->p.getOnderhoud().equals("med");
    	map.put("onderhoudsPrijs=med", onderhoudCat2);
    	Predicate<Auto> onderhoudCat1=p->p.getOnderhoud().equals("low");
    	map.put("onderhoudsPrijs=low", onderhoudCat1);
    	
    	Predicate<Auto> deuren2=p->p.getDeuren().equals("2");
    	map.put("aantalDeuren=2", deuren2);
    	Predicate<Auto> deuren3=p->p.getDeuren().equals("3");
    	map.put("aantalDeuren=3", deuren3);
    	Predicate<Auto> deuren4=p->p.getDeuren().equals("4");
    	map.put("aantalDeuren=4", deuren4);
    	Predicate<Auto> deuren5=p->p.getDeuren().equals("5more");
    	map.put("aantalDeuren=5more", deuren5);
    	
    	Predicate<Auto> personen2=p->p.getPersonen().equals("2");
    	map.put("aantalPersonen=2", personen2);
    	Predicate<Auto> personen4=p->p.getPersonen().equals("4");
    	map.put("aantalPersonen=4", personen4);
    	Predicate<Auto> personenMeer=p->p.getPersonen().equals("more");
    	map.put("aantalPersonen=more", personenMeer);
    	
    	Predicate<Auto> kofferCat3=p->p.getKoffer().equals("big");
    	map.put("kofferruimte=big", kofferCat3);
    	Predicate<Auto> kofferCat2=p->p.getKoffer().equals("med");
    	map.put("kofferruimte=med", kofferCat2);
    	Predicate<Auto> kofferCat1=p->p.getKoffer().equals("small");
    	map.put("kofferruimte=small", kofferCat1);
    	
    	Predicate<Auto> veiligCat3=p->p.getVeiligheid().equals("high");
    	map.put("veiligheid=high", veiligCat3);
    	Predicate<Auto> veiligCat2=p->p.getVeiligheid().equals("med");
    	map.put("veiligheid=med", veiligCat2);
    	Predicate<Auto> veiligCat1=p->p.getVeiligheid().equals("low");
    	map.put("veiligheid=low", veiligCat1);
    	
    	Predicate<Auto> klasseCat4=p->p.getKlasse().equals("vgood");
    	map.put("klasse=vgood", klasseCat4);
    	Predicate<Auto> klasseCat3=p->p.getKlasse().equals("good");
    	map.put("klasse=good", klasseCat3);
    	Predicate<Auto> klasseCat2=p->p.getKlasse().equals("acc");
    	map.put("klasse=acc", klasseCat2);
    	Predicate<Auto> klasseCat1=p->p.getKlasse().equals("unacc");
    	map.put("klasse=unacc", klasseCat1);
    }
 
    public Predicate<Auto> getCriteria(String PredicateNaam){
    	Predicate<Auto> res;
    	res=map.get(PredicateNaam);
    	if(res==null)System.out.println("Criteria "+PredicateNaam+" niet gevonden");
    	return res;
    }
}
class Auto{
	private String prijs;
	private String onderhoud;
	private String deuren;
	private String personen;
	private String koffer;
	private String veiligheid;
	private String klasse;	
	
	public Auto(String pr, String o, String d, String pe, String ko, String v, String kl) {
		prijs=pr;
		onderhoud=o;
		deuren=d;
		personen=pe;
		koffer=ko;
		veiligheid=v;
		klasse=kl;
	}
	public void print() {
		System.out.println("Auto: Aankoopprijs: "+prijs+", Onderhoudsprijs:"+onderhoud+", Aantal deuren:"+deuren+", Aantal personen:"+personen+", Koffer:"+koffer+", Veiligheid:"+veiligheid+", Klasse:"+klasse);
	}
	public String getAlle() {return "alle";}
	public String getPrijs() {return prijs;}
	public String getOnderhoud() {return onderhoud;}
	public String getDeuren() {return deuren;}
	public String getPersonen() {return personen;}
	public String getKoffer() {return koffer;}
	public String getVeiligheid() {return veiligheid;}
	public String getKlasse() {return klasse;}
} 
public class Main {
    public static void main(String[] args)throws Exception {
        Scanner sc= new Scanner(new File("autos.txt"));
        Scanner sc2= new Scanner(System.in);
        zoekCriteria criteria=new zoekCriteria();
        ArrayList<Auto> autos=new ArrayList<Auto>();
        while(sc.hasNext()) {
        	String[] auto=sc.nextLine().split(",");
        	autos.add(new Auto(auto[0],auto[1],auto[2],auto[3],auto[4],auto[5],auto[6]));
        }
        System.out.println("Beschikbare criteria:");
        
        System.out.println("aankoopprijs=vhigh,aankoopprijs=high,aankoopprijs=med,aankoopprijs=low");
        System.out.println("onderhoudsprijs=vhigh,onderhoudsprijs=high,onderhoudsprijs=med,onderhoudsprijs=low");
        System.out.println("aantalDeuren=2,aantalDeuren=3,aantalDeuren=4,aantalDeuren=5more");
        System.out.println("aantalPersonen=2,aantalPersonen=4,aantalPersonen=more");
        System.out.println("kofferruimte=big,kofferruimte=med,kofferruimte=small");
        System.out.println("veiligheid=low,veiligheid=med,veiligheid=high");
        System.out.println("klasse=vgood,klasse=good,klasse=acc,klasse=unacc");
        System.out.println();
        System.out.println("Geef gewenste criteria in(max 3), gescheiden door een komma:");
        
        String[] crit= {"alleAutos","alleAutos","alleAutos"};
        String[] critIngelezen=sc2.nextLine().split(",");
        for(int i=0;i<critIngelezen.length;i++)crit[i]=critIngelezen[i];
        
        autos.stream().filter(criteria.getCriteria(crit[0])).filter(criteria.getCriteria(crit[1])).filter(criteria.getCriteria(crit[2])).forEach(Auto::print);    
        
        sc.close();
        sc2.close();
    }
}
