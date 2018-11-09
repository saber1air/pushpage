package com.deliver.util;



import java.util.HashMap;
import java.util.Map;
import java.util.Set;
 

public class CosDistance {
 
    // 求余弦相似度
    public double sim(double [] d1 , double [] d2) {
        double result = 0;
        result = pointMulti(d1, d2 ) / sqrtMulti(d1, d2);
        return result;
    }
 
    private double sqrtMulti(double [] d1 , double [] d2) {
        double result = 0;
        result = squares(d1, d2);
        result = Math.sqrt(result);
        return result;
    }
 
    
    
    // 求平方和
    private double squares(double [] d1 , double [] d2) {
        double result1 = 0;
        double result2 = 0;
        
        for ( double tempd1 : d1 ){
        	result1 += tempd1 * tempd1;
        }
        for ( double tempd2 : d2 ){
        	result2 += tempd2 * tempd2;
        }

        return result1 * result2;
    }
 
    // 点乘法
    private double pointMulti(double [] d1 , double [] d2) {
    	double result = 0;        
        
        for ( int i = 0; i < d1.length; i++ ){
        	result += d1[i] * d2[i];
        }

        return result;
    }

 
    public static void main(String[] args) {
//        double[] d1 = {1.3, 44.9};
//        double[] d2 = {1.666, 44.9};
        double[] d1 = {1, 1, 1, 1, 1, 1};
        double[] d2 = {1, 1, 0, 0, 1, 1};
        CosDistance similarity = new CosDistance();
        System.out.println(similarity.sim(d1, d2));
    }
 
}