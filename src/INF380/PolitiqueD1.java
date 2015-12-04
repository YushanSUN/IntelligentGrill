package INF380;
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author Yushan SUN
 *
 * 
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class PolitiqueD1 extends PolitiqueD{
	HashMap<Integer,Double> map=new HashMap<Integer,Double>(); 
	public PolitiqueD1(){
		
		
	}
	/*
	 * Enter: Map
	 * Return: List sorted
	 * This function will sort the enter map into a sorted list.
	 */
	private static List<Entry<Integer,Double>> sortList(HashMap<Integer,Double> map) { 
		List<Entry<Integer,Double>> list = new LinkedList<Entry<Integer,Double>>(map.entrySet());

	       // Defined Custom Comparator here
		Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare(Map.Entry<Integer, Double> o1,
					Map.Entry<Integer, Double> o2) {
				return (int) (o2.getValue() - o1.getValue());
			}
		});
		return list;
	}
	
	/*
	 * Enter: list
	 * Return: map
	 * This function will change a list to a hash map.
	 */
	
	private static HashMap<Integer,Double> listMap(List<Entry<Integer,Double>> list) { 
	       // Here I am copying the sorted list in HashMap
	       // using LinkedHashMap to preserve the insertion order
	       HashMap<Integer,Double> sortedHashMap = new LinkedHashMap<Integer,Double>();
	       for (Iterator<Entry<Integer,Double>> it = list.iterator(); it.hasNext();) {
	              Map.Entry<Integer,Double> entry = (Map.Entry<Integer,Double>) it.next();
	              sortedHashMap.put(Integer.parseInt(entry.getKey().toString()), Double.parseDouble(entry.getValue().toString()));
	       } 
	       return sortedHashMap;
	  }
	/*
	 * Enter: List, district
	 * Return: List sorted
	 * This function will sort the enter list into a sorted list by the rule of priority.
	 */
	
	public static List<Entry<Integer,Double>> sortPrio(List<Entry<Integer,Double>> list, final List<Member> d, final int j){
		
		Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare(Map.Entry<Integer, Double> o1,
					Map.Entry<Integer, Double> o2) {
				if(o2.getValue() - o1.getValue() == 0.0){
					return((int)(d.get(o2.getKey()).getP().get(j)-d.get(o1.getKey()).getP().get(j)));
				}
				else{
					return (int) (o2.getValue() - o1.getValue());
				}
			}
		});;
		
	return list;
	}
	/*
	 * This is the main function which will distribute the energy.
	 */
	
	
	public void distribuer(List<Double> p,List<Member> d){
		this.p = p;
		
		int n = d.get(0).getP().size();
		int nn = d.size();
		
		//distribuer the ap to cc and co if there is any
		for(int j=0;j<nn;j++){
			if(d.get(j).flag1==1){
				for(int i=0;i<n;i++){
					if(d.get(j).getAP().get(i)>=d.get(j).getCC().get(i)){
						d.get(j).setAPC(i, d.get(j).getCC().get(i));
						d.get(j).setAPO(i, (d.get(j).getAP().get(i)-d.get(j).getCC().get(i)));
					}
					else{
						d.get(j).setAPC(i, d.get(j).getAP().get(i));
						d.get(j).setAPO(i, 0);
					}
				}
			}
			else if(d.get(j).flag1==2){
				for(int i=0;i<n;i++){
					d.get(j).setAPC(i, 0);
					d.get(j).setAPO(i, 0);
				}
			}
		}
			
		
		
		
		for(int j=0;j<n;j++){
			
			
			//first distribuer the cc
			//the one who need the ressources most will first get them, if equals, who produce most can get first.
			for(int i=0;i<nn;i++){
				map.put(i, (d.get(i).getCC().get(j)-d.get(i).getAPC().get(j)));
			}
			
			/*the one who produce more will have the priority*/ 
			
			List<Entry<Integer,Double>> list0 = sortList(map);
			List<Entry<Integer,Double>> list = sortPrio(list0,d,j);
			
			
			
			while(list.get(0).getValue()>0){
				if(this.p.get(j) >= 1.0){
					this.p.set(j, this.p.get(j) - 1.0);
					list.get(0).setValue(list.get(0).getValue()-1.0);
				}
				else if(0< this.p.get(j) && this.p.get(j) < 1.0){
					list.get(0).setValue(list.get(0).getValue() - this.p.get(j));
					this.p.set(j, 0.0);
					
				}
				else if(this.p.get(j)==0.0){
					break;
				}
				
				
				list = sortPrio(list,d,j);
			}
			HashMap<Integer,Double> maps1 = listMap(list);
			
			for(int i=0;i<nn;i++){
				d.get(i).setAPC(j,(d.get(i).getCC().get(j)-maps1.get(i)));
			}
			
			
			
			
			
			
			//then distribuer the co
			//the one who need the ressources most will first get them, if equals, who produce most can get first.
			map.clear();
			for(int i=0;i<nn;i++){
				map.put(i,(d.get(i).getCO().get(j)-d.get(i).getAPO().get(j)));
			}
			
			/*the one who produce more will have the priority*/ 
			
			List<Entry<Integer,Double>> list02 = sortList(map);
			List<Entry<Integer,Double>> list2 = sortPrio(list02,d,j);
			
			
				
						
			while(list2.get(0).getValue()>0.0){
				if(this.p.get(j) >= 1.0){
					this.p.set(j, this.p.get(j) - 1.0);
					list2.get(0).setValue(list2.get(0).getValue() - 1.0);
				}

				else if(0< this.p.get(j) && this.p.get(j) < 1.0){
					list2.get(0).setValue(list2.get(0).getValue() - this.p.get(j));
					this.p.set(j, 0.0);
					
				}
				else if(this.p.get(j)==0.0){
					break;
				}
				
				list2 = sortPrio(list2,d,j);
			}
			
			
			HashMap<Integer,Double> maps2 = listMap(list2);
			for(int i=0;i<nn;i++){
				d.get(i).setAPO(j,(d.get(i).getCO().get(j)-maps2.get(i)));
			}
			
			
			//calculate the ap by the cc and co
			for(int i=0;i<nn;i++){
				d.get(i).setAP(j,(d.get(i).getAPC().get(j)+d.get(i).getAPO().get(j)));
			}
			maps1.clear();
			maps2.clear();
			map.clear();
			
		}
		
		
		
		
	}

}
