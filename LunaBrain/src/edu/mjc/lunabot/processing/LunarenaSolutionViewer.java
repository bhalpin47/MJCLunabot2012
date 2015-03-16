package edu.mjc.lunabot.processing;


import java.util.ArrayList;

import cc.arduino.Arduino;
import processing.core.*;
import edu.mjc.lunabot.*;
import edu.mjc.lunabot.util.Location;
import edu.mjc.lunabot.util.Obstacle;

/*
 * LunarenaSolutionViewer takes files as input with lists of obstacle
 * layouts and path traversal commands generated by path simulator
 * File should reside in data directory with name "path.log"
 *
 */

public class LunarenaSolutionViewer extends PApplet{
	float y1= 150, y2 = 444;
	Navigation n = new Navigation("path.log");
	Location[] obstacles;
	Location current;
	ArrayList<Location> pathProjected;
	ArrayList<Obstacle> obs;
	int steps = 0;
	int r = 15;
	int total = 0;
	int success = 0;
	int testNum = 0;
	int botWidth;
	int botLength;

	    


	public void setup() {
		size(388, 738);
		  background(255, 255, 255);
		  botWidth = (int)n.getBot().getWidth();
		  botLength = (int)n.getBot().getLength();
		  n.updateFromLine(testNum);
		  pathProjected = n.getPathProjected();
		  //n.traversePath();
		  obs = n.getObstacles();
		  obstacles = new Location[obs.size()];
		  for(int i = 0; i < obs.size(); i++){
		     obstacles[i] = obs.get(i).getLocation();
		  }

	}

	public void draw() {
		if(n.io.readLine(testNum) != null){
		    
		    
		      if(steps >= pathProjected.size()-1){
		        System.out.println("Test: " + testNum);
		        steps = 0;
		        testNum++;
		        n.updateFromLine(testNum);
		        pathProjected = n.getPathProjected();
		        //n.traversePath();
		        obs = n.getObstacles();
		        obstacles = new Location[obs.size()];
		        for(int i = 0; i < obs.size(); i++){
		           obstacles[i] = obs.get(i).getLocation();
		        }
		        
		        delay(250);
		        background(255);
		      }
		      drawArena();
		      drawObstacles();
		      current = pathProjected.get(steps);
		      drawBot(current);
		      steps++;
		      
		    
		  }else{
		    System.out.println("Done");
		    noLoop();
		  }
	}

	void drawArena(){
	    int c = color(0,255,255); //turquoise
	    stroke(c);
	    line(0, y1, width, y1 );
	    line(0, y2, width, y2 );
	    c = color(169,169,169); //gray
	    stroke(c);
	    for(int i = 1; i < 5; i++){   
	      line(i*100, 0, i*100, height); 
	    }
	    for(int j = 1; j < 9; j++){
	       line(0,j*100, 400, j*100);       
	    } 
	} 

	void drawBot(Location l){
	  fill(15,162,121); //pale turquoise
	  rectMode(CENTER);
	  rect((float)l.getX(), (float)l.getY(), botWidth, botLength);
	}

	void drawTarget(Location l){
	  int x = (int)l.getX();
	  int y = (int)l.getY();
	  fill(128,0,128); //purple
	  triangle(x, y+15, x+13, y-8, x-13, y-8);
	}


	void drawObstacles(){
	  for(int i = 0; i < obstacles.length; i++){
	     if(i%2 == 0){
	        fill(0,255,255); //turquoise
	     }else{
	       fill(0,0,0); //black
	     } 
	     ellipse((float)obstacles[i].getX(), (float)obstacles[i].getY(), 2*r, 2*r); 
	  }
	}




}
