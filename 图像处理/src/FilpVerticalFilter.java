/**
 * FilpVertical implements
 * <p>
 * 逆置行
 * 把第一行与最后一行交换，第二行与倒数第二行交换。。。
 * <p>
 * 我优化了实现，只用了一次层for循环
 *
 * @author HANLING LEI
 * @version June 2017
 */
public class FilpVerticalFilter implements Filter {

    @Override
    public void filter(PixelImage theImage) {
        // TODO Auto-generated method stub
        // get the data from the image
        Pixel[][] data = theImage.getData();

        // image height
        int height = theImage.getHeight();
        Pixel[] tmp;
        for (int rowIndex = 0; rowIndex < height / 2; rowIndex++) {
            tmp = data[rowIndex];
            data[rowIndex] = data[height - rowIndex - 1];
            data[height - rowIndex - 1] = tmp;
        }

        // update the image with the moved pixels
        theImage.setData(data);

    }

}
