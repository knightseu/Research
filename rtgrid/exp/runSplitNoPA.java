/**
 * 
 */
package rtgrid.exp;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import junit.framework.TestCase;

import rtgrid.COMMON;
import rtgrid.resource.Cluster;
import rtgrid.scheduler.IScheduler;
import rtgrid.scheduler.SchSplitNoPA;
//import rtgrid.scheduler.SchUsrReserveEDF;
import rtgrid.util.SimResults;
import rtgrid.util.Simulator;

/**
 * runReserve.java
 * Package:  rtgrid.exp
 * @author Xuan, 2007-1-9 
 */
public class runSplitNoPA {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String configFile="";
		boolean bAllNode=false; //whether we split the task to all nodes or Nmin is just a random number. 
		if (args.length>0) {
			configFile=args[0];
			try { //see whether we split the task to all nodes or just random number of nodes.
				int tmp=Integer.parseInt(args[1]);
				if (tmp!=0)
					bAllNode=true;
			}
			catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		else
			configFile="config.sys";	
		
		Simulator sim=new Simulator();
		sim.readPara(configFile);
		
		boolean run_sim=true;
		if (run_sim) {
			Vector NTList=null;//new tasks Lists
			IScheduler sch=null;
			SimResults simrsts=new SimResults(COMMON.PARA_EXP_ITERATION);
			String inputfile="";
			String surfix="";
			FileOutputStream out_rej,out_que,out_rtime,out_wtime,out_adworkload,out_miss,out_nmin; // declare a file output object
			PrintStream p_rej,p_que,p_rtime,p_wtime,p_adworkload,p_miss,p_nmin; // declare a print stream object
			try {
				String seperator="";
				switch (COMMON.PARA_PLATFORM) {
				case 1://unix
					seperator="/";
					break;
				case 0://window
					seperator="\\";
					break;
				}
				if (bAllNode)
					surfix="-splitnopa-an.rst";
				else
					surfix="-splitnopa.rst";
				out_rej = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-rej"+surfix);
				p_rej = new PrintStream( out_rej );
				out_que = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-que"+surfix);
				p_que = new PrintStream( out_que );
				out_rtime = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-rtime"+surfix);
				p_rtime = new PrintStream( out_rtime );
				out_wtime = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-wtime"+surfix);
				p_wtime = new PrintStream( out_wtime );
				out_adworkload = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-adworkload"+surfix);
				p_adworkload = new PrintStream( out_adworkload );
				out_miss = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-miss"+surfix);
				p_miss = new PrintStream( out_miss );
				out_nmin = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-nmin"+surfix);
				p_nmin = new PrintStream( out_nmin );
				
				for (int j=1;j<11;j+=1) {
					System.out.print(1.0*j/10+"		");
					p_rej.print(1.0*j/10+"		");
					p_que.print(1.0*j/10+"		");
					p_rtime.print(1.0*j/10+"		");
					p_wtime.print(1.0*j/10+"		");
					p_adworkload.print(1.0*j/10+"		");
					p_miss.print(1.0*j/10+"		");
					p_nmin.print(1.0*j/10+"		");
					
					for (int k=0;k<COMMON.PARA_EXP_ITERATION;k++) {
						inputfile=COMMON.PARA_INPUT_FILENAME+j+"-"+k+".seq";
//						init cluster
						Cluster cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
						
						sch=new SchSplitNoPA(cl);
						
		                Vector tmpVector;
						if (bAllNode)
							NTList=sim.getFromFile(inputfile+".splitan");
						else 
							NTList=sim.getFromFile(inputfile+".split");
						
						//NTList=sim.genUserReserve(tmpVector);
						//run the simulator
						simrsts.results[k]=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
						
					}// end of k
					
					System.out.print(simrsts.getRejectRate()+"	");
					
					
					p_rej.print(simrsts.getSplitRejRatio()+"	");
					p_rej.print(simrsts.getRejectRate()+"	");
					
					//p_rej.print(simrsts.getRejectRate()+"	");
					p_que.print(simrsts.getAvgQueueLen()+"	");
					p_rtime.print(simrsts.getAvgResponseTime()+"	");
					p_wtime.print(simrsts.getAvgWaitingTime()+"		");
					p_adworkload.print(simrsts.getAdWorkloadRatio()+"		");
					p_miss.print(simrsts.getMissRatio()+"		");
					p_nmin.print(simrsts.getAvgNodes()+"		");
					System.out.println();

					p_rej.println();
					p_que.println();
					p_rtime.println();
					p_wtime.println();
					p_adworkload.println();
					p_miss.println();
					p_nmin.println();
				}//end of j
				p_rej.close();
				p_que.close();
				p_rtime.close();
				p_wtime.close();
				p_adworkload.close();
				p_miss.close();
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		
	}
}
