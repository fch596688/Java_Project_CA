package edu.neu.csye6200.ca;


public class CARule {
	
	
	private int[] ruleSet;
	
	
	public CARule(){
		
		
	}
	
	

	public int[] generate(int[] cells, int ruleSet) {
		
		if(ruleSet == 0){
			this.ruleSet =  new int[]{0,1,0,1,1,0,1,0};//Rule 90
		}else if(ruleSet == 1){
			this.ruleSet = new int[]{0,1,1,1,1,0,1,1};//Rule 222
		}else{
			this.ruleSet = new int[]{0,1,1,0,0,1,0,1};
		}
		
		int[] nextGen = new int[cells.length];
		for(int i = 1; i < cells.length-1; i++){
			int left = cells[i-1];
			int mid = cells[i];
			int right = cells[i+1];
			nextGen[i] = rule1(left, mid, right);
		}
		return nextGen;
	}
	
	public int rule1(int a, int b, int c){
		String s = "" + a + b + c;
		int index = Integer.parseInt(s, 2);
		return ruleSet[index];
	}
	

}
