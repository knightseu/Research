package rtgrid.util;
import java.io.*;
import java.util.Random;
import java.util.Vector;

import rtgrid.scheduler.*;
import rtgrid.taskmodel.*;
import rtgrid.COMMON;

public class Simulator {
	//int duration=10000000;
	Vector NTList;//new tasks Lists
	IScheduler sch;
	
	public void setScheduler(IScheduler sch) {
		this.sch=sch;
	}
    
	/**
	 * Read tasks sequence from a file
	 * @param filename
	 * @return return a vector that contains such sequence
	 */
	
	public Vector genFromRealWorkload(String filename) {
		String record = null;
		Vector NLTlist=new Vector();
	    TaskList tlst=new TaskList(-1);
	  
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			record = new String();
			int index=0,arrtime,tid,deadline,datasize,SC,ST,Cms,Cps;
			String sTmp,sKey;
			
			int recCount=0;
			boolean test=true;
			record = br.readLine();
			while ((record = br.readLine()) != null) {
				recCount++;
				//System.out.println(recCount + ": " + record);
				if (test) {
					test=false;
					System.out.println(record);
					while (index!=-1) {
						index=record.indexOf(" ");
						sTmp=record.substring(0, index);
						System.out.println(sTmp);
						record=record.substring(index+1);
						System.out.println("ok!");
					}
					System.out.println(record);
				}
				
				
				
				
		    	//parse String
		    	
		    /*	
		    	sKey="A=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                arrtime=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="id=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                tid=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="w=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                datasize=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="D=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                deadline=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                
                sKey="SC=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                SC=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="ST=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                ST=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="Cms=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                //Cms=Integer.parseInt(sTmp);
                Cms=COMMON.PARA_CMS;
                //System.out.println(sTmp);
                
                sKey="Cps=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(")");
                sTmp=record.substring(0,index);
                //Cps=Integer.parseInt(sTmp);
                Cps=COMMON.PARA_CPS;
                //System.out.println(sTmp);
                
                
                //**************************************************
		    	 * insert task to the task list
		    	 * 
		    	 ***********************************************
				boolean b=false;
		        //check if arrtime already exit;
				//TODO best solution here if everything assume to be in order
		    	for (int j=0;j<NLTlist.size();j++) {
		    		TaskList tmp=(TaskList) NLTlist.elementAt(j);
		    		if (tmp.time==arrtime) {
		    			tlst=tmp;
			    		b=true;
			    		break;
			    	}
			    }
			    
			    if (!b)
			    	tlst=new TaskList(arrtime);
			    Task task_=new Task(tid,arrtime,deadline,datasize);
			    task_.setCmsCps(Cms, Cps);
			    task_.setSetCost(ST, SC);
			    tlst.addTask(task_);
			    if (!b)
			    	NLTlist.add(tlst);
			    
			    
			    */
			}
		
			br.close();
			fr.close();
			System.out.println("\nThere are "+recCount+" tasks in the real workload!\n");
		} catch (IOException e) {
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
		
		//printListScreen(NLTlist);
	    return NLTlist;
	  
	}
	public Vector getFromFile(String filename) {
//		TODO getFromFile not tested, especially several tasks in the same time point
		String record = null;
		Vector NLTlist=new Vector();
	    TaskList tlst=new TaskList(-1);
	  
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			record = new String();
			int index,arrtime,tid,deadline,datasize,SC,ST,Cms,Cps;
			String sTmp,sKey;
			while ((record = br.readLine()) != null) {
//				recCount++;
//				System.out.println(recCount + ": " + record);
				
		    	//parse String
		    	
		    	
		    	sKey="A=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                arrtime=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="id=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                tid=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="w=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                datasize=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="D=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                deadline=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                
                sKey="SC=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                SC=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="ST=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                ST=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="Cms=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                //Cms=Integer.parseInt(sTmp);
                Cms=COMMON.PARA_CMS;
                //System.out.println(sTmp);
                
                sKey="Cps=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(")");
                sTmp=record.substring(0,index);
                //Cps=Integer.parseInt(sTmp);
                Cps=COMMON.PARA_CPS;
                //System.out.println(sTmp);
                
                
                /**************************************************
		    	 * insert task to the task list
		    	 * 
		    	 ***********************************************/
				boolean b=false;
		        //check if arrtime already exit;
				//TODO best solution here if everything assume to be in order
		    	for (int j=0;j<NLTlist.size();j++) {
		    		TaskList tmp=(TaskList) NLTlist.elementAt(j);
		    		if (tmp.time==arrtime) {
		    			tlst=tmp;
			    		b=true;
			    		break;
			    	}
			    }
			    
			    if (!b)
			    	tlst=new TaskList(arrtime);
			    Task task_=new Task(tid,arrtime,deadline,datasize);
			    task_.setCmsCps(Cms, Cps);
			    task_.setSetCost(ST, SC);
			    tlst.addTask(task_);
			    if (!b)
			    	NLTlist.add(tlst);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
		
		//printListScreen(NLTlist);
	    return NLTlist;
	  
	}
	
	
	/**
	 * 
	 * @param filename
	 * @param st setup cost for communication
	 * @param sc setup cost for computation
	 * @return
	 */
	public Vector getFromFile(String filename, int st, int sc) {
//		TODO getFromFile not tested, especially several tasks in the same time point
		String record = null;
		Vector NLTlist=new Vector();
	    TaskList tlst=new TaskList(-1);
	  
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			record = new String();
			int index,arrtime,tid,deadline,datasize,SC,ST,Cms,Cps;
			String sTmp,sKey;
			while ((record = br.readLine()) != null) {
//				recCount++;
//				System.out.println(recCount + ": " + record);
				
		    	//parse String
		    	
		    	
		    	sKey="A=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                arrtime=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="id=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                tid=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="w=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                datasize=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="D=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                deadline=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                
                sKey="SC=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                SC=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="ST=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                ST=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="Cms=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                //Cms=Integer.parseInt(sTmp);
                Cms=COMMON.PARA_CMS;
                //System.out.println(sTmp);
                
                sKey="Cps=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(")");
                sTmp=record.substring(0,index);
                //Cps=Integer.parseInt(sTmp);
                Cps=COMMON.PARA_CPS;
                //System.out.println(sTmp);
                
                
                /**************************************************
		    	 * insert task to the task list
		    	 * 
		    	 ***********************************************/
				boolean b=false;
		        //check if arrtime already exit;
				//TODO best solution here if everything assume to be in order
		    	for (int j=0;j<NLTlist.size();j++) {
		    		TaskList tmp=(TaskList) NLTlist.elementAt(j);
		    		if (tmp.time==arrtime) {
		    			tlst=tmp;
			    		b=true;
			    		break;
			    	}
			    }
			    
			    if (!b)
			    	tlst=new TaskList(arrtime);
			    Task task_=new Task(tid,arrtime,deadline,datasize);
			    task_.setCmsCps(Cms, Cps);
			    task_.setSetCost(st, sc);
			    tlst.addTask(task_);
			    if (!b)
			    	NLTlist.add(tlst);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
		
		//printListScreen(NLTlist);
	    return NLTlist;
	  
	}
	
	
	
	
	/**
	 * read files and split them
	 * @param filename
	 * @param bAllNode true if the piece of the splitted tasks are equal to the cluster size, otherwise, we use the random number
	 * @return
	 */
	public Vector getSplitTasksFromFile(String filename,boolean bAllNode) {
		String record = null;
		Vector NLTlist=new Vector();
	    TaskList tlst=new TaskList(-1);
	    Random ren=new Random();
	  
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			record = new String();
			int index,arrtime,tid,deadline,datasize,SC,ST,Cms,Cps;
			String sTmp,sKey;
			while ((record = br.readLine()) != null) {
		    	//parse String
		    	sKey="A=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                arrtime=Integer.parseInt(sTmp);
                
                sKey="id=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                tid=Integer.parseInt(sTmp);
                
                sKey="w=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                datasize=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="D=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                deadline=Integer.parseInt(sTmp);
                
                
                sKey="SC=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                SC=Integer.parseInt(sTmp);
                
                sKey="ST=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                ST=Integer.parseInt(sTmp);
                
                sKey="Cms=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                //Cms=Integer.parseInt(sTmp);
                Cms=COMMON.PARA_CMS;
                //System.out.println(sTmp);
                
                sKey="Cps=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(")");
                sTmp=record.substring(0,index);
                //Cps=Integer.parseInt(sTmp);
                Cps=COMMON.PARA_CPS;
                //System.out.println(sTmp);
                
                
                /**************************************************
		    	 * insert task to the task list
		    	 * 
		    	 ***********************************************/
				boolean b=false;
		        //check if arrtime already exit;
		    	for (int j=0;j<NLTlist.size();j++) {
		    		TaskList tmp=(TaskList) NLTlist.elementAt(j);
		    		if (tmp.time==arrtime) {
		    			tlst=tmp;
			    		b=true;
			    		break;
			    	}
			    }
			    
			    if (!b)
			    	tlst=new TaskList(arrtime);
			    

                int pieces=0,size=0;
                
                Task task_; 
                int Nmin=1;
                
                if (bAllNode) {
                	pieces=COMMON.PARA_CLUSTERSIZE;
                }
                else {
                	Nmin =(int) Math.ceil(1.0*(COMMON.PARA_CPS*datasize)/(deadline-datasize*COMMON.PARA_CMS-COMMON.PARA_CPS) );
                	if (Nmin<COMMON.PARA_CLUSTERSIZE) {
                		pieces=ren.nextInt(COMMON.PARA_CLUSTERSIZE-Nmin);
                		pieces+=Nmin;
                	}
                	else
                		pieces=COMMON.PARA_CLUSTERSIZE;
                }//end of  if bAllNode
                if (datasize<pieces)
                	pieces=datasize;
                
                
                int k=(int) Math.ceil(datasize*1.0/pieces);
                k=k*pieces-datasize;
                
                
                size=(int)Math.ceil(datasize*1.0/pieces);
                for (int i=0;i<(pieces-k);i++) {
                	task_=new Task(tid,arrtime,deadline,size);

    			    task_.setCmsCps(Cms, Cps);
    			    task_.setSetCost(ST, SC);
    			    if (Nmin>0)
    			    	task_.setNmin(1);
    			    else
    			    	task_.setNmin(-1);
    			    tlst.addTask(task_);
                }
                
                size=(int)Math.floor(datasize*1.0/pieces);
                for (int i=0;i<k;i++) {
                	task_=new Task(tid,arrtime,deadline,size);

    			    task_.setCmsCps(Cms, Cps);
    			    task_.setSetCost(ST, SC);
    			    if (Nmin>0)
    			    	task_.setNmin(1);
    			    else
    			    	task_.setNmin(-1);
    			    tlst.addTask(task_);
                }
              
  		    if (!b)
			    	NLTlist.add(tlst);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
		
		//printListScreen(NLTlist);
	    return NLTlist;
	  
	}
	
	
	
	/**
	 * read parameters from the configuration file
	 * @param confName
	 */
	
	//TODO change the format of config file to XML
	public  void readPara(String confName) {
		int line=0;
		try {
			FileReader fr = new FileReader(confName);
			BufferedReader br = new BufferedReader(fr);
			String record;
			int index=0;
			while ((record = br.readLine()) != null) {
				line++;
				if ((index = record.indexOf("#")) !=-1) continue;
				if ((index = record.indexOf(COMMON.STR_SYSTEM_SIZE)) !=-1) {
					index=record.indexOf("=");
					String s=record.substring(index+1);
					COMMON.PARA_CLUSTERSIZE=Integer.parseInt(s);
					System.out.println("System Size = "+COMMON.PARA_CLUSTERSIZE);
				}
				else if ((index = record.indexOf(COMMON.STR_OUTPUT_DIRECTORY)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_OUTPUT_DIR=record.substring(index+1);
					System.out.println("Output Directory = "+COMMON.PARA_OUTPUT_DIR);
				}
				else if ((index = record.indexOf(COMMON.STR_ITERATION)) !=-1) {
					index=record.indexOf("=");
					String s=record.substring(index+1);
					COMMON.PARA_EXP_ITERATION=Integer.parseInt(s);
					System.out.println("EXPERIMENT ITERATION = "+COMMON.PARA_EXP_ITERATION);
				}
				else if ((index = record.indexOf(COMMON.STR_INPUT_FILENAME)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_INPUT_FILENAME=record.substring(index+1);
					System.out.println("Input File = "+COMMON.PARA_INPUT_FILENAME);
				}
				else if ((index = record.indexOf(COMMON.STR_LATEX)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_LATEX_FILENAME=record.substring(index+1);
					System.out.println("latex file = "+COMMON.PARA_LATEX_FILENAME);
				}
				else if ((index = record.indexOf(COMMON.STR_CMS)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_CMS=Integer.parseInt(record.substring(index+1));
					System.out.println("CMS = "+COMMON.PARA_CMS);
				}
				else if ((index = record.indexOf(COMMON.STR_CPS)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_CPS=Integer.parseInt(record.substring(index+1));
					System.out.println("CPS = "+COMMON.PARA_CPS);
				}
				else if ((index = record.indexOf(COMMON.STR_ST)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_ST=Integer.parseInt(record.substring(index+1));
					System.out.println("ST = "+COMMON.PARA_ST);
				}
				else if ((index = record.indexOf(COMMON.STR_SC)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_SC=Integer.parseInt(record.substring(index+1));
					System.out.println("SC = "+COMMON.PARA_SC);
				}
				else if ((index = record.indexOf(COMMON.STR_SIMULATION_TIME)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_SIMULATION_TIME=Integer.parseInt(record.substring(index+1));
					System.out.println("simulation time = "+COMMON.PARA_SIMULATION_TIME);
				}
				else if ((index = record.indexOf(COMMON.STR_CDRATIO)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_CDRATIO=Integer.parseInt(record.substring(index+1));
					System.out.println("cdratio = "+COMMON.PARA_CDRATIO);
				}
				else if ((index = record.indexOf(COMMON.STR_DATASIZE)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_DATASIZE=Integer.parseInt(record.substring(index+1));
					System.out.println("datasize = "+COMMON.PARA_DATASIZE);
				}
				else if ((index = record.indexOf(COMMON.STR_SEQ_NAME)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_SEQ_NAME=record.substring(index+1);
					System.out.println("seqname = "+COMMON.PARA_SEQ_NAME);
				}
				else if ((index = record.indexOf(COMMON.STR_PLATFORM)) !=-1) {
					index=record.indexOf("=");
					COMMON.PARA_PLATFORM=Integer.parseInt(record.substring(index+1));
					System.out.println("platform = "+COMMON.PARA_PLATFORM);
				}
			}
		} catch (Exception e) {
			System.out.println("Error when read line "+line+" from configuration file!");
			System.out.println(e);
		}
		
	}

	public Vector getInput(String filename) {
		int totalWorkload=0;
		int numOfTask=0;
		double dcratio=0;
		int totalDatasize=0;
		int oldArrTime=0;
		int totalInterarrivalTime=0;
//		TODO getFromFile not tested, especially several tasks in the same time point

		System.out.println();
		System.out.println("--------------------------------------------------");
		System.out.println("...Begin to read input file: "+filename +"  ...");
		System.out.println();
		
		String record = null;
		Vector NLTlist=new Vector();
	    TaskList tlst=new TaskList(-1);
	  
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			record = new String();
			int index,arrtime,tid,deadline,datasize,SC,ST,Cms,Cps,nMin;
			String sTmp,sKey;
			while ((record = br.readLine()) != null) {
		    	//parse String
		    	sKey="A=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                arrtime=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="id=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                tid=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="w=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                datasize=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="D=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                deadline=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                
                sKey="SC=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                SC=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="ST=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                ST=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="Nmin=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                nMin=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                
                sKey="Cms=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                //Cms=Integer.parseInt(sTmp);
                Cms=COMMON.PARA_CMS;
                //System.out.println(sTmp);
                
                sKey="Cps=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(")");
                sTmp=record.substring(0,index);
                //Cps=Integer.parseInt(sTmp);
                Cps=COMMON.PARA_CPS;
                //System.out.println(sTmp);
                
                
                /**************************************************
		    	 * insert task to the task list
		    	 * 
		    	 ***********************************************/
				boolean b=false;
		        //check if arrtime already exit;
				//TODO best solution here if everything assume to be in order
		    	for (int j=0;j<NLTlist.size();j++) {
		    		TaskList tmp=(TaskList) NLTlist.elementAt(j);
		    		if (tmp.time==arrtime) {
		    			tlst=tmp;
			    		b=true;
			    		break;
			    	}
			    }
			    
			    if (!b)
			    	tlst=new TaskList(arrtime);
			    Task task_=new Task(tid,arrtime,deadline,datasize);
			    task_.setCmsCps(Cms, Cps);
			    task_.setSetCost(ST, SC);
			    task_.setNmin(nMin);
			    
			    
			    
			    /////////////////////////////begin test
			    
			    
//			    task_.computeByNodes(arrtime, COMMON.PARA_CLUSTERSIZE, COMMON.ROUND_SINGLE);
//			    if (task_.isDeadlineMissed())
//			    	System.out.println("Deadline Missed: "+task_.toString());
			    totalWorkload+=task_.getExeTime();
			    numOfTask++;
			    totalDatasize+=task_.getDatasize();
			    dcratio+=1.0*task_.getRelativeDeadline()/task_.getExeTime();
			    totalInterarrivalTime+=task_.getArrTime()-oldArrTime;
			    oldArrTime=task_.getArrTime();
			    
			    
			    
			    
			    
			    
			    
			    /////////////////////////////end test
			    tlst.addTask(task_);
			    if (!b)
			    	NLTlist.add(tlst);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
		

		System.out.println("Workload: "+1.0*totalWorkload/COMMON.PARA_SIMULATION_TIME);

		System.out.println("Number of tasks: "+numOfTask);

		System.out.println("Average DCRATIO: "+dcratio/numOfTask);
		
		System.out.println("Average Datasize: "+1.0*totalDatasize/numOfTask);
		
		System.out.println("Average Inter-arrival Time: "+1.0*totalInterarrivalTime/numOfTask);
		
		//printListScreen(NLTlist);
	    return NLTlist;
	  
	}

    public Vector genUserReserve(Vector NTList) {
    	Vector rst=new Vector();
    	for (int j=0;j<NTList.size();j++) {
    		TaskList tmp=(TaskList) NTList.elementAt(j);
    		TaskList tlst=new TaskList(tmp.time);
    		Vector tasks=tmp.tasks;
    		int x=0,count=1;
    		Task task,newtask;
    		while ( x<tasks.size() ) {
    			
    			task=(Task)tasks.elementAt(x);
    			newtask=new Task(task);
    			int datasize=task.getDatasize();
    			int i=x+1;
    			while (i<tasks.size()) {
    				Task t=(Task)tasks.elementAt(i);
    				if (t.getID()==task.getID())  {
    					count++;
    					datasize+=t.getDatasize();
    					i++;
    				}
    				else {
    					break;
    				}
    			}
    			newtask.setNmin(count);
    			newtask.setDatasize(datasize);
    			tlst.addTask(newtask);
    			x=i;
    		}//end of while x
    		rst.add(tlst);
    		
	    }// end of for j
    	return rst;
    }

}
