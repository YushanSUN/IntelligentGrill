package INF380;
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author DENG Xiao
 *
 * 
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class Member {

	protected List<Double> p = new ArrayList<Double>();   // production
	protected List<Double> c = new ArrayList<Double>();   // consommation totale
	protected List<Double> cc = new ArrayList<Double>();  // consommation critique
	protected List<Double> co = new ArrayList<Double>();  // consommation optionelle
	protected List<Double> ap = new ArrayList<Double>();  // appropriation totale
	protected List<Double> apc = new ArrayList<Double>(); // appropriation critique
	protected List<Double> apo = new ArrayList<Double>(); // appropriation optionelle
	protected List<Double> pt = new ArrayList<Double>();  // production totale
	
	protected List<Member> member = new ArrayList<Member>();   

	public List<Double> p_app_dif= new ArrayList<Double>(); // p - app
	
	protected Properties prop = new Properties();
	protected String flag;
	protected int flag1=0;

	protected List<Double> stf_list = new ArrayList<Double>();  // list of short term satisfactions in different periods
	protected double stf_lt_v;  // long term satisfaction, gini
	public double stf_lt_m;     // long term satisfaction, mean

	protected PolitiqueA poli;

	public Member() throws IOException{
		InputStream fis = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		prop.load(fis);
		fis.close();
		flag=prop.getProperty("flag");
		flag1=Integer.parseInt(flag);

		if(flag1==1){
			poli = new PolitiqueA1();
			//System.out.println("Politique 1");
		}

		if(flag1==2){
			poli = new PolitiqueA2();  
			//System.out.println("Politique 2");
		}
	}
	

	
	/*
	 *calculate the short term satisfaction of a district,
	 *this function is override in Class District
	 */
	public void stf_st(){ };
	
	/*
	 *calculate the short term satisfaction of a home,
	 *this function is override in Class Home 
	 */
	public void stf_st(double x,double y,double a, double b,double c,double d) {};

	
	/*
	 * calculate the long term satisfaction of one member(home or district ), 
	 * including the mean and the variance( Gini coefficient)  
	 * This function is override in Class Home and District
	 */
	public void stf_lt(){};


	public List<Double> getStockplus(){
		List<Double> stockplus = new ArrayList<Double>();
		stockplus = poli.getP(this.p,this.c);
		ap = poli.setAP(this.p, this.c);		
		//this.setP_app_dif(app_dif);
		//System.out.println(ap);
		return stockplus;
	}


	public PolitiqueA getPoli(){
		return poli;
	}
	
	public void setP(List<Double> p) {
		for(Double item: p) this.p.add(item);
	}

	public void setC(List<Double> c) {
		for(Double item: c) this.c.add(item);
	}

	public void setCC(List<Double> cc) {
		for(Double item: cc) this.cc.add(item);
	}

	public void setCO(List<Double> co) {
		for(Double item: co) this.co.add(item);
	}

	public void setAP(List<Double> ap) {
		for(Double item: ap) this.ap.add(item);
	}
	public void setAP(int i,double api) {
		this.ap.set(i, api);
	}
	public void setAPC(List<Double> apc) {
		for(Double item: apc) this.apc.add(item);
	}
	public void setAPC(int i,double apci) {
		this.apc.set(i,apci);
	}
	public void setAPO(List<Double> apo) {
		for(Double item: apo) this.apo.add(item);
	}
	
	public void setAPO(int i,double apoi) {
		this.apo.set(i,apoi);
	}

	public void setP_app_dif( int i,double l){
		this.p_app_dif.set(i,l);
	}
	
	public void setP_app_dif( List<Double> l){
		for(double i:l) this.p_app_dif.add(i);
	}

	public double getStf_lt_v(){
		return this.stf_lt_v;
	}

	public double getStf_lt_m(){
		return this.stf_lt_m;
	}

	public List<Double> getP() {
		return p;
	} 

	public List<Double> getC() {
		return c;
	} 

	public List<Double> getCC() {
		return cc;
	} 

	public List<Double> getCO() {
		return co;
	} 

	public List<Double> getAP() {
		return ap;
	}

	public List<Double> getAPC() {
		return apc;
	}

	public List<Double> getAPO() {
		return apo;
	}

	public List<Double> getStf_list(){
		return stf_list;
	}

	public List<Double> getP_app_dif(){
		return this.p_app_dif;
	}

	public void setPT(List<Double> pt){
		for(int i=0;i<pt.size();i++) this.pt.set(i,pt.get(i));
	}
	public void setPT(int i,double pt) {
		this.pt.set(i, pt);
	}
	//DENG
	
	
	public void addPT(List<Double> p){
		double p1 = 0;
		for(int i=0;i<p.size();i++){
			
			p1 = this.pt.get(i)+p.get(i);
			this.pt.set(i,p1);
		}
	}

	public void addMember(Member h){
		member.add(h);
	}

	public List<Member> getMember(){
		return  member;
	}

	public void set_list() {
		// TODO Auto-generated method stub
		
	}

}
