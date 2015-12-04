package INF380;
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author Wenjia WEI
 *
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Writecsv {
	//delete the subfiles of one folder
	static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// when the catalogue is empty,it can delete
		return dir.delete();
	}


	public static void CsvCaseWriter(District dis,int case_num,int k) { 

		List<Double> p = new ArrayList<Double>(); // list of the production of this district
		List<Double> c = new ArrayList<Double>(); // list of the consummation of this district
		List<Double> cc = new ArrayList<Double>(); // list of the critical consummation of this district
		List<Double> co = new ArrayList<Double>(); // list of the optional consummation of this district
		List<Double> ap = new ArrayList<Double>(); // list of the appropriation of this district
		List<Double> apc = new ArrayList<Double>(); // list of the critical appropriation of this district
		List<Double> apo = new ArrayList<Double>(); // list of the optional appropriation of this district
		List<Double> stf = new ArrayList<Double>(); // list of the satisfaction of this district

		int home_num = dis.getMember().size();
		for(Member m : dis.getMember()){
			p.addAll(m.getP());
			c.addAll(m.getC());
			cc.addAll(m.getCC());
			co.addAll(m.getCO());
			ap.addAll(m.getAP());
			apc.addAll(m.getAPC());
			apo.addAll(m.getAPO());
			stf.addAll(m.getStf_list());
		}

		String filename="data1.csv";
		String p_data=null;
		String c_data=null;
		String cc_data=null;
		String co_data=null;
		String ap_data=null;
		String apc_data=null;
		String apo_data=null;
		String s_data=null;

		int size1 = p.size(); //the length of the list of production(p,c,cc,co,ap,apc,apo,stf) 
		int period_num = size1/home_num;
		try { 
			File file = new File("write"); // create a folder
			file.mkdirs();
			File f = new File("write/"+filename);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f,true)); 
			// add a new data line
			if(k==0){
				bw.write("case "+(case_num+1));
				bw.newLine(); 
			}
			else if(k==1){
				bw.write("District "+(case_num+1));
				bw.newLine(); 

			}
			for(int i=1 ; i<home_num+1 ;i++)
			{
				bw.write("home"+i);
				bw.write(","); // separator ","
				bw.write("production"); 
				bw.write(","); 
				bw.write("consommation");
				bw.write(",");
				bw.write("consommation critique");
				bw.write(",");
				bw.write("consommation optionelle");
				bw.write(",");
				bw.write("appropriation");
				bw.write(",");
				bw.write("appropriation critique");
				bw.write(",");
				bw.write("appropriation optionelle");
				bw.write(",");
				bw.write("satisfaction");
				bw.newLine(); 

				int hp = 1;   
				for(int j=0 ; j<p.size()/2; j++){
					// change all the data from double to string
					p_data=Double.toString(p.get(j*2));
					// System.out.println(p_data);
					c_data=Double.toString(c.get(j*2));
					cc_data=Double.toString(cc.get(j*2));
					co_data=Double.toString(co.get(j*2));
					ap_data=Double.toString(ap.get(j*2));
					apc_data=Double.toString(apc.get(j*2));
					apo_data=Double.toString(apo.get(j*2));
					s_data=Double.toString(stf.get(j*2));

					bw.write("period"+hp); // write every period
					bw.write(","); 
					bw.write(p_data); 
					bw.write(",");
					bw.write(c_data); 
					bw.write(",");
					bw.write(cc_data); 
					bw.write(",");
					bw.write(co_data); 
					bw.write(",");
					bw.write(ap_data); 
					bw.write(","); 
					bw.write(apc_data); 
					bw.write(","); 
					bw.write(apo_data); 
					bw.write(","); 
					bw.write(s_data);
					bw.newLine(); 
					p_data=null;
					c_data=null;
					cc_data=null;
					co_data=null;
					ap_data=null;
					apc_data=null;
					apo_data=null;
					s_data=null;
					hp++;
				}
			}

			bw.close(); 

		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 


	}
	public static void CsvResultWriter(District dis,int case_num,int dk) { 

		List<Double> mean = dis.getStf_list();
		List<Double> gini = dis.getStf_v_list();

		String filename="data2.csv";
		String mean_data = null;
		String gini_data = null;
		try { 
			File file = new File("write"); 
			file.mkdirs();
			File f = new File("write/"+filename);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f,true)); 
			String p = null;
			if(dis.getPoli().getClass().getName().equals("INF380.PolitiqueA2"))
				p = "Politique 2";
			else 
				p = "Politique 1";
			bw.write(p); //Write the politic we have used
			bw.newLine();

			//System.out.println(case_num);
			if(dk==0){
				bw.write("case "+(case_num+1));
			}
			else if(dk==1){
				bw.write("District "+(case_num+1));
			}
			bw.write(",");
			bw.write("mean"); 
			bw.write(",");
			bw.write("gini");
			bw.newLine();

			int size2 = mean.size();
			int hp = 1;
			for(int k=0 ; k<size2 ;k++){
				mean_data=Double.toString(mean.get(k));
				gini_data=Double.toString(gini.get(k));
				bw.write("period"+hp); 
				bw.write(",");
				bw.write(mean_data);
				bw.write(",");
				bw.write(gini_data);
				bw.newLine();
				mean_data=null;
				gini_data=null;
				hp++;
			}

			String l_mean_data = Double.toString(dis.getStf_lt_m());
			String l_gini_data=Double.toString(dis.getStf_lt_v());
			bw.write("average"); 
			bw.write(",");
			bw.write(l_mean_data); 
			bw.write(",");
			bw.write(l_gini_data);
			bw.newLine();
			bw.close(); 

		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 

	}
}
