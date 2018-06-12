/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.util;

import edu.zhenger.DGGS;
import edu.zhenger.model.Sphere;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.Globe;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/10
 */
public final class Change implements DGGS
{
//    private static Change ourInstance = new Change();
//
//    public static Change getInstance()
//    {
//        return ourInstance;
//    }
//
//    private Change()
//    {
//    }

    public static Globe getGlobe()
    {
        return Sphere.getInstance();
    }

    public static Vec4 fromLatLon(LatLon latLon)
    {
        //return getGlobe().computePointFromLocation(latLon);
        double epsilon = 1e-8;
        Vec4 vec4 = getGlobe().computePointFromLocation(latLon);
        double x, y, z;
        x = Math.abs(vec4.x) <= epsilon ? 0.0 : vec4.x;
        y = Math.abs(vec4.y) <= epsilon ? 0.0 : vec4.y;
        z = Math.abs(vec4.z) <= epsilon ? 0.0 : vec4.z;

        return new Vec4(x, y, z);
    }

    public static LatLon fromVec4(Vec4 vec4)
    {
        return new LatLon(getGlobe().computePositionFromPoint(vec4));
    }

    public static LatLon approximate(LatLon latLon)
    {
        double lat, lon, sigma;
        sigma = 1E-8;
        lat = latLon.getLatitude().degrees;
        lon = latLon.getLongitude().degrees;

        // Approximate to 0
        lat = lat > 0 ? lat < sigma ? 0 : lat : -lat < sigma ? 0 : lat;
        lon = lon > 0 ? lon < sigma ? 0 : lon : -lon < sigma ? 0 : lon;

        // Approximate to 90
        if (Math.abs(90 - lat) < sigma)
        {
            lat = 90;
        }
        else if (Math.abs(90 + lat) < sigma)
        {
            lat = -90;
        }

        if (Math.abs(90 - lon) < sigma)
        {
            lon = 90;
        }
        else if (Math.abs(90 + lon) < sigma)
        {
            lon = -90;
        }
        if ((180 - lon) < sigma)
            lon = 180;
        if ((180 + lon) < sigma)
            lon = 180;

        return LatLon.fromDegrees(lat,lon);
    }
}
