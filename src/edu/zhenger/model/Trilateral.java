/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.util.Change;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.Path;

import java.util.*;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function: used in View
 * @Date: 2017/12/6
 */
public class Trilateral
{
    private String id;
    private int orientation; // 1 up; -1 down
    private Edge edge1, edge2, edge3;

//    public Trilateral(LatLon top, LatLon left,
//        LatLon right, String geocode,int orientation)
//    {
//        super(top, left, right, geocode);
//        this.setOrientation(orientation);
//        edge1 = new Edge(new Vertex(top,"1"),new Vertex(left,"2"),"1");
//        edge2 = new Edge(new Vertex(top,"1"),new Vertex(right,"3"),"2");
//        edge3 = new Edge(new Vertex(left,"2"),new Vertex(right,"3"),"3");
//
//    }

    public Trilateral(Edge edge1, Edge edge2, Edge edge3, String id, int orientation)
    {
        this.id = id;
        this.edge1 = edge1;
        this.edge2 = edge2;
        this.edge3 = edge3;
        this.orientation = orientation;
    }

    public Trilateral(Trigon trigon)
    {
        this.id = trigon.getGeocode();
        List<LatLon> list = new ArrayList<>();
        while (trigon.getSide().getLocations().iterator().hasNext())
        {
            list.add(trigon.getSide().getLocations().iterator().next());
        }

        this.edge1 = new Edge(new Vertex(list.get(0), id + "0"), new Vertex(list.get(1),id+"1"),
            id + "0");
        this.edge2 = new Edge(new Vertex(list.get(1), id + "1"), new Vertex(list.get(2),id+"2"),
            id + "1");
        this.edge3 = new Edge(new Vertex(list.get(2), id + "2"), new Vertex(list.get(0),id+"0"),
            id + "2");
        this.orientation = orientation;
    }

    public List<Position> showPosition()
    {
        List<Position> positions = new ArrayList<>();
        positions.addAll(edge1.showPosition());
        positions.addAll(edge2.showPosition());
        positions.addAll(edge3.showPosition());
        return positions;
    }

    public String getId()
    {
        return id;
    }

    public Edge getEdge1()
    {
        return edge1;
    }

    public Edge getEdge2()
    {
        return edge2;
    }

    public Edge getEdge3()
    {
        return edge3;
    }

    public int getOrientation()
    {
        return orientation;
    }
}
