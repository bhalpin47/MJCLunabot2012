package edu.mjc.lunabot;

import edu.mjc.lunabot.unfinished.Motor;
import edu.mjc.lunabot.unfinished.Sensor;

public class LunaBrain {

	/**
	 * @param args
	 */
	
    private Motor[] motors;
    private Sensor[] sensors;
    private double currentMass;
    private double massCollected;
    private Navigation navigation;
    private String[] dataLog;
    
    public static final int AUTONOMOUS = 0;
    public static final int SEMIAUTO = 1;
    public static final int MANUAL = 2;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean run = true;
		int mode = AUTONOMOUS;
		while(run){
			switch(mode){
			case AUTONOMOUS:
				mode = autoRunCycle();
				break;
			case SEMIAUTO:
				mode = semiAutoRunCycle();
				break;
			case MANUAL:
				mode = manualRunCycle();
				break;	
			default:
				run = false;
			}
			
		}
			
	}
	
	private static int autoRunCycle() {
		// TODO Auto-generated method stub
		//update variables
		//check sensors
		//check state
		//determine next state
		//execute state change
		//verify feedback results
		//write to log file
		
		return AUTONOMOUS;
		
	}

	private static int semiAutoRunCycle() {
		// TODO Auto-generated method stub
		return SEMIAUTO;
	}

	private static int manualRunCycle() {
		// TODO Auto-generated method stub
		return MANUAL;
	}

	

	public Motor[] getMotors() {
		return motors;
	}

	public void setMotors(Motor[] motors) {
		this.motors = motors;
	}

	public Sensor[] getSensors() {
		return sensors;
	}

	public void setSensors(Sensor[] sensors) {
		this.sensors = sensors;
	}

	public double getCurrentMass() {
		return currentMass;
	}

	public void setCurrentMass(double currentMass) {
		this.currentMass = currentMass;
	}

	public double getMassCollected() {
		return massCollected;
	}

	public void setMassCollected(double massCollected) {
		this.massCollected = massCollected;
	}

	public Navigation getNavigation() {
		return navigation;
	}

	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}

	public String[] getDataLog() {
		return dataLog;
	}

	public void setDataLog(String[] dataLog) {
		this.dataLog = dataLog;
	}



}
