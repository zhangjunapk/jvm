package org.zj.jvm;

import com.sun.tools.classfile.ConstantPoolException;

import java.io.IOException;

/**
 * Created by ZhangJun on 2018/8/10.
 */
public class Content {
    public static void main(String[] args) throws Exception {
        new VirtualMachine("E:\\rt","E:\\java\\base\\jvm\\src\\main\\java\\").run("Demo");
    }
}
