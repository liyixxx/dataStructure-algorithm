package com.xin.dataStructure.stack.basic;

import java.util.Arrays;

/**
 * @Auther: xin
 * @DATE: 2021/1/20 18:13
 *
 * 模拟实现带泛型的动态扩容栈
 *      参考ArrayList的扩容方式,简化流程
 *
 */
public class DynamicGenericStackDemo {

    public static void main(String[] args) {

        DynamicStack<String> staticStack = new DynamicStack<String>();
        System.out.println("static push : ");
        staticStack.push("a");
        staticStack.push("b");
        staticStack.push("c");
        staticStack.push("d");
        staticStack.showStack();
        // 超过最大容量，不允许再入栈
        staticStack.push("e");

        DynamicStack<String> dynamicStack = new DynamicStack<String>(4,true);
        System.out.println("dynamic push : ");
        dynamicStack.push("1");
        dynamicStack.push("2");
        dynamicStack.push("3");
        dynamicStack.push("4");
        // 增加栈容量
        dynamicStack.push("5");
        dynamicStack.showStack();

        System.out.println("pop : ");
        System.out.println(dynamicStack.pop());
        System.out.println(dynamicStack.pop());
        System.out.println(dynamicStack.pop());
        System.out.println(dynamicStack.pop());
        System.out.println(dynamicStack.pop());
        // 栈空
        System.out.println(dynamicStack.pop());
    }
}

class DynamicStack <T>{

    /** 最大容量 */
    private int maxSize ;

    /** 栈 */
    private Object[] stack ;

    /** 当前栈顶所在位置 */
    private int curr ;

    /** 是否可以扩容 */
    private boolean expand ;

    /** 可扩容时，影响因子 */
    private double impactFactor = 0.75;

    /**
     * 默认构造 ：
     *      不支持扩容，最大容量为4
     */
    public DynamicStack() {
        maxSize = 4 ;
        expand = false ;
        curr = -1 ;
        stack = new Object[maxSize] ;
    }

    /**
     * 指定栈容量和是否可以扩容
     * @param maxSize
     *          最大栈容量
     * @param expand
     *          是否支持扩容
     */
    public DynamicStack(int maxSize , boolean expand){
        this.maxSize = maxSize ;
        this.expand = expand ;
        curr = -1 ;
        stack = new Object[maxSize] ;
    }

    // /**
    //  * 指定影响因子（指定时，必须为可扩容的栈）
    //  * @param maxSize
    //  *          栈最大容量
    //  * @param impactFactor
    //  *          影响因子
    //  */
    // public DynamicStack(int maxSize , double impactFactor){
    //     if (ObjectUtil.isNotNull(impactFactor) || 0 == impactFactor){
    //         this.expand = true ;
    //         this.impactFactor = impactFactor ;
    //     } else {
    //         this.expand = false ;
    //     }
    //     this.maxSize = maxSize ;
    //     curr = -1 ;
    //     stack = new Object[maxSize] ;
    // }

    /**
     * 入栈
     * @param data
     */
    public void push(T data){
        if (!expand){
            // 固定容量
            if (isFull()){
                System.out.println("栈已满 ...");
                return;
            }
            stack[++curr] = data ;
        } else {
            // 动态
            if ((int)(maxSize * impactFactor) <= curr){
                // 判断是否需要扩容
                expandStack();
            }
            stack[++curr] = data ;
        }
    }

    /**
     * 出栈
     * @return
     */
    public T pop (){
        if (isEmpty()) {
            throw new RuntimeException("栈已空 ...");
        }
        return (T) stack[curr --];
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
     * 判断是否满，不支持扩容时使用
     * @return
     */
    public boolean isFull(){
        return maxSize-1 == curr ;
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

}