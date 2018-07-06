/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.Cell;
import edu.zhenger.model.*;
import edu.zhenger.util.*;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/18
 */
public class Calculator
{
    private int level;
    private QTM ranksOrderGrid;
    private Cell[][] geocodeOrderCells;

    public Calculator(int level)
    {
        this(level,0);
    }

    public Calculator(int level,int facet)
    {
        if (level < 0)
        {
            throw new Mistake("LevelDoesNotExist");
        }
        this.level = level;

        geocodeOrderCells = new Cell[level + 1][];
        geocodeOrderCells[0] = new Cell[1];
        geocodeOrderCells[0][0] = Octant.getInstance().getFacet(facet);

        //OQTM qtm = OQTM.getInstance();
        Cell[] trigon;

        ranksOrderGrid = new QTM(level);
        for (int i = 1; i < level + 1; i++)
        {
            geocodeOrderCells[i] = new Cell[(int) Math.pow(4, i)];

            for (int j = 0; j < geocodeOrderCells[i - 1].length; j++)
            {
                trigon = null;
                //trigon = qtm.subdivide(geocodeOrderCells[i - 1][j], null);
                trigon = geocodeOrderCells[i-1][j].subdivide().getChildren();

//                if (i == 9)
//                {
//                    System.out.println(j);
//                }

                //System.arraycopy(trigon, 0, geocodeOrderCells[i], 4 * j, 4);
                for (int k = 0; k < 4; k++)
                {
                    geocodeOrderCells[i][4 * j + k] = trigon[k];
                    if (i == level)
                    {
                        ranksOrderGrid.add(trigon[k]);
                    }
                }
            }
        }
    }

    public int getLevel()
    {
        return level;
    }

    public QTM getRanksOrderGrid()
    {
        return ranksOrderGrid;
    }

    public Cell[][] getGeocodeOrderCells()
    {
        return geocodeOrderCells;
    }

}
