package edu.mjc.lunabot.ai;

import edu.mjc.lunabot.util.Location;

/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile.
 */
public class Heuristic {

        public double getHeuristic(Location start, Location target) {         
                
                
                //double result = (double) (Math.sqrt((dx*dx)+(dy*dy)));
                //Optimization! Changed to Manhattan distance:
                double result = (Math.abs(start.getX() - target.getX())) + Math.abs((start.getY() - target.getY()));
                
                
                return result;
        }

}
