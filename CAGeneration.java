package edu.neu.csye6200.ca;


public class CAGeneration {
	private int[] cells;
	private CACell cell;
	private int rowLength;
	private CARule rule;
	
	public CAGeneration(){
		cell = new CACell();
		rule = new CARule();
		this.rowLength = 640;
		cells = new int[rowLength/cell.getWidth()];
		initCells();
	}
	
	public void initCells(){
		
	for(int i = 0; i < cells.length; i++){
		cells[i] = 0;
	}
	cells[cells.length/2] = 1;
	}
	
	public int[] getCells() {
		return cells;
	}

	public int[] generateCA(int[] cells, int ruleSet){
		this.cells = rule.generate(cells, ruleSet);
		return this.cells;
	}
}
