/* Animated Sprite class - useful to have Sprites move around
 * Designed to be used with Spritesheets & JSON Array files from TexturePacker software: 
 * https://free-tex-packer.com/app/
 * Inspired by Daniel Shiffman's p5js Animated Sprite tutorial: https://youtu.be/3noMeuufLZY
 * Authors: Joel Bianchi, Aiden Sing, Tahlei Richardson
 * Last Edit: 5/8/25
 * Updated to Java version
 */


import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.ArrayList;

 
public class AnimatedSprite extends Sprite{

  private String pngFile;
  private String jsonFile;
  private ArrayList<PImage> animation;
  private int len;
  float iBucket;
  float aSpeed; //variable to track how quickly the animation images cycle

  JSONObject spriteData;
  PImage spriteSheet;

  // Constructor #1 for AnimatedSprite with Spritesheet (Must use the TexturePacker to make the JSON)
  public AnimatedSprite(PApplet p, String png, String json, float x, float y, float aSpeed) {
    super(p, png, x, y, 1.0f, true);
    
    this.jsonFile = json;
    this.pngFile = png;
    this.animation = convertPngToList(png);
    super.setW(this.animation.get(0).width);
    super.setH(this.animation.get(0).height);
    super.setLeft(x);
    super.setTop(y);
    this.aSpeed = aSpeed;
    //System.out.println("AS w: " + super.getW() + ",h: " + super.getH());

  }

  //Constructor #2: animations + starting coordinates
  public AnimatedSprite(PApplet p, String png, String json, float x, float y ) {
    this(p, png, json, x, y, 1.0f);
  }

  // Constructor #3 taking in images and json only
  public AnimatedSprite(PApplet p, String png, String json) {
    this(p, png, json, 0.0f, 0.0f, 1.0f);
  }


  //Overriden method: Displays the correct frame of the Sprite image on the screen
  public void show() {
    int index = (int) Math.floor(Math.abs(this.iBucket)) % this.len;
    p.image(animation.get(index), super.getLeft(), super.getTop());
    //System.out.println("aSpeed: "+ aSpeed+"\tib: "+iBucket+"\t ind: "+ index);
    //System.out.println("Pos: "+ super.getX() +"," + super.getY());
  } 


  //Method to set the speed of how fast the frames cycle
  public void setSpeed(float animationSpeed){
    this.aSpeed = animationSpeed;
  }

  //Method to cycle through the images of the animated sprite & reset a new animation speed
  public void animate(float animationSpeed){
    setSpeed(animationSpeed);
    animate();
  }

  //Method to cycle through the images of the animated sprite
  public void animate(){
    iBucket += aSpeed/this.len;
    show();
  }

  //Method that makes animated sprite move in any straight line + sets animation speed
  public void animateMove(float hSpeed, float vSpeed, float animationSpeed, boolean wraparound){
    this.aSpeed = animationSpeed;
    animateMove(hSpeed, vSpeed, wraparound);
  }
  
  //Method that makes animated sprite move in any straight line
  public void animateMove(float hSpeed, float vSpeed, boolean wraparound){
    
    //adjust speed & frames
    animate();
    super.move( (int) (hSpeed * 10), (int) (vSpeed * 10) );
  
    //wraparound sprite if goes off the right or left
    if(wraparound){
      wraparoundHorizontal();
      wraparoundVertical();
    }
  }

  //animated method that makes the Sprite move to the right-left
  public void animateHorizontal(float horizontalSpeed, float animationSpeed, boolean wraparound) {
    animateMove(horizontalSpeed, 0, animationSpeed, wraparound);
  }

  //animated method that makes the Sprite move down-up
  public void animateVertical(float verticalSpeed, float animationSpeed, boolean wraparound) {
    animateMove(0, verticalSpeed, animationSpeed, wraparound);
  }

  //- Niko Baletin + Jaiden Kelly, 2023
  public void animateToPlayer(AnimatedSprite player, float animationSpeed, boolean wraparound) {
    float xDifference = player.getCenterX() - this.getCenterX();
    float yDifference = player.getCenterY() - this.getCenterY();
    if ((xDifference < 100 && xDifference > -100) && (yDifference < 150 && yDifference > -150)) {
      animateMove(xDifference/300.0f, yDifference/300.0f, animationSpeed, wraparound);
    }
    animateMove(xDifference/1000.0f, yDifference/1000.0f, animationSpeed, wraparound);
  }

  //Accessor method for the JSON path
  public String getJsonFile(){
    return this.jsonFile;
  }
  
  //Mutator method for the speed of the animation -Aiden Sing & Tahlei Richardson, 2023
  public void setAnimationSpeed(float aSpeed) {
    this.aSpeed = aSpeed;
  }

  //Method to resize the animated sprite images to different dimensions
  public void resize(int x, int y){
    for(int i=0; i<animation.size(); i++){
      PImage pi = animation.get(i);
      pi.resize(x,y);
    }
  }

  //Method to copy an AnimatedSprite
  public AnimatedSprite copySprite(){
    //super.copy();
    return new AnimatedSprite(p, this.pngFile, this.jsonFile, super.getLeft(), super.getTop(), this.aSpeed);
  }
  
  //Method to copy an AnimatedSprite to a specific location
  public AnimatedSprite copyTo(float x, float y){
    //super.copy();
    return new AnimatedSprite(p, this.pngFile, this.jsonFile, x, y, this.aSpeed);
  }

  //Method to copy an AnimatedSprite to a specific location with a new speed
  public AnimatedSprite copyTo(float x, float y, float aSpeed){
    //super.copy();
    return new AnimatedSprite(p, this.pngFile, this.jsonFile, x, y, aSpeed);
  }
  

  //---------------------PRIVATE HELPER METHODS--------------------------//

  //wraparound sprite if goes off the right-left
  private void wraparoundHorizontal(){
    if ( super.getLeft() > p.width ) {
      super.setLeft( -super.getW() );
    } else if ( super.getRight() < -p.width ){
      super.setRight( p.width );
    }
  }

  //wraparound sprite if goes off the top-bottom
  private void wraparoundVertical(){
    if ( super.getTop() > p.height ) {
      super.setTop( -super.getH() );
    } else if ( super.getBottom() < -p.height ){
      super.setBottom( p.height );
    }
  }

  private ArrayList<PImage> convertPngToList(String png){

      ArrayList<PImage> ani = new ArrayList<PImage>();
      spriteData = p.loadJSONObject(jsonFile);
      spriteSheet = p.loadImage(png);
      JSONArray frames = spriteData.getJSONArray("frames");
      
      // System.out.println("Loading Animated Sprite... " + pngFile);
      for(int i=0; i<frames.size(); i++){

        JSONObject frame = frames.getJSONObject(i);
        //System.out.println(i + ": " + frame + "\n");
        JSONObject fr = frame.getJSONObject("frame");
        //System.out.println("ss: " + fr + "\n");

        int sX = fr.getInt("x");
        int sY = fr.getInt("y");
        int sW = fr.getInt("w");
        int sH = fr.getInt("h");
        //System.out.println(i + ":\t sX:" + sX + ":\t sY:" + sY + ":\t sW:" + sW + ":\t sH:" + sH);
        PImage img = spriteSheet.get(sX, sY, sW, sH);
        ani.add(img);

        this.len = ani.size();
        this.iBucket = 0.0f;
        this.aSpeed = aSpeed;
      }

      return ani;

    }


}
