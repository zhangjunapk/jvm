package org.zj.jvm;

import java.util.NoSuchElementException;

/**
 * Created by ZhangJun on 2018/8/11.
 */
//操作数栈
public class OperandStack {
    Object[] values;
    int index = 0;

    public OperandStack(int len) {
        values = new Object[len];
    }

    public Object get(int index) {
        try {
            return values[index];
        } catch (Exception e) {
            System.out.println("出错 " + e.getMessage());
        }
        return null;
    }

    /**
     * 放到栈顶的
     *
     * @param obj
     * @return
     */
    public Object push(Object obj) {
        values[this.index++] = obj;
        System.out.println("当前栈顶的数据" + pick());
        return obj;
    }

    /**
     * 栈顶出栈
     *
     * @return
     */
    public Object pop() {

        System.out.println("当前操作数栈的元素");
        for(Object o:values){
            System.out.println("  "+o);
        }
        System.out.println("end ");
        Object result = values[index - 1 < 0 ? 0 : index - 1];
        values[index - 1 < 0?0:index - 1] =null;

        //后面的数据应该上移
        for (int i = 0; i < values.length - 1; i++) {
            if (values[i] == null) {
                values[i] = values[i + 1];
                values[i + 1] = null;
            }
        }
        index--;

        return result;
    }

    /**
     * 获得栈顶数据，不出栈
     *
     * @return
     */
    public Object pick() {
        return values[index - 1 <= 0 ? 0 : index - 1];
    }

    public static void main(String[] args) {
        OperandStack operandStack = new OperandStack(2);
        operandStack.push("a");
        operandStack.push("b");
        Object pop = operandStack.pop();
        System.out.println(pop);
        System.out.println(operandStack.pop());

    }

}
