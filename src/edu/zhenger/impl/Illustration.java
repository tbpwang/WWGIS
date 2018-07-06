/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.Trigon;
import edu.zhenger.util.Change;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.util.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/1/17
 */
public class Illustration extends ApplicationTemplate
{
    public static class AppFrame extends ApplicationTemplate.AppFrame
    {
        public AppFrame()
        {
            super();
            // display layers

            RenderableLayer trigon = new RenderableLayer();
            trigon.setName("Illustration1");
            for (SurfacePolygon sp : first2levels())
            {
                trigon.addRenderable(sp);
            }
            this.getWwd().getView().setEyePosition(
                new Position(LatLon.getCenter(first2levels().get(0).getLocations(Change.getGlobe())),Change.getGlobe().getRadius()));
            this.getWwd().getModel().getLayers().add(trigon);
        }

        private List<SurfacePolygon> first2levels()
        {
            List<SurfacePolygon> levels = new ArrayList<>();

            LatLon a, b, c;
            a = LatLon.fromDegrees(0, 45);
            b = LatLon.fromDegrees(45, 0);
            c = LatLon.fromDegrees(45, 90);

            Trigon trigon = new Trigon(a, b, c, "");
//            Trigon[] children = new Trigon[4];
//            children[0] = (Trigon) trigon.subdivide().getChildren()[0];
//            children[1] = (Trigon) trigon.subdivide().getChildren()[1];
//            children[2] = (Trigon) trigon.subdivide().getChildren()[2];
//            children[3] = (Trigon) trigon.subdivide().getChildren()[3];

            //levels.add(trigon.surfacePolygon());
//            levels.add(children[0].surfacePolygon());
//            levels.add(children[1].surfacePolygon());
//            levels.add(children[2].surfacePolygon());
//            levels.add(children[3].surfacePolygon());
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                   levels.add(((Trigon)trigon.subdivide().getChildren()[i].subdivide().getChildren()[j]).getSurfacePolygon());
                }
            }




            return levels;
        }
    }

    public static void main(String[] args)
    {
        start("Illustration",AppFrame.class);
    }
}
