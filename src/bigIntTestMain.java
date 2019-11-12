import java.io.*;
import java.lang.*;

public class bigIntTestMain
{
    public static void main(String[] args) {
        String a = "551344";
        String b = "1391";
        String c;

        myBigInteger bigInt1 = new myBigInteger(a);
        myBigInteger bigInt2 = new myBigInteger(b);

        c = bigInt1.Minus(bigInt2);

        System.out.println(bigInt1.ToString());
        System.out.println(bigInt2.ToString());
        System.out.println(c);


    }
}
