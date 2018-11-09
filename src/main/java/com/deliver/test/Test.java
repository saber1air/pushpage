package com.deliver.test;

import net.sf.json.JSONArray;

/**
 * Created by pdl on 2018/9/29.
 */
public class Test {
    public static void main(String[] args) {
        double[] feature = new double[]{1,2,3,4,5};
        JSONArray jsonObject = JSONArray.fromObject(feature);
        String jsonFeature = jsonObject.toString();
        System.out.println(jsonFeature);
    }

}
