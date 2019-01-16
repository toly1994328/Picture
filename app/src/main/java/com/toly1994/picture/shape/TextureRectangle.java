package com.toly1994.picture.shape;

import android.content.Context;
import android.opengl.GLES20;

import com.toly1994.picture.utils.GLUtil;
import com.toly1994.picture.world.abs.RendererAble;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:20:09<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：贴图测试
 */
public class TextureRectangle extends RendererAble {
    private static final String TAG = "Triangle";
    private Context mContext;
    private int mTId;

    private int mProgram;//OpenGL ES 程序
    private int mPositionHandle;//位置句柄
    private int mColorHandle;//颜色句柄
    private int muMVPMatrixHandle;//顶点变换矩阵句柄

    private FloatBuffer vertexBuffer;//顶点缓冲
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 3*4=12
    static final int COORDS_PER_VERTEX = 3;//数组中每个顶点的坐标数
    private final int vertexCount = vertexs.length / COORDS_PER_VERTEX;//顶点个数

    float s = 1f;//s纹理坐标系数
    float t = 1f;//t纹理坐标系数
    static final float UNIT_SIZE = 0.3f;

    static float vertexs[] = {   //以逆时针顺序

            -1, 1, 0,
            -1, -1, 0,
            1, -1, 0,

            1, -1, 0,
            1, 1, 0,
            -1, 1, 0

//            //F面
//            -1, -1, 1,
//            1, -1, 1,
//            1, -1, -1,
//
//            1, -1, -1,
//            -1, -1, -1,
//            -1, -1, 1,

    };

    private final float[] textureCoo = {
            0, 0,
            0, t,
            s, t,

            s, t,
            s, 0,
            0, 0
    };

//    //索引数组
//    private short[] idx = {
//            1, 2, 3,
//            0, 1, 3,
//    };

    private ShortBuffer idxBuffer;
    private FloatBuffer mTextureCooBuffer;

    public TextureRectangle(Context context, int tId) {
        super(context);
        mContext = context;
        mTId = tId;
        //初始化顶点字节缓冲区
        bufferData();//缓冲顶点数据
        initProgram();//初始化OpenGL ES 程序
    }


    /**
     * 缓冲数据
     */
    private void bufferData() {
        vertexBuffer = GLUtil.getFloatBuffer(vertexs);
        mTextureCooBuffer = GLUtil.getFloatBuffer(textureCoo);
//        idxBuffer = GLUtil.getShortBuffer(idx);
    }

    /**
     * 初始化OpenGL ES 程序
     */
    private void initProgram() {
        ////顶点着色
        int vertexShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_VERTEX_SHADER, "rect_texture.vert");
        //片元着色
        int fragmentShader = GLUtil.loadShaderAssets(mContext,
                GLES20.GL_FRAGMENT_SHADER, "rect_texture.frag");

        mProgram = GLES20.glCreateProgram();//创建空的OpenGL ES 程序
        GLES20.glAttachShader(mProgram, vertexShader);//加入顶点着色器
        GLES20.glAttachShader(mProgram, fragmentShader);//加入片元着色器
        GLES20.glLinkProgram(mProgram);//创建可执行的OpenGL ES项目

        //获取顶点着色器的vPosition成员的句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "vCoordinate");
        //获取程序中总变换矩阵uMVPMatrix成员的句柄
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }


    public void draw(float[] mvpMatrix) {
        // 将程序添加到OpenGL ES环境中
        GLES20.glUseProgram(mProgram);
        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //启用三角形顶点颜色的句柄
        GLES20.glEnableVertexAttribArray(mColorHandle);

        //顶点矩阵变换
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0);

        //准备三角顶点坐标数据
        GLES20.glVertexAttribPointer(
                mPositionHandle,//int indx, 索引
                COORDS_PER_VERTEX,//int size,大小
                GLES20.GL_FLOAT,//int type,类型
                false,//boolean normalized,//是否标准化
                vertexStride,// int stride,//跨度
                vertexBuffer);// java.nio.Buffer ptr//缓冲

        //准备三角顶点颜色数据
        GLES20.glVertexAttribPointer(
                mColorHandle,
                2,
                GLES20.GL_FLOAT,
                false,
                2 * 4,
                mTextureCooBuffer);

        //绑定纹理
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTId);
//        GLES20.glDrawElements(GLES20.GL_TRIANGLES, idx.length, GLES20.GL_UNSIGNED_SHORT, idxBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        //绘制
        //禁用顶点数组:
        //禁用index指定的通用顶点属性数组。
        // 默认情况下，禁用所有客户端功能，包括所有通用顶点属性数组。
        // 如果启用，将访问通用顶点属性数组中的值，
        // 并在调用顶点数组命令（如glDrawArrays或glDrawElements）时用于呈现
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

