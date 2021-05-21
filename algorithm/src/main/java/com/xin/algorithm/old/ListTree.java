package com.xin.algorithm.old;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther : lihaonan
 * @date : Created in 2021/5/20 9:18
 *
 * 集合 - 树 相互转换和相关操作 [可优化]
 */
public class ListTree {

    private static List<TestDemo> list = new ArrayList<>();

    static {
        list.add(new TestDemo("1","湖北","0",1));
        list.add(new TestDemo("2","荆州","1",1));
        list.add(new TestDemo("3","武汉","1",2));
        list.add(new TestDemo("4","松滋","2",1));
        list.add(new TestDemo("5","黄石","1",3));
        list.add(new TestDemo("6","湖南","0",2));
        list.add(new TestDemo("7","长沙","6",1));
        list.add(new TestDemo("8","广东","0",3));
        list.add(new TestDemo("9","深圳","8",1));
        list.add(new TestDemo("10","珠海","8",2));
        list.add(new TestDemo("11","广州","8",3));
        list.add(new TestDemo("12","岳阳","6",2));
        list.add(new TestDemo("13","张家界","6",3));
        list.add(new TestDemo("14","永定区","13",1));
        list.add(new TestDemo("15","江西","0",4));
        list.add(new TestDemo("16","南昌","15",1));
        list.add(new TestDemo("17","赣州","15",2));
    }

    public static void main(String[] args) {
        // 1. 将list转换为Tree
        List<TestDemo> tree = listToTree(list);
        String str = JSONUtil.toJsonStr(tree);
        System.out.println("list转换为Tree : " + str);

        // 2. 根据Tree的叶子结点返回所有父节点(向上递归)
        List<TestDemo> upRecursionResult = search("4");
        List<String> superIds = upRecursionResult.stream().map(TestDemo::getSuperId).collect(Collectors.toList());
        for (String superId : superIds) {
            getSuperNode(upRecursionResult , superId);
        }
        System.out.println("向上递归 : " + JSONUtil.toJsonStr(listToTree(upRecursionResult)));

        // 3. 根据Tree的父节点返回所有的子节点(向下递归)
        List<TestDemo> downRecursionResult = search("1");
        List<String> ids = downRecursionResult.stream().map(TestDemo::getId).collect(Collectors.toList());
        for (String id : ids) {
            getChildNode(downRecursionResult , id);
        }
        System.out.println("向下递归 : " + JSONUtil.toJsonStr(listToTree(downRecursionResult)));

        // 4. 混合情况: 赛选结果既有顶层节点，又有子节点
        List<TestDemo> resultTree = search("1", "14");
        List<Map<String, Object>> mapList = resultTree.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("superId", item.getSuperId());
            return map;
        }).collect(Collectors.toList());
        for (Map<String, Object> item : mapList) {
            if (!"0".equals(item.get("superId"))){
                // 非顶层部门 向上递归
                getSuperNode(resultTree , item.get("superId").toString());
            } else {
                // 顶层部门 向下递归
                getChildNode(resultTree , item.get("id").toString());
            }
        }
        System.out.println("混合查询递归 : " + JSONUtil.toJsonStr(listToTree(resultTree)));

    }

    /**
     * 向下获取子节点
     *
     * @param downRecursionResult
     * @param id
     * @return void
     * @author lhl
     * @Date 2021/5/20 17:22
     */
    private static void getChildNode(List<TestDemo> downRecursionResult, String id) {
        List<TestDemo> list = ListTree.list.stream().filter(testDemo -> id.equals(testDemo.getSuperId())).collect(Collectors.toList());
        // 该顶层节点没有子节点 直接返回
        if (list.isEmpty()){
            return;
        }
        list.forEach(item->{
            if (!downRecursionResult.contains(item)){
                downRecursionResult.add(item);
            }
            getChildNode(downRecursionResult , item.getId());
        });
    }

    /**
     * 向上获取父节点
     *
     * @param searchResult
     * @param superId
     * @return void
     * @author lhl
     * @Date 2021/5/20 17:22
     */
    private static void getSuperNode(List<TestDemo> searchResult, String superId) {
        TestDemo one = getById(superId);
        if (!searchResult.contains(one)){
            searchResult.add(one);
        }
        // 如果上级节点不是最顶层节点，继续递归
        if (!"0".equals(one.getSuperId())){
            getSuperNode(searchResult , one.getSuperId());
        }
    }

    /**
     * 模拟根据ID查询
     *
     * @param id
     * @return TestDemo
     * @author lhl
     * @Date 2021/5/20 16:16
     */
    private static TestDemo getById(String id){
        for (TestDemo item : list) {
            if (item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    /**
     * 模拟数据库模糊搜索条件
     *
     * @param params
     * @return List<TestDemo>
     * @author lhl
     * @Date 2021/5/20 16:05
     */
    private static List<TestDemo> search(Object ... params) {
        List<TestDemo> result = new ArrayList<>();
        for (Object param : params) {
            for (TestDemo item : list) {
                if (param.equals(item.getId()) || param.equals(item.getName())){
                    result.add(item);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 将List转换为Tree
     *
     * @param list
     * @return List<TestDemo>
     * @author lhl
     * @Date 2021/5/20 16:00
     */
    private static List<TestDemo> listToTree(List<TestDemo> list) {
        // 所有的父节点
        List<TestDemo> parent = new ArrayList<>();
        // 每个父节点对应的子节点
        Map<String , List<TestDemo>> map = new HashMap<>();
        // 1. 处理父节点 , 每个节点对应的子节点列表
        for (TestDemo item : list) {
            String superId = item.getSuperId();
            if ("0".equals(superId)){
                parent.add(item);
            }
            if (map.containsKey(superId)){
                // 添加该节点到对应的superId的节点下
                map.get(superId).add(item);
            } else {
                // 新增节点
                List<TestDemo> child = new ArrayList<>();
                child.add(item);
                map.put(superId , child);
            }
        }
        // 构成树结构
        for (TestDemo item : list) {
            String id = item.getId();
            if (map.containsKey(id)){
                // 说明该节点是父节点 添加对应的子节点并排序
                List<TestDemo> child = map.get(id).stream()
                        .sorted(Comparator.comparingInt(TestDemo::getOrderNum)).collect(Collectors.toList());
                item.setChildren(child);
            }
        }
        // return
        return parent.stream().sorted(Comparator.comparingInt(TestDemo::getOrderNum)).collect(Collectors.toList());
    }

}

@Data
@NoArgsConstructor
class TestDemo {

    /**
     * Id
     */
    String id ;

    /**
     * name
     */
    String name ;

    /**
     * superId
     */
    String superId ;

    /**
     * orderNum
     */
    Integer orderNum ;

    /**
     * children
     */
    List<TestDemo> children ;

    public TestDemo(String id, String name, String superId, Integer orderNum) {
        this.id = id;
        this.name = name;
        this.superId = superId;
        this.orderNum = orderNum;
    }

}