package edu.mjc.lunabot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import edu.mjc.lunabot.ai.PathFinder;
import edu.mjc.lunabot.util.Angle;
import edu.mjc.lunabot.util.FileIO;
import edu.mjc.lunabot.util.Location;
import edu.mjc.lunabot.util.Obstacle;
import edu.mjc.lunabot.util.Orientation;

public class Navigation{
	
	/*File stream*/
	public FileIO io;
    
    /* Static arena data */
    public final double arenaWidth = 388;
    public final double arenaLength = 738;
    private final double obstacleLowerBound = 150;
    private final double obstacleUpperBound = 444;
    private final Location hopper = new Location(arenaWidth/2, 0);
    private Location[] arenaBoundaries = {new Location(0,0), new Location(arenaWidth, 0),
            new Location(0,arenaLength), new Location(arenaWidth,arenaLength)};
    
    /* Dynamic navigation data */
    public Robot bot;
    private boolean autonomous = false;

    private ArrayList<Obstacle> obstacles;
    private ArrayList<Location> pathProjected;
    private ArrayList<Location> pathTraversed;
    private ArrayList<Location> pathDeviation;
    //ticker for current path location
    private int pathIndex = 0;

    private double gravityX = .00011;
    private double gravityY = 0.0002;
    
    
    /* Used for path simulations */
    private PathFinder pathFinder;
    private int stepSize = 5; //number of centimeters between path calculations
    private Location positionAverage = new Location(0, 0);
    private int iterations;
    Location start = new Location(arenaWidth/2, obstacleLowerBound- 40);
	Location target = new Location(arenaWidth/2, obstacleUpperBound + 40);
    private Random random = new Random(new Date().getTime());
    
    public Navigation(){
        this.bot = new Robot(new Orientation(start, 270));
        pathFinder = new PathFinder(this);
        io = new FileIO((new Date().toString().replace(":", "-").replace(" ", "_"))
				+"nav.log", this);
    }

    public Navigation(String fileName){
        this.bot = new Robot(new Orientation(start, 270));
        pathFinder = new PathFinder(this);
        io = new FileIO(fileName, this);
        System.out.println();
        //TODO
    }
    
    public Navigation(Robot bot){
        this.bot = bot;
        pathFinder = new PathFinder(this);
    }
    
    public static void main(String[] args){
        Navigation.test();
    }
    
    private static void test(){
    	Navigation n = new Navigation();
    	System.out.print("Enter number of obstacle configurations: ");
    	Scanner scan = new Scanner(System.in);
    	n.runSimulation(scan.nextInt());
    	scan.close();
    	//System.out.println(line);
    }
    
  
    
    
    
    /**
     * 
     * 
     */
    public void traversePath(){
    	Location current, next;
    	Angle angle;
    	pathTraversed = new ArrayList<Location>(pathProjected.size());
    	for(int i = 0; i < pathProjected.size(); i++){
    		current = pathProjected.get(i);
    		if(i < pathProjected.size() - 1){
    			next = pathProjected.get(i + 1);
        		angle = new Angle(current.angleTo(next) + Math.PI/2, Angle.RAD);
    		}else{
    			angle = new Angle(180, Angle.DEG);
    		}
    		
    		bot.setOrientation(new Orientation(current, angle));
    		pathTraversed.add(current);
    		//io.write(this.toString());
    	}
    }
    
    //retrieves the next destination
    public Location nextPointOnPath(){
    	if(pathIndex == pathProjected.size()){
    		return null;
    	}else{
    		return pathProjected.get(pathIndex++);
    	}
    }
    
    //Used to write the actual location which the robot has arrived after
    //traveling to the nextPointOnPath
    public void updateTraversal(Location current){
    	pathTraversed.add(current);
    	io.write(toString());
    }
    
    public boolean generatePath(){
    	boolean success = pathFinder.findPath(start, target);
    	pathProjected = pathFinder.getPathProjected();
    	return success;
    }
    
    public boolean isOverlap(Obstacle o1, Obstacle o2){
        double d = o1.location.distanceTo(o2.location);
        boolean overlap;
        if(d < o1.getRadius() + o2.getRadius()){
            overlap = true;
        }else{
            overlap = false;
        }
        return overlap;
            
    }


    public void generateObstacles(){ 
    	if(obstacles != null){
    		obstacles.clear();
    	}
    	obstacles = new ArrayList<Obstacle>(6);
        double x, y;
        
        Obstacle o = new Obstacle(0, 0);
        int r = o.getRadius();
        boolean collision = false;
        for(int i = 0 ; i < 6; i++){
            //make sure no obstacles are overlapping
            do{
                collision = false;
                x = r + ((arenaWidth-r) - r) * random.nextDouble();        
                y = (obstacleLowerBound+r) + ((obstacleUpperBound-r) -(obstacleLowerBound+r)) * random.nextDouble();        
                o = new Obstacle(x, y);
                for(int j = 0; j < i; j++){
                    if(isOverlap(o, obstacles.get(j))){
                        collision = true;
                    }
                }
            } while(collision);
                obstacles.add(o);
        } 

    }
    
    public void updateRobot(){
    	String data = io.read();
    	int start, end;
    	double x, y, angle, velocity;
    	start = data.indexOf(FileIO.ROBOT);
    	end = data.indexOf(FileIO.OBSTACLE);
    	data = data.substring(start, end);
    	//System.out.println(data);
    	String[] split = data.split("\\s");
    	for(int i = 0; i < split.length; i++){
    		if(split[i].equals(FileIO.LOCATION.trim())){
    			bot.getOrientation().setX(Double.parseDouble(split[i+1]));
    			bot.getOrientation().setY(Double.parseDouble(split[i+2]));
    			//System.out.println("X and Y updated!");
    		}
    		if(split[i].equals(FileIO.ANGLE.trim())){
    			bot.getOrientation().getAngle().setDeg(Double.parseDouble(split[i+1]));
    			//System.out.println("Angle updated!");
    		}
    		if(split[i].equals(FileIO.VELOCITY.trim())){
    			bot.setVelocity(Integer.parseInt(split[i+1]));
    			//System.out.println("Velocity updated!");
    		}
    	}
    	
    }
    
    public void updatePathProjected(){
    	pathProjected  = new ArrayList<Location>(50);
    	String data = io.read();
    	int start, end;
    	double x, y;
    	start = data.indexOf(FileIO.PATH_PROJECTED);
    	end = data.indexOf(FileIO.PATH_TRAVERSED);
    	data = data.substring(start, end);
    	//System.out.println(data);
    	String[] split = data.split("\\s");
    	for(int i = 0; i < split.length; i++){
    		if(split[i].equals(FileIO.LOCATION.trim())){
    			x = Double.parseDouble(split[i+1]);
    			y = Double.parseDouble(split[i+2]);
    			pathProjected.add(new Location(x, y));
    			//System.out.println("X and Y updated!");
    		}
    	}
    }
    
    public void updatePathTraversed(){
    	pathTraversed  = new ArrayList<Location>(50);
    	String data = io.read();
    	int start, end;
    	double x, y;
    	start = data.indexOf(FileIO.PATH_TRAVERSED);
    	end = data.indexOf(FileIO.PATH_TRAVERSED);
    	data = data.substring(start);
    	//System.out.println(data);
    	String[] split = data.split("\\s");
    	for(int i = 0; i < split.length; i++){
    		if(split[i].equals(FileIO.LOCATION.trim())){
    			x = Double.parseDouble(split[i+1]);
    			y = Double.parseDouble(split[i+2]);
    			pathTraversed.add(new Location(x, y));
    			//System.out.println("X and Y updated!");
    		}
    	}
    }
    
    public void updateObstacles(){
    	obstacles = new ArrayList<Obstacle>(6);
    	String data = io.read();
    	int start, end;
    	double x, y;
    	start = data.indexOf(FileIO.OBSTACLE_LIST);
    	end = data.indexOf(FileIO.PATH_PROJECTED);
    	data = data.substring(start, end);
    	//System.out.println(data);
    	String[] split = data.split("\\s");
    	for(int i = 0; i < split.length; i++){
    		if(split[i].equals(FileIO.LOCATION.trim())){
    			x = Double.parseDouble(split[i+1]);
    			y = Double.parseDouble(split[i+2]);
    			obstacles.add(new Obstacle(new Location(x, y)));
    			//System.out.println("X and Y updated!");
    		}
    	}
    }

	public String toString(){
        StringBuffer s = new StringBuffer(FileIO.NAVIGATION);
        s.append(FileIO.ARENA_LENGTH);
        s.append(arenaLength);
        s.append(FileIO.ARENA_WIDTH);
        s.append(arenaWidth);
        s.append(FileIO.OBSTACLE_LB);
        s.append(obstacleLowerBound);
        s.append(FileIO.OBSTACLE_UB);
        s.append(obstacleUpperBound);
        s.append(FileIO.HOPPER);
        s.append(hopper.toString());
        s.append(FileIO.ARENA_BOUNDS);
        for(int i = 0; i < arenaBoundaries.length; i++){
            s.append(arenaBoundaries[i].toString());
            s.append(" ");
        }
        s.append(bot.toString());
        s.append(FileIO.AUTONOMOUS);
        s.append(autonomous);
        s.append(" ");
        s.append(FileIO.OBSTACLE_LIST);
        if(obstacles != null && !obstacles.isEmpty()){
	        for(int i = 0; i < obstacles.size(); i++){
	            s.append(obstacles.get(i).toString());
	            s.append(" ");
	        }
        }
        s.append(FileIO.PATH_PROJECTED);
        if(pathProjected != null && !pathProjected.isEmpty()){
	        for(int i = 0; i < pathProjected.size(); i++){
	            s.append(pathProjected.get(i).toString());
	            s.append(" ");
	        }
        }
        s.append(FileIO.PATH_TRAVERSED);
        if(pathTraversed != null && !pathTraversed.isEmpty()){
	        for(int i = 0; i < pathTraversed.size(); i++){
	            s.append(pathTraversed.get(i).toString());
	            s.append(" ");
	        }
        }
        s.append("End");
/*
        for(int i = 0; i < pathDeviation.size(); i++){
            s.append(pathDeviation.get(i).toString());
            s.append(" ");
        }
*/
        
        return s.toString();
    }

	/* ---------------------Getters and Setters-------------------*/
    public double getObstacleLowerBound(){
        return obstacleLowerBound;
    }
    public double getObstacleUpperBound(){
        return obstacleUpperBound;
    }
    public Robot getBot(){
        return bot;
    }
    public void setBot(Robot bot){
        this.bot = bot;
    }
    public boolean isAutonomous(){
        return autonomous;
    }
    public void setAutonomous(boolean autonomous){
        this.autonomous = autonomous;
    }
    public Location[] getArenaBoundaries(){
        return arenaBoundaries;
    }
    public void setArenaBoundaries(Location[] arenaBoundaries){
        this.arenaBoundaries = arenaBoundaries;
    }   
    public ArrayList<Obstacle> getObstacles(){
        return obstacles;
    }
    public void setObstacles(ArrayList<Obstacle> obstacles){
        this.obstacles = obstacles;
    }
    public ArrayList<Location> getPathProjected(){
        return pathProjected;
    }
    public void setPathProjected(ArrayList<Location> pathProjected){
        this.pathProjected = pathProjected;
    }
    public ArrayList<Location> getPathTraversed(){
        return pathTraversed;
    }
    public void setPathTraversed(ArrayList<Location> pathTraversed){
        this.pathTraversed = pathTraversed;
    }
    public ArrayList<Location> getPathDeviation(){
        return pathDeviation;
    }
    public void setPathDeviation(ArrayList<Location> pathDeviation){
        this.pathDeviation = pathDeviation;
    }
    public Location getPositionAverage(){
        return positionAverage;
    }
    public void setPositionAverage(Location positionAverage){
        this.positionAverage = positionAverage;
    }
    public int getIterations(){
        return iterations;
    }
    public void setIterations(int iterations){
        this.iterations = iterations;
    }
    public double getGravityX(){
        return gravityX;
    }
    public void setGravityX(double gravityX){
        this.gravityX = gravityX;
    }
    public double getGravityY(){
        return gravityY;
    }
    public void setGravityY(double gravityY){
        this.gravityY = gravityY;
    }
    public double getArenaWidth(){
        return arenaWidth;
    }
    public double getArenaLength(){
        return arenaLength;
    }
    public Location getHopper(){
        return hopper;
    }
    public FileIO getIo() {
		return io;
	}
	public void setIo(FileIO io) {
		this.io = io;
	}
	public int getStepSize() {
		return stepSize;
	}
	public void setStepSize(int stepSize) {
		this.stepSize = stepSize;
	}
	public Location getStart() {
		return start;
	}
	public void setStart(Location start) {
		this.start = start;
	}
	public Location getTarget() {
		return target;
	}
	public void setTarget(Location target) {
		this.target = target;
	}
	
	/* ---------------------Methods for simulation-------------------*/
	  /**
     * Runs (reps) number of simulations,
     * each with new obstacle locations.
     * prints percentage of success
     * @param reps
     */
    
    public void runSimulation(int reps){
    	int success=0, total=0;

    	boolean result = true;
    	System.out.println("Simulating " + reps + " layouts...");
    	for(int i = 0; i < reps; i++){
    		generateObstacles();
            pathFinder.setObstacles(obstacles);
            result = generatePath();
    		io.write(result + " " + toString());
    		//System.out.println(result);
    		if(result){
    			success++;
    		}
    		total++;
    		System.out.printf("Layout %d complete.\n", total);
    	}
    	double percentage = ((double)success/(double)total) * 100;
    	System.out.printf("%3.2f%% success, after %d runs.", percentage, reps);
    	
    }
    
    public void updateFromLine(int line){
    	if(io.readLine(line) != null){
    		//updateRobot(line);
    		updateObstacles(line);
    		updatePathProjected(line);
    	}
    }
	
	/**
	 * Updates variables from specific line in file
	 * @param line
	 */
    public void updateRobot(int line){
    	String data = io.readLine(line);
    	int start, end;
    	double x, y, angle, velocity;
    	start = data.indexOf(FileIO.ROBOT);
    	end = data.indexOf(FileIO.OBSTACLE);
    	data = data.substring(start, end);
    	//System.out.println(data);
    	String[] split = data.split("\\s");
    	for(int i = 0; i < split.length; i++){
    		if(split[i].equals(FileIO.LOCATION.trim())){
    			bot.getOrientation().setX(Double.parseDouble(split[i+1]));
    			bot.getOrientation().setY(Double.parseDouble(split[i+2]));
    			//System.out.println("X and Y updated!");
    		}
    		if(split[i].equals(FileIO.ANGLE.trim())){
    			bot.getOrientation().getAngle().setDeg(Double.parseDouble(split[i+1]));
    			//System.out.println("Angle updated!");
    		}
    		if(split[i].equals(FileIO.VELOCITY.trim())){
    			bot.setVelocity(Integer.parseInt(split[i+1]));
    			//System.out.println("Velocity updated!");
    		}
    	}
    	
    }
    /**
     * Updates variables from specific line
     * @param line
     */
    public void updatePathProjected(int line){
    	pathProjected  = new ArrayList<Location>(50);
    	String data = io.readLine(line);
    	int start, end;
    	double x, y;
    	start = data.indexOf(FileIO.PATH_PROJECTED);
    	end = data.indexOf(FileIO.PATH_TRAVERSED);
    	data = data.substring(start, end);
    	//System.out.println(data);
    	String[] split = data.split("\\s");
    	for(int i = 0; i < split.length; i++){
    		if(split[i].equals(FileIO.LOCATION.trim())){
    			x = Double.parseDouble(split[i+1]);
    			y = Double.parseDouble(split[i+2]);
    			pathProjected.add(new Location(x, y));
    			//System.out.println("X and Y updated!");
    		}
    	}
    }
    
    /**
     * Updates variables from specific line
     * @param line
     */
    public void updatePathTraversed(int line){
    	pathTraversed  = new ArrayList<Location>(50);
    	String data = io.readLine(line);
    	int start, end;
    	double x, y;
    	start = data.indexOf(FileIO.PATH_TRAVERSED);
    	end = data.indexOf(FileIO.PATH_TRAVERSED);
    	data = data.substring(start);
    	//System.out.println(data);
    	String[] split = data.split("\\s");
    	for(int i = 0; i < split.length; i++){
    		if(split[i].equals(FileIO.LOCATION.trim())){
    			x = Double.parseDouble(split[i+1]);
    			y = Double.parseDouble(split[i+2]);
    			pathTraversed.add(new Location(x, y));
    			//System.out.println("X and Y updated!");
    		}
    	}
    }
    
    /**
     * Updates variables from specific line
     * @param line
     */
    public void updateObstacles(int line){
    	obstacles = new ArrayList<Obstacle>(6);
    	String data = io.readLine(line);
    	int start, end;
    	double x, y;
    	start = data.indexOf(FileIO.OBSTACLE_LIST);
    	end = data.indexOf(FileIO.PATH_PROJECTED);
    	data = data.substring(start, end);
    	//System.out.println(data);
    	String[] split = data.split("\\s");
    	for(int i = 0; i < split.length; i++){
    		if(split[i].equals(FileIO.LOCATION.trim())){
    			x = Double.parseDouble(split[i+1]);
    			y = Double.parseDouble(split[i+2]);
    			obstacles.add(new Obstacle(new Location(x, y)));
    			//System.out.println("X and Y updated!");
    		}
    	}
    }
    

    
}
