package com.toly1994.picture.shape.threeD;

import android.content.Context;

import com.toly1994.picture.shape.part.CircleFanEvn;
import com.toly1994.picture.shape.part.CylinderSideEvn;
import com.toly1994.picture.utils.MatrixStack;
import com.toly1994.picture.world.abs.RenderAble;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/16/016:19:22<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：圆柱类
 */
public class Cylinder extends RenderAble {
    private final CircleFanEvn mBottomCircleTris;//底圆
    private final CircleFanEvn mTopCircleTris;//顶圆
    private final CylinderSideEvn mCylinderSide;
    private float mH;

    /**
     * @param context     上下文
     * @param h           高
     * @param r           底面半径
     * @param splitCount  切割数
     * @param textureIdX3 贴图id 上、下、周围贴图
     */
    public Cylinder(Context context, float r, float h, int splitCount, int[] textureIdX3) {
        super(context);
        if (textureIdX3.length != 3) {
            throw new IllegalArgumentException("the length of textureIdX3 must be 3");
        }
        mH = h;
        mBottomCircleTris = new CircleFanEvn(context, textureIdX3[0], r, splitCount);
        mTopCircleTris = new CircleFanEvn(context, textureIdX3[1], r, splitCount);
        mCylinderSide = new CylinderSideEvn(context, textureIdX3[2], r, h, splitCount);
    }


    @Override
    public void draw(float[] mvpMatrix) {

        MatrixStack.reTranslate(mvpMatrix, 0, 0, mH);
        mTopCircleTris.draw(MatrixStack.getOpMatrix());
        MatrixStack.restore();

        MatrixStack.reRotate(mvpMatrix, 90, 1, 0, 0);
        mCylinderSide.draw(MatrixStack.getOpMatrix());
        MatrixStack.restore();

        mBottomCircleTris.draw(mvpMatrix);
    }
}
