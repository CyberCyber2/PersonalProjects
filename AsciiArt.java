import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.stream.IntStream;
import java.util.Arrays;
import java.util.Random;
import java.awt.Font;
import java.awt.Graphics;

public class AsciiArt{
//Resize the image to given bounsd
    public static void resize (String inputImagePath, String outputImagePath, 
    int scaledWidth, int scaledHeight) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

//Given an image of size 500,500, convert the image to an 2D array of pixels 
    public static Color[][] loadPixelsFromImage(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        Color[][] colors = new Color[image.getWidth()][image.getHeight()];
        for (int rows = 0; rows < image.getWidth(); rows++) {
            for (int cols = 0; cols < image.getHeight(); cols++) {
                colors[rows][cols] = new Color(image.getRGB(rows, cols));
            }
        }
        return colors;
    }
//Find distance between two colors
    public static double getClosestColorOfPixel(Color imgColor, Color constColor){
        int red1 = imgColor.getRed();
        int red2 = constColor.getRed();
        int rmean = (red1 + red2) >> 1;
        int r = red1 - red2;
        int g = imgColor.getGreen() - constColor.getGreen();
        int b = imgColor.getBlue() - constColor.getBlue();
        return Math.sqrt((((512+rmean)*r*r)>>8) + 4*g*g + (((767-rmean)*b*b)>>8));
    }
//Convert raw color array to closest color array
    public static Color[][] convertColorArray(Color[][] oldArray, Color[] constColorArray){
        int width = oldArray.length;
        int height = oldArray.length;
        Color[][] newArray = new Color[width][height]; //same array as input, except all colors changed to their closest counterpart
        for (int rows = 0; rows < width; rows++) {
            for (int cols = 0; cols < height; cols++) {
                Color closestColor = null;
                double distance = Double.MAX_VALUE;
                for (int constColorID = 0; constColorID < constColorArray.length; constColorID++) { //compare current pixel against all the constant colors. 
                    double tempDistance = getClosestColorOfPixel(oldArray[rows][cols], constColorArray[constColorID]);
                    if (tempDistance < distance){
                        distance = tempDistance;
                        closestColor = constColorArray[constColorID];
                        newArray[rows][cols] = closestColor; //Change the corresponding pixel in the new array to the closest color out of all the constant colors. 
                    }
                }
            }            
        }
        System.out.println("Converted Array");
        System.out.println(Arrays.deepToString(newArray));
        return newArray;
    }
//2D array to image
    public static void ArraytoImage(Color[][] array){
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(array.length, array[0].length,
        BufferedImage.TYPE_INT_RGB);
        File outputfile = new File("C:/Users/Beta/Desktop/Code/colorCorrectedImage.png");
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < array.length; x++) {
        for (int y = 0; y < array[x].length; y++) {
        bufferedImage.setRGB(x, y, array[x][y].getRGB());
        }
        }     
        try {
            ImageIO.write(bufferedImage, "png", outputfile); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//Assign color of smaller array to character and put on new image.
    public static Color[][] SectorArray(Color[][] array, int sd){ //sd = scale down factor
        int height = array.length;
        int width = array[0].length;
        Color[][] colorLocations = new Color [height/sd][width/sd];
        for (int rows = 0,nr=0; rows < width; nr++, rows+=sd) {
            for (int cols = 0, nc = 0; cols < height; nc++, cols+=sd) {
                Color tValueA = array [rows][cols];
                Color tValueB = array [rows][cols + 1];
                Color tValueC = array [rows + 1][cols];
                Color tValueD = array [rows + 1][cols+1];
                int avgRed = (tValueA.getRed() + tValueB.getRed() + tValueC.getRed() + tValueD.getRed())/4;
                int avgGreen = (tValueA.getGreen() + tValueB.getGreen() + tValueC.getGreen() + tValueD.getGreen())/4;
                int avgBlue = (tValueA.getBlue() + tValueB.getBlue() + tValueC.getBlue() + tValueD.getBlue())/4;
                Color avgColor = new Color(avgRed,avgGreen,avgBlue);
                colorLocations[nr][nc] = avgColor; //Populate 2D colorLocations array with the average color from a "Sector" of the input array
            }
        } 
        //return charLocations;
        System.out.println("Length of Array " + colorLocations.length);
        return colorLocations;
    }
    public static void arrayToASCII(Color[][] array, int sd) throws IOException { 
        String s = new String(IntStream.rangeClosed(32, 126).toArray(), 0, 95); //Array of basic ASCII characters
        char[] ascii = s.toCharArray();
        int height = array.length;
        int width = array[0].length;
        int oh = sd*height;
        int ow = sd*width;
        int ScalingFactor = ow/width;
        BufferedImage bufferedImage = new BufferedImage(oh, ow, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(new Color(10,10,10));
        graphics.fillRect(0, 0, oh, ow); //background color
        File outputfile = new File("C:/Users/Beta/Desktop/Code/asciiArt.png");
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) { //for every color in the array 
                Random random = new Random();
                int rand_index = random.nextInt(ascii.length);
                //
                String key = Character.toString(ascii[rand_index]); //write random character to screen
                graphics.setColor(array[r][c]); //text color
                graphics.setFont(new Font("TimesRoman", Font.PLAIN, sd)); 
                graphics.drawString(key, r*ScalingFactor, c*ScalingFactor); 
            }
        }
        try {
            ImageIO.write(bufferedImage, "png", outputfile); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


