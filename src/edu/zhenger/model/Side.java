/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Author: WangZheng Email: tbpwang@gmail.com Function: Side represents the arcs of the spherical surfacePolygon Date:
 * 2017/10/11
 */
public class Side extends SurfacePolyline
{
    private Trigon trigon;

    private String geocode;

    private SurfacePolyline arcs;

    public Side(Trigon trigon)
    {
        this(trigon,AVKey.GREAT_CIRCLE);
    }

    public Side(Trigon trigon, String pathType)
    {
        this.geocode = trigon.getGeocode();
        this.trigon = trigon;
        arcs.setPathType(pathType);

        List<LatLon> list = new ArrayList<>();
        list.add(trigon.getTopV());
        list.add(trigon.getLeftV());
        list.add(trigon.getRightV());
        arcs.setLocations(list);
        arcs.setClosed(true);

        ShapeAttributes attributes = new BasicShapeAttributes();
        attributes.setOutlineMaterial(new Material(new Color(0,255,0)));
        attributes.setInteriorOpacity(0.1);
        arcs.setAttributes(activeAttrs);

    }

    public Trigon getTrigon()
    {
        return trigon;
    }

    public SurfacePolyline getArcs()
    {
        return arcs;
    }
}
