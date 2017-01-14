package FCFS;

import java.util.LinkedList;

public class Process {

	private String nameOfProcess = "";
	private int currentBurst = 0;
	private int currentIO = 0;
	private int currentState = 0;
	private int arrivalTime = 0;
	private int priority = 0;
	public int waitTime = 0;
	public int turaroundTime = 0;
	public int responseTime = 0; 
	private Boolean inIO = false;
	private LinkedList<Integer> cpuBursts = new LinkedList<Integer>();
	private LinkedList<Integer> ioTimes = new LinkedList<Integer>();

	public Process(String name, LinkedList<Integer> cpuBursts, LinkedList<Integer> ioTimes) {
		nameOfProcess = name;
		for (Integer s : ioTimes) {
			this.ioTimes.add(s);
		}
		for (Integer s : cpuBursts) {
			this.cpuBursts.add(s);
		}
		currentBurst = this.cpuBursts.getFirst();
		currentIO = this.ioTimes.getFirst();

	}

	/**
	 * getBurstList prints a list of cpu burst times for given process.
	 * 
	 * @precondition process object must be created.
	 * @postcondition list of bursts is printed to the console.
	 * 
	 */
	public void getBurstList() {
		for (Integer s : ioTimes) {
			System.out.println("io Times");
			System.out.println(s);
		}
	}
	
	public void removeBurstFromList(){
		cpuBursts.removeFirst();
	}
	
	public void removeIOFromList(){
		ioTimes.removeFirst();
	}

	/**
	 * getName gets the name of the process
	 * 
	 * 
	 * @precondition process object must be created.
	 * @postcondition none.
	 * @return String of the process name
	 */
	public String getName() {
		return nameOfProcess;
	}

	/**
	 * getArrivalTime returns the current arrival time
	 * 
	 * @precondition Process object must be created.
	 * @postcondition none.
	 * @return int of the process arrival time.
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * setArrivalTime sets the arrival time of the current process
	 * 
	 * @param int
	 *            time in units
	 * @precondition Process object must be created.
	 * @postcondition arrival time is set.
	 * @return none.
	 */
	public void setArrivalTime(int aTime) {
		arrivalTime = aTime;
	}

	/**
	 * getCurrentBurst returns the current cpu burst
	 * 
	 * @precondition Process object must be created.
	 * @postcondition none.
	 * @return int of the process current cpu burst.
	 */
	public int getCurrentBurst() {

		return currentBurst;
	}
	
	public void decrementCurrentBurst(){
		currentBurst = currentBurst-1;
	}

	/**
	 * getIOlTime returns the current IO time
	 * 
	 * @precondition Process object must be created.
	 * @postcondition none.
	 * @return int of the process io time.
	 */
	public int getIOTime() {

		return currentIO;
	}

	/**
	 * setIOTime sets the current io time
	 * 
	 * @precondition Process object must be created and the list must not be
	 *               empty.
	 * @postcondition currentIO parameter is set.
	 * @return none.
	 */
	public void setIOTime() {
		currentIO = ioTimes.getFirst();
	}
	
	public void decrementIOTime() {
		currentIO = currentIO - 1;
	}

	/**
	 * setCurrentBurst sets the current burst time
	 * 
	 * @precondition Process object must be created and the list must not be
	 *               empty.
	 * @postcondition currentBurst parameter is set.
	 * @return none.
	 */
	public void setCurrentBurst() {
		currentBurst = cpuBursts.getFirst();
	}
	
	public void setCurrentBurst(int burst) {
		currentBurst = burst;
	}

	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int state) {
		currentState = state;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int p) {
		priority = p;
	}
	
	public void setInIO(Boolean inIO){
		this.inIO = inIO;
	}
	
	public Boolean isInIO(){
		return inIO;
	}

	/**
	 * runProcess runs the burst on the cpu and resets the arrival time
	 * 
	 * @precondition Process object must be created, the cpuBurst list and
	 *               ioTimes list must not be empty.
	 * @postcondition arrivalTime, ioTime and currentBurst is set. The ran burst
	 *                and io time is removed from the queue.
	 * @return 1 or -1.
	 */
	public int runProcess() {

		if (cpuBursts.isEmpty() == false) {
			if (ioTimes.isEmpty() == false) {
				setIOTime();
				
			}
			setCurrentBurst();
			setArrivalTime(currentBurst + currentIO + getArrivalTime());
			waitTime = arrivalTime - currentBurst;
			ioTimes.removeFirst();
			cpuBursts.removeFirst();
			setInIO(true);

			return 1;
		} else
			return -1;
	}

	public int runTQ7() {
		if (cpuBursts.isEmpty() == false) {
			if (ioTimes.isEmpty() == false) {
				setIOTime();
				setCurrentBurst();
			}
			if (getCurrentBurst() > 7) {
				setArrivalTime(getArrivalTime() + 7);
				currentBurst = currentBurst - 7;
				setPriority(1);
			} else {
				setArrivalTime(currentBurst + currentIO + getArrivalTime());
				ioTimes.removeFirst();
				cpuBursts.removeFirst();
				setInIO(true);
			}

			return 1;
		} else
			return -1;
	}

	public int runTQ14() {
		if (cpuBursts.isEmpty() == false) {
			if (ioTimes.isEmpty() == false) {
				setIOTime();
				
			}
			setCurrentBurst();
			if (getCurrentBurst() > 14) {
				setArrivalTime(getArrivalTime() + 14);
				currentBurst = currentBurst - 14;
				setPriority(2);
			} else {
				setArrivalTime(currentBurst + currentIO + getArrivalTime());
				
				ioTimes.removeFirst();
				cpuBursts.removeFirst();
				setInIO(true);
			}

			return 1;
		} else
			return -1;
	}
	
	public int runPreEmpt(int count) {
		if (cpuBursts.isEmpty() == false) {
			if (ioTimes.isEmpty() == false) {
				
			}
			System.out.println("premepted");
				setArrivalTime(getArrivalTime() + count);
				currentBurst -= count;
				if(getPriority() == 1){
					setPriority(2);
				}
			
			}

			return count;
		
	}
	
	
	
	
}
