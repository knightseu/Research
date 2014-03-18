/**
 * 
 */
package rtgrid.taskmodel;

/**
 * SubTask.java
 * Package:  rtgrid.taskmodel
 * @author Xuan, 2006-11-6 
 */
public class SubTask implements ITask {
	public int _mid; //machine id, where this subtask is runned
	public int _datasize; // 
	public int _sTime; // when the subtask is dispathed;
	public int _aTime; // when the subtask arrived the worker node.
    public int _compTime; // when the subtask is completed;	
    public Task _parents=null;
   
    double alpha_i=0;
	
    
    /**
     * Here, we assume that all nodes available at the same
     * @param parents
     * @param node
     * @param datasize
     */
	public SubTask(Task parents, int node, int datasize) {
		
		_parents=parents;
		_mid=node;
		_sTime=parents.startTime;
		
		alpha_i=parents.alpha1*Math.pow(parents.beta,node);
		int commtime=0;
		for (int i=0;i<=node;i++) {
			commtime += (int) parents.alpha1*Math.pow(parents.beta,i)*parents.datasize*parents.Cms;
		}
		_aTime=_sTime+commtime;
		_compTime=parents.compTime;
	}
	
	/**
	 * 
	 * @param parents parent task
	 * @param node
	 * @param datasize
	 * @param sTime
	 * @param aTime
	 * @param compTime
	 */
	public SubTask(Task parents,int node, int datasize, int sTime, int aTime, int compTime) {
		_parents=parents;
		_mid=node;
		_datasize=datasize;
		_sTime=sTime;
		_aTime=aTime;
		_compTime=compTime;
		
	}
	
	public SubTask(SubTask st) {
		_parents=st._parents;
		_sTime=st._sTime;
		_aTime=st._aTime;
		_compTime=st._compTime;
		_datasize=st._datasize;
		_mid=st._mid;
	}

	
	
	public int getCommunicationTime() {
		return _aTime-_sTime;
	}
	
	public int getComputationTime() {
		return _compTime-_aTime;
	}
	
	/**
	 * 
	 * @return return the time when the subtask begin to be computed
	 */
	public int getCompStartTime() {
		return _aTime;
	}
	
	
	/**
	 * 
	 * @return return the time when the subtask begin to be dispatched.
	 */
	public int getCommStartTime() {
		return _sTime;
	}
	
	public int getTotalTime() {
		return (_compTime-_sTime);
	}

    public String toString() {
    	String rst="";
    	rst+="(pid="+_parents.id;
    	rst+=",id="+_mid;
    	rst+=",sTime="+_sTime;
    	rst+=",aTime="+_aTime;
    	rst+=",compTime="+_compTime;
    	rst+=")";
    	return rst;
    }

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getAbsoluteDeadline()
	 */
	public int getAbsoluteDeadline() {
		
		return _parents.getAbsoluteDeadline();
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getArrTime()
	 */
	public int getArrTime() {
		
		return _parents.getArrTime();
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getCompletionTime()
	 */
	public int getCompletionTime() {
		
		return _compTime;
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getExeTime()
	 * 
	 */
	public int getExeTime() {
		
		return _compTime-_aTime;
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getID()
	 */
	public int getID() {
		
		return this._mid;
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getNodesAct()
	 */
	public int getNodesAct() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getRelativeDeadline()
	 */
	public int getRelativeDeadline() {
		
		return _parents.getRelativeDeadline();
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getRespondsTime()
	 */
	public int getRespondsTime() {
		return _compTime-_parents.arrTime;
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getStartTime()
	 */
	public int getStartTime() {
		return _sTime;
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getDatasize()
	 */
	public int getDatasize() {
		
		return _datasize;
	}

}
