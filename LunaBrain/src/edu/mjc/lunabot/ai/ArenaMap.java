package edu.mjc.lunabot.ai;

import java.util.ArrayList;

import edu.mjc.lunabot.util.FileIO;
import edu.mjc.lunabot.util.Location;

public class ArenaMap {
	private int mapWidth;
    private int mapLength;
    private ArrayList<ArrayList<Node>> map;
    private Location start = new Location();
    private Location target = new Location();

   
    
    ArenaMap(int mapWidth, int mapLength) {
            this.mapWidth = mapWidth;
            this.mapLength = mapLength;
            
            createMap();
            //System.out.println("\tMap Created");
            registerEdges();
            //System.out.println("\tMap Node edges registered");
    }
    private void createMap() {
            map = new ArrayList<ArrayList<Node>>();
            for (int x=0; x<mapWidth; x++) {
                    map.add(new ArrayList<Node>());
                    for (int y=0; y<mapLength; y++) {
                            map.get(x).add(new Node(x,y));
                    }
            }
    }

    /**
     * Registers the nodes edges (connections to its neighbors).
     */
    private void registerEdges() {
            for ( int x = 0; x < mapWidth-1; x++ ) {
                    for ( int y = 0; y < mapLength-1; y++ ) {
                            Node node = map.get(x).get(y);
                            if (!(y==0))
                                    node.setNorth(map.get(x).get(y-1));
                            if (!(y==0) && !(x==mapWidth))
                                    node.setNorthEast(map.get(x+1).get(y-1));
                            if (!(x==mapWidth))
                                    node.setEast(map.get(x+1).get(y));
                            if (!(x==mapWidth) && !(y==mapLength))
                                    node.setSouthEast(map.get(x+1).get(y+1));
                            if (!(y==mapLength))
                                    node.setSouth(map.get(x).get(y+1));
                            if (!(x==0) && !(y==mapLength))
                                    node.setSouthWest(map.get(x-1).get(y+1));
                            if (!(x==0))
                                    node.setWest(map.get(x-1).get(y));
                            if (!(x==0) && !(y==0))
                                    node.setNorthWest(map.get(x-1).get(y-1));
                    }
            }
    }
    
    

    public ArrayList<ArrayList<Node>> getNodes() {
            return map;
    }
    public void setObstacle(int x, int y, boolean isObstacle) {
            map.get(x).get(y).setObstacle(isObstacle);
    }

    public Node getNode(int d, int e) {
            return map.get(d).get(e);
    }

    public void setStartLocation(int x, int y) {
            map.get((int)start.getX()).get((int)start.getY()).setStart(false);
            map.get(x).get(y).setStart(true);
            start.setX(x);
            start.setY(y);
    }

    public void setTargetLocation(int x, int y) {
            map.get((int)target.getX()).get((int)target.getY()).setTarget(false);
            map.get(x).get(y).setTarget(true);
            target.setX(x);
            target.setY(y);
    }



    public Location getStartLocation() {
            return start;
    }
    
    public Node getStartNode() {
            return map.get((int)start.getX()).get((int)start.getY());
    }
    
    public Location getTargetLocation(){
    	return target;
    }
    
    public Node getTargetNode() {
            return map.get((int)target.getX()).get((int)target.getY());
    }
    
    public double getDistanceBetween(Node node1, Node node2) {
            //if the nodes are on top or next to each other, return 1
            if (node1.getX() == node2.getX() || node1.getY() == node2.getY()){
                    return 1;
            } else { //if they are diagonal to each other return diagonal distance: sqrt(1^2+1^2)
                    return Math.sqrt(2);
            }
    }
    
    public int getMapWidth() {
            return mapWidth;
    }
    public int getMapLength() {
            return mapLength;
    }
    public void clear() {
            start.setX(0);
            start.setY(0);
            target.setX(0);
            target.setY(0);
            createMap();
            registerEdges();
    }
}
