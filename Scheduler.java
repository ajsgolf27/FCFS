package FCFS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.print.DocFlavor.URL;




public class Scheduler {

	private LinkedList<Process> processes = new LinkedList<Process>();
	
	public static Comparator<Process> sortByArrival(){
		
		Comparator<Process> comp = new
				Comparator<Process>()
		{
			/**
			 * sortByArival new comparator to sort by arrival time
			 * 
			 * @param process object
			 * @param process object
			 * @precondition process object must be created.
			 * @postcondition process objects are compared
			 * @return -int or + int
			 */
			public int compare(Process process1, Process process2)
			{ 
			return process1.getArrivalTime() - (process2.getArrivalTime());
			} 
		};
		return comp;
	}
	

	
	
	public static void main(String[] args){
		int count = 0;
		int cpuBurstFinished = 0;
		int startTime = 0;
		ArrayList<Process> processes = new ArrayList<Process>();
		LinkedList<Integer> io = new LinkedList<Integer>();
		LinkedList<Integer> burst = new LinkedList<Integer>();
		LinkedList<Integer> avgWaitTime = new LinkedList<Integer>();
		LinkedList<Integer> avgTurnaroundTime = new LinkedList<Integer>();
		LinkedList<Integer> avgResponseTime = new LinkedList<Integer>();
		
		
		//read in file
		
		String fileName = "jobInfo.txt";
		String line = null;
		
		
		try {
            // FileReader reads text files in the default encoding.
			
			InputStream is = ClassLoader.getSystemResourceAsStream(fileName);


            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
           
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
           
            while((line = bufferedReader.readLine()) != null) {
            	
            	line = line.replace('{', ',');
            	line = line.replace('}', ' ');
            	line = line.replaceAll("\\s", "");
            	
            	
            	String a[] = line.split(",");
            	
            	
            	String processId = a[0];
            	
            	for(int i = 1; i < a.length - 1; i += 2){

            		burst.add(new Integer(a[i]));
            		io.add(new Integer(a[i+ 1]));

            		if(i + 2 == a.length){
            			burst.add(new Integer(a[i + 2]));
            		}
            		
            	}
            	
            	processes.add(new Process(processId,burst,io));
            	io.clear();
            	burst.clear();
            }   

            
            bufferedReader.close();         
        }
		
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }catch(IOException ex) {
            System.out.println(
                    "Error reading file '" 
                    + fileName + "'");                  
                
            }
		///////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////
		
		Comparator<Process> comp = sortByArrival();
		
		System.out.println("current time: " + count);
		System.out.println("" );
		
		do{
			
			
			Collections.sort(processes, comp);
			
			startTime = count;
			
			count += processes.get(0).getCurrentBurst();
			
			System.out.println("current time: " + count);
			System.out.println("now running: " + processes.get(0).getName());
			System.out.println("");
			System.out.println("-----------------------------------------------");
			System.out.println("");
			System.out.println("Ready queue:	Process		Burst");
			for (int i = 0; i < processes.size(); i++){
			System.out.println("                " +processes.get(i).getName() + "		" + processes.get(i).getCurrentBurst() );
			}
			System.out.println("");
			
			processes.get(0).responseTime += (startTime -processes.get(0).getArrivalTime());
			System.out.println("responseTime " + (startTime - processes.get(0).getArrivalTime()));
			cpuBurstFinished = processes.get(0).runProcess();
			processes.get(0).turaroundTime += (count - startTime);
			
			if(cpuBurstFinished == -1){
				avgWaitTime.add(processes.get(0).waitTime);
				//avgTurnaroundTime.add(count - startTime);
				//avgResponseTime.add(startTime -processes.get(0).getArrivalTime());
				avgTurnaroundTime.add(processes.get(0).turaroundTime);
				avgResponseTime.add(processes.get(0).responseTime/7);
				processes.remove(0);
			}
			
		
			
		}while(processes.isEmpty() == false);
		
		DecimalFormat df = new DecimalFormat(".##");
		int avgwait = 0;
		int avgTurnaround = 0;
		int avgResponse = 0;
		// calculate wait time
		for (int i = 0; i < avgWaitTime.size(); i++){
			avgwait += avgWaitTime.get(i);
			}
		avgwait = avgwait/avgWaitTime.size();
		System.out.println(" ");
		System.out.println("current time: " + count);
		double cpu = ((count - avgwait));
		cpu = cpu/count;
		cpu = cpu * 100;
		System.out.println("cpu utilization: " + df.format(cpu) + "%");
		System.out.println("Average wait time: " + avgwait);
		for (int i = 0; i < avgWaitTime.size(); i++){
			//System.out.println(avgWaitTime.get(i));
			}
		
		// calculate turnaround time
		for (int i = 0; i < avgTurnaroundTime.size(); i++){
			avgTurnaround += avgTurnaroundTime.get(i);
			//System.out.println(avgTurnaroundTime.get(i));
			}
		avgTurnaround = avgTurnaround/avgTurnaroundTime.size();
		System.out.println("Average turnaround time: " + avgTurnaround);
		// calculate response time
		for (int i = 0; i < avgResponseTime.size(); i++){
			//System.out.println(avgResponseTime.get(i));
			avgResponse += avgResponseTime.get(i);
			}
		avgResponse = avgResponse/avgResponseTime.size();
		System.out.println("Average response time: " + avgResponse);
		
	}
}
