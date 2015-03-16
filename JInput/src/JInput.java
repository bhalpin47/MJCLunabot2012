

import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


/**
 *
 * @author Brandon
 */
public class JInput {
    
    public static int Y_AXIS = 0;
    public static int X_AXIS = 1;
    public static int Y_ROT = 2;
    public static int X_ROT = 3;
    public static int Z_AXIS = 4;
    
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
    
    
    private Controller xBox = null;
    private ArrayList<Component> analog;
    private ArrayList<Component> digital;
    private float[] analogVals;
    private boolean[] digitalVals;
    
    /**
     * creates an instance of JInput which identifies an xbox controller
     * as well as all of its components separated by analog and digital.
     *
     */
    public JInput(){
    	//System.loadLibrary("jinput-dx8-64");
    	// run config -Djava.library.path="${workspace_loc:JInput}\lib;${env_var:PATH}"
    	//System.out.println(System.getProperty("java.library.path"));
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        while(controllers.length < 1 || xBox == null){
        	controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        	for (int i = 0; i < controllers.length; i++) {
                System.out.println("Device " + i + ":" +controllers[i].getName() + " : " +
                		controllers[i].getType());

                if (controllers[i].getType() == Controller.Type.GAMEPAD) {
                    xBox = controllers[i];
                }
            }
            
            
            if(xBox == null){    
                System.out.println("Xbox controller not found, please plug in a controller!\n");
         
            	try {
    				Thread.sleep(2000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            }
        }
        Component[] components = null;
        analog = new ArrayList<Component>();
        digital = new ArrayList<Component>();
        components = xBox.getComponents();
        for (int j = 0; j < components.length; j++) {
            if (components[j].isAnalog()) {
                analog.add(components[j]);
            } else {
                digital.add(components[j]);
            }
        }
        analogVals = new float[analog.size()];
        digitalVals = new boolean[digital.size()];
        
    }
    
    public static void main(String[] args){
        JInput j = new JInput();
        while(true){
            j.poll();
        }
    }
    
    public void poll() {
    	if(xBox != null){
	        xBox.poll();
	        StringBuffer buffer = new StringBuffer();
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
