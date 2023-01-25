package com.dasffafa.fantasia.common.utils.math;
/*
 * Copyright (c) 2021/8/3 下午12:02.
 * Author:MCBBS 的 轻光233 https://www.mcbbs.net/home.php?mod=space&uid=2327606
 * Thanks for your permission!
 */

import com.mojang.math.Vector3f;
import net.minecraft.util.Mth;

import java.text.DecimalFormat;

public class Quaternion {

    float re;
    float i;
    float j;
    float k;
    public Quaternion(float re, float i, float j, float k) {
        this.re = re;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public Quaternion(float re, Vector3f v) {
        this.re = re;
        this.i = v.x();
        this.j = v.y();
        this.k = v.z();
    }

    public float getRe() {
        return re;
    }

    public float getI() {
        return i;
    }

    public float getJ() {
        return j;
    }

    public float getK() {
        return k;
    }

    public Vector3f getVectorPart(){
        return new Vector3f(i,j,k);
    }

    public Quaternion add(Quaternion other){
        Vector3f s = getVectorPart();
        s.add(other.getVectorPart());
        return new Quaternion(re+other.re , s);
    }

    public Quaternion minus(Quaternion other){
        Vector3f s = getVectorPart();
        s.sub(other.getVectorPart());
        return new Quaternion(re + other.re,s);
    }

    //数乘
    public Quaternion scale(float m){
        return new Quaternion(re*m,i*m,j*m,k*m);
    }

    //四元数乘法的向量实现版本(好记)
//    public Quaternion multVecImpl(Quaternion other){
//        // TODO: implement mltVecRepl
//    }

    //四元数乘法的定义实现版本((计算机)算的快)
    public Quaternion multPolImpl(Quaternion other){
        float a = re;
        float b = i;
        float c = j;
        float d = k;
        float e = other.re;
        float f = other.i;
        float g = other.j;
        float h = other.k;
        return new Quaternion(
                a*e-b*f-c*g-d*h,
                a*f+b*e+c*h-d*g,
                a*g+c*e+d*f-b*h,
                a*h+d*e+b*g-c*f
        );
    }

    public float length(){
        return Mth.sqrt(lengthSquare());
    }

    public float lengthSquare(){
        return re*re + i*i + j*j + k*k;
    }

    //此四元数的逆
    public Quaternion anti(){
        return conjugate().scale(1.0f/lengthSquare());
    }

    //此四元数的共轭四元数
    public Quaternion conjugate(){
        return new Quaternion(re,-i,-j,-k);
    }

    //此四元数的单位四元数
    public Quaternion unit(){
        return scale(1.0f/length());
    }

    static DecimalFormat df = new DecimalFormat("0.00");

    public String toString(){
        return df.format(re) + "+" + df.format(i) + "i+" + df.format(j) + "j+" + df.format(k) + "k";
    }
    /**
     * **/
    public static Vector3f moveQ(Vector3f startV, Vector3f targetV, float maxAngle){

        float now = startV.dot(targetV) / (lengthOf(startV)*lengthOf(targetV));
        float maxCos = Mth.cos((float) (maxAngle*Math.PI/180));
        float maxSin = Mth.sin((float) (maxAngle*Math.PI/180));
        if(now >= maxCos){
            return targetV;
        }
        Quaternion start = new Quaternion(0,startV);
        startV.cross(targetV);
        Quaternion rotationAxle = new Quaternion(0,startV).unit();
        Quaternion rotate = rotationAxle.scale(maxSin);
        rotate.re = maxCos;

        return rotate.multPolImpl(start).multPolImpl(rotate.conjugate()).getVectorPart();
    }
    public static float lengthOf(Vector3f vec) {
        return Mth.sqrt((float) (Math.pow(vec.x(), 2 )+Math.pow(vec.x(), 2)+Math.pow(vec.x(), 2)));
    }
}