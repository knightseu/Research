/**
 * 
 */
package rtgrid;

/**
 * COMMON.java
 * Package:  rtgrid
 * @author Xuan, 2006-10-17 
 * 
 * The Class store the common variables.
 */
public final class COMMON {
	public static final String RESOURCE_CLUSTER="cluster";
	public static final String RESOURCE_PNODE="processing node";
	
	
	
	
//	Control Knob
	public static boolean DEBUG=false;
	public static final boolean DEBUG_NOWAIT=false;
	
	public static final boolean PRINT_LOG_RT=false; //	print schedule real-time
	public static boolean CLUSTER_INFO=false; //test log

	public static boolean LOG_PRINT_ARRIVE=true; //when print log, whether the arrive info is printed
	public static boolean LOG_PRINT_REJECT=true;//when print log, whether the reject info is printed
	public static boolean LOG_PRINT_ADMIT=true;//when print log, whether the admit info is printed
	public static boolean LOG_PRINT_DISPATCH=true;//when print log, whether the dispatch info is printed
	public static boolean LOG_PRINT_DEADLINEMISS=true;//when print log, whether the deadlinemiss info is printed
	
	public static boolean PRINT_SCHEDULER_NAME=false;  //print the name of the scheduler when it is initialized.
	
	//System Parameters
	//public static int PARA_DISPATCHTIME=0;
	//public static int PARA_DISPATCHTIME_ACT=0;
	public static int PARA_PLATFORM=0; // 0-window, 1-linux/unix
	public static int PARA_SIMULATION_TIME=1000000;
	public static int PARA_CLUSTERSIZE=16; //size of the system
	public static String PARA_OUTPUT_DIR=".\\";
	public static int PARA_EXP_ITERATION=10;
	public static String PARA_INPUT_FILENAME="";
	public static String PARA_LATEX_FILENAME="";
	public static int PARA_CMS=1;
	public static int PARA_CPS=10;
    public static int PARA_ST=0;
    public static int PARA_SC=0;
	public static int PARA_CDRATIO=10;
	public static int PARA_DATASIZE=200;
	public static String PARA_SEQ_NAME="D:\\";
	
	
	//Constant
	public static final String STR_SYSTEM_SIZE="systemsize";
	public static final String STR_OUTPUT_DIRECTORY="output";
	public static final String STR_ITERATION="iteration";
	public static final String STR_INPUT_FILENAME="input";
	public static final String STR_LATEX="latex";
	public static final String STR_CMS="cms";
	public static final String STR_CPS="cps";
	public static final String STR_ST="st";
	public static final String STR_SC="sc";
	public static final String STR_SIMULATION_TIME="simulationtime";
	public static final String STR_CDRATIO="cdratio";
	public static final String STR_DATASIZE="datasize";
	public static final String STR_SEQ_NAME="seqname";
	public static final String STR_PLATFORM="platform";
	
	
	//Flags
	public static final int TASK_ARRIVE=0;
	public static final int TASK_REJECT=1;
	public static final int TASK_DEADLINEMISS=2;
	public static final int TASK_ADMIT=3;
	public static final int TASK_DISPATCH=4;
	public static final int TASK_SCHEDULABLE=5;
	
	
	////Flags for different types of scheduling algorithm
	/**
	 * 
	 * SCH_Order_Nodes_Partition_Round_Other
	 * 
	 * Order: FIFO, EDF,MWF
	 * 
	 * Nodes: AN, MN, K# 
	 * 
	 * Partition: OPR,HEU       
	 * 
	 * Round: Single, UMR
	 *
	 * 
	 * Other: [optional]
	 * 
	 */
	public static final int SCH_FIFO_AN_OPR_Single=1;  //originally SCH_FIFO_AN
	public static final int SCH_FIFO_AN_OPR_UMR=3; 
	public static final int SCH_FIFO_MN_OPR_Single=5; 
	public static final int SCH_FIFO_MN_OPR_UMR=7; 
	
	
	public static final int SCH_EDF_AN_OPR_Single=9;  //originally SCH_EDF_AN
	public static final int SCH_EDF_AN_OPR_UMR=11; 
	public static final int SCH_EDF_MN_OPR_Single=13; //SCH_EDF_MN
	public static final int SCH_EDF_MN_OPR_UMR=15;
	public static final int SCH_EDF_MN_OPR_UMR_IE=16; // with Inserted-Idle-Time Eliminated
	

	public static final int SCH_MWF_MN_OPR_Single=17; // 
	public static final int SCH_MWF_MN_OPR_UMR=19; 
	
	
	public static final int SCH_FIFO_MN_SIMPLE=112;
	public static final int SCH_FIFO_MN_SIMPLE_ACT=113;
	public static final int SCH_FIFO_MN_SIMPLE2=114; // assign Nmin of 2's power
	public static final int SCH_FIFO_MN_SIMPLE2_ACT=115;
	public static final int SCH_FIFO_MN_DLT_SINGLE=116;
	public static final int SCH_FIFO_MN_DLT_MULTI=117;
	public static final int SCH_EDF_AN=110;
	public static final int SCH_EDF_AN_SIMPLEHEU1=121;
	public static final int SCH_EDF_AN_SIMPLEHEU1_ACT=122;
	public static final int SCH_EDF_AN_SIMPLEHEU3=123;
	public static final int SCH_EDF_AN_SIMPLEHEU3_ACT=124;
	//Space for all nodes algorithm
	public static final int SCH_EDF_MN=125;
	public static final int SCH_EDF_MN_SIMPLE3=126;
	public static final int SCH_MCDF=130;
	public static final int SCH_MCDF_COMPARE=131;// when there are setup cost and assume there are no
	public static final int SCH_USERSPLIT=240;
	
	
	//Flag for compute Nmin using Single-round or Multiround
	public static final int ROUND_SINGLE=1;
	public static final int ROUND_UMR=3;
	
	
	//Flag 
	public static final int SCH_USR_RESERVE=1000;

}
