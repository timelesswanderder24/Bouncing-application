/*
 *  ============================================================================================
	Name: Imashi Kinigama
 *  Tree model adapter that extends abstract tree model
 *  UPI: ikin507
	Date : 6/8/2021
 *  ============================================================================================
 */

import java.awt.*;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.io.*;
public class TreeModelAdapter implements TreeModel {
    private NestedShape nestedShape;
	private ArrayList<TreeModelListener> treeModelListeners = new ArrayList<TreeModelListener>();
	
    public TreeModelAdapter(NestedShape nShape){
		nestedShape = nShape;
	}
	public NestedShape getRoot(){return nestedShape;}
	
	public boolean isLeaf(Object Node){
		if(Node instanceof NestedShape){ 
		    return false;
		}
		return true;
	}
	
	public Object getChild(Object parent, int index){
		if(parent instanceof NestedShape){
			NestedShape ns = (NestedShape) parent;
			if(ns.getSize()>=index){
			    Shape[] s = ns.getChildren();
				return s[index];
			}
			else{return null;}
		}
		return null;
	}
	public int getChildCount(Object parent){
		if(parent instanceof NestedShape){
			NestedShape ns = (NestedShape) parent;
			return ns.getSize();
		}
		return 0;
	}
	public int getIndexOfChild(Object parent, Object child){
		if(parent instanceof NestedShape){
			NestedShape ns = (NestedShape) parent;
			return ns.indexOf((Shape)child);
		}
		return -1;
	}
    public void addTreeModelListener(final TreeModelListener modelListener) {
		treeModelListeners.add(modelListener);
	}
	public void removeTreeModelListener(final TreeModelListener modelListener) {
		treeModelListeners.remove(modelListener);
	}
	protected void fireTreeStructureChanged(final Object source, final Object[] path, final int[] childIndices, final Object[] children){
		final TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
		for(final TreeModelListener l:treeModelListeners){
			l.treeStructureChanged(e);
		}
	}
	protected void fireTreeNodesInserted(Object source, Object[] path,int[] childIndices,Object[] children){
		final TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
		for(final TreeModelListener l:treeModelListeners){
			l.treeStructureChanged(e);
			//l.treeNodesInserted(e);
		}
	}
	
	protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children){
		final TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
		for(final TreeModelListener l:treeModelListeners){
			l.treeStructureChanged(e);
			//l.treeNodesRemoved(e);
		}
	}
	
	public void addToRoot(Shape s){
		nestedShape.add(s);
		int size = nestedShape.getSize();
		fireTreeNodesInserted(this, new Object[]{nestedShape}, new int[]{size}, new Object[]{s});
	}
	public boolean addNode(TreePath selectedPath, ShapeType currentShapeType){
		Shape s = (Shape)selectedPath.getLastPathComponent();
		if(!(s instanceof NestedShape)){ return false;}
		NestedShape	parent = (NestedShape)selectedPath.getLastPathComponent();
		int size = parent.getSize();
		parent.createAddInnerShape(currentShapeType);
		Shape child = parent.getShapeAt(size);
		fireTreeNodesInserted(this, selectedPath.getPath(), new int[]{size}, new Object[]{child});
		return true;
	}
	
	public boolean removeNodeFromParent(TreePath selectedPath){
		Shape s = (Shape)selectedPath.getLastPathComponent();
		if(s.getParent()==null){ return false;}
		NestedShape parent = s.getParent();
		int position = parent.indexOf(s);
		parent.remove(s);
		fireTreeNodesRemoved(this, selectedPath.getParentPath().getPath(), new int[]{position}, new Object[]{s});
		return true;
	}
	
	public void valueForPathChanged(TreePath path, Object newValue) {}
    public void fireTreeNodesChanged(TreeModelEvent e) {}
}