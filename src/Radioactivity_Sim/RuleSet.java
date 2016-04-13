/* MIT License

Copyright (c) 2016 Jonathan Wayne Hart

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package Radioactivity_Sim;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

//TODO
// 1 - Strange input data sheet parsing error.  A commented line near the end of an
// input file causes the parser to skip the last few lines.


public class RuleSet {
	//A container for rule data read in from a file
	/* Variable and Function Nomenclature Prescipts:
     * pv = private
     * pu = public
     * pvs = private static
     * pus = public static
     * pvsf = private static final
     * pusf = public static final
     */
	private String[] pvNuclei; //All independent nuclei in the rule set
	private int pvNumBranches = 0; //number of RuleBranch objects
	private RuleBranch[] pvBranches; //Stores the rule branches for predictive computation
	private String[] pvStartNuclei; //The starting nucleus of each rule
	private String[] pvEndNuclei; //The ending nucleus of each rule
	private String[] pvType; //The emmission type of each rule
	private double[] pvEnergy; //The energy quantity for each rule
	private double[] pvHalfLife; //The half-life for each rule
	private double[] pvProbability; //The probability of each rule
	private int pvNumRule = 0; //The total number of rules
	private int pvNumNuclei = 0; //The total number of independent nuclei
	public RuleSet() {
		//Blank RuleSet Constructor
	}
	public RuleSet(String input) {
		//RuleSet Constructor that reads in and parses a RuleSet file
		String inputData = "";
    	try {
        	inputData = pvReadData(input);
        	//System.out.println(inputData);
        } catch (Exception e) {
        	System.out.println(e);
        	System.out.println("Input file: " + input + " failed to open for reading");
        }
    	boolean stillParsing = true;
    	boolean noLineEnd = true;
    	boolean stillRuleParsing = true;
    	while (stillParsing) {
    		int x = 0;
    		String ruleLine = "";
    		while (noLineEnd) {
    				if((x+1)>inputData.length()){
    					noLineEnd = false;
    					break;
    				}
    				if (inputData.substring(x,x+1).compareTo(System.getProperty("line.separator"))==0) {
    					noLineEnd = false;
    					ruleLine = inputData.substring(0,x) + "       ";
    					inputData = inputData.substring(x+1,inputData.length());
    					int y = 0;
    					int ruleNum = 0;
    					String startNucleus = "";
    					String endNucleus = "";
    					String type = "";
    					double energy = 0.0;
    					double halfLife = 0.0;
    					double probability = 0.0;
    					if (ruleLine.substring(0,1).compareTo("#")==0){
    						stillRuleParsing = false;
    					} else if (ruleLine.substring(0,1).compareTo(" ")==0){
    						stillRuleParsing = false;
    					} else if (ruleLine.substring(0,2).compareTo("//")==0){
    						stillRuleParsing = false;
    					}
    					while (stillRuleParsing) {
    						if (ruleLine.substring(y,y+1).compareTo(" ")==0) {
    							ruleNum++;
    							switch (ruleNum) {
    							case 1:
    								startNucleus = ruleLine.substring(0,y);
    								ruleLine = ruleLine.substring(y+1,ruleLine.length());
    								y = 0;
    								break;
    							case 2:
    								endNucleus = ruleLine.substring(0,y);
    								ruleLine = ruleLine.substring(y+1,ruleLine.length());
    								y = 0;
    								break;
    							case 3:
    								type = ruleLine.substring(0,y);
    								ruleLine = ruleLine.substring(y+1,ruleLine.length());
    								y = 0;
    								break;
    							case 4:
    								energy = Double.valueOf(ruleLine.substring(0,y));
    								ruleLine = ruleLine.substring(y+1,ruleLine.length());
    								y = 0;
    								break;
    							case 5:
    								halfLife = Double.valueOf(ruleLine.substring(0,y));
    								ruleLine = ruleLine.substring(y+1,ruleLine.length());
    								y = 0;
    								break;
    							case 6:
    								probability = Double.valueOf(ruleLine.substring(0,y));
    								ruleLine = ruleLine.substring(y+1,ruleLine.length());
    								stillRuleParsing = false;
    								puAddRule(startNucleus,endNucleus,type,energy,halfLife,probability);
    								if (startNucleus.compareTo("")==0){
    									System.out.println("Parsing of the input file, " + input + " failed at the StartNucleus string!");
    								}
    								if (endNucleus.compareTo("")==0){
    									System.out.println("Parsing of the input file, " + input + " failed at the EndNucleus string!");
    								}
    								if (type.compareTo("alpha")!=0&type.compareTo("beta-")!=0&type.compareTo("beta+")!=0&type.compareTo("gamma")!=0&type.compareTo("neutron&beta-")!=0){
    									System.out.println("Parsing of the input file, " + input + " failed at the type string!");
    									System.out.println("type should be one of the following: alpha, beta-, beta+, gamma, neutron&beta-");
    								}
    								if (energy<0){
    									System.out.println("Parsing of the input file, " + input + " failed because the energy was less than zero!");
    								}
    								if (halfLife<0){
    									System.out.println("Parsing of the input file, " + input + " failed because the halflife was less than zero!");
    								}
    								if (probability<0|probability>1){
    									System.out.println("Parsing of the input file, " + input + " failed because the probability was less than zero or greater than one!");
    								}
    								break;
    							case 7:
    								stillRuleParsing = false;
    								break;
    							}
    						}
    						y++;
    						if ((y+1)>ruleLine.length()){
    							stillRuleParsing = false;
    						}
    					}
    					stillRuleParsing = true;
    				}
    			x++;
    			if((x+1)>inputData.length()){
    				noLineEnd = false;
    			}
    		}
    		if((x+1)>inputData.length()){
				break;
			}
    		noLineEnd = true;
    	}
    	pvAddRuleBranch();
    	for(int x = 0; x<pvNumRule; x++){
    		if(x == 0) {
    			pvBranches[0].puAddRuleToBranch(pvStartNuclei[x],pvEndNuclei[x],pvType[x],pvEnergy[x],pvHalfLife[x],pvProbability[x]);
    		} else {
    			for(int y = 0; y<pvNumBranches; y++){
    				if(pvStartNuclei[x].compareTo(pvBranches[y].puGetEndNucleus(pvBranches[y].puGetNumRules()-1))==0) {
    	    			pvBranches[y].puAddRuleToBranch(pvStartNuclei[x],pvEndNuclei[x],pvType[x],pvEnergy[x],pvHalfLife[x],pvProbability[x]);
    					if (x<pvNumRule-1){
    						if(pvProbability[x]+pvProbability[x+1]==1) {
    							pvAddRuleBranch();
    	    					for (int z = 0; z<pvBranches[y].puGetNumRules()-1; z++) {
    	    						pvBranches[pvNumBranches-1].puAddRuleToBranch(pvBranches[y].puGetStartNucleus(z),pvBranches[y].puGetEndNucleus(z),pvBranches[y].puGetType(z),pvBranches[y].puGetEnergy(z),pvBranches[y].puGetHalfLife(z),pvBranches[y].puGetProbability(z));
    	    					}
    	    					pvBranches[pvNumBranches-1].puAddRuleToBranch(pvStartNuclei[x+1],pvEndNuclei[x+1],pvType[x+1],pvEnergy[x+1],pvHalfLife[x+1],pvProbability[x+1]);
    						}
    	    			}
    					if (x<pvNumRule-2){
    	    				if(pvProbability[x]+pvProbability[x+1]+pvProbability[x+2]==1){
    	    					pvAddRuleBranch();
    	    					pvAddRuleBranch();
    	    					for (int z = 0; z<pvBranches[y].puGetNumRules()-1; z++) {
    	    						pvBranches[pvNumBranches-2].puAddRuleToBranch(pvBranches[y].puGetStartNucleus(z),pvBranches[y].puGetEndNucleus(z),pvBranches[y].puGetType(z),pvBranches[y].puGetEnergy(z),pvBranches[y].puGetHalfLife(z),pvBranches[y].puGetProbability(z));
    	    						pvBranches[pvNumBranches-1].puAddRuleToBranch(pvBranches[y].puGetStartNucleus(z),pvBranches[y].puGetEndNucleus(z),pvBranches[y].puGetType(z),pvBranches[y].puGetEnergy(z),pvBranches[y].puGetHalfLife(z),pvBranches[y].puGetProbability(z));
    	    					}
    	    					pvBranches[pvNumBranches-2].puAddRuleToBranch(pvStartNuclei[x+1],pvEndNuclei[x+1],pvType[x+1],pvEnergy[x+1],pvHalfLife[x+1],pvProbability[x+1]);
    	    					pvBranches[pvNumBranches-1].puAddRuleToBranch(pvStartNuclei[x+2],pvEndNuclei[x+2],pvType[x+2],pvEnergy[x+2],pvHalfLife[x+2],pvProbability[x+2]);
    	    				}
    	    			}
    				}
    			}
     		}
    	}
    	for (int x = 0; x<pvBranches.length;x++) {
    		pvBranches[x].puReorderProbabilities();
    	}
    	int n = 0, r = 0;
    	for(int x = 0; x<pvNumRule; x++){
    		r = 0;
    		for (int y = 0; y<x; y++){
    			if(pvStartNuclei[x].compareTo(pvStartNuclei[y])==0){
    				r++;
    			}
    		}
    		if (r==0) {
    			n++;
    		}
    	}
    	pvNumNuclei = n;
    	pvNuclei = new String[n];
    	n = 0;
    	r = 0;
    	for(int x = 0; x<pvNumRule; x++){
    		r = 0;
    		for (int y = 0; y<x; y++){
    			if(pvStartNuclei[x].compareTo(pvStartNuclei[y])==0){
    				r++;
    			}
    		}
    		if (r==0) {
    			pvNuclei[n] = pvStartNuclei[x];
    			n++;

    		}
    	}
	}
	public String[] puGetNuclei() {
		//Returns a string array containing the independent nuclei addressed in this (RuleSet)
		return pvNuclei;
	}
	public int puGetNumBranches() {
		//Returns the number of (RuleBranch) objects in this (RuleSet)
		return pvNumBranches;
	}
	public RuleBranch puGetRuleBranch(int index){
		//Return the (RuleBranch) from this (RuleSet) at the supplied (index)
		if(index < 0) {
			String errString = "(puGetRuleBranch) failed because the supplied (index) is less than zero!";
			System.out.println(errString);
			return new RuleBranch();
		} else if (index >= pvNumBranches) {
			String errString = "(puGetRuleBranch) failed because the supplied (index) is greater than the number of (RuleBranches) in this (RuleSet)!";
			System.out.println(errString);
			return new RuleBranch();
		} else {
			return pvBranches[index];
		}
	}
	public String puOutputRuleBranch(int index) {
		//Return a string containing the (RuleBranch) at the supplied (index)
		if(index < 0) {
			String errString = "(puGetRuleBranch) failed because the supplied (index) is less than zero!";
			System.out.println(errString);
			return errString;
		} else if (index >= pvNumBranches) {
			String errString = "(puGetRuleBranch) failed because the supplied (index) is greater than the number of (RuleBranches) in this (RuleSet)!";
			System.out.println(errString);
			return errString;
		} else {
			StringBuilder text = new StringBuilder();
        	text.append("StartingNucleus,EndingNucleus,Type,Energy(MeV),Halflife(s),Probability" + System.getProperty("line.separator"));
        	for (int x = 0;x<pvBranches[index].puGetNumRules();x++) {
        		text.append(pvBranches[index].puGetStartNucleus(x) + "," + pvBranches[index].puGetEndNucleus(x) + "," + pvBranches[index].puGetType(x) + "," + pvBranches[index].puGetEnergy(x) + "," + pvBranches[index].puGetHalfLife(x) + "," + pvBranches[index].puGetProbability(x) + System.getProperty("line.separator"));
        	}
        	text.append(System.getProperty("line.separator"));
        	return text.toString();
		}
	}
	public String puOutputRuleSet() {
		//Return a string containing this (RuleSet)
		StringBuilder text = new StringBuilder();
       	text.append("StartingNucleus,EndingNucleus,Type,Energy(MeV),Halflife(s),Probability" + System.getProperty("line.separator"));
       	for (int x = 0;x<pvNumRule;x++) {
       		text.append(pvStartNuclei[x] + "," + pvEndNuclei[x] + "," + pvType[x] + "," + pvEnergy[x] + "," + pvHalfLife[x] + "," + pvProbability[x] + System.getProperty("line.separator"));
       	}
       	return text.toString();
	}
	private void pvAddRuleBranch(){
		//Adds an empty RuleBranch to pvRuleBranches
		RuleBranch[] newBranches = new RuleBranch[pvNumBranches+1];
		for (int x = 0; x < pvNumBranches;x++){
			newBranches[x] = pvBranches[x];
		}
		newBranches[pvNumBranches] = new RuleBranch();
		pvBranches = newBranches;
		pvNumBranches++;
	}
	private String pvReadData(String input) throws IOException {
		// reads data into a string
		StringBuilder text = new StringBuilder();
	    Scanner scanner = new Scanner(new FileInputStream(input), "UTF-8");
	    try {
	    	while (scanner.hasNextLine()){
	        	text.append(scanner.nextLine() + System.getProperty("line.separator"));
	        }
	    } catch (Exception e) {
	    	System.out.println(e);
	    	System.out.println("Input file: " + input + " failed to open for reading");
	    }
	    finally{
	      scanner.close();
	    }
	    return text.toString();
	}
	public int puGetNumberOfNuclei() {
		//retrieve the total number of different nuclei
		return pvNumNuclei;
	}
	public int puGetNumberOfRules() {
		//retrieve the total number of rules in this (RuleSet)
		return pvNumRule;
	}
	public String puGetStartNucleus(int index) {
		//retrieve the starting nucleus String at the supplied index (first rule is at index 0)
		if (index < 0){
			System.out.println("(puGetStartNucleus) failed because the supplied (index) was less than zero");
			return "Failed!";
		}
		if (index <= (pvNumRule-1)) {
			return pvStartNuclei[index];
		} else {
			System.out.println("(puGetStartNucleus) failed because the supplied (index) was out of bounds");
			return "Failed!";
		}
	}
	public String puGetEndNucleus(int index) {
		//retrieve the ending nucleus String at the supplied index (first rule is at index 0)
		if (index < 0){
			System.out.println("(puGetEndNucleus) failed because the supplied (index) was less than zero");
			return "Failed!";
		}
		if (index <= (pvNumRule-1)) {
			return pvEndNuclei[index];
		} else {
			System.out.println("(puGetEndNucleus) failed because the supplied (index) was out of bounds");
			return "Failed!";
		}
	}
	public String puGetType(int index) {
		//retrieve the event type String at the supplied index (first rule is at index 0)
		if (index < 0){
			System.out.println("(puGetType) failed because the supplied (index) was less than zero");
			return "Failed!";
		}
		if (index <= (pvNumRule-1)) {
			return pvType[index];
		} else {
			System.out.println("(puGetType) failed because the supplied (index) was out of bounds");
			return "Failed!";
		}
	}
	public double puGetEnergy(int index) {
		//retrieve the event energy double at the supplied index (first rule is at index 0)
		if (index < 0){
			System.out.println("(puGetEnergy) failed because the supplied (index) was less than zero");
			return -1;
		}
		if (index <= (pvNumRule-1)) {
			return pvEnergy[index];
		} else {
			System.out.println("(puGetEnergy) failed because the supplied (index) was out of bounds");
			return -1;
		}
	}
	public double puGetHalfLife(int index) {
		//retrieve the event half life double at the supplied index (first rule is at index 0)
		if (index < 0){
			System.out.println("(puGetHalfLife) failed because the supplied (index) was less than zero");
			return -1;
		}
		if (index <= (pvNumRule-1)) {
			return pvHalfLife[index];
		} else {
			System.out.println("(puGetHalfLife) failed because the supplied (index) was out of bounds");
			return -1;
		}
	}
	public double puGetProbability(int index) {
		//retrieve the event probability double at the supplied index (first rule is at index 0)
		if (index < 0){
			System.out.println("(puGetProbability) failed because the supplied (index) was less than zero");
			return -1;
		}
		if (index <= (pvNumRule-1)) {
			return pvProbability[index];
		} else {
			System.out.println("(puGetProbability) failed because the supplied (index) was out of bounds");
			return -1;
		}
	}
	public void puAddRule(String startNucleus, String endNucleus, String type, double energy, double halfLife, double probability) {
		//Add a rule to the (RuleSet) with the supplied parameters
		if (energy <= 0){
			System.out.println("(puAddRule) failed because the input (energy) was not greater than zero");
		}
		if (halfLife <= 0){
			System.out.println("(puAddRule) failed because the input (halfLife) was not greater than zero");
		}
		if (energy > 0 & halfLife > 0) {
			if (pvNumRule == 0) {
				pvStartNuclei = new String[1];
				pvStartNuclei[0] = startNucleus;
				pvEndNuclei = new String[1];
				pvEndNuclei[0] = endNucleus;
				pvType = new String[1];
				pvType[0] = type;
				pvEnergy = new double[1];
				pvEnergy[0] = energy;
				pvHalfLife = new double[1];
				pvHalfLife[0] = halfLife;
				pvProbability = new double[1];
				pvProbability[0] = probability;
				pvNumRule++;
			} else {
				String[] s,e,t;
				double[] en,hl,p;
				s = new String[pvNumRule+1];
				e = new String[pvNumRule+1];
				t = new String[pvNumRule+1];
				en = new double[pvNumRule+1];
				hl = new double[pvNumRule+1];
				p = new double[pvNumRule+1];
				for (int x = 0; x<pvNumRule; x++) {
					s[x] = pvStartNuclei[x];
					e[x] = pvEndNuclei[x];
					t[x] = pvType[x];
					en[x] = pvEnergy[x];
					hl[x] = pvHalfLife[x];
					p[x] = pvProbability[x];
				}
				s[pvNumRule] = startNucleus;
				e[pvNumRule] = endNucleus;
				t[pvNumRule] = type;
				en[pvNumRule] = energy;
				hl[pvNumRule] = halfLife;
				p[pvNumRule] = probability;
				pvStartNuclei = s;
				pvEndNuclei = e;
				pvType = t;
				pvEnergy = en;
				pvHalfLife = hl;
				pvProbability = p;
				pvNumRule++;
			}
		}
	}
	public void puDelRule(int index) {
		//Remove a rule from the (RuleSet) at the supplied index
		if (index < 0){
			System.out.println("(puDelRule) failed because the supplied (index) was less than zero");
		} else if (index <= ((pvNumRule-1))) {
			if (pvNumRule > 1) {
				String[] s,e,t;
				double[] en,hl,p;
				s = new String[pvNumRule-1];
				e = new String[pvNumRule-1];
				t = new String[pvNumRule-1];
				en = new double[pvNumRule-1];
				hl = new double[pvNumRule-1];
				p = new double[pvNumRule-1];
				for (int x = 0; x<index; x++) {
					s[x] = pvStartNuclei[x];
					e[x] = pvEndNuclei[x];
					t[x] = pvType[x];
					en[x] = pvEnergy[x];
					hl[x] = pvHalfLife[x];
					p[x] = pvProbability[x];
				}
				for (int x = index+1; x<pvNumRule; x++) {
					s[x-1] = pvStartNuclei[x];
					e[x-1] = pvEndNuclei[x];
					t[x-1] = pvType[x];
					en[x-1] = pvEnergy[x];
					hl[x-1] = pvHalfLife[x];
					p[x-1] = pvProbability[x];
				}
				pvStartNuclei = s;
				pvEndNuclei = e;
				pvType = t;
				pvEnergy = en;
				pvHalfLife = hl;
				pvProbability = p;
				pvNumRule+=1;
			} else {
				System.out.println("(puDelRule) failed because there are no rules to delete!");
			}
		} else {
			System.out.println("(puDelRule) failed because the supplied (index) was out of bounds");
		}
	}
}





