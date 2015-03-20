import processing.serial.*;
//BE SURE YOU ARE USING FirmataServo
import cc.arduino.*;
Navigation n;

Arduino arduino;
//for testing pwm on LED

int motor1 = 10;
int motor2 = 9;
int push = 2;
int val = 0;
int prev = 0;
//int motor1 = 10;
//int motor2 = 9;
// speed=50 =.615m/s
// 180deg rotation requires 2375mS(rather than the expected 2222mS)
int rampSteps = 50;
int motor1Speed = 0;
int motor2Speed = 0;
//int maxSpeed = 90; //****Used for calibration*****
int maxSpeed = 89;// .615m/s
int neutral = 90;

void setup() {
  Robot bot = new Robot(new Orientation(0,0,90));
  Navigation n = new Navigation(bot);
  //size(512, 200);
  arduino = new Arduino(this, Arduino.list()[0], 57600);
  arduino.pinMode(motor1, Arduino.SERVO);
  arduino.digitalWrite(motor1, 180);
  arduino.pinMode(push, Arduino.INPUT);
  //arduino.pinMode(motor2, Arduino.OUTPUT);
  //arduino.setPwmFrequency(motor1, 1024);
 // arduino.setPwmFrequency(motor2, 1024);
  
}

void draw() {
  //Orientation previous = new Orientation(n.bot.getOrientation().getLocation(), n.bot.getOrientation().getDeg());
  
  if(keyPressed) {
    if (key == '1') {
      arduino.digitalWrite(motor1, arduino.LOW);
      motor1Speed = 1;
      arduino.analogWrite(motor1, motor1Speed);
    }else if (key == '2') {
      arduino.digitalWrite(motor1, arduino.LOW);
      motor1Speed = 20;
      arduino.analogWrite(motor1, motor1Speed);
    }else if (key == '3') {
      arduino.digitalWrite(motor1, arduino.LOW);
      motor1Speed = 40;
      arduino.analogWrite(motor1, motor1Speed);
    }else if (key == '4') {
      arduino.digitalWrite(motor1, arduino.LOW);
      motor1Speed = 60;
      arduino.analogWrite(motor1, motor1Speed);
    }else if (key == '5') {
      arduino.digitalWrite(motor1, arduino.LOW);
      motor1Speed = 90;
      arduino.analogWrite(motor1, motor1Speed);
    }else if (key == '6') {
      arduino.digitalWrite(motor1, arduino.LOW);
      motor1Speed = 110;
      arduino.analogWrite(motor1, motor1Speed);
    }else if (key == '7') {
      arduino.digitalWrite(motor1, arduino.LOW);
      motor1Speed = 130;
      arduino.analogWrite(motor1, motor1Speed);
    }else if (key == '8') {
      arduino.digitalWrite(motor1, arduino.LOW);
      motor1Speed = 150;
      arduino.analogWrite(motor1, motor1Speed);
    }else if (key == '9') {
      arduino.digitalWrite(motor1, arduino.LOW);
      motor1Speed = 169;
      arduino.analogWrite(motor1, motor1Speed);
    }else if(key == 'w'){
      if(motor1Speed < 169){
        motor1Speed += 1;
      }
      delay(100);
      arduino.analogWrite(motor1, motor1Speed);
    }else if(key == 's'){
      if(motor1Speed > 0){
        motor1Speed -= 1;
      }
      delay(100);
      arduino.analogWrite(motor1, motor1Speed);
    }
  } 
  System.out.println(motor1Speed);
}

void forward(){
    arduino.analogWrite(motor1, neutral + maxSpeed);
    arduino.analogWrite(motor2, neutral + maxSpeed);
}

void rev(){
    arduino.analogWrite(motor1, neutral - maxSpeed);
    arduino.analogWrite(motor2, neutral - maxSpeed);
}

void rotatemotor2(){
    arduino.analogWrite(motor1, neutral + maxSpeed);
    arduino.analogWrite(motor2, neutral - maxSpeed);
  
}
void rotatemotor1(){
    arduino.analogWrite(motor1, neutral - maxSpeed);
    arduino.analogWrite(motor2, neutral + maxSpeed);
}
void neutral(){
  arduino.analogWrite(motor1, neutral);
  arduino.analogWrite(motor2, neutral);
  
}

