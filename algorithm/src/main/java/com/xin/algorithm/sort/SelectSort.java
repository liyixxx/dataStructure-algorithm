package com.xin.algorithm.sort;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * @Auther: xin
 * @DATE: 2021/1/28 15:29
 *
 * 选择排序
 *
 * 1. 选择排序的基本思想
 *      第一次从 arr[0]~arr[n-1]中选取最小值， 与 arr[0]交换，
 *      第二次从 arr[1]~arr[n-1]中选取最小值，与 arr[1]交换，
 *      第三次从 arr[2]~arr[n-1]中选取最小值，与 arr[2] 交换，…，
 *      第 i 次从 arr[i-1]~arr[n-1]中选取最小值，与 arr[i-1]交换，…,
 *      第 n-1 次从 arr[n-2]~arr[n-1]中选取最小值，与 arr[n-2]交换，
 *      总共通过 n-1 次，得到一个按排序码从小到大排列的有序序列。
 *
 * 2. 举例说明 array = [15,21,7,63,12]
 *     第一次排序：
 *          1. 从array[0] 到 array[4] 整个数组中，找取最小值 = arr[2] = 7 , 让最小值和array[0]交换
 *          2. 第一轮排序结果为 array = [7,21,15,63,12]
 *     第二次排序
 *          1. 从array[1] 到array[4] 中找到最小值为 arr[4] = 12 , 让它和array[1]交换
 *          2. 第二轮排序结果为 array = [7,12,15,63,21]
 *     第三次排序
 *          1. 从array[2] 到array[4] 中找到最小值为 arr[2] = 15 , 就是自己，不需要交换
 *          2. 第三轮排序结果为 array = [7,12,15,63,21]
 *     第四次排序
 *          1. 从array[3] 到array[4] 中找到最小值为 arr[4] = 21 , 和array[3]交换
 *          2. 第四轮排序结果为 array = [7,12,15,21,63]
 *
 * 至此排序结束，最终结果为 array = [7,12,15,21,63]
 *
 *
 *
 */
public class SelectSort {

    public static void main(String[] args) {

        int [] array = {15,21,7,63,12};
        selectSort(array);
        System.out.println(Arrays.toString(array));

        // 选择排序耗时查看 , 时间复杂度 : O(n^2)
        int [] testArr = new int[150*1000];
        // 随机赋值
        for (int index = 0; index < testArr.length; index++) {
            testArr[index] = (int)(Math.random() * 200*1000);
        }
        Instant start = Instant.now();
        selectSort(testArr);
        Instant end = Instant.now();
        long used = ChronoUnit.MILLIS.between(start, end);

        // 15w  ----  6.7s
        System.out.println("选择排序耗时 : " + used + " 毫秒");

    }

    /**
     * 选择排序  时间复杂度 = O(n^2)
     *
     * 循环 array.length -1 轮
     * 每一轮从 i+1(设定最小值的下一个) 比较到 array 的最后一个位置
     *
     * @param array
     */
    private static void selectSort(int[] array) {

        for (int i = 0; i < array.length - 1; i++) {
            // 每一轮排序时，先假定自己是最小值，如果在循环时发现有比自己更小的，那么就记录新的最小值和下标
            int minIndex = i ;
            // 最小值,可作为temp
            int min = array[i] ;

            for (int j = i+1 ; j < array.length; j++){
                // 每一轮比较都是从i的下一个元素开始进行比较
                if (array[j] < min){
                    // 如果发现比默认最小值更小的值,就对最小值和下标进行更新
                    minIndex = j ;
                    min = array[j];
                }
            }

            // 这样在一轮循环中就找到了最小的值,如果最小值不是自己，那么就进行交换
            if (i != minIndex){
                // 将当前值移到最小值处
                array[minIndex] = array[i] ;
                // 让当前下标的值变为最小值
                array[i] = min ;
            }

        }
        
    }

}
