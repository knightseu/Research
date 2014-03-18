/**
 * 
 */
package rtgrid.test;
import java.util.Vector;

import junit.framework.TestCase;

import rtgrid.COMMON;
import rtgrid.resource.*;
import rtgrid.scheduler.*;
import rtgrid.util.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TestSchedulers.java
 * Package:  rtgrid.test
 * @author Xuan, 2006-11-11 
 */
public class TestSchedulers extends TestCase {
	boolean bGenLog;
	Vector NTList;
	IScheduler sch;
	Simulator sim;
	Cluster cl;
	/**
	 * @throws java.lang.Exception
	 */
	//@Before
	public void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	//@After
	public void tearDown() throws Exception {
	}

	public void testSchEDFMN() {

		bGenLog=true;
		Simulator sim=new Simulator();
        sim.readPara("config\\config.sys");
		NTList=sim.getFromFile("config\\testdata.txt");		
        cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
        
		sch= new SchMNEDF(cl);
		
		SimResult simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		if (bGenLog)
		    	simrst.printLog("config\\testrst\\testdata-schedfmn.log");
		fail("'compare log - testrst\\testdata-schedfmn.log' WITH 'config\\testdata-edfmn.log'" );
	}
	
	public void testSchEDFMN2() {

		bGenLog=true;
		Simulator sim=new Simulator();
        sim.readPara("config\\config2.sys");
		NTList=sim.getFromFile("config\\newtestcase.seq");		
        cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
        
		sch= new SchMNEDF(cl);
		
		SimResult simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		if (bGenLog)
		    	simrst.printLog("config\\testrst\\newtestcase-schedfmn.log");
		fail("'compare log - testrst\\newtestcase-schedfmn.log' WITH 'config\\newtestcase-edfmn.log'" );
	}

	public void testSchEDFAN() {
		bGenLog=true;
		Simulator sim=new Simulator();
        sim.readPara("config\\config.sys");
		NTList=sim.getFromFile("config\\an-dispatch.TXT");		
        cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
		sch= new SchEDFAN(cl);
		
		SimResult simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		if (bGenLog)
		    	simrst.printLog("config\\testrst\\an-dispatch-schedfan.log");
	}
	
	

}
