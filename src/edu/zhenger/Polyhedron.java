/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger;

import gov.nasa.worldwind.geom.Vec4;

import java.io.Serializable;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/11
 */
public interface Polyhedron extends Serializable
{
    Vec4[][] getVertex();
    Cell getFacet(int ID);
}
