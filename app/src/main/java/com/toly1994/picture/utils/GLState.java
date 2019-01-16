package com.toly1994.picture.utils;

import java.nio.FloatBuffer;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/15/015:15:33<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class GLState {
    ////////----------设置光源
    private static float[] lightLocation = new float[]{0, 0, 0};//定位光光源位置
    public static FloatBuffer lightPositionFB;

    //设置灯光位置的方法
    public static void setLightLocation(float x, float y, float z) {
        lightLocation[0] = x;
        lightLocation[1] = y;
        lightLocation[2] = z;
        lightPositionFB = GLUtil.getFloatBuffer(lightLocation);
    }

    ////////----------设置相机位置
    static float[] cameraLocation = new float[3];//摄像机位置
    public static FloatBuffer cameraFB;

    //设置灯光位置的方法
    public static void setCameraLocation(float x, float y, float z) {
        cameraLocation[0] = x;
        cameraLocation[1] = y;
        cameraLocation[2] = z;
        cameraFB = GLUtil.getFloatBuffer(cameraLocation);
    }

    ////////----------环境光
    static float[] eviLight = new float[4];//摄像机位置
    public static FloatBuffer eviLightFB;

    //设置灯光位置的方法
    public static void setEviLight(float r, float g, float b,float a) {
        eviLight[0] = r;
        eviLight[1] = g;
        eviLight[2] = b;
        eviLight[3] = a;
        eviLightFB = GLUtil.getFloatBuffer(eviLight);
    }
}
