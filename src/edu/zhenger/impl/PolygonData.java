/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.Cell;
import edu.zhenger.model.Trigon;
import gov.nasa.worldwind.render.*;

import java.util.*;
import java.util.List;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/1/18
 */
public class PolygonData
{
    private List<SurfacePolygon> polygons;

    public PolygonData(Cell[][] geocodeOrderTrigons)
    {
        polygons = new ArrayList<>();
//        ShapeAttributes attr = new BasicShapeAttributes();
//        attr.setInteriorOpacity(0.05);
        //SurfacePolygon surfacePolygon;
        for (int i = 0; i < geocodeOrderTrigons.length; i++)
        {
            for (int j = 0; j < geocodeOrderTrigons[i].length; j++)
            {
//                surfacePolygon = ((Trigon)(geocodeOrderTrigons[i][j])).surfacePolygon();
//                surfacePolygon.setPathType(AVKey.GREAT_CIRCLE);
//                surfacePolygon.setAttributes(attr);
                polygons.add(((Trigon)(geocodeOrderTrigons[i][j])).surfacePolygon());
            }
        }
    }

    public List<SurfacePolygon> getPolygons()
    {
        if (polygons.isEmpty())return null;

        return polygons;
    }
}
