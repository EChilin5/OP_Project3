public class SEGWorstFit {

	private Process[] processes;
	private processQueue processQueue;
	private boolean startCounting[];
	private int time;
	private int memorySize, fitAlgorithm, totalProcesses;
	private int[] memoryMap;
	private int[] turnaround;
	
	public SEGWorstFit(Process[] processes, int memorySize, int fitAlgorithm) {
		this.processes = processes;
		this.memorySize = memorySize;
		this.fitAlgorithm = fitAlgorithm;
		this.turnaround = new int[processes.length];
		this.memoryMap = new int[memorySize];
		
		
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
			//bestFit();
		}else if(fitAlgorithm == 3) {
			worstFit();
		}
	}
	
	public void worstFit() {
		boolean enoughSpace = false;
		boolean timeNotMarked = true;
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
			
			//adds process into queue
			for(int i = 0; i < processes.length; i++) {
				int id = processes[i].getProcessID();
				int ArrivalTime = processes[i].getArrivalTime();
				int processSize = processes[i].getProcessSize();
				int lifetime = processes[i].getLifetime();
				
				if(processes[i].getArrivalTime() == time) {
					if(timeNotMarked) {
						System.out.print("t = "+time+": ");;
						timeNotMarked = false;
					}else {
						System.out.print("\t");
					}
					System.out.println("Process "+processes[i].getProcessID()+ " arrives");
					processQueue.enqueue(processes[i]);
					
					boolean result = checkFit(id, processSize, lifetime);
					if(result)
						enoughSpace = true;
				}
			}
			//process adding into memory
			boolean noMoreFit = false;
			do {
				if(processQueue.isEmpty()) {
					noMoreFit = true;
				}else {
					//worstFit 
					Process worstFit = processQueue.getLast();
					int position = worstFit.getProcessID() + 1; //get position of last node
					
					Process currentProcess = processQueue.getFront();
					int[] validIndexes = worstFitCheck(currentProcess.getProcessSize());
					if(validIndexes.length - 1 == -1) {
						noMoreFit = true;
					}else if(validIndexes[validIndexes.length - 1] == -1) {
						noMoreFit = true;
					}else {
						for(int i = 0; i < validIndexes.length; i++) {
							memoryMap[validIndexes[i + position]] = currentProcess.getProcessID();
						}
						if(timeNotMarked) {
							System.out.print("t = "+time+": ");
							System.out.println("MM moves Process "+ currentProcess.getProcessID()+" to memory");
							timeNotMarked = false;
						}else {
							System.out.println("\tMM moves Process"+currentProcess.getProcessID()+" to memory");
						}
						currentProcess.isActive();
						processQueue.dequeue();
						System.out.println("\tInput Queue: "+ processQueue);
						System.out.println(getMemoryMap());
					}
				}
			}while(!(noMoreFit));
			for(int i = 0; i < processes.length; i++) {
				if(startCounting[i]) {
					turnaround[i]++;
				}
			}
			timeNotMarked = true;
			this.time++;
		}
		
		double avgTurnaround = turnaround[0];
		for(int i = 0; i < turnaround.length; i++) {
			avgTurnaround += turnaround[i];
		}
		System.out.println("Average turnaround: "+ (avgTurnaround / turnaround.length));
	}
	
	
	public Boolean checkFit(int processID, int processSize2, int processLifetime) {
		boolean spaceAvailable = false;
		int count = 0;
		for(int i = 0; i < memoryMap.length; i++) {
			if(memoryMap[i] == -1){
				count++;
			}
			if(count > processSize2) {
				spaceAvailable = true;
				}
		}
		
		return spaceAvailable;
	}

	//STILL NEED WORK ON. CANT CONFIRM IF CODE WORKS WITHOUT THIS
	public String getMemoryMap() {
		String output = "No memory map available at the moment.";
		int startKB, endKB, positionCount, kbCount = 0;
		
		SEGWorstFit.processQueue.Node currentNode = processQueue.firstNode;
		//getting position of process in memory
		//need to work on this part
		while(currentNode.getNext() != null) {
			//System.out.println(currentNode.data.getProcessSize());
			//int tempSize = currentNode.data.getProcessSize();
			
			/*int tempID = currentNode.data.getProcessID();
			for(int i = 0; i < memorySize - 1; i++) {
				if(memoryMap[i] == tempID) {	
					kbCount++;
				}
				positionCount = kbCount;
			}				
			currentNode = currentNode.next;*/
		}
		return output;
		
		}

	private int[] worstFitCheck(int processSize) {
		int spacesToFill = processSize;
		//worst fit - find largest hole (last node)
		Process worstFit = processQueue.getLast();
		int position = worstFit.getProcessID() + 1;
		int[] validIndexes = invalidate(new int[spacesToFill]);
		int i = 0;
		int j = 0;
		while(i < totalProcesses && j < validIndexes.length) {
			if(memoryMap[i + position] == -1) {
				validIndexes[j] = i;
				j++;
			}
			i++;
		}
		return validIndexes;
		
	}
	
	//now taking up space
	private int[] invalidate(int[] array) {
		for(int i = 0; i < array.length; i++) {
			array[i] = -1;
		}
		return array;
	}
		
	
	//boolean if lifetime is at 0
	private boolean checkLifeTimes() {
		for(int i = 0; i < processes.length; i++) {
			if(processes[i].getLifetime() > 0) {
				return false;
			}
		}
		return true;
	}
	
	//Removes all spaces with processID
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
		private int count = 0;
		
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
			count++;
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
			count--;
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
		
		public int getCount() 
		{
			return count;
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

