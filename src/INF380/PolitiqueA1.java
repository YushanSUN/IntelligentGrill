package INF380;
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author Yushan SUN
 *
 * 
 */
import java.util.ArrayList;
import java.util.List;

public class PolitiqueA1 extends PolitiqueA{
	
	public PolitiqueA1(){
		
	}

	/*for the politique 1, the users will first use the energy as they want, and then 
	 *give the energy which they don't need to the society
	 */
	@Override
	public List<Double> getP(List<Double> p, List<Double> c) {
		// TODO Auto-generated method stub
		double stockplus = 0;
		List<Double> stock = new ArrayList<Double>();
		for(int i=0;i<p.size();i++){
			if(p.get(i)>c.get(i)){
				stockplus = p.get(i)-c.get(i);
			}
			else if(p.get(i)<=c.get(i)){
				stockplus = 0;
			}
			stock.add(stockplus);
		}
		
		
		return stock;
	}


	

	/*after we collect the energy, we should set the origin appropiation energy as they have/need
	 * selon the different politique*/
	@Override
	public List<Double> setAP(List<Double> p, List<Double> c) {
		List<Double> apt = new ArrayList<Double>();
		double apti  = 0;
		for(int i=0;i<p.size();i++){
			if(p.get(i)>=c.get(i)){
				apti = c.get(i);
			}
			else if(p.get(i)<c.get(i)){
				apti = p.get(i);
			}
			apt.add(i,apti);
		}
		return apt;
	}

}