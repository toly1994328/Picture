package com.toly1994.picture.shape.part;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.utils.ObjLoaderUtil;
import com.toly1994.picture.world.abs.EvnRender;

import java.util.ArrayList;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:20:09<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：环面
 */
public class ObjEvn extends EvnRender {

    public ObjEvn(Context context, int tId) {
        super(context, tId, GLES20.GL_TRIANGLES);
        mContext = context;
        initVertex();
    }

    @Override
    protected void init(float[] vertex, float[] texture, float[] normal) {
        super.init(vertex, texture, normal);
    }


    //自定义的初始化顶点数据的方法

    public void initVertex() {
        try {
            ArrayList<ObjLoaderUtil.ObjData> load = ObjLoaderUtil.load("cy.obj", mContext.getResources());
            float[] aNormals = load.get(0).aNormals;
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
