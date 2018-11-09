package com.deliver.mapbody;

/**
 * Created by pdl on 2018/9/21.
 */
public class DeliverParam {
    private double[] feature1;
    private double[] feature2;
    private Integer delivertype;
    private Integer schoolID;

    public double[] getFeature1() {
        return feature1;
    }

    public void setFeature1(double[] feature1) {
        this.feature1 = feature1;
    }

    public double[] getFeature2() {
        return feature2;
    }

    public void setFeature2(double[] feature2) {
        this.feature2 = feature2;
    }

    public Integer getDelivertype() {
        return delivertype;
    }

    public void setDelivertype(Integer delivertype) {
        this.delivertype = delivertype;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }
}
