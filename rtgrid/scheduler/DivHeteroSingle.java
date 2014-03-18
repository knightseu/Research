/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.taskmodel.Task;
import rtgrid.util.SysVector;

/**
 * DivHeteroSingle.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2006-12-31 
 * 
 * DLT class for Heterogeneous system, Single Round, No setup.
 * 
 */
public class DivHeteroSingle implements IDivisible {

	/**
	 * 
	 */
	public DivHeteroSingle() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCompletionTime(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCompletionTime(int curTime, Task task, SysVector sysv) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCompletionTime(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCompletionTime(int curTime, int nodes, Task task,
			SysVector sysv) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCost(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCost(int nodes, Task task, SysVector sysv) {
		 int size=sysv.getSize();
			double [] X =new double[size];
			double [] alpha =new double [size];
			int [] data = new int[size];
			//int [] datasize;
			
			
			//calculate X 
			
			X[0]=0;
			for (int i=1;i<size;i++) {
				X[i]=1.0*sysv.getCps(i-1)/(sysv.getCms()+sysv.getCps(i));
			}
			
			///////////////////////calculate alpha
			double tmp=0;
			for (int i=1;i<size;i++) {
				double tmp2=1;
				for (int j=1;j<=i;j++) {
				   tmp2*=X[j];
				}
				tmp+=tmp2;
			}
			
			
			alpha[0]=1.0/(1+tmp); 
			data[0]=(int)Math.ceil(alpha[0]*task.datasize);
			int totaldata=data[0];
			for (int i=1;i<size;i++) {
				alpha[i]=X[i]*alpha[i-1];
				
				if (i<(size-1)) {
					data[i]=(int)Math.ceil(alpha[i]*task.datasize);
					totaldata+=data[i];
				}
				else 
					data[i]=task.datasize-totaldata;
			}
			
			//////////////take care of the dispatchdone time
			totaldata=data[size-1];
			for(int i=(size-2); i>=0;i--) {
				if (sysv.getCps(i)==sysv.getCps(size-1))
					totaldata+=data[i];
				else 
					break;
			}
			task.dispDoneTime=sysv.getCms()*totaldata+task.startTime;
			
	        
//			System.out.println("i, X, alpha,datasize");
//			for (int i=0;i<size;i++) {
//		        System.out.println(""+(i+1)+", "+X[i]+", "+alpha[i]+", "+data[i]);  
//			}
			//System.out.println((int) Math.ceil( alpha[0]*task.datasize*(sysv.getCms()+sysv.getCps()) ));
			
			return (int) Math.ceil( alpha[0]*task.datasize*(sysv.getCms()+sysv.getCps()) );
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeDatasize(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeDatasize(int duration, int nodes, Task task, SysVector sys) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeNmin(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeNmin(int curTime, Task task, SysVector sysv) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeDC(rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public double computeDC(Task task, SysVector sysv) {
		// TODO Auto-generated method stub
		return 0;
	}

}
