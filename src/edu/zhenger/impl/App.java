/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.util.Change;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.formats.shapefile.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/31
 */
public class App extends ApplicationTemplate//EsriShapefile
{
    public static class AppFrame extends ApplicationTemplate.AppFrame//EsriShapefile.AppFrame
    {
        public AppFrame()
        {
            super(true, true, false);

            int level = 0;

//            PolygonData polygonData = null;
//            Calculator calculator = null;

//            RenderableLayer trigon = new RenderableLayer();
//            trigon.setName("Trigon");
//            for (int i = 2; i < 3; i++)
//            {
//                outline(i);
//
//                calculator = new Calculator(level, i);
//                polygonData = new PolygonData(calculator.getGeocodeOrderCells());
//                for (SurfacePolygon sp : polygonData.getPolygons())
//                {
//                    trigon.addRenderable(sp);
//                }
//            }

//            getWwd().getModel().getLayers().add(trigon);
//             polygonData layers
            RenderableLayer layer = new RenderableLayer();
            //showConterminousUS(layer);
            showLevelOne(layer);
            getWwd().getModel().getLayers().add(layer);

            getWwd().getView().setEyePosition(Position.fromDegrees(35, -100, 1.5 * Change.getGlobe().getRadius()));
        }

        private void showConterminousUS(RenderableLayer layer)
        {
            ShapeAttributes attributes = new BasicShapeAttributes();
            attributes.setInteriorOpacity(0.5);
            attributes.setInteriorMaterial(Material.BLUE);//BLACK

            String shpfileSource = "src/edu/zhenger/data/UnitedStates.shp";
            Shapefile shpfile = new Shapefile(shpfileSource);

            ShapefileRecordPolygon polygon = shpfile.nextRecord().asPolygonRecord();

            List<LatLon> latLons = new ArrayList<LatLon>();
            for (double[] d : polygon.getPoints(0))
            {
                latLons.add(LatLon.fromDegrees(d[1], d[0]));
            }

            SurfacePolygon shape = new SurfacePolygon(latLons);
            shape.setAttributes(attributes);
            shape.setPathType(AVKey.GREAT_CIRCLE);

            System.out.println("Area = " + shape.getArea(Change.getGlobe()));

            //ShapefileRenderable renderable = new ShapefilePolygons(shpfile);
            //RenderableLayer layer = new RenderableLayer();
            layer.setName("ConterminousUS");
            layer.addRenderable(shape);
            insertBeforeCompass(getWwd(), layer);
        }

        private void outline(int facet)
        {
            ShapeAttributes attributes = new BasicShapeAttributes();
            attributes.setOutlineWidth(5);
//                attributes.setOutlineStippleFactor(1);
//                attributes.setOutlineStipplePattern((short) 1000);
            attributes.setOutlineMaterial(new Material(Color.MAGENTA));
            attributes.setOutlineStippleFactor(1);
            attributes.setOutlineStipplePattern((short) 1000);
            attributes.setInteriorOpacity(0.0);
            //attributes.setImageScale(1.0);
            //attributes.

            Calculator calculator = new Calculator(1, facet);
            PolygonData polygonData = new PolygonData(calculator.getGeocodeOrderCells());

            RenderableLayer outline = new RenderableLayer();
            outline.setName("outline" + facet);
            for (SurfacePolygon sp : polygonData.getPolygons())
            {
                sp.setAttributes(attributes);
                outline.addRenderable(sp);
            }
            insertBeforeCompass(getWwd(), outline);
        }

        private void showLevelOne(RenderableLayer layer)
        {
            ShapeAttributes attributes = new BasicShapeAttributes();
//            attributes.setInteriorOpacity(0.5);
//            attributes.setInteriorMaterial(Material.BLUE);//BLACK
            attributes.setOutlineMaterial(new Material(Color.MAGENTA));
            attributes.setOutlineWidth(20);


            List<LatLon> latLons = new ArrayList<LatLon>();
            latLons.add(LatLon.fromDegrees(90,-90));
            latLons.add(LatLon.fromDegrees(0,-90));
            latLons.add(LatLon.fromDegrees(-90,-90));
            latLons.add(LatLon.fromDegrees(-90,0));
            latLons.add(LatLon.fromDegrees(0,0));
            latLons.add(LatLon.fromDegrees(90,0));
            latLons.add(LatLon.fromDegrees(90,90));
            latLons.add(LatLon.fromDegrees(0,90));
            latLons.add(LatLon.fromDegrees(-90,90));
            latLons.add(LatLon.fromDegrees(-90,180));
            latLons.add(LatLon.fromDegrees(0,180));
            latLons.add(LatLon.fromDegrees(90,180));
            latLons.add(LatLon.fromDegrees(90,-90));
            latLons.add(LatLon.fromDegrees(0,-90));
            latLons.add(LatLon.fromDegrees(0,0));
            latLons.add(LatLon.fromDegrees(0,90));
            latLons.add(LatLon.fromDegrees(0,180));
            latLons.add(LatLon.fromDegrees(0,-90));


            SurfacePolyline polyline = new SurfacePolyline(latLons);
            polyline.setAttributes(attributes);
            polyline.setPathType(AVKey.GREAT_CIRCLE);
//            polyline.setPathType(AVKey.RHUMB_LINE);

            layer.setName("Level One");
            layer.addRenderable(polyline);
            insertBeforeCompass(getWwd(), layer);
        }
    }

    public static void main(String[] args)
    {
        start("Application", AppFrame.class);
    }
}
