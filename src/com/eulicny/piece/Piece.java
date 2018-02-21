package com.eulicny.piece;

import Jama.Matrix;

public class Piece {
	int x;
	int y;
	int rotation;  //0 normal, 1 = 90, 2 = 180, 3 = 270
	Matrix shape;
	boolean active;
	ShapeName shapeName;
	
	public Piece(ShapeName shapeName) {
		this.rotation	= 0;
		this.x			= 0;
		this.y			= 0;
		this.shape 		= Shapes.getShape(shapeName);
		this.active		= true;
		this.shapeName  = shapeName;
	}
	
	public ShapeName getShapeName() {
	    return this.shapeName;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public void setActive(boolean activeState) {
		this.active = activeState;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
	    this.x = x;
	}
	
	public void setY(int y) {
	    this.y = y;
	}
	
	public Matrix getShape() {
		return this.shape;
	}
	
	public void moveDown() {
		this.y ++;
	}
	
	public void moveLeft() {
		this.x --;
	}
	
	public void moveRight() {
		this.x ++;
	}
	
	public Matrix getMatrix(int dimCol, int dimRow) {
	    Matrix newMatrix = new Matrix(dimRow, dimCol);
	    
	    for(int i = 0; i < this.shape.getRowDimension(); i++) {
	        for(int j = 0; j< this.shape.getColumnDimension(); j++) {
	            if(this.shape.get(i, j) == 1) {
	                   newMatrix.set(j+y, i+x, 1);
	            }
	          
	        }
	    }
	    return newMatrix;
	}
 
}


