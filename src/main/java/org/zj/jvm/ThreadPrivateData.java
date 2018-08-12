package org.zj.jvm;

/**
 * Created by ZhangJun on 2018/8/11.
 */
public class ThreadPrivateData {
    //本地方法栈(线程私有)
    NativeStack nativeStack;

    //虚拟机栈(线程私有)
    JavaStack javaStack;

    //计数器(线程私有)
    int counter;

    public JavaStack getJavaStack() {
        return javaStack;
    }

    public ThreadPrivateData setJavaStack(JavaStack javaStack) {
        this.javaStack = javaStack;
        return this;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
