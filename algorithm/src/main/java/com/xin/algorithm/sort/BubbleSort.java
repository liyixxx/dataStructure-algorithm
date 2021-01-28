package com.xin.algorithm.sort;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * @Auther: xin
 * @DATE: 2021/1/28 14:06
 *
 * 冒泡排序
 *
 * 1. 冒泡排序（Bubble Sorting）的基本思想是：
 *      通过对待排序序列从前向后（从下标较小的元素开始）,依次比较相邻元素的值，若发现逆序则交换，使值较大的元素逐渐从前移向后移，就象水底下的气泡一样逐渐向上冒。
 *
 * 2. 举例排序过程  array = [2,5,3,9,1]
 *    第一次排序：
 *       1. 指针A指向2 , 指针B指向5 , 两者发生比较, 2<5 , 不发生变化， AB指针后移
 *       2. 指针A指向5 , 指针B指向3 , 5>3 , 发生交换 , array = [2,3,5,9,1] , AB指针后移
 *       3. 指针A指向5 , 指针B指向9 , 5<9 , 不发生变化 , AB指针后移
 *       4. 指针A指向9 , 指针B指向1 , 9>1 , 交换 , array[2,3,5,1,9] ,B指针已经到达末尾，第一次排序结束。
 *       5. 第一次排序后，结果变为： array = [2,3,5,1,9]
 *    第二次排序：
 *       1. A->2 , B->3 , 2<3 , 不变 , AB指针后移
 *       2. A->3 , B->5 , 3<5 , 不变 , AB指针后移
 *       3. A->5 , B->1 , 5>1 , 交换 , array = [2,3,1,5,9] , AB指针后移
 *       4. 由于第一次比较已经选出了最大的值，所以第二次的比较过程比第一次少一次，因此不会在和 9 进行比较了。
 *       5. 第二次排序后，结果变为： array = [2,3,1,5,9]
 *   第三次排序
 *      1. A->2 , B->3 , 2<3 , 不变 , AB指针后移
 *      2. A->3 , B->1 , 3>1 , 交换 , array = [2,1,3,5,9] , AB指针后移
 *      3. 不在和 5 进行比较
 *      4. 第三次排序后，结果变为： array = [2,1,3,5,9]
 *   第四次排序
 *      1. A->2 , B->1 , 2>1 , 交换 , array = [1,2,3,5,9] , AB指针后移
 *      2. 不在和 3 进行比较
 *      3. 第三次排序后，最终结果变为： array = [1,2,3,5,9]
 *
 *
 * 3. 小结：
 *      每一轮比较时，会选出当前比较数据的最大值/最小值，然后下一轮在比较时，这个值不会参与比较了。
 *
 *
 *
 */
public class BubbleSort {

    public static void main(String[] args) {

        int [] array = {2,5,3,9,1} ;

        // 模拟冒泡排序的执行过程
        imitateBubble(array);

        // 冒泡排序
        bubbleSort(array);

        // 冒泡排序优化
        bubbleSortV2(array);

        // 冒泡排序耗时查看 , 时间复杂度 : O(n^2)
        int [] testArr = new int[100*1000];
        // 随机赋值
        for (int index = 0; index < testArr.length; index++) {
            testArr[index] = (int)(Math.random() * 200*1000);
        }

        Instant start = Instant.now();
        bubbleSortV2(testArr);
        Instant end = Instant.now();
        long used = ChronoUnit.SECONDS.between(start, end);

        // 10w  ---  15s
        System.out.println("冒泡排序耗时 : " + used + " 秒");

    }

    /**
     * 对冒泡排序简单优化
     *
     * 当排序数组为 aray = [1,2,3,4,5] 时，会发现数组已经是排好序的，所以不需要在进行排序。
     * 因此可以在排序时添加标识符，如果在某一轮依次交换都没有发生过，那么就表示当前数组已经排好序了，也就不需要再进行后续步骤了，直接跳出循环即可
     *
     * @param array
     */
    private static void bubbleSortV2(int[] array) {

        // 临时交换变量
        int temp = 0 ;

        // 标识符，表示在某一轮是否发生了交换
        boolean flag = false ;

        for (int i = 0 ; i < array.length -1 ; i ++){
            for (int j = 0 ; j < array.length -1 -i ; j++){
                if (array[j] > array[j+1]){
                    // 改变标识符，表明发生过交换了
                    flag = true ;
                    // 交换
                    temp = array[j] ;
                    array[j] = array[j+1];
                    array[j+1] = temp ;
                }
            }
            // 在一轮循环完后，判断标识符，如果是false表示没有发生过交换，表明数组已经是有序的了，有序也就不需要要在进行比较了
            if (!flag){
                break;
            } else {
                // 发生过交换，还需要把标识符还原，让后面继续判断
                // flag = !flag ;
                flag = false ;
            }
        }
    }

    /**
     * 冒泡排序实现
     *
     * 通过模拟可以看出，冒泡排序整体是由两个for循环来组成，外层控制比较的轮数，内层对每一轮要比较的数据进行比较和交换
     *
     * 对应的时间复杂度为 O(n^2)
     * @param array
     */
    private static void bubbleSort(int[] array) {

        int temp = 0 ;

        for (int i = 0 ; i < array.length -1 ; i ++){
            // 循环 length -1 轮 , 每一轮选出最大值/最小值
            for (int j = 0 ; j < array.length - 1 - i ; j ++){
                // 每次要判断的数据-1 (因为上一轮已经选出对应的最大值/最小值了，因此这一轮只需要在剩下的数据里面进行比较即可)
                if (array[j] > array[j+1]){
                    temp = array[j];
                    array[j] = array[j+1] ;
                    array[j+1] = temp ;
                }
            }

        }

    }

    /**
     * 模拟冒泡排序的执行过程
     * @param array
     */
    private static void imitateBubble(int[] array) {

        System.out.println("原始数组 : " + Arrays.toString(array));

        // 临时变量
        int temp = 0 ;

        // 首轮比较，选出最大值，放在数组末尾
        for (int i = 0; i < array.length -1; i++) {
            if (array[i] > array[i+1]){
                temp = array[i];
                array[i] = array[i+1] ;
                array[i+1] = temp ;
            }
        }
        System.out.println("首轮比较交换后的结果: " + Arrays.toString(array));

        // 第二轮比较，将倒数第二大的结果放在倒数第二个位置
        // 由于已经选出了最大值了，所以这次不需要和最大值比较，循环次数-1
        for (int i = 0; i < array.length -1 -1; i++) {
            if (array[i] > array[i+1]){
                temp = array[i];
                array[i] = array[i+1] ;
                array[i+1] = temp ;
            }
        }
        System.out.println("第二轮比较交换后的结果: " + Arrays.toString(array));

        // 依次类推，比较第三轮和第四轮
        for (int i = 0; i < array.length -1 -1 -1; i++) {
            if (array[i] > array[i+1]){
                temp = array[i];
                array[i] = array[i+1] ;
                array[i+1] = temp ;
            }
        }
        System.out.println("第三轮比较交换后的结果: " + Arrays.toString(array));

        for (int i = 0; i < array.length -1 -1 -1; i++) {
            if (array[i] > array[i+1]){
                temp = array[i];
                array[i] = array[i+1] ;
                array[i+1] = temp ;
            }
        }
        System.out.println("第四轮（最终）比较交换后的结果: " + Arrays.toString(array));

    }

}
