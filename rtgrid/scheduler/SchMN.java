/**
 * 
 */
package rtgrid.scheduler;

import java.util.Vector;

import rtgrid.resource.ANI;
import rtgrid.resource.Cluster;
import rtgrid.taskmodel.Task;

/**
 * SchMN.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-1 
 * 
 * 
 * FIFO-OPR-MN
 */
public class SchMN extends SchMNAbstract {

	/**
	 * 
	 * Initialize the default Order and the DLT class
	 * @param c
	 */
	public SchMN(Cluster c) {
		super(c);
		_order=new OrderFIFO();  
		_dlt=new DivHomoSingle();
	}

	public boolean computeMN(Vector tasks, int curtime) {
		for (int i=0;i<tasks.size();i++) {
			Task t=(Task)tasks.elementAt(i);
			_dlt.computeCompletionTime(curtime, t, _sysv);
			if ((t.getNmin()<=0) || (t.getNmin()>_cluster.size)) 
				return false;			
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.SchEDFMNAbstract#assignMoreNodes(rtgrid.taskmodel.Task, rtgrid.resource.ANI, int)
	 */
	public int assignMoreNodes(Task t, ANI ani, int dispatchwait) {
		if (t.getNmin()<ani.num) {
			while ((t.getNmin()<ani.num) && t.isDeadlineMissed() )  {
				_dlt.computeCompletionTime(ani.time+dispatchwait, t.getNmin()+1, t, _sysv);	
			}
			if (t.isDeadlineMissed()) return 1;
		}
		else 
			return 1;
		return 0;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.SchEDFMNAbstract#recalculateTask(rtgrid.taskmodel.Task, java.util.Vector, int)
	 */
	public void recalculateTask(Task task, Vector ANList, int position) {
		return;
	}



	
}
