package com.xin.algorithm.recursion;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: xin
 * @DATE: 2021/1/26 15:59
 * <p>
 * 求迷宫的最短行走路径
 *      暴力解决方式 : 把每种方式策略都走一遍，求出最短的
 */
public class ShortestLabyrinth {

    public static void main(String[] args) {
        int [][] map = initMap(10 , 9 );
        getStepCount(map);

        System.out.println("\033[30m" + "初始化迷宫 : ");
        for (int i = 0 ; i < 10 ; i++){
            for (int j = 0 ; j < 9 ; j++){
                if (map[i][j] == 0 ){
                    System.out.print("\033[30m" + map[i][j] + " ");
                } else {
                    System.out.print("\033[31m" + map[i][j] + " ");
                }
            }
            System.out.println();
        }

        // 策略列表
        List<Map<String, Object>> list = StrategyEnum.toList();
        // 策略的对应步长
        Map<Integer,Integer> stepStarge = new HashMap<>();

        for (Map<String, Object> objectMap : list) {

            Integer strategyType = (Integer) objectMap.get("code");
            String desc = (String) objectMap.get("desc");

            goWay(map,1,1,strategyType);
            int stepCount = getStepCount(map);

            System.out.println("\033[30m" + "starge " + desc +  "使用的步长 : " + stepCount);
            System.out.println("\033[30m" + "starge " + desc +  "走过并标识后的迷宫 : ");
            for (int i = 0 ; i < 10 ; i++){
                for (int j = 0 ; j < 9 ; j++){
                    if (map[i][j] == 2){
                        System.out.print("\033[32m" + map[i][j] + " ");
                    } else if (map[i][j] == 1) {
                        System.out.print("\033[31m" + map[i][j] + " ");
                    } else {
                        System.out.print("\033[30m" + map[i][j] + " ");
                    }
                }
                System.out.println();
            }

            map = initMap(map.length , map[0].length);
            stepStarge.put(strategyType,stepCount);
        }

        // java8 stream流处理
        List<Map.Entry<Integer, Integer>> result = stepStarge.entrySet().stream()
                .sorted((step1, step2) -> step1.getValue().compareTo(step2.getValue()))
                .collect(Collectors.toList());

        // result.forEach(System.out::println);

        System.out.println("\033[30m" + "最短路径使用策略为 : " + StrategyEnum.getDesc(result.get(0).getKey()) + ", 使用的步数为 ：" + result.get(0).getValue());

    }

    /**
     * 获取步长
     * @param map
     * @return
     */
    public static int getStepCount(int[][] map){
        int stepCount = 0 ;
        for (int i = 0 ; i < map.length ; i++){
            for (int j = 0 ; j < map[i].length ; j++){
                if (map[i][j] == 2) {
                    stepCount ++ ;
                }
            }
        }
        return stepCount ;
    }

    /**
     *
     * 走迷宫
     *
     * @param map           迷宫
     * @param i             坐标
     * @param j             坐标
     * @param strategyType  策略
     */
    private static boolean goWay(int[][] map, int i, int j, int strategyType) {
        // 终点 map[8][7]
        if (map[8][7] == 2){
            return true ;
        } else {
            // 没有到达终点
            if (map[i][j] == 0){
                // 开始走迷宫
                map[i][j] = 2 ;

                switch (strategyType){
                    case 1 :
                        // 上下左右
                        if (goWay(map, i - 1, j,strategyType)){
                            return true ;
                        } else if (goWay(map, i+1, j,strategyType)){
                            return true ;
                        } else if (goWay(map, i, j-1,strategyType)){
                            return true ;
                        } else if (goWay(map,i,j+1,strategyType)){
                            return true ;
                        } else {
                            map[i][j] = 3 ;
                            return false ;
                        }
                    case 2:
                        //上下右左
                        if (goWay(map, i - 1, j,strategyType)){
                            return true ;
                        } else if (goWay(map, i+1, j,strategyType)){
                            return true ;
                        } else if (goWay(map, i, j+1,strategyType)){
                            return true ;
                        } else if (goWay(map,i,j-1,strategyType)){
                            return true ;
                        } else {
                            map[i][j] = 3 ;
                            return false ;
                        }
                    case 3:
                        // 上左下右
                        if (goWay(map, i - 1, j,strategyType)){
                            return true ;
                        } else if (goWay(map, i, j-1,strategyType)){
                            return true ;
                        } else if (goWay(map, i+1, j,strategyType)){
                            return true ;
                        } else if (goWay(map,i,j+1,strategyType)){
                            return true ;
                        } else {
                            map[i][j] = 3 ;
                            return false ;
                        }
                    case 4:
                        // 上左右下
                        if (goWay(map, i - 1, j,strategyType)){
                            return true ;
                        } else if (goWay(map, i, j-1,strategyType)){
                            return true ;
                        } else if (goWay(map, i, j+1,strategyType)){
                            return true ;
                        } else if (goWay(map,i+1,j,strategyType)){
                            return true ;
                        } else {
                            map[i][j] = 3 ;
                            return false ;
                        }
                    case 5:
                        // 上右下左
                        if (goWay(map, i - 1, j,strategyType)){
                            return true ;
                        } else if (goWay(map, i, j+1,strategyType)){
                            return true ;
                        } else if (goWay(map, i+1, j,strategyType)){
                            return true ;
                        } else if (goWay(map,i,j-1,strategyType)){
                            return true ;
                        } else {
                            map[i][j] = 3 ;
                            return false ;
                        }
                    case 6:
                        // 上右左下
                        if (goWay(map, i - 1, j,strategyType)){
                            return true ;
                        } else if (goWay(map, i, j+1,strategyType)){
                            return true ;
                        } else if (goWay(map, i, j-1,strategyType)){
                            return true ;
                        } else if (goWay(map,i+1,j,strategyType)){
                            return true ;
                        } else {
                            map[i][j] = 3 ;
                            return false ;
                        }
                    default:
                        throw new RuntimeException("nothing to do ...") ;
                }
            } else {
                return false ;
            }
        }

    }

    /**
     * 初始化迷宫
     * @param row       行
     * @param column    列
     * @return
     */
    private static int[][] initMap(int row , int column) {
        int [][] map = new int[row][column];
        for (int i = 0 ; i < 10 ; i ++){
            map[i][0] = 1 ;
            map[i][8] = 1 ;
        }

        for (int j = 0 ; j < 9 ; j++){
            map[0][j] = 1 ;
            map[9][j] = 1 ;
        }

        map[3][4] = 1 ;
        map[2][5] = 1 ;
        map[7][7] = 1 ;
        map[4][6] = 1 ;
        map[4][1] = 1 ;
        map[4][2] = 1 ;
        map[4][3] = 1 ;

        return map ;
    }
}

/**
 * 所有策略，放在枚举中
 * <p>
 * 按照 上(UP) 下(DOWN) 左(LEFT) 右(RIGHT)  简写 描述策略方式
 */
enum StrategyEnum {

    UDLR(1, "上下左右"),
    UDRL(2, "上下右左"),
    ULDR(3, "上左下右"),
    ULRD(4, "上左右下"),
    URDL(5, "上右下左"),
    URLD(6, "上右左下");

    // 暂时列举这六种情况

    int code;
    String desc;


    StrategyEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static List<Map<String, Object>> toList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StrategyEnum strategy : StrategyEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", strategy.code);
            map.put("desc", strategy.desc);
            list.add(map);
        }
        return list;
    }

    public static String getDesc(int code){
        if (code == UDLR.code){
            return UDLR.desc ;
        }
        if (code == UDRL.code){
            return UDRL.desc ;
        }
        if (code == ULDR.code){
            return ULDR.desc ;
        }
        if (code == ULRD.code){
            return ULRD.desc ;
        }
        if (code == URDL.code){
            return URDL.desc ;
        }
        if (code == URLD.code){
            return URLD.desc ;
        }
        return null ;
    }
}