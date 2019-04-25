package com.toly1994.picture.world.abs;

import android.content.Context;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:8:37<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public abstract class RenderAble {
    protected Context mContext;

    public RenderAble(Context context) {
        mContext = context;
    }

    public abstract void draw(float[] mvpMatrix);



    /**
     * 求sin值
     *
     * @param θ 角度值
     * @return sinθ
     */
    public float sin(float θ) {
        return (float) Math.sin(Math.toRadians(θ));
    }

    /**
     * 求cos值
     *
     * @param θ 角度值
     * @return cosθ
     */
    public float cos(float θ) {
        return (float) Math.cos(Math.toRadians(θ));
    }
}
