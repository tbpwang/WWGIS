/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.dao;

import java.util.Arrays;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/12
 */
public class Analysis
{
    private double area;
    private double[] distance;

    public Analysis()
    {
    }

    public Analysis(double area)
    {
        this.area = area;
    }

    public Analysis(double area, double[] distance)
    {
        this.area = area;
        this.distance = Arrays.copyOf(distance, distance.length);
    }

    public void setArea(double area)
    {
        this.area = area;
        //TODO: write into database
    }

    public void setDistance(double[] distance)
    {
        this.distance = Arrays.copyOf(distance, distance.length);
        //TODO: write into database
    }

    public double getArea()
    {
        //TODO: read from database
        return area;
    }

    public double[] getDistance()
    {
        //TODO: read from database
        return distance;
    }
}
