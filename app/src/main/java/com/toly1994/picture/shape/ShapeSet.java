package com.toly1994.picture.shape;

import android.content.Context;

import com.toly1994.picture.world.abs.RenderAble;
import com.toly1994.picture.world.itf.OP;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:8:39<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：世界的形状
 */
public class ShapeSet extends RenderAble implements OP<RenderAble> {
    List<RenderAble> mRenderAbles;

    public ShapeSet(Context context) {
        super(context);
        mRenderAbles = new ArrayList<>();
    }


    @Override
    public void draw(float[] mvpMatrix) {
        for (RenderAble renderAble : mRenderAbles) {
            renderAble.draw(mvpMatrix);
        }
    }

    @Override
    public void add(RenderAble... renderAbles) {
        for (RenderAble renderAble : renderAbles) {
            mRenderAbles.add(renderAble);
        }
    }

    @Override
    public void remove(int id) {
        if (id >= mRenderAbles.size()) {
            return;
        }
        mRenderAbles.remove(id);
    }
}
