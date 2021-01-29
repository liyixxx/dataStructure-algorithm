package com.xin.algorithm.recursion;

/**
 * @Auther: xin
 * @DATE: 2021/1/26 13:50
 *
 * 递归代码测试 ， 添加对应的非递归的实现方法
 *
 */
public class RecursionTest {

    public static void main(String[] args) {

        // 递归打印
        printTestWithRecursion(5);
        // printTestWithoutRecursion(5);

        // 阶乘
        int result = factorialWithRecursion(5);
        result = factorialWithoutRecursion(5);
        System.out.println("factorial 5! = " + result);

        // 阶乘求合
        int sum = 0 ;
        for (int i = 1; i <= 5; i++) {
            sum += factorialWithRecursion(i) ;
            // sum += factorialWithoutRecursion(i);
        }
        System.out.println("sum = " + sum);

        // 斐波那契数列
        System.out.println("fibonacci(8) = " + fibonacciWithRecursion(8));
        System.out.println("fibonacci(7) = " + fibonacciWithOutRecursion(7));

        // 回文数判断
        System.out.println("palindrome : " + isPalindromeWithRecursion("122353221"));
        System.out.println("palindrome : " + isPalindromeWithOutRecursion("12235  53221"));

    }


    /**
     * 判断是否为回文字符 非递归实现
     * @param str
     * @return
     */
    private static boolean isPalindromeWithOutRecursion(String str) {

        char[] array = str.toCharArray();

        for (int i = 0 ; i < array.length/2 ; i++){
            if (array[i] == array[array.length - i -1]){
                continue;
            } else {
                return false ;
            }
        }

        return true ;

    }

    /**
     * 判断是否为回文数或者是回文字符
     * @param str
     */
    private static boolean isPalindromeWithRecursion(String str) {
        char[] array = str.toCharArray();
        // 首元素下标
        int minIndex = 0 ;
        // 末尾元素下标
        int maxIndex = array.length -1 ;

        if (maxIndex >= minIndex && array.length != 1){
            if (array[minIndex] != array[maxIndex]) {
                return false;
            } else {
                return isPalindromeWithRecursion(str.substring(minIndex + 1, maxIndex));
            }
        }
        return true ;
    }

    /**
     * 斐波那契数列 非递归实现
     * @param number
     * @return
     */
    private static int fibonacciWithOutRecursion(int number) {

        int result = -1 ;
        // 第一个数
        int first = 1 ;
        // 第二个数
        int second = 1 ;

        if (number == 0) {
            result = 0 ;
        }
        if (number == 1 || number == 2){
            result = 1 ;
        }

        for (int i = 3; i <= number; i++) {
            // 计算第三个数
            result = second + first ;
            // first 从第一个数变为第二个数 ， 后移
            first = second ;
            // second 从第二个数变为第三个数， 后移
            second = result ;
        }

        return result;
    }

    /**
     * 斐波那契数列
     * @param number
     */
    private static int fibonacciWithRecursion(int number) {
        if (number == 0) {
            return 0 ;
        } else if (number == 1){
            return 1 ;
        }
        if (number > 1){
            return fibonacciWithRecursion(number-1) + fibonacciWithRecursion(number-2);
        }
        return -1 ;
    }

    /**
     * 阶乘 非递归实现
     * @param number
     * @return
     */
    private static int factorialWithoutRecursion(int number) {
        int result = number ;
        while (--number > 1){
            result *= number ;
        }
        return result;
    }

    /**
     * 阶乘
     * @param number
     */
    private static int factorialWithRecursion(int number) {

        if (number == 1){
            return number ;
        } else {
            return factorialWithRecursion(number - 1) * number ;
        }

    }

    /**
     * 打印 非递归实现
     * @param number
     */
    private static void printTestWithoutRecursion(int number) {
        for (int i = 1; i <= number; i++) {
            System.out.println("number = " + i);
        }
    }

    /**
     * 递归打印
     * @param number
     */
    private static void printTestWithRecursion(int number) {
        if (number > 1) {
            printTestWithRecursion(number - 1);
        }
        System.out.println("number = " + number);
    }

}
