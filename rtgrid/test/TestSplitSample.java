/**
 * 
 */
package rtgrid.test;

import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.resource.Cluster;
import rtgrid.scheduler.*;
import rtgrid.util.SimResult;
import rtgrid.util.Simulator;

/**
 * TestSplitSample.java
 * Package:  rtgrid.test
 * @author Xuan, 2006-11-30 
 */
public class TestSplitSample {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Simulator sim;
		Vector NTList;
		String configFile;
		String inputFile;
		String outputFile;
		IScheduler sch=null;
		Cluster cl=null;
		
		SimResult simrst;
		configFile="E:\\MyDocuments\\aaaProjects\\Testcases\\tmp\\config-1.sys";
		inputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\tmp\\exp1-0.seq";
		outputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\tmp\\cps5000";
		sim= new Simulator();
		System.out.println("...Begin to read config file...");
		sim.readPara(configFile);
		
		

		NTList=sim.getInput(inputFile);
		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
		sch=new SchMNEDF(cl);
		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		System.out.println("edfmn: "+simrst.getRejectRate());
		simrst.printLog(outputFile+"-edfmn.log");
		
		
//		NTList=sim.getInput(inputFile);
//		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
//		sch=new SchEDFMN_UMR(cl);
//		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
//		System.out.println("edfmn-umr: "+simrst.getRejectRate());
//		simrst.printLog(outputFile+"-edfmn-umr.log");
		
	
	}

}
