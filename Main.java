import java.awt.Color;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.io.IOException;
import java.io.*;
import java.util.StringJoiner;

public class Main{
    public static void main(String[] args) {
        Color[] constColorArray = new Color[] {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.WHITE, Color.BLACK, Color.PINK,Color.ORANGE};
        //Color[] constColorArray = new Color[] {Color.RED, Color.BLACK, Color.yellow};
        try{
            //Define Stuff
            PrintStream rawArray = new PrintStream(new File("raw_Array.txt"));
            PrintStream colorCorrectedArray = new PrintStream(new File("colorCorrectedArray.txt"));
            PrintStream testing = new PrintStream(new File("testing.txt"));
            int w = 5000;
            int h = 5000;
            String fileName = "testImage";
            String iIP = "C:/Users/Beta/Desktop/Code/images/" + fileName + ".png";
            String oIP = "C:/Users/Beta/Desktop/Code/images/" + fileName + "_o.png" ;
            AsciiArt.resize(iIP, oIP, w, h); //resize image to make sure it is square so we can convert pixels to an array.
            //
            File file_iIP = new File(oIP);
            /*System.setOut(rawArray);
            System.out.println(Arrays.deepToString(AsciiArt.loadPixelsFromImage(file_iIP)));
            System.setOut(colorCorrectedArray);
            System.out.println(Arrays.deepToString(AsciiArt.convertColorArray(AsciiArt.loadPixelsFromImage(file_iIP), constColorArray)));
            */
            //
            System.setOut(testing);
            int ScaleFactor = 50; //how much to reduce the character count. Character count ---> width/scaleFactor
            Color[][] matrix = AsciiArt.SectorArray(AsciiArt.convertColorArray(AsciiArt.loadPixelsFromImage(file_iIP), constColorArray),ScaleFactor);
            //System.out.println(Arrays.deepToString(matrix));
            //Color[][] matrix = AsciiArt.loadPixelsFromImage(file_iIP);
            AsciiArt.arrayToASCII(matrix, ScaleFactor);
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.out.println(matrix.length);
          {  
          }
        } catch (IOException ex){
            System.out.println("Error");
            System.out.println(ex.toString());
        } 
    }    
}

