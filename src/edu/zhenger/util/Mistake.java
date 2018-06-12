/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.util;

import gov.nasa.worldwind.util.Logging;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/10
 */
public class Mistake extends IllegalArgumentException
{
    public Mistake(String message)
    {
        String e = Logging.getMessage(message);
        Logging.logger().severe(e);
    }
}
