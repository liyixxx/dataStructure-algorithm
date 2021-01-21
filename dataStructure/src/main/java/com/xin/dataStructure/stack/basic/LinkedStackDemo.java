package com.xin.dataStructure.stack.basic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Auther: xin
 * @DATE: 2021/1/20 16:55
 *
 * 使用链表模拟栈
 *
 */
public class LinkedStackDemo {

    public static void main(String[] args) {
        LinkedStack linkedStack = new LinkedStack();
        System.out.println("init");
        linkedStack.showStack();

        linkedStack.push(1);
        linkedStack.push(2);
        linkedStack.push(3);
        linkedStack.push(4);
        linkedStack.push(5);

        System.out.println("push : ");
        linkedStack.showStack();

        linkedStack.pop();
        linkedStack.pop();
        linkedStack.pop();
        System.out.println("pop : ");
        linkedStack.showStack();
    }
}

class LinkedStack{

    @NoArgsConstructor
    @Getter
    @Setter
    class LinkedNode {
        int data ;
        LinkedNode next ;

        public LinkedNode(int data) {
            this.data = data;
        }
    }

    private LinkedNode head ;

    /** 当前栈容量 */
    private int size ;

    public LinkedStack() {
        head = new LinkedNode();
    }

    /**
     * 入栈
     * @param data
     */
    public void push(int data){
        LinkedNode entry = new LinkedNode(data);
        LinkedNode curr = this.head;
        while (curr.next != null){
            curr = curr.next ;
        }
        curr.next = entry ;
    }

    /**
     * 出栈
     * @return
     */
    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("栈已空 ...") ;
        }
        LinkedNode curr = this.head;
        int data ;
        while (true){
            if (curr.next.next == null){
                data = curr.next.data ;
                curr.next = null;
                break;
            }
            curr = curr.next ;
        }
        return data ;
    }

    /**
     * 显示栈
     */
    public void showStack(){
        // // 使用链表实现 ： 反转当前链表，再打印
        // // FIXME: 会破坏原有链表 ~ ,未采取
        //
        // if (isEmpty()){
        //     System.out.println("栈为空 ...");
        //     return;
        // }
        //
        // // 反转后的head节点
        // LinkedNode reverseHeadNode = new LinkedNode() ;
        //
        // // 当前遍历节点
        // LinkedNode curr = this.head.next;
        //
        // // 临时节点 存放curr.next
        // LinkedNode next = null ;
        //
        // // 遍历反转
        // while (curr != null){
        //     // 存放临时节点
        //     next = curr.next ;
        //
        //     // 反转节点
        //     curr.next = reverseHeadNode.next ;
        //
        //     // 做头插
        //     reverseHeadNode.next = curr ;
        //
        //     // 指针后移
        //     curr = next ;
        // }
        // // 得到新的链表： 0 - x - x - x - x  需要把head（0）去掉
        // reverseHeadNode = reverseHeadNode.next ;
        // // 遍历输出
        // while (reverseHeadNode != null){
        //     System.out.println(reverseHeadNode.data);
        //     reverseHeadNode = reverseHeadNode.next ;
        // }

        // TODO: 将当前栈放入另一个栈中，然后打印即可
        /**
         * 当前栈： 1 - 2 - 3 - 4 - 5   ==> 1为栈底 ， 打印显示：54321
         * 放入另外一个栈中 newStack , newStack的结构变为 ; 5 - 4 - 3 - 2 - 1     ==> 5为栈底
         * 然后让新的栈newStack依次出栈打印即可，便可完成栈的遍历了
         */
        LinkedStack newStack = new LinkedStack();
        LinkedNode curr = head.next;
        while (curr != null){
            newStack.push(curr.data);
            curr = curr.next;
        }

        // 出栈输出
        while (!newStack.isEmpty()){
            System.out.println(newStack.pop());
        }


    }

    /**
     * 判断栈是否为空
     * @return
     */
    public boolean isEmpty(){
        return head.next == null ;
    }
}