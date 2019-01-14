package com.toly1994.picture.world;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:18:56<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：GL世界渲染类
 */
public class WorldRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "GLRenderer";
    //Model View Projection Matrix--模型视图投影矩阵
    private static float[] mMVPMatrix = new float[16];
    //投影矩阵 mProjectionMatrix
    private static final float[] mProjectionMatrix = new float[16];
    //视图矩阵 mViewMatrix
    private static final float[] mViewMatrix = new float[16];
    //变换矩阵
    private float[] mOpMatrix = new float[16];

    private Context mContext;
    private WorldShape mWorldShape;
    private float axisZ;
    private float axisY;
    private float axisX;

    public WorldRenderer(Context context) {
        mContext = context;
    }

    private int currDeg = 0;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);//rgba
        mWorldShape = new WorldShape(mContext);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);//GL视口
        float ratio = (float) width / height;
        //透视投影矩阵--截锥
        Matrix.frustumM(mProjectionMatrix, 0,
                -ratio, ratio, -1, 1,
                3, 9);

        // 设置相机位置(视图矩阵)
        Matrix.setLookAtM(mViewMatrix, 0,
                -1.5f, 1.5f, -6,
                0f, 0f, 0f,
                0f, 1.0f, 0.0f);
    }

    public void setAxisZ(float axisZ) {
        this.axisZ = axisZ;
    }

    public void setAxisY(float axisY) {
        this.axisY = axisY;
    }

    public void setAxisX(float axisX) {
        this.axisX = axisX;
    }

    /**
     * 此方法会不断执行 {@link GLSurfaceView.RENDERMODE_CONTINUOUSLY}
     * 此方法执行一次 {@link GLSurfaceView.RENDERMODE_WHEN_DIRTY}
     *
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {



        //清除颜色缓存和深度缓存
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        //初始化变换矩阵
        Matrix.setRotateM(mOpMatrix, 0, currDeg, 0, 1, 0);

        Matrix.translateM(mOpMatrix, 0, axisX, axisY,axisZ);

        Matrix.multiplyMM(mMVPMatrix, 0,
                mViewMatrix, 0,
                mOpMatrix, 0);

        Matrix.multiplyMM(mMVPMatrix, 0,
                mProjectionMatrix, 0,
                mMVPMatrix, 0);

        mWorldShape.draw(mMVPMatrix);

//        currDeg++;
        if (currDeg == 360) {
            currDeg = 0;
        }

        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }


}
