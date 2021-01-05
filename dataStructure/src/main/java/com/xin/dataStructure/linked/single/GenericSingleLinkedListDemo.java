package com.xin.dataStructure.linked.single;

import lombok.Getter;
import lombok.ToString;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Auther: xin
 * @DATE: 2020/12/29 9:13
 * @version : 1.0.0
 *
 * 单向链表  : 添加泛型
 *
 *  待优化： ID冲突，代码可读性等问题
 *
 */
public class GenericSingleLinkedListDemo {

    public static void main(String[] args) {
        GenericSingleLinkedList<String> list = new GenericSingleLinkedList<String>();

        // 添加链表数据
        list.addNode("伊泽瑞尔",null);
        list.addNode("卡莎",null);
        list.addNode("复仇之矛",null);
        list.addNode("奥巴马",null);
        System.out.println("初始化链表 ~~~");
        list.showLinked();

        // 指定位置插入
        list.addNode("卡牌",null,1);
        System.out.println("指定位置插入 ~~~");
        list.showLinked();

        // 修改链表节点
        list.update(1,"疾风剑豪");
        System.out.println("节点修改 ~~~");
        list.showLinked();

        // 删除链表节点
        list.remove();

        // 删除指定位置节点
        list.remove(1);

        System.out.println("节点删除 ~~~");
        list.showLinked();

        // 链表长度
        System.out.println("\n链表长度 length : " + list.length());
        System.out.println("链表数据最大ID maxId : " + list.getMaxId());

        // ------------------------------------------------------- interview question
        //                 一般都是传入链表的头节点，返回操作后的新链表的头节点

        //1. 求倒数第K个节点
        list.getLastIndexNode(1);
        //2. 链表反转
        // list.reverse();
        // list.showLinked();
        //3. 逆序打印
        list.reversePrint();
        //3.5 链表排序 -- 后续添加

        //4. 合并两个有序的单链表，要求结果依旧有序
        GenericSingleLinkedList<String> list1 = new GenericSingleLinkedList<String>();
        list1.addNode("劫",null);
        list1.addNode("小鱼人",null);
        list1.addNode("男刀",3);
        list.mergeLinked(list1);
    }

}

class GenericSingleLinkedList<T>{

    /** 头节点 */
    @Getter
    private TNode<T> head ;

    /** 节点类 */
    @ToString(exclude = "next")
    class TNode<T>{

        /** Id */
        public int id ;
        /** 数据 */
        public T data ;
        /** 下一节点 */
        public TNode next ;

        public TNode(T data) {
            this.data = data;
        }

        public TNode(int id , T data){
            this.id = id ;
            this.data = data ;
        }

        public TNode() {

        }
    }

    /** Id */
    private volatile AtomicInteger atomicId  ;

    /**
     * 构造
     */
    public GenericSingleLinkedList(){
        // 初始化head节点
        head = new TNode<T>();
        atomicId = new AtomicInteger();
    }

    /**
     * 向链表尾部添加节点
     * @param data      data
     * @param id        id , 默认自增
     */
    public void addNode( T data , Integer id ) {
        TNode<T> node = this.head;
        TNode<T> entry = null ;
        // FIXME: ID冲突问题
        if (id == null){
            entry = new TNode<>(atomicId.getAndIncrement(),data);
        } else {
            entry = new TNode<>(id,data);
        }
        // 头节点
        while (node.next != null){
            // 指针后移，直到链表尾部
            node = node.next ;
        }
        // 向链表末尾添加
        node.next = entry ;
    }

    /**
     * 将节点插入指定位置
     * @param data      节点数据
     * @param index     下标索引
     */
    public void addNode(T data , Integer id , Integer index){
        if (index == null) {
            addNode(data,id);
            return;
        }
        TNode<T> node = this.head;
        TNode<T> entry = new TNode<>(atomicId.getAndIncrement(), data);
        int len = 0 ;
        if (index < 0 ){
            System.out.println("error : index can not be less then zero");
            return;
        }
        if (index > length()){
            // 超过链表长度时，直接添加到链表尾部
            addNode(data,null);
            return;
        }

        while (node != null){
            if (len == index){
                // 插入位置 修改链表指针指向
                entry.next = node.next ;
                node.next = entry ;
            }
            len ++ ;
            node = node.next ;
        }

    }

    /**
     * 根据Id 修改节点数据
     * @param id        Id
     * @param data      data
     */
    public void update( Integer id , T data){
        if (head.next == null){
            System.out.println("链表为空~");
            return;
        }

        TNode<T> node = this.head;
        boolean flag = false ;
        while (node != null){
            if (node.id == id){
                node.data = data ;
                flag = true ;
            }
            node = node.next ;
        }

        if (!flag){
            System.out.printf("链表中没有ID: %d 的数据,无法进行修改~\n",id);
        }
    }

    /**
     * 删除尾节点
     */
    public void remove(){
        if (head.next == null){
            System.out.println("链表为空~");
        }
        TNode<T> node = this.head;
        while (node!=null){
            if (node.next.next == null){
                // 将倒数第二个节点设置为最后一个节点
                node.next = null ;
            }
            node = node.next ;
        }
    }

    /**
     * 删除指定位置的节点
     * @param index
     */
    public void remove(int index){
        if (head.next == null){
            System.out.println("链表已经为空了~");
            return;
        }
        if (index < 0 || index >= length()) {
            System.out.println("参数不和法,删除失败~");
            return;
        }
        if (index == 0){
            head.next = head.next.next ;
            return;
        }
        // 第一个节点
        TNode<T> node = this.head.next;
        // 第一个节点的下标
        int len = 0 ;
        while (true){
            if (node == null){
                break;
            }
            // 目标节点的上一个节点
            if ((len + 1) == index){
                node.next = node.next.next ;
                break;
            }
            len ++ ;
            node = node.next ;
        }
    }

    /**
     * 查看链表
     */
    public void showLinked(){
        if (head.next == null){
            System.out.println("链表暂无数据~");
        }
        TNode<T> node = head.next;
        while (true){
            if (node == null){
                // 尾节点
                break;
            }
            System.out.println(node);
            // 指针后移
            node = node.next ;
        }
    }

    /**
     * 计算当前链表的最大Id
     * @return
     */
    public int getMaxId(){
        if (head.next == null){
            return 0 ;
        }
        TNode node = head.next;
        Integer maxId = node.id ;
        while (node.next != null){
            Integer nextId = node.next.id ;
            maxId = maxId.compareTo(nextId) > 0 ? maxId : nextId ;
            node = node.next;
        }
        // atomicId.compareAndSet(atomicId.get(),maxId);
        // return atomicId.get() ;
        return maxId ;
    }

    /**
     * 计算链表长度
     * @return
     */
    public int length(){
        if (head.next == null){
            return 0 ;
        }

        int length = 0 ;
        TNode<T> node = this.head.next;
        while (node!=null){
            length ++ ;
            node = node.next ;
        }

        return length ;
    }

    /**
     * 获取倒数第 index 个节点的信息
     * @param index
     * @return
     */
    public Object getLastIndexNode(int index){
        if (index <= 0 || index > length()){
            return null ;
        }
        TNode node = this.head.next ;
        for (int i = 0; i < length() - index; i++) {
            node = node.next ;
        }
        return node.data ;
    }

    /**
     * 链表反转
     */
    public void reverse(){
        if (head.next == null){
            return;
        }
        // 新的链表的头节点
        TNode newHead = new TNode();
        // 当前遍历节点
        TNode node = this.head.next;
        // 存放node.next的临时节点
        TNode next = null ;

        // 遍历反转
        while (node != null){
            // 先保存临时节点
            next = node.next ;

            // 将该节点指向新链表头部
            node.next = newHead.next;

            // 头插
            newHead.next = node ;

            // 节点指针后移
            node = next ;
        }
        // 修改源链表指向
        head.next = newHead.next ;
    }

    /**
     * 逆序打印
     */
    public void reversePrint(){
        if (head.next == null){
            return;
        }
        TNode node = head.next;
        Stack<TNode> stack = new Stack<>();
        while (node != null){
            stack.push(node);
            node = node.next;
        }
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }

    /**
     * 合并链表
     * @param list  应该传入的是链表的head节点
     * @return
     */
    public TNode<T> mergeLinked(GenericSingleLinkedList<T> list){
        if (head.next == null){
            return list.getHead().next ;
        }
        if (list.getHead().next == null){
            return head.next ;
        }
        TNode node1 = head.next;
        TNode node2 = list.getHead().next;

        // 结果链表的第一个节点
        TNode head = new TNode() ;
        // 定义辅助指针，该指针永远指向第一个节点，不发生变化
        TNode dummy = head ;

        while (node1 != null && node2!= null){
            if (node1.id <= node2.id){
                head.next = node1 ;
                node1 = node1.next ;
            } else {
                head.next = node2;
                node2 = node2.next;
            }
            head = head.next ;
        }
        // 后续链表元素追加
        if (node1 != null){
            head.next = node1 ;
        } else {
            head.next = node2 ;
        }
        // 返回第一个节点
        return dummy.next;
    }
}