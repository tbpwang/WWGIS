/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.test;

import edu.zhenger.util.Change;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.render.Path;

import java.util.Arrays;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/6/27
 */
public class Test
{
    public static void main(String[] args)
    {


        Vec4 vec4 = new Vec4(0,0,0);
        new LatLon(Change.getGlobe().computePositionFromPoint(vec4));

        LatLon A = LatLon.fromDegrees(45,0);
        LatLon B = LatLon.fromDegrees(45,90);
        LatLon Cgc = LatLon.interpolateGreatCircle(0.5,A,B);
        System.out.println(Cgc);
        LatLon Drh = LatLon.interpolateRhumb(0.5,A,B);
        System.out.println(Drh);

        //LatLon E = LatLon.i
        System.out.println(Arrays.toString(LatLon.greatCircleArcExtremeLocations(A,B)));
        System.out.println("====================");
        Angle a1 = LatLon.rhumbAzimuth(A,B);
        Angle a11 = LatLon.rhumbAzimuth(A,B);
        System.out.println(a1);
        System.out.println("A11: " + a11);
        a1 = LatLon.greatCircleDistance(A,B);
        System.out.println(a1);
        System.out.println("A11= "+a11);

        Angle a2 = LatLon.rhumbDistance(A,B);
        System.out.println(a2);
        Angle a3 = LatLon.greatCircleAzimuth(A,B);
        System.out.println(a3);
        Angle a4 = LatLon.greatCircleDistance(A,B);
        System.out.println(a4);
        Angle a5 = LatLon.linearAzimuth(A,B);
        System.out.println(a5);
        Angle a6 = LatLon.linearDistance(A,B);
        System.out.println(a6);


    }

}
