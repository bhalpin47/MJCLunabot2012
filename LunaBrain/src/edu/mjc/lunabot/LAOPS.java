package edu.mjc.lunabot;
/*
 * A utility for all local positioning data
 * 
 */
import java.util.*;

import edu.mjc.lunabot.util.Centroid;
import edu.mjc.lunabot.util.Location;
import edu.mjc.lunabot.util.Orientation;

public class LAOPS {
	
	public static void main(String[] args){
		LAOPS.test();
	}
	
	public static void test(){
		
		double rad1 = 17.3205081;
		double rad2 = 17.3205081;
		double rad3 = 17.3205081;
		
		Location reference = triangulate(rad1,rad2,rad3);
		
		System.out.println("Testing triangulate...");
		reference.toString();
		System.out.println("	X: " + reference.getX() + " " + "\n	Y: " +reference.getY());
		
		Location pointOne = new Location(15, 25.98);		//Front      Location
		Location pointTwo = new Location(30, 0);			//Back Right Location
		Location pointTre = new Location(0,0);				//Back Left  Location
		
		Orientation orientation = direction(pointOne, pointTwo, pointTre);
		
		System.out.println("Testing");
		System.out.println(orientation.toString());
		
	}

	public static Location triangulate(double rad1, double rad2, double rad3){
		
		Location c1 = new Location(0,0);				//Back Left  Location
		Location c2 = new Location(30,0);				//Back Right Location
		Location c3 = new Location(15,24.5);			//Front      Location
		
		double X = (Math.pow(rad1,2)-(Math.pow(rad2,2))+ Math.pow(c2.getX(), 2))/(2*c2.getX());
		double Y = (((Math.pow(rad1,2))-(Math.pow(rad3,2))+(Math.pow(c3.getX(),2))+(Math.pow(c3.getY(),2)))/(2*c3.getY()))-((c3.getX()/c3.getY())*X);
		
		Location reference = new Location(X,Y);
		
		return reference;								//Location of the Reference Point
	}
	
	public static Orientation direction(Location front, Location bkright, Location bkleft){
		
		Centroid centroid = new Centroid(front, bkright, bkleft);
		
		double vectorOne = centroid.angleTo(front);
		double vectorTwo = centroid.angleTo(bkright);
		double vectorTre = centroid.angleTo(bkleft);
		
		vectorTwo = vectorTwo - 240.0;
		vectorTre = vectorTre - 120.0;
		
		double direction = ((vectorOne + vectorTwo + vectorTre)/(3.0));
		
		Orientation orientation = new Orientation(centroid, direction);
		
		return orientation;
		
	}


}
