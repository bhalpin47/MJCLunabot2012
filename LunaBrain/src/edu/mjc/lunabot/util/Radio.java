package edu.mjc.lunabot.util;

public class Radio extends Centroid{
	private int address;									//The Pin Serial Address
	private int  partner_address;							//Partner Address [Tx/Rx Pair]
	private boolean transmitter;							//true = Tx, false = Rx
	private double carrier;									//Carrier Freq. 89-107MHz
	private double offset;									//Error Correction
	private boolean powered;								//true = on, false = off
	private Location location;

	
	public Radio(){
		address = 0;
		partner_address = 0;
		transmitter = false;
		carrier = 0;
		offset = 0;
		powered = false;
		//Location is passed from the extended class of Centroid
	}
	
	public static void main(String[] args){
		//Currently Contains Nothing
		Radio.test();
	}

	public static void test(){
		
		Radio foo = new Radio();
		
		foo.setAddress(0001);
		foo.setCarrier(99.01);
		
		Location point = new Location(0.0,0.0);
		
		foo.setLocation(point);
		foo.setOffset(0.002);
		foo.setPartner(0010);
		foo.setTransmitter(true);
		foo.setPowered(false);
		
		System.out.println("Testing Radio Settings:");
		System.out.println("	Address: " + foo.getAddress());
		System.out.println("	Partner: " + foo.getPartner());
		System.out.println("	Transmitter: " + foo.getTransmitter());
		System.out.println("	Offset: " + foo.getOffset());
		System.out.println("	Powered: " + foo.getPowered());
		System.out.println("	X: " + foo.getX());
		System.out.println("	Y: " + foo.getY());
		
	}
	
	public void setAddress(int address){
		this.address = address;
	}
	public int getAddress(){
		return address;
	}
	public void setPartner(int partner){
		this.partner_address = partner;
	}
	public int getPartner(){
		return partner_address;
	}
	public void setTransmitter(boolean tx){
		this.transmitter = tx;
	}
	public boolean getTransmitter(){
		return transmitter;
	}
	public void setCarrier(double carrier){
		this.carrier = carrier;
	}	
	public double getCarrier(){
		return carrier;
	}	
	public void setOffset(double offset){
		this.offset = offset;
	}	
	public double getOffset(){
		return offset;
	}	
	public void setPowered(boolean on){
		this.powered = on;
	}
	public boolean getPowered(){
		return powered;
	}
	public void setLocation(Location location){
		this.location = location;
	}
	public Location setLocation(){
		return location;
	}
	
}