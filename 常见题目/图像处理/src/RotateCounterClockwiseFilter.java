/**
 * RotateCounterClockwiseFilter implements
 * 逆时针90度旋转图片，
 * 原图片的宽度和高度与旋转后图片的宽度高度互换
 * 重新开辟一个Pixel数组，用来保存旋转后图片的像素点
 * 把原图片的最后一列到第一列换成旋转后图片的第一行到最后一行
 *
 * @author HANLING LEI
 * @version June 2017
 */
public class RotateCounterClockwiseFilter implements Filter {

    @Override
    public void filter(PixelImage theImage) {
        //图片原来的像素
        Pixel[][] origImgData = theImage.getData();
        // image height
        int height = theImage.getHeight();
        // image width
        int width = theImage.getWidth();
        //图片旋转后的像素
        Pixel[][] newImgData = new Pixel[width][height];

        for (int col = width - 1; col >= 0; col--) {
            for (int row = 0; row < height; row++) {
                newImgData[width - col - 1][row] = origImgData[row][col];
            }
        }
        theImage.setData(newImgData, true);

    }

}
