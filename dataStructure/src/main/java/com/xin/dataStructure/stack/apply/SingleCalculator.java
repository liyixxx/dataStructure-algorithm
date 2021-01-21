package com.xin.dataStructure.stack.apply;

import java.util.Arrays;

/**
 * @Auther: xin
 * @DATE: 2021/1/21 14:51
 *
 * 利用栈实现简单的计算器
 *
 */
public class SingleCalculator {

    public static void main(String[] args) {
        CalculatorStack<String> stack = new CalculatorStack<>();

        String expression = "1+2*3+4/2+6-7" ;
        System.out.println(stack.execExpression(expression));
    }

}

class CalculatorStack <T>{

    /** 最大容量 */
    private int maxSize ;

    /** 栈 */
    private Object[] stack ;

    /** 当前栈顶所在位置 */
    private int curr ;

    /** 可扩容时，影响因子 */
    private double impactFactor = 0.75;

    /**
     * 默认构造 ：
     *      不支持扩容，最大容量为4
     */
    public CalculatorStack() {
        maxSize = 4 ;
        curr = -1 ;
        stack = new Object[maxSize] ;
    }

    /**
     * 指定栈容量
     * @param maxSize
     *          最大栈容量
     */
    public CalculatorStack(int maxSize){
        this.maxSize = maxSize ;
        curr = -1 ;
        stack = new Object[maxSize] ;
    }

    /**
     * 入栈
     * @param data
     */
    public void push(T data){
        // 动态
        if ((int)(maxSize * impactFactor) <= curr){
            // 判断是否需要扩容
            expandStack();
        }
        stack[++curr] = data ;
    }

    /**
     * 出栈
     * @return
     */
    public T pop(){
        if (isEmpty()) {
            throw new RuntimeException("栈已空 ...");
        }
        return (T) stack[curr --];
    }

    /**
     * 显示栈顶数据
     * @return
     */
    public T peek(){
        return (T) stack[curr];
    }

    /**
     * 显示栈
     */
    public void showStack(){
        if (isEmpty()){
            System.out.println("栈已空 ...") ;
            return;
        }
        for (int i = curr; i > -1; i--) {
            System.out.println(stack[i]);
        }
    }

    /**
     * 判断是否空
     * @return
     */
    public boolean isEmpty(){
        return curr == -1 ;
    }

    /**
     * 扩容
     */
    public void expandStack(){
        int oldCapacity = maxSize;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        stack = Arrays.copyOf(stack , newCapacity) ;
    }

    // --------------------------------------------- calculator

    /**
     * 计算
     * @param expression
     * @return
     */
    public int execExpression(String expression){

        // 1. 准备数栈和运算符栈
        CalculatorStack<Integer> numStack = new CalculatorStack<>();
        CalculatorStack<Character> operStack = new CalculatorStack<>();

        // 2. 遍历运算符
        char[] array = expression.toCharArray();
        for (int index = 0; index < array.length; index++) {

            if (this.isOper(array[index])){
                // 如果是运算符的情况

                if (operStack.isEmpty()){
                    // 运算符栈没有内容，直接放入运算符
                    operStack.push(array[index]);
                    // continue , 表达式不应该以运算符结尾
                    continue;
                }

                // 判断是否时连续运算符
                if (this.isOper(array[index+1])){
                    throw new RuntimeException("表达式异常： 连续的操作运算符 ...");
                }

                // 运算符栈存在内容，需要比较优先级
                if (this.priority(array[index]) <= this.priority(operStack.peek())){
                    // 如果优先级小于或者等于栈中运算符，则需要进行计算处理
                    int result = 0;
                    try {
                        // 避免除0
                        result = this.calcNum(numStack.pop(), numStack.pop(), operStack.pop());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return -1 ;
                    }
                    numStack.push(result);
                    operStack.push(array[index]);
                } else {
                    // 如果优先级大于栈中运算符，则进行入栈
                    operStack.push(array[index]);
                }

            } else {
                // 如果是数字的情况
                numStack.push(array[index] - '0');
            }

            // 遍历到末尾时，计算总结果
            if ( index == array.length -1 ){
                if (this.isOper(array[index])){
                    throw new RuntimeException("表达式异常 ： 以运算符为结尾 ....");
                }
                while (true){
                    if (operStack.isEmpty()){
                        break;
                    }
                    // 将计算结果push到数栈，直到计算结束【数栈剩余总计算结果】
                    numStack.push(calcNum(numStack.pop(),numStack.pop(),operStack.pop()));
                }
            }
        }

        return numStack.pop();

    }

    /**
     * 计算结果
     * @param number1
     * @param number2
     * @param operator
     * @return
     */
    private int calcNum(Integer number1, Integer number2, Character operator) {
        int result = 0 ;
        switch (operator){
            case '+':
                result = number2 + number1 ;
                break;
            case '-':
                result = number2 - number1 ;
                break;
            case '*':
                result = number2 * number1 ;
                break;
            case '/':
                if (number1 == 0){
                    throw new RuntimeException("表达式不符合规范 : 发现除0操作... ");
                }
                result = number2 / number1 ;
                break;
        }
        return result ;
    }

    /**
     * 判断是否为运算符
     * @param oper
     * @return
     */
    public boolean isOper(char oper){
        return oper == '+' || oper == '-' || oper == '*' || oper == '/' ;
    }


    /**
     * 获取运算表达式优先级
     * @return
     */
    public int priority(char oper){
        if (oper == '*' || oper == '/'){
            return 10 ;
        } else if (oper == '+' || oper == '-'){
            return 1 ;
        } else {
            return -1 ;
        }
    }

}