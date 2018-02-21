package com.eulicny.piece;

import Jama.Matrix;

public class Shapes {
	
	private static Matrix line;
	
	public Shapes() {
		initShapes();
	}
	
	public static Matrix getShape(ShapeName shapeName) {
		initShapes();
		
		if(shapeName == ShapeName.LINE) {
			return line;
		}
		
		return null;
	}
	
	private static void initShapes() {
		// Line = ----
		//		= 
		line = new Matrix(4,2);
		line.set(0, 0, 1);
		line.set(1, 0, 1);
		line.set(2, 0, 1);
		line.set(3, 0, 1);
	}
	
	 
	

}
