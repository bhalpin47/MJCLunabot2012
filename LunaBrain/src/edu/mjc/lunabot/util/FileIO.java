package edu.mjc.lunabot.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import edu.mjc.lunabot.Navigation;

public class FileIO{
    /* General utility class identifiers */
    public static final String ANGLE = " |Angle ";
    public static final String CENTROID = " |Centroid ";
    
    public static final String LOCATION = " |Location ";
    public static final String ORIENTATION = " |Orientation ";
    public static final String OBSTACLE = " |Obstacle ";
    public static final String ROBOT = " |Robot ";
    
    /* Robot Class */
    public static final String LENGTH = " |length ";// length, width, safety margin
    public static final String WIDTH = " |width ";
    public static final String SAFETY_MARGIN = " |safetyMargin ";
    public static final String VELOCITY = " |velocity ";
    
    /* LAOPS Class */
    public static final String LAOPS = " |LAOPS ";
    
    /* Navigation Class */
    public static final String NAVIGATION = " |Navigation ";
    public static final String ARENA_LENGTH = " |arenaLength "; // width, height, obstacleLowerBound, obstacleUpperBound, hopper location, arena corner locations
    public static final String ARENA_WIDTH = " |arenaWidth ";
    public static final String OBSTACLE_LB = " |obstacleLowerBound ";
    public static final String OBSTACLE_UB = " |obstacleUpperBound ";
    public static final String HOPPER = " |hopper ";
    public static final String ARENA_BOUNDS = " |arenaBoundaries ";
    //Robot
    public static final String AUTONOMOUS = " |autonomous ";
    public static final String OBSTACLE_LIST = " |obstacles "; // 6 obstacle locations
    public static final String PATH_PROJECTED = " |pathProjected ";// list of locations
    public static final String PATH_TRAVERSED = " |pathTraversed ";// list of locations
    public static final String PATH_DEVIATION = " |pathDeviation ";// list of distances
    
    /* PathFinder Class */
    public static final String PATH_FINDER = " |PathFinder ";
    
    public static final String ELECTRICAL = " |Electrical ";//pertinent electrical feedback
    
    public static final String EXCAVATION = " |Excavation ";//excavation parameters
    
    public static final String MECHANICAL = " |Mechanical ";//mechanical feedback
    
    public static final String SENSORS = " |Sensors ";//actual sensor values
    
    /* Obstacle Class attributes */
    public static final String RADIUS = " |radius ";
    public static final String CRATER = " |crater ";

    private String file = new Date() + "out.log";
    private FileWriter writer;
    private final String[] args = {ROBOT};
    public Navigation n;
    
    public FileIO(String file){
        this.file = file;
        this.n = null;
    }
    
    public FileIO(String file, Navigation n){
        this.file = file;
        this.n = n;
    }
    
    private void open(){
        try{
            writer = new FileWriter(file, true);
        } catch(IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void close(){
        try{
            writer.close();
        } catch(IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public String read(){
    	String previousLine = null;
    	 try{
    		  // Open the file that is the first 
    		  // command line parameter
    		  FileInputStream fstream = new FileInputStream(file);
    		  // Get the object of DataInputStream
    		  DataInputStream in = new DataInputStream(fstream);
    		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		  String currentLine = null;
    		  //Read File Line By Line
    		  while ((currentLine = br.readLine()) != null)   {
    			  previousLine = currentLine;
    		  }
    		  //Close the input stream
    		  in.close();
    		    }catch (Exception e){//Catch exception if any
    		  System.err.println("Error: " + e.getMessage());
    		  }
    	 return previousLine;
    }
    
    public String readLine(int line){
    	String previousLine = null;
    	try{
    		// Open the file that is the first 
    		// command line parameter
    		FileInputStream fstream = new FileInputStream(file);
    		// Get the object of DataInputStream
    		DataInputStream in = new DataInputStream(fstream);
    		BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		String currentLine = null;
    		int count = 0;
    		//Read File Line By Line
    		while ((currentLine = br.readLine()) != null){
    			if(count == line){
    				return currentLine;
    			}
    			previousLine = currentLine;
    			count++;
    		}
    		//Close the input stream
    		in.close();
    	}catch (Exception e){//Catch exception if any
    		System.err.println("Error: " + e.getMessage());
    	}
    	return null;
    }
    
    public void write(){
        String[] args = {ROBOT};
        String out = generateOutput(args);
        open();
        try{
            writer.write(out);
        } catch(IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        close();
    }
    
    public void write(String out){
        out += "\n";
        open();
        try{
            writer.write(out);
        } catch(IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        close();
    }
    
    private String generateOutput(String[] args){
        String out = "\n";
        for(int i = 0; i < args.length; i++){
            if(args[i] !=null){
                switch(args[i]){
                    case ARENA_BOUNDS: ;
                    break;
                    case OBSTACLE: ;
                    break;
                    case ROBOT: out = out + ROBOT + n.bot.getOrientation();
                    break;
                    case PATH_PROJECTED: ;
                    break;
                    case PATH_TRAVERSED: ;
                    break;
                    case PATH_DEVIATION: ;
                    break;
                    case ELECTRICAL: ;
                    break;
                    case EXCAVATION: ;
                    break;
                    case MECHANICAL: ;
                    break;
                    case SENSORS: ;
                    break;
                    default: //invalid identifier exception
                }
            }
        }
        
        return out;
    }
}
