package com.xin.algorithm.recursion;

/**
 * @Auther: xin
 * @DATE: 2021/1/26 13:50
 *
 * 递归代码测试
 *
 */
public class RecursionTest {

    public static void main(String[] args) {

        // 递归打印
        printTest(5);

        // 阶乘
        int result = factorial(5);
        System.out.println("factorial 5! = " + result);

        // 阶乘求合
        int sum = 0 ;
        for (int i = 1; i <= 5; i++) {
            sum += factorial(i) ;
        }
        System.out.println("sum = " + sum);

        // 斐波那契数列
        System.out.println("fibonacci(8) = " + fibonacci(8));

    }

    /**
     * 斐波那契数列
     * @param number
     */
    private static int fibonacci(int number) {
        if (number == 0) {
            return 0 ;
        } else if (number == 1){
            return 1 ;
        }
        if (number > 1){
            return fibonacci(number-1) + fibonacci(number-2);
        }
        return -1 ;
    }

    /**
     * 阶乘
     * @param number
     */
    private static int factorial(int number) {

        if (number == 1){
            return number ;
        } else {
            return factorial(number - 1) * number ;
        }

    }

    /**
     * 递归打印
     * @param number
     */
    private static void printTest(int number) {
        if (number > 1) {
            printTest(number - 1);
        }
        System.out.println("number = " + number);
    }

}
