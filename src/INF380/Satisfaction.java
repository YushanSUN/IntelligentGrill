package INF380;
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author DENG Xiao 
 *
 * 
 */

import java.text.DecimalFormat;
import java.util.List;

public class Satisfaction {

	public Satisfaction(){}


	/* calculate the Gini index of a series of numbers, stored in a list as input
	 * return the result 
	 */
	public static double calcul_gini(List<Double> list){

		double sum = 0;
		double mean = 0;

		mean = calcul_mean(list);

		//gini equation
		for(double k : list){
			for(double j : list){
				sum += Math.abs(k-j);	
			}
		}

		double temp = 0;
		if(mean != 0)  temp = 0.5*(1/mean)*(1/(Math.pow(list.size(), 2)))*sum;

		DecimalFormat df = new DecimalFormat("0.00000");

		temp = Double.parseDouble(df.format(temp));		

		return  Math.abs(temp);
	}

	
	/*
	 * calculate the mean of a series of numbers,
	 * return the result
	 */
	public static double calcul_mean(List<Double> list){

		double sum = 0;
		for(Double i : list){
			sum = sum + i;
		}
		DecimalFormat df = new DecimalFormat("0.00000");

		double temp = sum/list.size();
		temp = Double.parseDouble(df.format(temp));

		return temp;
	}

	/*
	 * execute the function of calculating the satisfaction of a district and its homes,
	 * print the results to the console
	 * Input: District Class
	 * Output: void
	 */
	public static void cal_dis_sat(District dis){
		DecimalFormat df5 = new DecimalFormat("0.00000");

		int homeid = 1;
		for(Member h:dis.getMember()){

			//input: x,y,a,b,c,d(see the document). these 5 coefficients are all < 1 and >0 
			h.stf_st(0.8, 0.2, 0.05 , 0.05 , 0.1 ,0.05);

			System.out.println("home "+homeid);
			int hp = 1;
			for(double j : h.getStf_list()){
				System.out.println("period "+hp+"       "+df5.format(j));
				hp++;
			}
			h.stf_lt();
			homeid++;

			System.out.println("home long term satisfaction : mean = "+h.getStf_lt_m());
			System.out.println("home long term satisfaction : gini = "+h.getStf_lt_v());
			System.out.println("");

		}


		dis.stf_st();
		dis.stf_lt();

		System.out.println("********************************************");
		System.out.println("District satisfaction : ");
		System.out.println("short term (mean) : ");
		for (double d : dis.getStf_list()){
			System.out.print(d+"\t");		
		}
		System.out.println("");
		System.out.println("short term (gini) : ");
		for (double d : dis.getStf_v_list()){
			System.out.print(d + "\t");		
		}
		System.out.println("");
		System.out.println("long term : ");
		System.out.println("gini = "+dis.getStf_v_lt());
		System.out.println("mean = "+dis.getStf_m_lt());
		System.out.println("********************************************");
	}

	/*
	 * execute the function of calculating the satisfaction of a city,
	 * print the results to the console
	 * Input: District Class (a city)
	 * Output: void
	 */
	public static void cal_city_sat(District city){
		city.stf_st();
		city.stf_lt();
		System.out.println("---------------------------------------------");
		System.out.println("City satisfaction : gini = "+city.getStf_v_lt());
		System.out.println("City satisfaction : mean = "+city.getStf_m_lt());
	}
}
