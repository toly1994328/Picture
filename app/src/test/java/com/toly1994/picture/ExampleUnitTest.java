package com.toly1994.picture;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        rgba2float(50,255,7, 255);
    }

    @Test
    public void butifSre() {
        String src = "* Multiplies two 4x4 matrices together and stores the result in a third 4x4\n" +
                " * matrix. In matrix notation: result = lhs x rhs. Due to the way\n" +
                " * matrix multiplication works, the result matrix will have the same\n" +
                " * effect as first multiplying by the rhs matrix, then multiplying by\n" +
                " * the lhs matrix. This is the opposite of what you might expect.";

        src = src.replaceAll("\\*", "");
        src = src.replaceAll("\n", "");
        System.out.println(src);

    }


    private void rgba2float(int r, int g, int b, int a) {
        System.out.println(r / 255.f + "f," + g / 255.f + "f," + b / 255.f + "f," + a / 255.f+"f");
    }
}