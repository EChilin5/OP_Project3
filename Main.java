import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{	
	// FOR DEBUG ONLY TO SKIP INPUT
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
        
        Scanner workloadFile = new Scanner(new File(workloadFileName));
        numberOfProcesses = readInputForNumberOfProcesses(workloadFile);
        System.out.println("# of processes: " + numberOfProcesses); // TEST LINE
                
        // THE THING THAT YOU WANT TO DO HERE
        
        workloadFile.close();
	}
	//
	
	/* THE ACTUAL ONE WITH REAL INPUTS
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner consoleInput = new Scanner(System.in);
        
        int memorySize = -1;
        int memoryManagementPolicy = -1;
        int fitAlgorithm = -1; // used ONLY for VSP/SEG
        int pageSize = -1; // used ONLY for PAG
        String workloadFileName;
        
        int numberOfProcesses = -1;
        
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
        
        Scanner workloadFile = new Scanner(new File(workloadFileName));
        numberOfProcesses = readInputForNumberOfProcesses(workloadFile);
        System.out.println("# of processes: " + numberOfProcesses); // TEST LINE
                
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
        
        consoleInput.close();
        workloadFile.close();
	}
	*/
	
	private static int readInputForNumberOfProcesses(Scanner inputFile)
	{
		return inputFile.nextInt();
	}
	
}
