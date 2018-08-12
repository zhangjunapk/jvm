import com.sun.tools.classfile.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by ZhangJun on 2018/7/28.
 */
public class Content {
    public static void main(String[] args) throws IOException, ConstantPoolException {


        Class c;
        //通过这样获得类里面的字段
        ClassFile read = ClassFile.read(new File("E:\\java\\base\\jvm\\src\\main\\java\\Demo.class"));

        System.out.println("----常量池中的数据");

        ConstantPool.CPInfo info= read.constant_pool.get(6);

        System.out.println(((ConstantPool.CONSTANT_String_info) info).getString());

        //这里获得的是全局变量字段
        Field[] fields = read.fields;
        for(Field f:fields){
            //如果是静态变量
            System.out.println(f.getName(read.constant_pool));
            System.out.println(f.descriptor);

        }



       /* for(int i=0;i<read.constant_pool.size();i++){
            ConstantPool.CPInfo cpInfo = read.constant_pool.get(i);
            System.out.println(((ConstantPool.CONSTANT_Integer_info) cpInfo).getTag());
            System.out.println("   "+((ConstantPool.CONSTANT_Integer_info) cpInfo).value);
        }*/
    }

}
