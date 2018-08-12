package org.zj.jvm;

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.tools.classfile.*;
import sun.tools.java.ClassNotFound;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by ZhangJun on 2018/8/10.
 */
//虚拟机对象
public class VirtualMachine {

    //初始化共享区域 方法区/堆
    ShareData shareData=new ShareData();

    /**
     * 初始化共享区域 方法区/堆
     * @param classPath
     */
    public VirtualMachine(String classPath){
        //初始化共享区域的方法区和堆区
        initShareData(classPath);
    }

    /**
     * 递归放到方法区
     * @param file
     */
    private void loadClass(File file) {
        if(file!=null&&file.isFile()&&file.getName().endsWith(".class")){
            try{
                //将类信息放到方法区
                ClassFile classFile=ClassFile.read(file);
                shareData.getMethodArea().put(classFile.getName(),new JvmClass().setClassFile(classFile));

            }catch (Exception e){
            }
        }
        if(file.isDirectory()){
            for(File f:file.listFiles()){
                loadClass(f);
            }
        }

    }

    /**
     * 执行指定类的main方法
     * @param className
     * @throws IOException
     * @throws ConstantPoolException
     */
    public void run(String className) throws Exception {

        //先从堆中获得，如果没有就初始化类
        JvmInitedClass jvmInitedClass = shareData.getHeap().get(className);

        if(jvmInitedClass==null){

            System.out.println("堆中没有，需要初始化");

            jvmInitedClass=initClassAndInflate(className);
        }

        //执行main方法
        jvmInitedClass.run(shareData);
    }

    /**
     * 初始化类并放到堆中
     * @param className
     * @throws ConstantPoolException
     */
    private JvmInitedClass initClassAndInflate(String className) throws Exception {
        ClassFile classFile=shareData.getMethodArea().get(className).getClassFile();
        if(classFile==null){
            throw new ClassNotFoundException();
        }

        JvmInitedClass jvmInitedClass=new JvmInitedClass();

        jvmInitedClass.setClassFile(classFile);
        jvmInitedClass.setConstantPool(classFile.constant_pool);
        Map<Map.Entry<String,String>,JvmMethod> methodMap=new HashMap<>();
        //处理所有方法
        for(Method method:classFile.methods){

            Code_attribute codeAttribute = (Code_attribute)method.attributes.get("Code");

            String name = method.getName(classFile.constant_pool);
            System.out.println("方法名  "+name);
            String value = method.descriptor.getValue(classFile.constant_pool);
            System.out.println("返回值类型  "+value);
            Code_attribute code = (Code_attribute) method.attributes.get("Code");
            System.out.println("--方法里面的字节码数据");


            JvmMethod jvmMethod=new JvmMethod();
            List<Opcode> opcodes=new ArrayList<>();
            for(int i=0;i<code.code.length;i++){
                short c = (short)(0xff&code.code[i]);
                Opcode opcode = Opcode.opcodeMap.get(c);
                //数据在常量池中索引的数组 0 号元素表示在常量池中的索引
                byte[] operands = Arrays.copyOfRange(code.code, i + 1, i + 1 + Constants.NO_OF_OPERANDS[c]);

                // TODO: 2018/8/12 这里还要获得每个指令的数组
                //为每个用类表示的指令设置指令数组 比如 ldc 4 这就表示一个数组 0号元素是ldc 1号元素是4
                if(opcode!=null)
                    opcodes.add(opcode.setCurrentInstruction(operands));

                System.out.println(c);
            }
            //为方法设置指令集合
            jvmMethod.setOpcodes(opcodes);
            jvmMethod.setMethod(method);
            methodMap.put(new AbstractMap.SimpleEntry<>(name,value),jvmMethod);
        }
        jvmInitedClass.setMethodMap(methodMap);

        //存放到堆区
        shareData.getHeap().put(classFile.getName(),jvmInitedClass);

        //应该先执行static 方法

        JvmMethod staticMethod= shareData.getHeap().get(classFile.getName()).getMethodMap().get(new AbstractMap.SimpleEntry<>("<init>", "()V"));


        staticMethod.invoke(shareData,new ThreadPrivateData().setJavaStack(new JavaStack().setConstantPool(classFile.constant_pool)));

        //执行jvmclass的空参构造
        JvmMethod initMethod= shareData.getHeap().get(classFile.getName()).getMethodMap().get(new AbstractMap.SimpleEntry<>("<clinit>", "()V"));
        //把常量池弄到线程私有的数据区域
        initMethod.invoke(shareData,new ThreadPrivateData().setJavaStack(new JavaStack().setConstantPool(classFile.constant_pool)));
        return jvmInitedClass;
    }

    /**
     * 初始化共享区域
     * @param classPath
     */
    public void initShareData(String classPath){

        shareData.setMethodArea(new HashMap<String, JvmClass>());

        // 将类信息放到方法区

        //最开始就初始化共享区域
        File file=new File(classPath);
        loadClass(file);
        //初始化堆区
        shareData.setHeap(new HashMap<String, JvmInitedClass>());
    }

}
