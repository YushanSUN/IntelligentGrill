package INF380;        
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author Wenjia WEI
 *
 * 
 */
import com.csvreader.CsvReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList; 
import java.util.List;  

public class Initialize {
	public Initialize() throws Exception{
	}
	
	public static Home Ini(Home home_n,File file) throws Exception{
		List<String[]> csvList = new ArrayList<String[]>();  
		List<Double> p = new ArrayList<Double>(); //production
		List<Double> c = new ArrayList<Double>(); //consummation
		List<Double> cc = new ArrayList<Double>(); //critical consummation 
		List<Double> co = new ArrayList<Double>(); //optional consummation 

		String filePath = file.getAbsolutePath();
		if (isCsv(filePath)) {  
			CsvReader reader = new CsvReader(filePath, ',', Charset.forName("UTF-8"));
			reader.readHeaders(); // ignore the headline of CSV file
			while (reader.readRecord()) {
				csvList.add(reader.getValues());
			}
			reader.close(); 

			for (int r_list = 0; r_list < csvList.size(); r_list++) {
				for (int c_list = 0; c_list < csvList.get(r_list).length; c_list++) {
					double cell = 0;
					try{
						cell = Double.parseDouble(csvList.get(r_list)[c_list]);
					}
					catch(NumberFormatException NFE){
						System.out.println("The format is wrong.");
					}
					if(c_list==0) p.add(cell);
					else if(c_list==1) c.add(cell);   
					else if(c_list==2) cc.add(cell);
					else if(c_list==3) co.add(cell);
					else System.out.print("error!");		                
				}
				//System.out.print("\n");
			}
			
			home_n.setP(p);
			home_n.setC(c);
			home_n.setCC(cc);
			home_n.setCO(co);
			
			int ls_size = home_n.getP().size();
			//generate a list filled with 1, size is equal to p
			List<Double> temp = new ArrayList<Double>(); 
			for(int i=0;i<ls_size;i++){
				temp.add(1.0);
			}
			for(int i=0;i<ls_size;i++){
				temp.set(i,0.0);
			}
			home_n.setAP(temp);
			home_n.setAPC(temp);
			home_n.setAPO(temp);

			List<Double> p_app_temp = new ArrayList<Double>();

			for(int j=0;j<ls_size;j++) {
				
				p_app_temp.add(home_n.getP().get(j)-home_n.getAP().get(j));

			}

			home_n.setP_app_dif(p_app_temp);

		} else { 
			System.out.println("This is not a CSV file!");  
		}
		return home_n;
	}

	public static District initial_district(File f) throws Exception{
		District d=new District();
		File[] listFiles = f.listFiles();
		for(File file: listFiles){
			Home h=new Home();
			h=Ini(h,file);
			d.addMember(h);
			System.out.println(file);
		}
		d.set_lists();
		//System.out.println("");
		return d;
	}

	// judge whether it is a CSV file
	private static boolean isCsv(String fileName) { 
		return fileName.matches("^.+\\.(?i)(csv)$"); 
	}
}