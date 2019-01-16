package com.toly1994.picture.world;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.toly1994.picture.utils.GLState;
import com.toly1994.picture.utils.MatrixStack;

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


//        lovo=LoadUtil.loadFromFile("ch.obj", mContext.getResources(),MySurfaceView.this);

        float ratio = (float) width / height;

        MatrixStack.frustum(
                -ratio, ratio, -1, 1,
                3f, 9);

        MatrixStack.lookAt(2, 2, -6,
                0f, 0f, 0f,
                0f, 1.0f, 0.0f);

        MatrixStack.reset();
        GLState.setEviLight(0.7f,0.7f,0.7f,1.0f);
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



        GLState.setLightLocation(1, 1, -1);
//        GLState.setLightLocation(-1, 1, -1);

        //清除颜色缓存和深度缓存
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        MatrixStack.save();
        MatrixStack.rotate(currDeg, 0, 1, 0);
//        MatrixStack.scale(0.01f,0.01f,0.01f);
        mWorldShape.draw(MatrixStack.peek());
        MatrixStack.restore();


//        MatrixStack.save();
//        MatrixStack.translate(0, 0, 1f);
//        MatrixStack.rotate(currDeg, -1.5f, 0, 0);
//        mWorldShape.draw(MatrixStack.peek());
//        MatrixStack.restore();
//
//        MatrixStack.save();
//        MatrixStack.translate(1.5f, 0, 0);
//        MatrixStack.rotate(currDeg, 0, 1, 0);
//        MatrixStack.scale(0.5f,1,0.5f);
//        mWorldShape.draw(MatrixStack.peek());
//        MatrixStack.restore();

        currDeg++;
        if (currDeg == 360) {
            currDeg = 0;
        }

        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }


}
