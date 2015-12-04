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

public class PublicTests extends BaseTests{
	public PublicTests() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	public void testHC_Correct() {
		for(int i=0;i<dis.getMember().size();i++){
			for(int j=0;j<dis.getMember().get(0).getC().size();j++){
				assertEquals(10.,dis.getMember().get(i).getC().get(j));	
			}		
		}
	}
	public void testDC_Correct() {
		assertEquals(20.,dis.getC().get(0));
	}
	
	
	public void testHCC_Correct() {
		for(int i=0;i<dis.getMember().size();i++){
			for(int j=0;j<dis.getMember().get(0).getCC().size();j++){
				assertEquals(5.,dis.getMember().get(i).getCC().get(j));	
			}
		}
	}
	public void testDCC_Correct() {
		assertEquals(10.,dis.getCC().get(0));
	}
	
	
	public void testHCO_Correct() {
		for(int i=0;i<dis.getMember().size();i++){
			for(int j=0;j<dis.getMember().get(0).getCO().size();j++){
				assertEquals(5.,dis.getMember().get(i).getCO().get(j));	
			}		}
	}
	public void testDCO_Correct() {
		assertEquals(10.,dis.getCO().get(0));
	}

	
	public void testHP_Correct() {
		for(int i=0;i<dis.getMember().size();i++){
			for(int j=0;j<dis.getMember().get(0).getP().size();j++){
				if(i==0)	assertEquals(6.,dis.getMember().get(i).getP().get(j));	
				else if(i==1)	assertEquals(4.,dis.getMember().get(i).getP().get(j));
			}	
		}
	}
	public void testDP_Correct() {
		assertEquals(10.,dis.getP().get(0));
	}
	
	public void testHAPProduction_Correct() {
		for(int i=0;i<dis.getMember().size();i++){
			if(dis.flag1==1){
				for(int j=0;j<dis.getMember().get(0).getAP().size();j++){
					if(i==0)	assertEquals(6.,dis.getMember().get(i).getAP().get(j));	
					else if(i==1)	assertEquals(4.,dis.getMember().get(i).getAP().get(j));
				}
			}
			else if(dis.flag1==2){
				for(int j=0;j<dis.getMember().get(0).getAP().size();j++){
					if(i==0)	assertEquals(5.,dis.getMember().get(i).getAP().get(j));	
					else if(i==1)	assertEquals(5.,dis.getMember().get(i).getAP().get(j));
				}
			}
		}
	}
	public void testDAProduction_Correct() {
		assertEquals(10.,dis.getAP().get(0));
	}

	public void testHSatisfaction_Correct() {
		for(int i=0;i<dis.getMember().size();i++){
			if(dis.flag1==1){
				for(int j=0;j<dis.getMember().get(0).getAP().size();j++){
					if(i==0)	assertFalse(Math.abs(0.84-dis.getMember().get(i).getStf_list().get(j))>0.00001);	
					else if(i==1)	assertFalse(Math.abs(0.64-dis.getMember().get(i).getStf_list().get(j))>0.00001);
				}
			}
			else if(dis.flag1==2){
				for(int j=0;j<dis.getMember().get(0).getAP().size();j++){
					if(i==0){
						if(j==0)	assertFalse(Math.abs(0.64-dis.getMember().get(i).getStf_list().get(0))>0.00001);
						else if(j==1)	assertFalse(Math.abs(0.62333-dis.getMember().get(i).getStf_list().get(1))>0.00001);
					}
					else if(i==1){
						if(j==0)	assertFalse(Math.abs(0.80000-dis.getMember().get(i).getStf_list().get(0))>0.00001);
						else if(j==1)	assertFalse(Math.abs(0.80000-dis.getMember().get(i).getStf_list().get(1))>0.00001);
					}
				}
			}
		}
	}
	public void testDSatisfaction_Correct() {
		if(dis.flag1==2)	assertEquals(0.71583,dis.getStf_m_lt());
		if(dis.flag1==1)	assertEquals(0.74,dis.getStf_m_lt());
	}

	
}
