import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;

public class VSPoptions {
		
	 public static void RunCommand(int totalMemorySize, int fitAlgorithm, Process[] processes) throws InterruptedException {
	     System.out.println("For VSP it uses a timer so please do not\n "
	     		+ "terminate the program because it waits until the proccess is complete to remove it. \n"
	     		+ "Some proccess might be 1800 so I divided it by 50 and added the result\n"
	     		+ "to the current time that program is on.\n");   
		 if(fitAlgorithm == 1){
			 System.out.println("VSP BestFit\n");
	            BestFit(totalMemorySize, processes);
	        }else if(fitAlgorithm == 2){
	       	 System.out.println("VSP FirstFit\n");
	            FirstFit(totalMemorySize, processes);
	        }else if(fitAlgorithm == 3){
	       	 System.out.println("VSP WorseFit\n");
	            WorseFit(totalMemorySize, processes);
	        }
	    }
	 
	 private static void BestFit(int totalmemory, Process[] processes) throws InterruptedException {
	        VSPbestFit.BF(totalmemory, processes);
	    }

	 
		private static void FirstFit(int totalmemory, Process[] processes) throws InterruptedException {
	    	VSPfirstFit.BF(totalmemory, processes);
	    }

	    private static  void WorseFit(int totalmemory, Process[] processes) throws InterruptedException {
	        VSPworsefit.WF(totalmemory , processes);

	    }
	    
	   

}
