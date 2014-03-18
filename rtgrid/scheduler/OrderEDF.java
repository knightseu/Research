/**
 * 
 */
package rtgrid.scheduler;

import java.util.Vector;

import rtgrid.taskmodel.Task;

/**
 * OrderEDF.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2006-12-16 
 */
class OrderEDF implements IOrder {

	/**
	 * 
	 */
	public OrderEDF() {
		
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IOrder#order(java.util.Vector)
	 */
	public Vector order(Vector tasks) {
		Vector v=new Vector();
    	for (int i=0;i<tasks.size();i++) {
    		Task t1=(Task)tasks.elementAt(i);
    		if (v.isEmpty()) {
    			v.add(t1);
    		}
    		else
    		for (int j=0;j<v.size();j++) {
    			Task t2=(Task)v.elementAt(j);
    			if (t1.getAbsoluteDeadline()>=t2.getAbsoluteDeadline()) {
					 if (j==v.size()-1) {
						 v.add(j+1,t1);
						 break;
					 }
					 else continue;
				 }
			     v.add(j,t1);
			     break;
    		}
    	}
    	return v;
	}

}
