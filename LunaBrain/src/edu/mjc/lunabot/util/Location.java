package edu.mjc.lunabot.util;


public class Location{
    protected double x;
    protected double y;
    
    //Constructors must come first
    public Location(){
        x = 0;
        y = 0;
    }
    
    public Location(Location l){
    	this.x = l.x;
    	this.y = l.y;
    }
    
    public Location(int x, int y){
        this.x = (double)x;
        this.y = (double)y;
    }
    
    public Location(double x, double y){
        this.x = x;
        this.y = y;
    }
    //main and test should reside right after constructors
    private static void main(String[] args){
        Location.test();
    }
    
    public static void test(){
        Location a = new Location();
        Location b = new Location(3, 4);
        Location c = new Location(2.5, 3.5);
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);
        System.out.println("a->b " + a.distanceTo(b));
        System.out.println("Angle a->b " + a.angleTo(b));
        System.out.println("a->c " + a.distanceTo(c));
        System.out.println("Angle a->c " + a.angleTo(c));
    }
    
    //accessor methods(methods that do not change values) come next
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public double distanceTo(Location there){
        //uses equation sqrt((x2-x1)^2+(y2-y1)^2)
        double distance = Math.sqrt(Math.pow((there.x-this.x), 2)+Math.pow((there.y-this.y), 2));
        return distance;
    }
    
    //returns angle in degrees from here to there
    public double angleTo(Location there){
        Angle angle = new Angle(Math.atan2(((there.y)-y), ((there.x)-x)), 1);
        return angle.getRad();
    }
    
    public LVector vectorTo(Location there){
        LVector v = new LVector(this.distanceTo(there), this.angleTo(there));
        return v;
    }
    
    public Location midpoint(Location there){
    	double x, y;
    	x = (this.x + there.x)/2;
    	y = (this.y + there.y)/2;
    	return new Location(x, y);
    }
    
    public Location getLocation(){
    	return this;
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer(FileIO.LOCATION);
        s.append(this.x);
        s.append(" ");
        s.append(this.y);
        return s.toString();
    }
    
    
    
}
