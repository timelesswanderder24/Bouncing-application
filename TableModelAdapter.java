import java.awt.*;
/*
 *  ============================================================================================
 	Name: Imashi Kinigama
 *  Table model adapter that extends abstract table model
 *  UPI: ikin507
	Date : 6/8/2021
 *  ============================================================================================
 */


import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.io.*;

public class TableModelAdapter extends AbstractTableModel{
		private Shape nestedShape;
		private static final String[] columnNames = {"Type", "X-pos", "Y-pos", "Width", "Height"};
		
		public TableModelAdapter(NestedShape nShape){
		    nestedShape = nShape;
		}
		
		public int getColumnCount(){return columnNames.length;}
		
		public int getRowCount(){
		    NestedShape ns = (NestedShape) nestedShape;
		    return ns.getSize();
		    
		}
		
		public String getColumnName(int column){return columnNames[column];}
		
		public Object getValueAt(int rowIndex, int columnIndex){
		    NestedShape ns = (NestedShape) nestedShape;
			if(columnIndex==0){
				return ns.getShapeAt(rowIndex).getClass().getName();
			}else if(columnIndex==1){
				return ns.getShapeAt(rowIndex).getX();
			}else if(columnIndex==2){
				return ns.getShapeAt(rowIndex).getY();
			}else if(columnIndex==3){
				return ns.getShapeAt(rowIndex).getWidth();
			}else if(columnIndex==4){
				return ns.getShapeAt(rowIndex).getHeight();
			}
			return null;
		}
		public void setNestedShape(Shape s){
		    nestedShape = s;
		}
	}
	