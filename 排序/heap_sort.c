
#include <stdio.h>

#define LEN 12
//打印数组
void print_array(int* array, int length)
{
    int index = 0;
    printf("array:\n");
    for (; index < length; index++) {
        printf(" %d,", *(array + index));
    }
    printf("\n\n");
}
//堆化函数
void _heapSort(int* array, int i, int length)
{
    int child, tmp;
    //这个是改变了哪个节点，就从该节点开始对以该节点为根节点的子树进行排序
    for (; 2 * i + 1 < length; i = child) { //依次到它的子树的子树。。。。
        child = 2 * i + 1;
        if ((child + 1 < length) && (array[child + 1] > array[child]))
            child++;                   //选个最大的孩子节点
        if (array[i] < array[child]) { //最大子节点和父节点进行交互
            tmp = array[i];
            array[i] = array[child];
            array[child] = tmp;
        } else
            break;
    }
}

void heapSort(int* array, int length)
{
    int i, tmp;

    if (length <= 1)
        return; //如果元素小于1，则退出
    //这一步是先把元素都堆化好，后面的话 哪个节点修改过，就从哪个节点开始对以它为根节点的子树进行堆化
    for (i = length / 2 - 1; i >= 0; i--) {
        _heapSort(array, i, length); //从第一个非叶子节点开始排序，一直到根节点
        print_array(array, length);
    }

    // 先抽取到根节点，然后再对元素进行堆化，然后又抽取根节点，再对元素进行堆化。。。。依次循环
    for (i = 0; i < length; i++) {
        tmp = array[0];
        array[0] = array[length - i - 1];
        array[length - i - 1] = tmp;
        _heapSort(array, 0, length - 1 - i); //堆化子树
    }
}

int main(void)
{
    int array[LEN] = { 2, 1, 4, 0};
    print_array(array, LEN);
    heapSort(array, LEN);
    print_array(array, LEN);
    return 0;
}
