package edu.neu.csye6200.ca;




import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


import javax.swing.JPanel;


public class CACanvas extends JPanel implements Runnable{
	
	CAGenerationSet caGen;
	ArrayList<int[]> genSet;//get generation set
	private int[] cellRow = null;
	static boolean state = false;
	int rowIndex = 0;
	
	public CACanvas(){
		
	}
	
	public void paint(Graphics g){
		if(state){
			drawCanvas(g);
		}
	}

	public void drawCanvas (Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
				
		for(int a=0; a<=rowIndex; a++){
				cellRow = genSet.get(a);
				for(int j =0; j<cellRow.length; j++){
					if(cellRow[j] == 0){
						paintCell(g2d, j*10, 10*(a+1), 10, Color.black);
					}else{
						paintCell(g2d, j*10, 10*(a+1), 10, Color.white);
					}
				}
				cellRow = null;
			}	
		System.out.println("repaint finish");
			
		}
	
	private void paintCell(Graphics2D g2d, int x, int y, int size, Color col){
		g2d.setColor(col);
		g2d.fillRect(x, y, size, size);
	}

	@Override
	public void run() {
		System.out.println("Start a new thread!");
		this.caGen = new CAGenerationSet();
		caGen.setGenSet(MyApp.rowAmount, MyApp.ruleIndex);
			genSet = caGen.getGenSet();
				while(true && MyApp.stop == false){
					try {
						Thread.sleep(1000);
						synchronized(MyApp.caCanvas){
							while(MyApp.suspend){
								
								MyApp.caCanvas.wait();
								
							}
						}
					} catch (InterruptedException e) {
						MyApp.log.severe("draw thread interrupted exception");
					}
					this.repaint();
					if(rowIndex<MyApp.rowAmount-1){
						rowIndex++;
					}else{
						MyApp.startBtn.setEnabled(true);
						MyApp.stopBtn.setEnabled(false);
						MyApp.pauseBtn.setEnabled(false);
						MyApp.restartBtn.setEnabled(false);
						MyApp.ruleList.setEnabled(true);
						MyApp.genNumField.setEnabled(true);
						
						rowIndex = 0;
						state = false;
						return;		
					}
					
				}
				genSet = new ArrayList<int[]>();
				rowIndex = 0;
				state = false;
				System.out.println("Out of thread!");
		}
	





}
