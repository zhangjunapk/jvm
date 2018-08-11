package org.zj.jvm;

import com.sun.tools.classfile.ClassFile;
import com.sun.tools.classfile.ConstantPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangJun on 2018/8/10.
 */
//字节码class
public class JvmClass {

    private ConstantPool constantPool;

    private ClassFile classFile;

    //jvmclass中的方法集合
    //key 是一个entry k 是方法名,v 是返回值
    Map<Map.Entry<String,String>,JvmMethod> methodMap=new HashMap<>();

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public ClassFile getClassFile() {
        return classFile;
    }

    public void setClassFile(ClassFile classFile) {
        this.classFile = classFile;
    }

    public Map<Map.Entry<String, String>, JvmMethod> getMethodMap() {
        return methodMap;
    }

    public void setMethodMap(Map<Map.Entry<String, String>, JvmMethod> methodMap) {
        this.methodMap = methodMap;
    }
}
