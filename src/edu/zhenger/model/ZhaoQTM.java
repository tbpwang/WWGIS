/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.Cell;
import edu.zhenger.util.*;
import gov.nasa.worldwind.geom.LatLon;

/**
 * @author Wangzheng
 * @email tbpwang@gmail.com
 * @function based on Zhao et al.(2016), with direct to levels on sphere
 * @date 2018/8/3
 */
public class ZhaoQTM
{
    private int level;
    private LatLon[][] latLons;
    private int lines;

    public ZhaoQTM(Cell trigon, int level)
    {
        this.level = level;
        lines = (int) Math.pow(2, level);
//        WWMath
        latLons = new LatLon[lines + 1][];
        init(trigon);

//        latLons = initView(trigon);

    }

    //    private LatLon[][] init(Trigon trigon)
    private void init(Cell trigon)
    {
//        LatLon[][] latLons = new LatLon[lines + 1][];
        double[] lats = new double[lines + 1];
        double dCol = 1.0;
//        double delt;
//        if (trigon.getRightV().longitude.multiply(trigon.getLeftV().longitude.degrees).degrees < 0)
//        {
//            delt = trigon.getRightV().longitude.degrees + trigon.getLeftV().longitude.degrees;
//        }
//        else
//        {
//            delt = trigon.getRightV().longitude.subtract(trigon.getLeftV().longitude).degrees;
//        }

        for (int i = 0; i < lines + 1; i++)
        {
            if (i == 0)
            {
//                lats[0] = 90.0;
//                lats[0] = trigon.getTopV().latitude.getRadians();
                lats[0] = trigon.getLatLons()[0].latitude.radians;
//                System.out.println(trigon.getTopV().latitude.degrees);
            }
            else if (i == lines)
            {
//                lats[i] = 0.0;
//                lats[i] = trigon.getLeft().latitude.getRadians();
                lats[i] = trigon.getLatLons()[1].latitude.radians;
                dCol = (trigon.getLatLons()[2].longitude.subtract(trigon.getLatLons()[1].longitude)).divide(i).degrees;
            }
            else
            {
                if (lats[i - 1] >= 0.0)
                {
                    lats[i] = Math.asin(Math.sin(lats[i - 1]) - (2 * i - 1) / Math.pow(4, level));
                }
                else
                {
                    lats[i] = Math.asin(Math.sin(lats[i - 1]) + (2 * i - 1) / Math.pow(4, level));
                }
                dCol = (trigon.getLatLons()[2].longitude.subtract(trigon.getLatLons()[1].longitude)).divide(i).degrees;
            }
            latLons[i] = new LatLon[i + 1];
            for (int j = 0; j < i + 1; j++)
            {
                if (i == 0)
                {
                    latLons[0][0] = new LatLon(trigon.getLatLons()[0]);
                }
                else if (j == 0)
                {
                    latLons[i][0] = LatLon.fromDegrees(Change.radian2Degree(lats[i]),
                        trigon.getLatLons()[1].longitude.degrees);
                }
                else if (j == i)
                {
                    latLons[i][j] = LatLon.fromDegrees(Change.radian2Degree(lats[i]),
                        trigon.getLatLons()[2].longitude.degrees);
                }
                else
                {
                    latLons[i][j] = LatLon.fromDegrees(Change.radian2Degree(lats[i]),
                        trigon.getLatLons()[1].longitude.addDegrees(j * dCol).degrees);
                }
            }
        }
//        System.out.println(Arrays.toString(lats));
//        for (LatLon[] ls : latLons)
//        {
//            for (LatLon l : ls)
//            {
//                System.out.println(l);
//            }
//        }
//        return latLons;
    }

    private LatLon[][] initView(Trigon trigon)
    {
        LatLon[][] temp = new LatLon[lines + 1][];
        if (trigon.getTopV().latitude.degrees == 90.0)
        {
            double[] lats = new double[lines + 1];
            double dCol = 1.0;
            double delt = trigon.getRightV().longitude.subtract(trigon.getLeftV().longitude).degrees;
            for (int i = 0; i < lines + 1; i++)
            {
                if (i == 0)
                {
                    lats[0] = 90.0;
                }
                else if (i == lines)
                {
                    lats[i] = 0.0;
                    dCol = 90.0 / i;
                }
                else
                {
                    lats[i] = Math.asin(Math.sin(lats[i - 1]) - (2 * i - 1) / Math.pow(4, level));
                    dCol = delt / i;
                }
                temp[i] = new LatLon[i + 1];

                for (int j = 0; j < i + 1; j++)
                {
                    if (i == 0)
                    {
                        temp[0][0] = new LatLon(trigon.getTopV());
                    }
                    else if (j == 0)
                    {
                        temp[i][0] = LatLon.fromRadians(lats[i], trigon.getLeftV().longitude.radians);
                    }
                    else if (j == i)
                    {
                        temp[i][j] = LatLon.fromRadians(lats[i], trigon.getRightV().longitude.radians);
                    }
                    else
                    {
                        temp[i][j] = LatLon.fromRadians(lats[i],
                            trigon.getLeftV().longitude.addDegrees(j * dCol).degrees);
                    }
                }
            }
        }
        else
        {
            LatLon A, B, C;
            A = new LatLon(trigon.getTopV().latitude.multiply(-1.0), trigon.getTopV().longitude);
            B = new LatLon(trigon.getLeftV().latitude.multiply(-1.0), trigon.getLeftV().longitude);
            C = new LatLon(trigon.getRightV().latitude.multiply(-1.0), trigon.getRightV().longitude);
            Trigon trigon1 = new Trigon(A, B, C, trigon.getGeocode());
            LatLon[][] latLons1 = initView(trigon1);
            for (int i = 0; i < latLons1.length; i++)
            {
                for (int j = 0; j < latLons1[i].length; j++)
                {
                    temp[i][j] = new LatLon(latLons1[i][j].latitude.multiply(-1.0), latLons1[i][j].longitude);
                }
            }
        }

        return temp;
    }

    public LatLon[][] getLatLons()
    {
        return latLons;
    }

    public int getLevel()
    {
        return level;
    }
}
