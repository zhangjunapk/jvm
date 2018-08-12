package org.zj.jvm;

import com.sun.tools.classfile.ClassFile;
import com.sun.tools.classfile.Code_attribute;
import com.sun.tools.classfile.ConstantPoolException;
import com.sun.tools.classfile.Method;

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
        initShareData(classPath);
    }

    /**
     * 递归放到方法区
     * @param shareData
     * @param file
     */
    private void loadClass(ShareData shareData, File file) {
        if(file!=null&&file.isFile()&&file.getName().endsWith(".class")){
            try{
                //将类信息放到方法区
                initClassAndInflate(shareData.getMethodArea(),file);
            }catch (Exception e){
            }
        }
        if(file.isDirectory()){
            for(File f:file.listFiles()){
                loadClass(shareData,f);
            }
        }

    }

    /**
     * 执行指定类的main方法
     * @param className
     * @throws IOException
     * @throws ConstantPoolException
     */
    public void run(String className) throws IOException, ConstantPoolException {
        //从共享区域获得jvmClass
        JvmClass jvmClass = shareData.getMethodArea().get(className);
        //执行main方法
        jvmClass.run(shareData,new ThreadPrivateData());
    }

    /**
     * 对类进行初始化并放到方法区
     * @param methodArea
     * @param file
     * @throws ConstantPoolException
     */
    private void initClassAndInflate(Map<String,JvmClass> methodArea, File file) throws ConstantPoolException {
        ClassFile classFile;
        try{
            classFile=ClassFile.read(file);
        }catch (Exception e){
            return;
        }

        JvmClass jvmClass=new JvmClass();
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

        //应该先执行static 方法

        JvmMethod staticMethod= methodArea.get(classFile.getName()).getMethodMap().get(new AbstractMap.SimpleEntry<>("<static>", "()V"));

        staticMethod.invoke(shareData,new ThreadPrivateData().setJavaStack(new JavaStack().setConstantPool(classFile.constant_pool)));

        //执行jvmclass的空参构造
        JvmMethod initMethod= methodArea.get(classFile.getName()).getMethodMap().get(new AbstractMap.SimpleEntry<>("<clinit>", "()V"));
        //把常量池弄到线程私有的数据区域
        initMethod.invoke(shareData,new ThreadPrivateData().setJavaStack(new JavaStack().setConstantPool(classFile.constant_pool)));

    }

    /**
     * 初始化共享区域
     * @param classPath
     */
    public void initShareData(String classPath){
        // 将类信息放到方法区

        //最开始就初始化共享区域
        File file=new File(classPath);

        for(File f:file.listFiles()){
            //将每个类解析出来放到方法区
            loadClass(shareData,f);
        }
        //初始化堆区

    }

}
