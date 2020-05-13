import java.util.Arrays;

public class Segmentation2 {
	
	private Process[] processes;
	private processQueue processQueue = new processQueue();
	private boolean startCounting[];
	private int time;
	private int memorySize, fitAlgorithm, totalProcesses;
	private int[] memoryMap;
	private int[] turnaround;
	private int processesAdded;

	
	public Segmentation2(Process[] processes, int memorySize, int fitAlgorithm) {
		this.processes = processes;
		this.memorySize = memorySize;
		this.fitAlgorithm = fitAlgorithm;
		this.turnaround = new int[processes.length];
		this.startCounting = new boolean[processes.length];
		
		
		for(int i = 0; i < processes.length; i++) {
			if(processes[i].getProcessID() >= 0) {
				totalProcesses++;
			}
		}
		
		chooseAlgorithm();
	}
	
	public void chooseAlgorithm(){
		if(fitAlgorithm == 1) {
			//firstFit();
		}else if(fitAlgorithm == 2) {
			bestFit();
		}else if(fitAlgorithm == 3) {
			//worstFit();
		}
	}
	
	
	public void bestFit() {
		boolean timeNotMarked = true;
		int[] validIndexes = bestFitCheck(currentProcess.getProcessSize());
		while(!(checkLifeTimes())) {
			for(int i = 0; i < processes.length; i++) {
				processes[i].decrementLifetime();
				//active --> deactive
				if(processes[i].getLifetime() == 0 && processes[i].isActive()) {
					exits(processes[i].getProcessID());
					if(timeNotMarked) {
						System.out.print("t = "+ time + ": ");
						System.out.println("Process: "+processes[i].getProcessID()+ "completes");
						timeNotMarked = false;
					}else {
						System.out.println("\tProcess "+processes[i].getProcessID()+ "completes");
					}
					System.out.println(getMemoryMap());
					startCounting[i] = false;
					processes[i].deactivate();
				}
			}
			
			for(int i = 0; i < processes.length; i++) {
				if(processes[i].getArrivalTime() == time) {
					if(timeNotMarked) {
						System.out.print("t = "+ time +": ");
						timeNotMarked = false;
					}else {
						System.out.print("\t");
					}
					System.out.println("Process "+processes[i].getProcessID()+ " arrives");
					processQueue.enqueue(processes[i]);
					startCounting[i] = true;
					System.out.println("\tInput Queue: "+processQueue);
				}
			}
			
			boolean noMoreFit = true;
			/*do {
				Process currentProcess = processQueue.getFront();
				int[] validIndexes = fits(currentProcess.getProcessSize());
				
			}while(!(noMoreFit));*/
			
		}
		
		
	}

	private int[] bestFitCheck(int processSize) {
		int spaceToFill = processSize;
		//worst fit - find largest hole (last node)
		int[] validIndexes = invalidate(new int[spacesToFill]);
		for(int i = 0; i < processQueue.length; i++) {
			if(processQueue.data.getProcessSize() <= memoryMap[i]) {
				validIndexes[i] = i;
			}
		}
		return validIndexes;
		
	}
	
	
	private int[] invalidate(int[] array) {
		for(int i = 0; i < array.length; i++) {
			array[i] = -1;
		}
		return array;
	}
	
	//STILL NEED WORK ON. CANT CONFIRM IF CODE WORKS WITHOUT THIS
	private String getMemoryMap() {
		String output = "Memory Map";
		int currentKB = 0;
		int maxKB = memorySize - 1;
		int count = 1;
		Segmentation2.processQueue.Node currentNode = processQueue.firstNode;
		try {
			while (currentNode.getData() != null) {
				System.out.println(currentNode.data.getSegments()[0]);
				if (currentKB == 0) {
					System.out.println("Memory Map: " + currentKB + "-" + maxKB + ": Hole");
				} else if (currentKB > 0) {
					for (int i = 0; i < processes.length; i++) {
						
					}
				}
				else
					break;
				currentKB = currentKB + currentNode.data.getProcessSize();
				currentNode = currentNode.next;
				count++;
			}
		}catch (NullPointerException e){System.out.println();}
		System.out.println("Current KB: " + currentKB);
		int startKB = 0;
		int totalKB = 0;
		int endKB = 0;
		boolean tabAfterFirst = false;
		
		
		return output;
	}
	//STILL NEED WORK ON. CANT CONFIRM IF CODE WORKS WITHOUT THIS

	
	private boolean checkLifeTimes() {
		for(int i = 0; i < processes.length; i++) {
			if(processes[i].getLifetime() > 0) {
				return false;
			}
		}
		return true;
	}
	
	private void exits(int processID)
	{
		for (int i = 0; i < memoryMap.length; i++)
		{
			if (memoryMap[i] == processID)
			{
				memoryMap[i] = -1;
			}
		}
	}
    
	
	//Wait queue - Node class
	private class processQueue {
		private Node firstNode;
		private Node lastNode;
		
		public processQueue()
		{
			firstNode = null;
			lastNode = null;
		}
		
		public void enqueue(Process newEntry)
		{
			Node newNode = new Node(newEntry, null);
			if (isEmpty())
			{
				firstNode = newNode;
			}
			else
			{
				lastNode.setNext(newNode);
			}
			lastNode = newNode;
		}
		
		public Process dequeue()
		{
			Process front = getFront();
			firstNode.setData(null);
			firstNode = firstNode.getNext();
			if (firstNode == null)
			{
				lastNode = null;
			}
			return front;
		}
		
		public Process getFront()
		{
			return firstNode.getData();
		}
		
		public Process getLast() 
		{
			return lastNode.getData();
			
		}
		
		public boolean isEmpty()
		{
			return (firstNode == null) && (lastNode == null);
		}
		
		public String toString()
		{
			if (isEmpty())
			{
				return "[]";
			}
			String output = "["; 
			Node currentNode = this.firstNode;
			while (currentNode.getNext() != null)
			{
				output += currentNode.getData().getProcessID() + " ";
				currentNode = currentNode.getNext();
			}
			return output + currentNode.getData().getProcessID() + "]";
		}
		
		//Node class
		private class Node
		{
			private Process data;
			private Node next;
			
			public Node(Process data, Node next)
			{
				this.data = data;
				this.next = next;
			}

			public Process getData()
			{
				return data;
			}

			public void setData(Process data)
			{
				this.data = data;
			}

			public Node getNext()
			{
				return next;
			}

			public void setNext(Node next)
			{
				this.next = next;
				}
			}
		}
	}

