# ![alt text](http://www.nasa.gov/sites/default/themes/NASAPortal/images/nasa-logo.gif "Nasa Logo") MJC Moon Pirates Lunabot2012 
Codebase for Modesto junior College's entry in the 2012 NASA Lunabotics mining competition.

For more information about the competition check out [NASA's Robotic Mining Competition Homepage](https://www.nasa.gov/offices/education/centers/kennedy/technology/nasarmc.html).

## Project Overview
The project was initially developed with the goal of creating a robot capable of fully autonomous operation. Due to time constraints and our limited budget, we were unable to get all of the sensors to function in time for the competition. As the project exists now it is capable of remote manual operation using an XBox controller. The core pathfinding algorithm (based on the A* algorithm) is complete, but the software to support the sensors and provide real world input to the path finding algorithm is unfinished.

### Architecture
The code is designed so that it can be used with or without the command station by just connecting the controller directly to the robot's on-board machine. The typical set-up used during the competition is as follows: 
* The command station (off-board) is has an XBox controller remotely mounted to the on-board machine using [USB/IP](http://usbip.sourceforge.net/) 
* The robot's on board machine is configured to run `LunaBrain\src\edu\mjc\processing\ManualControl.java` on boot. It also has an Arduino Mega connected via USB.
* The Arduino is running `Arduino/SerialMotorControl.ino` which listens for serial input and maps it to each of the 6 motor controllers (4 Wheels, Lift Motor, and Excavation Motor).
