package edu.neu.csye6200.ca;

import java.util.ArrayList;


public class CAGenerationSet {
	private CAGeneration cag;
	private ArrayList<int[]> genSet;
	

	
	public CAGenerationSet(){
		cag = new CAGeneration();
		genSet = new ArrayList<int[]>();
	}
	
	
	public  ArrayList<int[]> getGenSet() {

		return genSet;
	}
	
	public  void setGenSet(int n, int ruleset){
		genSet.add(cag.getCells());//first row is added generation set
		
		for(int i = 0; i < n-1; i++){
		
			genSet.add(cag.generateCA(cag.getCells(), ruleset));
			
		}
		System.out.println("set is ready!");
	}
	

}
