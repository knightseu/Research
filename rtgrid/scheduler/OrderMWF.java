package rtgrid.scheduler;

import java.util.Vector;

import rtgrid.taskmodel.Task;

public class OrderMWF implements IOrder {

  public Vector order(Vector tasks) {
	  Vector v=new Vector();
		for (int i=0;i<tasks.size();i++) {
			Task t1=(Task)tasks.elementAt(i);
			if (v.isEmpty()) {
				v.add(t1);
				//break;
			}
			else
				for (int j=0;j<v.size();j++) {
					Task t2=(Task)v.elementAt(j);
					if (t1.getDC()<=t2.getDC()) {
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