package com.xin.dataStructure.stack;

/**
 * @Auther: xin
 * @DATE: 2021/1/20 16:31
 *
 * 使用数组模拟栈
 *
 */
public class ArrayStackDemo {

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(5);

        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);
        arrayStack.push(4);
        arrayStack.push(5);
        System.out.println("push ...");
        arrayStack.showStack();

        arrayStack.pop();
        arrayStack.pop();
        arrayStack.pop();
        arrayStack.pop();

        System.out.println("pop ...");
        arrayStack.showStack();
    }

}

class ArrayStack{

    /** 栈容量 */
    private int maxSize ;

    /** 当前栈指针 */
    private int curr ;

    /** 栈帧 */
    private int [] stack ;

    /**
     * 初始化
     * @param maxSize 栈容量
     */
    public ArrayStack (int maxSize){
        this.maxSize = maxSize ;
        stack = new int[maxSize] ;
        curr = -1 ;
    }

    /**
     * 入栈
     * @param data
     */
    public void push(int data){
        if (isFull()) {
            System.out.println("栈以满 ...");
            return;
        }
        stack[++curr] = data ;
    }

    /**
     * 出栈
     * @return
     */
    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("栈已空 ...") ;
        }
        return stack[curr --] ;
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
     * 判断栈是否满
     * @return
     */
    public boolean isFull(){
        return  (maxSize-1) == curr ;
    }

    /**
     * 判断栈是否为空
     * @return
     */
    public boolean isEmpty(){
        return -1 == curr ;
    }

}