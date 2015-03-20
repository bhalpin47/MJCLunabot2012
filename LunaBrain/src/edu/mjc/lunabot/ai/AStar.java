package edu.mjc.lunabot.ai;

import java.util.ArrayList;
import java.util.Collections;

import edu.mjc.lunabot.util.FileIO;
import edu.mjc.lunabot.util.Location;



public class AStar {
        private ArenaMap map;
        private Heuristic heuristic = new Heuristic();

        /**
         * closedList The list of Nodes not searched yet, sorted by their distance to the goal as guessed by heuristic.
         */
        private ArrayList<Node> closedList;
        private SortedNodeList openList;
        private Path shortestPath;
        FileIO io = new FileIO("map.log");
        
        AStar(ArenaMap map) {
                this.map = map;
                closedList = new ArrayList<Node>();
                openList = new SortedNodeList();
        }
        
        public Path calcShortestPath(Location start, Location target) {

                
                //mark start and goal node
                map.setStartLocation((int)start.getX(), (int)start.getY());
                map.setTargetLocation((int)target.getX(), (int)target.getY());
                
                //Check if the goal node is blocked (if it is, it is impossible to find a path there)
                if (map.getNode((int)target.getX(), (int)target.getY()).isObstacle) {
                        return null;
                }
                
                map.getStartNode().setG(0);
                closedList.clear();
                openList.clear();
                openList.add(map.getStartNode());
                
                //while we haven't reached the goal yet
                while(openList.size() != 0) {
                		/*
                        if (openList.size()%100 == 0)
                                io.write("\topenList.size() = "+ openList.size());
                        */
                        //get the first Node from non-searched Node list, sorted by lowest distance from our goal as guessed by our heuristic
                        Node current = openList.getFirst();
                        
                        // check if our current Node location is the goal Node. If it is, we are done.
                        if(current.getX() == map.getTargetLocation().getX() && current.getY() == map.getTargetLocation().getY()) {
                                return reconstructPath(current);
                        }
                        
                        //move current Node to the closed (already searched) list
                        openList.remove(current);
                        closedList.add(current);
                        
                        //go through all the current Nodes neighbors and calculate if one should be our next step
                        for(Node neighbor : current.getNeighborList()) {
                                boolean neighborIsBetter;
                                
                                //if we have already searched this Node, don't bother and continue to the next one 
                                if (closedList.contains(neighbor))
                                        continue;
                                
                                //also just continue if the neighbor is an obstacle
                                if (!neighbor.isObstacle) {
                                        
                                        // calculate how long the path is if we choose this neighbor as the next step in the path 
                                        double neighborDistanceFromStart = (current.getG() + map.getDistanceBetween(current, neighbor));
                                        
                                        //add neighbor to the open list if it is not there
                                        if(!openList.contains(neighbor)) {
                                                openList.add(neighbor);
                                                neighborIsBetter = true;
                                        //if neighbor is closer to start it could also be better
                                        } else if(neighborDistanceFromStart < current.getG()) {
                                                neighborIsBetter = true;
                                        } else {
                                                neighborIsBetter = false;
                                        }
                                        // set neighbors parameters if it is better
                                        if (neighborIsBetter) {
                                                neighbor.setPreviousNode(current);
                                                neighbor.setG(neighborDistanceFromStart);
                                                neighbor.setH(heuristic.getHeuristic(neighbor, map.getTargetNode()));
                                        }
                                }
                                
                        }
                        
                }
                return null;
        }
        
        public void printPath() {
                Node node;
                for(int x=0; x<map.getMapWidth(); x++) {
                        for(int y=0; y<map.getMapLength(); y++) {
                                node = map.getNode(x, y);
                                if (node.isObstacle) {
                                        System.out.print("X");
                                } else if (node.isStart) {
                                        System.out.print("s");
                                } else if (node.isStart) {
                                        System.out.print("g");
                                } else if (shortestPath.contains(node.getX(), node.getY())) {
                                        System.out.print("¤");
                                } else {
                                        System.out.print(" ");
                                }
                        }
                        System.out.println();
                }
        }
        
        private Path reconstructPath(Node node) {
                Path path = new Path();
                while(!(node.getPreviousNode() == null)) {
                        path.prependWayPoint(node);
                        node = node.getPreviousNode();
                }
                this.shortestPath = path;
                return path;
        }



}
