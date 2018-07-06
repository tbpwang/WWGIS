/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger;

import edu.zhenger.model.Ranks;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/20
 */
public interface Adjacency
{
//    private Cell flat;
//    private Ranks[] shareVertexCells;
//    private Ranks[] shareSideCells;

    Cell getFlat();

    //void setShareVertexCells(Ranks[] shareVertexCells);
    Ranks[] getSubVertexRanks();
    Ranks[] getVertexRanks();


    Ranks[] getSideRanks();

    //void setShareSideCells(Ranks[] shareSideCells);

}
