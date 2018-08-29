/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.*;
import edu.zhenger.util.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function: PicView in Screen
 * @Date: 2017/12/6
 */
public class PicView extends ApplicationTemplate
{
    // as init path
    private static List<SurfacePolyline> polylineList = new ArrayList<>();
    private static Position viewCenter;

    public static class VAppFrame extends ApplicationTemplate.AppFrame
    {
        public VAppFrame()
        {
            super(true,true,false);

            RenderableLayer layer = new RenderableLayer();

            getWwd().getView().setEyePosition(new Position(viewCenter, Change.getGlobe().getRadius()));


            for (SurfacePolyline aline  : polylineList)
            {
                if (aline == null)
                {
                    break;
                }
                layer.addRenderable(aline);
            }
            layer.setName("Grid");


            // show share line
//            SurfacePolyline shareLine = new SurfacePolyline();
//            LatLon shareP1 = LatLon.interpolateGreatCircle(0.5,LatLon.fromDegrees(0,0),LatLon.fromDegrees(90,0));
//            LatLon shareP2 = LatLon.interpolateGreatCircle(0.5,LatLon.fromDegrees(0,0),LatLon.fromDegrees(0,90));
//            List<LatLon> shareList = new ArrayList<>();
//            shareList.add(shareP1);
//            shareList.add(shareP2);
//            shareLine.setLocations(shareList);
//            shareLine.setPathType(AVKey.GREAT_CIRCLE);
//
//            ShapeAttributes attrs = new BasicShapeAttributes();
//            attrs.setOutlineMaterial(new Material(Color.white));
//            attrs.setOutlineWidth(2);
//            attrs.setOutlineStippleFactor(1);
//            attrs.setOutlineStipplePattern((short) 1000);
//
//
//            shareLine.setAttributes(attrs);
//
//            layer.addRenderable(shareLine);

            // show out board of first Octant
//            List<LatLon> outBoard = new ArrayList<>();
//            LatLon p1 = LatLon.fromDegrees(0,0);
//            LatLon p2 = LatLon.fromDegrees(90,0);
//            LatLon p3 = LatLon.fromDegrees(0,90);
//            outBoard.add(p1);
//            outBoard.add(p2);
//            outBoard.add(p3);
//            ShapeAttributes attrsBoarder = new BasicShapeAttributes();
//            attrsBoarder.setOutlineMaterial(new Material(Color.WHITE));
//            attrsBoarder.setOutlineWidth(5);
//            attrsBoarder.setOutlineStippleFactor(1);
//            attrsBoarder.setOutlineStipplePattern((short) 1000);
//            SurfacePolyline boarder = new SurfacePolyline();
//            boarder.setAttributes(attrsBoarder);
//            boarder.setLocations(outBoard);
//            boarder.setPathType(AVKey.GREAT_CIRCLE);
//            boarder.setClosed(true);
//
//            layer.addRenderable(boarder);

            // simulate Entity Layer
            RenderableLayer entityLayer = new RenderableLayer();
            entityLayer.addRenderable(simulateEntity());
            entityLayer.setName("simulateEntity");

            // subdivision level
            RenderableLayer levelOne = new RenderableLayer();
            levelOne.addRenderable(firstLevel());
            levelOne.setName("Level");

            // Annotation
            RenderableLayer annotation = new RenderableLayer();
//            SurfaceText surfaceText = new SurfaceText("Desolation Wilderness", Position.fromDegrees(45, 45, 10000));
//            surfaceText.setBackgroundColor(Color.WHITE);

//            surfaceText.setTextSize();

//            surfaceText.setColor(Color.RED);
//            surfaceText.setVisible(true);
//            annotation.addRenderable(surfaceText);

            GlobeAnnotation ga = new GlobeAnnotation("AGL Annotation", Position.fromDegrees(45, 45, 0));
            ga.setAlwaysOnTop(true);
            ga.setDragEnabled(true);
            annotation.addRenderable(ga);

            annotation.setName("Annotation");
            //this.getWwd().getModel().getLayers().add(annotation);


//            insertBeforeCompass(getWwd(),annotation);
//            insertBeforeCompass(getWwd(),entityLayer);
            insertBeforeCompass(getWwd(),levelOne);
            insertBeforeCompass(getWwd(),layer);


        }

    }

    private static SurfacePolyline firstLevel()
    {
        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setOutlineMaterial(new Material(Color.MAGENTA));
        attrs.setOutlineWidth(3);
//        attrs.setOutlineStippleFactor(1);
//        attrs.setOutlineStipplePattern((short) 1000);

        LatLon A,Ba,Bc,C,a,b,c;
        A = LatLon.fromDegrees(0,0);
        Ba = LatLon.fromDegrees(90,0);
        Bc = LatLon.fromDegrees(90,90);
        C = LatLon.fromDegrees(0,90);
        a = LatLon.interpolateGreatCircle(0.5,Bc,C);
        b = LatLon.interpolateGreatCircle(0.5,A,C);
        c = LatLon.interpolateGreatCircle(0.5,A,Ba);

        List<LatLon> lineList = new ArrayList<>();
        lineList.add(A);
        lineList.add(Ba);
        lineList.add(Bc);
        lineList.add(C);
        lineList.add(A);
        lineList.add(c);
        lineList.add(b);
        lineList.add(a);
        lineList.add(c);

        SurfacePolyline polyline = new SurfacePolyline();
        polyline.setLocations(lineList);
        polyline.setPathType(AVKey.GREAT_CIRCLE);
        polyline.setAttributes(attrs);

        return polyline;
    }

    private static SurfacePolygon simulateEntity()
    {
        ShapeAttributes attributes = new BasicShapeAttributes();
        attributes.setOutlineMaterial(new Material(Color.MAGENTA));
        attributes.setOutlineOpacity(0.5);
        attributes.setInteriorMaterial(new Material(Color.MAGENTA));
        attributes.setInteriorOpacity(0.2);

        List<LatLon> entity = new ArrayList<>();
        entity.add(LatLon.fromDegrees(50,30));
        entity.add(LatLon.fromDegrees(40,60));
        entity.add(LatLon.fromDegrees(15,50));
        entity.add(LatLon.fromDegrees(10,10));
        entity.add(LatLon.fromDegrees(50,30));

        SurfacePolygon polygon = new SurfacePolygon();
        polygon.addInnerBoundary(entity);
        polygon.setAttributes(attributes);
        polygon.setPathType(AVKey.GREAT_CIRCLE);
//        surfacePolygon.getLocations(Change.getGlobe())
//        LatLon.getCenter(surfacePolygon.getLocations(Change.getGlobe()));



        return polygon;
    }
    private static SurfacePolyline simulateOutline()
    {
        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setOutlineMaterial(new Material(Color.MAGENTA));
        attrs.setOutlineWidth(3);
        attrs.setOutlineStippleFactor(1);
        attrs.setOutlineStipplePattern((short) 1000);

        List<LatLon> entity = new ArrayList<>();
        entity.add(LatLon.fromDegrees(50,30));
        entity.add(LatLon.fromDegrees(40,60));
        entity.add(LatLon.fromDegrees(15,50));
        entity.add(LatLon.fromDegrees(10,10));
        entity.add(LatLon.fromDegrees(50,30));
        // calculate entity area,by triangle formula
        // first construct triangle

//        Trigon trigon1 = new Trigon(entity.get(0),entity.get(1),entity.get(2),"");
//        Trigon trigon2 = new Trigon(entity.get(3),entity.get(1),entity.get(2),"");
//        double area = trigon1.getArea(Change.getGlobe())+trigon2.getArea(Change.getGlobe());
//        IO.write("simulateEntityArea",trigon1.getArea(Change.getGlobe()));
//        IO.write("simulateEntityArea",trigon2.getArea(Change.getGlobe()));
//        IO.write("simulateEntityArea",area);

        SurfacePolyline polyline = new SurfacePolyline();
        polyline.setLocations(entity);
        polyline.setAttributes(attrs);

        polyline.setPathType(AVKey.GREAT_CIRCLE);

//        polyline.setVisible(true);
        return polyline;
    }
    public static void init(Graph graph)
    {
        for (int i = 0; i < graph.getEdgeList().size(); i++)
        {
           // path.setVisible();
           // pathList.add(graph.getEdgeList().get(i).showPath());
            polylineList.add(graph.getEdgeList().get(i).showPath());
        }
        Trilateral trilateral = graph.getBaseTriangle();
        List<LatLon> latLonList = new ArrayList<>();
        latLonList.add(trilateral.getEdge1().getV1().getCoordinate());
        latLonList.add(trilateral.getEdge2().getV1().getCoordinate());
        latLonList.add(trilateral.getEdge3().getV1().getCoordinate());
        viewCenter = new Position(LatLon.getCenter(latLonList),0);

    }

    public static ApplicationTemplate.AppFrame start(String title, Class appFrameClass)
    {
        return  ApplicationTemplate.start(title,appFrameClass);
    }

    public static void main(String[] args)
    {
        int level = 6;
        Trilateral triangle1, triangle2, trilateral3;
//        LatLon p1,p2,p3,p4;
//
//        p1 = LatLon.interpolateGreatCircle(0.5,LatLon.fromDegrees(0,0),LatLon.fromDegrees(0,90));
//        p2 = LatLon.interpolateGreatCircle(0.5,LatLon.fromDegrees(90,0),LatLon.fromDegrees(0,0));
//        p3 = LatLon.interpolateGreatCircle(0.5,LatLon.fromDegrees(90,90),LatLon.fromDegrees(0,90));
//        p4 = LatLon.fromDegrees(0,0);
//
        Edge edge1,edge2,edge3,edge4,edge5,ab,bc,ca;
//        edge1 = new Edge(new Vertex(p1,"1"),new Vertex(p2,"2"),"1");
//        edge2 = new Edge(new Vertex(p3,"3"),new Vertex(p1,"1"),"2");
//        edge3 = new Edge(new Vertex(p2,"2"),new Vertex(p3,"3"),"3");
//        edge4 = new Edge(new Vertex(p2,"2"),new Vertex(p4,"4"),"4");
//        edge5 = new Edge(new Vertex(p4,"4"),new Vertex(p1,"1"),"5");

//        // up triangle as antic-clockwise
//        // down triangle as clockwise
//        triangle1 = new Trilateral(edge1,edge3,edge2,"0",-1);
//        triangle2 = new Trilateral(edge4,edge5,edge1,"1",1);

        Vertex vertex1,vertex2,vertex0;
        vertex0 = new Vertex(((Trigon)Octant.getInstance().getFacet(0)).getTopV(),"A");
        vertex1 = new Vertex(((Trigon)Octant.getInstance().getFacet(0)).getLeftV(),"B");
        vertex2 = new Vertex(((Trigon)Octant.getInstance().getFacet(0)).getRightV(),"C");
//        vertex1 = new Vertex(Change.fromVec4(Octant.getInstance().getFacet(0).getVertex()[0]),"0");
//        vertex2 = new Vertex(Change.fromVec4(Octant.getInstance().getFacet(0).getVertex()[1]),"1");
//        vertex3 = new Vertex(Change.fromVec4(Octant.getInstance().getFacet(0).getVertex()[2]),"2");
//        vertex0 = new Vertex(LatLon.fromDegrees(90,0),"0");
//        //vertex00 = new Vertex(LatLon.fromDegrees(90,90),"0");
//        vertex1 = new Vertex(LatLon.fromDegrees(0,0),"1");
//        vertex2 = new Vertex(LatLon.fromDegrees(0,90),"2");
        ab = new Edge(vertex0,vertex1,"0");
        bc = new Edge(vertex1,vertex2,"1");
        ca = new Edge(vertex2,vertex0,"2");
        trilateral3 = new Trilateral(ab,bc,ca,"0",1);

        //PicView.loadData(new Graph(triangle1,level));
        //PicView.loadData(new Graph(triangle2,level));
//        init(new Graph(triangle1,level));
//        init(new Graph(triangle2,level));
        init(new Graph(trilateral3,level));


        start("SimulateGeographicEntity",PicView.VAppFrame.class);
    }
}
