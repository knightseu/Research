/**
 * 
 */
package rtgrid.test;

import java.util.Vector;

import rtgrid.resource.Cluster;
import rtgrid.scheduler.*;
import rtgrid.taskmodel.Task;
import rtgrid.util.SimResult;
import rtgrid.util.Simulator;
import rtgrid.COMMON;
import junit.framework.TestCase;

/**
 * TestSchedule.java
 * Package:  rtgrid.test
 * @author Xuan, 2006-11-24 
 */
public class TestSchedule extends TestCase {
	Simulator sim;
	Vector NTList;
	String configFile;
	String inputFile;
	String outputFile;
	IScheduler sch=null;
	Cluster cl=null;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		
		
		
		
		
	}
	
	public void testOld() {
		SimResult simrst;
		configFile="E:\\MyDocuments\\aaaProjects\\Testcases\\systematic\\config-old.sys";
		inputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\systematic\\old.seq";
		outputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\systematic\\output-old";
		sim= new Simulator();
		System.out.println("...Begin to read config file...");
		sim.readPara(configFile);
		
		
		
		NTList=sim.getInput(inputFile);
		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
		sch=new SchMNEDF(cl);
		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		simrst.printLog(outputFile+"-edf.log");
		
		NTList=sim.getInput(inputFile);
		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
		sch=new SchMN(cl);
		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		simrst.printLog(outputFile+"-fifo.log");
		
		NTList=sim.getInput(inputFile);
		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
		sch=new SchMWF(cl);
		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		simrst.printLog(outputFile+"-mwf.log");
		
//		NTList=sim.getInput(inputFile);
//		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
//		sch=new SchEDFMN_UMR(cl);
//		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
//		simrst.printLog(outputFile+"-edfumr.log");
//		
//		NTList=sim.getInput(inputFile);
//		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
//		sch=new SchEDFMN_UMR_IE(cl);
//		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
//		simrst.printLog(outputFile+"-edfumrie.log");
	}
	
	public void testOld2() {
		SimResult simrst;
		configFile="E:\\MyDocuments\\aaaProjects\\Testcases\\systematic\\config-old.sys";
		inputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\systematic\\old2.seq";
		outputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\systematic\\output-old2";
		sim= new Simulator();
		System.out.println("...Begin to read config file...");
		sim.readPara(configFile);
		
		

		NTList=sim.getInput(inputFile);
		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
		sch=new SchMWF(cl);
		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		simrst.printLog(outputFile+"-mwf.log");
		
	}
	

}
