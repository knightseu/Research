package rtgrid.util;

public class SimResults extends AbstractSimResult {
	public int size;
    public SimResult [] results;
    public SimResults(int size) {
    	this.size=size;
    	results= new SimResult[size];
    	for (int i=0;i<size;i++)
    		results[i]=new SimResult();
    }
	public int getTotalNum() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getTotalNum();
		return rst/size;
	}
	public int getRejectNum() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getRejectNum();
		return rst/size;
	}
	public int getMissNum() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getMissNum();
		return rst/size;
	}
	public int getAdmitNum() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getAdmitNum();
		return rst/size;
	}
	public int getTotalWorkload() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getTotalWorkload();
		return rst/size;
	}
	public int getRejWorkload() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getRejWorkload();
		return rst/size;
	}
	public int getAdWorkload() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getAdWorkload();
		return rst/size;
	}
	public int getMissWorkload() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getMissWorkload();
		return rst/size;
	}
	public int getWorkload() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getWorkload();
		return rst/size;
	}
	public double getRejectRate() {
		double rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getRejectRate();
		return rst/size;
	}
	public double getMissRatio() {
		double rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getMissRatio();
		return rst/size;
	}
	
	public double getAvgNodes() {
		double rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getAvgNodes();
		return rst/size;
	}
	
	public double getSplitRejRatio() {
		double rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getSplitRejRatio();
		return rst/size;
	}
	/*
	public void addNewTask(int time, Task t) {
		// TODO Auto-generated method stub
		
	}
	public void addReject(int time, Task t) {
		// TODO Auto-generated method stub
		
	}
	public void addMiss(int time, Task t) {
		// TODO Auto-generated method stub
		
	}
	public void log(int time, Task task, int type) {
		// TODO Auto-generated method stub
		
	}
	public void printSchedule() {
		// TODO Auto-generated method stub
		
	}
	public void printRejTasks() {
		// TODO Auto-generated method stub
		
	}
	public void printMisTasks() {
		// TODO Auto-generated method stub
		
	}
	public void printLog() {
		// TODO Auto-generated method stub
		
	}
	public void printLog(String filename) {
		// TODO Auto-generated method stub
		
	}*/
	public double getAvgQueueLen() {
		double rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getAvgQueueLen();
		return rst/size;
	}
	public long getAvgResponseTime() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getAvgResponseTime();
		return rst/size;
	}
	public long getAvgWaitingTime() {
		int rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getAvgWaitingTime();
		return rst/size;
	}
	public double getAdWorkloadRatio() {
		double rst=0;
		for (int i=0;i<size;i++)
    		rst+=results[i].getAdWorkloadRatio();
		return rst/size;
	}
	public void printLog() {
		// TODO Auto-generated method stub
		
	}
	public void printLog(String filename) {
		// TODO Auto-generated method stub
		
	}
}
