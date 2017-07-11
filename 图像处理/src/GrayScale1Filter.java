/**
 * GrayScale1Filter implements
 * <p>
 * 对于彩色转灰度，有一个很著名的心理学公式：
 * Gray = R*0.299 + G*0.587 + B*0.114
 * 根据这个公式，把每个rgb像素点的灰度值算出来，并把rgb三个值都用这个灰度值填充
 * <p>
 * <p>
 * 之前用的公式是 Gray = R*0.3 + G*0.59 + B*0.11 我改成了标准值
 *
 * @author HANLING LEI
 * @version June 2017
 */
public class GrayScale1Filter implements Filter {

    @Override
    public void filter(PixelImage theImage) {
        Pixel[][] data = theImage.getData();

        for (int row = 0; row < theImage.getHeight(); row++) {
            for (int col = 0; col < theImage.getWidth(); col++) {
                int gray = (int) (data[row][col].rgb[Pixel.RED] * 0.299 + 0.587 * data[row][col].rgb[Pixel.GREEN] + 0.114 * data[row][col].rgb[Pixel.BLUE]);
                for (int i = 0; i < data[row][col].rgb.length; i++)
                    data[row][col].rgb[i] = gray;
            }
        }

        // update the image with the moved pixels
        theImage.setData(data);
    }

}
