package me.aifaq.commons.lang.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程的Factory类，以便自定义线程名
 */
public class NamedThreadFactory implements ThreadFactory {
    static final AtomicLong poolNumber = new AtomicLong(1);
    final ThreadGroup group;
    final AtomicLong threadNumber = new AtomicLong(1);
    final String namePrefix;


    public NamedThreadFactory() {
        final SecurityManager s = System.getSecurityManager();
        this.group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
    }


    public NamedThreadFactory(final String name) {
        final SecurityManager s = System.getSecurityManager();
        this.group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = generateNamePrefix(name);
    }


    public static String generateNamePrefix(final String name) {
        return name + "-" + poolNumber.getAndIncrement() + "-thread-";
    }


    @Override
    public Thread newThread(final Runnable r) {
        final Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}