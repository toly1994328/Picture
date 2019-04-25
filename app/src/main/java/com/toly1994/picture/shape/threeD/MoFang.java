package com.toly1994.picture.shape.threeD;

import android.content.Context;

import com.toly1994.picture.utils.MatrixStack;
import com.toly1994.picture.world.abs.RenderAble;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/17/017:15:32<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class MoFang extends RenderAble {
    private final float[] mTrans;
    private final Cube3d mCube3d;

    public MoFang(Context context, float x, float y, float z, int[] textureIdX6) {
        super(context);
        mCube3d = new Cube3d(context, x, y, z, textureIdX6);
        //立方的偏移数组
        mTrans = new float[]{
                0, 0, 0,
                0, 0, 0.5f,
                0, 0, -0.5f,

                0, 0.5f, 0,
                0, 0.5f, 0.5f,
                0, 0.5f, -0.5f,

                0.5f, 0.5f, 0,
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f,

                0.5f, 0f, 0,
                0.5f, 0f, 0.5f,
                0.5f, 0f, -0.5f,

                0.5f, -0.5f, 0,
                0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, -0.5f,

                0f, -0.5f, 0,
                0f, -0.5f, 0.5f,
                0f, -0.5f, -0.5f,

                -0.5f, -0.5f, 0,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,

                -0.5f, 0f, 0,
                -0.5f, 0f, 0.5f,
                -0.5f, 0f, -0.5f,

                -0.5f, 0.5f, 0,
                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
        };

    }

    @Override
    public void draw(float[] mvpMatrix) {
        for (int i = 0; i < mTrans.length / 3; i++) {
            MatrixStack.reTranslate(mvpMatrix, mTrans[3 * i], mTrans[3 * i + 1], mTrans[3 * i + 2]);
            mCube3d.draw(MatrixStack.getOpMatrix());
            MatrixStack.restore();
        }
    }
}
