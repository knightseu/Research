/**
 * 
 */
package rtgrid.util;
import rtgrid.COMMON;
import java.util.ArrayList;

/**
 * SysVector.java
 * Package:  rtgrid.util
 * @author Xuan, 2006-12-30 
 * 
 * 
 * Store the parameters for cluster.
 */
public class SysVector {
	protected int [] cms;
	protected int [] cps;
	protected int size;
	String sError="out of array boundary, SysVector";
	public SysVector(int size) {
		cms=new int[size];
		cps=new int[size];
		this.size=size;
	}
	
	public String toString() {
		String rst="Node:"+size+"\n";
		for (int i=0;i<size;i++) {
			rst+=i+","+cms[i]+","+cps[i]+"\n";
		}
		return  rst;
	}
	
	/**
	 * set cms value for sepecific node
	 * @param index the index of the node
	 * @param value  cms value
	 */
	public void setCms(int index, int value) {
		cms[index]=value;
	}
	
	/**
	 * set cps value for sepecific node
	 * @param index the index of the node
	 * @param value  cps value
	 */
	public void setCps(int index, int value) {
		cps[index]=value;
	}
	
    public int getCms(int index) {
    	if ((index>=size) || (index<0)) {
    		if (COMMON.DEBUG)
    			System.out.println(sError);
    		return -1;
    	}
    	return  cms[index];
    }

    public int getCps(int index) {
    	if ((index>=size) || (index<0)) {
    		if (COMMON.DEBUG)
    			System.out.println(sError);
    		return -1;
    	}
    	return  cps[index];
    }
    
    /**
     * set all cms to the same value
     * @param value
     */
    public void setCms(int value) {
    	for (int i=0;i<size;i++)
    		cms[i]=value;
    }
    
    public int getCms() {
    	return cms[0];
    }
    
    /**
     * set all cps to the same value
     * @param value
     */
    public void setCps(int value) {
    	for (int i=0;i<size;i++)
    		cps[i]=value;
    }
    
    public int getCps() {
    	return cps[0];
    }
    
    public int getSize() {
    	return size;
    }
}
