package com.toly1994.picture.shape;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.utils.GLUtil;
import com.toly1994.picture.world.abs.RendererAble;

import java.nio.FloatBuffer;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:20:09<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：矩形
 */
public class Circle extends RendererAble {
    private static final String TAG = "Triangle";
    private int mProgram;//OpenGL ES 程序
    private int mPositionHandle;//位置句柄
    private int mColorHandle;//颜色句柄
    private int muMVPMatrixHandle;//顶点变换矩阵句柄

    private FloatBuffer vertexBuffer;//顶点缓冲
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 3*4=12
    static final int COORDS_PER_VERTEX = 3;//数组中每个顶点的坐标数

    //顶点坐标个数
    private int verticeCount;

    private FloatBuffer mColorBuffer;//颜色缓冲
    static final int COLOR_PER_VERTEX = 4;//数组中每个顶点的坐标数
    private final int vertexColorStride = COLOR_PER_VERTEX * 4; // 4*4=16

    public Circle(Context context) {
        super(context);
        initVertex(8, 0.5f, 1.0f, 360.f);
        initProgram();//初始化OpenGL ES 程序
    }

    /**
     * 初始化顶点坐标与颜色
     *
     * @param splitCount 分割点数
     * @param r          内圆半径
     * @param R          外圈半径
     */
    public void initVertex(int splitCount, float r, float R, float deg) {
        //顶点坐标数据的初始化
        verticeCount = splitCount * 2 + 2;
        float[] vertices = new float[verticeCount * 3];//坐标数据
        float thta = deg / splitCount;
        for (int i = 0; i < vertices.length; i += 3) {
            int n = i / 3;
            if (n % 2 == 0) {//偶数点--内圈
                vertices[i] = (float) (r * Math.cos(Math.toRadians((n / 2) * thta)));//x
                vertices[i + 1] = (float) (r * Math.sin(Math.toRadians((n / 2) * thta)));//y
                vertices[i + 2] = 0;//z
            } else {//奇数点--外圈
                vertices[i] = (float) (R * Math.cos(Math.toRadians((n / 2) * thta)));//x
                vertices[i + 1] = (float) (R * Math.sin(Math.toRadians((n / 2) * thta)));//y
                vertices[i + 2] = 0;//z
            }
        }

        //顶点颜色值数组，每个顶点4个色彩值RGBA
        //橙色：0.972549f,0.5019608f,0.09411765f,1.0f
        float colors[] = new float[verticeCount * 4];
        for (int i = 0; i < colors.length; i += 8) {
            colors[i + 0] = 1;
            colors[i + 1] = 1;
            colors[i + 2] = 1;
            colors[i + 3] = 1;

            colors[i + 4] = 0.972549f;
            colors[i + 5] = 0.5019608f;
            colors[i + 6] = 0.09411765f;
            colors[i + 7] = 1.0f;
        }

        vertexBuffer = GLUtil.getFloatBuffer(vertices);
        mColorBuffer = GLUtil.getFloatBuffer(colors);
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

//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, verticeCount);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, verticeCount);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, verticeCount);
//        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, verticeCount);
//        GLES20.glDrawArrays(GLES20.GL_LINES, 0, verticeCount);
//        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, verticeCount);
//        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, verticeCount);

        //禁用顶点数组:
        //禁用index指定的通用顶点属性数组。
        // 默认情况下，禁用所有客户端功能，包括所有通用顶点属性数组。
        // 如果启用，将访问通用顶点属性数组中的值，
        // 并在调用顶点数组命令（如glDrawArrays或glDrawElements）时用于呈现
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

