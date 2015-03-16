package edu.mjc.lunabot.util;
/*
 * A class to represent a vector with it's
 * magnitude and angle
 * X and Y represent the components of a given vector
 * 
 */
import java.util.*;

public class LVector{
    protected double x, y, mag;
    Angle angle;
    
    public LVector(){
        x = 0;
        y = 0;
        mag = 0;
        angle = new Angle();
    }
    
    /**
     * 
     * 
     * @param mag
     * @param angle: must be in RAD
     */
    public LVector(double mag, double angle){
        this.mag = mag;
        this.angle = new Angle(angle, Angle.RAD);
        this.x = getX();
        this.y = getY();
    }
    
    
    public LVector(int x, int y){
        this.x = x;
        this.y = y;
        this.mag = calcMag(x, y);
        this.angle = new Angle(Math.atan((double)y/(double)x), 1);
    }
    
    
    public static void main(String[] args){
        LVector.test();
    }
    
    public static void test(){
        LVector a = new LVector(1.5, 6);
        LVector b = new LVector(1, 2);
        System.out.println("A:\n" + a);
        System.out.println("B:\n" + b);
        System.out.println("A+B\n" + a.add(b));
        System.out.println(a.normalize());
    }
    
    public double calcMag(double x, double y){
        double m = Math.sqrt(Math.pow(x,2) + Math.pow(y, 2));
        return m;
    }
    
    public double calcAngle(double x, double y){
    	Angle angle = new Angle(Math.atan2(y,x), Angle.RAD);
    	return angle.getRad();
    	
    }
    
    public LVector normalize(){
    	double x = this.x / mag;
    	double y = this.y /mag;
    	return new LVector(calcMag(x,y),calcAngle(x,y));
    }
    
    public double getX(){
        x = mag*Math.cos(angle.getRad());
        return x;
    }
    
    public double getY(){
        y = mag*Math.sin(angle.getRad());
        return y;
    }
    
    public double getMag(){
        return mag;
    }
    
    public double getAngle(){
        return angle.getRad();
    }
    
    public LVector add(LVector v){
        double x = this.x + v.x;
        double y = this.y + v.y;
        return new LVector(x, y);
    }
    
    public String toString(){
        return ("(" + x + ", " + y + ")\n" 
                + "m: " + mag + " Angle: " + angle.getDeg());
    }
    
}
