package rtgrid.taskmodel;
import java.util.Vector;

public class TaskList extends Object{
	public int time;
	public Vector tasks;
	
	public TaskList(int time) {
		this.time=time;
		tasks=new Vector();
	}
	
	public TaskList(TaskList tl) {
		this.time=tl.time;
		tasks=new Vector();
		Vector ts=tl.tasks;
		for (int i=0;i<ts.size();i++) {
			Task t=(Task)ts.elementAt(i);
			Task t_=new Task(t);
			tasks.add(t_);
		}
	}
	
	public Vector getTasks() {
		return tasks;
	}
	public void addTask(Task task) {
		tasks.add(task);
	}
	public void addHisTask(HisTask task) {
		tasks.add(task);
	}
}
