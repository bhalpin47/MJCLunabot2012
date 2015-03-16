
package edu.mjc.jinput;
import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


/**
 *
 * @author Brandon
 */
public class JInput {
    //indices for analog controller inputs
    public static int Y_AXIS = 0;
    public static int X_AXIS = 1;
    public static int Y_ROT = 2;
    public static int X_ROT = 3;
    public static int Z_AXIS = 4;
    
    //indices for digital controller inputs
    public static int A_BUTTON = 0;
    public static int B_BUTTON = 1;
    public static int X_BUTTON = 2;
    public static int Y_BUTTON = 3;
    public static int L_BUMPER = 4;
    public static int R_BUMPER = 5;
    public static int BACK_BUTTON = 6;
    public static int START_BUTTON = 7;
    public static int L_CLICK = 8;
    public static int R_CLICK = 9;
    
    //reference to xbox controller
    private Controller xBox = null;
    
    // containers for analog and digital components 
    private ArrayList<Component> analog;
    private ArrayList<Component> digital;
    
    // arrays storing last values read from components
    private float[] analogVals;
    private boolean[] digitalVals;
    
    /**
     * Contructor for JInput:
     * Selects the first xbox controller to be connected(or any controller of type GAMEPAD)
     * and identifies all of its components.
     * 
     * Controller will be assigned to xBox variable
     * Components will be stored in analog and digital ArrayLists
     * 
     * If no controller is found, devices will be updated every 2s 
     * until a controller is identified
     *
     */
    public JInput(){    	
        Controller[] controllers; 
        while(xBox == null){		// loop until controller is found
        	controllers = ControllerEnvironment.
        			getDefaultEnvironment().getControllers(); //initial device check
        	
        	for (int i = 0; i < controllers.length; i++) { //loop over all devices
                System.out.println("Device " + i + ":" + 
                		controllers[i].getName() + " : " +
                		controllers[i].getType());	//print each device
                if (controllers[i].getType() == Controller.Type.GAMEPAD) {	//controller found
                    xBox = controllers[i];
                }
            }
            
            
            if(xBox == null){    //controller not found
                System.out.println("Xbox controller not found, please plug in a controller!\n");
            	try {
    				Thread.sleep(2000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            }
        }
        // identify all components
        Component[] components = null;
        components = xBox.getComponents();
        analog = new ArrayList<Component>();
        digital = new ArrayList<Component>();
        //separate components by digital and analog
        for (int j = 0; j < components.length; j++) {
            if (components[j].isAnalog()) {
                analog.add(components[j]);
            } else {
                digital.add(components[j]);
            }
        }
        // initialize arrays for storing component values
        analogVals = new float[analog.size()];
        digitalVals = new boolean[digital.size()];
        
    }
    
    /**
     * For testing
     * 
     */
    public static void main(String[] args){
        JInput j = new JInput();
        while(true){
            j.poll();
        }
    }
    
    /**
     * Polls the current values for each of the components and
     * stores them in the analogVals and digitalVals arrays
     */
    public void poll() {
    	if(xBox != null){
	        xBox.poll(); // updates values for all components
	        for (int i = 0; i < analog.size(); i++) {
	            analogVals[i] = analog.get(i).getPollData();
	        }
	        for (int i = 0; i < digital.size(); i++) {
	            if (digital.get(i).getPollData() == 1.0f) {
	                digitalVals[i] = true;
	            } else {
	                digitalVals[i] = false;
	            }
	        }
	
	        try {
	            Thread.sleep(20);
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
    	}else{
    		System.out.println("Unable to poll, no controller was detected.");
    		try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    }
    
    public float[] getAnalogVals(){
        return analogVals;
    }
    
    public boolean[] getDigitalVals(){
        return digitalVals;
    }
    
    public int getAnalogSize(){
        return analog.size();
    }
    
    public int getDigitalSize(){
        return digital.size();
    }

    
}
