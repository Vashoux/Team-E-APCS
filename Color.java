
import processing.core.PApplet;

public class Color {


    //------------------ COLOR CONSTANTS--------------------//
    public static int BLACK = getColor(0,0,0); // #000000;
    public static int WHITE = getColor(255,255,255); // #FFFFFF;
    public static int GRAY = getColor(127,127,127); // #7F7F7F;
    public static int RED = getColor(255,0,0); // #FF0000;
    public static int GREEN = getColor(0,255,0); // #00FF00;
    public static int BLUE = getColor(0,0,255); // #0000FF;
    public static int CYAN = getColor(0,255,255); // #00FFFF;
    public static int MAGENTA = getColor(255,0,255); // #FF00FF;
    public static int YELLOW = getColor(255,255,0); // #FFFF00;


    public static int getColor(int red, int green, int blue){
        return (int)( -(long)Math.pow(16,6) + red*Math.pow(16,4) + green*Math.pow(16,2) + blue );
    }

    public static int getColor(String hexString){
        int hashIndex = hexString.indexOf("#");

        int red = Integer.parseInt(hexString.substring(hashIndex+1, hashIndex+3), 16);
        int green = Integer.parseInt(hexString.substring(hashIndex+3, hashIndex+5), 16);
        int blue = Integer.parseInt(hexString.substring(hashIndex+5, hashIndex+7), 16);
        return getColor(red, green, blue);        
    }

    public static void printColors(PApplet p, int color){
        System.out.println("Pixel color --> (R:"+(int)p.red(color)+",G:"+(int)p.green(color)+",B:"+(int)p.blue(color)+")");
    }


}
