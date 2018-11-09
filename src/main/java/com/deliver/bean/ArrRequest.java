package com.deliver.bean;

import java.util.List;

/**
 * Created by pdl on 2018/9/18.
 */
public class ArrRequest {
    List<String> feature;
    int delivertype;

    public List<String> getFeature() {
        return feature;
    }

    public void setFeature(List<String> feature) {
        this.feature = feature;
    }

    public int getDelivertype() {
        return delivertype;
    }

    public void setDelivertype(int delivertype) {
        this.delivertype = delivertype;
    }
}
