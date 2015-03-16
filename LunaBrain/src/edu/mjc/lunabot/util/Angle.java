package edu.mjc.lunabot.util;


/* Angle is a container class for a double 
 * representing an angle in radians.
 * This class ensures an angle will be [0, 2PI).
 * It also provides utilities for converting from deg to rad,
 * and can take input in either deg or rad.
 * 
 */
public class Angle{
    private double angle;
    public static final int DEG = 0;
    public static final int RAD = 1;
    
    /**
     * Initializes an angle of 0
     */
    public Angle(){
        angle = 0;
    }
    
    /**
     * 
     * @param angle: in deg or rad
     * @param fmt: 0 for DEG or any other value for RAD by default
     */
    public Angle(double angle, int fmt){
        
        if(fmt == DEG){
          angle = angle*(Math.PI/180.0);
        }
        angle = checkRange(angle);
        
        this.angle = angle;
    }
    
    public static void main(String[] args){
        Angle.test();
    }
    
    public static void test(){
        System.out.println("*Angle class test*");
        Angle a, b, c, d, e;
        a = new Angle();
        b = new Angle(180*5, DEG);
        c = new Angle(Math.PI*5, 1);
        d = new Angle(-180*5, DEG);
        e = new Angle(-Math.PI*5, 1);
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);
        System.out.println("d: " + d);
        System.out.println("e: " + e);
        
    }
    
    /**
     * 
     * @param angle: angle to be checked
     * @return: an angle from [0, 2PI)
     */
    private double checkRange(double angle){
        double limit = Math.PI * 2;
        while(angle > limit || angle < (0)){
            if(angle > (limit)){
                angle -= (limit);
            }else if(angle < (0)){
                angle += (limit);
            }      
        }
        return angle;
    }
    /**
     * 
     * @param angle: in DEG
     * @return: result from addition in RAD within range [0, 2PI)
     * does not affect instance's angle value
     */
    public double addDeg(double angle){
        
        angle = angle*(Math.PI/180.0);
        angle = checkRange(this.angle + angle);
        
        return angle;
    } 
    
    public double subDeg(double angle){
        
        angle = angle*(Math.PI/180.0);
        angle = checkRange(this.angle - angle);
        
        return angle;
    } 
    
    /**
     * 
     * @param angle: in RAD
     * @return: in RAD
     */
    public double addRad(double angle){
        angle = checkRange(this.angle + angle);
        return angle;
    }
    
    public double subRad(double angle){
        angle = checkRange(this.angle - angle);
        return angle;
    }
    
    /**
     * 
     * @param angle: in Degrees
     * @return: angle in Radians
     */
    public static double degToRad(double angle){
        angle = angle*(Math.PI/180.0);
        return angle;
    }
    
    public static double radToDeg(double angle){
        return angle*(180.0/Math.PI);
    }
    
    public double getRad(){
        return angle;
    }
    
    public double getDeg(){
        return angle*(180.0/Math.PI);
    }
    
    public void setRad(double angle){
        angle = checkRange(angle);
        this.angle = angle;
    }
    
    public void setDeg(double angle){
        angle = angle*(Math.PI/180.0);
        angle = checkRange(angle);
        this.angle = angle;
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer(FileIO.ANGLE);
        s.append(getDeg());
        return s.toString();
    }
}
