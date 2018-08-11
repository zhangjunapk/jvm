import java.lang.reflect.Method;

/**
 * Created by ZhangJun on 2018/8/8.
 */
public class Demo {

    static{
        System.out.println("love you");
    }
    public static void main(String[] args) {

        int a=5;
        int b=9;
        int c=10;
        int d=66;
        a=a+b;
        b=b+c;
        a=a+b+d;
    }
}
