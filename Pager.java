
public class Pager
{
	private int pageSize;
	private int pageCount;
	
	private Process[] processes;
	private ProcessQueue waitQueue;
	private boolean[] startCounting;
	private int[] turnaround;
	
	private int[] memoryMap;
	
	private int time;
	
	public Pager(int totalMemorySize, int pageSize, Process[] processes)
	{
		this.pageSize = pageSize;
		this.pageCount = totalMemorySize / pageSize;
		this.processes = processes;
		this.waitQueue = new ProcessQueue();
		this.startCounting = new boolean[processes.length];
		this.turnaround = new int[processes.length];
		this.memoryMap = invalidate(new int[pageCount]);
		this.time = 0;
		page();
	}
	
	private void page()
	{
		boolean timeNotMarked = true;
		while (!(checkLifetimes()))
		{
			// Check for completion. If so, removes the process from memory.
			for (int i = 0; i < processes.length; i++)
			{
				processes[i].decrementLifetime();
				if (processes[i].getLifetime() == 0 && processes[i].isActive())
				{
					exits(processes[i].getProcessID());
					if (timeNotMarked)
					{
						System.out.print("t = " + time + ": ");
						System.out.println("Process " + processes[i].getProcessID() + " completes");
						timeNotMarked = false;
					}
					else
					{
						System.out.println("\tProcess " + processes[i].getProcessID() + " completes");
					}
					System.out.println(getMemoryMap());
					startCounting[i] = false;
					processes[i].deactivate();
				}
			}
			
			// Check if can add this process to the wait queue.
			for (int i = 0; i < processes.length; i++)
			{
				if (processes[i].getArrivalTime() == time) // must be EXACTLY equal to time
				{
					if (timeNotMarked)
					{
						System.out.print("t = " + time + ": ");
						timeNotMarked = false;
					}
					else
					{
						System.out.print("\t");
					}
					System.out.println("Process " + processes[i].getProcessID() + " arrives");
					waitQueue.enqueue(processes[i]);
					startCounting[i] = true;
					System.out.println("\tInput Queue: " + waitQueue);
				}
			}
			
			// Tries to add processes in wait queue into memory.
			boolean noMoreFit = false;
			do
			{
				if (waitQueue.isEmpty())
				{
					noMoreFit = true;
				}
				else
				{
					Process currentProcess = waitQueue.getFront();
					int[] validIndexes = fits(currentProcess.getProcessSize());
					if (validIndexes.length - 1 == -1)
					{
						noMoreFit = true;
					}
					else if (validIndexes[validIndexes.length - 1] == -1)
					{
						noMoreFit = true;
					}
					else
					{
						for (int i = 0; i < validIndexes.length; i++)
						{
							memoryMap[validIndexes[i]] = currentProcess.getProcessID();
						}
						if (timeNotMarked)
						{
							System.out.print("t = " + time + ": ");
							System.out.println("MM moves Process " + currentProcess.getProcessID() + " to memory");
							timeNotMarked = false;
						}
						else
						{
							System.out.println("\tMM moves Process " + currentProcess.getProcessID() + " to memory");
						}
						currentProcess.activate();
						waitQueue.dequeue();
						System.out.println("\tInput Queue: " + waitQueue);
						System.out.println(getMemoryMap());
					}
				}
			} while (!(noMoreFit));
			
			// Counts turnaround.
			for (int i = 0; i < processes.length; i++)
			{
				if (startCounting[i])
				{
					turnaround[i]++;
				}
			}
			
			timeNotMarked = true;
			this.time++;
		}
		
		double avgTurnaround = turnaround[0];
		for (int i = 1; i < turnaround.length; i++)
		{
			avgTurnaround += turnaround[i];
		}
		System.out.println("Average turnaround: " + (avgTurnaround / turnaround.length));
		
	}
	
	private boolean checkLifetimes()
	{
		for (int i = 0; i < processes.length; i++)
		{
			if (processes[i].getLifetime() > 0)
			{
				return false;
			}
		}
		
		return true;
	}
	
	private String getMemoryMap()
	{
		String output = "";
		int startKB = 0;
		int endKB = pageSize - 1;
		int[] pageNumbers = new int[processes.length];
		boolean tabAfterFirst = false;
		
		output += "\tMemory Map: ";
		for (int i = 0; i < memoryMap.length; i++)
		{	
			if (memoryMap[i] != -1)
			{
				if (tabAfterFirst)
				{
					output += "\t\t";
				}
				output += startKB + "-" + endKB + ": Process " + memoryMap[i] + ", Page " + ++pageNumbers[memoryMap[i] - 1] + "\n";
				startKB += pageSize;
				tabAfterFirst = true;
			}
			else if (i == memoryMap.length - 1)
			{
				if (tabAfterFirst)
				{
					output += "\t\t";
				}
				output += startKB + "-" + endKB + ": Free frame(s)\n";
				tabAfterFirst = true;
			}
			else if (i < memoryMap.length - 1)
			{
				if (memoryMap[i + 1] != -1)
				{
					if (tabAfterFirst)
					{
						output += "\t\t";
					}
					output += startKB + "-" + endKB + ": Free frame(s)\n";
					startKB = endKB + 1;
					tabAfterFirst = true;
				}
			}
			endKB += pageSize;
		}
		
		return output;
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
	
	private int[] fits(int processSize)
	{
		int spacesToFill = processSize / pageSize;
		if (processSize % pageSize != 0)
		{
			spacesToFill += 1;
		}
		
		int[] validIndexes = invalidate(new int[spacesToFill]);
		
		int i = 0;
		int j = 0;
		while (i < pageCount && j < validIndexes.length)
		{
			if (memoryMap[i] == -1)
			{
				validIndexes[j] = i;
				j++;
			}
			i++;
		}
		
		return validIndexes;
	}
	
	private int[] invalidate(int[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			array[i] = -1;
		}
		return array;
	}
	
	private class ProcessQueue
	{
		private Node firstNode;
		private Node lastNode;
		
		public ProcessQueue()
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
