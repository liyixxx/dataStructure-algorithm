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
 *
 *
 */
public class Josepfu {

    public static void main(String[] args) {
        CircleSingleLinkedList circleList = new CircleSingleLinkedList();

        // init
        circleList.addCHild(15);

        // show
        circleList.showCircleLinked();


    }
}


class CircleSingleLinkedList{

    /** 头节点 */
    private Child first = null ;

    /**
     * 新增节点
     * @param number
     */
    public void addCHild(int number){
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
     * 显示唤醒链表
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

}

// 节点对象
@ToString(exclude = "next")
@NoArgsConstructor
class Child{

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