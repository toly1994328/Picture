package com.toly1994.picture.utils;

import android.util.Log;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/11 0011:9:17<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class L {

    public static void logM(float[] matrix) {
        logM(matrix, "Matrix");
    }

    /**
     * 打印方阵数组
     *
     * @param matrix
     * @param name
     */
    public static void logM(float[] matrix, String name) {
        int wei = (int) Math.sqrt(matrix.length);
        StringBuffer sb = new StringBuffer("\n[");
        for (int i = 0; i < matrix.length; i++) {
            sb.append(matrix[i]);
            if ((i + 1) % wei == 0) {
                if (i == matrix.length - 1) {
                    sb.append("]");
                    continue;
                }
                sb.append("\n");
                continue;
            }
            sb.append(" , ");
        }
        Log.e("Matrix_TAG", name + ": " + sb.toString());
    }
}
