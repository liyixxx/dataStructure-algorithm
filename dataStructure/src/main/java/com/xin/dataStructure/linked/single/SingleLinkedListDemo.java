package com.xin.dataStructure.linked.single;


import lombok.NoArgsConstructor;
import lombok.ToString;

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

    }
}

/**
 * 单向链表
 */
class SingleLinkedList{

    /** 头节点 , 无法修改 */
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