package rtgrid.resource;
/**
 * 
 * @author Xuan
 * 
 * Available node info, at time there are num nodes available.
 * 
 * Used in SchedulabilityTest of Scheduling Algorithms, See Pseudo Code for details
 */
public class ANI {
	public int time;
	public int num;
	public ANI(int time,int num) {
		this.time=time;
		this.num=num;
	}
}
