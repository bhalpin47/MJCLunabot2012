/**
 * 
 */
package edu.mjc.lunabot.unfinished;

/**
 * @author Hoyt
 *
 */
public class Motor {

	/**
	 * @param args
	 */
	private int position;
	private int velocity;
	private double currentDraw;
	private int pin;
	public String label;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	public double getCurrentDraw()
	{
		return currentDraw;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setCurrentDraw(double currentDraw) {
		this.currentDraw = currentDraw;
	}
	
	public void run(int velocity, int duration)
	{
		
	}
	
	public void runTo(int velocity, int position)
	{
		
	}

}
