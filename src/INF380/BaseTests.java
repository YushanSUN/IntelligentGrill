package INF380;
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author Yushan SUN
 *
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
	/*
	 * This is the basic test which will ensure that the program runs well.
	 */

public class BaseTests extends TestCase {
	protected List<Double> list_p = new ArrayList<Double>(); 
	protected List<Double> list_cc = new ArrayList<Double>();
	protected List<Double> list_co = new ArrayList<Double>();
	protected District dis;
	protected List<District> dis_list = new ArrayList<District>();

	protected int period_num = 2;
	protected int cas_num = 1;

	
	public BaseTests() throws Exception {
		super();
		for(int i=0;i<period_num;i++){
			list_cc.add(5.0);
		}

		for(int i=0;i<period_num;i++){
			list_co.add(5.0);
		}
		for(int i=0;i<period_num;i++){
			list_p.add(6.0);
		}
		Home h1 = new Home(list_p,list_cc,list_co);
		list_p.clear();
		for(int i=0;i<period_num;i++){
			list_p.add(4.0);
		}
		Home h2 = new Home(list_p,list_cc,list_co);
		
		dis = new District();
		dis.addMember(h1);
		dis.addMember(h2);
		Action.collect(dis);
		Action.distributionTest(dis);
		dis.set_lists();
		Satisfaction.cal_dis_sat(dis);
	}
		
	public void testDisCreate() {
		assertNotNull(dis);
	}
}
