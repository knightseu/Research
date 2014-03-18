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
import rtgrid.util.RunTemplate;
import rtgrid.util.SimResults;
import rtgrid.util.Simulator;

/**
 * runSingleWS.java
 * Package:  rtgrid.exp
 * @author lxuan, 2007-4-11 
 * 
 * run Single With Setup 
 */
public class runSC_MNEDF extends RunTemplate {
	public static void main(String[] args) {
		if (args.length>0)
			configfile=args[0];
		else
			configfile="config.sys";		
		//configfile="/home/lxuan/work/experiments/config.sys"; 



		prefix="sc";
		surfix="SchMNEDF";
		sim=new Simulator();		
		sim.readPara(configfile);
		surfix+="-"+COMMON.PARA_SC;
		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);	
		sch=new SchMNEDF_SC(cl);
		//System.out.println(sch.getClass().getName());
		Run(configfile,sch.getClass().getName());
	}
	
}