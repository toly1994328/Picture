package com.toly1994.picture.world;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.world.abs.RendererAble;
import com.toly1994.picture.world.base.Cons;
import com.toly1994.picture.world.base.Shape;
import com.toly1994.picture.world.e.SimpleShape;
import com.toly1994.picture.world.itf.OP;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:8:39<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：世界的形状
 */
public class WorldShape extends RendererAble implements OP<Shape> {
    List<RendererAble> mRendererAbles;

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

    public WorldShape(Context ctx) {
        super(ctx);
        mRendererAbles = new ArrayList<>();

        Shape coo = new Shape(Cons.VERTEX_COO, Cons.COLOR_COO, GLES20.GL_LINES);
        Shape ground = new Shape(mVertex, mColor, GLES20.GL_LINE_LOOP);
        Shape side = new Shape(mVertex2, mColor2, GLES20.GL_LINES);
        Shape top = ground.moveAndCreate(0, 1, 0);
        Shape bottom = ground.moveAndCreate(0, -1, 0);
        add(coo, top, bottom, side);
    }


    @Override
    public void draw(float[] mvpMatrix) {
        for (RendererAble rendererAble : mRendererAbles) {
            rendererAble.draw(mvpMatrix);
        }
    }

    @Override
    public void add(Shape... shapes) {
        for (Shape shape : shapes) {
            mRendererAbles.add(new SimpleShape(mContext, shape));
        }
    }

    @Override
    public void remove(int id) {
        if (id >= mRendererAbles.size()) {
            return;
        }
        mRendererAbles.remove(id);
    }
}
