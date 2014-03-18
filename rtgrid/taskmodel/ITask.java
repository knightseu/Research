package rtgrid.taskmodel;

public interface ITask {
	
	/**
	 * 
	 * @return ID of the task
	 */
	public int getID();

	
	/**
	 * 
	 * @return the execution time of the task
	 */
	public int getExeTime();
	
	/**
	 * 
	 * @return relative deadline of the task
	 */
	public int getRelativeDeadline();
	
	
	/**
	 * 
	 * @return absolute deadline of the task
	 */
	public int getAbsoluteDeadline();
	
	/**
	 * 
	 * @return the time when the task arrives the system
	 */
	public int getArrTime();
	
	
	/**
	 * 
	 * @return the time when the task start computing
	 */
	public int getStartTime();
	
	/**
	 * 
	 * @return the time when the task complete computation
	 */
	public int getCompletionTime();
	
	/**
	 * 
	 * @return the repondsTime= finishTime-arrivalTime
	 */
	public int getRespondsTime();
	/**
	 * 
	 * @return the number of nodes that actually assigned to the task
	 */
	public int getNodesAct();
	
	/**
	 * 
	 * @return the datasize of the tasks;
	 */
	public int getDatasize();
	
	
}
