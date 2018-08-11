package org.zj.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangJun on 2018/8/10.
 */
//运行时数据
public class RuntimeData {

    //方法区(存放类数据**奇葩)(线程共享的)
    Map<String,JvmClass> methodArea=new HashMap<>();
    //堆 heap(线程共享的)



    //本地方法栈(线程私有)

    //虚拟机栈(线程私有)
    JvmStack jvmStack;

    //计数器(线程私有)
    int counter;

    public Map<String, JvmClass> getMethodArea() {
        return methodArea;
    }

    public void setMethodArea(Map<String, JvmClass> methodArea) {
        this.methodArea = methodArea;
    }

    public JvmStack getJvmStack() {
        return jvmStack;
    }

    public void setJvmStack(JvmStack jvmStack) {
        this.jvmStack = jvmStack;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
