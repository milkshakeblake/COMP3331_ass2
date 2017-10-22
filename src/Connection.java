import java.util.*;

public class Connection extends Thread {
	
	long start;
	long end;
	int origin;
	int destination;
	long startTime;
	Network network;
	String routingScheme;
	int packetRate;
	
	

	public Connection(Network n, float start, float end, String origin, 
			String destination, long startTime, String rs, int pr) {
		this.network = n;
		this.start = (long) start * 1000000;
		this.end = (long) end * 1000000;
		this.origin = Network.let2Num(origin);
		this.destination = Network.let2Num(destination);
		this.startTime = startTime;
		this.routingScheme = rs;
		this.packetRate = pr;
	}


	public void run() {
		try {
			Thread.sleep((this.start - System.nanoTime() + this.startTime)/1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Hop path = network.pathSearch(origin, destination, routingScheme);

		if (path == null) { //no path found
			System.err.println("No path found!");
		} else if (this.network.hasCapacity(path.linkPath()) == true) { //capacity available
			System.out.print("PATH IS: ");
			path.printPath();
			this.network.changeLoad(path.linkPath(), 1);
		}
		
		//teardown
		try {
			Thread.sleep((this.end - this.start)/1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//deload
		this.network.printLinks();
		this.network.changeLoad(path.linkPath(), -1);
		
	}
	
	
		
}