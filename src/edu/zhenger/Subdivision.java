/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/12
 */
public interface Subdivision extends DGGS
{
    Cell[] getChildren();
//    void analyze(Cell[] cells, Analysis analysis);
}
