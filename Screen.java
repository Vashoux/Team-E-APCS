/* Screen class - a high level class that handles background screens & millisecond timing
 * Has a World Subclass
 * Author: Joel Bianchi & Carey Jiang
 * Last Edit: 5/8/25
 * Updated to Java version
 */


import processing.core.PApplet;
import processing.core.PImage;

public class Screen{

    PApplet p;

    //------------------ SCREEN FIELDS --------------------//
    private String screenName;
    private PImage bg;
    private boolean isMoveable;
    private Sprite mbg;
    
    private long startTime;
    private long lastTime = 0;

    //------------------ SCREEN CONSTRUCTORS --------------------//

    //Screen Constructor #1: Stationary background image
    public Screen(PApplet p, String screenName, PImage bg) {
        this.p = p;
        this.isMoveable = false;
        this.setName(screenName);
        if(bg != null) {
            this.setBg(bg);
        }
        System.out.println("bg of " + screenName + " Screen: " + Util.toStringPImage(bg));
        startTime = getTotalTime(); //?
    }

    //Screen Constructor #2: For background images that move - Takes String (Coded as a Sprite, not a Processing background PImage)
    public Screen(PApplet p, String screenName, String movingBgFile, float scale, float x, float y) {
        this.p = p;
        this.isMoveable = true;
        this.setName(screenName);
        mbg = new Sprite(p, movingBgFile, scale, x, y);
        startTime = getTotalTime();
    }

    //Screen Constructor #3: For background images that move - Takes PImage (Coded as a Sprite, not a Processing background PImage)
    public Screen(PApplet p, String screenName, PImage movingBg, float scale, float x, float y) {
        this.p = p;
        this.isMoveable = true;
        this.setName(screenName);
        mbg = new Sprite(p, movingBg, scale, x, y);
        startTime = getTotalTime();
    }


    //------------------ ACCESSORS & MUTATORS --------------------//
    public void setName(String screenName){
        this.screenName = screenName;
    }
    public String getName(){
        return screenName;
    }

    public void setBg(PImage bg){
        if(!isMoveable){
            this.bg = bg;
            //p.background(bg);
        }
    }
    public PImage getBgImage(){
        
        if(isMoveable){
            return mbg.getImage();
        }
        return bg;
    }

    public boolean getIsMoveable(){
        return isMoveable;
    }


    //------------------ SCREEN MOVING METHODS --------------------//

    //move the background image in the X & Y directions
    public void moveBgXY(float speedX, float speedY){
        if(isMoveable){
            mbg.move(speedX, speedY);
        } else {
            System.out.println("Can't move this background");
        }
    }

    public void setLeftX(float leftX) {
        mbg.setLeft(leftX);
    }
    public float getLeftX() {
        return mbg.getLeft();
    }

    public void setTopY(float topY) {
        mbg.setTop(topY);
    }
    public float getTopY() {
        return mbg.getTop();
    }

    //updates any movement of the background
    public void show(){
        if(isMoveable){
            mbg.show();
        }
    }



    //------------------ SCREEN TIME METHODS --------------------//

    public long getTotalTime(){
        return p.millis();  //milliseconds world
    }
    public long getScreenTime(){
        return p.millis() - startTime;  //milliseconds world
    }
    public long getTimeSince(long lastCheck){
        return getScreenTime() - lastCheck;
    }
    public float getScreenTimeSeconds(){
        return getScreenTime() / 1000.0f;
    }
    
    //pauses ALL screen methods!
    public void pause(final int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    //resets the timer for the screen
    public void resetTime(){
        startTime = getTotalTime();
    }


    public String toString(){
        return "Screen: " + screenName + " at LeftX:" + getLeftX() + " ,TopY:" + getTopY() ;
    }


}
