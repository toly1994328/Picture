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
public class CylinderSideEvn extends EvnRender {

    public CylinderSideEvn(Context context, int tId, float r, float h, int splitCount) {
        super(context, tId, GLES20.GL_TRIANGLES);
        initVertex(r, h, splitCount);
    }


    /**
     * 初始化顶点坐标数据的方法
     *
     * @param r          半径
     * @param h          高
     * @param splitCount 切分的份数
     */
    public void initVertex(float r, float h, int splitCount) {
        float dθ = 360.0f / splitCount;
        int vertexCount = splitCount * 4 * 3;//顶点个数，共有3*splitCount*4个三角形，每个三角形都有三个顶点
        //坐标数据初始化
        float[] vertices = new float[vertexCount * 3];
        float[] textures = new float[vertexCount * 2];//顶点纹理S、T坐标值数组



        for (int v = 0; v < vertexCount; v += 6) {
            int n = v / 6;
            float x = r * cos(n * dθ);
            float xNext = r * cos(n * dθ + dθ);
            float z = -r * sin(n * dθ);
            float zNext = -r * sin(n * dθ + dθ);

            vertices[3 * v + 0] = x;//底部p0
            vertices[3 * v + 1] = 0;
            vertices[3 * v + 2] = z;
            vertices[3 * v + 3] = xNext;//顶部p2
            vertices[3 * v + 4] = h;
            vertices[3 * v + 5] = zNext;
            vertices[3 * v + 6] = x;//顶部p1
            vertices[3 * v + 7] = h;//y
            vertices[3 * v + 8] = z;//z

            vertices[3 * v + 9] = x;//底部p0
            vertices[3 * v + 10] = 0;
            vertices[3 * v + 11] = z;
            vertices[3 * v + 12] = xNext;//底部p3
            vertices[3 * v + 13] = 0;//y
            vertices[3 * v + 14] = zNext;//z
            vertices[3 * v + 15] = xNext;//顶部p2
            vertices[3 * v + 16] = h;//y
            vertices[3 * v + 17] = zNext;//z

            float s = n * dθ / 360.f;
            float sNext = (n + 1) * dθ / 360.f;
            textures[2 * v + 0] = s;//贴图：p0
            textures[2 * v + 1] = 1;
            textures[2 * v + 2] = sNext;//贴图：p2
            textures[2 * v + 3] = 0;
            textures[2 * v + 4] = s;//贴图：p1
            textures[2 * v + 5] = 0;

            textures[2 * v + 6] = s;//贴图：p0
            textures[2 * v + 7] = 1;
            textures[2 * v + 8] = sNext;//贴图：p3
            textures[2 * v + 9] = 1;
            textures[2 * v + 10] = sNext;//贴图：p2
            textures[2 * v + 11] = 0;
        }

        //法向量数据初始化
        float[] normals = new float[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            if (i % 3 == 1) {
                normals[i] = 0;
            } else {
                normals[i] = vertices[i];
            }
        }
        init(vertices, textures, normals);
    }
}
