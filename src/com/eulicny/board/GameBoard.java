package com.eulicny.board;

import java.util.ArrayList;

import Jama.Matrix;

import com.eulicny.piece.Piece;
import com.eulicny.piece.ShapeName;

public class GameBoard {
	private final int CONST_GAMEROWS	= 10;
	private final int CONST_GAMECOLS	= 20;
	private int pieceNumber				= 0;
	private ArrayList<Piece> pieces			= new ArrayList<Piece>();
	private ArrayList<Piece> pieceBuffer	= new ArrayList<Piece>();
	private Matrix gameState				= new Matrix(CONST_GAMEROWS, CONST_GAMECOLS);
	private int score 						= 0;
	private boolean currPieceValid;
	private DIRECTION nextMove;
	
	public GameBoard() {
		Piece piece = new Piece(ShapeName.LINE);
		//piece.moveRight();
		this.plotPiece(piece);
		pieces.add(piece);
	}
	
	public enum DIRECTION {
		DOWN,
		RIGHT,
		LEFT,
		NIL
	}
	
	private Piece newPiece() {
		System.out.println("Spawning New Piece!");
		Piece piece = new Piece(ShapeName.LINE);
		return piece;
	}
	
	
	private Piece move(Piece p, DIRECTION direction) {
		if(direction == DIRECTION.RIGHT) {
			if(p.getX() + p.getShape().getColumnDimension() < CONST_GAMECOLS)
				p.moveRight();
		}
		
		if(direction == DIRECTION.LEFT) {
			if(p.getX() > 0)
				p.moveLeft();
		}
		
		p.moveDown();
		
		return p;
	}
	
	public void setNextMove(DIRECTION direction) {
		this.nextMove = direction;
	}
	
	/**
	 * Function simply converts pieces to the logical gameboard object.
	 */
	public void updateState() {
		boolean newPiece = false;
		pieceBuffer.clear();
		
		//Movement phase
		for(Piece p : pieces) {
		    if(p.getX() < this.gameState.getColumnDimension()-1 && p.getY() < this.gameState.getRowDimension()-1 && p.isActive()) {
		        if(checkCollision(p)) {
	                p.setActive(false);
	                pieceBuffer.add(p);
	                newPiece = true;
		        } else {
		            pieceBuffer.add(move(p, this.nextMove));
		        }
			} else if (p.isActive()) {
				p.setActive(false);
				pieceBuffer.add(p);
				newPiece = true;
			} else {
				//Just add the non active pieces to be drawn
				pieceBuffer.add(p);
			}
		}
		
		if (newPiece == true) {
			newPiece = false;
			pieceBuffer.add(newPiece());
		}
		
		this.gameState = new Matrix(CONST_GAMEROWS, CONST_GAMECOLS); 
		pieces.clear();
		
		//Plotting Phase
		for(Piece p : pieceBuffer) {
			this.plotPiece(p);
			pieces.add(p);
		}
		
	
	}
	
	public int getScore() {
		return this.score;
	}
	
	public Matrix getGameState() {
		return this.gameState;
	}
	
	private void plotPiece(Piece p) {
		Matrix shape 	= p.getShape();
		int startX 		= p.getX();
		int startY 		= p.getY();
		System.out.println("Piece X="+startX+" Y="+startY);
		for(int x = 0; x < shape.getRowDimension(); x++) {
			for (int y = 0; y < shape.getColumnDimension(); y++) {
				if(shape.get(x, y) == 1 && (startY+y) < this.gameState.getRowDimension() && (startX+x) < this.gameState.getColumnDimension() && (startX+x) >= 0 && (startY+y) >= 0 )
					this.gameState.set(startY+y, startX+x, 1);
			}
		}
	}
	
	private boolean checkCollision(Piece currPiece) {
		//Now check if theres a collision
		
		//Step 1 Plot all existing pieces
		Matrix pieceShape   = new Matrix(CONST_GAMEROWS, CONST_GAMECOLS); 
		
		//Step 2 Move the theoretical Piece Down
		Piece theoreticalPiece = new Piece(currPiece.getShapeName());
		theoreticalPiece.setX(currPiece.getX());
		theoreticalPiece.setY(currPiece.getY());
		
		theoreticalPiece.moveDown();
		
	
		//Step 3 Add all numbers to game board Matrix of the piece in motion
		Matrix currentTheoreticalBoard = this.gameState.plus(theoreticalPiece.getMatrix(CONST_GAMECOLS, CONST_GAMEROWS));
	
		//Step 4 if any number == 2 that indicates a collision
		for(int x = 0; x < currentTheoreticalBoard.getRowDimension(); x++) {
			for (int y = 0; y < currentTheoreticalBoard.getColumnDimension(); y++) {
				if(currentTheoreticalBoard.get(x,y) == 2) {
				        System.out.println("Collision Detected");
						return true;
				}
			}
		}
		

		return false;
	}
	

}
