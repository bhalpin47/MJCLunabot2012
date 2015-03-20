package edu.mjc.lunabot.util;


/**
 * Attributes:
 * location
 * radius
 * isCrater
 */
public class Obstacle{
    public Location location;
    private int radius = 15;
    private boolean crater = false;
    
    public Obstacle(Location l){
        location = l;
    }
    
    public Obstacle(Location l, int radius){
        location = l;
        this.radius = radius;
    }
    
    public Obstacle(double x, double y){
        location = new Location(x, y);
    }
    
    
    
    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public int getRadius(){
        return radius;
    }
    
    public void setRadius(int r){
        radius = r;
    }
    
    public boolean isCrater(){
        return crater;
    }
    
    public void setCrater(boolean b){
        crater = b;
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer(FileIO.OBSTACLE);
        s.append(location.toString());
        s.append(FileIO.RADIUS);
        s.append(radius);
        s.append(FileIO.CRATER);
        s.append(crater);
        return  s.toString();
    }
    
}
