package org.zj.jvm;

import com.sun.tools.classfile.ConstantPool;
import com.sun.tools.classfile.ConstantPoolException;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangJun on 2018/8/12.
 */
public class JvmInitedClass extends JvmClass{
    private ConstantPool constantPool;

    //jvmclass中的方法集合
    //key 是一个entry k 是方法名,v 是返回值
    Map<Map.Entry<String,String>,JvmMethod> methodMap=new HashMap<>();

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public Map<Map.Entry<String, String>, JvmMethod> getMethodMap() {
        return methodMap;
    }

    public void setMethodMap(Map<Map.Entry<String, String>, JvmMethod> methodMap) {
        this.methodMap = methodMap;
    }
    public void run(ShareData shareData,ThreadPrivateData threadPrivateData) throws ConstantPoolException {
        //获得要执行的类中的main方法
        JvmMethod jvmMethod = shareData.getHeap().get(getClassFile().getName()).getMethodMap().get(new AbstractMap.SimpleEntry<>("main", "([Ljava/lang/String;)V"));
        //线程每次执行的时候都会创建栈帧(这里面保存了局部变量表/操作数栈/计数器)
        jvmMethod.invoke(shareData,new ThreadPrivateData().setJavaStack(new JavaStack().setConstantPool(getClassFile().constant_pool)));
    }
}
