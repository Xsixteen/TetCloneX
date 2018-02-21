package com.eulicny;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.eulicny.board.GameBoard;
import com.eulicny.board.GameBoard.DIRECTION;

import Jama.Matrix;

public class MainWindow extends JFrame {
	private GameWindow panel;
	private static int CONST_SCALE_FACTOR 	= 25;
	private static int speedSec				= 1;
	
	public static void main(String[] args) {
		
		 EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new MainWindow().setVisible(true);
	            }
	        });

	}
	
	public MainWindow() {
		initComponents();	
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  panel.getGameboard().updateState();
				  panel.repaint();
				  panel.revalidate();
				 
			  }
		}, speedSec*1000, speedSec*1000);
	}
	
	private void initComponents() {
		  panel = new GameWindow();
		  this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					//Right Key
					panel.move(DIRECTION.RIGHT);
					System.out.println("Right Key Press");
					
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					//Leftf Key
					panel.move(DIRECTION.LEFT);
					System.out.println("Left Key Press");
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}});
		  
		  // add the component to the frame to see it!
	      this.setContentPane(panel);
          // be nice to testers..
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          pack();
	}
	
	public class GameWindow extends JPanel {
		 	private Matrix currentBoard;
			private GameBoard gameboard;

			GameWindow() {
	            // set a preferred size for the custom panel.
	            setPreferredSize(new Dimension(420,420));
	  		    this.gameboard	= new GameBoard();

	        }
			
	        @Override
	        public void paintComponent(Graphics g) {
	        	System.out.println("Window Repainted!");
	            super.paintComponent(g);
	            this.currentBoard	=	this.gameboard.getGameState();
	            
	            //Paint the recs & the blocks
	            for(int i=0; i < this.currentBoard.getColumnDimension(); i++) {
	            	for(int j=0; j < this.currentBoard.getRowDimension(); j++ ){
	            		if(this.currentBoard.get(j, i) == 1)
	            				g.fillRect((i*CONST_SCALE_FACTOR), (j*CONST_SCALE_FACTOR), CONST_SCALE_FACTOR, CONST_SCALE_FACTOR);
	            		
	            		g.drawRect((i*CONST_SCALE_FACTOR), (j*CONST_SCALE_FACTOR), CONST_SCALE_FACTOR, CONST_SCALE_FACTOR);
	            	}
	            }
	            
	            //Reset movement.
	            this.move(DIRECTION.NIL);
	         
	        }
	        
	        public GameBoard getGameboard() {
	        	return this.gameboard;
	        }
	        
	        public void setMatrix(Matrix m) {
	        	this.currentBoard	= m;
	        	//this.
	        }
	        
	        public void move(DIRECTION direction) {
	            this.gameboard.setNextMove(direction);
	        }
	        
	    }
}
