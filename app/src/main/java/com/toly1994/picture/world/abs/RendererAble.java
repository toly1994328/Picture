package com.toly1994.picture.world.abs;

import android.content.Context;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:8:37<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public abstract class RendererAble {
    protected Context mContext;

    public RendererAble(Context context) {
        mContext = context;
    }

    public abstract void draw(float[] mvpMatrix);
}
