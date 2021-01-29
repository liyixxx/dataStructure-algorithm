package com.xin.algorithm.sort;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * @Auther: xin
 * @DATE: 2021/1/29 10:32
 *
 * 希尔排序
 *      希尔排序是希尔（Donald Shell）于 1959 年提出的一种排序算法。希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，也称为缩小增量排序。
 *
 * 思想：
 *      希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止
 *
 * 举例说明: array = {8,9,1,7,2,3,5,4,6,0}
 *    第一次分组
 *        1. 按照增量 incre = array.length / 2 = 5来进行分组，将数组分为五组
 *              g1 = {8,3} , g2 = {9,5} , g3 = {1,4} , g2 = {7,6} , g2 = {2,0}
 *        2. 分组后对每一组进行插入排序
 *              g1 = {3,8} , g2 = {5,9} , g3 = {1,4} , g2 = {6,7} , g2 = {0,2}
 *        3. 分组排序后的整个数组的结果如下： array = {3,5,1,6,0,8,9,4,7,2}
 *    第二次分组
 *        1. 每次分组时，增量减少，第二次分组时增量incre = incre /2 = 2 , 为第一次的一般，所以第二次分组时，数组分为了两组
 *              g1 = {3,1,0,9,7} , g2 = {5,6,8,4,2}
 *        2. 分组后对每一组进行插入排序
 *              g1 = {0,1,3,7,9} , g2 = {2,4,5,6,8}
 *        3. 分组排序后的整个数组的结果如下： array = {0,2,1,4,3,5,7,6,9,8}
 *    第三次分组
 *        1. 第三次增量 incre = incre / 2 = 1 , 此时增量为1，整个文件恰被分成一组，直接执行插入排序，排序后结果便是最终结果，算法便终止了
 *        2. 直接对整个数组进行排序，结果为 array = {0,1,2,3,4,5,6,7,8,9}
 *
 */
public class ShellSort {

    public static void main(String[] args) {

        int [] array = {8,9,1,7,2,3,5,4,6,0};
        imitateShellSortByChange(array);

        System.out.println("排序前 : " + Arrays.toString(array));
        shellSortByChacnge(array) ;
        System.out.println("排序后 : " + Arrays.toString(array));

        // shell排序耗时查看
        int [] testArr = new int[1500*1000];
        // 随机赋值
        for (int index = 0; index < testArr.length; index++) {
            testArr[index] = (int)(Math.random() * 20*1000*1000);
        }
        Instant start = Instant.now();
        // shellSortByChacnge(testArr);
        shellSortByShift(testArr);
        Instant end = Instant.now();
        long used = ChronoUnit.MILLIS.between(start, end);

        // 交换方式：15w  ----  23.7s
        // 以为方式：150w ----  297 ms
        System.out.println("shell排序耗时 : " + used + " 毫秒");

    }

    /**
     * shell排序，移位方式
     * @param array
     */
    private static void shellSortByShift(int[] array) {

        for (int gap = array.length / 2 ; gap > 0 ; gap /= 2){
            // 从分组的位置开始，向后遍历，遍历的每一个值都会和当前组的前面只进行比较，判断是否需要移位
            for (int i = gap ; i < array.length ; i++){
                /** 进行插入排序 */

                // 待插入位置
                int j = i ;
                // 待插入值
                int val = array[j] ;

                // j - gap > 0 （表明当前组还有需要比较的值）
                // val < arr[j-gap]  待插入的值比当前组的前一个值小，需要继续前移
                while (j - gap >= 0 && val < array[j-gap]){
                    // 进行移位
                    array[j] = array[j-gap] ;
                    // 向前继续寻找位置
                    j -= gap ;
                }
                // 找到插入位置
                array[j] = val ;
            }
        }

    }

    /**
     * shell 排序，交换法
     * @param array
     */
    private static void shellSortByChacnge(int[] array) {
        int temp = 0 ;
        for (int incre = array.length / 2 ; incre > 0 ; incre/=2 ){

            for (int i = incre ; i < array.length ; i++) {

                for (int j = i - incre ; j >= 0 ; j -= incre){
                    if (array[j] > array[j+incre]){
                        temp = array[j] ;
                        array[j] = array[j + incre] ;
                        array[j+incre] = temp ;
                    }
                }

            }

        }
    }

    /**
     * 模拟shell排序 (交换法) 过程
     * @param array
     */
    private static void imitateShellSortByChange(int[] array) {

        // 临时变量
        int temp = 0 ;

        /**
         * 双循环
         *   第一次将数组按照增量进行分组
         *   第二次获取分组中的元素
         */
        // 第一次分为5组 group = array.length/2 = 5
        for (int i = 5 ; i < array.length ; i++){
            // 获取每一组的元素
            for (int j = i-5 ; j>=0 ; j-=5){
                // 如果当前元素小于对应步长的后一个元素，那么就进行交换
                if (array[j] > array[j+5]){
                    temp = array[j];
                    array[j] = array[j+5] ;
                    array[j+5] = temp ;
                }
            }
        }

        System.out.println("第1次分组排序结果：" + Arrays.toString(array));

        // 第二次分为2组  group = group / 2 = 2
        for (int i = 2 ; i < array.length ; i++){
            // 获取每一组的元素
            for (int j = i-2 ; j>=0 ; j-=2){
                // 如果当前元素小于对应步长的后一个元素，那么就进行交换
                if (array[j] > array[j+2]){
                    temp = array[j];
                    array[j] = array[j+2] ;
                    array[j+2] = temp ;
                }
            }
        }
        System.out.println("第2次分组排序结果：" + Arrays.toString(array));

        // 第三次只剩一组了 group = group / 2 = 1
        for (int i = 1 ; i < array.length ; i++){
            // 获取每一组的元素
            for (int j = i-1 ; j>=0 ; j-=1){
                // 如果当前元素小于对应步长的后一个元素，那么就进行交换
                if (array[j] > array[j+1]){
                    temp = array[j];
                    array[j] = array[j+1] ;
                    array[j+1] = temp ;
                }
            }
        }
        System.out.println("第3次分组排序结果：" + Arrays.toString(array));
    }

}
