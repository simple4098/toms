package com.fanqielaile.toms.support.event;

import org.springframework.context.ApplicationEvent;


/**
 * Created by wangdayin on 2015/6/5.
 */
public class TomsApplicationEvent extends ApplicationEvent {

    private Object objsource;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never <code>null</code>)
     */
    public TomsApplicationEvent(Object source) {
        super(source);
        this.objsource = source;
    }

    public Object getObjsource() {
        return objsource;
    }

    public void setObjsource(Object objsource) {
        this.objsource = objsource;
    }
}
