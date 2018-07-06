/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.*;
import gov.nasa.worldwind.formats.shapefile.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwind.util.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import gov.nasa.worldwindx.examples.util.RandomShapeAttributes;

import javax.swing.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/7/6
 */
public class ClassIMidArcsView extends ApplicationTemplate
{
    public static class MidArcsAPP extends ApplicationTemplate.AppFrame
    {
        public MidArcsAPP()
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
            for (int i = 0; i < 8; i++)
            {
                RenderableLayer midArcsLayer = new RenderableLayer();
                Trigon trigon = (Trigon) Octant.getInstance().getFacet(i);
                OctantMeshMidArcs mesh = new OctantMeshMidArcs(trigon, level);
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

            final RandomShapeAttributes randomAttrs = new RandomShapeAttributes();
            factory.setAttributeDelegate(new ShapefileRenderable.AttributeDelegate()
            {
                @Override
                public void assignAttributes(ShapefileRecord shapefileRecord,
                    ShapefileRenderable.Record renderableRecord)
                {
                    renderableRecord.setAttributes(randomAttrs.nextAttributes().asShapeAttributes());
                }
            });

            // Load the shapefile. Define the completion callback.
            String shapefileSource = "src/edu/zhenger/data/testZone.shp";

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
                                MidArcsAPP.this.getWwd().getModel().getLayers().add(layer);
                                MidArcsAPP.this.layerPanel.updateLayers(
                                    MidArcsAPP.this.getWwd());
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

        public static void main(String[] args)
        {
            start("Class I Mid-Arcs", MidArcsAPP.class);
        }
    }
}
