package edu.mjc.lunabot.unfinished;

import edu.mjc.lunabot.util.Location;

public class VisionOpenCV {

	/**
	 * @param args
	 */
	
	private Camera camera;
	private Location[] rocks = new Location[3];
	private Location[] craters = new Location[3];
	private Location[] corners = new Location[4];
	private Location hopper;
	//private cvlImage[] videoFeed;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Location[] getRocks() {
		return rocks;
	}

	public void setRocks(Location[] rocks) {
		this.rocks = rocks;
	}

	public Location[] getCraters() {
		return craters;
	}

	public void setCraters(Location[] craters) {
		this.craters = craters;
	}

	public Location[] getCorners() {
		return corners;
	}

	public void setCorners(Location[] corners) {
		this.corners = corners;
	}

	public Location getHopper() {
		return hopper;
	}

	public void setHopper(Location hopper) {
		this.hopper = hopper;
	}
/*
	public cvlImage[] getVideoFeed() {
		return videoFeed;
	}

	public void setVideoFeed(cvlImage[] videoFeed) {
		this.videoFeed = videoFeed;
	}
*/
	public void initiate()
	{
		
	}
}
