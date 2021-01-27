package com.xin.dataStructure.recursion;

/**
 * @Auther: xin
 * @DATE: 2021/1/27 9:56
 *
 * 递归应用： 八皇后问题
 *
 * 问题描述：
 *      在 8×8 格的国际象棋上摆放八个皇后，使其不能互相攻击，即：任意两个皇后都不能处于同一行、 同一列或同一斜线上，问有多少种摆法 (92)
 *
 * 思路，采取递归回溯的方式
 *      1) 第一个皇后先放第一行第一列
 *      2) 第二个皇后放在第二行第一列、然后判断是否 OK， 如果不 OK，继续放在第二列、第三列、依次把所有列都 放完，找到一个合适
 *      3) 继续第三个皇后，还是第一列、第二列……直到第 8 个皇后也能放在一个不冲突的位置，算是找到了一个正确 解
 *      4) 当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解， 全部得到.
 *      5) 然后回头继续第一个皇后放第二列，后面继续循环执行 1,2,3,4 的步骤
 *
 * EG:
 *      1. 放入第一行第一列 arr[0][0] = 1 ;
 *      2. 第一列放完以后，放第二行，也是从第一列开始 , arr[0][1] = 1 。放完之后和之前棋子进行判断，如果在同一列，或者同一斜线则失败 (判断逻辑)，将棋子向后移一位再试。 (递归)
 *      3. 第8个皇后也放完之后，就表示一个解已经完成了，此时在进行回溯。 从第8行开始，向右移动棋子，判断是否能放，不行则后移。
 *          如果一行全部移动完成，则向上回溯： 将上一列的棋子后移，然后判断，如果允许防止则重新再放置第8行的棋子。
 *          依次向上回溯，直到到达第一行，也就表示 从放入第一行第一列得到的正确解已经求完， 第一行棋子后移， arr[0][1] = 1 。然后循环上述步骤，直到arr[0][7] = 1 ，得到所有解法。
 */
public class EightQueens {

    // 棋盘尺寸
    int checkerBoardSize = 8 ;

    /**
     * 用一个一维数组来表示皇后摆放方式。  ==> 稀疏数组的方法
     *
     * 实现方式:
     *      数组下标 index 表示棋盘的第几行， 比如 index = 0 时， 表示再棋盘的第1行.
     *      数组下标 index 对应的值 array1[index] 表示皇后再第 index 行落下的列的位置，
     *          比如 array1[index] :  array1[1] = 1     表示皇后的落子为第二行，第二列 ， 对应二维数组的表现方式为 arr[1][1] = 1
     *          也就是 ， 如果用二维数组表示的话，表达式为 array2[index][arr[index]] = 1 。
     *
     * 那么用一维数组来判断是否允许棋子落在某一个位置时，由于数组已经记录了棋子再棋盘中的坐标位置，所以可以这样来判断：
     *      假定index为数组遍历下标(0-8) , n为当前棋子落下的第几行
     *      1. 判断是否在同一列：
     *          arr[index] == arr[n] 时 , 表示两者处在同一列了
     *          用二维数组的方法来表示就是 arr2[index][arr[index]] , arr2[n,arr[n]] , 二维数组的第二个元素相同，处在同一列。
     *
     *      2. 判断是否在同一个斜线
     *         先用二维数组的方法来判断，使用二维数组判断比较简单，就是比较两个坐标的距离(横纵坐标查相同)是否相等即可。
     *         比如 arr[0][0] = 1 , arr[1][2] = 1 。那么两者的横距离： 2 , 纵距离 1 . 并不相等，所以不在同一个斜线
     *
     *         那么用该思路，在一位数组的判断就比较容易了：
     *         已经分析过数组下标 index ， 表示 第几行， 对应的值表示第几列。 (可以直接类比到坐标轴的 x,y轴)
     *
     *         那么两者距离：
     *              x =  | index - n |
     *              y =  | arr[index] - arr[n] |
     *         x = y 时表示在同一个斜线
     */
    int [] checkerBoard = new int[checkerBoardSize] ;

    // 一共有多少种摆法
    static int count = 0 ;

    // 判断次数         建议让checkerBoardSize改小一些(2,3)，自己画一下二维数组取对应理解~~~
    static int judgeCount = 0 ;

    public static void main(String[] args) {
        EightQueens queens = new EightQueens();

        queens.check(0);
        System.out.println("count = " +count);
        System.out.println("backCount = " + judgeCount);
    }

    /**
     * 摆放
     * @param n
     */
    public void check(int n){

        if (n == checkerBoardSize){
            // 已经到第八个皇后的位置了，说明已经摆好了一个组合
            printResult(checkerBoard);
            // 那么就进行return，跳回到上一层栈中，在进行摆放和判断,直到跳回到最开始的栈，也就是摆放第1个棋子的时候，让第1个棋子后移，然后再重新摆放和判断
            return;
        }

        // 开始依次放入皇后
        for (int index = 0; index < checkerBoardSize; index++) {
            // 首先摆放在将该行的第1列
            checkerBoard[n] = index ;

            // 摆放后，判断该位置是否允许，如果可以就摆放下一行，否则就向后一列摆放
            if (judge(n)){
                // 允许
                check(n+1);
            }

            // 不允许: index后移，更改 checkBoard[n]的摆放方式

            // 相对二维数组，使用一维数组，由于是直接存放摆放的棋子的坐标，因此省去了将之前摆放的数据清0的步骤

            // 如果是使用二维数组，那么摆放时，需要将该坐标置为1，如果不允许，需要将该左边重新置为0，再将下一列置为1


        }

    }

    /**
     * 判断第n个皇后是否可以拜访在该位置
     * @param n     第n个皇后
     * @return
     */
    private boolean judge (int n){
        judgeCount++ ;
        // 如果是摆放的第一个皇后(n=0) ， 不进入循环直接返回true
        for (int index = 0; index < n; index++) {
            // 判断是否在同一行
            if (checkerBoard[index] == checkerBoard[n]){
                return false ;
            }

            // 判断是否在统一斜线
            if (Math.abs(checkerBoard[index] - checkerBoard[n]) == Math.abs(index - n)){
                return false ;
            }
        }
        return true ;
    }

    /**
     * 显示执行结果
     * @param check
     */
    public void printResult(int [] check){
        count ++ ;
        for (int i : check) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

}
