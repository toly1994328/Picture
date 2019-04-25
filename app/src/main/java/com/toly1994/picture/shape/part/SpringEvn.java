package com.toly1994.picture.shape.part;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.world.abs.EvnRender;

import java.util.ArrayList;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:20:09<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：螺线管
 */
public class SpringEvn extends EvnRender {

    public SpringEvn(Context context, int tId, float rBig, float rSmall,//螺旋管外径、螺旋管内径
                     float h, float nCirclef, //螺旋管高度，螺旋管圈数
                     int nCol, int nRow) {
        super(context, tId, GLES20.GL_TRIANGLES);
        initVertex(rBig, rSmall, h, nCirclef, nCol, nRow);
    }


    //自定义的初始化顶点数据的方法
    public void initVertex(
            float rBig, float rSmall,//螺旋管外径、螺旋管内径
            float h, float nCirclef, //螺旋管高度，螺旋管圈数
            int nCol, int nRow) {//小圆周和大圆周切分的份数
        float angdegTotal = nCirclef * 360.0f;//大圆周总度数
        float angdegColSpan = 360.0f / nCol;//小圆周每份的角度跨度
        float angdegRowSpan = angdegTotal / nRow;//大圆周每份的角度跨度
        float A = (rBig - rSmall) / 2;//用于旋转的小圆半径
        float D = rSmall + A;//旋转轨迹形成的大圆周半径
        int vCount = 3 * nCol * nRow * 2;//顶点个数，共有nColumn*nRow*2个三角形，每个三角形都有三个顶点
        //坐标数据初始化
        ArrayList<Float> alVertix = new ArrayList<>();//原顶点列表（未卷绕）
        ArrayList<Integer> alFaceIndex = new ArrayList<>();//组织成面的顶点的索引值列表（按逆时针卷绕）
        //顶点
        for (float angdegCol = 0; Math.ceil(angdegCol) < 360 + angdegColSpan; angdegCol += angdegColSpan) {
            double a = Math.toRadians(angdegCol);//当前小圆周弧度
            for (float angdegRow = 0; Math.ceil(angdegRow) < angdegTotal + angdegRowSpan; angdegRow += angdegRowSpan)//重复了一列顶点，方便了索引的计算
            {
                float yVec = (angdegRow / angdegTotal) * h;//根据旋转角度增加y的值
                double u = Math.toRadians(angdegRow);//当前大圆周弧度
                float y = (float) (A * Math.cos(a));
                float x = (float) ((D + A * Math.sin(a)) * Math.sin(u));
                float z = (float) ((D + A * Math.sin(a)) * Math.cos(u));
                //将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
                alVertix.add(x);
                alVertix.add(y + yVec);
                alVertix.add(z);
            }
        }
        //索引
        for (int i = 0; i < nCol; i++) {
            for (int j = 0; j < nRow; j++) {
                int index = i * (nRow + 1) + j;//当前索引
                //卷绕索引
                alFaceIndex.add(index + 1);//下一列---1
                alFaceIndex.add(index + nRow + 1);//下一列---2
                alFaceIndex.add(index + nRow + 2);//下一行下一列---3

                alFaceIndex.add(index + 1);//下一列---1
                alFaceIndex.add(index);//当前---0
                alFaceIndex.add(index + nRow + 1);//下一列---2
            }
        }
        //计算卷绕顶点和平均法向量
        float[] vertices = new float[vCount * 3];
        cullVertex(alVertix, alFaceIndex, vertices);

        //纹理
        ArrayList<Float> alST = new ArrayList<Float>();//原纹理坐标列表（未卷绕）
        for (float angdegCol = 0; Math.ceil(angdegCol) < 360 + angdegColSpan; angdegCol += angdegColSpan) {
            float t = angdegCol / 360;//t坐标
            for (float angdegRow = 0; Math.ceil(angdegRow) < angdegTotal + angdegRowSpan; angdegRow += angdegRowSpan)//重复了一列纹理坐标，以索引的计算
            {
                float s = angdegRow / angdegTotal;//s坐标
                //将计算出来的ST坐标加入存放顶点坐标的ArrayList
                alST.add(s);
                alST.add(t);
            }
        }
        //计算卷绕后纹理坐标
        float[] textures = cullTexCoor(alST, alFaceIndex);
        float[] normals = new float[vertices.length];
        init(vertices, textures, normals);
    }

    //通过原顶点和面的索引值，得到用顶点卷绕的数组
    public static void cullVertex(
            ArrayList<Float> alv,//原顶点列表（未卷绕）
            ArrayList<Integer> alFaceIndex,//组织成面的顶点的索引值列表（按逆时针卷绕）
            float[] vertices//用顶点卷绕的数组（顶点结果放入该数组中，数组长度应等于索引列表长度的3倍）
    ) {
        //生成顶点的数组
        int vCount = 0;
        for (int i : alFaceIndex) {
            vertices[vCount++] = alv.get(3 * i);
            vertices[vCount++] = alv.get(3 * i + 1);
            vertices[vCount++] = alv.get(3 * i + 2);
        }
    }

    //根据原纹理坐标和索引，计算卷绕后的纹理的方法
    public static float[] cullTexCoor(
            ArrayList<Float> alST,//原纹理坐标列表（未卷绕）
            ArrayList<Integer> alTexIndex//组织成面的纹理坐标的索引值列表（按逆时针卷绕）
    ) {
        float[] textures = new float[alTexIndex.size() * 2];
        //生成顶点的数组
        int stCount = 0;
        for (int i : alTexIndex) {
            textures[stCount++] = alST.get(2 * i);
            textures[stCount++] = alST.get(2 * i + 1);
        }
        return textures;
    }
}