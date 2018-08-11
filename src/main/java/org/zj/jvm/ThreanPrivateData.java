package org.zj.jvm;

/**
 * Created by ZhangJun on 2018/8/11.
 */
public class ThreanPrivateData {
    //本地方法栈(线程私有)

    //虚拟机栈(线程私有)
    JvmStack jvmStack;

    //计数器(线程私有)
    int counter;

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
