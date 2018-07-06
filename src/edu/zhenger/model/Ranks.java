/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/18
 */
public class Ranks
{
    private int row, col;
    private String geocode;
    private int level;

    protected Ranks(int row, int col, String geocode)
    {
        this.row = row;
        this.col = col;
        this.geocode = geocode;
        this.level = geocode.length() - 1;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getCol()
    {
        return col;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public String getGeocode()
    {
        return geocode;
    }

    public void setGeocode(String geocode)
    {
        this.geocode = geocode;
    }

    public int getLevel()
    {
        return level;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Ranks ranks = (Ranks) o;

        return row == ranks.row && col == ranks.col && (geocode != null ? geocode.equals(ranks.geocode)
            : ranks.geocode == null);
    }

    @Override
    public int hashCode()
    {
        int result = row;
        result = 31 * result + col;
        result = 31 * result + (geocode != null ? geocode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Ranks{" +
            "level=" + level +
            ",geocode=" + geocode +
            ", row=" + row +
            ", column=" + col +
            '}';
    }
}
