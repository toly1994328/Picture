package com.toly1994.picture.world.e;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.utils.GLUtil;
import com.toly1994.picture.world.base.Cons;
import com.toly1994.picture.world.abs.RendererAble;
import com.toly1994.picture.world.base.Shape;

import java.nio.FloatBuffer;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:8:39<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：简单的形状
 */
public class SimpleShape extends RendererAble {

    private int mProgram;//OpenGL ES 程序
    private int mPositionHandle;//位置句柄
    private int mColorHandle;//颜色句柄
    private int muMVPMatrixHandle;//顶点变换矩阵句柄
    private FloatBuffer mColorBuffer;//颜色缓冲
    private final int vertexColorStride = Cons.DIMENSION_4 * 4; // 4*4=16
    private FloatBuffer mVertexBuffer;//顶点缓冲
    private final int vertexStride = Cons.DIMENSION_3 * 4; // 3*4=12
    private Shape mShape;

    public SimpleShape(Context context, Shape shape) {
        super(context);
        mShape = shape;
        mColorBuffer = GLUtil.getFloatBuffer(mShape.getColor());
        mVertexBuffer = GLUtil.getFloatBuffer(mShape.getVertex());
        initProgram();
    }

    private void initProgram() {
        //顶点着色
        int vertexShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_VERTEX_SHADER, "world.vert");
        //片元着色
        int fragmentShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_FRAGMENT_SHADER, "world.frag");
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

    @Override
    public void draw(float[] mvpMatrix) {
        // 将程序添加到OpenGL ES环境中
        GLES20.glUseProgram(mProgram);
        //启用顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //启用顶点颜色的句柄
        GLES20.glEnableVertexAttribArray(mColorHandle);
        //顶点矩阵变换
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0);
        //准备顶点坐标数据
        GLES20.glVertexAttribPointer(
                mPositionHandle,//int indx, 索引
                Cons.DIMENSION_3,//int size,大小
                GLES20.GL_FLOAT,//int type,类型
                false,//boolean normalized,//是否标准化
                vertexStride,// int stride,//跨度
                mVertexBuffer);// java.nio.Buffer ptr//缓冲
        //准备顶点颜色数据
        GLES20.glVertexAttribPointer(
                mColorHandle,
                Cons.DIMENSION_4,
                GLES20.GL_FLOAT,
                false,
                vertexColorStride,
                mColorBuffer);
        GLES20.glLineWidth(10);//设置线的宽度
        GLES20.glDrawArrays(mShape.getDrawType(), 0, mShape.getCount());
    }
}
