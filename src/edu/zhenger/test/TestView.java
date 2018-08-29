/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.test;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/8/28
 */
public class TestView extends ApplicationTemplate
{
    public static class TestApp extends ApplicationTemplate.AppFrame
    {
        public TestApp()
        {
            ShapeAttributes attributes = new BasicShapeAttributes();
            attributes.setInteriorMaterial(new Material(new Color(255,0,0)));
            attributes.setDrawInterior(true);
            SurfacePolyline polyline = new SurfacePolyline();
            List<LatLon> list = new ArrayList<>();
            list.add(LatLon.fromDegrees(90,180));
            list.add(LatLon.fromDegrees(-89,180));
            polyline.setLocations(list);
            polyline.setAttributes(attributes);
            RenderableLayer poly = new RenderableLayer();
            poly.addRenderable(polyline);
            insertBeforeCompass(getWwd(),poly);
            Path path = new Path(Position.fromDegrees(30,-180),Position.fromDegrees(30,-90));
//            Path path = new Path(Position.fromDegrees(30,-90),Position.fromDegrees(30,-180));
            path.setAttributes(attributes);
            path.setPathType(AVKey.GREAT_CIRCLE);
            path.setFollowTerrain(true);
            path.setTerrainConformance(1);
            path.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
            RenderableLayer layer = new RenderableLayer();
            layer.addRenderable(path);
            insertBeforeCompass(getWwd(),layer);
        }
    }

    public static void main(String[] args)
    {
        start("TestView",TestApp.class);
    }
}
