/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

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
 * @Date: 2018/1/18
 */
public class EsriShapefile extends ApplicationTemplate
{
    public static class AppFrame extends ApplicationTemplate.AppFrame
    {
        public AppFrame()
        {
            super();
            ShapefileLayerFactory factory = new ShapefileLayerFactory();

            // Specify an attribute delegate to assign random attributes to each shapefile record.
            //final RandomShapeAttributes randomAttrs = new RandomShapeAttributes();
            final ShapeAttributes attributes = new BasicShapeAttributes();
            attributes.setInteriorMaterial(new Material(new Color(50,120,50)));
//            attributes.setInteriorOpacity(0.2);
            factory.setAttributeDelegate(new ShapefileRenderable.AttributeDelegate()
            {
                @Override
                public void assignAttributes(ShapefileRecord shapefileRecord,
                    ShapefileRenderable.Record renderableRecord)
                {
//                    renderableRecord.setAttributes(randomAttrs.nextAttributes().asShapeAttributes());
                    renderableRecord.setAttributes(attributes);
                }
            });

           this.getWwjPanel().setBackground(new Color(255,255,255));



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
                                EsriShapefile.AppFrame.this.getWwd().getModel().getLayers().add(layer);
                                EsriShapefile.AppFrame.this.layerPanel.updateLayers(
                                    EsriShapefile.AppFrame.this.getWwd());
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
        start("World Wind Shapefiles", AppFrame.class);
    }
}
