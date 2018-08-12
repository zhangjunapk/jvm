package org.zj.jvm;

import com.sun.tools.classfile.ClassFile;
import com.sun.tools.classfile.Code_attribute;
import com.sun.tools.classfile.ConstantPoolException;
import com.sun.tools.classfile.Method;
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
    public void run(String className) throws IOException, ConstantPoolException, ClassNotFoundException {

        //先从堆中获得，如果没有就初始化类
        JvmInitedClass jvmInitedClass = shareData.getHeap().get(className);

        if(jvmInitedClass==null){
            initClassAndInflate(className);
        }

        //执行main方法
        jvmInitedClass.run(shareData,new ThreadPrivateData());
    }

    /**
     * 初始化类并放到堆中
     * @param className
     * @throws ConstantPoolException
     */
    private void initClassAndInflate(String className) throws ConstantPoolException, ClassNotFoundException {
        ClassFile classFile=shareData.getMethodArea().get(className).getClassFile();
        if(classFile==null){
            throw new ClassNotFoundException();
        }

        JvmInitedClass jvmClass=new JvmInitedClass();

        jvmClass.setClassFile(classFile);
        jvmClass.setConstantPool(classFile.constant_pool);
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
            for(Byte b:code.code){
                short c = (short)(0xff&b);

                if(Opcode.opcodeMap.get(c)!=null)
                    opcodes.add(Opcode.opcodeMap.get(c));

                System.out.println(c);
            }
            //为方法设置指令集合
            jvmMethod.setOpcodes(opcodes);
            jvmMethod.setMethod(method);
            methodMap.put(new AbstractMap.SimpleEntry<>(name,value),jvmMethod);
        }
        jvmClass.setMethodMap(methodMap);

        //存放到堆区
        shareData.getHeap().put(classFile.getName(),jvmClass);

        //应该先执行static 方法

        JvmMethod staticMethod= shareData.getHeap().get(classFile.getName()).getMethodMap().get(new AbstractMap.SimpleEntry<>("<static>", "()V"));

        staticMethod.invoke(shareData,new ThreadPrivateData().setJavaStack(new JavaStack().setConstantPool(classFile.constant_pool)));

        //执行jvmclass的空参构造
        JvmMethod initMethod= shareData.getHeap().get(classFile.getName()).getMethodMap().get(new AbstractMap.SimpleEntry<>("<clinit>", "()V"));
        //把常量池弄到线程私有的数据区域
        initMethod.invoke(shareData,new ThreadPrivateData().setJavaStack(new JavaStack().setConstantPool(classFile.constant_pool)));

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
