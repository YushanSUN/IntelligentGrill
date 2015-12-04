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

public class PolitiqueA2 extends PolitiqueA{
	
    public PolitiqueA2(){
		
	}
    
    /*for the politique 2, the users will first give all their energy to the society,
     * and then apply for the energy they want
	 */
	@Override
	public List<Double> getP(List<Double> p, List<Double> c) {
		List<Double> stock = new ArrayList<Double>();
		for(Double item : p){
			stock.add(item);
		}
		return stock;
	}

	
	/*after we collect the energy, we should set the origin appropiation energy as they have/need
	 * selon the different politique*/
	@Override
	public List<Double> setAP(List<Double> p, List<Double> c) {
		List<Double> apt = new ArrayList<Double>();
		for(int i=0;i<p.size();i++){
			apt.add(1.0);
		}
		for(int i=0;i<p.size();i++){
			apt.set(i,0.0);
		}
		
		return apt;
	}
	

}