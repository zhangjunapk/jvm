import com.sun.tools.classfile.*;

import java.io.File;
import java.io.IOException;


/**
 * Created by ZhangJun on 2018/8/10.
 */
public class MethodCode {
    public static void main(String[] args) throws NoSuchMethodException, IOException, ConstantPoolException {

        ClassFile classFile=ClassFile.read(new File("E:\\java\\base\\jvm\\src\\main\\java\\Demo.class"));
        for(Method method:classFile.methods){

            Code_attribute codeAttribute = (Code_attribute)method.attributes.get("Code");

            System.out.println("方法名  "+method.getName(classFile.constant_pool));
            System.out.println("返回值类型  "+method.descriptor.getValue(classFile.constant_pool));
            Code_attribute code = (Code_attribute) method.attributes.get("Code");
            System.out.println("--方法里面的字节码数据");
            for(Byte b:code.code){
                short c = (short)(0xff&b);
                System.out.println(c);
            }
        }
    }
}
