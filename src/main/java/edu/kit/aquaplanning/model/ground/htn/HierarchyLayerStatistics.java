package edu.kit.aquaplanning.model.ground.htn;

public class HierarchyLayerStatistics {

	public int size;
	
	public int totalNumReductions;
	public int meanNumReductions;
	//public int medianNumReductions;
	
	public int totalNumActions;
	public int meanNumActions;
	//public int medianNumActions;
	
	public int totalNumFacts;
	public int meanNumFacts;
	//public int medianNumFacts;
	
	@Override
	public String toString() {
		String out = "Statistics over " + size + " positions in total\n";
		out += ("\tRed\tAct\tFac\n");
		out += ("Total\t" + totalNumReductions + "\t" + totalNumActions + "\t" + totalNumFacts + "\n");
		out += ("Mean\t" + meanNumReductions + "\t" + meanNumActions + "\t" + meanNumFacts);
		return out;
	}
}
