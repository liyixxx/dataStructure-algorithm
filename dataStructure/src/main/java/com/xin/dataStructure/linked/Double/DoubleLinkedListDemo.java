package com.xin.dataStructure.linked.Double;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Auther: xin
 * @DATE: 2021/1/6 15:21
 *
 * 双链表
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {
        DoubleLinkedList list = new DoubleLinkedList();
        list.addNode(new DoubleHeroNode(1,"白手","吊机"));
        list.addNode(new DoubleHeroNode(2,"狂战","红狗"));
        list.addNode(new DoubleHeroNode(3,"鬼泣","鬼哭"));
        list.addNode(new DoubleHeroNode(4,"修罗","瞎子"));

        System.out.println("init : ");
        list.showLinked();

        System.out.println("remove : ");
        list.remove(2);
        list.showLinked();

        list.update(new DoubleHeroNode(4,"剑鬼","见鬼"));
        System.out.println("update : ");
        list.showLinked();

        System.out.println("add index : ");
        list.addNode(new DoubleHeroNode(2,"狂战","红狗"),2);
        list.showLinked();
    }

}


class DoubleLinkedList{

    @Getter
    private DoubleHeroNode head = new DoubleHeroNode();

    /**
     * 在末尾新增节点
     * @param entry
     */
    public void addNode(DoubleHeroNode entry){
        DoubleHeroNode curr = this.head;
        while (curr.getNext() != null){
            curr = curr.getNext();
        }
        // 让源链表最后一个节点的next 指向新的节点
        curr.setNext(entry);
        // 让新节点的pre 指向源链表的最后一个节点
        entry.setPre(curr);
    }

    /**
     * 向指定位置添加节点
     * @param entry     数据
     * @param index     索引
     */
    public void addNode(DoubleHeroNode entry , int index){
        if (index<0 || index>length()-1){
            System.out.println("参数 index 不符合规则");
        }
        DoubleHeroNode curr = head.getNext();
        for (int i = 0; i < length() -1 ; i++) {
            if (i == index){
                break;
            }
            curr = curr.getNext();
        }
        /**
         * 插入节点数据,有链表如下：
         *      A -- C -- W -- E -- G
         *  在index=2 处插入数据D , 插入后链表为： A-C-D-W-E-G
         *      1. 将链表移动到index=2的位置，即W,让该位置记录为curr，插入的数据记为entry
         *      2. 开始数据插入：
         *          让C.next指向插入数据entry  ==>  curr.pre.next = entry
         *          让W.pre指向插入数据entry   ==>  curr.pre = entry
         *          让插入数据entry.pre指向C   ==>  entry.pre = curr.pre
         *          让插入数据entry.next指向W  ==>  entry.next = curr
         *
         */
        curr.getPre().setNext(entry);
        entry.setNext(curr);

        entry.setPre(curr.getPre());
        curr.setPre(entry);

    }

    /**
     * 删除节点
     * @param id
     */
    public void remove(int id){
        if (isEmpty()){
            System.out.println(" linkedList is empty ...");
            return;
        }
        DoubleHeroNode curr = head.getNext();
        /**
         * 双向链表可以实现自我删除：
         *    需要找到待删除节点： node 和 node的上下节点： node.pre , node.next
         *    将node.pre.next 指向 node.next 相当于让node的上一个节点直接指向了node的下一个节点
         *    将node.next.pre 指向 node.pre 相当于让node的下一个节点直接指向了node的上一个节点
         *    这样node的上下节点便连接起来了，跳过node节点，node节点便丢失了引用会被自动删除
         */
        boolean flag = false ;
        while (curr != null){
            // 找到node节点
            if (curr.getId() == id){
                flag = true ;
                break;
            }
            curr = curr.getNext() ;
        }

        if (flag){
            curr.getPre().setNext(curr.getNext());
            if (curr.getNext() != null){
                // 删除的不是最后一个节点
                curr.getNext().setPre(curr.getPre());
            }
        }
    }

    /**
     * 修改节点
     * @param entry
     */
    public void update(DoubleHeroNode entry){
        if (isEmpty()){
            System.out.println(" linkedList is empty ...");
            return;
        }
        DoubleHeroNode curr = head.getNext();
        Boolean flag = false ;
        while (curr!=null){
            if (curr.getId() == entry.getId()){
                flag = true ;
                break;
            }
            curr = curr.getNext();
        }
        if (flag){
            curr.setName(entry.getName());
            curr.setNickName(entry.getNickName());
        }
    }

    /**
     * 显示链表
     */
    public void showLinked(){
        if (isEmpty()){
            System.out.println(" linkedList is empty ...");
            return;
        }
        DoubleHeroNode curr = head.getNext();
        while (curr != null){
            System.out.println(curr);
            curr = curr.getNext();
        }
    }

    /**
     * 判断是否为空链表
     * @return
     */
    public Boolean isEmpty(){
        return (head.getNext()) == null ;
    }

    public int length(){
        if (isEmpty()){
            return 0 ;
        }
        DoubleHeroNode curr = head.getNext();
        int len = 0 ;
        while (curr != null){
            len ++ ;
            curr = curr.getNext() ;
        }
        return len ;
    }

}

@NoArgsConstructor
@ToString(exclude = {"next","pre"})
@Data
class DoubleHeroNode{

    /** id */
    private int id ;

    /** name */
    private String name ;

    /** nickName */
    private String nickName ;

    /** pre */
    private DoubleHeroNode pre ;

    /** next */
    private DoubleHeroNode next ;

    public DoubleHeroNode(int id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }

}