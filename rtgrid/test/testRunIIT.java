/**
 * 
 */
package rtgrid.test;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.resource.Cluster;
import rtgrid.scheduler.IScheduler;
import rtgrid.scheduler.SchMNEDF_IIT;
import rtgrid.scheduler.SchMN_IIT;
import rtgrid.util.SimResult;
import rtgrid.util.SimResults;
import rtgrid.util.Simulator;

/**
 * testRunIIT.java
 * Package:  rtgrid.test
 * @author lxuan, 2007-5-1 
 */
public class testRunIIT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Simulator sim;
		Vector NTList;
		String configFile;
		String inputFile;
		String outputFile;
		//IScheduler sch=null;
		Cluster cl=null;
		

		
		
		
		SimResult simrst;
		configFile="/home/lxuan/work/experiments/data/16-2-200-cm8/config.sys";
		inputFile="/home/lxuan/work/experiments/data/16-2-200-cm8/exp5-1.seq";
//		configFile="/home/lxuan/work/experiments/config.sys";
//		inputFile="/home/lxuan/work/experiments/data/16-2-200/exp8-0.seq";
		//outputFile="E:\\Projects\\testcases\\output";
		sim= new Simulator();
		System.out.println("...Begin to read config file...");
		sim.readPara(configFile);
		
		

		NTList=sim.getInput(inputFile);
	   cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
	   SchMN_IIT sch=new SchMN_IIT(cl);
		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		System.out.println("reject ratio: "+simrst.getRejectRate());
		System.out.println("total tasks: "+simrst.getTotalNum());
		System.out.println("iits: "+sch.iitnum);
		System.out.println("opt-iits: "+sch.optiitnum);
		//simrst.printLog(outputFile+"-edfmn.log");
		
		
	}
}
