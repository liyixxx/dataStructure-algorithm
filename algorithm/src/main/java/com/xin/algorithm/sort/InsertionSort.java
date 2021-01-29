package com.xin.algorithm.sort;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * @Auther: xin
 * @DATE: 2021/1/28 17:10
 *
 * 插入排序
 *
 * 1. 插入排序的基本思想
 *      把 n 个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，无序表中包含有 n-1 个元素，
 *      排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，将它插入到有序表中的适当位置，使之成为新的有序表
 *
 * 2.举例说明 array = [9,5,2,11,3]
 *     初始状态，将元素[9]看作是有序表的元素，[5,2,11,3]是无序表的元素
 *     第一次插入
 *        1. 取出无序表的第一个元素[5],让其和有序表的元素[9]进行比较,发现比9小，把5放在9的前面，完成插入
 *        2. 插入后的有序表为 [5,9] , 无序表为 [2,11,3]
 *     第二次插入
 *        1. 取出无序表的第一个元素[2]和有序表进行依次比较，发现2最小，放在有序表的头部
 *        2. 插入后的有序表为 [2,5,9] , 无序表为 [11,3]
 *     第三次插入
 *        1. 取出无序表的第一个元素[11]和有序表进行依次比较,发现比9大，是最大值，放在有序表的末尾
 *        2. 插入后的有序表为 [2,5,9,11] , 无序表为 [3]
 *     第四次插入
 *        1. 取出无序表的第一个元素[3]和有序表进行依次比较,发现比2大，放在2的后面
 *        2. 插入后的有序表为 [2,3,5,9,11] , 无序表为 []
 *
 *  至此，无序表没有数据，排序完成，结果是 array = [2,3,5,9,11]
 *
 *  整体的插入过程类似扑克牌摸牌摆排的过程,每摸一张，就放在手上手牌的合适位置。
 *
 *
 */
public class InsertionSort {

    public static void main(String[] args) {

        int [] array = {9,5,2,11,3};

        // imitateInsertionSort(array);

        System.out.println("排序前 : " + Arrays.toString(array));
        insertionSort(array);
        System.out.println("排序后 : " + Arrays.toString(array));

        // 插入排序耗时查看 , 时间复杂度 : O(n^2)
        int [] testArr = new int[150*1000];
        // 随机赋值
        for (int index = 0; index < testArr.length; index++) {
            testArr[index] = (int)(Math.random() * 200*1000);
        }
        Instant start = Instant.now();
        insertionSort(testArr);
        Instant end = Instant.now();
        long used = ChronoUnit.MILLIS.between(start, end);

        // 15w  ----  1.8s
        System.out.println("插入排序耗时 : " + used + " 毫秒");

    }

    /**
     * 插入排序
     * @param array
     */
    private static void insertionSort(int[] array) {

        // 待插入值
        int insertVal = 0 ;
        // 待插入下标
        int insertIndex = -1 ;

        // 循环次数 length-1 , 第一次从第二个数开始
        for (int i = 1 ; i < array.length ; i++){
            // 当前值为待插入之
            insertVal = array[i] ;
            // 向前进行插入，插入下标为i-1
            insertIndex = i -1;

            while (insertIndex >= 0 && insertVal<array[insertIndex]){
                // 如果不满足插入的条件，需要将数据进行后移
                array[insertIndex+1] = array[insertIndex];
                // 继续向前插入，寻找合适的插入位置
                insertIndex -- ;
            }

            // 如果没有进行操作，表示当前数据不需要更改位置
            if (insertIndex +1 != i){
                // 修改位置，把当前值插入到合适的位置
                array[insertIndex+1] = insertVal ;
            }

        }

    }

    /**
     * 模拟插入排序过程
     * @param array
     */
    private static void imitateInsertionSort(int[] array) {
        // 当前插入值
        int insertVal = array[1] ;
        /**
         * 插入位置
         *
         * [12,21,55,9]
         * 第一次待插入值为 21 , 从12开始，向前比较，因此插入位置从12所在的位置(0)向前
         */
        int insertIndex = 1 - 1 ;

        /**
         * insertIndex >=0 : 待插入位置还没有到达数组最开始地方，继续前移,防止越界
         * insertVal < array[insertIndex] : 待插入的值比当前索引值要小，继续前移，直到合适位置
         */
        while (insertIndex >=0 && insertVal < array[insertIndex]){
            /**
             * 数据后移
             * [12,21,9,17,23] : 如果时第一次移动,把 12 放在21所在的位置
             *
             * [12,21,9,17,23] : 如果是第二次移动, 那么先把 21后移变成[12,21,21,17,23] , 然后指针前移在比较，将12后移变成[12,12,21,17,23]，在比较12和9并且index==1，确定插入位置
             */
            array[insertIndex+1] = array[insertIndex];
            insertIndex-- ;
        }

        // 把待插入位置的值放在当前位置

        // 待插入位置做了-1处理，在极端情况(比如插入位置为数组头部，那么对应的index = -1)，无法进行插入，需要对其+1处理
        array[insertIndex +1] = insertVal ;

        System.out.println("第1次 ： " + Arrays.toString(array));


        // 第2次
        insertVal = array[2] ;
        insertIndex = 1 ;
        while (insertIndex>=0 && insertVal<array[insertIndex]){
            array[insertIndex+1] = array[insertIndex];
            insertIndex -- ;
        }

        array[insertIndex +1] = insertVal ;
        System.out.println("第2次 ： " + Arrays.toString(array));

        // 第3次
        insertVal = array[3] ;
        insertIndex = 2 ;
        while (insertIndex>=0 && insertVal<array[insertIndex]){
            array[insertIndex+1] = array[insertIndex];
            insertIndex -- ;
        }

        array[insertIndex +1] = insertVal ;
        System.out.println("第3次 ： " + Arrays.toString(array));

        // 第4次
        insertVal = array[4] ;
        insertIndex = 3 ;
        while (insertIndex>=0 && insertVal<array[insertIndex]){
            array[insertIndex+1] = array[insertIndex];
            insertIndex -- ;
        }

        array[insertIndex +1] = insertVal ;
        System.out.println("第4次 ： " + Arrays.toString(array));
    }


}
