/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.geom.LatLon;

import java.util.*;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function: used in View
 * @Date: 2017/12/6
 */
public class Graph
{
    private Vertex[][] vertices;
    private List<Edge> edgeList;
    //private int level;
    private int maxRow;
    private Trilateral baseTriangle;
    private boolean initial;

    public Graph(Trilateral baseTriangle, int level)
    {
        //int total = (int) ((Math.pow(2,level-1)+1)*(Math.pow(2,level)+1));
        //this.level = level;
        this.maxRow = (int) Math.pow(2, level);
        this.vertices = new Vertex[maxRow + 1][];
        this.baseTriangle = baseTriangle;
        this.edgeList = new ArrayList<>();
        initial = false;
    }

    public void init()
    {
        // up triangle as antic-clockwise
        // down triangle as clockwise

        LatLon a, b, c, p1, p2, p3;
        a = baseTriangle.getEdge1().getV1().getCoordinate();
        b = baseTriangle.getEdge2().getV1().getCoordinate();
        c = baseTriangle.getEdge3().getV1().getCoordinate();

        // up triangle as antic-clockwise
        // down triangle as clockwise

        // init row
        vertices[0] = new Vertex[] {new Vertex(a, "1")};
        int edgeCounter = 0;

        int total;
        double intervalRow = 1.0 / maxRow;

        if (baseTriangle.getOrientation() == 1)
        {
            for (int i = 1; i <= maxRow; i++)
            {
                total = i * (i + 1) / 2;
                // col number equals = i + 1
                vertices[i] = new Vertex[i + 1];

                p1 = LatLon.interpolateGreatCircle((i * intervalRow), a, b);
                p2 = LatLon.interpolateGreatCircle((i * intervalRow), a, c);

                //init col
                vertices[i][0] = new Vertex(p1, String.valueOf(total + 1));
                edgeList.add(new Edge(vertices[i][0], vertices[i - 1][0], String.valueOf(edgeCounter++)));

                double intervalCol = 1.0 / i;
                for (int j = 1; j <= i; j++)
                {
                    p3 = LatLon.interpolateGreatCircle((j * intervalCol), p1, p2);
                    vertices[i][j] = new Vertex(p3, String.valueOf(total + 1 + j));

                    // connect or join points to lines

                    // up-front
                    if (exist(i - 1, j - 1))
                    {
                        edgeList.add(new Edge(vertices[i][j], vertices[i - 1][j - 1], String.valueOf(edgeCounter++)));
                    }
                    // up-same
                    if (exist(i - 1, j))
                    {
                        edgeList.add(new Edge(vertices[i][j], vertices[i - 1][j], String.valueOf(edgeCounter++)));
                    }
                    // same-front
                    if (exist(i, j - 1))
                    {
                        edgeList.add(new Edge(vertices[i][j], vertices[i][j - 1], String.valueOf(edgeCounter++)));
                    }
                }
            }
        }
        else
        {
            for (int i = 1; i <= maxRow; i++)
            {
                total = i * (i + 1) / 2;
                // col number equals = i + 1
                vertices[i] = new Vertex[i + 1];

                p1 = LatLon.interpolateGreatCircle((i * intervalRow), a, b);
                p2 = LatLon.interpolateGreatCircle((i * intervalRow), a, c);

                //init col
                vertices[i][0] = new Vertex(p1, String.valueOf(total + 1));
                //edgeList.add(new Edge(vertices[i][0], vertices[i - 1][0], String.valueOf(edgeCounter++)));

                double intervalCol = 1.0 / i;
                for (int j = 1; j <= i; j++)
                {
                    p3 = LatLon.interpolateGreatCircle((j * intervalCol), p1, p2);
                    vertices[i][j] = new Vertex(p3, String.valueOf(total + 1 + j));

                    // connect or join points to lines

                    // up-front
                    if (exist(i - 1, j - 1))
                    {
                        edgeList.add(new Edge(vertices[i][j], vertices[i - 1][j - 1], String.valueOf(edgeCounter++)));
                    }
                    // up-same
                    if (exist(i - 1, j))
                    {
                        edgeList.add(new Edge(vertices[i][j], vertices[i - 1][j], String.valueOf(edgeCounter++)));
                    }
                    // same-front
                    if (exist(i, j - 1))
                    {
                        edgeList.add(new Edge(vertices[i][j], vertices[i][j - 1], String.valueOf(edgeCounter++)));
                    }
                }
            }

        }

        initial = true;

    }

    // join or not
    private boolean exist(int row, int col)
    {
        return row >= 0 && col >= 0 && col <= row;
    }

    public Vertex[][] getVertices()
    {
        if (!initial)
            init();
        return vertices;
    }

    public List<Edge> getEdgeList()
    {
        if (!initial)
            init();
        return edgeList;
    }

    public Trilateral getBaseTriangle()
    {
        return baseTriangle;
    }
}
