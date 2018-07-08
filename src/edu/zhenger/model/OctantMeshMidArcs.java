/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.Partition;
import gov.nasa.worldwind.geom.LatLon;

import java.util.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/7/6
 */
public class OctantMeshMidArcs implements Partition
{
    private Trigon trigon;
    private int level;
    private Trigon[] trigons;

    public OctantMeshMidArcs()
    {
        this((Trigon) Octant.getInstance().getFacet(0), 2);
    }

    public OctantMeshMidArcs(Trigon trigon, int level)
    {
        this.level = level;
        this.trigon = trigon;
        trigons = new Trigon[(int) Math.pow(4, level)];
        init();
    }

    public Trigon[] getTrigons()
    {
        return trigons;
    }

    private void init()
    {
        List<Trigon> trigonList = new ArrayList<>();
        trigonList.add(0, trigon);
        int tempNum = trigonList.size();
        List<Trigon> tempList;
        for (int i = 0; i < level; i++)
        {
            tempList = new ArrayList<>();
            for (int j = 0; j < tempNum; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    tempList.add(j * 4 + k, subdivide(trigonList.get(j))[k]);
                }
            }
            tempNum = tempList.size();
            trigonList.clear();
            trigonList.addAll(tempList);
        }
        for (int i = 0; i < trigonList.size(); i++)
        {
            trigons[i] = trigonList.get(i);
        }
    }

    @Override
    public Trigon[] subdivide(Trigon trigon)
    {
        Trigon[] children = new Trigon[4];
        if (trigon == null)
        {
            trigon = (Trigon) Octant.getInstance().getFacet(0);
        }
        LatLon top, left, right, t, l, r;
        top = trigon.getTop();
        left = trigon.getLeft();
        right = trigon.getRight();
        t = LatLon.interpolateGreatCircle(0.5f, left, right);
        r = LatLon.interpolateGreatCircle(0.5f, top, left);
        l = LatLon.interpolateGreatCircle(0.5f, right, top);
        String geocode = trigon.getGeocode();
        children[0] = new Trigon(t, r, l, geocode + "0");
        children[1] = new Trigon(top, r, l, geocode + "1");
        children[2] = new Trigon(r, left, t, geocode + "2");
        children[3] = new Trigon(l, t, right, geocode + "3");
        return children;
    }
}
