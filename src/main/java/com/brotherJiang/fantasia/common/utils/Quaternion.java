/*
 * Copyright (c) 2021/8/3 下午12:02.
 * Author:MCBBS 的 轻光233 https://www.mcbbs.net/home.php?mod=space&uid=2327606
 * Thanks for your permission!
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/bj_magic2.
 */

package com.brotherJiang.fantasia.common.utils;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.text.DecimalFormat;

public class Quaternion {

    double re;
    double i;
    double j;
    double k;

    public Quaternion(){}

    public Quaternion(double re, double i, double j, double k) {
        this.re = re;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public Quaternion(double re, Vector3d v) {
        this.re = re;
        this.i = v.getX();
        this.j = v.getY();
        this.k = v.getZ();
    }

    public double getRe() {
        return re;
    }

    public double getI() {
        return i;
    }

    public double getJ() {
        return j;
    }

    public double getK() {
        return k;
    }

    public Vector3d getVectorPart(){
        return new Vector3d(i,j,k);
    }

    public Quaternion add(Quaternion other){
        return new Quaternion(re + other.re,getVectorPart().add(other.getVectorPart()));
    }

    public Quaternion minus(Quaternion other){
        return new Quaternion(re + other.re,getVectorPart().subtract(other.getVectorPart()));
    }

    //数乘
    public Quaternion scale(double m){
        return new Quaternion(re*m,i*m,j*m,k*m);
    }

    //四元数乘法的向量实现版本(好记)
    public Quaternion multVecImpl(Quaternion other){
        return new Quaternion(
                re * other.re - getVectorPart().dotProduct(other.getVectorPart()),
                other.getVectorPart().scale(re).add(getVectorPart().scale(other.re)).add(getVectorPart().crossProduct(other.getVectorPart()))
        );
    }

    //四元数乘法的定义实现版本((计算机)算的快)
    public Quaternion multPolImpl(Quaternion other){
        double a = re;
        double b = i;
        double c = j;
        double d = k;
        double e = other.re;
        double f = other.i;
        double g = other.j;
        double h = other.k;
        return new Quaternion(
                a*e-b*f-c*g-d*h,
                a*f+b*e+c*h-d*g,
                a*g+c*e+d*f-b*h,
                a*h+d*e+b*g-c*f
        );
    }

    public double length(){
        return Math.sqrt(lengthSquare());
    }

    public double lengthSquare(){
        return re*re + i*i + j*j + k*k;
    }

    //此四元数的逆
    public Quaternion anti(){
        return conjugate().scale(1.0/lengthSquare());
    }

    //此四元数的共轭四元数
    public Quaternion conjugate(){
        return new Quaternion(re,-i,-j,-k);
    }

    //此四元数的单位四元数
    public Quaternion unit(){
        return scale(1.0/length());
    }

    static DecimalFormat df = new DecimalFormat("0.00");

    public String toString(){
        return df.format(re) + "+" + df.format(i) + "i+" + df.format(j) + "j+" + df.format(k) + "k";
    }
    /**
     * **/
    public static Vector3d moveQ(Vector3d startV, Vector3d targetV, float maxAngle){

        double now = startV.dotProduct(targetV) / (startV.length()*targetV.length());
        float maxCos = MathHelper.cos((float) (maxAngle*Math.PI/180));
        float maxSin = MathHelper.sin((float) (maxAngle*Math.PI/180));
        if(now >= maxCos){
            return targetV;
        }
        Quaternion start = new Quaternion(0,startV);
        Quaternion rotationAxle = new Quaternion(0,startV.crossProduct(targetV)).unit();
        Quaternion rotate = rotationAxle.scale(maxSin);
        rotate.re = maxCos;

        return rotate.multPolImpl(start).multPolImpl(rotate.conjugate()).getVectorPart();

    }
}