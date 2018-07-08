/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function: Subsivision View with Class I and equal-arcs(two great circles)
 * @Date: 2018/7/6
 */
public class Equal2ArcsView extends ApplicationTemplate
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

            // One octant only
            Trigon octant = (Trigon) Octant.getInstance().getFacet(0);
            OctantMeshEqualArcs mesh = new OctantMeshEqualArcs(octant, level);
            SurfacePolygon[] polygons = new SurfacePolygon[(int) Math.pow(4, level)];
            RenderableLayer octantLayer = new RenderableLayer();
            octantLayer.setName("Octant");
            for (int j = 0; j < mesh.getTrigons().length; j++)
            {
                polygons[j] = mesh.getTrigons()[j].getSurfacePolygon();
            }
            for (SurfacePolygon polygon : polygons)
            {
                octantLayer.addRenderable(polygon);
            }
//            for (int i = 0; i < mesh.getTrigons().length; i++)
//            {
//                for (int j = 0; j < mesh.getTrigons()[i].length; j++)
//                {
//                    octantLayer.addRenderable(mesh.getTrigons()[i][j].getSurfacePolygon());
//                }
//            }
            insertBeforeCompass(getWwd(),octantLayer);

            // globe surface
            Trigon[] trigons = new Trigon[total];
            for (int i = frm; i < to; i++)
            {
                trigons[i] = (Trigon) Octant.getInstance().getFacet(i);
            }
            OctantMeshEqualArcs[] meshs = new OctantMeshEqualArcs[total];
            for (int i = frm; i < to; i++)
            {
                meshs[i] = new OctantMeshEqualArcs(trigons[i], level);
            }
            RenderableLayer[] layers = new RenderableLayer[total];
            for (int i = frm; i < to; i++)
            {
                layers[i] = new RenderableLayer();
                layers[i].setName("Octant_" + i);
                for (int j = 0; j < meshs[i].getTrigons().length; j++)
                {
//                    for (int k = 0; k < meshs[i].getTrigons()[j].length; k++)
//                    {
//                        layers[i].addRenderable(meshs[i].getTrigons()[j][k].getSurfacePolygon());
//                    }
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
