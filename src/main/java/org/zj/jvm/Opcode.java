package org.zj.jvm;

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.xpath.internal.compiler.OpMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangJun on 2018/8/11.
 */
//要执行的指令
public enum Opcode {

    //将0推入操作数栈
    ICONST_0(Constants.ICONST_0){
        @Override
        public void invoke(ShareData shareData, ThreanPrivateData threanPrivateData) {

        }
    };

    short code;
    static Map<Short,Opcode> opcodeMap=new HashMap<>();
    Opcode(short code){
        this.code=code;
    }


    //我需要共享区域,还有常量池/局部变量表/操作数栈/计数器
    public abstract void invoke(ShareData shareData, ThreanPrivateData threanPrivateData);


    static{
        for(Opcode opcode:values()){
           opcodeMap.put(opcode.code,opcode);
        }
    }
}
