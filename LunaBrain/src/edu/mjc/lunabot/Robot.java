package edu.mjc.lunabot;

import edu.mjc.lunabot.util.FileIO;
/*
 * A container class for all of the robot's navigation data
 * 
 */
import edu.mjc.lunabot.util.Obstacle;
import edu.mjc.lunabot.util.Orientation;

public class Robot{
    public final double length = 60;
    public final double width = 60;
    private double safetyMargin = 5;    // zone of avoidance
    private Orientation orientation;
    private int velocity = 5;

    
    public Robot(Orientation o){
        orientation = o;
    }
    
    
    public static void main(String[] args){
        //create a robot with Location (0,0) and angle 0;
        Navigation n = new Navigation(new Robot(new Orientation()));
        Obstacle o, p, q;
        o = new Obstacle(30, 0);
        p = new Obstacle(45, 0);
        q = new Obstacle(55, 0);

   
        
    }
    
    public Orientation getOrientation(){
        return orientation;
    }
    
    /**
     * 
     * @param angle: angle in RAD of the vector between the bot and an obstacle
     * @return: distance to the edge of the bot in the given direction with
        the current orientation
     */
    public double getEdgeDist(double aObs){
        double aBot = orientation.getRad();
        
        //double aObs = Math.abs(angle - aBot);
        double aMax = Math.atan((width/2.0)/(length/2.0));
        //System.out.println("aBot = " + Angle.radToDeg(aBot) + "\naObs = " + Angle.radToDeg(aObs));
        double edge;
        if((aBot + aMax > aObs && aBot - aMax < aObs) ||
          ((aBot - (Math.PI) + aMax > aObs) && (aBot - (Math.PI) - aMax < aObs))){
            edge = (length/2.0) / (Math.abs(Math.cos(aObs - aBot)));
        }else{
            edge = (width/2.0) / (Math.abs(Math.sin(aObs - aBot)));
        }
        //System.out.println("dEdge =" + edge); 
        return edge;
    }
    
    public double getSafetyMargin(){
        return safetyMargin;
    }


    public void setSafetyMargin(double safetyMargin){
        this.safetyMargin = safetyMargin;
    }


    public int getVelocity(){
        return velocity;
    }


    public void setVelocity(int velocity){
        this.velocity = velocity;
    }


    public double getLength(){
        return length;
    }


    public double getWidth(){
        return width;
    }


    public void setOrientation(Orientation orientation){
        this.orientation = orientation;
    }


    public String toString(){
        StringBuffer s = new StringBuffer(FileIO.ROBOT);
        s.append(FileIO.LENGTH);
        s.append(length);
        s.append(FileIO.WIDTH);
        s.append(width);
        s.append(FileIO.SAFETY_MARGIN);
        s.append(safetyMargin);
        s.append(orientation.toString());
        s.append(FileIO.VELOCITY);
        s.append(velocity);
        
        return s.toString();      
    }
}

