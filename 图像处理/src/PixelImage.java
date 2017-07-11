import java.awt.image.*;

/**
 * Provides access to an image as an array of Pixels
 *
 * @author Richard Dunn
 * @author Donald Chinn
 * @author Dan Jinguji
 * @version March 1, 2002
 */

public class PixelImage {
    // underlying image for this object
    private BufferedImage myImage;

    /**
     * Construct a PixelImage from a Java BufferedImage
     *
     * @param bi The image
     */
    public PixelImage(BufferedImage bi) {
        // initialise instance variables
        myImage = bi;
    }

    /**
     * Return the width of the image
     */
    public int getWidth() {
        return myImage.getWidth();
    }

    /**
     * Return the height of the image
     */
    public int getHeight() {
        return myImage.getHeight();
    }

    /**
     * Return the image's pixel data as an array of Pixels.
     *
     * @return The array of pixels
     */
    public Pixel[][] getData() {

    /* The Raster has origin 0,0 , so the size of the 
     * array is [width][height], where width and height are the 
     * dimensions of the Raster
     */
        Raster r = this.myImage.getRaster();
        Pixel[][] data = new Pixel[r.getHeight()][r.getWidth()];
        int[] samples = new int[3];

        // Translates from java image data to Pixel data
        for (int row = 0; row < r.getHeight(); row++) {
            for (int col = 0; col < r.getWidth(); col++) {
                r.getPixel(col, row, samples);
                Pixel ourPixel = new Pixel(samples[0], samples[1], samples[2]);
                data[row][col] = ourPixel;
            }
        }
        return data;
    }

    /**
     * Set the image's pixel data from an array.  This array must match
     * that returned by getData() in terms of dimensions.
     *
     * @param data The array to pull from
     * @throws IllegalStateException    if the passed in array does not match
     *                                  the dimensions of the PixelImage
     * @throws IllegalArgumentException if the passed in array has pixels
     *                                  with invalid values
     */
    public void setData(Pixel[][] data, boolean rotated) {
        //对于非正方形的图片，旋转后宽度和高度变了，需要重新new一个BufferedImage
        if (rotated) {
            this.myImage = new BufferedImage(getHeight(), getWidth(), BufferedImage.TYPE_INT_RGB);
        }
        WritableRaster wr = this.myImage.getRaster();

        if (data.length != wr.getHeight()) {
            throw new IllegalStateException("Array size does not match");
        } else if (data[0].length != wr.getWidth()) {
            throw new IllegalStateException("Array size does not match");
        }

        // Translates from an array of Pixel data to java image data
        for (int row = 0; row < wr.getHeight(); row++) {
            for (int col = 0; col < wr.getWidth(); col++) {
                int[] px = data[row][col].rgb;
                int red = px[Pixel.RED];
                int green = px[Pixel.GREEN];
                int blue = px[Pixel.BLUE];
                if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
                    System.out.printf("red:%d, green:%d, blue:%d\n", red, green, blue);
                    throw new IllegalArgumentException("Pixel value out of range");
                }
                wr.setPixel(col, row, px);
            }
        }
    }

    public void setData(Pixel[][] data) {
        setData(data, false);
    }

    /**
     * IGNORE THIS METHOD -- you won't need it
     */
    public BufferedImage getImage() {
        return this.myImage;
    }
}

