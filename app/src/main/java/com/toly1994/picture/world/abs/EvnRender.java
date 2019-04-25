package com.toly1994.picture.world.abs;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.utils.GLState;
import com.toly1994.picture.utils.GLUtil;
import com.toly1994.picture.utils.MatrixStack;

import java.nio.FloatBuffer;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/17/017:8:40<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public abstract class EvnRender extends RenderAble {
    private int mProgram;//OpenGL ES 程序
    private int maPositionHandle;//位置句柄
    private int muMVPMatrixHandle;//顶点变换矩阵句柄

    private int muAmbientHandle;//环境光句柄

    //顶点坐标个数
    private int vertexCount;


    private int maNormalHandle;//顶点法向量句柄
    private int maLightLocationHandle;//光源位置句柄
    private int muMMatrixHandle;//操作矩阵句柄
    private int maCameraHandle;//相机句柄
    private int muShininessHandle;//粗糙度句柄

    private FloatBuffer vertexBuffer;//顶点缓冲
    private FloatBuffer mNormalBuffer;
    private FloatBuffer mTexCoorBuffer;
    private int maTextureHandle;//纹理句柄
    private int mTId;
    private int mTirType;

    public EvnRender(Context context, int tId, int tirType) {
        super(context);
        mTId = tId;
        mTirType = tirType;
        initProgram();
    }

    protected void init(float[] vertex, float[] texture, float[] normal) {
        vertexBuffer = GLUtil.getFloatBuffer(vertex);
        mTexCoorBuffer = GLUtil.getFloatBuffer(texture);
        mNormalBuffer = GLUtil.getFloatBuffer(normal);
        vertexCount = vertex.length / 3;
    }

    /**
     * 初始化OpenGL ES 程序
     */
    private void initProgram() {
        //顶点着色
        int vertexShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_VERTEX_SHADER, "evn.vert");
        //片元着色
        int fragmentShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_FRAGMENT_SHADER, "evn.frag");

        mProgram = GLES20.glCreateProgram();//创建空的OpenGL ES 程序
        GLES20.glAttachShader(mProgram, vertexShader);//加入顶点着色器
        GLES20.glAttachShader(mProgram, fragmentShader);//加入片元着色器
        GLES20.glLinkProgram(mProgram);//创建可执行的OpenGL ES项目

        //获取句柄
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        maNormalHandle = GLES20.glGetAttribLocation(mProgram, "aNormal");
        maTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTexture");

        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        muAmbientHandle = GLES20.glGetUniformLocation(mProgram, "uAmbient");
        maLightLocationHandle = GLES20.glGetUniformLocation(mProgram, "uLightLocation");
        muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix");
        maCameraHandle = GLES20.glGetUniformLocation(mProgram, "uCamera");
        muShininessHandle = GLES20.glGetUniformLocation(mProgram, "uShininess");
    }


    @Override
    public void draw(float[] mvpMatrix) {
        // 将程序添加到OpenGL ES环境中
        GLES20.glUseProgram(mProgram);
        //启用属性句柄
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maNormalHandle);
        GLES20.glEnableVertexAttribArray(maTextureHandle);
        //绑定纹理
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTId);

        //准备三角顶点坐标数据
        GLES20.glVertexAttribPointer(
                maPositionHandle,//int indx, 索引
                3,//int size,大小
                GLES20.GL_FLOAT,//int type,类型
                false,//boolean normalized,//是否标准化
                3 * 4,// int stride,//跨度
                vertexBuffer);// java.nio.Buffer ptr//缓冲
        //将顶点法向量数据传入渲染管线
        GLES20.glVertexAttribPointer(
                maNormalHandle,
                3,
                GLES20.GL_FLOAT,
                false,
                3 * 4,
                mNormalBuffer);

        //传送顶点纹理坐标数据
        GLES20.glVertexAttribPointer(
                maTextureHandle,
                2,
                GLES20.GL_FLOAT,
                false,
                2 * 4,
                mTexCoorBuffer);
        //句柄传参
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixStack.getOpMatrix(), 0);
        GLES20.glUniform1f(muShininessHandle, 30);
        GLES20.glUniform3fv(maLightLocationHandle, 1, GLState.lightPositionFB);
        GLES20.glUniform3fv(maCameraHandle, 1, GLState.cameraFB);

        GLES20.glDrawArrays(mTirType, 0, vertexCount);
    }
}
