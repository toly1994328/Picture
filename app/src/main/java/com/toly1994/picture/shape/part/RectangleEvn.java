package com.toly1994.picture.shape.part;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.world.abs.EvnRender;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/17/017:11:28<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：与Y轴组成的面
 */
public class RectangleEvn extends EvnRender {
    public RectangleEvn(Context context, int tId, float x, float y, float z) {
        super(context, tId, GLES20.GL_TRIANGLE_STRIP);
        initVertex(x, y, z);
    }

    private void initVertex(float x, float y, float z) {
        int vertexCount = 4;//顶点个数，共有n个三角形，每个三角形都有三个顶点
        float[] vertices = new float[vertexCount * 3];//坐标数据
        float[] textures = new float[vertexCount * 2];//顶点纹理S、T坐标值数组
        float[] normals = new float[vertices.length];

        //顶点坐标
        vertices[0] = 0;//p0
        vertices[1] = 0;
        vertices[2] = 0;

        vertices[3] = 0;//p1
        vertices[4] = y;
        vertices[5] = 0;

        vertices[6] = x;//p3
        vertices[7] = 0;
        vertices[8] = z;

        vertices[9] = x;//p2
        vertices[10] = y;
        vertices[11] = z;

        //贴图坐标
        textures[0] = 0;//p0
        textures[1] = 1;

        textures[2] = 0;//p1
        textures[3] = 0;

        textures[4] = 1;//p3
        textures[5] = 1;

        textures[6] = 1;//p2
        textures[7] = 0;
        init(vertices, textures, normals);
    }
}
