import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{	
	/* FOR DEBUG ONLY TO SKIP THE PART WHERE YOU HAVE TO MANUALLY INPUT EVERYTHING
	public static void main(String[] args) throws FileNotFoundException
	{    
        // CHANGE THESE TO MATCH YOUR CONDITIONS FOUND ON BLACKBOARD
		int memorySize = 2000; // THIS IS 2000 FOR ALL OF THE GIVEN FILES
        int memoryManagementPolicy = 2; // 1 for VSP, 2 for PAG, 3 for SEG
        int fitAlgorithm = 100; // for VSP/SEG, should be 1/2/3; for PAG, determines PAGE SIZE
        String workloadFileName = "C:\\Users\\Dell\\Downloads\\cs4310project\\in1.dat";
        // THIS IS WHERE I SAVED THE INPUT FILES, PROBABLY NOT WHERE YOU SAVED THEM
        
        int numberOfProcesses = -1;
        
        System.out.println("memory size: " + memorySize); // TEST LINE
        System.out.println("memory management policy: " + memoryManagementPolicy); // TEST LINE
        System.out.print(memoryManagementPolicy != 2 ? "fit algorithm: " : "page size: "); // TEST LINE
        System.out.println(fitAlgorithm); // TEST LINE
        System.out.println("workload file: " + workloadFileName); // TEST LINE
                
        readInput(workloadFileName);
        // THE THING THAT YOU WANT TO DO HERE
	}
	*/
	
	//
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner consoleInput = new Scanner(System.in);
        
        int memorySize;
        int memoryManagementPolicy;
        int fitAlgorithm; // used ONLY for VSP/SEG, act as "pageSize" for PAG
        String workloadFileName;
        Process[] processes;
        
        System.out.print("Memory size: ");
        memorySize = consoleInput.nextInt();
        consoleInput.nextLine(); // consumes new line character
        System.out.println("memory size: " + memorySize); // TEST LINE
        
        System.out.print("Memory management policy (1- VSP, 2- PAG, 3- SEG): ");
        memoryManagementPolicy = consoleInput.nextInt();
        consoleInput.nextLine(); // consumes new line character
        System.out.println("memory management policy: " + memoryManagementPolicy); // TEST LINE
        
        System.out.print(memoryManagementPolicy != 2 ? "Fit algorithm (1- first-fit, 2- best-fit, 3- worst-fit): " : "Page size: ");
        fitAlgorithm = consoleInput.nextInt();
        consoleInput.nextLine(); // consumes new line character
        System.out.print(memoryManagementPolicy != 2 ? "fit algorithm: " : "page size: "); // TEST LINE
        System.out.println(fitAlgorithm); // TEST LINE
        
        System.out.print("Workload file: ");
        workloadFileName = consoleInput.nextLine();
        System.out.println(workloadFileName); // TEST LINE
        
        consoleInput.close();
        processes = readInput(workloadFileName);
                
        if (memoryManagementPolicy == 1)
        {
        	// Variable-Size Partitioning
        }
        else if (memoryManagementPolicy == 2)
        {
        	// Paging
        }
        else if (memoryManagementPolicy == 3)
        {
        	// Segmentation
        }
	}
	//
	
	private static Process[] readInput(String inputFileName) throws FileNotFoundException
	{
		Scanner inputFile = new Scanner(new File(inputFileName));
		int processCount = inputFile.nextInt();
		Process[] processes = new Process[processCount];
		
		for (int i = 0; i < processCount; i++)
		{
			int processID = inputFile.nextInt();
			int arrivalTime = inputFile.nextInt();
			int lifetime = inputFile.nextInt();
			int segmentCount = inputFile.nextInt();
			
			// You should only be using 1 of these two, but to make it a bit easier to manage, here's both.
			int spaceReq = 0;
			int[] segments = new int[segmentCount];
			
			for (int j = 0; j < segmentCount; j++)
			{
				int nextInt = inputFile.nextInt();
				spaceReq += nextInt;
				segments[j] = nextInt;
			}
			
			processes[i] = new Process(processID, arrivalTime, lifetime, spaceReq, segments);
			processes[i].printDebugMsg(); // TEST LINE
		}
			
		inputFile.close();
		return processes;
	}
}
