/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.*;
import edu.zhenger.util.*;
import gov.nasa.worldwind.geom.*;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/11
 */
public class Octant implements Polyhedron
{
    private static Octant ourInstance = new Octant();

    public static Octant getInstance()
    {
        return ourInstance;
    }

    private Vec4 vec4A, vec4B, vec4C, vec4D, vec4E, vec4F;

    private Cell[] cells;

    private Octant()
    {
        cells = new Cell[8];
        cells[0]= new Trigon(LatLon.fromDegrees(90,-180),LatLon.fromDegrees(0,-180),LatLon.fromDegrees(0,-90),"0");
        cells[1]= new Trigon(LatLon.fromDegrees(90,-90),LatLon.fromDegrees(0,-90),LatLon.fromDegrees(0,0),"1");
        cells[2]= new Trigon(LatLon.fromDegrees(90,0),LatLon.fromDegrees(0,0),LatLon.fromDegrees(0,90),"2");
        cells[3]= new Trigon(LatLon.fromDegrees(90,90),LatLon.fromDegrees(0,90),LatLon.fromDegrees(0,180),"3");
        cells[4]= new Trigon(LatLon.fromDegrees(-90,-180),LatLon.fromDegrees(0,-180),LatLon.fromDegrees(0,-90),"4");
        cells[5]= new Trigon(LatLon.fromDegrees(-90,-90),LatLon.fromDegrees(0,-90),LatLon.fromDegrees(0,0),"5");
        cells[6]= new Trigon(LatLon.fromDegrees(-90,0),LatLon.fromDegrees(0,0),LatLon.fromDegrees(0,90),"6");
        cells[7]= new Trigon(LatLon.fromDegrees(-90,90),LatLon.fromDegrees(0,90),LatLon.fromDegrees(0,180),"7");

        double r = Change.getGlobe().getRadius();
        // Vec4
        // y faces to North
        // z faces to the prime meridian
        // x orientate to east in equator, and is perpendicular y and z
        this.vec4A = new Vec4(0, r, 0);//(90,90)
        this.vec4B = new Vec4(0, 0, r);//(0,0)
        this.vec4C = new Vec4(r, 0, 0);//(0,90)
        this.vec4D = new Vec4(0, 0, -r);//(0,180)
        this.vec4E = new Vec4(-r, 0, 0);//(0,-90)
        this.vec4F = new Vec4(0, -r, 0);//(-90,90)
    }

    @Override
    public Vec4[][] getVertex()
    {
        // 8个面,由6个顶点组成
        Vec4[][] vertex = new Vec4[8][];

        vertex[0] = new Vec4[] {vec4A, vec4B, vec4C};
        vertex[1] = new Vec4[] {vec4A, vec4C, vec4D};
        vertex[2] = new Vec4[] {vec4A, vec4D, vec4E};
        vertex[3] = new Vec4[] {vec4A, vec4E, vec4B};
        vertex[4] = new Vec4[] {vec4F, vec4B, vec4C};
        vertex[5] = new Vec4[] {vec4F, vec4C, vec4D};
        vertex[6] = new Vec4[] {vec4F, vec4D, vec4E};
        vertex[7] = new Vec4[] {vec4F, vec4E, vec4B};

        return vertex;
    }

    @Override
    public Cell getFacet(int ID)
    {
//        LatLon top;
//        LatLon left;
//        LatLon right;
//        if (ID == 0)
//        {
//            top = LatLon.fromDegrees(90,-180);
//            left = LatLon.fromDegrees(0,-180);
//            right = LatLon.fromDegrees(0,-90);
//            cells[ID] =  new Trigon(top, left, right, String.valueOf(ID));
//        }
//        else if (ID == 1)
//        {
//            top = LatLon.fromDegrees(90,-90);
//            left = LatLon.fromDegrees(0,-90);
//            right = LatLon.fromDegrees(0,0);
//            cells[ID] =  new Trigon(top, left, right, String.valueOf(ID));
//        }
//        else if (ID == 2)
//        {
//            top = LatLon.fromDegrees(90,0);
//            left = LatLon.fromDegrees(0,0);
//            right = LatLon.fromDegrees(0,90);
//            cells[ID] =  new Trigon(top, left, right, String.valueOf(ID));
//        }
//        else if (ID == 3)
//        {
//            top = LatLon.fromDegrees(90, 90);
//            left = LatLon.fromDegrees(0, 90);
//            right = LatLon.fromDegrees(0, 180);
//            cells[ID] =  new Trigon(top, left, right, String.valueOf(ID));
//        }
//        else if (ID == 4)
//        {
//            top = LatLon.fromDegrees(-90,-180);
//            left = LatLon.fromDegrees(0,-180);
//            right = LatLon.fromDegrees(0,-90);
//            cells[ID] =  new Trigon(top, left, right, String.valueOf(ID));
//        }
//        else if (ID == 5)
//        {
//            top = LatLon.fromDegrees(-90,-90);
//            left = LatLon.fromDegrees(0,-90);
//            right = LatLon.fromDegrees(0,0);
//            cells[ID] =  new Trigon(top, left, right, String.valueOf(ID));
//        }
//        else if (ID == 6)
//        {
//            top = LatLon.fromDegrees(-90,0);
//            left = LatLon.fromDegrees(0,0);
//            right = LatLon.fromDegrees(0,90);
//            cells[ID] =  new Trigon(top, left, right, String.valueOf(ID));
//        }
//        else if (ID == 7)
//        {
//            top = LatLon.fromDegrees(-90, 90);
//            left = LatLon.fromDegrees(0, 90);
//            right = LatLon.fromDegrees(0, 180);
//            cells[ID] =  new Trigon(top, left, right, String.valueOf(ID));
//        }
//        else
//        {
//            throw new Mistake("IDIsExcess");
//        }
        return cells[ID];
    }
}
