
public class Diagnostics {
	private long timeStart, timeEnd;
	private double timeTotal;
	
	public Diagnostics() {
		this.timeStart = this.timeEnd = 0;
		this.timeTotal = 0;
	}
	
	public void start() {
		this.timeStart = System.currentTimeMillis();
	}
	
	public double end() {
		this.timeEnd = System.currentTimeMillis();
		this.timeTotal = ((double)timeEnd - (double)timeStart) / 1000;
		return timeTotal;
	}
	
	public long getStartTime() {
		return this.timeStart;
	}
	
	public long getEndTime() {
		return this.timeEnd;
	}
	
	public double getTotalTime() {
		return this.timeTotal;
	}	
	
	public void showTime() {
		System.out.println("Time taken: " + this.timeTotal + "s");
	}
}
