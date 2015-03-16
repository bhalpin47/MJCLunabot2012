package edu.mjc.lunabot.processing;

import java.util.ArrayList;

import cc.arduino.Arduino;
import processing.core.*;
import edu.mjc.lunabot.*;
import edu.mjc.lunabot.util.Location;
import edu.mjc.lunabot.util.Obstacle;


public class Processing extends PApplet{
	float y1= 150, y2 = 444;
	Navigation n = new Navigation();
	Location[] obstacles;
	Location current;
	ArrayList<Location> pathProjected;
	int steps = 0;
	int r;
	int total = 0;
	int success = 0;

	    


	public void setup() {
	  size(388, 738);
	  background(255, 255, 255);
	  n.updateObstacles();
	  n.setStepSize(5);
	  n.updatePathProjected();
	  pathProjected = n.getPathProjected();
	  //n.traversePath();
	  ArrayList<Obstacle> obs = n.getObstacles();
	  r = obs.get(0).getRadius();
	  obstacles = new Location[obs.size()];
	  for(int i = 0; i < obs.size(); i++){
	     obstacles[i] = obs.get(i).getLocation();
	  }

	}

	public void draw() {
	    if(steps >= pathProjected.size() - 1){
	      noLoop();
	    }
	    drawArena();
	    drawObstacles();
	    current = pathProjected.get(steps);
	    drawBot(current);
	      drawTarget(n.getTarget());
	  drawBot(n.bot.getOrientation().getLocation());
	    steps++;
	}

	void drawArena(){
	    int c = 0x00FFFF;
	    stroke(c);
	    line(0, y1, width, y1 );
	    line(0, y2, width, y2 );
	    c = 0xA9A9A9;
	    stroke(c);
	    for(int i = 1; i < 5; i++){   
	      line(i*100, 0, i*100, height); 
	    }
	    for(int j = 1; j < 9; j++){
	       line(0,j*100, 400, j*100);       
	    } 
	} 

	void drawBot(Location l){
	  fill(0x0FA279);
	  rectMode(CENTER);
	  rect((float)l.getX(), (float)l.getY(), 80, 60);
	}

	void drawTarget(Location l){
	  int x = (int)l.getX();
	  int y = (int)l.getY();
	  fill(0x800080);
	  triangle(x, y+15, x+13, y-8, x-13, y-8);
	}


	void drawObstacles(){
	  for(int i = 0; i < obstacles.length; i++){
	     if(i%2 == 0){
	        fill(0x00FFFF);
	     }else{
	       fill(0x000000); 
	     } 
	     ellipse((float)obstacles[i].getX(), (float)obstacles[i].getY(), 2*r, 2*r); 
	  }
	}





}
