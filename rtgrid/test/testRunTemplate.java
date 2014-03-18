/**
 * 
 */
package rtgrid.test;

import rtgrid.COMMON;
import rtgrid.resource.Cluster;
import rtgrid.scheduler.SchMNEDF;
import rtgrid.util.RunTemplate;
import rtgrid.util.Simulator;

/**
 * testRunTemplate.java
 * Package:  rtgrid.test
 * @author lxuan, 2007-4-14 
 * 
 * 
 * 
   configfile=""; 
   prefix="exp-single-sc";
	  surfix="SchMNMWFSC";
		sim=new Simulator();		
		sim.readPara(configfile);
		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);	
		sch=new SchMNMWF_SC(cl);
 */
public class testRunTemplate extends RunTemplate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		   configfile="/home/lxuan/work/experiments/config.sys"; 
		   prefix="sc";
			 surfix="SchMNEDF";
				sim=new Simulator();		
				sim.readPara(configfile);
				cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);	
				sch=new SchMNEDF(cl);
				//System.out.println(sch.getClass().getName());
				Run(configfile,sch.getClass().getName());

	}

}
