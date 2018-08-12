package org.zj.jvm;



/**
 * Created by ZhangJun on 2018/8/11.
 */
public class LocalVariableTable {
    Object[] values;
    int index = 0;

    public LocalVariableTable(int len) {
        values = new Object[len];
    }

    public Object get(int index) throws NoSuchMethodException {
        if (index >= 0 && index < values.length) {
            return values[index];
        }
        throw new NoSuchMethodException();
    }

    public Object put(Object obj) {
        values[index++] = obj;
        return obj;
    }
    //将指定对象放到指定位置
    public Object put(Object obj,int index){
        System.out.println("接受过来的数据"+obj);
        values[index]=obj;
        System.out.println(values[index]+"   我放到局部变量表中了   "+index);
        return obj;
    }

    public static void main(String[] main){
        LocalVariableTable localVariableTable=new LocalVariableTable(5);
        localVariableTable.put("a",2);
        try {
            System.out.println(localVariableTable.get(2));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
