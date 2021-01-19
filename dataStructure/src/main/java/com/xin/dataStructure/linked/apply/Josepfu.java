package com.xin.dataStructure.linked.apply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author xin
 * @Date 2021/1/6 20:58
 *  单链表应用： 约瑟夫环(环形链表)
 *
 * 问题描述：
 *      设编号为 1，2，… n 的 n 个人围坐一圈，约定编号为 k（1<=k<=n）的人从 1 开始报数，
 *      数到 m 的那个人出列，它的下一位又从 1 开始报数，数到 m 的那个人又出列，依次类推，直到所有人出列为止，求由此产生一个出队编号的序列。
 */
public class Josepfu {

    public static void main(String[] args) {
        CircleSingleLinkedList circleList = new CircleSingleLinkedList();

        // init
        circleList.init(7);

        // show
        circleList.showCircleLinked();

        // length
        System.out.println("length : " + circleList.length());

        // josepfu
        circleList.execJosepfu(1,4);
    }
}


class CircleSingleLinkedList{

    /** 头节点 */
    private Child first = null ;

    /**
     * 创建环形链表
     * @param number
     */
    public void init(int number){
        if (number < 1){
            System.out.println(" index can not be less then one ...");
            return;
        }
        // 辅助指针 指向当前的节点
        Child curr = null ;
        for (int i = 1; i <= number; i++) {
            Child child = new Child(i);
            if (i == 1){
                // 第一个节点, first永远指向第一个节点
                first = child ;
                // first.next 指向first 构成环形队列
                first.next = first ;
                // curr 指向当前对象
                curr = first ;
            } else {
                // 让curr.next 指向新节点
                curr.next = child ;
                // 让新节点.next 指向first
                child.next = first ;
                // curr 后移
                curr = curr.next ;
                // curr = child ;
            }
        }
    }

    /**
     * 显示环形链表
     */
    public void showCircleLinked(){
        if (first == null){
            System.out.println("链表为空...");
            return;
        }
        Child curr = first;
        // 当curr.next为first时 ， 表示已经到达了链表的尾部
        while (true){
            System.out.println(curr);
            if (curr.next == first){
                break;
            }
            curr = curr.next ;
        }
    }

    public int length(){
        if (first == null){
            System.out.println("链表为空...");
            return 0;
        }
        Child curr = first;
        // 当curr.next为first时 ， 表示已经到达了链表的尾部
        int len = 0 ;
        while (true){
            len ++ ;
            if (curr.next == first){
                break;
            }
            curr = curr.next ;
        }
        return len ;
    }

    /**
     * 解决约瑟夫环问题
     *
     * @param startNo 开始报数的小孩编号
     * @param step  步长
     */
    public void execJosepfu(int startNo , int step){
        if (startNo < 0 || first.next == null || startNo > length()){
            System.out.println("参数不合法 ...");
            return;
        }
        // 1. 定义辅助指针p 该指针指向first的上一个节点
        Child p = first;
        while (p.next != first){
            p = p.next ;
        }
        // 2. 将first 指向开始报数的小孩的编号 , p指向first的上一个节点
        for (int i = 0; i < startNo -1 ; i++) {
            first = first.next ;
            p = p.next ;
        }
        // 3. 开始报数，当链表中只有一个元素节点即 p == first 时，结束
        while (true){
            if (p == first){
                break;
            }
            // 3.1 报数，将first节点指向目标报数的小孩的位置
            for (int i = 0; i < step -1 ; i++) {
                first = first.next ;
                p = p.next ;
            }
            // 3.2 将该小孩出圈
            System.out.printf("小孩编号 %d 出圈 ... \n" , first.getId());
            // 让first 指向 first.next
            first = first.next ;
            // p 指向 first的前一个节点
            p.next = first ;
        }
        // 4. 报数结束，p和first相同，出圈最后一个小孩
        System.out.printf("小孩编号 %d 出圈 ... \n" , first.getId());
    }
}

// 节点对象
@ToString(exclude = "next")
@NoArgsConstructor
class Child{

    @Getter
    @Setter
    private int id ;

    @Getter
    @Setter
    private String name ;

    protected Child next ;

    public Child(int id) {
        this.id = id;
    }

    public Child(int id, String name) {
        this.id = id;
        this.name = name;
    }

}