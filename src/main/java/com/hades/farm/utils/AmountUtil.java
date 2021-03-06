package com.hades.farm.utils;

import com.hades.farm.enums.GoodsType;

import java.math.BigDecimal;

/**
 * Created by zhengzl on 2018/3/19.
 */
public class AmountUtil {
    //卖出一只鸭或蛋获得的积分
    public static BigDecimal integralCalculate(int type){
        BigDecimal integral = new BigDecimal("1");
       if(type == GoodsType.EGG.getType()){
           integral = integral.multiply(Constant.EGG_INTEGRAL_RATE).multiply(Constant.EGG_PRICE);
       }else if(type == GoodsType.DUCK.getType()){
           integral = integral.multiply(Constant.DUCK_INTEGRAL_RATE).multiply(Constant.DUCK_PRICE);
       }
        return integral;
    }
    //卖出num鸭或num个蛋的净利润
    public static BigDecimal profitCalculate(int type,int num){
        BigDecimal profit = new BigDecimal("1");
        if(type == GoodsType.EGG.getType()){
            profit = new BigDecimal(Constant.PRODUCE_EGGS).multiply(Constant.EGG_PRICE);//3只蛋总收入
            profit = profit.multiply(new BigDecimal("1").subtract(Constant.SELL_EGG_RATE.add(Constant.EGG_INTEGRAL_RATE)));//减手续费,积分费
            profit = profit.subtract(Constant.DUCK_PRICE);//减鸭的价格
            profit = profit.subtract(new BigDecimal(Constant.BREEDING_DUCK_CYC).multiply(Constant.FEED_PRICE));//减饲料费用
            profit = profit.divide(new BigDecimal(Constant.PRODUCE_EGGS)).setScale(5, BigDecimal.ROUND_DOWN);//除以总共生产的蛋的数量得到单个利润(不考虑死掉的)
            profit = profit.multiply(new BigDecimal(num)).setScale(2,BigDecimal.ROUND_DOWN);
        }else if(type == GoodsType.DUCK.getType()){
            profit = Constant.DUCK_PRICE;//总收入
            profit = profit.multiply(new BigDecimal("1").subtract(Constant.SELL_DUCK_RATE.add(Constant.DUCK_INTEGRAL_RATE)));//减手续费积分费
            profit = profit.subtract(Constant.EGG_PRICE); //减去蛋的价格
            profit = profit.multiply(new BigDecimal(num)).setScale(2, BigDecimal.ROUND_DOWN);//乘以个数
        }
        return profit;
    }
}
