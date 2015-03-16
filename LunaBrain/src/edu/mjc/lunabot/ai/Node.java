package edu.mjc.lunabot.ai;


import java.util.ArrayList;

import edu.mjc.lunabot.util.Location;


public class Node extends Location implements Comparable<Node> {
        /* Nodes that this is connected to */
        ArenaMap map;
        Node north;
        Node northEast;
        Node east;
        Node southEast;
        Node south;
        Node southWest;
        Node west;
        Node northWest;
        ArrayList<Node> neighborList;
        boolean visited;
        double g;
        double h;
        Node previousNode;
        boolean isObstacle;
        boolean isStart;
        boolean isTarget;
        
        Node(int x, int y) {
                neighborList = new ArrayList<Node>();
                this.x = x;
                this.y = y;
                this.visited = false;
                this.g = Integer.MAX_VALUE;
                this.isObstacle = false;
                this.isStart = false;
                this.isTarget = false;
        }
        
        Node (int x, int y, boolean visited, int g, boolean isObstacle, boolean isStart, boolean isTarget) {
                neighborList = new ArrayList<Node>();
                this.x = x;
                this.y = y;
                this.visited = visited;
                this.g = g;
                this.isObstacle = isObstacle;
                this.isStart = isStart;
                this.isTarget = isTarget;
        }
        
        public Node getNorth() {
                return north;
        }

        public void setNorth(Node north) {
                //replace the old Node with the new one in the neighborList
                if (neighborList.contains(this.north))
                        neighborList.remove(this.north);
                neighborList.add(north);
                
                //set the new Node
                this.north = north;
        }

        public Node getNorthEast() {
                return northEast;
        }

        public void setNorthEast(Node northEast) {
                //replace the old Node with the new one in the neighborList
                if (neighborList.contains(this.northEast))
                        neighborList.remove(this.northEast);
                neighborList.add(northEast);
                
                //set the new Node
                this.northEast = northEast;
        }

        public Node getEast() {
                return east;
        }

        public void setEast(Node east) {
                //replace the old Node with the new one in the neighborList
                if (neighborList.contains(this.east))
                        neighborList.remove(this.east);
                neighborList.add(east);
                
                //set the new Node
                this.east = east;
        }

        public Node getSouthEast() {
                return southEast;
        }

        public void setSouthEast(Node southEast) {
                //replace the old Node with the new one in the neighborList
                if (neighborList.contains(this.southEast))
                        neighborList.remove(this.southEast);
                neighborList.add(southEast);
                
                //set the new Node
                this.southEast = southEast;
        }

        public Node getSouth() {
                return south;
        }

        public void setSouth(Node south) {
                //replace the old Node with the new one in the neighborList
                if (neighborList.contains(this.south))
                        neighborList.remove(this.south);
                neighborList.add(south);
                
                //set the new Node
                this.south = south;
        }

        public Node getSouthWest() {
                return southWest;
        }

        public void setSouthWest(Node southWest) {
                //replace the old Node with the new one in the neighborList
                if (neighborList.contains(this.southWest))
                        neighborList.remove(this.southWest);
                neighborList.add(southWest);
                
                //set the new Node
                this.southWest = southWest;
        }

        public Node getWest() {
                return west;
        }

        public void setWest(Node west) {
                //replace the old Node with the new one in the neighborList
                if (neighborList.contains(this.west))
                        neighborList.remove(this.west);
                neighborList.add(west);
                
                //set the new Node
                this.west = west;
        }

        public Node getNorthWest() {
                return northWest;
        }

        public void setNorthWest(Node northWest) {
                //replace the old Node with the new one in the neighborList
                if (neighborList.contains(this.northWest))
                        neighborList.remove(this.northWest);
                neighborList.add(northWest);
                
                //set the new Node
                this.northWest = northWest;
        }
        
        public ArrayList<Node> getNeighborList() {
                return neighborList;
        }

        public boolean isVisited() {
                return visited;
        }

        public void setVisited(boolean visited) {
                this.visited = visited;
        }

        public double getG() {
                return g;
        }

        public void setG(double f) {
                this.g = f;
        }

        public Node getPreviousNode() {
                return previousNode;
        }

        public void setPreviousNode(Node previousNode) {
                this.previousNode = previousNode;
        }
        
        public double getH() {
                return h;
        }

        public void setH(double h) {
                this.h = h;
        }
   
        public boolean isObstacle() {
                return isObstacle;
        }

        public void setObstacle(boolean isObstacle) {
                this.isObstacle = isObstacle;
        }

        public boolean isStart() {
                return isStart;
        }

        public void setStart(boolean isStart) {
                this.isStart = isStart;
        }

        public boolean isTarget() {
                return isTarget;
        }

        public void setTarget(boolean isTarget) {
                this.isTarget = isTarget;
        }

        public boolean equals(Node node) {
                return (node.x == x) && (node.y == y);
        }
        
        public double getF(){
        	return g + h;
        }

        public int compareTo(Node otherNode) {
                double thisF = this.getF();
                double otherF = otherNode.getF();
                
                if (thisF < otherF) {
                        return -1;
                } else if (thisF > otherF) {
                        return 1;
                } else {
                        return 0;
                }
        }
}