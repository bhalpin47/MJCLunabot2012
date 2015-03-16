package edu.mjc.lunabot.ai;
import java.util.ArrayList;
import java.util.Arrays;

import edu.mjc.lunabot.*;
import edu.mjc.lunabot.util.Angle;
import edu.mjc.lunabot.util.FileIO;
import edu.mjc.lunabot.util.Location;
import edu.mjc.lunabot.util.Obstacle;

public class PathFinder {
    private ArrayList<Location> pathProjected = new ArrayList<Location>();
	
	private Robot bot;
	private ArrayList<Obstacle> obstacles;
	private double arenaWidth;
	private double arenaLength;
	private double obstacleLowerBound;
	private double obstacleUpperBound;
	double resolution = 1;
	
	private ArenaMap map;
	private AStar aStar;


	private FileIO io = new FileIO("path.log");

	
	public PathFinder(Navigation n){
		bot = n.getBot();
		obstacles = n.getObstacles();
		arenaWidth = n.getArenaWidth();
		arenaLength = n.getArenaLength();
		obstacleLowerBound = n.getObstacleLowerBound();
		obstacleUpperBound = n.getObstacleUpperBound();
		map = new ArenaMap((int)(arenaWidth/resolution), (int)(arenaLength/resolution));
		aStar = new AStar(map);
		
		
	}
	
	public static void main(String[] args){
		Navigation n = new Navigation();
		n.updateFromLine(0);
		PathFinder p = new PathFinder(n);
		//System.out.println(p.generatePath(n.getStart()));
		p.createCollisionMap();
		Location start = new Location((p.getArenaWidth()/2)/p.resolution, (p.getObstacleLowerBound())/p.resolution);
		Location target = new Location((p.getArenaWidth()/2)/p.resolution, (p.getObstacleUpperBound())/p.resolution);
		
		p.findPath(start, target);
		//p.printCollisionMap();
		
	}
	
	
	/**
	 * Finds a path from start to target, given a particular map layout
	 * Pre-condition: global map variable must be initialized
	 * @param start
	 * @param target
	 * @return
	 */
	public boolean findPath(Location start, Location target){
		map.clear();
		createCollisionMap();
		boolean pathFound = generatePath(start, target);
		if(pathFound){
			io.write(pathFound + " " + toString());
			//aStar.printPath();
		}else{
			io.write("" + pathFound);
		}
		return pathFound;
	}
	
	public boolean generatePath(Location start, Location target){
		Path path = aStar.calcShortestPath(start, target);
		if(path == null){
			return false;
		}else{
			pathProjected = path.getPathList();
			return true;
		}
	}
	
	public void createCollisionMap(){
		boolean isCollision;
		Location current = new Location();
		for(int y = 0; y < map.getMapLength(); y++){
			for(int x = 0; x < map.getMapWidth(); x++){
				isCollision = false;
				current.setY((y*resolution));
				current.setX((x*resolution));
				if(isWallCollision(current) || isObstacleCollision(current)){
					isCollision = true;
				}
				map.setObstacle(x, y, isCollision);
			}
		}
	}
	
	

    
    public boolean isWallCollision(Location current){
    	boolean collision = false;
    	if(current.getY() - bot.getEdgeDist(Angle.degToRad(90)) <= 0 || 
    			current.getY() + bot.getEdgeDist(Angle.degToRad(270)) >= arenaLength ||
    			current.getX() - bot.getEdgeDist(Angle.degToRad(180)) <= 0 ||
    			current.getX() + bot.getEdgeDist(Angle.degToRad(0)) >= arenaWidth){
    		collision = true;
    	}
    	return collision;
    }
    
    public boolean isObstacleCollision(Location current){
        double dCenter, dMin, dCurrent, aBetween;
        Obstacle obstacle;
        for(int i = 0; i < obstacles.size(); i++){
        	obstacle = obstacles.get(i);
	        dCenter = current.distanceTo(obstacle.location);    		//get center to center distance
	        aBetween = current.angleTo(obstacle.location);      		//get angle between bot and obstacle
	        dMin = bot.getEdgeDist(aBetween) + obstacle.getRadius();  	//get minimum distance to avoid a collision
	        dCurrent = dCenter - dMin;                                  //calculate current distance
	        //System.out.println("dCurrent =" + dCurrent);
	        if(dCurrent <= 0){                                         	//if current <= 0 collision has occured
	           return true;
	        }
        }
        return false;
    }
    
    public void printCollisionMap(){
    	int point;
    	for(int x = 0; x < map.getMapWidth(); x++){
    		for(int y = 0; y < map.getMapLength(); y++){	
				if(map.getNode(x, y).isObstacle == true){
					point = 1;
				}else{
					point = 0;
				}
				System.out.print(point);
			}
			System.out.println();
    	}
    }
    
    public String toString(){
    	StringBuffer s = new StringBuffer(FileIO.PATH_FINDER);
        s.append(bot.toString());
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
        s.append("End");
/*
        for(int i = 0; i < pathDeviation.size(); i++){
            s.append(pathDeviation.get(i).toString());
            s.append(" ");
        }
*/
        
        return s.toString();
    }
    
    public void clearMap(){
    	map.clear();
    }

	public ArrayList<Location> getPathProjected() {
		return pathProjected;
	}

	public void setPathProjected(ArrayList<Location> pathProjected) {
		this.pathProjected = pathProjected;
	}

	public Robot getBot() {
		return bot;
	}

	public void setBot(Robot bot) {
		this.bot = bot;
	}

	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	public double getArenaWidth() {
		return arenaWidth;
	}

	public void setArenaWidth(double arenaWidth) {
		this.arenaWidth = arenaWidth;
	}

	public double getArenaLength() {
		return arenaLength;
	}

	public void setArenaLength(double arenaLength) {
		this.arenaLength = arenaLength;
	}

	public double getObstacleLowerBound() {
		return obstacleLowerBound;
	}

	public void setObstacleLowerBound(double obstacleLowerBound) {
		this.obstacleLowerBound = obstacleLowerBound;
	}

	public double getObstacleUpperBound() {
		return obstacleUpperBound;
	}

	public void setObstacleUpperBound(double obstacleUpperBound) {
		this.obstacleUpperBound = obstacleUpperBound;
	}

	public double getResolution() {
		return resolution;
	}

	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	public ArenaMap getArenaMap() {
		return map;
	}

	public void setMap(ArenaMap map) {
		this.map = map;
	}

	public FileIO getIo() {
		return io;
	}

	public void setIo(FileIO io) {
		this.io = io;
	} 
    
}
