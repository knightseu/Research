/**
 *   RunTemplate.java
 *   
 *   Serve as a template to write an expriment  driver
 *                    
 *   Extend this class, change the function init, but each statement in "init" should exist but could give a differnet value.
 */
package rtgrid.util;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.resource.Cluster;
import rtgrid.scheduler.IScheduler;

/**
 * RunTemplate.java
 * Package:  rtgrid.exp
 * @author lxuan, 2007-4-11 
 */
public class RunTemplate {
	public static String configfile="";  //configuration file	
	public static String prefix=""; // prefix for output file name 
	public static String surfix=""; // surfix for output file name
	public static String inputfile=""; // inputfile name
	public static 	String seperator=""; // seperator for diretory

	public static IScheduler sch=null;

	public static Simulator sim;
	public static Cluster cl;
	public static Vector NTList;//new tasks Lists

	public static SimResults simrsts;



	public static FileOutputStream out_rej,out_details; // declare a file output object
	public static PrintStream p_rej,p_details; // declare a print stream object

	public static void init(String configfile) {
		 prefix="exp-single-sc";
	  surfix="SchMNMWFSC";
		sim=new Simulator();		
	
	}

	public static final void Run(String configfile, String classname) {

		//sim.readPara(configfile);
		switch (COMMON.PARA_PLATFORM) {
		case 1://unix
			seperator="/";
			break;
		case 0://window
			seperator="\\";
			break;
		}
		
		simrsts=new SimResults(COMMON.PARA_EXP_ITERATION);

		try {

			out_rej = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+prefix+"-"+surfix+".rst");
			p_rej = new PrintStream( out_rej );
	
			out_details = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+prefix+"-"+surfix+"-details.rst");
			p_details = new PrintStream(out_details);

			p_rej.println("# sysload, rej, que, rtime, wtime, adworkload, miss, avgNodes");
			for (int j=1;j<11;j+=1) {
				System.out.print(1.0*j/10+"		");
				p_rej.print(1.0*j/10+"		");
	
				p_details.println(1.0*j/10+"		");

				for (int k=0;k<COMMON.PARA_EXP_ITERATION;k++) {
					inputfile=COMMON.PARA_INPUT_FILENAME+j+"-"+k+".seq";

					//read tasks sequence
					NTList=sim.getFromFile(inputfile,COMMON.PARA_ST,COMMON.PARA_SC);					
                   
					cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);	
					
					//use reflection to dynamically create class
					Class classType=Class.forName(classname);
					Class[] types=new Class[] {Cluster.class};
					Constructor cons=classType.getConstructor(types);
					Object[] args= new Object[] {cl};
					sch=(IScheduler)cons.newInstance(args);
					//sch=new SchMNMWF_SC(cl);
					//System.out.print("::"+inputfile);
					//run the simulator
					simrsts.results[k]=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
					p_details.print(simrsts.results[k].getRejectRate()+ "		");
					System.out.print(simrsts.results[k].getRejectRate()+ "		");
					

				}// end of k

				
				System.out.print("("+simrsts.getRejectRate()+")	");
				
				p_rej.print(simrsts.getRejectRate()+"	"+simrsts.getAvgQueueLen()+"	"+simrsts.getAvgResponseTime()+"	"
						+simrsts.getAvgWaitingTime()+"		"+simrsts.getAdWorkloadRatio()+"		"+simrsts.getMissRatio()+"		"
						+simrsts.getAvgNodes()+"		");

				System.out.println();
				p_rej.println();
			
				p_details.println();
			}//end of j
			p_rej.close();

			p_details.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}// end of Run



}
