package org.zj.jvm;

import com.sun.tools.classfile.Code_attribute;
import com.sun.tools.classfile.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangJun on 2018/8/10.
 */
//jvm方法
public class JvmMethod {
    //方法的指令(iconst/istore/iadd/ldc)集合
    List<Opcode> opcodes=new ArrayList<>();
    Method method;
    public List<Opcode> getOpcodes() {
        return opcodes;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setOpcodes(List<Opcode> opcodes) {
        this.opcodes = opcodes;
    }

    //方法的执行
    public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData){

        Code_attribute codeAttribute = (Code_attribute)method.attributes.get("Code");

        //初始化局部变量表
        threadPrivateData.setJavaStack(new JavaStack());
        threadPrivateData.getJavaStack().setLocalVariometer(new LocalVariableTable(codeAttribute.max_locals));
        //初始化操作数栈
        threadPrivateData.getJavaStack().setOperandStack(new OperandStack(codeAttribute.max_stack));

        //初始化常量池


        System.out.println("遍历每个指令并执行");

        for(Opcode opcode:opcodes){
            opcode.invoke(shareData, threadPrivateData);
        }

    }

}
