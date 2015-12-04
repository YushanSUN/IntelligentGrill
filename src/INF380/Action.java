package INF380;
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author Yushan SUN
 *
 * 
 */
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Action {
	
	/*
	 * After creating a district list, use this function to collect the electricity
	 * between or within districts
	 */
	
	public static void collect(District dis){
			
		for(int i=0;i<dis.getMember().get(0).getP().size();i++){
			dis.getPT().add(i, 0.0);
		}
		for(int i=0;i<dis.getMember().size();i++){
			Member h =  dis.getMember().get(i);
			int j=1;
			List<Double> stock = dis.getMember().get(i).getStockplus();
			
			dis.addPT(stock);
		}
	}
	/*
	 * After collecting a district list, use this function to distribute electricity
	 * within districts, and then print the results in the console
	 */
	
	public static void distribution(District dis){
		DecimalFormat df = new DecimalFormat("0.00");
		PolitiqueD1 d1 = new PolitiqueD1();
		List<Member> home = new ArrayList<Member>();
		for(int i=0;i<dis.getMember().size();i++){
			home.add(dis.getMember().get(i));
		}
		d1.distribuer(dis.getPT(),home);
		System.out.println("***************************************************************************");
		
		for(int j=0;j<dis.getMember().get(0).getP().size();j++){
			int num = 1;
			System.out.println("The "+" period"+(j+1));
			for(Member h:dis.getMember()){
			
				
				System.out.println("home "+num+"");
				System.out.println("P:" + df.format(h.getP().get(j))
						+"   C:" + df.format(h.getC().get(j))
						+"   CC:" + df.format(h.getCC().get(j))
						+"   CO:" + df.format(h.getCO().get(j))
						+"   AP:" + df.format(h.getAP().get(j))
						+"   APC:" + df.format(h.getAPC().get(j))
						+"   APO:" + df.format(h.getAPO().get(j)));
				
				num++;
			}
		}
		for(int j=0;j<dis.getMember().get(0).getP().size();j++){
			for(int i = 0; i<dis.getMember().size();i++){
				dis.getMember().get(i).setP_app_dif(j,dis.getMember().get(i).getP().get(j)-dis.getMember().get(i).getAP().get(j));
			}
			
		}
		System.out.println("***************************************************************************");
		System.out.println("");
		System.out.println("");
				
	}
	/*
	 * After collecting a district list, use this function to distribute electricity
	 * within districts, and then print the results in the console
	 */
	
	public static void distributionTest(District dis){
		DecimalFormat df = new DecimalFormat("0.00");
		PolitiqueD1 d1 = new PolitiqueD1();
		List<Member> home = new ArrayList<Member>();
		for(int i=0;i<dis.getMember().size();i++){
			home.add(dis.getMember().get(i));
		}
		d1.distribuer(dis.getPT(),home);
		for(int j=0;j<dis.getMember().get(0).getP().size();j++){
			for(int i = 0; i<dis.getMember().size();i++){
				dis.getMember().get(i).setP_app_dif(j,dis.getMember().get(i).getP().get(j)-dis.getMember().get(i).getAP().get(j));
			}
			
		}
		System.out.println("Test Distribute finish!");
	}
	
	
	/*
	 * After collecting a city list, use this function to distribute electricity
	 * between districts, and then print the results in the console
	 */
	
	
	public static void distribution2(District dis){
		DecimalFormat df = new DecimalFormat("0.00");
		PolitiqueD1 d1 = new PolitiqueD1();
		List<Member> home = new ArrayList<Member>();
		for(int i=0;i<dis.getMember().size();i++){
			home.add(dis.getMember().get(i));
		}
		d1.distribuer(dis.getPT(),home);
		
		
		System.out.println("***************************************************************************");
		for(int j=0;j<dis.getMember().get(0).getP().size();j++){
			int num = 1;
			System.out.println("The "+" period"+(j+1));
			for(Member h:dis.getMember()){
				System.out.println("District "+num+"");
				System.out.println("P:" + df.format(h.getP().get(j))
						+"   C:" + df.format(h.getC().get(j))
						+"   CC:" + df.format(h.getCC().get(j))
						+"   CO:" + df.format(h.getCO().get(j))
						+"   AP:" + df.format(h.getAP().get(j))
						+"   APC:" + df.format(h.getAPC().get(j))
						+"   APO:" + df.format(h.getAPO().get(j)));
				
				num++;
			}
		}
		for(int j=0;j<dis.getMember().get(0).getP().size();j++){
			for(int i = 0; i<dis.getMember().size();i++){
				dis.getMember().get(i).setP_app_dif(j,dis.getMember().get(i).getP().get(j)-dis.getMember().get(i).getAP().get(j));
			}
			
		}

		for(int i=0;i<dis.getMember().size();i++){
			if(dis.getMember().get(i).flag1==1){
				for(int j=0;j<dis.getMember().get(i).getP().size();j++){
					Member m = dis.getMember().get(i);
					int total = 0;
					for(int k = 0; k < m.getMember().size();k++){
						total += m.getMember().get(k).getAP().get(j);
					}
					if(m.getAP().get(j) >= total){
						m.setPT(j, m.getAP().get(j) - total);
					}
					else{
						m.setPT(j, 0.);
					}
				}
			}
			else if(dis.getMember().get(i).flag1==2){
				for(int j=0;j<dis.getMember().get(i).getP().size();j++){
					dis.getMember().get(i).setPT(j, dis.getMember().get(i).getAP().get(j));
				}
			};
		}
		System.out.println("***************************************************************************");
		System.out.println("");
		System.out.println("");
		
	}
	

	/*
	 * Build a district from a file directory in which there are several homes(.csv files)
	 * Input is the directory name
	 * Output is a District Class
	 */
	static District BuildDistrict(String filename) throws Exception{
		District dis = new District();
		File f = new File(filename);
		if(!f.exists()){
			System.out.println("file doesn't exist !");
			System.exit(-1);
		}
		dis = Initialize.initial_district(f);	
		return dis;
	}
	
	/*
	 * Build a district from some lists given (lists of production,consummation)
	 * Input are 3 lists: production, critical consummation, optional consummation
	 * the element in each list is another list corresponding to the list of a home.
	 * e.g. the first element in attribute "production" is the list of production of the first home in this district
	 * Output is a District Class
	 */
	static District BuildDistrict(List<List<Double>> production, List<List<Double>> con_cri, List<List<Double>> con_opt) throws IOException{
		
		int home_num = production.size();
		District dis = new District();
		for(int i=0;i<home_num;i++){
			Home h = new Home(production.get(i),con_cri.get(i),con_opt.get(i));
			dis.addMember(h);
		}
		return dis;
		
	}
	
}
