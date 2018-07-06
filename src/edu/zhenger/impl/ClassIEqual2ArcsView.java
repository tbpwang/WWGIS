/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function: Subsivision View with Class I and equal-arcs(two great circles)
 * @Date: 2018/7/6
 */
public class ClassIEqual2ArcsView extends ApplicationTemplate
{
    public static class EqualArcsApp extends ApplicationTemplate.AppFrame
    {

        public EqualArcsApp()
        {
            super();
            int level = 7;
            int total = 8;
            int frm = 3;
            int to = 5;

            Trigon[] trigons = new Trigon[total];
            for (int i = frm; i < to; i++)
            {
                trigons[i] = (Trigon) Octant.getInstance().getFacet(i);
            }
            OctantMesh[] meshs = new OctantMesh[total];
            for (int i = frm; i < to; i++)
            {
                meshs[i] = new OctantMesh(trigons[i], level);
            }
            RenderableLayer[] layers = new RenderableLayer[total];
            for (int i = frm; i < to; i++)
            {
                layers[i] = new RenderableLayer();
                layers[i].setName("Octant_" + i);
                for (int j = 0; j < meshs[i].getTriangles().length; j++)
                {
                    for (int k = 0; k < meshs[i].getTriangles()[j].length; k++)
                    {
                        layers[i].addRenderable(meshs[i].getTriangles()[j][k].getTrigon());
                    }
                }
                insertBeforeCompass(getWwd(), layers[i]);
            }
        }
    }

    public static void main(String[] args)
    {
        start("TestObjects", EqualArcsApp.class);
    }
}
