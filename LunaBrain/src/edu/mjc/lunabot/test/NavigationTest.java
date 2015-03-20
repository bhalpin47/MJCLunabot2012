package edu.mjc.lunabot.test;

import edu.mjc.lunabot.*;
import edu.mjc.lunabot.util.FileIO;

import static org.junit.Assert.*; 
import org.junit.Test;

public class NavigationTest{
	
	@Test
	public void testPathGeneration(){
		Navigation n = new Navigation();
		n.setIo(new FileIO("test.log", n));
        //n.runSimulation(100);
        n.generateObstacles();
        assertNotNull(n.getObstacles());
        n.generatePath();
        assertNotNull(n.getPathProjected());
        n.traversePath();
        assertNotNull(n.getPathProjected());
        assertEquals(n.getPathProjected().size(), n.getPathTraversed().size());
        n.io.write(n.toString());
        n.updatePathProjected();
        n.updatePathTraversed();
        n.updateObstacles();
	}
	
	@Test
	public void testRunSimulation(){
		//TODO
		assertTrue(true);
	}

}
