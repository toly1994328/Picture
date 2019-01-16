package com.toly1994.picture.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.toly1994.picture.world.bean.RepeatType;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;


/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/10 0010:10:58<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：OpenGL ES 辅助工具
 */
public class GLUtil {

    //从sh脚本中加载shader内容的方法
    public static int loadShaderAssets(Context ctx, int type, String name) {
        String result = null;
        try {
            InputStream in = ctx.getAssets().open(name);
            int ch = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = in.read()) != -1) {
                baos.write(ch);
            }
            byte[] buff = baos.toByteArray();
            baos.close();
            in.close();
            result = new String(buff, "UTF-8");
            result = result.replaceAll("\\r\\n", "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadShader(type, result);
    }


    /**
     * 加载作色器
     *
     * @param type       着色器类型    顶点着色 {@link GLES20.GL_VERTEX_SHADER}
     *                   片元着色 {@link GLES20.GL_FRAGMENT_SHADER}
     * @param shaderCode 着色代码
     * @return 作色器
     */
    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);//创建着色器
        if (shader == 0) {//加载失败直接返回
            return 0;
        }
        GLES20.glShaderSource(shader, shaderCode);//加载着色器源代码
        GLES20.glCompileShader(shader);//编译

        return checkCompile(type, shader);
    }

    /**
     * 检查shader代码是否编译成功
     *
     * @param type   着色器类型
     * @param shader 着色器
     * @return 着色器
     */
    private static int checkCompile(int type, int shader) {
        int[] compiled = new int[1];//存放编译成功shader数量的数组
        //获取Shader的编译情况
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {//若编译失败则显示错误日志并
            Log.e("ES20_COMPILE_ERROR",
                    "Could not compile shader " + type + ":" + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);//删除此shader
            shader = 0;
        }
        return shader;
    }

    /////////////////////---------缓冲数据工具----------------------

    /**
     * short数组缓冲数据
     *
     * @param vertexs short 数组
     * @return 获取short缓冲数据
     */
    public static ShortBuffer getShortBuffer(short[] vertexs) {
        ShortBuffer buffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(vertexs.length * 2);
        qbb.order(ByteOrder.nativeOrder());
        buffer = qbb.asShortBuffer();
        buffer.put(vertexs);
        buffer.position(0);
        return buffer;
    }

    /**
     * @param vertexs int数组
     * @return 获取整形缓冲数据
     */
    public static IntBuffer getIntBuffer(int[] vertexs) {
        IntBuffer buffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(vertexs.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        buffer = qbb.asIntBuffer();
        buffer.put(vertexs);
        buffer.position(0);
        return buffer;
    }

    /**
     * float数组缓冲数据
     *
     * @param vertexs 顶点
     * @return 获取浮点形缓冲数据
     */
    public static FloatBuffer getFloatBuffer(float[] vertexs) {
        FloatBuffer buffer;
        ///每个浮点数:坐标个数* 4字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(vertexs.length * 4);
        //使用本机硬件设备的字节顺序
        qbb.order(ByteOrder.nativeOrder());
        // 从字节缓冲区创建浮点缓冲区
        buffer = qbb.asFloatBuffer();
        // 将坐标添加到FloatBuffer
        buffer.put(vertexs);
        //设置缓冲区以读取第一个坐标
        buffer.position(0);
        return buffer;
    }

    /**
     * @param vertexs Byte 数组
     * @return 获取字节型缓冲数据
     */
    public static ByteBuffer getByteBuffer(byte[] vertexs) {
        ByteBuffer buffer = null;
        buffer = ByteBuffer.allocateDirect(vertexs.length);
        buffer.put(vertexs);
        buffer.position(0);
        return buffer;
    }

//---------------纹理加载工具--GLUtil.java-----


    /**
     * 资源id 加载纹理,默认重复方式：RepeatType.REPEAT
     *
     * @param ctx   上下文
     * @param resId 资源id
     * @return 纹理id
     */
    public static int loadTexture(Context ctx, int resId) {
        return loadTexture(ctx, resId, RepeatType.REPEAT);
    }

    /**
     * 资源id 加载纹理
     *
     * @param ctx        上下文
     * @param resId      资源id
     * @param repeatType 重复方式 {@link com.toly1994.picture.world.bean.RepeatType}
     * @return 纹理id
     */
    public static int loadTexture(Context ctx, int resId, RepeatType repeatType) {
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), resId);
        return loadTexture(bitmap, repeatType);
    }

    /**
     * bitmap 加载纹理
     *
     * @param bitmap     bitmap
     * @param repeatType 重复方式 {@link com.toly1994.picture.world.bean.RepeatType}
     * @return 纹理id
     */
    public static int loadTexture(Bitmap bitmap, RepeatType repeatType) {
        //生成纹理ID

        int[] textures = new int[1];
        //(产生的纹理id的数量,纹理id的数组,偏移量)
        GLES20.glGenTextures(1, textures, 0);
        int textureId = textures[0];
        //绑定纹理id
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        //采样方式MIN
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        int wrapS = 0;
        int wrapT = 0;
        switch (repeatType) {
            case NONE:
                wrapS = GLES20.GL_CLAMP_TO_EDGE;
                wrapT = GLES20.GL_CLAMP_TO_EDGE;
                break;
            case REPEAT_X:
                wrapS = GLES20.GL_REPEAT;
                wrapT = GLES20.GL_CLAMP_TO_EDGE;
                break;
            case REPEAT_Y:
                wrapS = GLES20.GL_CLAMP_TO_EDGE;
                wrapT = GLES20.GL_REPEAT;
                break;
            case REPEAT:
                wrapS = GLES20.GL_REPEAT;
                wrapT = GLES20.GL_REPEAT;
                break;
        }

        //设置s轴拉伸方式---重复
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, wrapS);
        //设置t轴拉伸方式---重复
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, wrapT);

        //实际加载纹理(纹理类型,纹理的层次,纹理图像,纹理边框尺寸)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();          //纹理加载成功后释放图片
        return textureId;
    }
    //-------------加载obj点集----------------
    //从obj文件中加载仅携带顶点信息的物体
    public static float[] loadPosInObj(String name, Context ctx) {
        ArrayList<Float> alv = new ArrayList<>();//原始顶点坐标列表
        ArrayList<Float> alvResult = new ArrayList<>();//结果顶点坐标列表
        try {
            InputStream in = ctx.getAssets().open(name);
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String temps = null;
            while ((temps = br.readLine()) != null) {
                String[] tempsa = temps.split("[ ]+");
                if (tempsa[0].trim().equals("v")) {//此行为顶点坐标
                    alv.add(Float.parseFloat(tempsa[1]));
                    alv.add(Float.parseFloat(tempsa[2]));
                    alv.add(Float.parseFloat(tempsa[3]));
                } else if (tempsa[0].trim().equals("f")) {//此行为三角形面
                    int index = Integer.parseInt(tempsa[1].split("/")[0]) - 1;
                    alvResult.add(alv.get(3 * index));
                    alvResult.add(alv.get(3 * index + 1));
                    alvResult.add(alv.get(3 * index + 2));
                    index = Integer.parseInt(tempsa[2].split("/")[0]) - 1;
                    alvResult.add(alv.get(3 * index));
                    alvResult.add(alv.get(3 * index + 1));
                    alvResult.add(alv.get(3 * index + 2));
                    index = Integer.parseInt(tempsa[3].split("/")[0]) - 1;
                    alvResult.add(alv.get(3 * index));
                    alvResult.add(alv.get(3 * index + 1));
                    alvResult.add(alv.get(3 * index + 2));
                }
            }
        } catch (Exception e) {
            Log.d("load error", "load error");
            e.printStackTrace();
        }
        //生成顶点数组
        int size = alvResult.size();
        float[] vXYZ = new float[size];
        for (int i = 0; i < size; i++) {
            vXYZ[i] = alvResult.get(i);
        }
        return vXYZ;
    }
}
