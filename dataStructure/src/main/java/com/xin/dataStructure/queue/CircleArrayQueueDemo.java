package com.xin.dataStructure.queue;

/**
 * @Auther: xin
 * @DATE: 2020/12/28 11:18
 *
 * 数组模拟环形队列
 *
 */
public class CircleArrayQueueDemo {

    public static void main(String[] args) {
        // 初始化环形队列
        CircleArray circleArray = new CircleArray(4);

        // 入队
        circleArray.addData(1);
        circleArray.addData(2);
        circleArray.addData(3);
        // circleArray.addData(1); 队列已满

        // 查看队列
        circleArray.showQueue();

        // 出队
        System.out.println("\npeek : " + circleArray.peek());

        // 再次入队
        circleArray.addData(4);
        circleArray.showQueue();

        // 全部出队
        System.out.println("\npeek : " + circleArray.peek());
        System.out.println("peek : " + circleArray.peek());
        System.out.println("peek : " + circleArray.peek());
        // System.out.println("peek : " + circleArray.peek()); 队列已经无数据

    }

}

/**
 * 模拟环形队列
 */
class CircleArray{

    /** 队列容量 */
    private int maxSize ;

    /** 队首指针 */
    private int front ;

    /** 队尾指针 */
    private int rear ;

    /** 队列数据 */
    private int [] queue ;

    /**
     * 构造  <==> ArrayQueue
     *
     *  1. 修改队首指针和队尾指针的指向
     *  2. 队列为空/满 的状态判断修改
     *  3. 入队/出队 逻辑
     *  4. 需要额外判断队列的有效数据
     *
     * @param initSize
     */
    public CircleArray (int initSize){
        // 空余一个空间作为约定 实际队列可用长度为initSize - 1
        maxSize = initSize ;
        queue = new int[initSize];
        // front 初始值设定： 0  指向队首元素所在位置      ==>  取数据时，取完数据将指针后移,同时需要考虑取模问题
        front = 0 ;
        // rear  初始值设定： 0  指向队尾元素的后一个位置  ==>  添加数据时，添加后将指针后移,同时需要考虑取模问题
        rear = 0 ;
    }

    /**
     * 判断队列是否已满
     * @return
     */
    public Boolean isFull(){
        /**
         * 取模运算:
         *
         *  rear可能会移动到front的前面
         *   初始:       0,0,0,0
         *              front = rear = 0         ==> (rear + 1) % maxSize = 1%4 != 0
         *   入队满:     1,1,1,0   ==> 增加了以为空间约定(方便进行取模运算) , 所以队列元素有三个时就满了
         *              front = 0 , rear = 3     ==> (rear + 1) % maxSize = 4%4 == 0
         *   出队:      0,0,1,0   ==> 出队两个元素
         *              front = 2 , rear = 3     ==> (rear + 1) % maxSize = 4%4 != 2
         *   再次入队:  2,0,1,0   ==> 入队一个元素
         *              front = 2 , rear = 0     ==> (rear +1) % maxSize = 1%4 != 2
         *   入队满:    2,2,1,0
         *              front = 2 , rear = 1     ==> (rear +1) % maxSize = 2%4 == 2
         *
         */
        return (rear + 1) % maxSize == front ;
    }

    /**
     * 判断队列是否为空
     * @return
     */
    public Boolean isEmpty(){
        // 当rear == front 时， 表示队列无数据了
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
        // 入队赋值
        queue[rear] = data ;

        // 队尾指针后移 , 取模
        rear = (rear + 1) % maxSize ;
    }

    /**
     * 出队
     * @return
     */
    public int peek(){
        if (isEmpty()){
            throw new RuntimeException("队列为空,需要进行数据入队");
        }
        // 队首元素
        int data = queue[front];
        // 队首指针后移
        front = (front +1 ) % maxSize ;
        return data;
    }

    /**
     * 显示队列数据
     */
    public void showQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列为空,需要进行数据入队");
        }

        for (int i = front ; i < front + effectiveDataSize(); i++) {
            System.out.printf("arr[%d] = %d " , i % maxSize , queue[i%maxSize]);
        }
    }

    /**
     * 有效数据个数
     * @return
     */
    public int effectiveDataSize(){
        /**
         *  计算队列有效数据个数:
         *    data       front   rear         calc
         *  0,0,0,0        0     0      (0+4-0) % 4 = 0
         *  1,1,1,0        0     3      (3+4-0) % 4 = 3
         *  0,0,1,0        2     3      (3+4-2) % 4 = 1
         *  2,0,1,0        2     0      (0+4-2) % 4 = 2
         *  2,2,1,0        2     1      (1+4-2) % 4 = 3
         *
         */
        return (rear + maxSize - front) % maxSize ;
    }

}
