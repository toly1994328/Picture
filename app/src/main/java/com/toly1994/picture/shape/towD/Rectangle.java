package com.toly1994.picture.shape.towD;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.utils.GLUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:20:09<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：矩形
 */
public class Rectangle {
    private static final String TAG = "Triangle";
    private Context mContext;

    private int mProgram;//OpenGL ES 程序
    private int mPositionHandle;//位置句柄
    private int mColorHandle;//颜色句柄
    private int muMVPMatrixHandle;//顶点变换矩阵句柄

    private FloatBuffer vertexBuffer;//顶点缓冲
    private final int vertexCount = sCoo.length / COORDS_PER_VERTEX;//顶点个数
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 3*4=12
    static final int COORDS_PER_VERTEX = 3;//数组中每个顶点的坐标数

    static float sCoo[] = {   //以逆时针顺序
            -0.5f, 0.5f, 0.0f, // p0
            -1.0f, 0.0f, 0.0f, // p1
            -0.5f, -0.5f, 0.0f, // p2
            0.5f, -0.5f, 0.0f, //p3
//            1.0f, 0.0f, 0.0f, //p4
//            0.5f, 0.5f, 0.0f, //p5
    };


    private FloatBuffer mColorBuffer;//颜色缓冲
    static final int COLOR_PER_VERTEX = 4;//数组中每个顶点的坐标数
    private final int vertexColorStride = COLOR_PER_VERTEX * 4; // 4*4=16

    float colors[] = new float[]{
            1f, 1f, 0.0f, 1.0f,//黄--3
            0.05882353f, 0.09411765f, 0.9372549f, 1.0f,//蓝--4
            0.19607843f, 1.0f, 0.02745098f, 1.0f,//绿--1
            1.0f, 0.0f, 1.0f, 1.0f,//紫色--2
//            1.0f, 1.0f, 0.0f, 1.0f,//黄色--2
//            0.5254902f,0.05490196f,0.91764706f,1.0f//黄色--2
    };
    // 颜色，rgba  0.5176471,0.77254903,0.9411765,1.0
    float color[] = {0.5176471f, 0.77254903f, 0.9411765f, 1.0f};

    private ShortBuffer idxBuffer;
    //索引数组
    private short[] idx = {
            0, 1, 5,
            1, 5, 2,
            2, 5, 4,
            2, 3, 4
    };

    public Rectangle(Context context) {
        mContext = context;
        //初始化顶点字节缓冲区
        bufferData();//缓冲顶点数据
        initProgram();//初始化OpenGL ES 程序
    }

    /**
     * 缓冲数据
     */
    private void bufferData() {
        vertexBuffer = GLUtil.getFloatBuffer(sCoo);
        mColorBuffer = GLUtil.getFloatBuffer(colors);
        idxBuffer = GLUtil.getShortBuffer(idx);
    }


    /**
     * 初始化OpenGL ES 程序
     */
    private void initProgram() {
        //顶点着色
        int vertexShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_VERTEX_SHADER, "tri.vert");
        //片元着色
        int fragmentShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_FRAGMENT_SHADER, "tri.frag");

        mProgram = GLES20.glCreateProgram();//创建空的OpenGL ES 程序
        GLES20.glAttachShader(mProgram, vertexShader);//加入顶点着色器
        GLES20.glAttachShader(mProgram, fragmentShader);//加入片元着色器
        GLES20.glLinkProgram(mProgram);//创建可执行的OpenGL ES项目

        //获取顶点着色器的vPosition成员的句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        //获取程序中总变换矩阵uMVPMatrix成员的句柄
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }


    public void draw(float[] mvpMatrix) {
        // 将程序添加到OpenGL ES环境中
        GLES20.glUseProgram(mProgram);
        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //启用三角形顶点颜色的句柄
        GLES20.glEnableVertexAttribArray(mColorHandle);

        //顶点矩阵变换
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0);

        //准备三角顶点坐标数据
        GLES20.glVertexAttribPointer(
                mPositionHandle,//int indx, 索引
                COORDS_PER_VERTEX,//int size,大小
                GLES20.GL_FLOAT,//int type,类型
                false,//boolean normalized,//是否标准化
                vertexStride,// int stride,//跨度
                vertexBuffer);// java.nio.Buffer ptr//缓冲

        //准备三角顶点颜色数据
        GLES20.glVertexAttribPointer(
                mColorHandle,
                COLOR_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexColorStride,
                mColorBuffer);

//        //为三角形设置颜色
//        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        GLES20.glLineWidth(5);

        //绘制三角形
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, idx.length, GLES20.GL_UNSIGNED_SHORT, idxBuffer);


        //禁用顶点数组:
        //禁用index指定的通用顶点属性数组。
        // 默认情况下，禁用所有客户端功能，包括所有通用顶点属性数组。
        // 如果启用，将访问通用顶点属性数组中的值，
        // 并在调用顶点数组命令（如glDrawArrays或glDrawElements）时用于呈现
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

