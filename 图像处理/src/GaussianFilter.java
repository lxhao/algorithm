/**
 * GaussianFilter implements
 * <p>
 * 算法实现的基本原理以理解成每一个像素都取周边像素的平均值,从而达到模糊的效果
 * 取每个像素点周围8个点加上本身共9个点，对9个点的每个RGB值分配不同的权重后相加，最后取均值,
 * 这个均值就是该点的值
 *
 * @author HANLING LEI
 * @version June 2017
 */
public class GaussianFilter implements Filter {

    @Override
    public void filter(PixelImage theImage) {
        // TODO Auto-generated method stub
        Pixel[][] data = theImage.getData();
        int sum = 1 + 2 + 1 + 2 + 4 + 2 + 1 + 2 + 1;
        //9个点对应的权重分配
        int[][] arr = {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };

        for (int row = 2; row < theImage.getHeight() - 2; row++) {
            for (int col = 2; col < theImage.getWidth() - 2; col++) {
                int temp = 0;
                temp = temp + arr[0][0] * data[row - 1][col - 1].rgb[Pixel.RED];
                temp = temp + arr[0][1] * data[row - 1][col].rgb[Pixel.RED];
                temp = temp + arr[0][2] * data[row - 1][col + 1].rgb[Pixel.RED];
                temp = temp + arr[1][0] * data[row][col - 1].rgb[Pixel.RED];
                temp = temp + arr[1][1] * data[row][col].rgb[Pixel.RED];
                temp = temp + arr[1][2] * data[row][col + 1].rgb[Pixel.RED];
                temp = temp + arr[2][0] * data[row + 1][col - 1].rgb[Pixel.RED];
                temp = temp + arr[2][1] * data[row + 1][col].rgb[Pixel.RED];
                temp = temp + arr[2][2] * data[row + 1][col + 1].rgb[Pixel.RED];
                temp /= sum;
                data[row][col].rgb[Pixel.RED] = temp;

                temp = 0;
                temp = temp + arr[0][0] * data[row - 1][col - 1].rgb[Pixel.GREEN];
                temp = temp + arr[0][1] * data[row - 1][col].rgb[Pixel.GREEN];
                temp = temp + arr[0][2] * data[row - 1][col + 1].rgb[Pixel.GREEN];
                temp = temp + arr[1][0] * data[row][col - 1].rgb[Pixel.GREEN];
                temp = temp + arr[1][1] * data[row][col].rgb[Pixel.GREEN];
                temp = temp + arr[1][2] * data[row][col + 1].rgb[Pixel.GREEN];
                temp = temp + arr[2][0] * data[row + 1][col - 1].rgb[Pixel.GREEN];
                temp = temp + arr[2][1] * data[row + 1][col].rgb[Pixel.GREEN];
                temp = temp + arr[2][2] * data[row + 1][col + 1].rgb[Pixel.GREEN];
                temp /= sum;
                data[row][col].rgb[Pixel.GREEN] = temp;

                temp = 0;
                temp = temp + arr[0][0] * data[row - 1][col - 1].rgb[Pixel.BLUE];
                temp = temp + arr[0][1] * data[row - 1][col].rgb[Pixel.BLUE];
                temp = temp + arr[0][2] * data[row - 1][col + 1].rgb[Pixel.BLUE];
                temp = temp + arr[1][0] * data[row][col - 1].rgb[Pixel.BLUE];
                temp = temp + arr[1][1] * data[row][col].rgb[Pixel.BLUE];
                temp = temp + arr[1][2] * data[row][col + 1].rgb[Pixel.BLUE];
                temp = temp + arr[2][0] * data[row + 1][col - 1].rgb[Pixel.BLUE];
                temp = temp + arr[2][1] * data[row + 1][col].rgb[Pixel.BLUE];
                temp = temp + arr[2][2] * data[row + 1][col + 1].rgb[Pixel.BLUE];
                temp /= sum;
                data[row][col].rgb[Pixel.BLUE] = temp;
            }
        }

        // update the image with the moved pixels
        theImage.setData(data);
    }

}
