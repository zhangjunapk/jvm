package org.zj.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangJun on 2018/8/11.
 */
public class ShareData {
    //方法区(存放类数据**奇葩)(线程共享的)
    Map<String,JvmClass> methodArea;
    //堆 heap(线程共享的)
    Map<String,JvmInitedClass> heap;

    public Map<String, JvmInitedClass> getHeap() {
        return heap;
    }

    public void setHeap(Map<String, JvmInitedClass> heap) {
        this.heap = heap;
    }

    public Map<String, JvmClass> getMethodArea() {
        return methodArea;
    }

    public void setMethodArea(Map<String, JvmClass> methodArea) {
        this.methodArea = methodArea;
    }

}
