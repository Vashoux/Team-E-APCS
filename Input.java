/* Input class - to have popup boxes for users to type data
 * Forked from North Toronto Collegiate Institute, Gerry Heffernan: https://ntci.on.ca/compsci/hef/ics3/ch1/1_4.html
 * Authors: Joel Bianchi
 * Last Edit: 5/8/25
 * Updated to Java version
 */


import javax.swing.*;


public class Input{

    public static String prompt(String s) {
        System.out.println(s);
        String entry = JOptionPane.showInputDialog(s);
        if (entry == null){
            return null;
        }
        System.out.println(entry);
        return entry;
    }

    public static String getString(String s){
        return prompt(s);
    }

    public static int getInt(String s){
        return Integer.parseInt(getString(s));
    }

    public static long getLong(String s){
        return Long.parseLong(getString(s));
    }

    public static float getFloat(String s){
        return Float.parseFloat(getString(s));
    }

    public static double getDouble(String s){
        return Double.parseDouble(getString(s));
    }

    public static char getChar(String s){
        String entry = prompt(s);
        if (entry.length() >= 1){
            return entry.charAt(0);
        } else {
            return '\n';
        }
    }

}
