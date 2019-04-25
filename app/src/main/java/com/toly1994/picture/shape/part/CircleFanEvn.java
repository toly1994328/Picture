package com.toly1994.picture.shape.part;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.world.abs.EvnRender;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:20:09<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：GL_TRIANGLE_FAN 三角形拼合的圆形
 */
public class CircleFanEvn extends EvnRender {

    public CircleFanEvn(Context context, int tId, float r, int splitCount) {
        super(context, tId, GLES20.GL_TRIANGLE_FAN);
        initVertex(r, splitCount);
    }


    /**
     * 初始化顶点坐标数据的方法
     *
     * @param r          半径
     * @param splitCount 切分的份数
     */
    public void initVertex(float r, int splitCount) {
        float dθ = 360.0f / splitCount;//顶角的度数
        int vertexCount = splitCount + 2;//顶点个数，共有n个三角形，每个三角形都有三个顶点
        float[] vertices = new float[vertexCount * 3];//坐标数据
        float[] textures = new float[vertexCount * 2];//顶点纹理S、T坐标值数组

        vertices[0] = 0;
        vertices[1] = 0;
        vertices[2] = 0;

        textures[0] = 0.5f;
        textures[1] = 0.5f;

        for (int n = 1; n < vertexCount; n++) {
            //顶点坐标
            vertices[n * 3 + 0] = r * cos((n - 1) * dθ);//x
            vertices[n * 3 + 1] = r * sin((n - 1) * dθ);//y
            vertices[n * 3 + 2] = 0;//z
            //纹理坐标
            textures[2 * n] = 0.5f + 0.5f * cos((n - 1) * dθ);
            textures[2 * n + 1] = 0.5f - 0.5f * sin((n - 1) * dθ);
        }

        //法向量数据初始化
        float[] normals = new float[vertices.length];
        for (int i = 0; i < normals.length; i += 3) {
            normals[i] = 0;
            normals[i + 1] = 0;
            normals[i + 2] = 1;
        }
        init(vertices, textures, normals);
    }
}
