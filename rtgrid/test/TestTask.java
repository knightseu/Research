/**
 * 
 */
package rtgrid.test;

import rtgrid.taskmodel.Task;
import rtgrid.COMMON;
import junit.framework.*;

/**
 * TestTask.java
 * Package:  rtgrid.test
 * @author Xuan, 2006-11-5 
 */

public class TestTask extends TestCase {
	Task task;
	
	public TestTask(String name) {
		super(name);
	}
	
	public static void main(String[] args) {

        junit.textui.TestRunner.run(

            TestTask.class);

    }
	


	protected void setUp() throws Exception {
		super.setUp();
		task= new Task(0, 0, 1000, 100);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'Task.computeNmin(int)'
	 */
	public void testComputeNmin() {
		Task task= new Task(0, 0, 335, 100);
		Assert.assertEquals(4,task.computeNmin(0,COMMON.ROUND_SINGLE));
		Assert.assertEquals(3,task.computeNmin(0,COMMON.ROUND_UMR));
		task= new Task(0, 0, 50, 125);
		COMMON.PARA_CLUSTERSIZE=64;
		Assert.assertEquals(50,task.computeNmin(0,COMMON.ROUND_UMR));
		task= new Task(0, 0, 10, 125);
		COMMON.PARA_CLUSTERSIZE=16;
		Assert.assertEquals(-1,task.computeNmin(0,COMMON.ROUND_UMR));
	}
	
	

	/*
	 * Test method for 'Task.computeCompletionTime(int)'
	 */
	public void testComputeByNodes() {
		Task task= new Task(0, 0, 3350, 125);
		task.computeByNodes(0, 3, COMMON.ROUND_UMR);
		Assert.assertEquals(419, task.getCompletionTime());
		task.computeByNodes(0, 7, COMMON.ROUND_UMR);
		Assert.assertEquals(183, task.getCompletionTime());
        //Assert.assertEquals(577,task.computeCompletionTime(task.computeNmin(0,COMMON.ROUND_SINGLE)));
	}

	
	
	
	/***************************************************************
	 * 
	 * following test for setup cost=0
	 * 
	 */
	
	
	
	/*
	 * Test method for 'Task.computeNminSU(int)'
	 */
	public void testComputeNminSU() {
		Assert.assertEquals(2
				,task.computeNmin(0,COMMON.ROUND_SINGLE));
		
	}
	
	

	/*
	 * Test method for 'Task.computeCompletionTime(int)'
	 */
	public void testComputeCompletionTimeSU() {
		
//        Assert.assertEquals(577,task.computeCompletionTime(task.computeNmin(0,COMMON.ROUND_SINGLE)));
	}
	
	public void testComputeCoompletionTimeHeuristic() {
//		int SCH_FIFO_MN_SIMPLE_ACT=3;
//		Task t=new Task(0,0,6669,67);
//		t.Cms=6;
//		t.Cps=100;
//		t.computeCompletionTime(2,SCH_FIFO_MN_SIMPLE_ACT);
//		Assert.assertEquals(3752,t.compTime);
	
	}

	/*
	 * Test method for 'Task.computeAN(int,int)'
	 */
	public void testComputeANSU() {
//		task.computeAN(0,2);
//		Assert.assertEquals(2,task.Nmin);
//		//Assert.assertEquals(54,task.DC);
//		Assert.assertEquals(577,task.compTime);
//		
//		
//		task.arrTime=10;
//		task.startTime=10;
//		task.computeAN(0,2);
//		Assert.assertEquals(2,task.Nmin);
//		//Assert.assertEquals(54,task.DC);
//		Assert.assertEquals(577,task.compTime);
	}


	
	/**
	 * see /home/grad/lxuan/rtgrid\simulation\testcases\testcase041006.xls
	 *
	 */
//	public void testSimpleHeuristic() {
//		int type=2;// SCH_FIFO_MN_SIMPLE
//		Task t=new Task(0,0,500,100);
//		Assert.assertEquals(3,t.computeMinNodes(0,type));
//	    Assert.assertEquals(367,t.computeCompletionTime(t.getNmin(),type));
//	    
//	    t=new Task(0,0,500,100);
//		Assert.assertEquals(3,t.computeMinNodes(10,type));
//	    Assert.assertEquals(377,t.computeCompletionTime(t.getNmin(),type));
//	    
//	    t=new Task(0,0,500,100);
//		Assert.assertEquals(4,t.computeMinNodes(200,type));
//	    Assert.assertEquals(475,t.computeCompletionTime(t.getNmin(),type));
//	    
//	    t=new Task(0,0,500,100);
//	    t.Cms=5;
//		Assert.assertEquals(5,t.computeMinNodes(200,type));
//	    Assert.assertEquals(500,t.computeCompletionTime(t.getNmin(),type));
//	    
//	}
//	
	
	/*
	public void testPoisson(){
		Poisson poisson= new Poisson("poisson",100,12345);
		for (int i=0;i<15;i++)
			System.out.println(poisson.sample());
		
	}
	*/
}
