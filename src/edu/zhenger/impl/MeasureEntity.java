/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.util.Change;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.formats.shapefile.*;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.*;
import gov.nasa.worldwind.util.measure.MeasureTool;
import gov.nasa.worldwindx.examples.*;
import gov.nasa.worldwindx.examples.util.RandomShapeAttributes;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/1/20
 */
public class MeasureEntity extends EsriShapefile
{
    public static class AppFrame extends EsriShapefile.AppFrame
    {
        public AppFrame()
        {
            super();
            this.getWwd().getView().setEyePosition(Position.fromDegrees(39, -100, 1.5 * Change.getGlobe().getRadius()));


//            ShapeAttributes attributes = new BasicShapeAttributes();
//            attributes.setInteriorOpacity(0.1);
//            attributes.setInteriorMaterial(new Material(new Color(100,255,100)));
//
//            String shpPath = "src/edu/zhenger/data/UnitedStates.shp";
//            Shapefile shapefile = new Shapefile(shpPath);
//            ShapefilePolygons polygons = new ShapefilePolygons(shapefile,attributes,attributes,null);
//            polygons.getRecord(0);
//
//
//            RenderableLayer layer = new RenderableLayer();
//            layer.addRenderable(polygons);
//            layer.setName("UnitedStates");
//
//            insertBeforeCompass(getWwd(),layer);
//            AppFrame.this.layerPanel.updateLayers(AppFrame.this.getWwd());

            int count=0;
            for (Layer l : getWwd().getModel().getLayers())
            {
                System.out.println(l.getName());
                count++;
            }
            System.out.println(count);
            System.out.println("-----------");
            System.out.println(getWwd().getModel().getLayers().getLayersByClass(Shapefile.class).isEmpty());


            MeasureTool measureTool = new MeasureTool(this.getWwd());
            measureTool.getUnitsFormat().setAreaUnits(UnitsFormat.METERS);
//            measureTool.setMeasureShape();
            System.out.println(measureTool.getArea());

        }
    }

    public static void main(String[] args)
    {
        start("UnitedStates", MeasureEntity.AppFrame.class);
    }
}
