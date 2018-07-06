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

    private Octant()
    {
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
        if (ID < 0 || ID > 7)
        {
            throw new Mistake("IDIsExcess");
        }
        Vec4[] vertex = getVertex()[ID];
        LatLon top = Change.fromVec4(vertex[0]);
        LatLon left = Change.fromVec4(vertex[1]);
        LatLon right = Change.fromVec4(vertex[2]);
        return new Trigon(top, left, right, String.valueOf(ID));
    }
}
