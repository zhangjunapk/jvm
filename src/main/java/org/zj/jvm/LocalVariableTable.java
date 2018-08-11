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


    public static void main(String[] main){
        LocalVariableTable localVariableTable=new LocalVariableTable(5);
        localVariableTable.put("a");
        localVariableTable.put("b");
        try {
            System.out.println(localVariableTable.get(0));
            System.out.println(localVariableTable.get(1));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
