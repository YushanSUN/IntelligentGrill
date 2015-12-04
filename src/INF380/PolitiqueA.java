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
/*
 * The abstract class which allows us to create different collect policy.
 */

public abstract class PolitiqueA { // politique de production
	
	abstract public List<Double> getP(List<Double> p, List<Double> c);
	abstract public List<Double> setAP(List<Double> p, List<Double> c);

}