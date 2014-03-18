/**
 * 
 */
package rtgrid.exp;

import rtgrid.COMMON;
import rtgrid.resource.Cluster;
import rtgrid.scheduler.*;
import rtgrid.util.RunTemplate;
import rtgrid.util.Simulator;

/**
 * runSC_ANEDF.java
 * Package:  rtgrid.exp
 * @author lxuan, 2007-4-16 
 */
public class runSC_ANEDF extends RunTemplate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length>0)
			configfile=args[0];
		else
			configfile="config.sys";		
		//configfile="/home/lxuan/work/experiments/config.sys"; 



		prefix="sc";
		surfix="SchANEDF";
		sim=new Simulator();		
		sim.readPara(configfile);
		surfix+="-"+COMMON.PARA_SC;
		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);	
		sch=new SchANEDF_SC(cl);
		//System.out.println(sch.getClass().getName());
		Run(configfile,sch.getClass().getName());

	}

}
