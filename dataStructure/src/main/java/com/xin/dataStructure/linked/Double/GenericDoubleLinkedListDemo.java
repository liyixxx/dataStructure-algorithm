package com.xin.dataStructure.linked.Double;

import com.sun.org.apache.regexp.internal.RE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Auther: xin
 * @DATE: 2021/1/7 15:14
 * <p>
 *  实现泛型双链表的基本功能 （添加head 和 tail指针 , 指向链表的头和尾）
 *
 */
public class GenericDoubleLinkedListDemo {

    public static void main(String[] args) {
        GenericDoubleLinkedList<Object> list = new GenericDoubleLinkedList<>();
        list.addNode("111");
        list.addNode(222);
        list.addNode(666);

        System.out.println("init :");
        list.showLinked(false);

        System.out.println("add node : ");
        list.addNode("333",2);
        list.addNode("444",3);
        list.showLinked(false);

        System.out.println("remove last node : ");
        list.removeLast();
        list.showLinked(false);

        System.out.println("remove first node : ");
        list.removeFirst();
        list.showLinked(false);

        System.out.println("remove node by index : ");
        list.removeIndex(1);
        list.showLinked(false);

    }
}

class GenericDoubleLinkedList<T> {

    @NoArgsConstructor
    @ToString(exclude = {"pre","next"})
    @Data
    class TNode<T> {
        private T data;
        private TNode pre;
        private TNode next;

        public TNode(T data) {
            this.data = data;
        }

        public TNode(TNode pre, TNode next) {
            this.pre = pre;
            this.next = next;
        }
    }

    // 头指针
    private TNode head ;
    // 尾指针
    private TNode tail ;

    /**
     * 构造
     */
    public GenericDoubleLinkedList() {
        // 初始化head 和 tail 节点
        // 初始 head = tail = null
        head = new TNode(null,null);
        tail = new TNode(null,null);
        head.next = tail ;
        tail.pre = head ;
    }

    public void addNode(T data){
        TNode entry = new TNode(data);
        TNode curr = head;
        while (curr.next != tail){
            curr = curr.next;
        }
        curr.next = entry ;
        tail.pre = entry ;
        entry.pre = curr ;
        entry.next = tail ;
    }

    /**
     * 在链表指定位置添加 (head 和 tail 之间的任意节点)
     * @param data
     * @param index
     */
    public void addNode(T data , int index){
        TNode<T> entry = new TNode<>(data);
        if (index < 0 || index > length() -1){
            System.out.println("参数不合法 ...");
            return;
        } else if (index == length()){
            // 允许data插入到链表尾 将上述if改为 index < 0 || index > length() , 然后添加该 else 分支语句
            addNode(data);
            return;
        }
        TNode curr = this.head.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        // 指定位置添加:
        // 连接 head --> tail 这条线
        curr.pre.next = entry;
        entry.next = curr ;
        // 连接tail --> head 这条线
        entry.pre = curr.pre;
        curr.pre = entry ;
    }

    /**
     * 删除尾节点
     */
    public void removeLast(){
        if (isEmpty()) {
            System.out.println("链表为空 ... ");
            return;
        }
        TNode curr = tail.pre ;
        if (curr.pre == head){
            tail.pre = head ;
            head.next = tail ;
        } else {
            curr.pre.next = tail ;
            tail.pre = curr.pre ;
        }
    }

    /**
     * 删除头节点
     */
    public void removeFirst(){

        if (isEmpty()){
            System.out.println("链表为空 ...");
            return;
        }
        TNode curr = head.next;
        if (curr.next == tail){
            head.next = tail ;
            tail.pre = head ;
        } else {
            head.next = curr.next ;
            curr.next.pre = head ;
        }
    }

    /**
     * 删除指定位置的节点
     * @param index  索引
     */
    public void removeIndex(int index){
        if (isEmpty()){
            System.out.println("the linkedList is empty ...");
            return;
        } else if (index < 0 || index > length() -1){
            System.out.println(" param index illegal");
            return;
        }

        if (index == 0) {
            removeFirst();
            return;
        } else if (index == length() -1){
            removeLast();
            return;
        }

        TNode curr = head.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next ;
        }
        // 节点自我删除
        curr.pre.next = curr.next ;
        curr.next.pre = curr.pre ;
    }

    /**
     * 输出链表
     * @param reverseFlag   是否反向输出
     */
    public void showLinked(boolean reverseFlag){
        if (isEmpty()){
            System.out.println("链表为空 ...");
            return;
        }
        TNode curr = null;
        if (reverseFlag){
            // 逆序输出
            curr = tail.pre ;
            while ( curr != head){
                System.out.println(curr.data + " ===> " + curr.data.getClass().getName());
                curr = curr.pre ;
            }
        } else {
            // 正常输出
            curr = head.next ;
            while ( curr != tail){
                System.out.println(curr);
                curr = curr.next ;
            }
        }
    }

    public Boolean isEmpty(){
        return head.next == tail ;
    }

    public int length(){
        TNode curr = this.head.next ;

        int len = 0 ;
        while (curr != tail){
            len ++ ;
            curr = curr.next;
        }
        return len ;
    }

}
