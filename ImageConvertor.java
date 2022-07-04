import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ImageConvertor {

    static char[] asciiChars = {'@', '#', '$', '%', '?', '*', '+', ';', ':', ',', '.'};

    BufferedImage image;

    int width, height;

    char[][] asciiArt;

    String inputPath, outputPath;
    int widthLimit;

    public ImageConvertor(String inputPath, String outputPath, int widthLimit) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.widthLimit = widthLimit;

        try {
            this.image = ImageIO.read(new File(inputPath));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    BufferedImage resizeImage(BufferedImage originalImage) {
        //calculate the ratio of the new image to the old image and the height and width of the new image
        double ratio = (double) this.widthLimit / (double) originalImage.getWidth();
        int targetHeight = (int) (originalImage.getHeight() * ratio) / 2;

        BufferedImage resizedImage = new BufferedImage(this.widthLimit, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, this.widthLimit, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    void processImage(){
        //resize image
        this.image = resizeImage(this.image);
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.asciiArt = new char[width][height];
        //make the image greyscale
        this.image = makeGrey(this.image);
        //convert the image to ascii
        this.asciiArt = convertToAscii(this.image);
        //output the ascii art to a file
        outputAsciiArt(this.asciiArt);
    }

    //make the image greyscale
    BufferedImage makeGrey(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x, y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int grey = (red + green + blue) / 3;
                Color newColor = new Color(grey, grey, grey);
                newImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return newImage;
    }

    //convert the image to ascii using the asciiChars array
    char[][] convertToAscii(BufferedImage image) {
        char[][] asciiArt = new char[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x, y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int grey = (red + green + blue) / 3;
                asciiArt[x][y] = asciiChars[grey / (256 / asciiChars.length) % asciiChars.length];
            }
        }
        return asciiArt;
    }

    //output the ascii art to a file
    void outputAsciiArt(char[][] asciiArt) {
        try {
            assert asciiArt != null;
            File file = new File(outputPath);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    fw.write(asciiArt[x][y]);
                }
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

}
