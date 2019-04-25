package com.toly1994.picture.shape.threeD;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.shape.towD.SimpleShape;
import com.toly1994.picture.world.abs.RenderAble;
import com.toly1994.picture.world.base.Cons;
import com.toly1994.picture.world.base.Shape;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/17/017:7:29<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：坐标系封装
 */
public class Coo3d extends RenderAble {
    private final SimpleShape mSideShape;
    private final SimpleShape mTopShape;
    private final SimpleShape mBottomShape;
    private float[] mVertex = new float[]{
            -1.0f, 0.0f, -1.0f,//A
            -1.0f, 0.0f, 1.0f,//B
            1.0f, 0.0f, 1.0f,//C
            1.0f, 0.0f, -1.0f,//D
    };

    private float[] mVertex2 = new float[]{
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,

            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,

            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,

            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
    };
    private float[] mColor2 = new float[]{
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
    };


    private float[] mColor = new float[]{
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            0.21960784f, 0.56078434f, 0.92156863f, 1.0f,
    };
    private final SimpleShape mCooShape;

    public Coo3d(Context context) {
        super(context);

        Shape coo = new Shape(Cons.VERTEX_COO, Cons.COLOR_COO, GLES20.GL_LINES);
        Shape ground = new Shape(mVertex, mColor, GLES20.GL_LINE_LOOP);
        Shape side = new Shape(mVertex2, mColor2, GLES20.GL_LINES);
        Shape top = ground.moveAndCreate(0, 1, 0);
        Shape bottom = ground.moveAndCreate(0, -1, 0);

        mCooShape = new SimpleShape(mContext, coo);
        mSideShape = new SimpleShape(mContext, side);
        mTopShape = new SimpleShape(mContext, top);
        mBottomShape = new SimpleShape(mContext, bottom);
    }

    @Override
    public void draw(float[] mvpMatrix) {

        mCooShape.draw(mvpMatrix);
        mSideShape.draw(mvpMatrix);
        mTopShape.draw(mvpMatrix);
        mBottomShape.draw(mvpMatrix);
    }
}