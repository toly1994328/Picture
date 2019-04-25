package com.toly1994.picture.shape.part;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.utils.VectorUtil;
import com.toly1994.picture.world.abs.EvnRender;

import java.util.ArrayList;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:20:09<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：环面
 */
public class Regular20Evn extends EvnRender {

    public Regular20Evn(Context context, int tId, float scale, float aHalf, int n) {
        super(context, tId, GLES20.GL_TRIANGLES);
        initVertex(scale, aHalf, n);
    }


    //自定义的初始化顶点数据的方法
    public void initVertex(float scale, float aHalf, int n) //大小，黄金长方形长边的一半，分段数
    {
        aHalf *= scale;        //长边的一半
        float bHalf = aHalf * 0.618034f;        //短边的一半
        float r = (float) Math.sqrt(aHalf * aHalf + bHalf * bHalf);
        int vCount = 3 * 20 * n * n;//顶点个数，共有20个三角形，每个三角形都有三个顶点
        //正20面体坐标数据初始化
        ArrayList<Float> alVertix20 = new ArrayList<Float>();//正20面体的顶点列表（未卷绕）
        ArrayList<Integer> alFaceIndex20 = new ArrayList<Integer>();//正20面体组织成面的顶点的索引值列表（按逆时针卷绕）
        //正20面体顶点
        initAlVertix20(alVertix20, aHalf, bHalf);
        //正20面体索引
        initAlFaceIndex20(alFaceIndex20);
        //计算卷绕顶点
        float[] vertices20 = VectorUtil.cullVertex(alVertix20, alFaceIndex20);//只计算顶点

        //坐标数据初始化
        ArrayList<Float> alVertix = new ArrayList<Float>();//原顶点列表（未卷绕）
        ArrayList<Integer> alFaceIndex = new ArrayList<Integer>();//组织成面的顶点的索引值列表（按逆时针卷绕）
        int vnCount = 0;//前i-1行前所有顶点数的和
        for (int k = 0; k < vertices20.length; k += 9)//对正20面体每个大三角形循环
        {
            float[] v1 = new float[]{vertices20[k + 0], vertices20[k + 1], vertices20[k + 2]};
            float[] v2 = new float[]{vertices20[k + 3], vertices20[k + 4], vertices20[k + 5]};
            float[] v3 = new float[]{vertices20[k + 6], vertices20[k + 7], vertices20[k + 8]};
            //顶点
            for (int i = 0; i <= n; i++) {
                float[] viStart = VectorUtil.devideBall(r, v1, v2, n, i);
                float[] viEnd = VectorUtil.devideBall(r, v1, v3, n, i);
                for (int j = 0; j <= i; j++) {
                    float[] vi = VectorUtil.devideBall(r, viStart, viEnd, i, j);
                    alVertix.add(vi[0]);
                    alVertix.add(vi[1]);
                    alVertix.add(vi[2]);
                }
            }
            //索引
            for (int i = 0; i < n; i++) {
                if (i == 0) {//若是第0行，直接加入卷绕后顶点索引012
                    alFaceIndex.add(vnCount + 0);
                    alFaceIndex.add(vnCount + 1);
                    alFaceIndex.add(vnCount + 2);
                    vnCount += 1;
                    if (i == n - 1) {//如果是每个大三角形的最后一次循环，将下一列的顶点个数也加上
                        vnCount += 2;
                    }
                    continue;
                }
                int iStart = vnCount;//第i行开始的索引
                int viCount = i + 1;//第i行顶点数
                int iEnd = iStart + viCount - 1;//第i行结束索引

                int iStartNext = iStart + viCount;//第i+1行开始的索引
                int viCountNext = viCount + 1;//第i+1行顶点数
                int iEndNext = iStartNext + viCountNext - 1;//第i+1行结束的索引
                //前面的四边形
                for (int j = 0; j < viCount - 1; j++) {
                    int index0 = iStart + j;//四边形的四个顶点索引
                    int index1 = index0 + 1;
                    int index2 = iStartNext + j;
                    int index3 = index2 + 1;
                    alFaceIndex.add(index0);
                    alFaceIndex.add(index2);
                    alFaceIndex.add(index3);//加入前面的四边形
                    alFaceIndex.add(index0);
                    alFaceIndex.add(index3);
                    alFaceIndex.add(index1);
                }// j
                alFaceIndex.add(iEnd);
                alFaceIndex.add(iEndNext - 1);
                alFaceIndex.add(iEndNext); //最后一个三角形
                vnCount += viCount;//第i行前所有顶点数的和
                if (i == n - 1) {//如果是每个大三角形的最后一次循环，将下一列的顶点个数也加上
                    vnCount += viCountNext;
                }
            }// i
        }// k

        //计算卷绕顶点
        float[] vertices = VectorUtil.cullVertex(alVertix, alFaceIndex);//只计算顶点
        float[] normals = vertices;//顶点就是法向量

        //纹理
        //正20面体纹理坐标数据初始化
        ArrayList<Float> alST20 = new ArrayList<Float>();//正20面体的纹理坐标列表（未卷绕）
        ArrayList<Integer> alTexIndex20 = new ArrayList<Integer>();//正20面体组织成面的纹理坐标的索引值列表（按逆时针卷绕）
        //正20面体纹理坐标
        float sSpan = 1 / 5.5f;//每个纹理三角形的边长
        float tSpan = 1 / 3.0f;//每个纹理三角形的高
        //按正二十面体的平面展开图计算纹理坐标
        for (int i = 0; i < 5; i++) {
            alST20.add(sSpan + sSpan * i);
            alST20.add(0f);
        }
        for (int i = 0; i < 6; i++) {
            alST20.add(sSpan / 2 + sSpan * i);
            alST20.add(tSpan);
        }
        for (int i = 0; i < 6; i++) {
            alST20.add(sSpan * i);
            alST20.add(tSpan * 2);
        }
        for (int i = 0; i < 5; i++) {
            alST20.add(sSpan / 2 + sSpan * i);
            alST20.add(tSpan * 3);
        }
        //正20面体索引
        initAlTexIndex20(alTexIndex20);

        //计算卷绕纹理坐标
        float[] st20 = VectorUtil.cullTexCoor(alST20, alTexIndex20);//只计算纹理坐标
        ArrayList<Float> alST = new ArrayList<Float>();//原纹理坐标列表（未卷绕）
        for (int k = 0; k < st20.length; k += 6) {
            float[] st1 = new float[]{st20[k + 0], st20[k + 1], 0};//三角形的纹理坐标
            float[] st2 = new float[]{st20[k + 2], st20[k + 3], 0};
            float[] st3 = new float[]{st20[k + 4], st20[k + 5], 0};
            for (int i = 0; i <= n; i++) {
                float[] stiStart = VectorUtil.devideLine(st1, st2, n, i);
                float[] stiEnd = VectorUtil.devideLine(st1, st3, n, i);
                for (int j = 0; j <= i; j++) {
                    float[] sti = VectorUtil.devideLine(stiStart, stiEnd, i, j);
                    //将纹理坐标加入列表
                    alST.add(sti[0]);
                    alST.add(sti[1]);
                }
            }
        }
        //计算卷绕后纹理坐标
        float[] textures = VectorUtil.cullTexCoor(alST, alFaceIndex);
        init(vertices, textures, normals);
    }

    public void initAlVertix20(ArrayList<Float> alVertix20, float aHalf, float bHalf) {

        alVertix20.add(0f);
        alVertix20.add(aHalf);
        alVertix20.add(-bHalf);//顶正棱锥顶点

        alVertix20.add(0f);
        alVertix20.add(aHalf);
        alVertix20.add(bHalf);//棱柱上的点
        alVertix20.add(aHalf);
        alVertix20.add(bHalf);
        alVertix20.add(0f);
        alVertix20.add(bHalf);
        alVertix20.add(0f);
        alVertix20.add(-aHalf);
        alVertix20.add(-bHalf);
        alVertix20.add(0f);
        alVertix20.add(-aHalf);
        alVertix20.add(-aHalf);
        alVertix20.add(bHalf);
        alVertix20.add(0f);

        alVertix20.add(-bHalf);
        alVertix20.add(0f);
        alVertix20.add(aHalf);
        alVertix20.add(bHalf);
        alVertix20.add(0f);
        alVertix20.add(aHalf);
        alVertix20.add(aHalf);
        alVertix20.add(-bHalf);
        alVertix20.add(0f);
        alVertix20.add(0f);
        alVertix20.add(-aHalf);
        alVertix20.add(-bHalf);
        alVertix20.add(-aHalf);
        alVertix20.add(-bHalf);
        alVertix20.add(0f);

        alVertix20.add(0f);
        alVertix20.add(-aHalf);
        alVertix20.add(bHalf);//底棱锥顶点

    }

    public void initAlFaceIndex20(ArrayList<Integer> alFaceIndex20) { //初始化正二十面体的顶点索引数据

        alFaceIndex20.add(0);
        alFaceIndex20.add(1);
        alFaceIndex20.add(2);
        alFaceIndex20.add(0);
        alFaceIndex20.add(2);
        alFaceIndex20.add(3);
        alFaceIndex20.add(0);
        alFaceIndex20.add(3);
        alFaceIndex20.add(4);
        alFaceIndex20.add(0);
        alFaceIndex20.add(4);
        alFaceIndex20.add(5);
        alFaceIndex20.add(0);
        alFaceIndex20.add(5);
        alFaceIndex20.add(1);

        alFaceIndex20.add(1);
        alFaceIndex20.add(6);
        alFaceIndex20.add(7);
        alFaceIndex20.add(1);
        alFaceIndex20.add(7);
        alFaceIndex20.add(2);
        alFaceIndex20.add(2);
        alFaceIndex20.add(7);
        alFaceIndex20.add(8);
        alFaceIndex20.add(2);
        alFaceIndex20.add(8);
        alFaceIndex20.add(3);
        alFaceIndex20.add(3);
        alFaceIndex20.add(8);
        alFaceIndex20.add(9);
        alFaceIndex20.add(3);
        alFaceIndex20.add(9);
        alFaceIndex20.add(4);
        alFaceIndex20.add(4);
        alFaceIndex20.add(9);
        alFaceIndex20.add(10);
        alFaceIndex20.add(4);
        alFaceIndex20.add(10);
        alFaceIndex20.add(5);
        alFaceIndex20.add(5);
        alFaceIndex20.add(10);
        alFaceIndex20.add(6);
        alFaceIndex20.add(5);
        alFaceIndex20.add(6);
        alFaceIndex20.add(1);

        alFaceIndex20.add(6);
        alFaceIndex20.add(11);
        alFaceIndex20.add(7);
        alFaceIndex20.add(7);
        alFaceIndex20.add(11);
        alFaceIndex20.add(8);
        alFaceIndex20.add(8);
        alFaceIndex20.add(11);
        alFaceIndex20.add(9);
        alFaceIndex20.add(9);
        alFaceIndex20.add(11);
        alFaceIndex20.add(10);
        alFaceIndex20.add(10);
        alFaceIndex20.add(11);
        alFaceIndex20.add(6);
    }

    public void initAlTexIndex20(ArrayList<Integer> alTexIndex20) //初始化顶点纹理索引数据
    {
        alTexIndex20.add(0);
        alTexIndex20.add(5);
        alTexIndex20.add(6);
        alTexIndex20.add(1);
        alTexIndex20.add(6);
        alTexIndex20.add(7);
        alTexIndex20.add(2);
        alTexIndex20.add(7);
        alTexIndex20.add(8);
        alTexIndex20.add(3);
        alTexIndex20.add(8);
        alTexIndex20.add(9);
        alTexIndex20.add(4);
        alTexIndex20.add(9);
        alTexIndex20.add(10);

        alTexIndex20.add(5);
        alTexIndex20.add(11);
        alTexIndex20.add(12);
        alTexIndex20.add(5);
        alTexIndex20.add(12);
        alTexIndex20.add(6);
        alTexIndex20.add(6);
        alTexIndex20.add(12);
        alTexIndex20.add(13);
        alTexIndex20.add(6);
        alTexIndex20.add(13);
        alTexIndex20.add(7);
        alTexIndex20.add(7);
        alTexIndex20.add(13);
        alTexIndex20.add(14);
        alTexIndex20.add(7);
        alTexIndex20.add(14);
        alTexIndex20.add(8);
        alTexIndex20.add(8);
        alTexIndex20.add(14);
        alTexIndex20.add(15);
        alTexIndex20.add(8);
        alTexIndex20.add(15);
        alTexIndex20.add(9);
        alTexIndex20.add(9);
        alTexIndex20.add(15);
        alTexIndex20.add(16);
        alTexIndex20.add(9);
        alTexIndex20.add(16);
        alTexIndex20.add(10);

        alTexIndex20.add(11);
        alTexIndex20.add(17);
        alTexIndex20.add(12);
        alTexIndex20.add(12);
        alTexIndex20.add(18);
        alTexIndex20.add(13);
        alTexIndex20.add(13);
        alTexIndex20.add(19);
        alTexIndex20.add(14);
        alTexIndex20.add(14);
        alTexIndex20.add(20);
        alTexIndex20.add(15);
        alTexIndex20.add(15);
        alTexIndex20.add(21);
        alTexIndex20.add(16);

    }


}
