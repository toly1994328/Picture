package com.toly1994.picture.shape.towD;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.utils.GLUtil;
import com.toly1994.picture.world.abs.RenderAble;

import java.nio.FloatBuffer;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:20:09<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：矩形--FAN 绘制 顶点最少
 */
public class Ring extends RenderAble {
    private static final String TAG = "Triangle";
    private int mProgram;//OpenGL ES 程序
    private int maPositionHandle;//位置句柄
    private int maCoordinateHandle;//颜色句柄
    private int muMVPMatrixHandle;//顶点变换矩阵句柄

    private FloatBuffer vertexBuffer;//顶点缓冲


    private int verticeCount;//顶点坐标个数
    private FloatBuffer mTextureBuffer;//颜色缓冲
    private int mTId;


    public Ring(Context context, float r, int splitCount, int tId) {
        super(context);
        mTId = tId;
        initVertex(splitCount, r);
        //初始化顶点字节缓冲区
        initProgram();//初始化OpenGL ES 程序
    }

    /**
     * 初始化顶点坐标与贴图坐标
     *
     * @param splitCount 分割点数
     * @param r          内圆半径
     */
    public void initVertex(int splitCount, float r) {
        verticeCount = splitCount + 2;
        float[] vertices = new float[verticeCount * 3];//坐标数据
        float[] textures = new float[verticeCount * 2];//坐标数据
        float dθ = 360.f / splitCount;

        vertices[0] = 0;
        vertices[1] = 0;
        vertices[2] = 0;

        for (int n = 1; n < verticeCount; n++) {

            vertices[n * 3 + 0] = r * cos((n - 1) * dθ);//x
            vertices[n * 3 + 1] = r * sin((n - 1) * dθ);//y
            vertices[n * 3 + 2] = 0;//z
        }

        textures[0] = 0.5f;
        textures[1] = 0.5f;

        for (int i = 1; i < verticeCount; i++) {
            textures[2 * i] = 0.5f + 0.5f * cos((i - 1) * dθ);
            textures[2 * i + 1] = 0.5f - 0.5f * sin((i - 1) * dθ);
        }

        vertexBuffer = GLUtil.getFloatBuffer(vertices);
        mTextureBuffer = GLUtil.getFloatBuffer(textures);
    }


    /**
     * 初始化OpenGL ES 程序
     */
    private void initProgram() {
        //顶点着色
        int vertexShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_VERTEX_SHADER, "texture.vert");
        //片元着色
        int fragmentShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_FRAGMENT_SHADER, "texture.frag");

        mProgram = GLES20.glCreateProgram();//创建空的OpenGL ES 程序
        GLES20.glAttachShader(mProgram, vertexShader);//加入顶点着色器
        GLES20.glAttachShader(mProgram, fragmentShader);//加入片元着色器
        GLES20.glLinkProgram(mProgram);//创建可执行的OpenGL ES项目

        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");//位置坐标
        maCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "aCoordinate");//贴图坐标
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");//变换矩阵
    }


    public void draw(float[] mvpMatrix) {
        // 将程序添加到OpenGL ES环境中
        GLES20.glUseProgram(mProgram);
        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        //启用三角形顶点颜色的句柄
        GLES20.glEnableVertexAttribArray(maCoordinateHandle);

        //顶点矩阵变换
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0);

        //准备三角顶点坐标数据
        GLES20.glVertexAttribPointer(
                maPositionHandle,//int indx, 索引
                3,//int size,大小
                GLES20.GL_FLOAT,//int type,类型
                false,//boolean normalized,//是否标准化
                3 * 4,// int stride,//跨度
                vertexBuffer);// java.nio.Buffer ptr//缓冲

        //准备三角顶点颜色数据
        GLES20.glVertexAttribPointer(
                maCoordinateHandle,
                2,
                GLES20.GL_FLOAT,
                false,
                2 * 4,
                mTextureBuffer);

        //绑定纹理
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTId);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, verticeCount);


        //禁用顶点数组:
        //禁用index指定的通用顶点属性数组。
        // 默认情况下，禁用所有客户端功能，包括所有通用顶点属性数组。
        // 如果启用，将访问通用顶点属性数组中的值，
        // 并在调用顶点数组命令（如glDrawArrays或glDrawElements）时用于呈现
        GLES20.glDisableVertexAttribArray(maPositionHandle);
    }
}

