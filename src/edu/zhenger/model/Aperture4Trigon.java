/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.geom.LatLon;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function: view of trigon with 4 apertures
 * @Date: 2018/8/3
 */
public class Aperture4Trigon
{
    private String sideType;
    private LatLon[][] vertexs;
    private int level;

    public Aperture4Trigon(LatLon[][] vertexs, String sideType)
    {
        //TODO
        this.sideType = sideType;
        this.vertexs = vertexs;
    }
}
