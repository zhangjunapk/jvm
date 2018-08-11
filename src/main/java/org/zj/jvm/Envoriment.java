package org.zj.jvm;

/**
 * Created by ZhangJun on 2018/8/10.
 */
//虚拟机所运行的环境
public class Envoriment {
    //需要运行的类
    JvmClass jvmClass;

    //运行时数据
    RuntimeData runtimeData;

    public JvmClass getJvmClass() {
        return jvmClass;
    }

    public void setJvmClass(JvmClass jvmClass) {
        this.jvmClass = jvmClass;
    }

    public RuntimeData getRuntimeData() {
        return runtimeData;
    }

    public void setRuntimeData(RuntimeData runtimeData) {
        this.runtimeData = runtimeData;
    }
}
