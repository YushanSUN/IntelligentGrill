package INF380;
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author DENG Xiao
 *
 * 
 */

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Test {

	/*
	 * After creating a district list, use this function to collect and distribute electricity
	 * between and within districts, and calculate the satisfaction then print the results
	 * in the console and output them into a file, finally generate some graphics
	 * 
	 * Input: a list of districts in which the lists of production and consummation are already initialized 
	 */
	public static void runOneCase(List<District> dis_list) throws Exception{

		int dis_num = dis_list.size();

		///////////////////////////////////////////////////////////////////
		/////    Collect electricity in every district among homes ////////
		///////////////////////////////////////////////////////////////////
		for(District dis : dis_list)	Action.collect(dis);

		///////////////////////////////////////////////////////////////////
		/////////////    Create a new city      /////////////////////////////
		///////////////////////////////////////////////////////////////////
		District city = new District();

		///////////////////////////////////////////////////////////////////
		/////////////    Add districts to city /////////////////////////////
		///////////////////////////////////////////////////////////////////
		for(District dis : dis_list) city.addMember(dis);

		///////////////////////////////////////////////////////////////////
		///   Collect and distribute electricity in city among districts///
		///////////////////////////////////////////////////////////////////
		System.out.println("\nCity 1");
		Action.collect(city);
		Action.distribution2(city);

		///////////////////////////////////////////////////////////////////
		///      Distribute electricity in district among homes   /////////
		///////////////////////////////////////////////////////////////////
		for(int i=0;i<dis_num;i++){
			System.out.println("District "+ (i+1));
			Action.distribution(dis_list.get(i));
		}

		///////////////////////////////////////////////////////////////////
		/////////////////   Calculate satisfaction    /////////////////////
		///////////////////////////////////////////////////////////////////

		System.out.println("\n****************  Calculate the satisfaction  ******************************");

		///////////////////////////////////////////////////////////////////
		////////   Calculate satisfaction of a district        ////////////
		///////////////////////////////////////////////////////////////////

		for(int i=0;i<dis_num;i++){
			System.out.println("\nDistrict "+(i+1));
			System.out.println("********************************************");
			Satisfaction.cal_dis_sat(dis_list.get(i));
		}


		///////////////////////////////////////////////////////////////////
		////////   Calculate satisfaction of a city        ////////////
		///////////////////////////////////////////////////////////////////		
		Satisfaction.cal_city_sat(city);

	}

	public static void main(String[] args) throws Exception {

		/*
		 * Firstly, you need to create a list of District Class which are already initialized
		 * 2 ways to get the initial data: read from files or add manually;
		 * 
		 * Secondly, call the function runOneCase() and put the list as input argument, this 
		 * function will collect and distribute electricity then calculate the 4 types of 
		 * satisfaction automatically, and generate outputs(to console, to files and graphics)
		 * 
		 * you need to specify the number of cases as cas_num 
		 */

		///////////////////////////////////////////////////////////////////
		/////////////    Create the district list /////////////////////////
		////////////		2 ways                /////////////////////////
		///////////////////////////////////////////////////////////////////

		///////////////  1.  add data manually /////////////////////////

		way1();    

		////////////////// 2. read data from external files  /////////////////
		//way2();

	}

	public static void way1() throws Exception{

		///////////////  1.  add data manually /////////////////////////

		/////// firstly create the district list ////// 

		List<Double> list_p = new ArrayList<Double>(); 
		List<Double> list_cc = new ArrayList<Double>();
		List<Double> list_co = new ArrayList<Double>();

		int period_num = 2;

		for(int i=0;i<period_num;i++){
			list_cc.add(5.0);
		}

		for(int i=0;i<period_num;i++){
			list_co.add(5.0);
		}

		File folder = new File("write"); 
		Writecsv.deleteDir(folder);//clear the file
		int cas_num = 6;

		District dis = null;

		for(int i= 0; i < cas_num;i++){

			System.out.println("===================================================================  Case "+ (i+1) + "=========================================================");

			//%%%%%%%%%%  each time, change the dis_list and run the function runOneCase %%%%%%%%%%%%%%%%

			list_p.add(10.0-i);
			list_p.add(10.0-i);

			// create a home, input arguments are its production list, critical consummation list, optional consummation list
			Home h1 = new Home(list_p,list_cc,list_co);

			list_p.clear();
			list_p.add(i*1.0);
			list_p.add(i*1.0);

			Home h2 = new Home(list_p,list_cc,list_co);
			list_p.clear();

			// create a district and add homes to it
			dis = new District();
			dis.addMember(h1);
			dis.addMember(h2);

			// create district list and add district to it
			List<District> dis_list = new ArrayList<District>();
			dis_list.add(dis);

			///////////// Then call the function to run ////////////
			runOneCase(dis_list);


			////////// Output to files ///////////////////


			// * the first argument is the District Class to be output
			// * the second argument is the number of cases


			///////////////////////////////////////////////////////////////////
			/////////////    Output ///////////////////////////////////////////
			///////////////////////////////////////////////////////////////////
			Writecsv.CsvCaseWriter(dis, i, 0);
			Writecsv.CsvResultWriter(dis, i, 0);


			//////////////////////////////////////////////

			///////////////////////////////////////////////////////////////////
			/////////////    Draw the graph of satisfaction ///////////////////
			///////////////////////////////////////////////////////////////////
			GTEST frame1=new GTEST(dis.getStf_list());
			frame1.setSize(800,600);
			frame1.setTitle("Satisfaction-District-"+(i+1));
			frame1.setLocationRelativeTo(null);//center
			frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame1.setVisible(true);


		}

		///////////////  1.  add data manually /////////////////////////


	}

	public static void way2() throws Exception{

		/////////////////// 2. read data from external files  /////////////////
		File folder = new File("write"); 
		Writecsv.deleteDir(folder);//clear the file


		District dis = new District();
		// input argument is the name of the file directory of a district, in which are there des home.csv
		File f = new File("District1");   
		dis = Initialize.initial_district(f);

		// **********  if you want to add another district, do like this
		District dis2 = new District();
		File f2 = new File("District2");   
		dis2 = Initialize.initial_district(f2);


		// create a distrcit list and add district instances to it
		List<District> dis_list = new ArrayList<District>();
		dis_list.add(dis);
		// **********  if you want to add another district, do like this
		dis_list.add(dis2);

		District city = new District();
		for(District d:dis_list){
			city.addMember(d);
		}
		// the input argument is a district list
		runOneCase(dis_list);


		///////////////////////////////////////////////////////////////////
		/////////////    Output ///////////////////////////////////////////
		///////////////////////////////////////////////////////////////////		
		Writecsv.CsvCaseWriter(dis, 0, 1);
		Writecsv.CsvResultWriter(dis, 0, 1);
		Writecsv.CsvCaseWriter(dis2, 1, 1);
		Writecsv.CsvResultWriter(dis2, 1, 1);

		//		Writecsv writer = new Writecsv();
		//		writer.WriteHome(dis,1,1);//WriteHome(District dis,int district_number,int city_number)
		//		writer.WriteHome(dis2,2,1);
		//		writer.WriteDis(city,1,1);//WriteDis(District city,int city_number,int district_num)
		//		writer.WriteDis(city,1,2);
		//		writer.WriteCity(city,1);//WriteCity(District city,int city_num)




		///////////////////////////////////////////////////////////////////
		/////////////    Draw the graph of satisfaction ///////////////////
		///////////////////////////////////////////////////////////////////
		GTEST frame1=new GTEST(dis.getStf_list());
		frame1.setSize(800,600);
		frame1.setTitle("Satisfaction-District-1");
		frame1.setLocationRelativeTo(null);//center
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);

		GTEST frame2=new GTEST(dis2.getStf_list());
		frame2.setSize(800,600);
		frame2.setTitle("Satisfaction-District-2");
		frame2.setLocationRelativeTo(null);//center
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(true);

		//      GTEST frame3=new GTEST(dis3.getStf_list());
		//		frame3.setSize(800,600);
		//		frame3.setTitle("Satisfaction-District-3");
		//		frame3.setLocationRelativeTo(null);//center
		//		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		frame3.setVisible(true);
		//				
		//	 	TODO Draw the graph as above




		/////////////////// 2. read data from external files  /////////////////


	}

}
