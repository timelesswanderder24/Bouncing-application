/*
 *  ============================================================================================
	Name : Imashi Kinigama
	UPI: ikin507
 *  NestedShape.java : Extends RectangleShape and creates shapes inside a shape. Obeys Comppsite Design Pattern.
 *  Date : 5/23/2021
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

class NestedShape extends RectangleShape{
	private ArrayList<Shape> nestedShapes = new ArrayList<Shape>();
	private static ShapeType nextShapeType = ShapeType.NESTED;
	
	public NestedShape(){
		super();
	}
	
	public NestedShape(int x, int y, int width, int height, int mw, int mh, Color bc, Color fc, PathType pt){
		super(x, y, width, height, mw, mh, bc, fc, pt);
		nextShapeType = nextShapeType.next();
		createAddInnerShape(nextShapeType);
	}
	
	public NestedShape(int x, int y, int width, int height, int mw, int mh, Color bc, Color fc, PathType pt, String txt){
		super(x, y, width, height, mw, mh, bc, fc, pt, txt);
		nextShapeType = nextShapeType.next();
		createAddInnerShape(nextShapeType);
	}
	
	public NestedShape(ArrayList<Shape> shapes, Color bc, Color fc){
		super(0, 0, DEFAULT_MARGIN_WIDTH, DEFAULT_MARGIN_HEIGHT, DEFAULT_MARGIN_WIDTH, DEFAULT_MARGIN_HEIGHT,bc, fc, DEFAULT_PATHTYPE, DEFAULT_TEXT);
		for(Shape s:shapes){
			nestedShapes.add(s);
			s.setParent(this);
		}
	}
	
	public void createAddInnerShape(ShapeType st){
		int newWidth = width/2;
		int newHeight = height/2;
		 switch (st) {
            case RECTANGLE: {
                RectangleShape r = new RectangleShape(0, 0,newWidth,newHeight,width,height,borderColor,fillColor,DEFAULT_PATHTYPE, text);
        		nestedShapes.add(r);
        		r.setParent(this);
                break;
			} case XRECTANGLE: {
			    XRectangleShape x = new XRectangleShape(0, 0,newWidth,newHeight,width,height,borderColor,fillColor,DEFAULT_PATHTYPE, text );
				nestedShapes.add(x);
				x.setParent(this);
			   	break;
		   } case OVAL: {
		       OvalShape o = new OvalShape(0,0,newWidth,newHeight,width,height,borderColor,fillColor,DEFAULT_PATHTYPE, text);
				nestedShapes.add(o);
				o.setParent(this);
				break;
		   } case SQUARE: {
		       SquareShape s = new SquareShape(0,0,Math.min(newWidth,newHeight),width,height,borderColor,fillColor,DEFAULT_PATHTYPE, text);
				nestedShapes.add(s);
				s.setParent(this);
				break;
			} case NESTED: {
		       NestedShape n = new NestedShape(0,0,newWidth,newHeight,width,height,borderColor,fillColor,DEFAULT_PATHTYPE, text);
				nestedShapes.add(n);
				n.setParent(this);
				break;
			} 
		}
	}
	
	public Shape getShapeAt(int index){
		return nestedShapes.get(index);
	}
	
	public int getSize(){
		return nestedShapes.size();
	}
	
	@Override
	public void draw(Painter painter){
		painter.setPaint(Color.black);
		painter.drawRect(x, y, width, height);
		painter.translate(x, y);
		for(Shape s:nestedShapes){
			s.draw(painter);
		}
		painter.translate(-x, -y);
	}
	
	@Override
	public void move(){
		path.move();
		for(Shape s:nestedShapes){
			s.move();
		}
	}
	
	public void add(Shape s){
		nestedShapes.add(s);
		s.setParent(this);
	}
	
	public void remove(Shape s){
		nestedShapes.remove(s);
		s.setParent(null);
	}
	
	public int indexOf(Shape s){
		return nestedShapes.indexOf(s);
	}
	
	public Shape[] getChildren(){
		Shape[] shapes = new Shape[nestedShapes.size()];
		return nestedShapes.toArray(shapes);
	}
	
}