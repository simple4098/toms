package com.fanqielaile.toms.support.listener;

import com.fanqielaile.toms.support.thread.SynchronousThread;
import com.fanqielaile.toms.support.util.ResourceBundleUtil;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/3/9
 * @version: v1.0.0
 */
public class TomsMsgListener extends ContextLoaderListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
        int size = ResourceBundleUtil.getInt("threadNum");
        for (int i = 0; i < size; i++) {
            SynchronousThread thread= new SynchronousThread(applicationContext,i);
            executor.execute(thread);
        }
    }
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }
}
