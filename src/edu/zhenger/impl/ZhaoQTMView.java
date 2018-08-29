/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.Cell;
import edu.zhenger.model.*;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.formats.shapefile.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function: impl ZhaoQTM
 * @Date: 2018/8/3
 */
public class ZhaoQTMView extends ApplicationTemplate
{
    public static class ZhaoQTMApp extends ApplicationTemplate.AppFrame
    {
        public ZhaoQTMApp()
        {
            super();
            int level = 4;
            Cell cell = Octant.getInstance().getFacet(0);
            Cell cell1 = Octant.getInstance().getFacet(6);

            ZhaoQTM qtm1 = new ZhaoQTM((Trigon) cell, level);
            ZhaoQTM qtm2 = new ZhaoQTM(cell1, level);
            draw(qtm1);
            draw(qtm2);


            getZone();
        }

        private void getZone()
        {
            ShapefileLayerFactory factory = new ShapefileLayerFactory();

            // Specify an attribute delegate to assign random attributes to each shapefile record.
            //final RandomShapeAttributes randomAttrs = new RandomShapeAttributes();
            final ShapeAttributes attributes = new BasicShapeAttributes();
            attributes.setInteriorMaterial(new Material(new Color(200, 200, 200)));
            factory.setAttributeDelegate(new ShapefileRenderable.AttributeDelegate()
            {
                @Override
                public void assignAttributes(ShapefileRecord shapefileRecord,
                    ShapefileRenderable.Record renderableRecord)
                {
                    renderableRecord.setAttributes(attributes);
                }
            });

            this.getWwjPanel().setBackground(new Color(200, 200, 200));

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
                                ZhaoQTMApp.this.getWwd().getModel().getLayers().add(layer);
                                ZhaoQTMApp.this.layerPanel.updateLayers(
                                    ZhaoQTMApp.this.getWwd());
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

        private void draw(ZhaoQTM qtm)
        {
            LatLon[][] vertexs = qtm.getLatLons();

//            System.out.println(vertexs.length);
////            for (int i = 0; i < vertexs.length; i++)
////            {
////                System.out.print(i + "------");
////                System.out.println(vertexs[i].length);
////                for (int j = 0; j < vertexs[i].length; j++)
////                {
////                    System.out.println(j + ": ");
////                    System.out.println(vertexs[i][j]);
////                }
////            }

            // for waists
            ShapeAttributes waistAttrs = new BasicShapeAttributes();
            waistAttrs.setOutlineMaterial(new Material(new Color(0, 255, 0)));
            RenderableLayer waistLayer = new RenderableLayer();
            waistLayer.setName("TrigonWaist");
            List<Position> leftPathPosition, rightPathPosition;
            List<Position> pathPosition;
            Path leftWaistPath, rightWaistPath, path;
            List<Path> waistPathList = new ArrayList<>();
            List<Path> pathList = new ArrayList<>();
            RenderableLayer layer = new RenderableLayer();
            for (int i = 0; i < (vertexs.length - 1); i++)
            {
                for (int j = 0; j < vertexs[i].length; j++)
                {
                    leftWaistPath = new Path();
                    rightWaistPath = new Path();
                    leftWaistPath.setAttributes(waistAttrs);
                    rightWaistPath.setAttributes(waistAttrs);
                    leftWaistPath.setPathType(AVKey.GREAT_CIRCLE);
                    rightWaistPath.setPathType(AVKey.GREAT_CIRCLE);
                    leftWaistPath.setFollowTerrain(true);
                    rightWaistPath.setFollowTerrain(true);
                    leftWaistPath.setTerrainConformance(1);
                    rightWaistPath.setTerrainConformance(1);
                    leftWaistPath.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
                    rightWaistPath.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
                    leftPathPosition = new ArrayList<>();
                    rightPathPosition = new ArrayList<>();

                    if (i == 0)
                    {
                        // && Math.abs(vertexs[0][0].getLatitude().degrees) == 90.0

                        leftPathPosition.add(Position.fromDegrees(90.0, vertexs[1][j].longitude.degrees));
                        leftPathPosition.add(new Position(vertexs[1][j], 0));
                        leftWaistPath.setPositions(leftPathPosition);
                        waistPathList.add(leftWaistPath);

                        pathPosition = new ArrayList<>();
                        pathPosition.add(Position.fromDegrees(90.0, vertexs[1][j].longitude.degrees));
                        pathPosition.add(new Position(vertexs[1][j], 0));
                        path = new Path(pathPosition);
                        path.setPathType(AVKey.GREAT_CIRCLE);
                        path.setAttributes(waistAttrs);
                        path.setFollowTerrain(true);
                        path.setTerrainConformance(1);
                        path.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
                        pathList.add(path);

                        pathPosition = new ArrayList<>();
                        pathPosition.add(Position.fromDegrees(90.0, vertexs[1][j + 1].longitude.degrees));
                        pathPosition.add(new Position(vertexs[1][j + 1], 0));
                        path = new Path(pathPosition);
                        path.setPathType(AVKey.GREAT_CIRCLE);
                        path.setAttributes(waistAttrs);
                        path.setFollowTerrain(true);
                        path.setTerrainConformance(1);
                        path.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
                        pathList.add(path);

                        rightPathPosition.add(Position.fromDegrees(90.0, vertexs[1][j + 1].longitude.degrees));
                        rightPathPosition.add(new Position(vertexs[1][j + 1], 0));
                        rightWaistPath.setPositions(rightPathPosition);
                        waistPathList.add(rightWaistPath);
                    }
                    else
                    {
                        leftPathPosition.add(new Position(vertexs[i][j], 0));
                        leftPathPosition.add(new Position(vertexs[i + 1][j], 0));
                        leftWaistPath.setPositions(leftPathPosition);
                        waistPathList.add(leftWaistPath);

                        rightPathPosition.add(new Position(vertexs[i][j], 0));
                        rightPathPosition.add(new Position(vertexs[i + 1][j + 1], 0));
                        rightWaistPath.setPositions(rightPathPosition);
                        waistPathList.add(rightWaistPath);

                        pathPosition = new ArrayList<>();
                        pathPosition.add(new Position(vertexs[i][j], 0));
                        pathPosition.add(new Position(vertexs[i + 1][j], 0));
                        path = new Path(pathPosition);
                        path.setPathType(AVKey.GREAT_CIRCLE);
                        path.setAttributes(waistAttrs);
                        path.setFollowTerrain(true);
                        path.setTerrainConformance(1);
                        path.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
                        pathList.add(path);

                        pathPosition = new ArrayList<>();
                        pathPosition.add(new Position(vertexs[i][j], 0));
                        pathPosition.add(new Position(vertexs[i + 1][j + 1], 0));
                        path = new Path(pathPosition);
                        path.setPathType(AVKey.GREAT_CIRCLE);
                        path.setAttributes(waistAttrs);
                        path.setFollowTerrain(true);
                        path.setTerrainConformance(1);
                        path.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
                        pathList.add(path);
                    }
                }

                //waistPathList.add(waistPath);
            }
            layer.addRenderables(pathList);
            insertBeforeCompass(getWwd(), layer);

//            waistLayer.addRenderables(waistPathList);
//            insertBeforeCompass(getWwd(), waistLayer);

            // for base
            ShapeAttributes baseAttrs = new BasicShapeAttributes();
            baseAttrs.setOutlineMaterial(new Material(new Color(255, 0, 255)));
            RenderableLayer baseLayer = new RenderableLayer();
            baseLayer.setName("TrigonBase");
            List<Position> positions;// = new ArrayList<>();
            List<Path> basePathList = new ArrayList<>();
            Path basePath;
            for (LatLon[] vertex : vertexs)
            {
                basePath = new Path();
                basePath.setAttributes(baseAttrs);
                basePath.setPathType(AVKey.LINEAR);
                basePath.setFollowTerrain(true);
                basePath.setTerrainConformance(1);
                basePath.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
                positions = new ArrayList<>();
//                basePath.clearList();
                for (LatLon aVertex : vertex)
                {
                    positions.add(new Position(aVertex, 0));
                }
                basePath.setPositions(positions);
                basePathList.add(basePath);
            }
            baseLayer.addRenderables(basePathList);

            insertBeforeCompass(getWwd(), baseLayer);

//            Path initPath = new Path();
//            List<Position> initPositionList = new ArrayList<>();
//            initPositionList.add(Position.fromDegrees(0, 0));
//            initPositionList.add(Position.fromDegrees(90, 0));
//            initPath.setPositions(initPositionList);
//            initPath.setAttributes(waistAttrs);
//            initPath.setTerrainConformance(1);
//            initPath.setFollowTerrain(true);
//            initPath.setPathType(AVKey.GREAT_CIRCLE);
//
//            RenderableLayer layer = new RenderableLayer();
//            layer.addRenderable(initPath);
//
//            insertBeforeCompass(getWwd(), layer);
        }
    }

    public static void main(String[] args)
    {
        start("ZhaoQTMView", ZhaoQTMApp.class);
    }
}
