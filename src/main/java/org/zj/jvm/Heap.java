package org.zj.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangJun on 2018/8/12.
 */
//存放new 出来的东西
public class Heap {
    Map<String,JvmInitedClass> jvmInitedClassMap=new HashMap<>();

    public Map<String, JvmInitedClass> getJvmInitedClassMap() {
        return jvmInitedClassMap;
    }

    public void setJvmInitedClassMap(Map<String, JvmInitedClass> jvmInitedClassMap) {
        this.jvmInitedClassMap = jvmInitedClassMap;
    }
}
