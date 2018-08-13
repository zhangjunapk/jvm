package org.zj.jvm;

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.tools.classfile.ConstantPool;
import com.sun.tools.classfile.ConstantPoolException;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangJun on 2018/8/11.
 */
//要执行的指令
public enum Opcode {

    //调用方法的指令
    INVOKEVIRTUAL(Constants.INVOKEVIRTUAL){
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) throws Exception, ConstantPool.InvalidIndex {

            int arg = (currentInstruction[0]<<8)|currentInstruction[1];
            System.out.println("参数在常量池中的索引   "+arg);
            System.out.println("--调用方法");
            for(byte b:currentInstruction){
                System.out.println(b);
            }
            //System.out.println(threadPrivateData.getJavaStack().getConstantPool().get(0));
            System.out.println(threadPrivateData.getJavaStack().getConstantPool().get(currentInstruction[1]));

            //获得方法
            JvmMethod method = getMethod(shareData, threadPrivateData.getJavaStack().getConstantPool(), currentInstruction[1]);

            //我需要获得这个方法的参数个数
            int parameterCount = method.getMethod().descriptor.getParameterCount(threadPrivateData.getJavaStack().getConstantPool());
            //然后从操作数栈中直接出栈指定数量，作为参数放到方法的局部变量表中
            Object[] param=threadPrivateData.getJavaStack().getOperandStack().muiltePop(parameterCount);
            //然后再invoke
            method.invoke(shareData,new ThreadPrivateData().setJavaStack(new JavaStack().setConstantPool(getJvmInitClass(shareData,threadPrivateData.getJavaStack().getConstantPool(),currentInstruction[1]).getConstantPool())),param);
        }
    },
    //从常量池获得数据
    LDC(Constants.LDC) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) throws ConstantPoolException {
            System.out.println(threadPrivateData.getJavaStack().getConstantPool()+"   常量池");
            Object val = getVal(threadPrivateData.getJavaStack().getConstantPool(), currentInstruction[0]);
            System.out.println("从常量池中获得的数据" + val);
        }
    },
    //将5压入栈
    ICONOST_5(Constants.ICONST_5) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) {
            System.out.println("将int 5压入栈");
            threadPrivateData.getJavaStack().getOperandStack().push(5);
            System.out.println(" 放到操作数栈的数据");
            System.out.println(threadPrivateData.getJavaStack().getOperandStack().pick());
            System.out.println("-------");
        }
    },

    //将局部变量表中索引为1的int推到操作数栈顶
    ILOAD_1(Constants.ILOAD_1) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) {
            try {
                System.out.println("将局部变量表中索引为1的的int推到操作数栈顶");

                System.out.println(" 局部变量表中的数据        " + threadPrivateData.getJavaStack().getLocalVariometer().get(1));

                threadPrivateData.getJavaStack().getOperandStack().push(threadPrivateData.getJavaStack().getLocalVariometer().get(1));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    },

    //将操作数栈顶的int放到局部变量表
    ISTORE_1(Constants.ISTORE_1) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) {
            //这里操作数栈应该是弹出
            System.out.println("将操作数栈顶的int放到局部变量表1的位置");
            threadPrivateData.getJavaStack().getLocalVariometer().put(threadPrivateData.getJavaStack().getOperandStack().pop(), 1);
        }
    },
    //将操作数栈顶的int放到局部变量表
    ISTORE_2(Constants.ISTORE_2) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) {
            //这里操作数栈应该是弹出
            System.out.println("将操作数栈顶的int放到局部变量表2的位置");
            threadPrivateData.getJavaStack().getLocalVariometer().put(threadPrivateData.getJavaStack().getOperandStack().pop(), 2);
        }
    },
    //将操作数栈顶的int放到局部变量表3号位
    ISTORE_3(Constants.ISTORE_3) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) {
            //这里操作数栈应该是弹出
            System.out.println("将操作数栈顶的int放到局部变量表2的位置");
            threadPrivateData.getJavaStack().getLocalVariometer().put(threadPrivateData.getJavaStack().getOperandStack().pop(), 3);
        }
    },
    //将操作数栈顶的两个数字相加并将结果推到栈顶
    IADD(Constants.IADD) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) {
            System.out.println("执行iadd");
            int a = (int) threadPrivateData.getJavaStack().getOperandStack().pop();
            System.out.println(a + " 第一个数字");
            int b = (int) threadPrivateData.getJavaStack().getOperandStack().pop();
            threadPrivateData.getJavaStack().getOperandStack().push(a + b);
        }
    },
    //把局部变量表中索引为2的int推到栈顶
    ILOAD_2(Constants.ILOAD_2) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) throws NoSuchMethodException {
            System.out.println("把局部变量表中索引为2的int推到栈顶");
            System.out.println("局部变量表中索引为2的元素   " + threadPrivateData.getJavaStack().getLocalVariometer().get(2));
            //--------------------------找了半天bug---------------粗心了
            threadPrivateData.getJavaStack().getOperandStack().push(threadPrivateData.getJavaStack().getLocalVariometer().get(2));
        }
    },
    //把局部变量表中索引为3的int推到栈顶
    ILOAD_3(Constants.ILOAD_3) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) throws NoSuchMethodException {
            System.out.println("把局部变量表中索引为2的int推到栈顶");
            threadPrivateData.getJavaStack().getLocalVariometer().put(threadPrivateData.getJavaStack().getLocalVariometer().get(3));
        }
    },
    //将8位数字压入栈
    BIPUSH(Constants.BIPUSH) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) {
            System.out.println("将8位数字压入栈顶");
            byte b = currentInstruction[0];
        }
    },
    //将0推入操作数栈
    ICONST_0(Constants.ICONST_0) {
        @Override
        public void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) {
            System.out.println("将0压入栈顶");
            threadPrivateData.getJavaStack().getOperandStack().push(0);
        }
    };
    //存储当前指令,比如ldc 4 从常量池获得索引为4的值
    byte[] currentInstruction;
    short code;
    static Map<Short, Opcode> opcodeMap = new HashMap<>();

    Opcode(short code) {
        this.code = code;
    }

    public byte[] getCurrentInstruction() {
        return currentInstruction;
    }

    public Opcode setCurrentInstruction(byte[] currentInstruction) {
        this.currentInstruction = currentInstruction;
        return this;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public static Map<Short, Opcode> getOpcodeMap() {
        return opcodeMap;
    }

    public static void setOpcodeMap(Map<Short, Opcode> opcodeMap) {
        Opcode.opcodeMap = opcodeMap;
    }

    //我需要共享区域,还有常量池/局部变量表/操作数栈/计数器
    public abstract void invoke(ShareData shareData, ThreadPrivateData threadPrivateData, byte[] currentInstruction) throws Exception, ConstantPool.InvalidIndex;


    //获得常量池中的值 可以通过这个返回一个方法
    Object getVal(ConstantPool constantPool, short index) throws ConstantPoolException {
        ConstantPool.CPInfo cpInfo = constantPool.get(index);
        int tag = cpInfo.getTag();
        System.out.println("标志" + tag);
        switch (tag) {
            case ConstantPool.CONSTANT_Integer:
                return ((ConstantPool.CONSTANT_Integer_info) cpInfo).value;
            case ConstantPool.CONSTANT_String:
                return ((ConstantPool.CONSTANT_String_info) cpInfo).getString();
            case ConstantPool.CONSTANT_Double:
                return ((ConstantPool.CONSTANT_Double_info) cpInfo).value;
            case ConstantPool.CONSTANT_Float:
                return ((ConstantPool.CONSTANT_Float_info) cpInfo).value;
        }
       return null;
    }
    JvmMethod getMethod(ShareData shareData,ConstantPool constantPool, short index) throws ConstantPoolException {
        ConstantPool.CPInfo cpInfo = constantPool.get(index);
        int tag = cpInfo.getTag();
        System.out.println("标志" + tag);
        if(tag== ConstantPool.CONSTANT_Methodref){

            //首先需要获得类名
            String className = ((ConstantPool.CONSTANT_Methodref_info) cpInfo).getClassName();
            //然后获得方法名和返回值类型
            int inde=((ConstantPool.CONSTANT_Methodref_info) cpInfo).name_and_type_index;
            String methodName = ((ConstantPool.CONSTANT_NameAndType_info) constantPool.get(inde)).getName();
            String returnType=((ConstantPool.CONSTANT_NameAndType_info) constantPool.get(inde)).getType();
            System.out.println("类名:"+className);
            System.out.println("方法名:"+methodName);
            System.out.println("方法返回值类型:"+returnType);
            return shareData.getHeap().get(className).getMethodMap().get(new AbstractMap.SimpleEntry<>(methodName,returnType));
        }
        return null;
    }
    //从堆中获得类
    JvmInitedClass getJvmInitClass(ShareData shareData,ConstantPool constantPool, short index) throws ConstantPoolException {
        ConstantPool.CPInfo cpInfo = constantPool.get(index);
        int tag = cpInfo.getTag();
        System.out.println("标志" + tag);
        if(tag== ConstantPool.CONSTANT_Methodref){
            //首先需要获得类名
            String className = ((ConstantPool.CONSTANT_Methodref_info) cpInfo).getClassName();
            //然后获得方法名和返回值类型
            int inde=((ConstantPool.CONSTANT_Methodref_info) cpInfo).name_and_type_index;
            String methodName = ((ConstantPool.CONSTANT_NameAndType_info) constantPool.get(inde)).getName();
            String returnType=((ConstantPool.CONSTANT_NameAndType_info) constantPool.get(inde)).getType();
            System.out.println("类名:"+className);
            System.out.println("方法名:"+methodName);
            System.out.println("方法返回值类型:"+returnType);
            JvmInitedClass jvmInitedClass = shareData.getHeap().get(className);
            //如果在堆中没找到，说明这个类没有初始化
            if(jvmInitedClass==null)
                throw new NullPointerException();
            return jvmInitedClass;
        }
        return null;
    }
    static {
        for (Opcode opcode : values()) {
            opcodeMap.put(opcode.code, opcode);
        }
    }

}
