/**
 * 
 */
package rtgrid.exp;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.resource.Cluster;
import rtgrid.scheduler.*;
import rtgrid.util.SimResults;
import rtgrid.util.Simulator;

/**
 * runUMRIE.java
 * Package:  rtgrid.exp
 * @author Xuan, 2006-12-3 
 * 
 * UMR no setup cost
 * 
 */
public class runUMRIE {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String configFile="";
		if (args.length>0)
			configFile=args[0];
		else
			configFile="config.sys";		
		Run(configFile);
	}
	
	
	public static void Run(String configFile) {
//		read configuration
		Simulator sim=new Simulator();
		sim.readPara(configFile);
		
		boolean run_sim=true;
		if (run_sim) {
			Vector NTList;//new tasks Lists
			IScheduler sch=null;
			SimResults simrsts=new SimResults(COMMON.PARA_EXP_ITERATION);
			String inputfile="";
			FileOutputStream out_rej,out_que,out_rtime,out_wtime,out_adworkload,out_miss,out_nmin,out_details; // declare a file output object
			PrintStream p_rej,p_que,p_rtime,p_wtime,p_adworkload,p_miss,p_nmin,p_details; // declare a print stream object
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
				out_rej = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-UMR-IE-rej.rst");
				p_rej = new PrintStream( out_rej );
				out_que = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-UMR-IE-que.rst");
				p_que = new PrintStream( out_que );
				out_rtime = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-UMR-IE-rtime.rst");
				p_rtime = new PrintStream( out_rtime );
				out_wtime = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-UMR-IE-wtime.rst");
				p_wtime = new PrintStream( out_wtime );
				out_adworkload = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-UMR-IE-adworkload.rst");
				p_adworkload = new PrintStream( out_adworkload );
				out_miss = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-UMR-IE-miss.rst");
				p_miss = new PrintStream( out_miss );
				out_nmin = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-UMR-IE-nmin.rst");
				p_nmin = new PrintStream( out_nmin );
				out_details = new FileOutputStream(COMMON.PARA_OUTPUT_DIR+seperator+"exp-UMR-IE-rej-mn-details.rst");
				p_details = new PrintStream(out_details);
				
				for (int j=1;j<11;j+=1) {
					System.out.print(1.0*j/10+"		");
					p_rej.print(1.0*j/10+"		");
					p_que.print(1.0*j/10+"		");
					p_rtime.print(1.0*j/10+"		");
					p_wtime.print(1.0*j/10+"		");
					p_adworkload.print(1.0*j/10+"		");
					p_miss.print(1.0*j/10+"		");
					p_nmin.print(1.0*j/10+"		");
					p_details.println(1.0*j/10+"		");
					for (int i=0;i<3;i++){
				
						for (int k=0;k<COMMON.PARA_EXP_ITERATION;k++) {
							inputfile=COMMON.PARA_INPUT_FILENAME+j+"-"+k+".seq";
							Cluster cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);	
							
//							switch (i) {
//							case 0 :
//								sch=new SchFIFOMN_UMR_IE(cl);
//								break;
//							case 1 :
//								sch=new SchEDFMN_UMR_IE(cl);
//								break;
//							case 2:
//								sch=new SchMWF_UMR_IE(cl);								
//								break;	
//							}
							
							//read tasks sequence
							NTList=sim.getFromFile(inputfile);					
								
							//run the simulator
							simrsts.results[k]=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
										
						}// end of k
						
						System.out.print(simrsts.getRejectRate()+"	");
				
						p_rej.print(simrsts.getRejectRate()+"	");
						p_que.print(simrsts.getAvgQueueLen()+"	");
						p_rtime.print(simrsts.getAvgResponseTime()+"	");
						p_wtime.print(simrsts.getAvgWaitingTime()+"		");
						p_adworkload.print(simrsts.getAdWorkloadRatio()+"		");
						p_miss.print(simrsts.getMissRatio()+"		");
						p_nmin.print(simrsts.getAvgNodes()+"		");
						
					}// end of i
					System.out.println();
					p_rej.println();
					p_que.println();
					p_rtime.println();
					p_wtime.println();
					p_adworkload.println();
					p_miss.println();
					p_nmin.println();
					p_details.println();
				}//end of j
				p_rej.close();
				p_que.close();
				p_rtime.close();
				p_wtime.close();
				p_adworkload.close();
				p_miss.close();
				p_details.close();
			}catch(Exception e) {
				System.out.println(e);
			}
		}

	}

}
