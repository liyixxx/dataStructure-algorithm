package com.xin.algorithm.recursion;


/**
 * @Auther: xin
 * @DATE: 2021/1/26 15:08
 *
 * 递归解决迷宫问题
 *
 */
public class Labyrinth {

    public static void main(String[] args) {

        // 颜色输出测试
        // System.out.println("\033[30m" + "write");
        // System.out.println("\033[31m" + "red");
        // System.out.println("\033[32m" + "yellow");
        // System.out.println("\033[37m" + "gray");

        // 初始化迷宫
        int [][] labyrinth = initLabyrinth(10,9);

        System.out.println("\033[30m" + "初始化迷宫 : ");
        for (int i = 0 ; i < 10 ; i++){
            for (int j = 0 ; j < 9 ; j++){
                if (labyrinth[i][j] == 0 ){
                    System.out.print("\033[30m" + labyrinth[i][j] + " ");
                } else {
                    System.out.print("\033[31m" + labyrinth[i][j] + " ");
                }
            }
            System.out.println();
        }

        // 走迷宫
        goWay(labyrinth,1,1);

        System.out.println("\033[30m" + "走过并标识后的迷宫 : ");
        for (int i = 0 ; i < 10 ; i++){
            for (int j = 0 ; j < 9 ; j++){
                if (labyrinth[i][j] == 2){
                    System.out.print("\033[32m" + labyrinth[i][j] + " ");
                } else if (labyrinth[i][j] == 1) {
                    System.out.print("\033[31m" + labyrinth[i][j] + " ");
                } else {
                    System.out.print("\033[30m" + labyrinth[i][j] + " ");
                }
            }
            System.out.println();
        }

    }

    // ----------------------------------------- 走迷宫  ： 建议DEBUG -----------------------------------------

    /**
     *
     * 约定：
     *     1. 当 labyrinth[i][j] 为 0 表示该点没有走过 当为 1 表示墙 ； 2 表示通路可以走 ； 3 表示该点已经 走过，但是走不通
     *     2. 在走迷宫时，需要确定一个策略(方法) 下->右->上->左 , 如果该点走不通，再回溯
     *
     *
     * @param map     迷宫
     * @param i       开始坐标
     * @param j       开始坐标
     * @return        是否能走到
     */
    private static boolean goWay(int[][] map, int i, int j) {
        // 终点坐标 [8][7]
        if (map[8][7] == 2 ){
            // 说明已经到达了终点
            return true ;
        } else {
            // 如果当前坐标没有走过
            if (map[i][j] == 0 ){
                // 修改坐标 ， 表示当前这个点已经走过了
                map[i][j] = 2 ;
                // 否则按照策略进行行走
                if (goWay(map, i + 1, j)){
                    // 下 : 先向下走，如果发现下面的点可以走(map[i+1][j]) 那么就吧下面的坐标标为2(以走过)
                    // 递归走完之后，当前的坐标已经来到了 map[i+1][j] 的位置，会在这个位置继续按照策略走，直到向下发现走不通(false)，那么就会返回到该坐标 ， 再向右走(策略)
                    // 随后每个点都会按照策略方式进行递归
                    return true ;
                } else if (goWay(map, i, j+1)){
                    // 右
                    // 当发现向下走不通时，会向右走，然后坐标就变为了 map[i][j+1]
                    // 坐标变化后，会按照原有规则，还是有限考虑向下走，除非向下无法通行
                    return true ;
                } else if (goWay(map, i-1, j)){
                    // 上
                    // 下，右 都走不通的情况
                    return true ;
                } else if (goWay(map,i,j -1)){
                    // 左
                    // 下，右，上 都走不通的情况
                    return true ;
                } else {
                    // 都不行 ， 表示为死胡同 ， 将该坐标置为3 返回false
                    map[i][j] = 3 ;
                    return false ;
                }
            } else {
                // 当前坐标点已经走过
                // 可能为 1 : 墙 ， 2 : 已经走过的道路 , 3 : 死胡同
                // 都是不再具有探测价值的点，返回false 也就是发生了回溯
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
    private static int[][] initLabyrinth(int row , int column) {
        int [][] labyrinth = new int[row][column];
        for (int i = 0 ; i < 10 ; i ++){
            labyrinth[i][0] = 1 ;
            labyrinth[i][8] = 1 ;
        }

        for (int j = 0 ; j < 9 ; j++){
            labyrinth[0][j] = 1 ;
            labyrinth[9][j] = 1 ;
        }

        labyrinth[3][4] = 1 ;
        labyrinth[2][5] = 1 ;
        labyrinth[7][7] = 1 ;
        labyrinth[4][6] = 1 ;
        labyrinth[6][4] = 1 ;
        labyrinth[4][1] = 1 ;
        labyrinth[4][2] = 1 ;
        labyrinth[4][3] = 1 ;

        return labyrinth ;
    }

}
