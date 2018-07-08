/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.geom.LatLon;

import java.util.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function: construction mesh of Class I and Mid-arcs without recursion. only 1/8 of globe
 * @Date: 2018/7/5
 */
public class OctantMeshEqualArcs
{
    // while p0 is the polar, p1 is a left point, and p2 is a right point
    private LatLon p1, p10, p20, p2;
    private int level;
    //
    private Trigon[][] triangles;
    private Trigon[] trigons;

    public OctantMeshEqualArcs(LatLon top, LatLon left, LatLon right, int level)
    {
        this.level = level;
        this.p1 = left;
        this.p2 = right;

        if ((Math.abs(top.latitude.getDegrees()) - 90) < Math.pow(10, -10))
        {
            p10 = LatLon.fromDegrees(top.latitude.degrees, left.longitude.degrees);
            p20 = LatLon.fromDegrees(top.latitude.degrees, right.longitude.degrees);
        }
        else
        {
            p10 = top;
            p20 = top;
        }
        init();
    }

    public OctantMeshEqualArcs(Trigon trigon, int level)
    {
        this.level = level;
        p1 = trigon.getLeft();
        p2 = trigon.getRight();
        if (Math.abs(trigon.getTop().latitude.getDegrees()) - 90 < Math.pow(10, -10))
        {
            p10 = LatLon.fromDegrees(trigon.getTop().latitude.degrees, p1.longitude.degrees);
            p20 = LatLon.fromDegrees(trigon.getTop().latitude.degrees, p2.longitude.degrees);
        }
        else
        {
            p10 = trigon.getTop();
            p20 = trigon.getTop();
        }
        init();
    }

    public int getLevel()
    {
        return level;
    }

    public Trigon[][] getTriangles()
    {
        return triangles;
    }

    public Trigon[] getTrigons()
    {
        List<Trigon> trigonList = new ArrayList<>();
        int counter = 0;
        for (Trigon[] triangle : triangles)
        {
            for (Trigon aTriangle : triangle)
            {
                trigonList.add(counter++, aTriangle);
//                counter = counter + 1;
            }
        }

        return (Trigon[]) trigonList.toArray();
    }

    private void init()
    {
        int lineNumber = (int) Math.pow(2, level);
        LatLon[][] sidePoints = new LatLon[lineNumber + 1][2];
        double deltline = 1.0f / lineNumber;
        for (int i = 0; i < lineNumber + 1; i++)
        {
            if (i == 0)
            {
                sidePoints[i][0] = new LatLon(p10);
                sidePoints[i][1] = new LatLon(p10);
                //sidePoints[i][1] = new LatLon(p20);
            }
            else
            {
                sidePoints[i][0] = LatLon.interpolateGreatCircle(deltline * i, p10, p1);
                sidePoints[i][1] = LatLon.interpolateGreatCircle(deltline * i, p20, p2);
            }
        }

        double deltCol;
        LatLon[][] colPoints = new LatLon[lineNumber + 1][];
        for (int i = 0; i < lineNumber + 1; i++)
        {
            colPoints[i] = new LatLon[i + 1];
            if (i > 0)
            {
                deltCol = 1.0f / i;
            }
            else
            {
                deltCol = 1;
            }
            for (int j = 0; j < i + 1; j++)
            {
                if (j == 0)
                {
                    colPoints[i][0] = new LatLon(sidePoints[i][0]);
                }
                else
                {
                    colPoints[i][j] = LatLon.interpolateGreatCircle(deltCol * j, sidePoints[i][0],
                        sidePoints[i][1]);
                }
            }
        }

        triangles = new Trigon[lineNumber][];
        for (int i = 0; i < lineNumber; i++)
        {
            triangles[i] = new Trigon[2 * i + 1];
            for (int j = 0; j < (2 * i + 1); j++)
            {
                // 正向
                if (j % 2 == 0)
                {
                    triangles[i][j] = new Trigon(colPoints[i][j / 2], colPoints[i + 1][j / 2],
                        colPoints[i + 1][j / 2 + 1], "");
                }
                else //反向
                {
                    triangles[i][j] = new Trigon(colPoints[i + 1][(j + 1) / 2], colPoints[i][(j + 1) / 2 - 1],
                        colPoints[i][(j + 1) / 2], "");
                }
            }
        }
    }
}
