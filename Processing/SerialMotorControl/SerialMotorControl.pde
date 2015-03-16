import processing.serial.*;


Serial port;
Joystick j1;
slider dig;
slider lift;
int maxSpeed = 0;

JInput controller;
float[] analogVals;
boolean[] digitalVals;

byte addressLeft = 0x00;
byte addressRight = 0x10;
byte addressLift = 0x20;
byte addressDig = 0x30;
byte speedMask = 0x07;
byte directionMask = 0x08;

int leftSpeed;
int rightSpeed;
int x;
int y;
byte rightSpeed_B;
byte leftSpeed_B;


/**** SETIUP ******/
void setup() {
  size(200, 200);
  smooth();
  controller = new JInput();
   
  analogVals = new float[controller.getAnalogSize()];
  digitalVals = new boolean[controller.getDigitalSize()];
  println("Available serial ports:");
  println(Serial.list());
  port = new Serial(this, Serial.list()[0], 9600);

  j1 = new Joystick(60, 120, 45, color(255,0,0));
  dig=new slider(140,20,90, color(0,0,255));
  lift=new slider(170,20,90, color(255,255,255));
}
/***** END SETUP *****/

/**** DRAW ******/
void draw() {
  background(0);
  controller.poll();
  analogVals = controller.getAnalogVals();
  digitalVals = controller.getDigitalVals();
  
  j1.yP = int(map(analogVals[JInput.Y_AXIS], -1.0, 1.0, float(-j1.theSize), float(j1.theSize)));
  j1.xP = int(map(analogVals[JInput.X_AXIS], -1.0, 1.0, float(-j1.theSize), float(j1.theSize)));
  x = j1.mapX();
  y = j1.mapY();
  j1.render();
  dig.render();
  lift.render();
  text(dig.p, 130, 15);
  text(lift.p, 160, 15);
  int leftSpeed = 0;
  int rightSpeed = 0;
  setDriveMotorSpeed();
  
  
  byte output = byte(addressLeft + leftSpeed_B);
  port.write(output);
  output = byte(addressRight + rightSpeed_B);
  port.write(output);
  output = byte(addressLift + dig.map());
  port.write(output);
  output = byte(addressDig + lift.map());
  port.write(output);
}
/****** END DRAW ******/

void setDigPosition(){
  
}

void evacuate(){
  
}

/* sets leftSpeed_B and rightSpeed_B based on the global x and y variables */
void setDriveMotorSpeed(){
  //change coordinate system for easier calculation
  //values are now from [-7, 7]
  if(x >= directionMask){
    x -= directionMask;  
  }else{
    x = -x;
  }
  if(y >= directionMask){
    y -= directionMask;
  }else{
    y = -y;  
  }
  
  //assign values for speed;
  leftSpeed = x + y;
  rightSpeed = -x + y;
  
  //ensure range is [-7,7]
  if(leftSpeed > 7){
    leftSpeed = 7;  
  }else if(leftSpeed < -7){
    leftSpeed = -7;  
  }
  if(rightSpeed > 7){
    rightSpeed = 7;  
  }else if(rightSpeed < -7){
    rightSpeed = -7;  
  }
  //display speeds
  text(leftSpeed, 20, 190);
  text(rightSpeed, 40, 190);
  
  if(leftSpeed < 0){
    leftSpeed_B = byte(abs(leftSpeed));
  }else if(leftSpeed > 0){
    leftSpeed_B = byte(directionMask + (leftSpeed));  
  }else{
    leftSpeed_B = 0;  
  }
  
  if(rightSpeed < 0){
    rightSpeed_B = byte(abs(rightSpeed));
  }else if(rightSpeed > 0){
    rightSpeed_B = byte(directionMask + (rightSpeed));  
  }else{
    rightSpeed_B = 0;  
  }  
}

/***** SLIDER class declaration *********/
class slider {
  int xpos, ypos, thesize, p;
  boolean slide;
  color c, cb;
  slider (int x, int y, int s, color col) {
    xpos=x;
    ypos=y;
    thesize=s;
    p=0;
    slide=true;
    c=col;
    cb=color(red(c),green(c),blue(c),150);
  }
  
  byte map(){
    byte out = 0x08;
    int step = thesize / 7;
    int option = (abs(p)/step) % 8;
    switch(option){
      case 0:
        //System.out.println("0");
        out += 0x00;
        break;
      case 1:
        //System.out.println("1");
        out += 0x01;
        break;
      case 2:
        //System.out.println("2");
        out += 0x02;
        break;
      case 3:
        //System.out.println("3");
        out += 0x03;
        break;
      case 4:
        //System.out.println("4");
        out += 0x04;
        break;
      case 5:
        //System.out.println("5");
        out += 0x05;
        break;
      case 6:
        //System.out.println("6");
        out += 0x06;
        break;
      case 7:
        //System.out.println("7");
        out += 0x07;
        break;
    }
    return out;    
  }

  void render() {
    stroke(40);
    strokeWeight(10);
    noFill();
    line(xpos,ypos,xpos,ypos+thesize);

    stroke(80);
    strokeWeight(2);
    noFill();
    line(xpos,ypos,xpos,ypos+thesize);

    noStroke();
    fill(cb);
    ellipse(xpos, thesize-p+ypos, 17, 17);
    fill(c);
    ellipse(xpos, thesize-p+ypos, 13, 13);

    //text(thesize-dialy,xpos+10,dialy+ypos+5);

    // replace the +'s with double ampersands (web display issues)
    if (slide=true && mousePressed==true && mouseX<xpos+15 && mouseX>xpos-15){
      if ((mouseY<=ypos+thesize+10) && (mouseY>=ypos-10)) {
        p=(3*p+(thesize-(mouseY-ypos)))/4;
        if (p<0) {
          p=0;
        } else if (p>thesize) {
          p=thesize;
        }
      }
    }
  }
}
/***** JOYSTICK class declaration *********/
class Joystick{
  int xPos, yPos, theSize, xP, yP;
  boolean slide;
  color c, cb;
  Joystick(int x, int y, int s, color col){
    xPos = x;
    yPos = y;
    theSize = s;
    xP = 0;
    yP = 0;
    c = col;
    cb = color(red(c),green(c),blue(c), 150);
  }
  
  byte mapX(){
    byte out = 0;
    if(xP > 0){
      out = 0x08;
    }else if(xP < 0){
      out = 0x00; 
    }
    int step = theSize / 7;
    int option = (abs(xP)/step) % 8;
    switch(option){
      case 0:
        //System.out.println("0");
        out += 0x00;
        break;
      case 1:
        //System.out.println("1");
        out += 0x01;
        break;
      case 2:
        //System.out.println("2");
        out += 0x02;
        break;
      case 3:
        //System.out.println("3");
        out += 0x03;
        break;
      case 4:
        //System.out.println("4");
        out += 0x04;
        break;
      case 5:
        //System.out.println("5");
        out += 0x05;
        break;
      case 6:
        //System.out.println("6");
        out += 0x06;
        break;
      case 7:
        //System.out.println("7");
        out += 0x07;
        break;
    }
    return out;
  }
  
  byte mapY(){
    byte out = 0;
    if(yP < 0){
      out = 0x08;
    }else if(yP > 0){
      out = 0x00; 
    }
    int step = theSize / 7;
    int option = (abs(yP)/step) % 8;
    switch(option){
      case 0:
        //System.out.println("0");
        out += 0x00;
        break;
      case 1:
        //System.out.println("1");
        out += 0x01;
        break;
      case 2:
        //System.out.println("2");
        out += 0x02;
        break;
      case 3:
        //System.out.println("3");
        out += 0x03;
        break;
      case 4:
        //System.out.println("4");
        out += 0x04;
        break;
      case 5:
        //System.out.println("5");
        out += 0x05;
        break;
      case 6:
        //System.out.println("6");
        out += 0x06;
        break;
      case 7:
        //System.out.println("7");
        out += 0x07;
        break;
    }
    return out;
  }
  
  void render(){
    stroke(40);
    strokeWeight(10);
    noFill();
    ellipse(xPos, yPos, theSize*2, theSize*2);
    
    stroke(80);
    strokeWeight(2);
    noFill();
    ellipse(xPos, yPos, theSize*2, theSize*2);
    
    noStroke();
    fill(cb);
    ellipse(xP + xPos, yP + yPos, 17, 17);
    fill(c);
    ellipse(xP + xPos, yP + yPos, 13, 13);
    
    
    if (slide=true && mousePressed==true && mouseX<xPos+theSize+30 && mouseX>xPos-theSize-30
        && mouseY<yPos+theSize+30 && mouseY>yPos-theSize-30){
        if (mouseY<yPos-theSize) {
          yP= -theSize;
        } else if (mouseY > yPos + theSize) {
          yP= theSize;
        }else{
          yP = mouseY - yPos;
        }
        
        if (mouseX<xPos-theSize) {
          xP= -theSize;
        } else if (mouseX>xPos+theSize) {
          xP= theSize;
        }
        else{
          xP = mouseX - xPos; 
        }
      }else{
         xP = 0;
         yP = 0; 
      }
  }
}
