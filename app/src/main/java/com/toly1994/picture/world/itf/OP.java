package com.toly1994.picture.world.itf;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/13/013:19:27<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：操作接口
 */
public interface OP<T> {
    /**
     * 添加
     * @param ts 若干对象
     */
    void add(T... ts);

    /**
     * 根据id移除元素
     * @param id 索引
     */
    void remove(int id);
}
