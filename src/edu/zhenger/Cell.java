/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger;

import edu.zhenger.model.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.Globe;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/10
 */
public interface Cell extends MeasurableArea
{
    // shape and topology
    Vec4[] getVertex();

//    LatLon getTopV();
//    LatLon getLeftV();
//    LatLon getRightV();
    LatLon[] getLatLons();

    Globe getGlobe();

    // Area
    double getArea();

    // Perimeter
    double getPerimeter();

    //String getID();
    String getGeocode();

    // Triangle's orientation
    int getOrientation();

    // single point as reference point is center of shape
    LatLon getRefPoint();

    // path
    Side getSide();

    // compactness
    double computeCompactness();

    //Cell[] subdivide();
    Subdivision subdivide();

}
