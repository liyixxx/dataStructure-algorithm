package com.xin.dataStructure.linked.single.Double;

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

    private Boolean isEmpty(){
        return (head.getNext()) == null ;
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