package org.zj.jvm;

import java.util.List;
import java.util.Stack;

/**
 * Created by ZhangJun on 2018/8/10.
 */
//虚拟机栈
public class JvmStack {
    //局部变量表(方法内部的)
    LocalVariableTable localVariometer;
    //操作数栈(存放临时结果)
    OperandStack operandStack;

    //动态链接

    //返回地址
    public LocalVariableTable getLocalVariometer() {
        return localVariometer;
    }

    public void setLocalVariometer(LocalVariableTable localVariometer) {
        this.localVariometer = localVariometer;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public void setOperandStack(OperandStack operandStack) {
        this.operandStack = operandStack;
    }
}
