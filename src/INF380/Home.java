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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Home extends Member {
	
	
	/*
	 * Create an instance Home by reading from files as input data
	 */
	public Home() throws IOException {
		super();
	
	}
	
	/*
	 * Create an instance Home by giving 3 lists as input data
	 */
	public Home(List<Double> p_l, List<Double> cc_l, List<Double> co_l) throws IOException {
		super();
		int period_num = p_l.size();
		
		this.setP(p_l);
		
		this.setCC(cc_l);
		this.setCO(co_l);
		
		List<Double> temp_list = new ArrayList<Double>();
		for(int i=0;i<period_num;i++){
			temp_list.add(0.0);
		}
		this.setAP(temp_list);
		this.setAPC(temp_list); 
		this.setAPO(temp_list);
		this.setP_app_dif(temp_list);
		temp_list.clear();
		for(int i = 0;i<period_num;i++){
			temp_list.add(cc_l.get(i)+co_l.get(i));
		}
		
		this.setC(temp_list);
	}

	/*
	 * 
	 * @see INF380.Member#stf_st(double, double, double, double, double, double)
	 */
	@Override
	public void stf_st(double x,double y,double a, double b,double c,double d) {

		double stf = 0;
		double m = 0;
		double k;

		//number of periods
		int per_num = this.p.size();

		for(int i = 0;i< per_num;i++){

			//get the data of this home in the ist period
			double pi = p.get(i);
			double api = ap.get(i);
			double apci =apc.get(i);
			double apoi =apo.get(i);
			double ci = this.c.get(i);
			double cci = cc.get(i);
			double coi = co.get(i);
			double p_appi = p_app_dif.get(i);


			if(cci==0 && coi!=0)  m = x + y*apoi/coi; 
			else if(cci!=0 && coi==0) m = y + x*apci/cci;
			else if(cci==0 && coi==0) m = 1;
			else m = x*(apci/cci) + y*(apoi/coi); 

			// all demand is satisfied 
			if(m==1){

				if(pi > api)  
					if(pi==0) k = 0;
					else	k = a*(p_appi)/pi;
				else 
					if(api==0) k = 0;
					else k = b*(p_appi)/api;
				stf = 1+k;
			}

			else{   //m<1

				if(api<pi)	{

					double give_sum = 0;
					double my_p_sum = 0;

					
					//////////// calculate the value of k ////////////////////////
					if(i==0) k = 0;

					else if(i==1) {
						give_sum += p_appi+ p_app_dif.get(0);
			
						// I give some electricity to others before and now
						if(give_sum > 0){
							my_p_sum += pi+p.get(0);
							
							if(my_p_sum==0) k = 0;
							else {
								if(give_sum == my_p_sum) k = -c*(1-1/my_p_sum);
								else k = -c*(give_sum/my_p_sum);
							}
						}
						// I receive some electricity from others before and now
						else k = 0;
					}
					
					else{

						//calculate sum(p_i-app_i) ,  including 2 periods before and this period
						give_sum += p_appi + p_app_dif.get(i-1) + p_app_dif.get(i-2);
						// I give some electricity to others before and now
						if(give_sum > 0){
							my_p_sum += pi + p.get(i-1) + p.get(i-2);
							if(my_p_sum==0) k = 0;
							else  {
								if(give_sum == my_p_sum) k = -c*(1-1/my_p_sum);
								else k = -c*(give_sum/my_p_sum);
							}
						}
						// I receive some electricity from others before and now
						else k = 0;
					}
					//////////// calculate the value of k ////////////////////////
					
					
					//////////// calculate the  final satisfaction /////////////// 

						double p_cc = 0;
						double p_co = 0;
											
						double n1 = 0;
						double n2 = 0;
						
						if(pi<cci) {
							p_cc = pi;
							p_co = 0;
						}
						
						else if(cci <= pi && pi < ci){
							p_cc = cci;
							p_co = pi - cci;
						}
						
						else{
							
							p_cc = cci;
							p_co = coi;
						}
						
						if(p_cc == 0 && p_co !=0){
							n1 = 1;
							n2 = apoi / p_co;
						}
						else if(p_cc != 0 && p_co == 0){
							n1 = apci / p_cc;
							n2 = 1;
						}
						else if(p_cc == 0 && p_co == 0){
							n1 = 1;
							n2 = 1;
						}
						else {
							n1 = apci / p_cc;
							n2 = apoi / p_co;
						}
						
						stf = m*(x*n1+y*n2)+k;					
						
					//////////// calculate the  final satisfaction /////////////// 
				}


				else  {
					double sumBefore=0;
					if(i==0) k=0;
					else if(i==1) {
						sumBefore=p_app_dif.get(0);
						
						if(sumBefore==0 || sumBefore < 0) k=0;
						else k=-d*(sumBefore-(api-pi))/sumBefore;
					}
				
					else{
						sumBefore=p_app_dif.get(i-1)+p_app_dif.get(i-2);

						if(sumBefore==0 || sumBefore < 0) k=0;
						else	k= -d*(sumBefore-(api-pi))/sumBefore;				
					}

					stf=m+k;             
				}				
				
			}
			this.stf_list.add(stf);

		}
	}

	/*
	 * 
	 * @see INF380.Member#stf_lt()
	 */
    public void stf_lt() {

		// the mean 
		stf_lt_m=Satisfaction.calcul_mean(stf_list);

		// the variance (Gini index) 
		stf_lt_v=Satisfaction.calcul_gini(stf_list);

	
	}

}
