package me.aifaq.commons.lang.timer;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:00 2019/3/29
 */
public class StepTimeWindowTest {

    StepTimeWindow stepTimeWindow = new StepTimeWindow(TimeUnit.SECONDS, 1, 2, 5);

    @Test
    public void testAll() {
        final long currentNanoTime = System.nanoTime();

        stepTimeWindow.setExpireNanoTime(currentNanoTime);

        stepTimeWindow.renew();

        Assert.assertTrue(stepTimeWindow.isInTimeWindow());

        Assert.assertEquals(stepTimeWindow.getExpireNanoTime(), currentNanoTime + TimeUnit.SECONDS.toNanos(1));

        stepTimeWindow.renew();

        Assert.assertEquals(stepTimeWindow.getExpireNanoTime(), currentNanoTime + TimeUnit.SECONDS.toNanos(3));

        stepTimeWindow.renew();

        Assert.assertEquals(stepTimeWindow.getExpireNanoTime(), currentNanoTime + TimeUnit.SECONDS.toNanos(8));

        System.out.println(stepTimeWindow);

        stepTimeWindow.renew();

        Assert.assertEquals(stepTimeWindow.getExpireNanoTime(), currentNanoTime + TimeUnit.SECONDS.toNanos(13));

        stepTimeWindow.reset();

        Assert.assertEquals(stepTimeWindow.getExpireNanoTime(), 0);

        System.out.println(stepTimeWindow);
    }
}
