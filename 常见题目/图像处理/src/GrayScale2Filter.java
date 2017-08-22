/**
 * GrayScale2Filter implements
 * <p>
 * Gray = (11 * RED + 16 * GREEN + 5 * BLUE) / 32
 * 根据这个公式，把每个rgb像素点的灰度值算出来，并把rgb三个值都用这个灰度值填充
 * <p>
 * <p>
 * 这个灰度公式我在网上没有搜索到，是你们老师给的计算公式吗？
 *
 * @author HANLING LEI
 * @version June 2017
 */
public class GrayScale2Filter implements Filter {

    @Override
    public void filter(PixelImage theImage) {
        Pixel[][] data = theImage.getData();

        for (int row = 0; row < theImage.getHeight(); row++) {
            for (int col = 0; col < theImage.getWidth(); col++) {
                int gray = (11 * data[row][col].rgb[Pixel.RED] +
                        16 * data[row][col].rgb[Pixel.GREEN] +
                        5 * data[row][col].rgb[Pixel.BLUE]) / 32;
                for (int i = 0; i < data[row][col].rgb.length; i++)
                    data[row][col].rgb[i] = gray;
            }
        }

        // update the image with the moved pixels
        theImage.setData(data);
    }

}
