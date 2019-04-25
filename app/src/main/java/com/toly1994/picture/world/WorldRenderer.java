package com.toly1994.picture.world;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.toly1994.picture.R;
import com.toly1994.picture.shape.ShapeSet;
import com.toly1994.picture.shape.threeD.Coo3d;
import com.toly1994.picture.shape.threeD.ObjShape;
import com.toly1994.picture.utils.GLState;
import com.toly1994.picture.utils.GLUtil;
import com.toly1994.picture.utils.MatrixStack;
import com.toly1994.picture.world.abs.RenderAble;
import com.toly1994.picture.world.bean.RepeatType;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/9 0009:18:56<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：GL世界渲染类
 */
public class WorldRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "GLRenderer";

    private RenderAble mWorldShape;

    private World mWorld;
    private Context mContext;


    public WorldRenderer(World world) {
        mWorld = world;
        mContext = this.getWorld().getContext();
    }

    private int currDeg = 0;

    public World getWorld() {
        return mWorld;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);//rgba

        int tId = GLUtil.loadTexture(mContext, R.mipmap.mian_a, RepeatType.NONE);
        int tId1 = GLUtil.loadTexture(mContext, R.mipmap.menu_bg, RepeatType.REPEAT);
        int tId2 = GLUtil.loadTexture(mContext, R.mipmap.mian_a, RepeatType.REPEAT);
        int tEarth = GLUtil.loadTexture(mContext, R.mipmap.earth, RepeatType.REPEAT);

        int tIdA = GLUtil.loadTexture(mContext, R.mipmap.wall_a, RepeatType.REPEAT);
        int tIdB = GLUtil.loadTexture(mContext, R.mipmap.wall_b, RepeatType.REPEAT);
        int tIdC = GLUtil.loadTexture(mContext, R.mipmap.wall_c, RepeatType.REPEAT);
        int tIdD = GLUtil.loadTexture(mContext, R.mipmap.wall_d, RepeatType.REPEAT);
        int tIdE = GLUtil.loadTexture(mContext, R.mipmap.wall_e, RepeatType.REPEAT);
        int tIdF = GLUtil.loadTexture(mContext, R.mipmap.wall_f, RepeatType.REPEAT);

        //魔方
//        mWorldShape = new MoFangShape(this.getWorld().getContext());

        //圆
//        mWorldShape = new CircleFanEvn(mContext, tId1, 1f, 36);
//        mWorldShape = new CircleFanSimple(mContext, 1f, 36, tId);
//        mWorldShape = new CircleTrisEvn(mContext, tId, 1f, 36);//三点三角形 圆

//        mWorldShape = new CylinderSideEvn(mContext, tId, 0.5f, 3.14f, 36);//圆柱侧面
//        mWorldShape = new ConeSideEvn(mContext, tId, 0.5f, 3.14f * 0.8f, 36);//圆锥侧面
//        mWorldShape = new ConeSideFanEvn(mContext, tId, 0.5f, 3.14f * 0.8f, 36);//圆锥侧面Fan绘制

        //矩形
//        mWorldShape = new TextureRectangle(mContext, tId);
//        mWorldShape = new RectangleEvn(mContext, tId, 1, 1, 0);
//        mWorldShape = new MatrixRectangle(mContext);

        //3D坐标系
//        mWorldShape = new Coo3d(mContext);

        //立方
//        mWorldShape = new Cube3d(mContext, 1f, 1f, 1f,
//                new int[]{tIdA, tIdB, tIdC, tIdD, tIdE, tIdF});

        //圆柱
//        mWorldShape = new Cylinder(mContext,
//                0.5f, (float) (2 * Math.PI * 0.5f), 36,
//                new int[]{tId2, tId2, tId});

        //圆锥类
//        mWorldShape = new Cone(mContext,
//                0.5f, (float) (2 * Math.PI * 0.5f), 36,
//                new int[]{tId2, tId});

        //球
//        mWorldShape = new Ball_M(mContext);
//        mWorldShape = new Ball(mContext);
//        mWorldShape = new Regular20Evn(mContext, tEarth, 1, 0.8f, 36);//地球


        //圆环
//        mWorldShape = new TorusEvn(mContext, tId1, 1, 0.7f, 10, 36);

        //螺线管
//        mWorldShape = new SpringEvn(mContext, tId1, 0.5f, 0.4f, 2, 5, 10, 36 * 5);

        //初音
//        mWorldShape = new ObjShape(mContext);

        //组合
        ShapeSet shapeSet = new ShapeSet(mContext);
        shapeSet.add(new Coo3d(mContext));
        shapeSet.add(new ObjShape(mContext));
        mWorldShape = shapeSet;


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);//GL视口

        float ratio = (float) width / height;

        MatrixStack.frustum(
                -ratio, ratio, -1, 1,
                3f, 9);

        MatrixStack.lookAt(2, 2, -6,
                0f, 0f, 0f,
                0f, 1.0f, 0.0f);

        MatrixStack.reset();
        GLState.setEviLight(1f, 1f, 1f, 1.0f);
    }

    /**
     * 此方法会不断执行 {@link-GLSurfaceView.RENDERMODE_CONTINUOUSLY}
     * 此方法执行一次 {@link-GLSurfaceView.RENDERMODE_WHEN_DIRTY}
     *
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {

        float[] lightPos = mWorld.getLightPos();

        GLState.setLightLocation(lightPos[0], lightPos[1], lightPos[2]);


        //清除颜色缓存和深度缓存
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

//        MatrixStack.save();
//        MatrixStack.rotate(currDeg, 0, 1, 0);
//        MatrixStack.scale(0.01f,0.01f,0.01f);
//        mWorldShape.draw(MatrixStack.peek());
//        MatrixStack.restore();

        MatrixStack.save();
        MatrixStack.rotate(currDeg, 0, 1, 0);
        mWorldShape.draw(MatrixStack.peek());
        MatrixStack.restore();

//        MatrixStack.save();
//        MatrixStack.translate(0, 0, 1f);
//        MatrixStack.rotate(currDeg, -1.5f, 0, 0);
//        mWorldShape.draw(MatrixStack.peek());
//        MatrixStack.restore();
//
//        MatrixStack.save();
//        MatrixStack.translate(1.5f, 0, 0);
//        MatrixStack.rotate(currDeg, 0, 1, 0);
//        MatrixStack.scale(0.5f,1,0.5f);
//        mWorldShape.draw(MatrixStack.peek());
//        MatrixStack.restore();


        currDeg++;
        if (currDeg == 360) {
            currDeg = 0;
        }

        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }

}
