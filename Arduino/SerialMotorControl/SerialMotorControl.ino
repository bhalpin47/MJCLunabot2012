#include <Servo.h>
/* 
 *	Control loop for Arduino mega listening for custom serial 
 * 	packets and sending commands to 6 ESC's for motor control:
 *  	4 drive motors (skid steer)
 * 		1 lift motor 
 *		1 excavation motor. 
 *	ESC's are non-reversible, so direction pins are outputs
 *	to relays that switch motor polarity.
 *	
 *	Packet structure allows for 8 speeds in either direction.
 *  
 *	See pin definitions below for wiring. 
 *
 * Data packets are sent in single bytes 1 byte/ms
 * controller reads in serial data and stores a 10 byte history in buff[]
 * Bit layout for serial packets from least to most significant bits:
 * 0-2: motor speed, Range:(0,8)
 * 3: forward/reverse signal (1=forward, 0=reverse)
 * 4-6: motor addressing bits
 * 7: error checking bit 
*/
long curTime = 0;
long prevTime = 0;

byte buff[] = {B0, B0, B0, B0, B0, 
			   B0, B0, B0, B0, B0}; //Buffer for serial history
//for some reason the first servo variable refuses to work!
//declare a dummy variable prior to any real ones.
Servo dummy, leftFront, leftRear, rightFront, rightRear, lift, dig;
int lrPin = 23; //pin for left-rear motor controller
int lfPin = 22; //pin for left-front motor controller
int rfPin = 24; //pin for right-frint motor controller
int rrPin = 25; //pin for right-rear motor controller
int liftPin = 26; //pin for lift motor
int digPin = 27; //pin for dig motor
int leftDirPin = 40; //pin for forward reverse signal of left motors 
int rightDirPin = 41; //pin for forward reverse signal of right motors 
int liftDirPin = 42; //pin for forward reverse signal of lift motor 
int digDirPin = 43; //pin for forward reverse signal of dig motor 
byte mask = B01110000; // mask for motor addressing
const int leftAddress = 0x00;
const int rightAddress = 0x10;
const int liftAddress = 0x20;
const int digAddress = 0x30;
const int reset = 0xFF; //byte used to signal start/end of data stream

void setup() {
  Serial.begin(9600);
  // initialize all pins and pin modes
  pinMode(leftDirPin, OUTPUT);
  pinMode(rightDirPin, OUTPUT);
  pinMode(liftDirPin, OUTPUT);
  pinMode(digDirPin, OUTPUT);
  
  leftRear.attach(lrPin);
  leftFront.attach(lfPin);
  rightFront.attach(rfPin);
  rightRear.attach(rrPin);
  lift.attach(liftPin);
  dig.attach(digPin);
  
  //run ESC calibration cycle
  leftFront.write(0);
  leftRear.write(0);
  rightFront.write(0);
  rightRear.write(0);
  lift.write(0);
  dig.write(0);
  
  delay(1000);
  
  leftFront.write(180);
  leftRear.write(180);
  rightFront.write(180);
  rightRear.write(180);
  lift.write(180);
  dig.write(180);
  
  delay(1000);
  
  leftFront.write(0);
  leftRear.write(0);
  rightFront.write(0);
  rightRear.write(0);
  lift.write(0);
  dig.write(0);
  // Sanity check pin modes
  pinMode(leftDirPin, OUTPUT);
  pinMode(rightDirPin, OUTPUT);
  pinMode(liftDirPin, OUTPUT);
  pinMode(digDirPin, OUTPUT);
  
  //initialize clock for command fequencies
  curTime = millis();
  prevTime = curTime;
}

/*Main control loop:
* This program is to be uploaded to an Arduino:
* Constantly blinks a status led at 2Hz to indicate the program is running
* Each run also polls the serial port waiting for a "reset" signal to start reading input
* Once Reset is detected a single byte is read and motor commands 
* are interpreted and sent to motor controller
*
*/

void loop() {

  curTime = millis(); //log time for periodic led blink (2Hz)
  
  // check if .5 sec has elapsed
  if(curTime - prevTime > 500){
    digitalWrite(13, HIGH); // led on
    
    //send halt signal to all motors
    leftFront.write(0);
    leftRear.write(0);
    rightFront.write(0);
    rightRear.write(0);
    lift.write(0);
    dig.write(0);
    
	/* leave led on until a "reset" byte is received
	 * this prevents the control loop from continuing
	 * if junk data is being sent.
	 */
    while(true){
      if(Serial.available() > 0){
	//move previous data down array
        for (int i=0; i<10; i++) {
          buff[i]=buff[i+1];
        }
        buff[10]=Serial.read(); // read new byte into end of array
        if(buff[10] == reset){
            break;	//data stream detected
        }
      }
    }  
  }else{
     digitalWrite(13, LOW); //led off
  } 
  
  //main control loop
  while (Serial.available()>0) {
    prevTime = curTime; //log time
	//move old data
    for (int i=0; i<10; i++) {
      buff[i]=buff[i+1];
    }
    buff[10]=Serial.read(); //read next byte (control byte)
    
    byte b = mask & buff[10]; //apply mask to determine which ESC is being addressed
	
	//check forward/reverse bit
    boolean forward;
    if(bitRead(buff[10], 3) == 1){
      forward = true;
    }
    else{
      forward = false;
    }

	//switch based on motor address
    switch(b){
    case 0:
      //left motors
      //Serial.print("0: ");
      //Serial.println(buff[10], BIN);
      if(forward){
        digitalWrite(leftDirPin, HIGH);
      }
      else{
        digitalWrite(leftDirPin, LOW);
      }
	  runMotor(leftFront, buff[10]);
      runMotor(leftRear, buff[10]);
      break;
    case 16:
      //right motors
      //Serial.print("1: ");
      //Serial.println(buff[10], BIN);
      if(forward){
        digitalWrite(rightDirPin, HIGH);
      }
      else{
        digitalWrite(rightDirPin, LOW);
      }
	  runMotor(rightFront, buff[10]);
      runMotor(rightRear, buff[10]);
      break;
    case 32:
      //lift
      //Serial.print("2: ");
      //Serial.println(buff[10], BIN);
      if(forward){
        digitalWrite(liftDirPin, HIGH);  
      }
      else{
        digitalWrite(liftDirPin, LOW);
      }
      runMotor(lift, buff[10]);
      break;
    case 48:
      //dig
      //Serial.print("3: ");
      //Serial.println(buff[10], BIN);
      if(forward){
        digitalWrite(digDirPin, HIGH);  
      }
      else{
        digitalWrite(digDirPin, LOW);
      }
      runMotor(dig, buff[10]);
      break;
    default:
      break;  
    }
  }
  delay(10);
}

/* function runMotor maps the speed value from the data byte (0-8) 
 * to a servo value between (30-180) max range (0-255)
 */
void runMotor(Servo motor, byte control){
  int c = control & B00000111; //mask for speed
  int mSpeed = map(c, 0, 8, 30, 180);
  motor.write(mSpeed);  
}

