//package com.toly1994.picture.widget;
//
//import android.content.Context;
//import android.opengl.GLSurfaceView;
//import android.util.AttributeSet;
//
///**
// * 作者：张风捷特烈<br/>
// * 时间：2019/1/9 0009:18:25<br/>
// * 邮箱：1981462002@qq.com<br/>
// * 说明：GL测试视图
// */
//public class GLView extends GLSurfaceView {
//    private GLRenderer mRenderer;
//
//    public GLView(Context context) {
//        this(context,null);
//    }
//
//    public GLView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    private void init() {
//        setEGLContextClientVersion(2);//设置OpenGL ES 2.0 context
//        mRenderer = new GLRenderer(getContext());
//        setRenderer(mRenderer);//设置渲染器
//        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
////        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//
//    }
//}
