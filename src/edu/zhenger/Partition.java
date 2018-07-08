/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger;

import edu.zhenger.DGGS;
import edu.zhenger.model.*;
import gov.nasa.worldwind.geom.LatLon;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/7/8
 */
public interface Partition extends DGGS
{
    Trigon[] subdivide(Trigon trigon);
    // example class as follows
//    private Trigon[] children;
//    public Partition(Trigon trigon, int depth)
//    {
//        // Great Circle Method
//        children = new Trigon[4];
//        if (trigon == null)
//        {
//            trigon = (Trigon) Octant.getInstance().getFacet(0);
//        }
//        LatLon top, left, right, t, l, r;
//        top = trigon.getTop();
//        left = trigon.getLeft();
//        right = trigon.getRight();
//        t = LatLon.interpolateGreatCircle(0.5f, left, right);
//        r = LatLon.interpolateGreatCircle(0.5f, top, left);
//        l = LatLon.interpolateGreatCircle(0.5f, right, top);
//        String geocode = trigon.getGeocode();
//        children[0] = new Trigon(t, r, l, geocode + "0");
//        children[1] = new Trigon(top, r, l, geocode + "1");
//        children[2] = new Trigon(r, left, t, geocode + "2");
//        children[3] = new Trigon(l, t, right, geocode + "3");
//    }
//
//    public Trigon[] getChildren()
//    {
//        return children;
//    }
}
