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
public class CircleTrisEvn extends EvnRender {

    public CircleTrisEvn(Context context, int tId, float r, int splitCount) {
        super(context, tId, GLES20.GL_TRIANGLES);
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
        int vertexCount = 3 * splitCount;//顶点个数，共有n个三角形，每个三角形都有三个顶点
        float[] vertices = new float[vertexCount * 3];//坐标数据
        float[] textures = new float[vertexCount * 2];//顶点纹理S、T坐标值数组

        for (int v = 0; v < vertexCount; v += 3) {
            int n = v / 3;
            vertices[3 * v] = 0;//顶点坐标:p0
            vertices[3 * v + 1] = 0;
            vertices[3 * v + 2] = 0;
            vertices[3 * v + 3] = r * cos(n * dθ);//顶点坐标:p1
            vertices[3 * v + 4] = r * sin(n * dθ);
            vertices[3 * v + 5] = 0;
            vertices[3 * v + 6] = r * cos((n + 1) * dθ);//顶点坐标:p2
            vertices[3 * v + 7] = r * sin((n + 1) * dθ);
            vertices[3 * v + 8] = 0;

            textures[2 * v] = 0.5f;//贴图：p0
            textures[2 * v + 1] = 0.5f;
            textures[2 * v + 2] = 0.5f + 0.5f * r * cos(n * dθ);//贴图：p1
            textures[2 * v + 3] = 0.5f - 0.5f * r * sin(n * dθ);
            textures[2 * v + 4] = 0.5f + 0.5f * r * cos((n + 1) * dθ);//贴图：p2
            textures[2 * v + 5] = 0.5f - 0.5f * r * sin((n + 1) * dθ);
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
