/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.globes.*;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/10
 */
public final class Sphere extends EllipsoidalGlobe
{
    private static Sphere ourInstance = new Sphere();

    public static Sphere getInstance()
    {
        return ourInstance;
    }

    private Sphere()
    {

//        double equatorialRadius = 6371008.7714;
//        double polarRadius = 6371008.7714;
//        double es = 0.0;
//        Vec4 center = new Vec4(0.0);
        //super(6371008.7714,6371008.7714,0.0,null,new Vec4(0.0));
        // 6378137.0
        super(Earth.WGS84_EQUATORIAL_RADIUS,Earth.WGS84_EQUATORIAL_RADIUS,0.0,null, new Earth().getCenter());
    }

    @Override
    public String toString()
    {
        return "Sphere{" +
            "equatorialRadius=" + equatorialRadius +
            ", polarRadius=" + polarRadius +
            ", es=" + es +
            ", egm96=" + egm96 +
            '}';
    }
}
