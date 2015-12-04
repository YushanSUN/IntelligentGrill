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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
/*
 * The abstract class which allows us to create different distribution policy.
 */
public abstract class PolitiqueD {//politique de distribution
	List<Double> p = new ArrayList<Double>();
	abstract void distribuer(List<Double> p,List<Member> d);
	
}
