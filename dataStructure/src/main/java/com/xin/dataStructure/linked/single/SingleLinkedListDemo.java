package com.xin.dataStructure.linked.single;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Stack;

/**
 * @Auther: xin
 * @DATE: 2020/12/28 16:13
 * 单向链表
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        // 初始化节点数据
        SingleHeroNode hero1 = new SingleHeroNode(5,"疾风剑豪","快乐风男");
        SingleHeroNode hero2 = new SingleHeroNode(7,"德玛西亚之力","德玛");
        SingleHeroNode hero3 = new SingleHeroNode(11,"发条魔灵","发条");
        SingleHeroNode hero4 = new SingleHeroNode(2,"赏金猎人","MF");
        SingleLinkedList linkedList = new SingleLinkedList();

        // 添加节点
        // linkedList.addNode(hero1);
        // linkedList.addNode(hero4);
        // linkedList.addNode(hero3);
        // linkedList.addNode(hero2);

        // 按照排名添加节点
        linkedList.addNodeByNo(hero1);
        linkedList.addNodeByNo(hero4);
        linkedList.addNodeByNo(hero3);
        linkedList.addNodeByNo(hero2);
        // linkedList.addNodeByNo(hero3); 重复节点，无法添加

        // 显示链表
        System.out.println("初始链表 ::");
        linkedList.showLinkedList();

        // 修改节点数据
        System.out.println("修改节点信息 ::");
        linkedList.update(new SingleHeroNode(4,"探险家","EZ"));

        // 显示修改后的链表信息
        linkedList.showLinkedList();

        // 删除节点
        // linkedList.remove();

        // 删除指定节点
        linkedList.remove(hero3);

        // 显示链表
        System.out.println("链表节点删除 :: ");
        linkedList.showLinkedList();

        // ------------------------------------------------------- 面试题
        System.out.println("\n------------------------------------------------------- some interview question --> test ：");
        // 初始数据
        SingleLinkedList interview = new SingleLinkedList();
        interview.addNode(new SingleHeroNode(1,"疾风剑豪","快乐风男"));
        interview.addNode(new SingleHeroNode(2,"德玛西亚之力","德玛"));
        interview.addNode(new SingleHeroNode(3,"发条魔灵","发条"));
        interview.addNode(new SingleHeroNode(4,"赏金猎人","MF"));
        System.out.println("init linkedList");
        interview.showLinkedList();

        // ------------------------------------------------------- some interview question --> test
        // 1. 求单链表中节点的个数
        System.out.println("length : " + interview.length());
        // 2. 求倒数第K个节点
        System.out.println("node : " + interview.getLastIndexNode(4));
        // 3. 链表反转
        // interview.reverseLinkedList();
        System.out.println("reverse linkedList : ");
        interview.showLinkedList();
        // 4. 逆序打印
        System.out.println("reverse print by stack : ");
        interview.reversePrint();
        // 5. 合并两个有序的单链表，要求结果依旧有序
        // 合并时需要保证传入链表有序，注释链表反转操作
        // mergeLinked(linkedList.getHead() , interview.getHead());
        mergeLinked2(linkedList.getHead() , interview.getHead());
    }

    /**
     * 合并两个有序的单链表，合并之后的单链表仍然有序
     * @param head1     传入链表1的头节点
     * @param head2     传入链表2的头节点
     */
    public static SingleHeroNode mergeLinked(SingleHeroNode head1 , SingleHeroNode head2){
        if (head1.next == null){
            return head2 ;
        }
        if (head2.next == null){
            return head1 ;
        }

        // 两个链表都不为空，进行遍历
        SingleHeroNode node1 = head1.next;
        SingleHeroNode node2 = head2.next;
        // 结果链表的第一个节点
        SingleHeroNode head = null ;
        // 结果链表的最后一个节点
        SingleHeroNode rear = null ;
        // 结果链表
        SingleLinkedList list = new SingleLinkedList();

        // 两个链表都还有数据
        while (node1 != null && node2 != null){
            if (node1.no <= node2.no){
                // 如果node1节点数据 <= node2节点数据， 将node1节点插入新链表且node1指针后移
                if (head == null){
                    // 选定头节点
                    head = node1 ;
                } else {
                    rear.next = node1 ;
                }
                rear = node1 ;
                node1 = node1.next ;
            } else {
                // 反之，将node2节点插入新链表且node2指针后移
                if (head == null){
                    // 选定头节点
                    head = node2 ;
                } else {
                    rear.next = node2 ;
                }
                rear = node2 ;
                node2 = node2.next ;
            }
        }
        // 处理剩余不为空的链表
        if (node1 != null){
            rear.next = node1 ;
        } else {
            rear.next = node2 ;
        }
        // 结果链表节点
        list.getHead().next = head ;

        System.out.println("\n链表合并后的结果:");
        list.showLinkedList();

        return head ;
    }

    /**
     * 合并有序链表 方式二 (更加直观易懂)
     * @param head1
     * @param head2
     * @return
     */
    public static SingleHeroNode mergeLinked2(SingleHeroNode head1 , SingleHeroNode head2){
        if (head1.next == null){
            return head2 ;
        }
        if (head2.next == null){
            return head1 ;
        }
        // 两个链表都不为空，进行遍历
        SingleHeroNode node1 = head1.next;
        SingleHeroNode node2 = head2.next;
        // 结果链表的第一个节点
        SingleHeroNode head = new SingleHeroNode() ;
        // 定义辅助指针，该指针永远指向第一个节点，不发生变化
        SingleHeroNode dummy = head ;

        while (node1 != null && node2 != null){
            if (node1.no <= node2.no){
                // 如果node1.no <= node2.no  将node1节点拼接到head节点后,同时node1指针后移
                head.next = node1 ;
                node1 = node1.next ;
            } else {
                // 反之 将node2节点拼接到head节点后,同时node2指针后移
                head.next = node2 ;
                node2 = node2.next ;
            }
            // 在head节点拼接完成之后，将head节点后移，否则就是在不断的修改head.next 而不是向链表后面追加元素
            head = head.next ;
        }

        // 后续链表元素追加
        if (node1 != null){
            head.next = node1 ;
        } else {
            head.next = node2 ;
        }

        SingleLinkedList list = new SingleLinkedList();
        list.getHead().next = dummy.next ;

        System.out.println("\n方式二，链表合并后的结果:");
        list.showLinkedList();

        return dummy.next ;
    }

}

/**
 * 单向链表
 */
class SingleLinkedList{

    /** 头节点 , 无法修改 */
    @Getter
    private SingleHeroNode head  = new SingleHeroNode();

    /**
     * 添加节点，不考虑排名问题，直接向链表尾部进行节点添加
     * @param singleHeroNode
     */
    public void addNode(SingleHeroNode singleHeroNode) {
        // 头节点
        SingleHeroNode tempNode = head ;
        while (true){
            if (null == tempNode.next) {
                break;
            }
            // 指针后移，直到链表尾部
            tempNode = tempNode.next ;
        }
        // 向链表末尾添加
        tempNode.next = singleHeroNode;
    }

    /**
     * 添加节点 考虑排名问题
     *      按照排名来进行节点添加
     *      如果目标排名已经存在，给出错误提示
     * @param singleHeroNode
     */
    public void addNodeByNo(SingleHeroNode singleHeroNode){
        SingleHeroNode tempNode = this.head;
        // 目标排名是否存在
        Boolean flag = false ;
        while (true){
            if (tempNode.next == null) {
                // 最后一个节点
                break;
            }
            if (tempNode.next.no > singleHeroNode.no){
                // 找到目标节点位置 ==> 添加节点
                break;
            } else if (tempNode.next.no == singleHeroNode.no){
                // 目标排名已经存在
                flag = true ;
                break;
            }
            // 链表指针后移
            tempNode = tempNode.next ;
        }
        // flag标识判断，决定是否添加节点
        if (flag){
            System.out.printf("编号 %d 在链表中已经存在~,无法添加\n", singleHeroNode.no);
        } else {
            /**
             *  插入节点 ==> 修改链表的指针指向
             *  linked :  data1 -> data2 -> data4 -> data5
             *  插入data3:
             *    1. 找到data2节点 : tempNode
             *    2. 修改节点指向:
             *          data3.next = data4 = tempNode.next
             *          data2.next = tempNode.next = data3
             *
             */
            singleHeroNode.next = tempNode.next;
            tempNode.next = singleHeroNode;
        }
    }

    /**
     * 根据No 修改节点信息
     * @param singleHeroNode
     */
    public void update(SingleHeroNode singleHeroNode){
        SingleHeroNode tempNode = head;
        boolean flag = false ;
        while (true){
            if (tempNode.no == singleHeroNode.no){
                // 找到该节点信息
                flag = true ;
                break;
            }
            if (tempNode.next == null){
                break;
            }
            tempNode = tempNode.next ;
        }

        if (flag){
            tempNode.name = singleHeroNode.name ;
            tempNode.nickName = singleHeroNode.nickName ;
        } else {
            System.out.printf("没有找到编号 %d 的节点，无法修改\n", singleHeroNode.no);
        }

    }

    /**
     * 删除链表的末尾节点
     */
    public void remove(){
        SingleHeroNode tempNode = this.head;
        if (tempNode.next == null){
            System.out.println("链表已经为空了~");
        }
        while (true){
            // 找到倒数第二个节点，将其next置为null
            // if (tempNode.next == null){
            //     // 尾节点
            //     break;
            // }
            if (tempNode.next.next == null){
                // 将尾节点置为null
                tempNode.next = null ;
                break;
            }
            // 指针后移
            tempNode = tempNode.next;
        }
    }

    /**
     * 删除链表指定节点
     * @param singleHeroNode
     */
    public void remove(SingleHeroNode singleHeroNode){
        SingleHeroNode tempNode = this.head;
        if (tempNode.next == null){
            System.out.println("链表已经为空了~");
        }
        // 标识是否找到目标节点
        boolean flag = false ;

        while (true){
            if (tempNode.next == null){
                // 尾节点
                break;
            }
            if (tempNode.next.no == singleHeroNode.no){
                // 待删除节点的前一个节点
                flag = true ;
                break;
            }
            tempNode = tempNode.next ;
        }

        if (flag){
            /**
             * Link: n1 - n2 - n3 - n4  删除n3节点：
             *      将n2的next指向n4 , n3节点没有引用 , 会被JVM回收
             */
            tempNode.next = tempNode.next.next ;
        } else {
            System.out.printf("没有找到no = %d 的节点~", singleHeroNode.no);
        }


    }

    /**
     * 显示链表数据
     */
    public void showLinkedList(){
        if (head.next == null){
            System.out.println("链表为空 ， 请向链表添加数据节点");
            return;
        }
        // 头节点
        SingleHeroNode tempNode = head.next;
        while (true){
            if (tempNode == null){
                break;
            }
            System.out.println(tempNode);
            // 指针后移
            tempNode = tempNode.next ;
        }
    }

    // ------------------------------------------------------- some interview question
    // 1. 求单链表中节点的个数
    // 2. 查找链表中的倒数第k个节点
    // 3. 反转单链表
    // 4. 倒叙打印单链表
    // 5. 合并两个有序的单链表，合并之后的单链表仍然有序

    /**
     * 求单链表中节点的个数
     * @return
     */
    public int length(){
        if (head.next == null){
            return 0 ;
        }
        int length = 0 ;
        SingleHeroNode node = this.head.next;
        while (node != null){
            length ++ ;
            node = node.next ;
        }
        return length ;
    }

    /**
     * 获取倒数第K个节点
     * @param index
     * @return
     */
    public SingleHeroNode getLastIndexNode(int index){
        if (index <= 0 || index > length()){
            return null ;
        }
        SingleHeroNode node = this.head.next;
        for (int i = 0; i < length() - index; i++) {
            node = node.next ;
        }
        return node ;
    }

    /**
     * 反转链表 ：头节点插入法
     * 新建一个头结点，遍历原链表，把每个节点用头结点插入到新建链表中。最后，新建的链表就是反转后的链表
     *      1. 定义新的head节点 reverseHead
     *      2. 定义临时节点，保存当前遍历节点的下一个节点 next
     *      3. ** 遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reverseHead 的最前端
     *      过程：
     *          3.1 : 保存下一次需要遍历的节点 ==>  next = node.next
     *          3.2 : 拼接目标节点 ==>  node.next = reverseHead.next
     *          3.3 : 取出当前节点，放在新链表的最前端 ==>  reverseHead.next = node
     *          3.4 : 指针后移 ==>  node = node.next
     *      4. 将源链表的next指向新链表的next ==>  head.next = reverseHead.next
     *
     */
    public void reverseLinkedList(){
        if (head.next == null){
            return;
        }
        // 新的head节点
        SingleHeroNode reverseHead = new SingleHeroNode();
        // 遍历
        SingleHeroNode node = this.head.next;
        // 临时节点，存放node.next
        SingleHeroNode next = null ;
        while (node!=null){
            // 临时节点
            next = node.next ;
            // ---------------------------------------------------------
            //          强烈建议进行debug，查看链表的节点来进行理解

            /**
             * 核心步骤：
             *   将节点指向新链表的队首
             *   第一次循环时, node.next [id = 2],reverseHead.next[null] ,做头插 : 将node[id=1]插入到新链表的队首
             *   第二次： node.next[id = 3] , reverseHead由于进行过头插了，队首数据发生改变，变为 node[id=1]
             *           node.next = reverseHead.next 会修改当前的节点 node.next=3 会修改为 node.next=1,这样node的节点状态就变成了node[id=2] -> node[id=1]
             *           然后再做头插法，把从新拼接的node链表拼接在新链表的队首 ,覆盖原有[node=1] 所以新链表的节点指向就变成了： node[id=2] -> node[id=1]
             *   后续就会重复第二轮的过程，不断拼接node节点，然后将node节点放在新链表的队首
             *   比如在第三次循环时， node节点就变成了 : node[id=3] -> node[id=2] -> node[id=1]  然后进行头插，到新的链表reverseHead中
             */
            node.next = reverseHead.next ;
            // 对新链表做头插动作
            reverseHead.next = node ;
            // 后移指针
            node = next ;
        }
        // 修改源链表head指向
        head.next = reverseHead.next ;

    }

    /**
     * 逆序打印链表 借助栈来实现
     */
    public void reversePrint(){
        if(head.next == null) {
            return;
        }
        // 创建栈
        Stack stack = new Stack<SingleHeroNode>();
        SingleHeroNode node = this.head.next;
        while (node != null) {
            stack.push(node);
            node = node.next ;
        }

        // 打印
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }

}

/**
 * 节点对象
 */
@ToString(exclude = "next")
@NoArgsConstructor
class SingleHeroNode {

    /** 编号 */
    public int no ;
    /** name */
    public String name ;
    /** 昵称 */
    public String nickName ;
    /** 下一节点 */
    public SingleHeroNode next ;

    public SingleHeroNode(int no , String name , String nickName ){
        this.no = no ;
        this.name = name ;
        this.nickName = nickName ;
    }

}