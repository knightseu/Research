package rtgrid.taskmodel;
import rtgrid.util.*;

public interface IDivisibleTask {
	/**
	 * 
	 * @return the datasize of the task
	 */
	public int getDatasize();
	
	/**
	 * 
	 * @return the minimum number of nodes that the task need to finish before its deadilne
	 */
	public int getNmin();
	
	
	/**
	 * 
	 * @return the time when the dispatch of the data of the task has been done.
	 */
	public int getDispatchDoneTime();
	
	/**
	 *  set  the time when the dispatch of the data of the task has been done.
	 * @param time
	 */
	public void setDispathDoneTime(int time);
	
	
	/**
	 * calculate the computation time in heterogenous system  according to certain paramerters of cms and cps and the type
	 * @param sysv
	 * @param type
	 * @return
	 */
	public int computeExecutiontime(SysVector sysv, int type);
}
