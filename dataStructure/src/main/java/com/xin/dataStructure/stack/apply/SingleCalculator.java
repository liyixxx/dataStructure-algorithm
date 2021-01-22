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
        String expression = "10+2*3+4/2/5+6" ;
        System.out.println(execExpression(expression));
        String expressionV2 = "(10+2*3+4-2*3)/2/5+6" ;
        System.out.println(execExpressionV2(expressionV2));

    }

    /**
     * 执行计算表达式 ， 不带括号
     * @param expression
     */
    public static Double execExpression(String expression){
        // 1. 准备数栈和运算符栈
        CalculatorStack<Double> numStack = new CalculatorStack<>();
        CalculatorStack<Character> operStack = new CalculatorStack<>();

        // 用于连接连续数字的字符串
        String connectStr = "" ;

        // 2. 遍历运算符
        char[] array = expression.toCharArray();
        for (int index = 0; index < array.length; index++) {

            if (isOper(array[index])){
                // 如果是运算符的情况

                if (operStack.isEmpty()){
                    // 运算符栈没有内容，直接放入运算符
                    operStack.push(array[index]);
                    continue;
                }

                // 判断是否时连续运算符
                if (isOper(array[index+1])){
                    throw new RuntimeException("表达式异常： 连续的操作运算符 ...");
                }

                // 运算符栈存在内容，需要比较优先级
                if (getPriority(array[index]) <= getPriority(operStack.peek())){
                    // 如果优先级小于或者等于栈中运算符，则需要进行计算处理
                    double result = 0d;
                    try {
                        // 避免除0
                        result = calcNum(numStack.pop(), numStack.pop(), operStack.pop());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return -1D ;
                    }
                    numStack.push(result);
                    operStack.push(array[index]);
                } else {
                    // 如果优先级大于栈中运算符，则进行入栈
                    operStack.push(array[index]);
                }

            } else {
                // 如果是数字的情况,需要判断是否为多位数

                // 拼接字符串
                connectStr += array[index];
                if (index == array.length -1){
                    numStack.push(Double.parseDouble(connectStr));
                } else {
                    if (isOper(array[index+1])){
                        // 如果下一位时运算符，则入栈，否则就拼接数字
                        numStack.push(Double.parseDouble(connectStr));
                        connectStr = "" ;
                    }
                }
            }

            // 遍历到末尾时，计算总结果
            if ( index == array.length -1 ){
                if (isOper(array[index])){
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
     * 计算表达式 添加()运算符处理
     * @param expression
     * @return
     */
    public static Double execExpressionV2(String expression){
        // 数栈和运算符栈
        CalculatorStack<Double> numStack = new CalculatorStack<>();
        CalculatorStack<Character> operStack = new CalculatorStack<>();
        // 连接数
        String connectNumStr = "";
        // 字符数组
        char[] array = expression.toCharArray();

        for (int index = 0; index < array.length; index++) {

            // 如果是 '(' 则直接放入运算符栈
            if (array[index] == '('){
                operStack.push('(');
                continue;
            }

            if (!isOper(array[index])){
                // 如果是数,则判断是否为多位数，再入数栈
                connectNumStr += array[index] ;
                if (index == array.length -1){
                    // 最后一个元素为数，直接入栈
                    numStack.push(Double.parseDouble(connectNumStr));
                } else {
                    if (isOper(array[index+1])){
                        // 如果下一位为运算符，则入栈
                        numStack.push(Double.parseDouble(connectNumStr));
                        connectNumStr = "" ;
                    }
                }
            } else {
                // 运算符的情况

                // 1. 栈中没有运算符，直接入栈
                if (operStack.isEmpty()){
                    operStack.push(array[index]);
                    continue;
                } else {
                    //2. 栈中有运算符 特殊考虑 '(' 和 ')'

                    // 2.1 考虑 ')' , 将()内的计算式处理
                    if (')' == array[index]){
                        while ('(' != operStack.peek()){
                            double r = calcNum(numStack.pop(), numStack.pop(), operStack.pop());
                            numStack.push(r);
                        }
                        // 将 '(' 出栈
                        operStack.pop();
                    } else {
                        // 2.2 处理其他运算符，优先级比较
                        if (getPriority(array[index]) <= getPriority(operStack.peek())){
                            // 2.2.1  当前优先级<= 栈中运算符优先级，进行计算，然后将当前运算符入栈
                            numStack.push(calcNum(numStack.pop(),numStack.pop(),operStack.pop()));
                            operStack.push(array[index]);
                        } else {
                            // 2.2.2  当前运算符优先级大，入栈
                            operStack.push(array[index]);
                        }
                    }
                }

            }

            // 遍历到尾部时，处理结果
            if ( index == array.length -1 ){
                if (isOper(array[index]) && ')' != array[index]){
                    throw new RuntimeException("表达式异常 ： 以非法的运算符为结尾 ....");
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

        return numStack.pop() ;
    }


    /**
     * 计算结果
     * @param number1
     * @param number2
     * @param operator
     * @return
     */
    public static Double calcNum(Double number1, Double number2, Character operator) {
        Double result = 0d ;
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
            default:
                throw new RuntimeException("未知运算符 ...");
        }
        return result ;
    }

    /**
     * 判断是否为运算符
     * @param oper
     * @return
     */
    public static boolean isOper(char oper){
        // return oper == '+' || oper == '-' || oper == '*' || oper == '/' ;
        return oper == '+' || oper == '-' || oper == '*' || oper == '/' || oper == '(' || oper == ')';
    }


    /**
     * 获取运算表达式优先级
     * @return
     */
    public static int getPriority(char oper){
        if (oper == '*' || oper == '/'){
            return 10 ;
        } else if (oper == '+' || oper == '-'){
            return 1 ;
        } else {
            return -1 ;
        }
    }

}

/**
 * 栈
 * @param <T>
 */
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
     * 判断是否空
     * @return
     */
    public boolean isEmpty(){
        return curr == -1 ;
    }

    /**
     * 扩容
     */
    private void expandStack(){
        int oldCapacity = maxSize;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        stack = Arrays.copyOf(stack , newCapacity) ;
    }

}