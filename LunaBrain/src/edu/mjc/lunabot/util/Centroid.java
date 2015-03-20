package edu.mjc.lunabot.util;
/*
 * A class to represent the center point between the transceiver modules
 * 
 */
import java.util.*;

public class Centroid extends Location{	

	public Centroid(){
		x = 0;
		y = 0;
	}
	
	public Centroid(Location positionOne, Location positionTwo, Location positionTre){		
		Location Centroid;
		
		Centroid = average(positionOne, positionTwo, positionTre);
		
		x = Centroid.getX();
		y = Centroid.getY();
		
	}
	
	private static void main(String[] args){
		//Currently Contains Nothing
		Centroid.test();
	}
	
	public static void test(){
		Location positionOne = new Location(0,0);
		Location positionTwo = new Location(30,0);
		Location positionTre = new Location(15,25);
		
		Centroid centroid = new Centroid(positionOne, positionTwo, positionTre);
		
		System.out.println("Now testing Centroid");
		System.out.println("	Centroid X: "+ centroid.getX());
		System.out.println("	Centroid Y: "+ centroid.getY());
	}

	public static Location average(Location positionOne, Location positionTwo, Location positionTre){
		Location average = new Location((((positionOne).getX() + positionTwo.getX() + positionTre.getX())/3.0), ((positionOne.getY() + positionTwo.getY() + positionTre.getY())/3.0));
		return average;
	}
	
}