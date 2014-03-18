package rtgrid.test;

import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.resource.Cluster;
import rtgrid.scheduler.*;
import rtgrid.util.SimResult;
import rtgrid.util.Simulator;
import rtgrid.util.TasksGenerator;

public class TestUserReserve {

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
		
//		SimResult simrst;
//		configFile="E:\\Projects\\testcases\\config.sys";
//		inputFile="E:\\Projects\\testcases\\exp1-0.seq.split";
//		outputFile="E:\\Projects\\testcases\\output";
//		sim= new Simulator();
//		System.out.println("...Begin to read config file...");
//		sim.readPara(configFile);
//		
//		
//
//		NTList=sim.getInput(inputFile);
//	    Vector tmp=sim.genUserReserve(NTList);
//	    TasksGenerator tg=new TasksGenerator();
//	    tg.printList(tmp, outputFile);
//		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
//		sch=new SchEDFMN(cl);
//		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
//		System.out.println("edfmn: "+simrst.getRejectRate());
//		simrst.printLog(outputFile+"-edfmn.log");
//		
//		
//		NTList=sim.getInput(inputFile);
//		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
//		sch=new SchEDFMN_UMR(cl);
//		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
//		System.out.println("edfmn-umr: "+simrst.getRejectRate());
//		simrst.printLog(outputFile+"-edfmn-umr.log");
		
		
		
		SimResult simrst;
		configFile="E:\\Projects\\testcases\\config.sys";
		inputFile="E:\\Projects\\testcases\\UsrReserve.txt";
		outputFile="E:\\Projects\\testcases\\output";
		sim= new Simulator();
		System.out.println("...Begin to read config file...");
		sim.readPara(configFile);
		
		

		NTList=sim.getInput(inputFile);
	   cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
		sch=new SchMNEDF(cl);
		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		System.out.println("edfmn: "+simrst.getRejectRate());
		//simrst.printLog(outputFile+"-edfmn.log");
		
		
//		NTList=sim.getInput(inputFile);
//		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
//		sch=new SchUsrReserveEDF(cl);
//		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
//		System.out.println("usr-reserve: "+simrst.getRejectRate());
//		simrst.printLog(outputFile+"-usr-reserve.log");
	
	}

}
