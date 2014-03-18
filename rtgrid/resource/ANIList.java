/**
 * 
 */
package rtgrid.resource;

import java.util.Vector;

/**
 * ANIList.java
 * Package:  rtgrid.resource
 * @author Xuan, 2007-1-5 
 */
public class ANIList {
	private Vector _data=null;
	public void add(ANI ani) {
		_data.add(ani);
	}
	
	public ANI get(int index) {
		return (ANI) _data.elementAt(index);
	}
	
	public void remove(int index){
		_data.remove(index);
	}
	
	/**
	 * When schedule a task, this list should change
	 * @param startTime the starttime of a task
	 * @param endTime the completion time of a task
	 * @param dispatchTime the time when the task has been dispatched, that is, the free time of the head node. 
	 * @param num the number of nodes the 
	 */
	public void inserttask(int startTime,int endTime, int dispatchTime,int num){
		
	}
}
