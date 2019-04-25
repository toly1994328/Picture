package com.toly1994.picture.shape.part;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.utils.VectorUtil;
import com.toly1994.picture.world.abs.EvnRender;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/17/017:9:44<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：圆锥侧面
 */
public class ConeSideEvn extends EvnRender {
    public ConeSideEvn(Context context, int tId, float r, float h, int splitCount) {
        super(context, tId, GLES20.GL_TRIANGLES);
        initVertexData(r, h, splitCount);
    }


    /**
     * 初始化顶点
     *
     * @param r          半径
     * @param h          高度
     * @param splitCount 切分的份数
     */
    public void initVertexData(float r, float h, int splitCount) {
        float dθ = 360.0f / splitCount;
        int vCount = splitCount * 3;//顶点个数，共有3*splitCount*4个三角形，每个三角形都有三个顶点
        //坐标数据初始化
        float[] vertices = new float[vCount * 3];
        float[] textures = new float[vCount * 2];//顶点纹理S、T坐标值数组
        float[] normals = new float[vertices.length];//法向量数组

        for (int v = 0; v < vCount; v += 3) {
            int n = v / 3;
            float x = r * cos(n * dθ);
            float xNext = r * cos(n * dθ + dθ);
            float z = r * sin(n * dθ);
            float zNext = r * sin(n * dθ + dθ);
            //顶点坐标
            vertices[3 * v + 0] = 0;//p0
            vertices[3 * v + 1] = h;
            vertices[3 * v + 2] = 0;

            vertices[3 * v + 3] = x;//p1
            vertices[3 * v + 4] = 0;
            vertices[3 * v + 5] = z;

            vertices[3 * v + 6] = xNext;//p2
            vertices[3 * v + 7] = 0;
            vertices[3 * v + 8] = zNext;
            //纹理坐标
            float s = n * dθ / 360.f;
            float sNext = (n + 1) * dθ / 360.f;

            textures[2 * v + 0] = 0.5f;//p0
            textures[2 * v + 1] = 0f;
            textures[2 * v + 2] = s;//p1
            textures[2 * v + 3] = 1f;
            textures[2 * v + 4] = sNext;//p2
            textures[2 * v + 5] = 1f;
        }


        int norCount = 0;
        //法向量数据的初始化
        for (int i = 0; i < vertices.length; i = i + 3) {
            //如果当前的顶点为圆锥的最高点
            if (vertices[i] == 0 && vertices[i + 1] == h && vertices[i + 2] == 0) {
                normals[norCount++] = 0;
                normals[norCount++] = 1;
                normals[norCount++] = 0;
            } else {//当前的顶点为底面圆上的顶点
                float[] norXYZ = VectorUtil.calConeNormal(//通过三个顶点求出法向量
                        0, 0, 0,                        //底面圆的中心点
                        vertices[i], vertices[i + 1], vertices[i + 2], //当前的顶点坐标
                        0, h, 0);                        //顶点坐标(圆锥最高点)
                normals[norCount++] = norXYZ[0];
                normals[norCount++] = norXYZ[1];
                normals[norCount++] = norXYZ[2];
            }
        }

        init(vertices, textures, normals);
    }
}
