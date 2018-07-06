/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function: used in View
 * @Date: 2017/12/6
 */
public class Edge
{
    private Vertex v1,v2;
    private String id;

    public Edge(Vertex v1, Vertex v2, String id)
    {
        this.v1 = v1;
        this.v2 = v2;
        this.id = id;
    }

    public List<Position> showPosition()
    {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(v1.getCoordinate(),0));
        positions.add(new Position(v2.getCoordinate(),0));
        return positions;
    }

    public SurfacePolyline showPath()
    {
        ShapeAttributes attributes = new BasicShapeAttributes();
        attributes.setOutlineMaterial(new Material(Color.GREEN));//CYAN
//            attributes.setOutlineStippleFactor(1);
//            attributes.setOutlineStipplePattern((short) 1);
        //attributes.setOutlineOpacity(1);
        attributes.setOutlineWidth(1);
        SurfacePolyline polyline = new SurfacePolyline(this.showPosition());
        polyline.setPathType(AVKey.GREAT_CIRCLE);
        polyline.setAttributes(attributes);
        return polyline;
    }
    public Vertex getV1()
    {
        return v1;
    }

    public Vertex getV2()
    {
        return v2;
    }

    public String getId()
    {
        return id;
    }

    public Edge negativeEdge()
    {
        return new Edge(this.v2,this.v1, this.id);
    }
    @Override
    public String toString()
    {
        return //"Edge{" +
            id +
            "{" + v1 +
            "," + v2 +
            //", id='" + id + '\'' +
            '}';
    }
}
