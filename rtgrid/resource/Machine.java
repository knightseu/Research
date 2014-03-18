package rtgrid.resource;
import java.util.Random;
import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.taskmodel.SubTask;



/**
 * 
 * @author Xuan
 *
 * The Machine class represents each processing node
 */
public class Machine implements IResource {
	//TODO not tested
	public int curOccupiedTime;/*the machine is idle at this time,
	                          however,it can not be used by another task,
	                          because it has been assigned, and now,
	                          it is waiting for the task to arrive */
	public int curStartTime=0;//the time when the task is actually running.
	public int curEndTime=0;
	public double alpha;
	public boolean b=true;	
	public int id;//machine id;
	public Vector hisData; //store the history data
	public int utlTime; // the total computation time of the machine

	public boolean bDynamic;
	public int speed;
    public int variance;
    private Random ran;
	Machine (int id){	
		hisData = new Vector();
		utlTime=0;
		this.id=id;
		this.bDynamic=false;
	}
	
	Machine (Machine mac) {
		this.id=mac.id;
		this.curEndTime=mac.curEndTime;
		this.curOccupiedTime=mac.curOccupiedTime;
		this.curStartTime=mac.curStartTime;
		this.utlTime=mac.utlTime;
		hisData=new Vector();
		Vector hd=mac.hisData;
		for (int i=0;i<hd.size();i++) {
			SubTask st=(SubTask)hd.elementAt(i);
			SubTask st2= new SubTask(st);
			hisData.add(st2);
		}
		this.bDynamic=mac.bDynamic;
	}

	public Machine (int id, int speed, int variance) {
		this.id=id;
		this.speed=speed;
		this.variance=variance;
		ran = new Random();
	}
	
	public Machine(int id, int speed) {
		this.id=id;
		this.speed=speed;
		bDynamic=false;
	}
	
	/**
	 * @return return the speed of the machine
	 */
	public int getSpeed() {
		if (bDynamic) {
			int rst=0;
			while (rst==0) {
				double tmp= ran.nextDouble();
				rst=(int) (tmp*2*variance + speed-variance);
			}
			return rst;
		}
		else 
			return speed;
	}
	/**
	 * Set the speed of dynamic Machine
	 * @param speed
	 * @param variance
	 */
	public void setSpeed(int speed,int variance) {
		this.speed=speed;
		this.variance=variance;
		if (!this.bDynamic)
			this.bDynamic=true;
	}
	public String getType() {
		
		return "Machine";
	}
	

	
	// the start time for current task/subtask
	public int getStartTime() {
		return curStartTime;
	}
	
	// the end time for current task/subtask
	public int getEndTime() {
		return curEndTime;
	}
	
	/**
	 * 
	 * @return return the time when the node is actually begin to
	 */
	public int getOccupiedTime(){
		return curOccupiedTime;
	}
	
	/**
	 * Weather the machine is available at time "curTime"
	 * @param curTime
	 * @return
	 */
	public boolean isAvailable(int curTime) {
		if (curTime >=curEndTime )
			return true;
		else return false;
	}
	
	/**
	 * 
	 * @return the available time of the machine
	 */
	public int whenAvailable() {
		return curEndTime;
	}
	
	
	/**
	 * Dispatch the task on this processing node;
	 * @param st
	 */
	public void assignTask(SubTask st) {
		//hisData.add(st);
		utlTime+=st.getComputationTime();
		curOccupiedTime=st._sTime;
		curStartTime=st._aTime;
		curEndTime=st._compTime;
		if (COMMON.DEBUG)
			System.out.println(st);
	}
	/**
	 * 
	 * @return utilization rate
	 */
	public double utlRate(){
		return 0;
	}
	
	/**
	 * 
	 * @return utilization rate
	 */
	public double utlRate(int curTime){
		return utlTime/curTime;
	}
	/**
	 * 
	 * @return total computation time of this machine
	 */
	public int getUtlTime() {
		return utlTime;
	}

	/* (non-Javadoc)
	 * @see rtgrid.resource.IResource#accept(rtgrid.resource.IResourceVisitor)
	 */
	public void accept(IResourceVisitor visitor) {
		
	}
	
	
}
