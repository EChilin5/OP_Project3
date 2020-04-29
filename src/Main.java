import java.io.File;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner userInput = new Scanner(System.in);
		
		int memorySize = -1;
		int memoryManagementPolicy = -1;
		int fitAlgorithm = -1;
		String workloadFileName = "";
		
		System.out.print("Memory size: ");
		memorySize = userInput.nextInt();
		System.out.print("Memory management policy (1- VSP, 2- PAG, 3- SEG): ");
		memoryManagementPolicy = userInput.nextInt();
		System.out.print("Fit algorithm (1- first-fit, 2- best-fit, 3- worst-fit): ");
		fitAlgorithm = userInput.nextInt();
		System.out.print("Workload file: ");
		workloadFileName = userInput.nextLine();
	}
}
