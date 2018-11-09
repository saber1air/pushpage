package com.deliver.util;

import java.util.Set;

/**
 * Created by pdl on 2018/9/18.
 */
public interface FlatSessionStore {
    public Boolean removeAttribute(String key);
    public String getAttribute(String key);
    public void setAttribute(String key, Object value);
    public void setAttribute(String key, Object value,Long ttl);
}
