/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.*;
import edu.zhenger.Cell;
import edu.zhenger.util.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.Globe;

import java.util.Arrays;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/18
 */
public class QTM implements Grid
{
    private int level;
    // storage is as row and col order.
    private Trigon[][] ranksCells;

    private Neighbor neighbor;

    private Globe globe;

    public Globe getGlobe()
    {
        return globe;
    }

    public QTM(int level)
    {
        this.level = level;
        ranksCells = new Trigon[(int) Math.pow(2, level)][];
        globe = Sphere.getInstance();
    }

    public class Neighbor implements Adjacency
    {
        Trigon flat;
        // Angle Point
        Ranks[] vertexRanks = null;
        // half side Angle Point
        Ranks[] subVertexRanks = null;
        // Edge Line
        Ranks[] sideRanks = null;

        private Neighbor(Trigon cell)
        {
            if (cell != null)
            {
                flat = cell;
            }
            else
            {
                throw new Mistake("CellIsNull");
            }

            // row and column is from 1 to larger,
            // whereas row is from equator and column is from left to right.

            // this cell's row and column
            int row = QTM.this.computeRanks(cell).getRow();
            int col = QTM.this.computeRanks(cell).getCol();

            // shareUnilateralCells get the same formula with up and down

            // two situation up(1) and down(-1)
            if (cell.getOrientation() == 1)
            {
                // up standing triangle
                // how many share side cells (<=3)
                int counter = 0;
                if (isRow(row - 1) && isColumn(row - 1, col + 1))
                    counter++;
                if (isColumn(row, col - 1))
                    counter++;
                if (isColumn(row, col + 1))
                    counter++;

                // if exist, new rank
                int index = 0;
                if (counter != 0)
                {
                    sideRanks = new Ranks[counter];

                    if (search(row - 1, col + 1) != null)
                        sideRanks[index++] = QTM.this.computeRanks(search(row - 1, col + 1));

                    if (search(row, col - 1) != null)
                        sideRanks[index++] = QTM.this.computeRanks(search(row, col - 1));

                    if (search(row, col + 1) != null)
                        sideRanks[index] = QTM.this.computeRanks(search(row, col + 1));
                }

                // how many shareBilateralCells (<=3)
                counter = 0;
                if (isRow(row - 1) && isColumn(row - 1, col - 1))
                    counter++;
                if (isRow(row - 1) && isColumn(row - 1, col + 3))
                    counter++;
                if (isRow(row + 1) && isColumn(row + 1, col - 1))
                    counter++;

                // if exist, new Ranks[counter]
                index = 0;
                if (counter != 0)
                {
                    vertexRanks = new Ranks[counter];

                    if (search(row - 1, col - 1) != null)
                        vertexRanks[index++] = QTM.this.computeRanks(search(row - 1, col - 1));
                    if (search(row - 1, col + 3) != null)
                        vertexRanks[index++] = QTM.this.computeRanks(search(row - 1, col + 3));
                    if (search(row + 1, col - 1) != null)
                        vertexRanks[index] = QTM.this.computeRanks(search(row + 1, col - 1));
                }

                // how many shareUnilateralCells(<=6)
                counter = 0;
                if (isRow(row - 1) && isColumn(row - 1, col))
                    counter++;
                if (isRow(row - 1) && isColumn(row - 1, col + 2))
                    counter++;
                if (isRow(row) && isColumn(row, col - 2))
                    counter++;
                if (isRow(row) && isColumn(row, col + 2))
                    counter++;
                if (isRow(row + 1) && isColumn(row + 1, col - 2))
                    counter++;
                if (isRow(row + 1) && isColumn(row + 1, col))
                    counter++;

                // if exist, new Ranks[counter]
                index = 0;
                if (counter != 0)
                {
                    subVertexRanks = new Ranks[counter];

                    if (search(row - 1, col) != null)
                        subVertexRanks[index++] = QTM.this.computeRanks(search(row - 1, col));
                    if (search(row - 1, col + 2) != null)
                        subVertexRanks[index++] = QTM.this.computeRanks(search(row - 1, col + 2));
                    if (search(row, col - 2) != null)
                        subVertexRanks[index++] = QTM.this.computeRanks(search(row, col - 2));
                    if (search(row, col + 2) != null)
                        subVertexRanks[index++] = QTM.this.computeRanks(search(row, col + 2));
                    if (search(row + 1, col - 2) != null)
                        subVertexRanks[index++] = QTM.this.computeRanks(search(row + 1, col - 2));
                    if (search(row + 1, col) != null)
                        subVertexRanks[index] = QTM.this.computeRanks(search(row + 1, col));
                }
            }
            else if (cell.getOrientation() == -1)
            {
                // how many shareSideCells
                int counter = 0;
                if (isRow(row) && isColumn(row, col - 1))
                    counter++;
                if (isRow(row) && isColumn(row, col + 1))
                    counter++;
                if (isRow(row + 1) && isColumn(row + 1, col - 1))
                    counter++;

                // if exist, new Ranks[counter]
                int index = 0;
                if (counter != 0)
                {
                    sideRanks = new Ranks[counter];

                    if (search(row, col - 1) != null)
                        sideRanks[index++] = QTM.this.computeRanks(search(row, col - 1));
                    if (search(row, col + 1) != null)
                        sideRanks[index++] = QTM.this.computeRanks(search(row, col + 1));
                    if (search(row + 1, col - 1) != null)
                        sideRanks[index] = QTM.this.computeRanks(search(row + 1, col - 1));
                }

                // how many shareBilateralCells
                counter = 0;
                if (isRow(row - 1) && isColumn(row - 1, col + 1))
                    counter++;
                if (isRow(row + 1) && isColumn(row + 1, col - 3))
                    counter++;
                if (isRow(row + 1) && isColumn(row + 1, col + 1))
                    counter++;

                // if exist, new Ranks[counter]
                index = 0;
                if (counter != 0)
                {
                    vertexRanks = new Ranks[counter];

                    if (search(row - 1, col + 1) != null)
                        vertexRanks[index++] = QTM.this.computeRanks(search(row - 1, col + 1));
                    if (search(row + 1, col - 3) != null)
                        vertexRanks[index++] = QTM.this.computeRanks(search(row + 1, col - 3));
                    if (search(row + 1, col + 1) != null)
                        vertexRanks[index] = QTM.this.computeRanks(search(row + 1, col + 1));
                }

                // how many shareUnilateralCells
                counter = 0;
                if (isRow(row - 1) && isColumn(row - 1, col))
                    counter++;
                if (isRow(row - 1) && isColumn(row - 1, col + 2))
                    counter++;
                if (isRow(row) && isColumn(row, col - 2))
                    counter++;
                if (isRow(row) && isColumn(row, col + 2))
                    counter++;
                if (isRow(row + 1) && isColumn(row + 1, col - 2))
                    counter++;
                if (isRow(row + 1) && isColumn(row + 1, col))
                    counter++;

                // if exist, new Ranks[counter]
                index = 0;
                if (counter != 0)
                {
                    subVertexRanks = new Ranks[counter];

                    if (search(row - 1, col) != null)
                        subVertexRanks[index++] = QTM.this.computeRanks(search(row - 1, col));
                    if (search(row - 1, col + 2) != null)
                        subVertexRanks[index++] = QTM.this.computeRanks(search(row - 1, col + 2));
                    if (search(row, col - 2) != null)
                        subVertexRanks[index++] = QTM.this.computeRanks(search(row, col - 2));
                    if (search(row, col + 2) != null)
                        subVertexRanks[index++] = QTM.this.computeRanks(search(row, col + 2));
                    if (search(row + 1, col - 2) != null)
                        subVertexRanks[index++] = QTM.this.computeRanks(search(row + 1, col - 2));
                    if (search(row + 1, col) != null)
                        subVertexRanks[index] = QTM.this.computeRanks(search(row + 1, col));
                }
            }
        }

        @Override
        public Cell getFlat()
        {
            return flat;
        }

        @Override
        public Ranks[] getSubVertexRanks()
        {
            return subVertexRanks;
        }

        @Override
        public Ranks[] getVertexRanks()
        {
            return vertexRanks;
        }

        @Override
        public Ranks[] getSideRanks()
        {
            return sideRanks;
        }
    }

    public Trigon[][] getRanksCells()
    {
        if (isFullGrid())
            return ranksCells;
        return null;
    }

    @Override
    public Adjacency adjoin(Cell cell)
    {
        if (this.computeRanks(cell).getLevel() != this.level)
            throw new Mistake("CellIsNotInGrid");
        if (isFullGrid())
            neighbor = new Neighbor((Trigon) cell);
        return neighbor;
    }

    @Override
    public void add(Cell cell)
    {
        if (cell == null)
        {
            throw new Mistake("CellIsNull");
        }
        int i = this.computeRanks(cell).getRow();
        int j = this.computeRanks(cell).getCol();

        int maxRow = ranksCells.length;

        if (ranksCells[i - 1] == null)
        {
            // i is from equator to top, and its value is from 1;
            // j is from left to right, and its values is from 1;
            ranksCells[i - 1] = new Trigon[2 * (maxRow - i) + 1];
        }
        ranksCells[i - 1][j - 1] = (Trigon) cell;
    }

    public int getLevel()
    {
        return level;
    }

    public Cell search(Ranks ranks)
    {
        return search(ranks.getRow(), ranks.getCol());
    }

    public Cell search(int row, int column)
    {
        if (ranksCells == null)
        {
            throw new Mistake("MeshIsNull");
        }

//        if (row < ranksCells.length && row >= 0 && column < ranksCells[row].length && column >= 0)
//        {
//            return ranksCells[row][column];
//        }
        if (isRow(row) && isColumn(row, column))
            return ranksCells[row - 1][column - 1];
        return null;
    }

    @Override
    public double measureDistance(Cell cell1, Cell cell2)
    {
        if (isNeighbor(cell1, cell2))
            return LatLon.greatCircleDistance(cell1.getRefPoint(), cell2.getRefPoint()).getRadians()
                * globe.getRadius();
        else
            return 0.0;
    }

    @Override
    public Ranks computeRanks(Cell cell)
    {
        if (cell.getGeocode().length() - 1 != getLevel())
            throw new Mistake("CellIsOutGrid");
        int row = getRow(cell.getGeocode());
        int col = getColumn(cell.getGeocode(), row);
        return new Ranks(row, col, cell.getGeocode());
    }

    @Override
    public String toString()
    {
        return "QTM{" +
            "level=" + level +
            ", ranksCells=" + Arrays.toString(ranksCells) +
            '}';
    }

    @Override
    public double halveDistance(Cell cell1, Cell cell2)
    {
        // Criteria 7
        // center of bisection side is center of bisection reference side

//        double distance = Double.MAX_VALUE;

        LatLon p1, p2;
        Vec4 vec41, vec42;
        p1 = LatLon.interpolateGreatCircle(0.5, cell1.getRefPoint(), cell2.getRefPoint());

        if (!isNeighbor(cell1, cell2))
        {
            throw new Mistake("CellsAreNotAdjacent");
        }
        else
        {
            int r1 = this.computeRanks(cell1).getRow();
            int r2 = this.computeRanks(cell2).getRow();
            if (r1 == r2)
            {
                p2 = LatLon.interpolateGreatCircle(0.5, ((Trigon) cell1).getTopV(), ((Trigon) cell2).getTopV());
            }
            else
            {
                p2 = LatLon.interpolateGreatCircle(0.5, ((Trigon) cell1).getLeftV(), ((Trigon) cell2).getRightV());
            }
            vec41 = Change.fromLatLon(p1);
            vec42 = Change.fromLatLon(p2);
            double distance = vec41.distanceTo2(vec42);
            p1 = null;
            p2 = null;
            vec41 = null;
            vec42 = null;

            return distance;
        }
    }

    private boolean isFullGrid()
    {
        int counter = 0;
        for (Cell[] aMesh : ranksCells)
        {
            counter += aMesh.length;
        }
        return counter == Math.pow(4, level);
    }

    private boolean isNeighbor(Cell cell1, Cell cell2)
    {
        boolean isAdjacency = false;
        Neighbor n = (Neighbor) this.adjoin(cell1);
        for (int i = 0; i < n.getSideRanks().length; i++)
        {
            if (n.getSideRanks()[i].equals(this.computeRanks(cell2)))
            {
                isAdjacency = true;
                break;
            }
        }
        n = null;
        return isAdjacency;
    }

    private boolean isRow(int row)
    {
        return row >= 1 && row <= (int) Math.pow(2, level);
    }

    private boolean isColumn(int row, int col)
    {
        return col >= 1 && col <= (int) (2 * (Math.pow(2, level) - row + 1) - 1);
    }

    /**
     * This method follows Wang Qian(2017)
     *
     * @param geocode
     *
     * @return row
     */
    private int getRow(String geocode)
    {
        int row = 1;
        char[] letters = geocode.toCharArray();
        if (letters.length == 1)
        {
            return 1;
        }
        // 表示三角形的指向,1指向上，-1指向下
        // 与初始三角形相同方向的为1，相反方向的为-1
        int direction = 0;
//        if (letters[0] == '4' || letters[0] == '5' || letters[0] == '6' || letters[0] == '7')
//        {
////            letters[0]=0;
//            direction = 1;
//        }
        //letters[0] = '0';
//        for (char letter : letters)
//        {
//            switch (letter)
        for (int i = 1; i < letters.length; i++)
        {
            switch (letters[i])
            {
                case '0':

                    if (direction % 2 == 0)
                    {
                        row = (row - 1) * 2 + 1;
                    }
                    else
                    {
                        row = (row - 1) * 2 + 2;
                    }
                    direction++;
                    break;
                case '1':
                    if (direction % 2 == 0)
                    {
                        row = (row - 1) * 2 + 2;
                    }
                    else
                    {
                        row = (row - 1) * 2 + 1;
                    }
                    break;
                case '2':
                    if (direction % 2 == 0)
                    {
                        row = (row - 1) * 2 + 1;
                    }
                    else
                    {
                        row = (row - 1) * 2 + 2;
                    }
                    break;
                case '3':
                    if (direction % 2 == 0)
                    {
                        row = (row - 1) * 2 + 1;
                    }
                    else
                    {
                        row = (row - 1) * 2 + 2;
                    }
                    break;
                default:
                    break;
            }
        }
        return row;
    }

    /**
     * This method follows Zhao Xuesheng(2003)
     *
     * @param geocode
     * @param row
     *
     * @return column
     */
    private int getColumn(String geocode, int row)
    {
        int col = 1;
        char[] letters = geocode.toCharArray();
        if (letters.length == 1)
        {
            return 1;
        }
        int level = letters.length - 1;
        int maxRow = (int) Math.pow(2, level);
        //letters[0] = '0';
        for (int i = 1; i < letters.length; i++)
        {
            switch (letters[i])
            {
                case '0':
                    maxRow = maxRow / 2;
                    col = col + (2 * (maxRow - row) + 1);
                    row = maxRow - row + 1;
                    break;
                case '1':
                    maxRow = maxRow / 2;
                    row = row - maxRow;
                    break;
                case '2':
                    maxRow = maxRow / 2;
                    break;
                case '3':
                    col += maxRow;
                    maxRow = maxRow / 2;
                    break;
                default:
                    break;
            }
        }
        return col;
    }
}
