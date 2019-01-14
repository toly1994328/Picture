package com.toly1994.picture.utils;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/6 0006:20:22<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class EventParser {
    private Context mContext;
    private OnEventListener onEventListener;


    private float mDensity;
    private Orientation mOrientation = Orientation.NO;

    private PointF mDownPos;//按下坐标点
    //移动时坐标点---在此创建对象，避免在move中创建大量对象
    private PointF mMovingPos = new PointF(0, 0);


    private float dy = 0;//下移总量
    private float dx = 0;//右移总量
    private double vMax;//最大速度

    private boolean isDown = false;//是否按下
    private boolean isMove = false;//是否移动


    protected PointF tempP0;//临时点--记录按下时点
    private long lastTimestamp = 0L;//最后一次的时间戳
    private double tempV = 0;

    public EventParser(Context context) {
        mContext = context;
        mDensity = mContext.getResources().getDisplayMetrics().density;
    }


    public void setOnEventListener(OnEventListener onEventListener) {
        this.onEventListener = onEventListener;
    }

    /**
     * 添加自己的事件解析
     *
     * @param event 事件
     */
    public void parseEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下---为p0赋值
                mDownPos = new PointF(event.getX(), event.getY());

                tempP0 = mDownPos;

                lastTimestamp = System.currentTimeMillis();
                isDown = true;

                if (onEventListener != null) {
                    onEventListener.down(mDownPos);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                //移动的那刻的坐标(移动中，不断更新)
                mMovingPos.x = event.getX();
                mMovingPos.y = event.getY();
                //处理速度

                dx = mMovingPos.x - tempP0.x;
                dy = mMovingPos.y - tempP0.y;

                double allS = Math.sqrt(dx * dx + dy * dy);
                double dir = deg((float) Math.acos(dx / allS));


                long curTimestamp = System.currentTimeMillis();
                long t = curTimestamp - lastTimestamp;
                //由于速度是px/ms-->dp/ms
                double v = allS / t * 100;

                orientationHandler(dir);//处理方向

                if (onEventListener != null) {
                    onEventListener.move(v, dy, dx, dy < 0 ? dir : -dir, mOrientation);
                }
                if (Math.abs(dy) > 50 / 3.0 * mDensity) {
                    isMove = true;
                }

                mDownPos = mMovingPos;//更新位置
                lastTimestamp = curTimestamp;//更新时间
                tempV = vMax;
                break;
            case MotionEvent.ACTION_UP:

                if (onEventListener != null) {
                    onEventListener.up(mDownPos, mOrientation);
                }
                reset();//重置工作
                break;
        }
    }

    /**
     * 重置工作
     */
    private void reset() {
        isDown = false;//重置按下状态
        tempP0.x = 0;//重置：tempP0
        tempP0.y = 0;//重置：tempP0
        tempV = 0;//重置速度
        vMax = 0;//最大速度置零
        mOrientation = Orientation.NO;//重置方向
    }

    /**
     * 处理方向
     *
     * @param dir 方向
     */
    private void orientationHandler(double dir) {

        if (dy < 0 && dir > 70 && dir < 110) {
            mOrientation = Orientation.TOP;
        }

        if (dy > 0 && dir > 70 && dir < 110) {
            mOrientation = Orientation.BOTTOM;
        }

        if (dx > 0 && dir < 20) {
            mOrientation = Orientation.RIGHT;
        }

        if (dx < 0 && dir > 160) {
            mOrientation = Orientation.LEFT;
        }

        if (dy < 0 && dir <= 70 && dir >= 20) {
            mOrientation = Orientation.RIGHT_TOP;
        }

        if (dy < 0 && dir >= 110 && dir <= 160) {
            mOrientation = Orientation.LEFT_TOP;
        }

        if (dx > 0 && dy > 0 && dir >= 20 && dir <= 70) {
            mOrientation = Orientation.RIGHT_BOTTOM;
        }

        if (dx < 0 && dy > 0 && dir >= 110 && dir <= 160) {
            mOrientation = Orientation.LEFT_BOTTOM;
        }
    }


    ////////////////////////////////////-----------事件监听回调
    public interface OnEventListener {
        /**
         * 点击
         *
         * @param pointF 点前点
         */
        void down(PointF pointF);

        /**
         * 抬起
         *
         * @param pointF      点前点
         * @param orientation 方向
         */
        void up(PointF pointF, Orientation orientation);

        /**
         * 移动
         *
         * @param v           点前点
         * @param dy          y 位移
         * @param dx          x位移
         * @param dir         角度
         * @param orientation 方向
         */
        void move(double v, float dy, float dx, double dir, Orientation orientation);
    }

    public boolean isDown() {
        return isDown;
    }

    public boolean isMove() {
        return isMove;
    }

    /**
     * 弧度制化为角度制
     *
     * @param rad 弧度
     * @return 角度
     */
    public float deg(float rad) {
        return (float) (rad * 180 / Math.PI);
    }


    /**
     * 两点间距离函数
     *
     * @param p0 第一点
     * @param p1 第二点
     * @return 两点间距离
     */
    public static float disPos2d(PointF p0, PointF p1) {
        return (float) Math.sqrt((p0.x - p1.x) * (p0.x - p1.x) + (p0.y - p1.y) * (p0.y - p1.y));
    }

    /**
     * 两点间距离函数
     *
     * @param dx
     * @param dy
     * @return 两点间距离
     */
    public static float disPos2d(float dx, float dy) {
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 移动方向枚举
     */
    public enum Orientation {
        NO,//无
        TOP, //上
        BOTTOM,//下
        LEFT,//左
        RIGHT,//右
        LEFT_TOP,// 左上
        RIGHT_TOP, // 右上
        LEFT_BOTTOM,//左下
        RIGHT_BOTTOM//右下
    }
}
