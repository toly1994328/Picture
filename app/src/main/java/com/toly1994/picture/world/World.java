package com.toly1994.picture.world;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.toly1994.picture.utils.EventParser;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:10:46<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：GL的世界
 */
public class World extends GLSurfaceView {
    private static final String TAG = "World";
    private WorldRenderer mRenderer;
    private EventParser mEventParser;

    public World(Context context) {
        this(context, null);
    }

    public World(Context context, AttributeSet attrs) {
        super(context, attrs);
        mEventParser = new EventParser(getContext());
        mEventParser.setOnEventListener(new EventParser.OnEventListener() {
            @Override
            public void down(PointF pointF) {

            }

            @Override
            public void up(PointF pointF, EventParser.Orientation orientation) {

            }

            @Override
            public void move(double v, float dy, float dx, double dir, EventParser.Orientation orientation) {
                Log.e(TAG, "move: " + dx);

                float ds = EventParser.disPos2d(dx, dy);
                float fra = ds / EventParser.disPos2d(getWidth(), getHeight());

                switch (orientation) {
                    case LEFT_BOTTOM://左下
                        mRenderer.setAxisZ(-6 * fra);
                        break;
                    case RIGHT_TOP:
                        mRenderer.setAxisZ(6 * fra);
                        break;
                    case LEFT:
                        mRenderer.setAxisX(fra * 3f);
                        break;
                    case RIGHT:
                        mRenderer.setAxisX(fra * -3f);
                        break;
                    case TOP:
                        mRenderer.setAxisY(fra* 3f);
                        break;
                    case BOTTOM:
                        mRenderer.setAxisY(-fra* 3f);
                        break;
                }

                requestRender();
            }
        });

        init();
    }

    private void init() {
        setEGLContextClientVersion(2);//设置OpenGL ES 2.0 context
        mRenderer = new WorldRenderer(getContext());
        setRenderer(mRenderer);//设置渲染器
//        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mEventParser.parseEvent(event);
        return true;
    }

}
