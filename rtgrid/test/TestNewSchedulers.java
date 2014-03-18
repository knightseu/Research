/**
 * 
 */
package rtgrid.test;

import java.util.Vector;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rtgrid.COMMON;
import rtgrid.resource.Cluster;
import rtgrid.scheduler.IScheduler;
import rtgrid.scheduler.SchMNEDF;
import rtgrid.scheduler.SchMWF;
import rtgrid.util.SimResult;
import rtgrid.util.Simulator;

/**
 * TestSchMNEDF.java
 * Package:  rtgrid.test
 * @author Xuan, 2007-1-2 
 */
public class TestNewSchedulers extends TestCase{
    boolean bGenLog;
    Vector NTList;
    Cluster cl;
    IScheduler sch;
	/**
	 * @throws java.lang.Exception
	 */
//	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	//@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link rtgrid.scheduler.Scheduler#run(int, java.util.Vector)}.
	 */
	//@Test
	public void testSchMNEDF() {
	    bGenLog=true;
		Simulator sim=new Simulator();
        sim.readPara("config\\config.sys");
		NTList=sim.getFromFile("config\\testdata.txt");		
        cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
        
		sch= new SchMNEDF(cl);
		
		SimResult simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		if (bGenLog)
		    	simrst.printLog("config\\testrst\\testdata-schmnedf.log");
		fail("'compare log - testrst\\testdata-schedfmn.log' WITH 'testrst\\testdata-schmnedf.log'" );
	}

	public void testSchMNEDF2() {

		bGenLog=true;
		Simulator sim=new Simulator();
        sim.readPara("config\\config2.sys");
		NTList=sim.getFromFile("config\\newtestcase.seq");		
        cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
        
		sch= new SchMNEDF(cl);
		
		SimResult simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		if (bGenLog)
		    	simrst.printLog("config\\testrst\\newtestcase-schmnedf.log");
		fail("'compare log - testrst\\newtestcase-schedfmn.log' WITH 'testrst\\newtestcase-schmnedf.log'" );
	}
	
	public void testOld() {
		String configFile,inputFile,outputFile;
		Simulator sim;
		SimResult simrst;
		configFile="E:\\MyDocuments\\aaaProjects\\Testcases\\systematic\\config-old.sys";
		inputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\systematic\\old.seq";
		outputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\systematic\\output-old";
		sim= new Simulator();
		System.out.println("...Begin to read config file...");
		sim.readPara(configFile);
		
		
		
		NTList=sim.getInput(inputFile);
		cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
		sch=new SchMNEDF(cl);
		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		simrst.printLog(outputFile+"-newedf.log");
		
	
	}
	
}
