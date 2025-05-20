/* HexTile Class
 * Based off code from Naomi Gaylor & Ezzeldin Moussa, June 2022
 * Last Edit: 5/8/25
 * Updated to Java version
 */


import processing.core.PApplet;
import processing.core.PImage;
import java.awt.Polygon;
import java.awt.Point;


public class HexTile extends GridTile{
  
  private Polygon hexPoly;
  private float radius;
  private Point centerPixels;
  //private HexLocation hexLoc;


  //HexTile Constructor #1: X,Y coordinates
  public HexTile(PApplet p, int xCoord, int yCoord, float rad){
    this(p, new HexLocation(xCoord,yCoord), rad);
  }

  //HexTile Constructor #2: GridLocation
  public HexTile(PApplet p, HexLocation loc, float rad){
    super(p, loc);
    //this.hexLoc = loc;
    this.radius = rad;
    this.centerPixels = new Point(0,0);
    this.hexPoly = null;
  }

  //method to access the location of the GridTile
  // public HexLocation getLocation(){
  //     return location;
  // }

  //mutator method to define the center point of the Tile
  public void setHexCenterPixels(Point centerPixels){
    this.centerPixels = centerPixels;
  }
  //accessor method to the center point of the Tile
  public Point getCenterPixels(){
    return centerPixels;
  }

  //mutator method to change the Java Polygon object that defines the shape of the HexTile
  public void setHexPoly(Polygon hexPoly){
    this.hexPoly = hexPoly;
  }
  //accessor method to return the Java Polygon object
  public Polygon getPoly(){
    return hexPoly;
  }

  //accessor method to return the hexagon's radius
  public float getRadius(){
    return radius;
  }

    
}  
