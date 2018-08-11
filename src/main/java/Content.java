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
        //这里获得的是全局变量字段
        Field[] fields = read.fields;
        for(Field f:fields){
            //如果是静态变量
            if(f.access_flags.is(AccessFlags.ACC_STATIC))
            System.out.println(f.getName(read.constant_pool));
            System.out.println(f.descriptor);

        }
    }

}
