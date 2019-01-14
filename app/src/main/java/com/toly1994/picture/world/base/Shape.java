package com.toly1994.picture.world.base;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:17:37<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：形状类
 */
public class Shape implements Cloneable {
    private String id;
    private float[] mVertex;//顶点
    private float[] mColor;//颜色
    private int mDrawType;//绘制类型

    public Shape(float[] vertex, float[] color, int drawType) {
        mVertex = vertex;
        mColor = color;
        mDrawType = drawType;
    }


    /**
     * 移动并创建新图形
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Shape moveAndCreate(float x, float y, float z) {
        Shape clone = clone();
        clone.move(x, y, z);
        return clone;
    }

    /**
     * 仅移动图形
     * @param x
     * @param y
     * @param z
     */
    public void move(float x, float y, float z) {
        for (int i = 0; i < mVertex.length; i++) {
            if (i % 3 == 0) {//x
                mVertex[i] += x;
            }
            if (i % 3 == 1) {//y
                mVertex[i] += y;
            }
            if (i % 3 == 2) {//y
                mVertex[i] += z;
            }
        }
    }

    /**
     * 深拷贝
     * @return 形状副本
     */
    public Shape clone() {
        Shape clone = null;
        try {
            clone = (Shape) super.clone();
            float[] vertex = new float[mVertex.length];
            float[] color = new float[mColor.length];
            System.arraycopy(mVertex, 0, vertex, 0, mVertex.length);
            System.arraycopy(mColor, 0, color, 0, mColor.length);
            clone.mVertex = vertex;
            clone.mColor = color;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public int getCount() {
        return mVertex.length / Cons.DIMENSION_3;
    }

    public float[] getVertex() {
        return mVertex;
    }

    public void setVertex(float[] vertex) {
        mVertex = vertex;
    }

    public float[] getColor() {
        return mColor;
    }

    public void setColor(float[] color) {
        mColor = color;
    }

    public int getDrawType() {
        return mDrawType;
    }

    public void setDrawType(int drawType) {
        mDrawType = drawType;
    }


}
