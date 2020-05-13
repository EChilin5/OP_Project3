import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{		
    // THESE LINES OF CODE ARE FOR TESTING. DELETE THEM WHEN DONE.
	private static final String defaultMemorySize = "2000";
	private static final String defaultMemoryManagementPolicy = "2";
	private static final String defaultFitAlgorithm = "100";
	private static final String defaultFileName = "C:\\Users\\Dell\\Downloads\\cs4310project\\in1.dat";
	//private static final String defaultFileName = "/Users/edgarchilin/git/opProject3/in1.txt";
	// THESE LINES OF CODE ARE FOR TESTING. DELETE THEM WHEN DONE.

	public static void main(String[] args) throws FileNotFoundException
	{        
        int memorySize;
        int memoryManagementPolicy;
        int fitAlgorithm; // used ONLY for VSP/SEG, act as "pageSize" for PAG
        String workloadFileName;
        Process[] processes;
        
        String[] userInput = readUserInput();
        
      // THESE LINES OF CODE ARE FOR TESTING. DELETE THEM WHEN DONE.
        if (userInput == null)
        {
            userInput = new String[4];
            userInput[0] = defaultMemorySize;
            userInput[1] = defaultMemoryManagementPolicy;
            userInput[2] = defaultFitAlgorithm;
            userInput[3] = defaultFileName;
        }
        // THESE LINES OF CODE ARE FOR TESTING. DELETE THEM WHEN DONE.
        
        memorySize = Integer.parseInt(userInput[0]);
        memoryManagementPolicy = Integer.parseInt(userInput[1]);
        fitAlgorithm = Integer.parseInt(userInput[2]);
        workloadFileName = userInput[3];
        
        processes = readWorkloadFile(workloadFileName);
        
        if (memoryManagementPolicy == 1)
        {
        	// Variable-Size Partitioning
        	try {
				VSPoptions.RunCommand(memorySize, fitAlgorithm, processes);
			} catch (InterruptedException e) {
				// use a separate thread so it requires a interrupted exception
				//e.printStackTrace();
			}
        }
        else if (memoryManagementPolicy == 2)
        {
        	// Paging
        	Pager pager = new Pager(memorySize, fitAlgorithm, processes);
        }
        else if (memoryManagementPolicy == 3)
        {
        	// Segmentation
        }
	}
	//
	
	private static String[] readUserInput()
	{
		Scanner input = new Scanner(System.in);
		
		String[] userInputValues = new String[4];
		
		// THESE LINES OF CODE ARE FOR TESTING. DELETE THEM WHEN DONE.
		System.out.print("Skip inputs for testing? (y/n): ");
		if (input.nextLine().contentEquals("y"))
			return null;
		// THESE LINSE OF CODE ARE FOR TESTING. DELETE THEM WHEN DONE.
		
		String prompt0 = "Memory size: ";
		String prompt1 = "Memory management policy (1- VSP, 2- PAG, 3- SEG): ";
		String prompt2_1 = "Fit algorithm (1- first-fit, 2- best-fit, 3- worst-fit): ";
		String prompt2_2 = "Page size: ";
		String prompt3 = "Workload file: ";
		
		System.out.print(prompt0);
        userInputValues[0] = input.nextLine();
        
        System.out.print(prompt1);
        userInputValues[1] = input.nextLine();
        
        System.out.print(userInputValues[1].equals("2") ? prompt2_2 : prompt2_1);
        userInputValues[2] = input.nextLine();
        
        System.out.print(prompt3);
        userInputValues[3] = input.nextLine();
        
        return userInputValues;
	}
	
	private static Process[] readWorkloadFile(String inputFileName) throws FileNotFoundException
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
			// processes[i].printDebugMsg(); // TEST LINE
		}
			
		inputFile.close();
		return processes;
	}
}
