/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.test;

import java.util.Vector;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/6/6
 */
public class QuadTree
{
    public QuadTreeNode root;
    public int depth;
    public int maxDepth;

    public QuadTree(int[] rect, int maxDepth) {
        depth = 0;
        this.maxDepth = maxDepth;
        root = new QuadTreeNode();
        root.init(rect);
    }

    /* inserts a node into quadtree and return pointer to new node */
    public QuadTreeNode insert(Object node_key,
        int[] rect) {
        depth = -1;
        debugNum = 0;
        return root.insertNode(this, rect, node_key);
    }

    /* searches nodes inside search_box */
    void search(int[] search_box, Vector results_list) {
        root.searchNodes(search_box, results_list);
    }

    public static int debugNum;
}
class QuadTreeNode {
    public static final int NODE_NUMS = 4;
    public static final int  NW = 0,NE = 1,SW = 2,SE = 3;
    /*
     * a quadrant defined below:
     *       NW(0) | NE(1)
     * 	-----------|-----------
     *   	SW(2)  | SE(3)
     */
    public int[] rect;// x,y,w,h
    public Vector vec;
    public QuadTreeNode[] node = new QuadTreeNode[NODE_NUMS];

    public QuadTreeNode(int x, int y, int w, int h) {
        init(x, y, w, h);
    }
    public QuadTreeNode() {
    }

    public void init(int x, int y, int w, int h) {
        int[] rect = new int[4];
        rect[NW] = x;
        rect[NE] = y;
        rect[SW] = w;
        rect[SE] = h;
        this.rect = rect;
        vec = new Vector();
    }

    public void init(int[] temprect) {
        this.rect = new int[4];
        System.arraycopy(temprect, 0, rect, 0, 4);
        vec = new Vector();
    }

    public void creatChild() {
        int x, y, w, h;
        x = rect[NW];
        y = rect[NE];
        w = rect[SW];
        h = rect[SE];
        this.node[NW] = new QuadTreeNode(x,y,w>>1,h>>1);
        this.node[NE] = new QuadTreeNode(x+(w>>1),y,w>>1,h>>1);
        this.node[SW] = new QuadTreeNode(x,y+(h>>1),w>>1,h>>1);
        this.node[SE] = new QuadTreeNode(x+(w>>1),y+(h>>1),w>>1,h>>1);
    }

    public boolean isHasChild(){
        if(node[NW] == null){
            return false;
        }else{
            return true;
        }
    }

    public QuadTreeNode insertNode(QuadTree tree,int[] nodeRect,Object key){
        QuadTree.debugNum++;
        if (Util.isInsideRect(nodeRect, rect) ) {
            if ( ++(tree.depth) < tree.maxDepth) {
                if ( !isHasChild() )
                    creatChild();

                if (Util.isInsideRect(nodeRect, node[NE].rect) )
                    return node[NE].insertNode(tree,
                        nodeRect ,key);

                if (Util.isInsideRect(nodeRect, node[NW].rect) )
                    return node[NW].insertNode(tree,
                        nodeRect ,key);

                if (Util.isInsideRect(nodeRect, node[SW].rect) )
                    return node[SW].insertNode(tree,
                        nodeRect ,key);

                if (Util.isInsideRect(nodeRect, node[SE].rect) )
                    return node[SE].insertNode(tree,
                        nodeRect,key );
            }

            /* inserts into this node since it can NOT be included in any subnodes */
            addData(key);
            System.out.println("QuadTree.debugNum: "+	QuadTree.debugNum);
            return this;
        }

        return null;
    }

    public void addData(Object node_key) {
        if (vec == null)
            vec = new Vector();

        vec.addElement(node_key);
    }



    /* searched tree nodes */
    public void searchNodes(
        int[] search_box, Vector results_list) {
        if (Util.isOverLapped(rect, search_box)) {
            if (vec!=null && vec.size()>0)
                for (int i = 0; i < vec.size(); i++) {
                    results_list.addElement(vec.elementAt(i));
                }
            //list_append_node(results_list, list_node_create(current_node));

            if (isHasChild()) {
                node[NE].searchNodes(search_box, results_list);
                node[NW].searchNodes(search_box, results_list);
                node[SW].searchNodes(search_box, results_list);
                node[SE].searchNodes(search_box, results_list);
            }
        }
    }
}
class Util {

    /**
     * return true if rectA is inside rectB;
     * @param rectA
     * @param rectB
     */
    public static boolean isInsideRect(int[] rectA,int[] rectB){

        if(rectA[0]>rectB[0] && (rectA[0]+rectA[2])< (rectB[0]+rectB[2])
            &&rectA[1]>rectB[1] && (rectA[1]+rectA[3])< (rectB[1]+rectB[3])){
            return true;
        }else {
            return false;
        }

    }

    public static boolean isOverLapped(int[] rectA,int[] rectB){
        if(
            rectA[0]+rectA[2]<rectB[0]
                ||rectA[0]>rectB[0]+rectB[2]
                ||rectA[1]+rectA[3]<rectB[1]
                ||rectA[1]>rectB[1]+rectB[3]
            ){
            return false;
        }else{
            return true;
        }
    }
}
