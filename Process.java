
public class Process
{
	private int processID; // line 1
	private int arrivalTime; // line 2, 1st number
	private int lifetime; // line 2, 2nd number
	
	private int spaceReq; // line 3, 1st number is the number of things to add, then add them all up
	private int[] segments; // line 3 alt for SEG, can ignore this if you're not using it
	
	public Process(int processID, int arrivalTime, int lifetime, int spaceReq, int[] segments)
	{
		this.processID = processID;
		this.arrivalTime = arrivalTime;
		this.lifetime = lifetime;
		this.spaceReq = spaceReq;
		this.segments = segments;
	}
	
	public int getProcessID()
	{
		return processID;
	}
	
	public void setProcessID(int processID)
	{
		this.processID = processID;
	}
	
	public int getArrivalTime()
	{
		return arrivalTime;
	}
	
	public void setArrivalTime(int arrivalTime)
	{
		this.arrivalTime = arrivalTime;
	}
	
	public int getLifetime()
	{
		return lifetime;
	}
	
	public void setLifetime(int lifetime)
	{
		this.lifetime = lifetime;
	}
	
	public int getSpaceReq()
	{
		return spaceReq;
	}
	
	public void setSpaceReq(int spaceReq)
	{
		this.spaceReq = spaceReq;
	}
	
	public int[] getSegments()
	{
		return segments;
	}

	public void setSegments(int[] segments)
	{
		this.segments = segments;
	}
	
	public void printDebugMsg()
	{
		System.out.print("Process - ID: " + processID + " / Arrival Time: "
				+ arrivalTime + " / Lifetime: " + lifetime + " / Space required: " + spaceReq
				+ " / Segments: [");
		for (int i = 0; i < segments.length - 1; i++)
		{
			System.out.print(segments[i] + ",");
		}
		System.out.println(segments[segments.length - 1] + "]");
	}
}
