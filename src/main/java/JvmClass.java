import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangJun on 2018/8/8.
 */
//字节码方式的类
public class JvmClass {
    Class clazz;
    //字段集合
    List<Field> fields=new ArrayList<Field>();
    //字段里面的所有方法
    List<Method> methods=new ArrayList<Method>();

    public JvmClass() {
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public JvmClass(Class clazz, List<Field> fields, List<Method> methods) {
        this.clazz = clazz;
        this.fields = fields;
        this.methods = methods;
    }
}
