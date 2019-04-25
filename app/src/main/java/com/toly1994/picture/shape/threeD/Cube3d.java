package com.toly1994.picture.shape.threeD;

import android.content.Context;

import com.toly1994.picture.shape.part.RectangleEvn;
import com.toly1994.picture.utils.MatrixStack;
import com.toly1994.picture.world.abs.RenderAble;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:20:09<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：贴图立方
 */
public class Cube3d extends RenderAble {
    private final RectangleEvn mRectA;
    private final RectangleEvn mRectB;
    private final RectangleEvn mRectD;
    private final RectangleEvn mRectC;
    private final RectangleEvn mRectE;
    private final RectangleEvn mRectF;

    private float rate;

    private float mX;
    private float mY;
    private float mZ;

    public Cube3d(Context context, float x, float y, float z, int[] textureIdX6) {
        super(context);
        if (textureIdX6.length != 6) {
            throw new IllegalArgumentException("the length of textureIdX3 must be 6");
        }
        mX = x;
        mY = y;
        mZ = z;

        mRectA = new RectangleEvn(mContext, textureIdX6[0], 0, y, z);
        mRectB = new RectangleEvn(mContext, textureIdX6[1], 0, y, z);
        mRectC = new RectangleEvn(mContext, textureIdX6[2], 0, y, z);
        mRectD = new RectangleEvn(mContext, textureIdX6[3], 0, y, z);
        mRectE = new RectangleEvn(mContext, textureIdX6[4], 0, y, z);
        mRectF = new RectangleEvn(mContext, textureIdX6[5], 0, y, z);
    }

    @Override
    public void draw(float[] mvpMatrix) {

        mRectA.draw(mvpMatrix);

        MatrixStack.reTranslate(mvpMatrix, 0, 0, mZ);
        MatrixStack.rotate(90, 0, 1, 0);
        mRectB.draw(MatrixStack.getOpMatrix());
        MatrixStack.restore();

        MatrixStack.reTranslate(mvpMatrix, mX, 0, 0);
        MatrixStack.rotate(90, 0, -1, 0);
        mRectD.draw(MatrixStack.getOpMatrix());
        MatrixStack.restore();


        MatrixStack.reTranslate(mvpMatrix, 0, 0, 0);
        MatrixStack.rotate(-90, 0, 0, 1);

        MatrixStack.translate(0, 0, mZ);
        MatrixStack.rotate(180, 0, 1, 0);
        mRectF.draw(MatrixStack.getOpMatrix());
        MatrixStack.restore();

        MatrixStack.reTranslate(mvpMatrix, 0, mY, 0);
        MatrixStack.rotate(-90, 0, 0, 1);
        mRectE.draw(MatrixStack.getOpMatrix());
        MatrixStack.restore();


        MatrixStack.reTranslate(mvpMatrix, mX, 0, mZ);
        MatrixStack.rotate(-180, 0, 1, 0);
        mRectC.draw(MatrixStack.getOpMatrix());
        MatrixStack.restore();
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
