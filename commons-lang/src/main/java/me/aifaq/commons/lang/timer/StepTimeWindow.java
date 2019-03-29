package me.aifaq.commons.lang.timer;

import com.google.common.base.Preconditions;
import me.aifaq.commons.lang.annotation.NotThreadSafe;

import java.util.concurrent.TimeUnit;

/**
 * 支持步长的时间窗口
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:29 2019/3/29
 */
@NotThreadSafe
public class StepTimeWindow {
    private final TimeUnit unit;
    private final long[] steps;

    private long expireNanoTime;

    private int counter = 0;

    public StepTimeWindow(TimeUnit unit, long... steps) {
        Preconditions.checkNotNull(unit);
        Preconditions.checkNotNull(steps);

        this.unit = unit;
        this.steps = steps;
    }

    /**
     * for unit test
     *
     * @param expireNanoTime
     */
    void setExpireNanoTime(long expireNanoTime) {
        this.expireNanoTime = expireNanoTime;
    }

    /**
     * for unit test
     *
     * @return
     */
    long getExpireNanoTime() {
        return this.expireNanoTime;
    }

    /**
     * 续期
     */
    public void renew() {
        if (expireNanoTime == 0) {
            expireNanoTime = System.nanoTime();
        }

        if (counter >= steps.length) {
            expireNanoTime += unit.toNanos(steps[steps.length - 1]);
        } else {
            expireNanoTime += unit.toNanos(steps[counter]);
        }

        ++counter;
    }

    /**
     * 是否在时间窗口中
     *
     * @return
     */
    public boolean isInTimeWindow() {
        return this.expireNanoTime > System.nanoTime();
    }

    /**
     * 重置时间窗口
     */
    public void reset() {
        this.expireNanoTime = 0;
        this.counter = 0;
    }
}
