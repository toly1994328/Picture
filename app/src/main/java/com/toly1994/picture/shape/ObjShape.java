package com.toly1994.picture.shape;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.toly1994.picture.utils.GLUtil;
import com.toly1994.picture.world.abs.RendererAble;

import java.nio.FloatBuffer;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:20:09<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：贴图测试
 */
public class ObjShape extends RendererAble {
    private static final String TAG = "Triangle";
    private Context mContext;

    private int mProgram;//OpenGL ES 程序
    private int mPositionHandle;//位置句柄
    private int muMVPMatrixHandle;//顶点变换矩阵句柄

    private FloatBuffer vertexBuffer;//顶点缓冲
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 3*4=12
    static final int COORDS_PER_VERTEX = 3;//数组中每个顶点的坐标数


    private static float[] mMVPMatrix = new float[16];//最终矩阵

    private int mVertexCount;

    public ObjShape(Context context) {
        super(context);
        mContext = context;
        //初始化顶点字节缓冲区
        bufferData();//缓冲顶点数据
        initProgram();//初始化OpenGL ES 程序

    }


    /**
     * 缓冲数据
     */
    private void bufferData() {
        float[] vertexs = GLUtil.loadPosInObj("obj.obj", mContext);
        mVertexCount = vertexs.length / COORDS_PER_VERTEX;
        vertexBuffer = GLUtil.getFloatBuffer(vertexs);

    }

    /**
     * 初始化OpenGL ES 程序
     */
    private void initProgram() {
        int vertexShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_VERTEX_SHADER, "obj.vert");
        int fragmentShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_FRAGMENT_SHADER, "obj.frag");

        mProgram = GLES20.glCreateProgram();//创建空的OpenGL ES 程序
        GLES20.glAttachShader(mProgram, vertexShader);//加入顶点着色器
        GLES20.glAttachShader(mProgram, fragmentShader);//加入片元着色器
        GLES20.glLinkProgram(mProgram);//创建可执行的OpenGL ES项目

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }


    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

//        MatrixStack.save();
//        MatrixStack.reset();
//        MatrixStack.scale(0.02f, 0.02f, 0.02f);
//        Matrix.multiplyMM(mvpMatrix, 0, mvpMatrix, 0, MatrixStack.peek(), 0);
//        MatrixStack.restore();

        Matrix.scaleM(mMVPMatrix, 0, mvpMatrix, 0, 0.02f, 0.02f, 0.02f);
        Matrix.translateM(mMVPMatrix,0,-230,-50,30);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES20.glVertexAttribPointer(
                mPositionHandle,//int indx, 索引
                COORDS_PER_VERTEX,//int size,大小
                GLES20.GL_FLOAT,//int type,类型
                false,//boolean normalized,//是否标准化
                vertexStride,// int stride,//跨度
                vertexBuffer);// java.nio.Buffer ptr//缓冲


        //绑定纹理
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

