package edu.mjc.lunabot.util;
/*
 * A container class for the location and angle of an object
 * Location stores (X,Y) coordinates
 * Angle stores an angle in the range [0,2PI)
 * 
 */
public class Orientation extends Location{
    private Angle angle;
    
    
    /**
     * Creates a new object at loc(0,0)
     * with angle = 0;
     */
    public Orientation(){
        super();
        angle = new Angle();
        
    }
 /**
  * 
  * @param x
  * @param y
  * @param angle: an object is facing in *DEG*
  */
    public Orientation(double x, double y, double angle){
        this.x = x;
        this.y = y;
        this.angle = new Angle(angle, Angle.DEG);
        
    }
    
    public Orientation(Location l, Angle a){
    	this.x = l.getX();
    	this.y = l.getY();
    	this.angle = a;
    }
    
    /**
     * 
     * @param location
     * @param angle: in *DEG*
     */
    public Orientation(Location location, double angle){
        this.x = location.getX();
        this.y = location.getY();
        this.angle = new Angle(angle, Angle.DEG);
    }
    
    public static void main(String[] args){
        Orientation.test();
    }
    
    public static void test(){
        Orientation o1 = new Orientation();
        Orientation o2 = new Orientation(new Location(3, 4), 90);
        System.out.println(o1);
        System.out.println(o2);
        o1.vectorTo(o2.getLocation());
        System.out.println("Calculating vector from point 1 -> 2");
        System.out.println(o1);
        
    }
    
    public Location getLocation(){
    	return new Location(x, y);
    }
    
    public double getRad(){
        return this.angle.getRad();
    }
    
    public double getDeg(){
        return this.angle.getDeg();
    }

    

    
    /**
     * 
     * @param loc: new location
     */
    public void setLocation(Location loc){
        this.x = loc.getX();
        this.y = loc.getY();
    }
    
    /**
     * New Coordinates:
     * @param x
     * @param y
     */
    public void setLocation(double x, double y){
        this.x = x;
        this.y = y;
    }
    

    /**
     * @return Angle(wrapper object)
     */
    public Angle getAngle(){
        return angle;
    }
    
    /**
     * 
     * @param angle: in DEG
     */
    public void setAngle(double angle){
        
        this.angle.setDeg(angle);
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer(super.toString());
        s.append(angle.toString());
        return s.toString();
    }
    
}
