package com.toly1994.picture.world.shape;

import android.content.Context;

import com.toly1994.picture.R;
import com.toly1994.picture.shape.threeD.MoFang;
import com.toly1994.picture.utils.GLUtil;
import com.toly1994.picture.world.abs.RenderAble;
import com.toly1994.picture.world.bean.RepeatType;
import com.toly1994.picture.world.itf.OP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:8:39<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：世界的形状
 */
public class MoFangShape extends RenderAble implements OP<RenderAble> {
    List<RenderAble> mRenderAbles;

    public MoFangShape(Context context) {
        super(context);
        mRenderAbles = new ArrayList<>();

        int tIdA = GLUtil.loadTexture(mContext, R.mipmap.wall_a, RepeatType.REPEAT);
        int tIdB = GLUtil.loadTexture(mContext, R.mipmap.wall_b, RepeatType.REPEAT);
        int tIdC = GLUtil.loadTexture(mContext, R.mipmap.wall_c, RepeatType.REPEAT);
        int tIdD = GLUtil.loadTexture(mContext, R.mipmap.wall_d, RepeatType.REPEAT);
        int tIdE = GLUtil.loadTexture(mContext, R.mipmap.wall_e, RepeatType.REPEAT);
        int tIdF = GLUtil.loadTexture(mContext, R.mipmap.wall_f, RepeatType.REPEAT);

        add(new MoFang(mContext, 0.5f, 0.5f, 0.5f,
                new int[]{tIdA, tIdB, tIdC, tIdD, tIdE, tIdF}));//魔方

    }


    @Override
    public void draw(float[] mvpMatrix) {
        for (RenderAble renderAble : mRenderAbles) {
            renderAble.draw(mvpMatrix);
        }
    }

    @Override
    public void add(RenderAble... renderAbles) {
        Collections.addAll(mRenderAbles, renderAbles);
    }

    @Override
    public void remove(int id) {
        if (id >= mRenderAbles.size()) {
            return;
        }
        mRenderAbles.remove(id);
    }
}
