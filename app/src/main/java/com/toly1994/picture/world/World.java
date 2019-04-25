package com.toly1994.picture.world;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:10:46<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：GL的世界
 */
public class World extends GLSurfaceView {
    private static final String TAG = "World";
    private WorldRenderer mRenderer;

    private float[] mLightPos;

    public World(Context context) {
        this(context, null);
    }

    public World(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLightPos = new float[]{
                1, 1, -1
        };

        init();
    }

    private void init() {
        mRenderer = new WorldRenderer(this);
        setEGLContextClientVersion(2);//设置OpenGL ES 2.0 context

        setRenderer(mRenderer);//设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

//    public WorldRenderer getRenderer() {
//        return mRenderer;
//    }
//
//    public void setRenderer(WorldRenderer renderer) {
//        mRenderer = renderer;
//    }


    public float[] getLightPos() {
        return mLightPos;
    }


//    public void setShape(RenderAble renderAble) {
//        mRenderer = new WorldRenderer(this,renderAble);
//    }
}
