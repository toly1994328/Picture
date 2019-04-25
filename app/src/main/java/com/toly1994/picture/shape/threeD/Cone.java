package com.toly1994.picture.shape.threeD;

import android.content.Context;

import com.toly1994.picture.shape.part.CircleFanEvn;
import com.toly1994.picture.shape.part.ConeSideFanEvn;
import com.toly1994.picture.utils.MatrixStack;
import com.toly1994.picture.world.abs.RenderAble;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/16/016:19:22<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：圆锥类
 */
public class Cone extends RenderAble {
    private  CircleFanEvn mBottomCircleTris;//底圆
    private  ConeSideFanEvn mConeSide;//侧面
    private float mH;

    /**
     * @param context     上下文
     * @param h           高
     * @param r           底面半径
     * @param splitCount  切割数
     * @param textureIdX2 贴图id 下、周围贴图
     */
    public Cone(Context context, float r, float h, int splitCount, int[] textureIdX2) {
        super(context);
        if (textureIdX2.length != 2) {
            throw new IllegalArgumentException("the length of textureIdX3 must be 2");
        }
        mH = h;
        mBottomCircleTris = new CircleFanEvn(context, textureIdX2[0], r, splitCount);
        mConeSide = new ConeSideFanEvn(context, textureIdX2[1], r, h,splitCount);
    }


    @Override
    public void draw(float[] mvpMatrix) {

        MatrixStack.reRotate(mvpMatrix, 90, 1, 0, 0);
        mConeSide.draw(MatrixStack.getOpMatrix());
        MatrixStack.restore();

        mBottomCircleTris.draw(mvpMatrix);
    }
}
