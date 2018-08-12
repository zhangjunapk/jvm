package org.zj.jvm;

import com.sun.tools.classfile.ClassFile;
import com.sun.tools.classfile.ConstantPool;
import com.sun.tools.classfile.ConstantPoolException;

import java.util.*;

/**
 * Created by ZhangJun on 2018/8/10.
 */
//字节码class
public class JvmClass {

    private ClassFile classFile;


    public ClassFile getClassFile() {
        return classFile;
    }

    public JvmClass setClassFile(ClassFile classFile) {
        this.classFile = classFile;
        return this;
    }


}
