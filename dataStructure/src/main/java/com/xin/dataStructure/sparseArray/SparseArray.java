package com.xin.dataStructure.sparseArray;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xin
 * @DATE: 2020/12/25 14:58
 *
 * 稀疏数组 :: 稀疏数组和二维数组的转换
 *
 * 稀疏数组结构:
 *  编号     row              col                 val
 *   0     对应二维数组行数   对应二维数组列数    对应二维数组数据个数
 *   1      对应数据所在行     对应数据所在列       对应数据值
 *
 * EG：
 *      一个二维数组int[3][3] ={{0,1,0},{0,0,0},{1,0,0}}
 *      对应的稀疏数组为 : int[3][3] = {{3,3,2},{0,1,1},{2,1,1}}
 */
public class SparseArray {

    /** 文件路径 */
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator+"files" + File.separator + "mapData.txt" ;

    public static void main(String[] args) {
        // 初始二维数组
        int [][] chessArr = new int[9][9];
        chessArr[2][3] = 1 ;
        chessArr[3][5] = 2 ;
        chessArr[4][4] = 1 ;
        chessArr[5][2] = 2 ;

        System.out.println("二维数组初始情况: ");
        for (int[] chess : chessArr) {
            for (int data : chess) {
                System.out.printf("%d\t" , data);
            }
            System.out.println();
        }

        // 将二维数组转化为稀疏数组
        int[][] sparseArr =  transferSparceArr(chessArr);

        System.out.println("稀疏数组: ");
        for (int[] sparses : sparseArr) {
            for (int data : sparses) {
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }

        // 将稀疏数组存入磁盘
        saveData(sparseArr);

        // 读取磁盘文件
        int [][] fileArr = loadData();

        System.out.println("读取磁盘的数组数据: ");
        for (int[] arrs : fileArr) {
            for (int data : arrs) {
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }

        // 将稀疏数组还原为二维数组
        int [][] chessArr2 = transferTwoDimensionalArray(fileArr);
        System.out.println("转换后的二维数组:");
        for (int[] chess : chessArr2) {
            for (int data : chess) {
                System.out.printf("%d\t" , data);
            }
            System.out.println();
        }

    }

    /**
     * 读取磁盘文件
     * @return
     */
    private static int[][] loadData() {

        // 返回数组时数组的行列根据棋盘状况会改变,会有动态扩容的可能,采取使用List,没有手动实现List进行动态扩容
        List<String> list = new ArrayList<String>();

        BufferedReader in = null ;
        try {
            in = new BufferedReader(new FileReader(new File(FILE_PATH)));
            String str;
            while ((str = in.readLine())!=null){
                // 先添加文件数据到集合,在处理字符串转换为需要的数组
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 获取目标数组的行列数
        int row = list.size();
        int col = list.get(0).split("\t").length;
        int[][] returnArr = new int[row][col];

        // 赋值
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // list :=> {[1,1,1],[2,2,2]}
                String num = list.get(i).split("\t")[j];
                returnArr[i][j] = Integer.parseInt(num);
            }
        }
        return returnArr ;
    }

    /**
     * 存储稀疏数组到磁盘
     * @param sparseArr
     */
    private static void saveData(int[][] sparseArr) {
        File file = new File(FILE_PATH);
        FileWriter out = null ;
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            // 写入流
            out = new FileWriter(file);
            for(int i=0;i<sparseArr.length;i++){
                for(int j=0;j<sparseArr[i].length-1;j++){
                    out.write(sparseArr[i][j] + "\t");
                }
                out.write(sparseArr[i][sparseArr[i].length-1] + "");

                out.write("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out!=null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 稀疏数组还原为二维数组
     * @param sparseArr   稀疏数组
     * @return
     */
    private static int[][] transferTwoDimensionalArray(int[][] sparseArr) {
        // 创建二维数组
        int[][] chessArr = new int[sparseArr[0][0]][sparseArr[0][1]];
        // sparseArr[0][2] + 1 == sparseArr.length
        for (int i = 1; i <= sparseArr[0][2] ; i++) {
            // spar{2,3,1} ==> chess[2][3]=1
            chessArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        return chessArr ;
    }

    /**
     * 二维数组转稀疏数组
     * @param chessArr   二维数组
     * @return
     */
    private static int[][] transferSparceArr(int[][] chessArr) {
        int numCount = 0 ;

        // 获取二维数组行列数
        int row = chessArr.length;
        int col = chessArr[0].length;
        for (int[] chess : chessArr) {
            for (int data : chess) {
                if (data != 0){
                    numCount ++ ;
                }
            }
        }

        // 创建稀疏数组，对第一行进行赋值
        int [][] sparceArr = new int[numCount+1][3];
        sparceArr[0][0] = row ;
        sparceArr[0][1] = col ;
        sparceArr[0][2] = numCount ;

        // 稀疏数组数据赋值
        int totalCount = 0 ; // 记录是第几个非0数据
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (chessArr[i][j] != 0) {
                    //    a[1][1]  ==> a[x][0]=i  a[x][1]=j  a[x][2] = a[1][1]
                    totalCount++ ;
                    sparceArr[totalCount][0] = i ;
                    sparceArr[totalCount][1] = j ;
                    sparceArr[totalCount][2] = chessArr[i][j] ;
                }
            }
        }

        return sparceArr;
    }

}
