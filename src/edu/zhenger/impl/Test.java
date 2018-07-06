/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import gov.nasa.worldwind.geom.LatLon;

import java.util.*;

/**
 * Author: WangZheng Email: tbpwang@gmail.com Function:
 * <p>
 * Date: 2017/10/11
 */
public class Test
{
    public static void main(String[] args)
    {
        int t = 3%2;
        System.out.println(t);
//        Earth earth = new Earth();
//        System.out.println(earth.getEquatorialRadius());
//        System.out.println(earth.getRadius());
//        System.out.println(Earth.WGS84_EQUATORIAL_RADIUS);
//        System.out.println("x = " + earth.getCenter().x);
//        System.out.println("y = " + earth.getCenter().y);
//        System.out.println("z = " + earth.getCenter().z);
//        // write to txt
//        int level = 1;
//        Calculator calculator = new Calculator(level);
//        Cell[][] cells = calculator.getGeocodeOrderCells();
//        IO.write(cells,level);

//        //calculate specific cell's area
////        LatLon a, b, c;
////        a = LatLon.fromDegrees(0, 45);
////        b = LatLon.fromDegrees(45, 0);
////        c = LatLon.fromDegrees(45, 90);
////        Trigon trigon = new Trigon(a, b, c, "");
////        System.out.println("Trigon=" + trigon.getArea());
////        Trigon trigon1 = new Trigon(LatLon.fromDegrees(45, 0), LatLon.fromDegrees(0, 0), LatLon.fromDegrees(0, 45), "");
////        System.out.println("T1 = " + trigon1.getArea());
////
////        System.out.println("All=" + (trigon.getArea() + 3 * trigon1.getArea()) / 4);

        double a, b, c, p;
        double A, B, C;

        a = LatLon.greatCircleDistance(LatLon.fromDegrees(0, 0), LatLon.fromDegrees(0, 90)).getRadians();
        b = LatLon.greatCircleDistance(LatLon.fromDegrees(0, 0), LatLon.fromDegrees(90, 0)).getRadians();
        c = LatLon.greatCircleDistance(LatLon.fromDegrees(90, 90), LatLon.fromDegrees(0, 90)).getRadians();
        System.out.println("PI = " + Math.PI/2);
        System.out.println("a = " + a);
        System.out.println(Math.PI/a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.println("================");
        // half-side of triangle
//        p = (a + b + c) / 2;
//        System.out.println("p = " + p);
//
//        A = 2 * Math.asin(Math.sqrt(Math.sin(p - b) * Math.sin(p - c) / (Math.sin(b) * Math.sin(c))));
//        B = 2 * Math.asin(Math.sqrt(Math.sin(p - c) * Math.sin(p - a) / (Math.sin(c) * Math.sin(a))));
//        C = 2 * Math.asin(Math.sqrt(Math.sin(p - a) * Math.sin(p - b) / (Math.sin(a) * Math.sin(b))));
//        System.out.println("A = " + A);
//        System.out.println("B = " + B);
//        System.out.println("C = " + C);

        LatLon C_ab, C_ac, C_bc;
        C_ab = LatLon.interpolateGreatCircle(0.5,LatLon.fromDegrees(0,0),LatLon.fromDegrees(90,0));
        C_ac = LatLon.interpolateGreatCircle(0.5,LatLon.fromDegrees(90,90),LatLon.fromDegrees(0,90));
        C_bc = LatLon.interpolateGreatCircle(0.5,LatLon.fromDegrees(0,0),LatLon.fromDegrees(0,90));

        double d_cb, d_ba,d_ac;
        d_cb = LatLon.greatCircleDistance(C_ab,C_ac).getRadians();
        d_ba = LatLon.greatCircleDistance(C_ac,C_bc).getRadians();
        d_ac = LatLon.greatCircleDistance(C_bc,C_ab).getRadians();

        System.out.println("0");
        System.out.println("cb = " + d_cb);
        System.out.println("ba = " + d_ba);
        System.out.println("ac = " + d_ac);
        System.out.println("n = " + Math.PI/d_cb);
        p = (d_cb + d_ba + d_ac)/2;
        double C0 = 2 * Math.asin(Math.sqrt(Math.sin(p - d_cb) * Math.sin(p - d_ac) / (Math.sin(d_cb) * Math.sin(d_ac))));
        System.out.println("C0 = PI/" + Math.PI/C0 + "-" + (180/Math.PI*C0));
        List tri = new ArrayList();
        tri.add(C_ab);
        tri.add(C_bc);
        tri.add(C_ac);
        System.out.println("Lat:" + LatLon.getCenter(tri).latitude.degrees + ", Lon:" +LatLon.getCenter(tri).longitude.degrees);
        System.out.println("---");

        double a1,b1;
        System.out.println("1");
        a1 = LatLon.greatCircleDistance(LatLon.fromDegrees(90,0),C_ab).getRadians();
        System.out.println("Ac: " + a1);
        b1 = LatLon.greatCircleDistance(LatLon.fromDegrees(90,90),C_ac).getRadians();
        System.out.println("Ab: " + b1);
        System.out.println("cb = " + d_cb);
        System.out.println("n1 " + Math.PI/a1 + ", n2 " + Math.PI/b1 + ", n3 " + Math.PI/d_cb);
        p = (a1 + b1 + d_cb)/2;
        double C1 =  2 * Math.asin(Math.sqrt(Math.sin(p - a1) * Math.sin(p - d_cb) / (Math.sin(a1) * Math.sin(d_cb))));
        System.out.println("C1 = PI/" + Math.PI/C1+ "-" + (180/Math.PI*C1));

        System.out.println("2");
        a1 = LatLon.greatCircleDistance(LatLon.fromDegrees(0,0),C_ab).getRadians();
        System.out.println("Bc: " + a1);
        b1 = LatLon.greatCircleDistance(LatLon.fromDegrees(0,0),C_bc).getRadians();
        System.out.println("Ba: " + b1);
        System.out.println("ac:" + d_ac );
        System.out.println("n1 " + Math.PI/a1 + ", n2 " + Math.PI/b1 + ", n3 " + Math.PI/d_ac);
        p = (a1 + b1 + d_ac)/2;
        double C2 =  2 * Math.asin(Math.sqrt(Math.sin(p - a1) * Math.sin(p - d_ac) / (Math.sin(a1) * Math.sin(d_ac))));
        System.out.println("C2 = PI/" + Math.PI/C2+ "-" + (180/Math.PI*C2));

        System.out.println("3");
        a1 = LatLon.greatCircleDistance(LatLon.fromDegrees(0,90),C_ac).getRadians();
        System.out.println("Cb: " + a1);
        b1 = LatLon.greatCircleDistance(LatLon.fromDegrees(0,90),C_bc).getRadians();
        System.out.println("Ca: " + b1);
        System.out.println("ab:" + d_ba );
        System.out.println("n1 " + Math.PI/a1 + ", n2 " + Math.PI/b1 + ", n3 " + Math.PI/d_ba);
//        p = (a1 + b1 + d_cb)/2;
//        double C3 =  2 * Math.asin(Math.sqrt(Math.sin(p - d_cb) * Math.sin(p - d_ac) / (Math.sin(d_cb) * Math.sin(d_ac))));
        System.out.println("C0 + C1 + C2 = PI*" + Math.PI/(C0 + C1 + C2));

    }
}
