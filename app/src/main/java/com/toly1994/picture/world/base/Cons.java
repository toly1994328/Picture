package com.toly1994.picture.world.base;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:14:52<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：常量类
 */
public class Cons {
    public static final int DIMENSION_2 = 2;//2维度
    public static final int DIMENSION_3 = 3;//3维度
    public static final int DIMENSION_4 = 4;//4维度

    public static final float[] VERTEX_COO = {//坐标轴
            0.0f, 0.0f, 0.0f,//Z轴
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 0.0f,//X轴
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,//Y轴
            0.0f, 1.0f, 0.0f,
    };

    public static final float[] COLOR_COO = {//坐标轴颜色
            0.0f, 0.0f, 1.0f, 1.0f,//Z轴:蓝色
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,//X轴：黄色
            1.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,//Y轴：绿色
            0.0f, 1.0f, 0.0f, 1.0f,
    };
    public static final float UNIT_SIZE = 1.0f;
}
