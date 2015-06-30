package com.fanqielaile.toms.support.security;

/**
 * @author wdy
 */
public interface ReloadableRequestMapResolver extends RequestMapResolver {
    public void refresh();
}
