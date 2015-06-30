package com.fanqielaile.toms.support.security;

import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

/**
 * @author wdy
 */
public interface RequestMapResolver {
    public Map<String, Collection<ConfigAttribute>> requestMap();
}
