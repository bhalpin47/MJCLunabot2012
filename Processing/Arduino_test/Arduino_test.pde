import processing.serial.*;

import cc.arduino.*;

Arduino arduino;

color off = color(4, 79, 111);
color on = color(84, 145, 158);

int[] values = { Arduino.LOW, Arduino.LOW, Arduino.LOW, Arduino.LOW,
 Arduino.LOW, Arduino.LOW, Arduino.LOW, Arduino.LOW, Arduino.LOW,
 Arduino.LOW, Arduino.LOW, Arduino.LOW, Arduino.LOW};
 int value = Arduino.LOW;

void setup() {
  size(470, 200);
  
  println(Arduino.list());
  arduino = new Arduino(this, Arduino.list()[0], 57600);
  
  
    arduino.pinMode(13, Arduino.OUTPUT);
}

void draw() {
  /*
  background(off);
  stroke(on);
  
  for (int i = 0; i < 2; i++) {
    if (value == Arduino.HIGH)
      fill(on);
    else
      fill(off);
      
    rect(420 - i * 30, 30, 20, 20);
  }
  */
}

void mousePressed()
{
  
  if (value == Arduino.LOW) {
    arduino.digitalWrite(13, Arduino.HIGH);
    value = Arduino.HIGH;
  } else {
    arduino.digitalWrite(13, Arduino.LOW);
    value = Arduino.LOW;
  }
}
