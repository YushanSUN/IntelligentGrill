package INF380;
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author Jia MA
 *
 * 
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class District extends Member {

	//short term satisfactions of the district
	
	// list: ist element is the mean of satisfactions of all the homes in ist period, defined in Class Member 
	//private List<Double> stf_list = new ArrayList<Double>();  
    // Idem, but this is variance  
	private List<Double> stf_v_list = new ArrayList<Double>(); 
		

	public District() throws IOException{
		super();
	}

	/*
	 * Calculate the 3 list (p,cc,co) of a district by collecting data from its members(homes) 
	 */
	public void set_lists(){
	
		//number of periods
		int pe_num = this.getMember().get(0).getP().size();

		p.clear();
		c.clear();
		cc.clear();
		co.clear();
		ap.clear();
		apc.clear();
		apo.clear();
		p_app_dif.clear();
		
		
		for(int i = 0; i < pe_num; i++){
			
			double p_sum = 0 ,c_sum = 0,cc_sum = 0,co_sum = 0,ap_sum = 0,apc_sum = 0,apo_sum = 0,app_dif = 0;
			
			for(int j = 0; j< member.size(); j++){
				
				p_sum +=  member.get(j).getP().get(i);
				c_sum +=  member.get(j).getC().get(i);
				cc_sum += member.get(j).getCC().get(i);
				co_sum += member.get(j).getCO().get(i);
				ap_sum += member.get(j).getAP().get(i);
				apc_sum += member.get(j).getAPC().get(i);
				apo_sum += member.get(j).getAPO().get(i);
				app_dif +=  member.get(j).getP().get(i);
			}
			p.add(p_sum);
			c.add(c_sum);
			cc.add(cc_sum);
			co.add(co_sum);
			ap.add(ap_sum);
			apc.add(apc_sum);
			apo.add(apo_sum);
			p_app_dif.add(app_dif);
		}
	}
	
	/*
	 * 
	 * @see INF380.Member#stf_st()
	 */
	public void stf_st() {

		//list used to store all the homes' satisfactions in one period,size is equal to the number of homes
		List<Double> home_stf = new ArrayList<Double>();

		//number of periods
		int per_num=pt.size();

		
		for(int i=0;i<per_num;i++){
			home_stf.clear();
			
			//calculate the satisfaction of district in  period  i 
			for(Member h:member){
				home_stf.add(h.getStf_list().get(i));
			}
			
			stf_list.add(Satisfaction.calcul_mean(home_stf));
			stf_v_list.add(Satisfaction.calcul_gini(home_stf));
		}
	}

	/*
	 * 
	 * @see INF380.Member#stf_lt()
	 */
	public void stf_lt() {
		
		
		//size is the same as number of homes
		List<Double> stf_homes_mean = new ArrayList<Double>();
		
		for(Member h:member){
			stf_homes_mean.add(h.getStf_lt_m());
		}
		
		stf_lt_m = Satisfaction.calcul_mean(stf_homes_mean);
		stf_lt_v = Satisfaction.calcul_gini(stf_homes_mean);
	}

	public List<Double> getStf_v_list(){
		return stf_v_list;
	}

	public double getStf_m_lt(){
		return stf_lt_m;
	}
	
	public double getStf_v_lt(){
		return stf_lt_v;
	}
	
	public List<Double> getPT(){
		return pt;
	}
}
