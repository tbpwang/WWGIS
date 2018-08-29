/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.*;
import edu.zhenger.util.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.render.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Author: WangZheng Email: tbpwang@gmail.com
 * Function: The model of Triangular subdivide is a basic unit in Sphere
 * Date: 2017/10/10
 */
public class Trigon implements Cell
{
    private Vec4 topV, leftV, rightV;
    private LatLon center;
    private String geocode;
    private int orientation;//1向上；-1向下
    private SurfacePolygon surfacePolygon;
    private LatLon[] latLons;

    public Trigon(LatLon top, LatLon left, LatLon right, String geocode)
    {
        if (top == null || left == null || right == null)
        {
            throw new Mistake("nullValue: VertexIsNull");
        }

        latLons = new LatLon[3];
        latLons[0] = top;
        latLons[1] = left;
        latLons[2] = right;
        Vec4 a, b, c, d;
        a = Change.fromLatLon(top);
        b = Change.fromLatLon(left);
        c = Change.fromLatLon(right);
        d = new Vec4((a.x + b.x + c.x) / 3, (a.y + b.y + c.y) / 3, (a.z + b.z + c.z) / 3);

        this.topV = new Vec4(a.getX(), a.getY(), a.getZ());
        this.leftV = new Vec4(b.getX(), b.getY(), b.getZ());
        this.rightV = new Vec4(c.getX(), c.getY(), c.getZ());
        this.geocode = geocode;

        this.center = new LatLon(Change.fromVec4(d));

        List<LatLon> side = new ArrayList<>();
        side.add(top);
        side.add(left);
        side.add(right);

        surfacePolygon = new SurfacePolygon();
        surfacePolygon.setLocations(side);
        if (!surfacePolygon.getPathType().equals(AVKey.GREAT_CIRCLE))
        {
            System.out.println("Surface type: " + surfacePolygon.getPathType());
            surfacePolygon.setPathType(AVKey.GREAT_CIRCLE);
        }
    }

    public SurfacePolygon getSurfacePolygon()
    {
        ShapeAttributes attributes = new BasicShapeAttributes();
//        attributes.setOutlineMaterial(new Material(new Color(0,150,0)));
//        attributes.setOutlineMaterial(new Material(new Color(130,0,130)));
        attributes.setOutlineMaterial(new Material(new Color(200,200,200)));
        attributes.setInteriorOpacity(0.1);
        surfacePolygon.setAttributes(attributes);
        return surfacePolygon;
    }

    public void setOrientation(int orientation)
    {
        this.orientation = orientation;
    }

    public double getArea()
    {
        return getArea(Change.getGlobe());
    }

    public double getPerimeter()
    {
        return getPerimeter(Change.getGlobe());
    }

    @Override
    public double computeCompactness()
    {
        return getArea(Change.getGlobe()) / getPerimeter(Change.getGlobe());
    }

    @Override
    public Subdivision subdivide()
    {
        return new Partition(this);
    }

    class Partition implements Subdivision
    {
        private Trigon cell;

        private Partition(Trigon cell)
        {
            this.cell = cell;
        }

        @Override
        public Cell[] getChildren()
        {
            // 固定方向编码

            if (cell == null)
            {
                throw new Mistake("CellIsNullError");
            }
            String geocode = cell.getGeocode();
            LatLon top = cell.getTopV();
            LatLon left = cell.getLeftV();
            LatLon right = cell.getRightV();

            // GreatCircle bisect.
            LatLon a = LatLon.interpolateGreatCircle(0.5, left, right);

            LatLon tempAb = top;
            LatLon tempAc = top;
            double epsilon = 1e-8;
            if (90d - Math.abs(top.getLatitude().getDegrees()) < epsilon)
            {
                tempAb = LatLon.fromDegrees(90, left.getLongitude().getDegrees());
                tempAc = LatLon.fromDegrees(90, right.getLongitude().getDegrees());
            }

            LatLon b = LatLon.interpolateGreatCircle(0.5, tempAb, left);
            LatLon c = LatLon.interpolateGreatCircle(0.5, tempAc, right);

            // As children
            // 固定方向编码
            Cell[] children = new Cell[4];
            children[0] = new Trigon(a, b, c, geocode + "0");
            children[1] = new Trigon(top, b, c, geocode + "1");
            children[2] = new Trigon(b, left, a, geocode + "2");
            children[3] = new Trigon(c, a, right, geocode + "3");

            return children;
        }

        public Globe getGlobe()
        {
            return null;
        }
    }

//    public SurfacePolygon surfacePolygon()
//    {
//        List<LatLon> vertice = new ArrayList<>();
//        vertice.add(this.getTopV());
//        vertice.add(this.getLeftV());
//        vertice.add(this.getRightV());
//
//        SurfacePolygon polygon = new SurfacePolygon(vertice);
//        polygon.setPathType(AVKey.GREAT_CIRCLE);
//
//        ShapeAttributes attributes = new BasicShapeAttributes();
//        attributes.setInteriorOpacity(0.0);
////        attributes.setInteriorMaterial(new Material(Color.WHITE));
//        attributes.setOutlineMaterial(new Material(Color.PINK));
//        //attributes.setOutlineOpacity(0.5);
//        attributes.setOutlineWidth(1);
////        attributes.setOutlineStippleFactor(1);
////        attributes.setOutlineStipplePattern((short) 1000);
//        polygon.setAttributes(attributes);
//
//        return polygon;
//    }

//    /**
//     * This method follows Wang Qian(2017)
//     *
//     * @param geocode
//     *
//     * @return row
//     */
//    private int getRow(String geocode)
//    {
//        int row = 1;
//        char[] letters = geocode.toCharArray();
//        if (letters.length == 1)
//        {
//            return 1;
//        }
//        // 表示三角形的指向,1指向上，-1指向下
//        // 与初始三角形相同方向的为1，相反方向的为-1
//        int direction = 0;
////        if (letters[0] == '4' || letters[0] == '5' || letters[0] == '6' || letters[0] == '7')
////        {
//////            letters[0]=0;
////            direction = 1;
////        }
//        //letters[0] = '0';
////        for (char letter : letters)
////        {
////            switch (letter)
//        for (int i = 1; i < letters.length; i++)
//        {
//            switch (letters[i])
//            {
//                case '0':
//
//                    if (direction % 2 == 0)
//                    {
//                        row = (row - 1) * 2 + 1;
//                    }
//                    else
//                    {
//                        row = (row - 1) * 2 + 2;
//                    }
//                    direction++;
//                    break;
//                case '1':
//                    if (direction % 2 == 0)
//                    {
//                        row = (row - 1) * 2 + 2;
//                    }
//                    else
//                    {
//                        row = (row - 1) * 2 + 1;
//                    }
//                    break;
//                case '2':
//                    if (direction % 2 == 0)
//                    {
//                        row = (row - 1) * 2 + 1;
//                    }
//                    else
//                    {
//                        row = (row - 1) * 2 + 2;
//                    }
//                    break;
//                case '3':
//                    if (direction % 2 == 0)
//                    {
//                        row = (row - 1) * 2 + 1;
//                    }
//                    else
//                    {
//                        row = (row - 1) * 2 + 2;
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//        return row;
//    }
//
//    /**
//     * This method follows Zhao Xuesheng(2003)
//     *
//     * @param geocode
//     * @param row
//     *
//     * @return column
//     */
//    private int getColumn(String geocode, int row)
//    {
//        int col = 1;
//        char[] letters = geocode.toCharArray();
//        if (letters.length == 1)
//        {
//            return 1;
//        }
//        int level = letters.length - 1;
//        int maxRow = (int) Math.pow(2, level);
//        //letters[0] = '0';
//        for (int i = 1; i < letters.length; i++)
//        {
//            switch (letters[i])
//            {
//                case '0':
//                    maxRow = maxRow / 2;
//                    col = col + (2 * (maxRow - row) + 1);
//                    row = maxRow - row + 1;
//                    break;
//                case '1':
//                    maxRow = maxRow / 2;
//                    row = row - maxRow;
//                    break;
//                case '2':
//                    maxRow = maxRow / 2;
//                    break;
//                case '3':
//                    col += maxRow;
//                    maxRow = maxRow / 2;
//                    break;
//                default:
//                    break;
//            }
//        }
//        return col;
//    }

    @Override
    public String toString()
    {
//        return "Cell{" +
//            "geocode=" + geocode +
//            ", topV=" + Change.getInstance().fromVec4(topV).toString() +
//            ", leftV=" + Change.getInstance().fromVec4(leftV).toString() +
//            ", rightV=" + Change.getInstance().fromVec4(rightV).toString() +
//            '}';
        return geocode.substring(1, geocode.length()) + "\t" + this.getArea(Change.getGlobe()) + "\t"
            + this.computeCompactness()
            + "\t";
    }

//    @Override
//    public Ranks computeRanks()
//    {
//        int row = getRow(geocode);
//        int col = getColumn(geocode, row);
//        return new Ranks(row, col, this.geocode);
//    }

    @Override
    public int getOrientation()
    {
        // up is 1, down is -1
        // 表示三角形的指向,1指向上，-1指向下
        // 与初始三角形相同方向的为1，相反方向的为-1
        return orientation;
    }

    // @Override
    //public Cell[] subdivide()
//    {
//        return OQTM.getInstance().subdivide(this,null);
//    }

//    @Override
//    public double measureDistance(Cell cell)
//    {
//        return LatLon.greatCircleDistance(this.center, cell.getRefPoint()).getRadians() * globe.getRadius();
//    }

    @Override
    public Vec4[] getVertex()
    {
        return new Vec4[] {topV, leftV, rightV};
    }

    @Override
    public LatLon[] getLatLons()
    {
        return latLons;
    }

    //@Override
    public LatLon getTopV()
    {
        return Change.fromVec4(topV);
    }

    //@Override
    public LatLon getLeftV()
    {
        return Change.fromVec4(leftV);
    }

    //@Override
    public LatLon getRightV()
    {
        return Change.fromVec4(rightV);
    }

    @Override
    public Globe getGlobe()
    {
        return Change.getGlobe();
    }

    @Override
    public Side getSide()
    {
        return new Side(this);
    }

    @Override
    public String getGeocode()
    {
        return geocode;
    }

    @Override
    public LatLon getRefPoint()
    {
        return center;
    }

    @Override
    public double getArea(Globe globe)
    {
        // getSphereArea
        //已知三边abc求面积
        //公式来源：一般球面三角形计算公式
        //数学手册编写组，数学手册，北京：高等教育出版社，2010年印，p49-50.
        //公式半角: sin(A/2)=sqrt(sin(p-b)*sin(p-c)/(sin(b)*sin(c)))
        // S = (A+B+C-PI)*R^2

        LatLon latLonA, latLonB, latLonC;
        latLonA = Change.fromVec4(topV);
        latLonB = Change.fromVec4(leftV);
        latLonC = Change.fromVec4(rightV);

        double a, b, c, p;
        double A, B, C;

        a = LatLon.greatCircleDistance(latLonB, latLonC).getRadians();
        b = LatLon.greatCircleDistance(latLonC, latLonA).getRadians();
        c = LatLon.greatCircleDistance(latLonA, latLonB).getRadians();
        // half-side of triangle
        p = (a + b + c) / 2;

        A = 2 * Math.asin(Math.sqrt(Math.sin(p - b) * Math.sin(p - c) / (Math.sin(b) * Math.sin(c))));
        B = 2 * Math.asin(Math.sqrt(Math.sin(p - c) * Math.sin(p - a) / (Math.sin(c) * Math.sin(a))));
        C = 2 * Math.asin(Math.sqrt(Math.sin(p - a) * Math.sin(p - b) / (Math.sin(a) * Math.sin(b))));

        return globe.getRadius() * globe.getRadius() * (A + B + C - Math.PI);
    }

    @Override
    public double getPerimeter(Globe globe)
    {
        double a = leftV.angleBetween3(rightV).getRadians();
        double b = rightV.angleBetween3(topV).getRadians();
        double c = topV.angleBetween3(leftV).getRadians();

        return (a + b + c) * globe.getRadius();
    }

    @Override
    public double getWidth(Globe globe)
    {
        // output min side as width

//        double a = leftV.distanceTo2(rightV);
//        double b = rightV.distanceTo2(topV);
//        double c = topV.distanceTo2(leftV);

        double a = Math.abs(leftV.angleBetween3(rightV).getRadians());
        double b = Math.abs(rightV.angleBetween3(topV).getRadians());
        double c = Math.abs(topV.angleBetween3(leftV).getRadians());

        double min = (a < b ? a : b) < c ? (a < b ? a : b) : c;
        return min * globe.getRadius();
    }

    @Override
    public double getHeight(Globe globe)
    {
        // output the distance of great circle,
        // from topV vertex along longitude to opposite side

        LatLon latLonA = Change.fromVec4(topV);
        LatLon latLonB = Change.fromVec4(leftV);
        LatLon latLonC = Change.fromVec4(rightV);

        double lat = Angle.average(latLonB.getLatitude(), latLonC.getLatitude()).getRadians();
        double lon = Math.abs(latLonA.getLatitude().getDegrees()) == 90 ? Angle.average(latLonB.getLongitude(),
            latLonC.getLongitude()).getRadians() : latLonA.getLongitude().getRadians();
        LatLon temp = LatLon.fromRadians(lat, lon);

        return LatLon.greatCircleDistance(latLonA, temp).getRadians() * globe.getRadius();
    }

//    @Override
//    public double getLength(Globe globe)
//    {
//        // output max side as width
////        LatLon latLonA = Change.getInstance().fromVec4(topV);
////        LatLon latLonB = Change.getInstance().fromVec4(leftV);
////        LatLon latLonC = Change.getInstance().fromVec4(rightV);
//        double a = Math.abs(leftV.angleBetween3(rightV).getRadians());
//        double b = Math.abs(rightV.angleBetween3(topV).getRadians());
//        double c = Math.abs(topV.angleBetween3(leftV).getRadians());
////        double a = LatLon.measureDistance(latLonB, latLonC).getRadians();
////        double b = LatLon.measureDistance(latLonC, latLonA).getRadians();
////        double c = LatLon.measureDistance(latLonA, latLonB).getRadians();
//        double max = (a > b ? a : b) > c ? (a > b ? a : b) : c;
//        return max * globe.getRadius();
//    }
}
