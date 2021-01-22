package com.xin.dataStructure.stack.apply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @Auther: xin
 * @DATE: 2021/1/22 12:53
 *
 * 波兰表达式[后缀表达式]计算
 *
 * 中缀表达式：
 *      中缀表达式就是常见的运算表达式 ， 人最熟悉中缀表达式，但计算机最难处理中缀表达式，所以往往将中缀表达式改为后缀表达式
 *      例如（3+4）*5-6就是一个中缀表达式
 *
 * 后缀表达式：
 *    一个表达式E的后缀形式可以如下定义：
 *      （1）如果E是一个变量或常量，则E的后缀式是E本身。
 *      （2）如果E是E1 op E2形式的表达式，这里op是任何二元操作符，则E的后缀式为E1'E2' op，这里E1'和E2'分别为E1和E2的后缀式。
 *      （3)如果E是（E1）形式的表达式，则E1的后缀式就是E的后缀式。
 *    如：我们平时写a+b，这是中缀表达式，写成后缀表达式就是：ab+
 *      (a+b)*c-(a+b)/e的后缀表达式为：ab+c*ab+e/-
 *      计算过程：
 *      (a+b)*c-(a+b)/e
 *         =  ((a+b)*c)((a+b)/e)-
 *         =  ((a+b)c*)((a+b)e/)-
 *         =  (ab+c*)(ab+e/)-
 *         =  ab+c*ab+e/-
 *
 */
public class PolandNotation {

    public static void main(String[] args) {

        // 给定后缀表达式，进行计算
        String suffixExpression = "3 4 + 11 * 65 -" ;
        Double suffixResult = execSuffixExpression(suffixExpression);

        System.out.println();

        // 给定正常的计算式(中缀表达式)，进行计算
        String inderExpression = "(3+11)*2-23" ;
        Double inOrderResult = execInOrderExpression(inderExpression);

    }

    /**
     * 计算后缀表达式
     * @param suffixExpression
     */
    public static Double execSuffixExpression(String suffixExpression) {

        // 1. 转换为List
        List<String> suffixList = getExpressionList(suffixExpression);

        // 2. 计算
        Double result = calculator(suffixList);

        // 打印执行结果
        System.out.println("suffixExpression : " + suffixExpression);
        System.out.println("suffixExpressionList : " + suffixList);
        System.out.println("calculator suffixExpression : " + result);

        // 3. 返回
        return result;
    }

    /**
     * 计算中缀表达式
     * 步骤：
     *      1. 转换表达式，获取表达式的每个参数
     *      2. 将中缀表达式转换为后缀表达式  (核心步骤)
     *      3. 计算后缀表达式，返回结果
     * @param inorderExpression
     */
    public static Double execInOrderExpression(String inorderExpression) {
        // 1. 将中缀表达式转换为对应的参数集合
        List<String> inorderList = getExpressionList(inorderExpression);

        // 2. 将中缀表达式转换为后缀表达式
        List<String> suffixList = parseSuffixExpreesionList(inorderList);

        // 3. 计算
        Double result = calculator(suffixList);

        // 打印执行结果
        System.out.println("inOrderExpression : " + inorderExpression);
        System.out.println("inOrderExpressionList : " + inorderList);
        System.out.println("suffixExpressionList : " + suffixList);
        System.out.println("calculator inOrderExpression : " + result);

        // 4. 返回
        return result ;
    }

    /**
     * 将中缀表达式转换为后缀表达式
     *
     * 步骤：
     *  1) 初始化两个栈：运算符栈s1和储存中间结果的栈s2
     *  2) 从左至右扫描中缀表达式
     *  3) 遇到操作数时，将其压s2
     *  4) 遇到运算符时，比较其与s1栈顶运算符的优先级
     *      1. 如果s1为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈；
     *      2. 否则，若优先级比栈顶运算符的高，也将运算符压入s1；
     *      3. 否则，将s1栈顶的运算符弹出并压入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较；
     *  5) 遇到括号时：
     *      1. 如果是左括号“(”，则直接压入s1
     *      2. 如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
     *  6) 重复步骤2至5，直到表达式的最右边
     *  7) 将s1中剩余的运算符依次弹出并压入s2
     *  8) 依次弹出s2中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式
     *
     *  EG: 将(3+4)*5-6 转换为后缀表达式
     *      1. 扫描 ( , 压入s1 , s1= '('
     *      2. 扫描 3 , 压入s2 , s2= '3'
     *      3. 扫描 + , 判断运算符为 '+' , s1中栈顶为'(' , 直接入栈 , s1= '(' , '+'
     *      4. 扫描 4 , 压入s2 , s2= '3' , '4'
     *      5. 扫描 ) , 判断运算符为 ')' , 依次弹出s1栈顶运算符到s2,直到s1栈顶为 '(' , 然后弹出'('
     *              5.1 将 + 弹出， s1= '('
     *              5.2 把 + 压入s2 , s2= '3' , '4' , '+'
     *              5.3 将 ( 弹出 , s1= ''
     *      6. 扫描 * , 判断运算符为 '*', s1为空，直接入栈， s1='*'
     *      7. 扫描 5 , 压入s2 , s2='3' , '4' , '+' , '5'
     *      8. 扫描 - , 判断运算符为 '-', s1不为空,栈顶也不是 '(' , 比较优先级 , 小于栈顶'*'的优先级
     *              将s1弹出，压入s2。  s1= '' , s2='3' , '4' , '+' , '5' , '*'
     *              再次比较，发现s1已为空,直接入栈  s1= '-'
     *      9. 扫描 6 , 压入s2 , s2='3' , '4' , '+' , '5' , '*' , '6'
     *      10. 扫描结束，将s1剩余内容 '-' 依次出栈，压入s2
     *          结果：    s1= ''
     *                   s2= '3' , '4' , '+' , '5' , '*' , '6' , '-'
     *      11. 将s2出栈结果逆序就是中缀表达式对应的后缀表达式 ==> 3, 4, +, 5, *, 6, -
     *
     * @param inorderList
     *
     * @return
     */
    private static List<String> parseSuffixExpreesionList(List<String> inorderList) {
        // 符号栈
        Stack<String> operStack = new Stack<>();
        // 结果栈 ： 结果栈s2从未进行过pop操作，一直是push，因此使用List来代替。并且可以不需要在进行逆序操作
        List<String> suffixList = new ArrayList<>();
        for (String item : inorderList) {
            if (item.matches("\\d+")){
                // 1. 如果是数，直接添加到List(s2)
                suffixList.add(item);
            } else if (item.equals("(")){
                // 2. 如果是 '(' , 入栈
                operStack.push(item);
            } else if (item.equals(")")){
                // 3. 如果是 ')' ， 需要将符号栈运算符弹出，添加到List(s2)
                while (!("(".equals(operStack.peek()))){
                    suffixList.add(operStack.pop());
                }
                // 3.1 弹出添加完成后，需要将 '(' 清除
                operStack.pop();
            } else {
                // 4. 运算符情况，需要比较优先级

                // 4.1 如果符号栈为空，或者栈顶为 '(' ，直接入栈
                if (operStack.isEmpty() || operStack.peek().equals("(")){
                    operStack.push(item);
                } else {
                    // 4.2 否则需要比较优先级
                    if (OperatorEnum.getPriority(item) > OperatorEnum.getPriority(operStack.peek())){
                        // 4.2.1 当前优先级高
                        operStack.push(item);
                    } else {
                        // 4.2.2 当前优先级低
                        // 将操作符栈(s1)弹出并且放入List(s2)，随后再和操作符栈栈定元素进行判断，以此循环
                        while (!operStack.isEmpty() && OperatorEnum.getPriority(item) <= OperatorEnum.getPriority(operStack.peek())){
                            suffixList.add(operStack.pop());
                        }
                        // 4.2.3 将当前运算符入栈
                        operStack.push(item);
                    }
                }
            }
        }

        // 5. 循环结束后，将s1剩余内容放入List中
        while (!operStack.isEmpty()){
            suffixList.add(operStack.pop());
        }

        // 6. 返回后缀表达式
        return suffixList ;
    }

    /**
     * 进行实际计算
     *
     * 计算方法：
     *      从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算（次顶元素 和 栈顶元素），并将结果入栈；
     *      重复过程直到表达式最右端，最后运算得出的值即为表达式的结果
     *
     * EG: 计算表达式为： 3, 4, +, 5, *, 6, -
     * 对应的计算过程为:
     *      1. 扫描 3 ，数字，入栈
     *      2. 扫描 4 ，数字，入栈
     *      3. 扫描 + ，运算符，弹出 3(次顶元素) 和 4(栈顶元素) ，计算 3+4=7 , 将7放入栈
     *      4. 扫描 5 ，数字，入栈
     *      5. 扫描 * ，运算符，弹出 7(次顶元素) 和 5(栈顶元素) ，计算 7*5=35 ， 将35入栈
     *      6. 扫描 6 ，数字，入栈
     *      7. 扫描 - ，运算符，弹出 35(次顶元素) 和 6(栈顶元素) , 计算 35-6=29 , 将29入栈
     *      8. 扫描结束，29为最终结果
     *
     * @param suffixList
     *          后缀表达式
     * @return
     */
    private static Double calculator(List<String> suffixList) {
        Stack<Double> stack = new Stack<>();
        for (String item : suffixList) {
            // 1. 数字判断  使用正则表达式
            if (item.matches("\\d+")){
                stack.push(Double.parseDouble(item));
            } else {
                // 2. 非数字，弹出两个数，和运算符进行计算
                Double topStack = stack.pop();
                Double secondTop = stack.pop();
                Double result = 0D ;
                if ("+".equals(item)){
                    result = secondTop + topStack ;
                } else if ("-".equals(item)){
                    result = secondTop - topStack ;
                } else if ("*".equals(item)){
                    result = secondTop * topStack ;
                } else if ("/".equals(item)){
                    result = secondTop / topStack ;
                }
                stack.push(result);
            }
        }
        // 最后栈中数据即为结果
        return stack.pop();
    }

    /**
     * 根据传入表达式得到表达式参数集合
     * @param expression
     * @return
     */
    private static List<String> getExpressionList(String expression) {
        // String[] array = expression.split(" ");
        // return Arrays.asList(array);

        char[] array = expression.toCharArray();
        String connectNumStr = "" ;
        ArrayList<String> expressionList = new ArrayList<>();
        for (int index = 0; index < array.length; index++) {
            // 使用ASCII码判断是否为数字
            /**
             * 常用字符对应码表
             * 0-9  :  48-57
             * A-Z  :  65-90
             * a-z  :  97-122
             */
            if (array[index] < 48 || array[index] > 57){
                if (array[index] == ' '){
                    continue;
                }
                // 如果是运算符 直接入栈
                expressionList.add("" + array[index]) ;
            } else {
                // 如果是数字，考虑多位数
                connectNumStr += array[index] ;
                for (int j = index + 1; j < array.length; j++) {
                    if (array[j] >= 48 && array[j] <= 57){
                        connectNumStr += array[j];
                        index ++ ;
                    } else {
                        break;
                    }
                }
                expressionList.add(connectNumStr);
                connectNumStr = "" ;
            }
        }

        return expressionList ;
    }

}


/**
 * 运算符优先级 ， 使用枚举方式
 */
enum OperatorEnum {

    ADD("+",1),

    SUB("-",1),

    MUL("*",2),

    DIV("/",2);

    /** 操作运算符 */
    private String oper ;

    /** 运算符优先级 */
    private int priority ;

    OperatorEnum(String oper, int priority) {
        this.oper = oper;
        this.priority = priority;
    }

    /**
     * 返回运算符的优先级
     * @param oper
     *        操作运算符
     * @return
     */
    public static int getPriority(String oper) {
        if (oper.equals(ADD.oper)){
            return ADD.priority ;
        }
        if (oper.equals(SUB.oper)){
            return SUB.priority ;
        }
        if (oper.equals(MUL.oper)){
            return MUL.priority ;
        }
        if (oper.equals(DIV.oper)){
            return DIV.priority ;
        }
        return -1 ;
    }

}