/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger;

import edu.zhenger.model.*;
import gov.nasa.worldwind.globes.Globe;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/18
 */
public interface Grid
{
    Globe getGlobe();

    int getLevel();

    Cell[][] getRanksCells();

    Adjacency adjoin(Cell cell);

    void add(Cell cell);

    Cell search(Ranks ranks);

    Cell search(int row, int column);

    double measureDistance(Cell cell1, Cell cell2);

    double halveDistance(Cell cell1, Cell cell2);

    Ranks computeRanks(Cell cell);
}
