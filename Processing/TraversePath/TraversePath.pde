import cc.arduino.*;

import processing.serial.*;
import edu.mjc.lunabot.*;
import cc.arduino.*;
Navigation n;

Arduino arduino;
int led = 13;
int left = 10;
int right = 9;
//int left = 10;
//int right = 9;
// speed=50 =.615m/s
// 180deg rotation requires 2375mS(rather than the expected 2222mS)
//float timePerCm = 16;// mS required to travel 1cm at .615m/s
//float timePerDeg = 14;//mS to rotate 1 degree with motors counter rotating at .615m/s
float timePerCm = 52;
//float timePerCm = 30;//shortened for small space
float timePerDeg = 40;
int rampSteps = 50;
int leftSpeed = 0;
int rightSpeed = 0;
int maxSpeed = 30;// .615m/s
int neutral = 90;

void setup() {
  Robot bot = new Robot(new Orientation(194,75,90));
  n = new Navigation(bot);
  String fileName = dataPath("out.log");
  n.setIo(new FileIO(fileName, n));
  n.updatePathProjected();
  n.setPathTraversed(new ArrayList<Location>(n.getPathProjected().size()));
  arduino = new Arduino(this, Arduino.list()[0], 57600);
  arduino.pinMode(left, Arduino.SERVO);
  arduino.pinMode(left, Arduino.SERVO);
  arduino.pinMode(led, Arduino.OUTPUT);
  arduino.digitalWrite(led, Arduino.HIGH);
}

void draw(){
  //delay(1000);
  Location next = null;
  next = n.nextPointOnPath();
  if(next == null){
     //turn(new Angle(90, Angle.DEG));
     System.out.println("Path Completed!");
     while(true){
       arduino.digitalWrite(led, Arduino.HIGH);
       delay(1000) ;
     }
  }else{
    arduino.digitalWrite(led, Arduino.LOW);
    runTo(next);
  }
}

void runTo(Location l){
  LVector v = n.bot.getOrientation().vectorTo(l);
  Orientation o;
  if(v.getMag() == 0){
    arduino.analogWrite(left, neutral);
    arduino.analogWrite(right, neutral);
    System.out.println("Robot is already at location!");
  }else{
    System.out.println("Robot is moving to :" + l);
    Angle a = new Angle(v.getAngle(), Angle.RAD);
    turn(a);
    forward((int)v.getMag());
    o = new Orientation(l, a);
    n.bot.setOrientation(o);
    n.updateTraversal(l);
  }
  arduino.analogWrite(left, neutral);
  arduino.analogWrite(right, neutral);
  
}

void forward(int distance){
  int time = (int)(timePerCm * distance);
  arduino.analogWrite(left, neutral + maxSpeed);
  arduino.analogWrite(right, neutral + maxSpeed);
  delay(time);
}

void turn(Angle a){
  float dTheta = (float)(n.bot.getOrientation().getAngle().getDeg() - a.getDeg());
  System.out.println("Robot is turning " + dTheta + " degrees to " + a.getDeg());
  int time;
  if(dTheta > 0){
    time = (int)(timePerDeg * dTheta);
    arduino.analogWrite(left, neutral - maxSpeed);
    arduino.analogWrite(right, neutral + maxSpeed);
    delay(time);
    arduino.analogWrite(left, neutral);
    arduino.analogWrite(right, neutral);
    //delay(500);
  }else if(dTheta < 0){
    time = (int)(timePerDeg * abs(dTheta));
    arduino.analogWrite(left, neutral + maxSpeed);
    arduino.analogWrite(right, neutral - maxSpeed);
    delay(time);
    arduino.analogWrite(left, neutral);
    arduino.analogWrite(right, neutral);
    //delay(500);
  }else{
    arduino.analogWrite(left, neutral);
    arduino.analogWrite(right, neutral);
  }

}
