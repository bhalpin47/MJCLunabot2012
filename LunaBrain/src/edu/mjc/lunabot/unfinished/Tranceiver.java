package edu.mjc.lunabot.unfinished;

import edu.mjc.lunabot.util.Location;

public class Tranceiver {

	/**
	 * @param args
	 */
	
	private double TxFrequency;
	private double RxFrequency;
	private double error;
	private Location location;
	private String label;
	private double[] radii;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public double getTxFrequency() {
		return TxFrequency;
	}

	public void setTxFrequency(double txFrequency) {
		TxFrequency = txFrequency;
	}

	public double getRxFrequency() {
		return RxFrequency;
	}

	public void setRxFrequency(double rxFrequency) {
		RxFrequency = rxFrequency;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double[] getRadii() {
		return radii;
	}

	public void setRadii(double[] radii) {
		this.radii = radii;
	}
	
	

}
