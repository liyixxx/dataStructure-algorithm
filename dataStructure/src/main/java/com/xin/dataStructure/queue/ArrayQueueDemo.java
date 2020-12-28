package com.xin.dataStructure.queue;

/**
 * @Auther: xin
 * @DATE: 2020/12/28 10:03
 *
 * 队列：优秀列表
 *  FIFO - 先进先出
 *  实现：数组 / 链表
 *
 * 使用数组方式实现队列case
 *    使用类模拟队列基础的入队/出队操作
 *
 * 存在问题：
 *    数组不能进行复用，当队列数据满时，即使去除队列数据也无法进行后续数据插入
 *
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(3);

        // 入队
        queue.addData(10);
        queue.addData(20);
        queue.addData(30);

        // 获取队列数据
        queue.showQueue();

        // 出队
        System.out.println("\npeek : " + queue.peek());
        System.out.println("peek : " + queue.peek());
        System.out.println("peek : " + queue.peek());

        // 再次入队，报错 ==> 队列优化
        queue.addData(40);

    }
}

/**
 * 模拟队列
 */
class ArrayQueue {

    /** 队列容量 */
    private int maxSize ;

    /** 队首指针 */
    private int front ;

    /** 队尾指针 */
    private int rear ;

    /** 队列数据 */
    private int [] queue ;

    /**
     * 构造
     * @param initSize
     */
    public ArrayQueue(int initSize){
        maxSize = initSize ;
        queue = new int[initSize];
        // front 初始值设定： -1 指向队首元素的前一个位置 ==> 取数据时，先将队首指针后移
        // front 初始值设定： 0  指向队首元素所在位置     ==> 取数据时，取完数据将指针后移
        front = -1 ;
        // rear 初始值设定： -1 指向队尾元素所在位置     ==> 添加数据时，先将对位指针后移
        // rear 初始值设定： 0  指向队尾元素的后一个位置  ==> 添加数据时，添加后将指针后移
        rear = -1 ;
    }

    /**
     * 判断队列是否已满
     * @return
     */
    public Boolean isFull(){
        return rear == maxSize ;
    }

    /**
     * 判断队列是否为空
     * @return
     */
    public Boolean isEmpty(){
        return rear == front ;
    }

    /**
     * 入队
     * @param data
     */
    public void addData(int data){
        if (isFull()){
            System.out.println("队列已满，无法在进行数据添加");
            return;
        }
        // 队尾指针后移
        rear ++ ;
        // 入队赋值
        queue[rear] = data ;
    }

    /**
     * 出队
     * @return
     */
    public int peek(){
        if (isEmpty()){
            throw new RuntimeException("队列为空,需要进行数据入队");
        }
        // 队首指针后移
        front ++ ;
        // 返回数据
        return queue[front] ;
    }

    /**
     * 显示队列数据
     */
    public void showQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列为空,需要进行数据入队");
        }
        for (int i = 0; i < queue.length; i++) {
            System.out.printf("arr[%d] = %d " , i , queue[i]);
        }
    }

}