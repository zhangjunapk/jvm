package org.zj.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangJun on 2018/8/11.
 */
public class ShareData {
    //方法区(存放类数据**奇葩)(线程共享的)
    Map<String,JvmClass> methodArea=new HashMap<>();
    //堆 heap(线程共享的)


    public Map<String, JvmClass> getMethodArea() {
        return methodArea;
    }

    public void setMethodArea(Map<String, JvmClass> methodArea) {
        this.methodArea = methodArea;
    }
}
