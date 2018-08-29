/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.*;
import gov.nasa.worldwind.formats.shapefile.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/7/6
 */
public class MidArcsView extends ApplicationTemplate
{
    public static class MidArcsApp extends ApplicationTemplate.AppFrame
    {
        public MidArcsApp()
        {
            super();
            int level = 7;
            // Adjustment some latitude and longitude
//            RenderableLayer midArcsLayer = new RenderableLayer();
//            Trigon trigon = new Trigon(LatLon.fromDegrees(90, 55), LatLon.fromDegrees(0, 55),
//                LatLon.fromDegrees(0, 145), "");
//            OctantMeshMidArcs mesh = new OctantMeshMidArcs(trigon, level);
//            SurfacePolygon[] polygons = new SurfacePolygon[(int) Math.pow(4, level)];
//
//            midArcsLayer.setName("Octant_");
//            for (int j = 0; j < mesh.getTrigons().length; j++)
//            {
//                polygons[j] = mesh.getTrigons()[j].getSurfacePolygon();
//            }
//            for (SurfacePolygon polygon : polygons)
//            {
//                midArcsLayer.addRenderable(polygon);
//            }
//            insertBeforeCompass(getWwd(), midArcsLayer);

            // All globe is in gird
            for (int i = 3; i < 4; i++)
            {
                RenderableLayer midArcsLayer = new RenderableLayer();
                Trigon octant = (Trigon) Octant.getInstance().getFacet(i);
                OctantMeshMidArcs mesh = new OctantMeshMidArcs(octant, level);
                SurfacePolygon[] polygons = new SurfacePolygon[(int) Math.pow(4, level)];

                midArcsLayer.setName("Octant_" + i);
                for (int j = 0; j < mesh.getTrigons().length; j++)
                {
                    polygons[j] = mesh.getTrigons()[j].getSurfacePolygon();
                }
                for (SurfacePolygon polygon : polygons)
                {
                    midArcsLayer.addRenderable(polygon);
                }
                insertBeforeCompass(getWwd(), midArcsLayer);
            }

            // Add shapefile of test Zone
            ShapefileLayerFactory factory = new ShapefileLayerFactory();

            // attrs as one style
            final ShapeAttributes attrs = new BasicShapeAttributes();
//            attrs.setInteriorMaterial(new Material(new Color(150,100,200)));
            attrs.setInteriorMaterial(new Material(new Color(50,150,50)));
            attrs.setInteriorOpacity(0.99);
            //as randomAttrs
            //final RandomShapeAttributes randomAttrs = new RandomShapeAttributes();
            factory.setAttributeDelegate(new ShapefileRenderable.AttributeDelegate()
            {
                @Override
                public void assignAttributes(ShapefileRecord shapefileRecord,
                    ShapefileRenderable.Record renderableRecord)
                {
                    //renderableRecord.setAttributes(randomAttrs.nextAttributes().asShapeAttributes());
                    renderableRecord.setAttributes(attrs);
                }
            });

            // Load the shapefile. Define the completion callback.
            String shapefileSource = "src/edu/zhenger/data/testZone.shp";
//            String shapefileSource = "src/edu/zhenger/data/China_District.shp";

            factory.createFromShapefileSource(shapefileSource,
                new ShapefileLayerFactory.CompletionCallback()
                {
                    @Override
                    public void completion(Object result)
                    {
                        // the result is the layer the factory created
                        final RenderableLayer layer = (RenderableLayer) result;

                        layer.setName(WWIO.getFilename(layer.getName()));

                        // Add the layer to the World Window's layer list on the Event Dispatch Thread.
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                // EsriShapefile.AppFrame.this.getWwd().getModel().setGlobe(Change.getGlobe());
                                MidArcsApp.this.getWwd().getModel().getLayers().add(layer);
                                MidArcsApp.this.layerPanel.updateLayers(
                                    MidArcsApp.this.getWwd());
                            }
                        });
                    }

                    @Override
                    public void exception(Exception e)
                    {
                        Logging.logger().log(java.util.logging.Level.SEVERE, e.getMessage(), e);
                    }
                });
        }


    }
    public static void main(String[] args)
    {
        start("Class I Mid-Arcs", MidArcsApp.class);
    }
}
