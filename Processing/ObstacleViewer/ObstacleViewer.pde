float y1= 150, y2 = 444;
Navigation n = new Navigation();
Location[] obstacles;
Location current;
ArrayList<Location> pathProjected;
ArrayList<Obstacle> obs;
int steps = 0;
int r = 15;
int total = 0;
int success = 0;
int testNum = 0;


void setup() {
  size(388, 738);
  background(255, 255, 255);
  String fileName = dataPath("out.log");
  n.setIo(new FileIO(fileName, n));
  n.updateFromLine(testNum);
  //n.traversePath();
  obs = n.getObstacles();
  obstacles = new Location[obs.size()];
  for(int i = 0; i < obs.size(); i++){
     obstacles[i] = obs.get(i).getLocation();
  }

}

void draw() {
    drawArena();
      drawObstacles();

}

void drawArena(){
    color c = #00FFFF;
    stroke(c);
    line(0, y1, width, y1 );
    line(0, y2, width, y2 );
    c = #A9A9A9;
    stroke(c);
    for(int i = 1; i < 5; i++){   
      line(i*100, 0, i*100, height); 
    }
    for(int j = 1; j < 9; j++){
       line(0,j*100, 400, j*100);       
    } 
} 

void drawBot(Location l){
  fill(#0FA279);
  rectMode(CENTER);
  rect((float)l.getX(), (float)l.getY(), 80, 60);
}

void drawTarget(Location l){
  int x = (int)l.getX();
  int y = (int)l.getY();
  fill(#800080);
  triangle(x, y+15, x+13, y-8, x-13, y-8);
}


void drawObstacles(){
  for(int i = 0; i < obstacles.length; i++){
     if(i%2 == 0){
        fill(#00FFFF);
     }else{
       fill(#000000); 
     } 
     ellipse((float)obstacles[i].getX(), (float)obstacles[i].getY(), 2*r, 2*r); 
  }
}
