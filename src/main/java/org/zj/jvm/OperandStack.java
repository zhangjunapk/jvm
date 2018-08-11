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
        values[index++] = obj;
        return obj;
    }

    /**
     * 栈顶出栈
     * @return
     */
    public Object pop(){
        if(index<values.length&&index>=0){
            values[index]=null;
            return values[index--];
        }
        throw new  NoSuchElementException();
    }

    /**
     * 获得栈顶数据，不出栈
     * @return
     */
    public Object pick(){

        System.out.println(index);

        if(index<values.length&&index>=0){
            return values[index-1<=0?0:index-1];
        }
        throw new  NoSuchElementException();
    }

    public static void main(String[] args) {
        OperandStack operandStack=new OperandStack(5);
        operandStack.push("a");
        operandStack.push("b");
        System.out.println(operandStack.pick());

    }

}
