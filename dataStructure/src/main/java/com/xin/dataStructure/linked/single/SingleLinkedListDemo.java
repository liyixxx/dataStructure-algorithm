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
        HeroNode hero1 = new HeroNode(1,"疾风剑豪","快乐风男");
        HeroNode hero2 = new HeroNode(2,"德玛西亚之力","德玛");
        HeroNode hero3 = new HeroNode(3,"发条魔灵","发条");
        HeroNode hero4 = new HeroNode(4,"赏金猎人","MF");
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
        linkedList.update(new HeroNode(4,"探险家","EZ"));

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
        interview.addNode(new HeroNode(1,"疾风剑豪","快乐风男"));
        interview.addNode(new HeroNode(2,"德玛西亚之力","德玛"));
        interview.addNode(new HeroNode(3,"发条魔灵","发条"));
        interview.addNode(new HeroNode(4,"赏金猎人","MF"));
        System.out.println("init linkedList");
        interview.showLinkedList();

        // ------------------------------------------------------- some interview question --> test
        // 1. 求单链表中节点的个数
        System.out.println("length : " + interview.length());
        // 2. 求倒数第K个节点
        System.out.println("node : " + interview.getLastIndexNode(4));
        // 3. 链表反转
        interview.reverseLinkedList();
        System.out.println("reverse linkedList : ");
        interview.showLinkedList();
        // 4. 逆序打印
        System.out.println("rever print by stack : ");
        interview.reversePrint();

    }
}

/**
 * 单向链表
 */
class SingleLinkedList{

    /** 头节点 , 无法修改 */
    @Getter
    private HeroNode head  = new HeroNode();

    /**
     * 添加节点，不考虑排名问题，直接向链表尾部进行节点添加
     * @param heroNode
     */
    public void addNode(HeroNode heroNode) {
        // 头节点
        HeroNode tempNode = head ;
        while (true){
            if (null == tempNode.next) {
                break;
            }
            // 指针后移，直到链表尾部
            tempNode = tempNode.next ;
        }
        // 向链表末尾添加
        tempNode.next = heroNode ;
    }

    /**
     * 添加节点 考虑排名问题
     *      按照排名来进行节点添加
     *      如果目标排名已经存在，给出错误提示
     * @param heroNode
     */
    public void addNodeByNo(HeroNode heroNode){
        HeroNode tempNode = this.head;
        // 目标排名是否存在
        Boolean flag = false ;
        while (true){
            if (tempNode.next == null) {
                // 最后一个节点
                break;
            }
            if (tempNode.next.no > heroNode.no){
                // 找到目标节点位置 ==> 添加节点
                break;
            } else if (tempNode.next.no == heroNode.no){
                // 目标排名已经存在
                flag = true ;
                break;
            }
            // 链表指针后移
            tempNode = tempNode.next ;
        }
        // flag标识判断，决定是否添加节点
        if (flag){
            System.out.printf("编号 %d 在链表中已经存在~,无法添加\n",heroNode.no);
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
            heroNode.next = tempNode.next;
            tempNode.next = heroNode ;
        }
    }

    /**
     * 根据No 修改节点信息
     * @param heroNode
     */
    public void update(HeroNode heroNode){
        HeroNode tempNode = head;
        boolean flag = false ;
        while (true){
            if (tempNode.no == heroNode.no){
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
            tempNode.name = heroNode.name ;
            tempNode.nickName = heroNode.nickName ;
        } else {
            System.out.printf("没有找到编号 %d 的节点，无法修改\n", heroNode.no);
        }

    }

    /**
     * 删除链表的末尾节点
     */
    public void remove(){
        HeroNode tempNode = this.head;
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
     * @param heroNode
     */
    public void remove(HeroNode heroNode){
        HeroNode tempNode = this.head;
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
            if (tempNode.next.no == heroNode.no){
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
            System.out.printf("没有找到no = %d 的节点~",heroNode.no);
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
        HeroNode tempNode = head.next;
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
        HeroNode node = this.head.next;
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
    public HeroNode getLastIndexNode(int index){
        if (index <= 0 || index > length()){
            return null ;
        }
        HeroNode node = this.head.next;
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
        HeroNode reverseHead = new HeroNode();
        // 遍历
        HeroNode node = this.head.next;
        // 临时节点，存放node.next
        HeroNode next = null ;
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
        Stack stack = new Stack<HeroNode>();
        HeroNode node = this.head.next;
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
class HeroNode{

    /** 编号 */
    public int no ;
    /** name */
    public String name ;
    /** 昵称 */
    public String nickName ;
    /** 下一节点 */
    public HeroNode next ;

    public HeroNode(int no , String name , String nickName ){
        this.no = no ;
        this.name = name ;
        this.nickName = nickName ;
    }

}