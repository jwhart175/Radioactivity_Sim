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

public class DecayChainRuleSet extends DecayRuleBranch {
	//A class to read and contain decay chain rules from specially formatted input files
	/* Variable and Function Nomenclature prescripts:
     * pv = private
     * pr = protected
     * pu = public
     * pvs = private static
     * prs = protected static
     * pus = public static
     * pvsf = private static final
     * prsf = protected static final
     * pusf = public static final
     */
	private String[] pvNuclei; //All independent nuclei in the rule set
	private int pvNumBranches = 0; //number of DecayRuleBranch objects
	private DecayRuleBranch[] pvBranches; //Stores the rule branches for predictive computation
	private int pvNumNuclei = 0; //The total number of independent nuclei
	public DecayChainRuleSet() {
		// Blank (DecayChainRuleSet) constructor
	}
	public void puReorderProbabilities() {
		//Hides the (puReorderProbabilities) from the super class
	}
	public DecayChainRuleSet(String input) {
		//DecayChainRuleSet Constructor that reads in and parses a DecayChainRuleSet file
		String inputData = "";
    	try {
        	inputData = pvReadData(input);
        	//System.out.println(inputData);
        } catch (Exception e) {
        	System.out.println(e);
        	System.out.println("Input file: " + input + " failed to open for reading");
        }
    	boolean noLineEnd = true;
    	boolean stillRuleParsing = true;
    	int z = 0;
    	String ruleLine = "";
    	while (noLineEnd) {
    		if((z+1)>inputData.length()){
    			noLineEnd = false;
    			break;
    		}
    		if (inputData.substring(z,z+1).compareTo(System.getProperty("line.separator"))==0) {
    			ruleLine = inputData.substring(0,z) + "       ";
    			inputData = inputData.substring(z+1,inputData.length());
    			z = 0;
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
    		z++;
    	}
    	pvAddDecayRuleBranch();
    	for(int x = 0; x<prNumRule; x++){
    		if(x == 0) {
    			pvBranches[0].puAddRule(prStartNuclei[x],prEndNuclei[x],prType[x],prEnergy[x],prHalfLife[x],prProbability[x]);
    		} else {
    			for(int y = 0; y<pvNumBranches; y++){
    				if(prStartNuclei[x].compareTo(pvBranches[y].puGetEndNucleus(pvBranches[y].puGetNumRules()-1))==0) {
    	    			pvBranches[y].puAddRule(prStartNuclei[x],prEndNuclei[x],prType[x],prEnergy[x],prHalfLife[x],prProbability[x]);
    					if (x<prNumRule-1){
    						if(prProbability[x]+prProbability[x+1]==1) {
    							pvAddDecayRuleBranch();
    	    					for (int w = 0; w<pvBranches[y].puGetNumRules()-1; w++) {
    	    						pvBranches[pvNumBranches-1].puAddRule(pvBranches[y].puGetStartNucleus(w),pvBranches[y].puGetEndNucleus(w),pvBranches[y].puGetType(w),pvBranches[y].puGetEnergy(w),pvBranches[y].puGetHalfLife(w),pvBranches[y].puGetProbability(w));
    	    					}
    	    					pvBranches[pvNumBranches-1].puAddRule(prStartNuclei[x+1],prEndNuclei[x+1],prType[x+1],prEnergy[x+1],prHalfLife[x+1],prProbability[x+1]);
    						}
    	    			}
    					if (x<prNumRule-2){
    	    				if(prProbability[x]+prProbability[x+1]+prProbability[x+2]==1){
    	    					pvAddDecayRuleBranch();
    	    					pvAddDecayRuleBranch();
    	    					for (int w = 0; w<pvBranches[y].puGetNumRules()-1; w++) {
    	    						pvBranches[pvNumBranches-2].puAddRule(pvBranches[y].puGetStartNucleus(w),pvBranches[y].puGetEndNucleus(w),pvBranches[y].puGetType(w),pvBranches[y].puGetEnergy(w),pvBranches[y].puGetHalfLife(w),pvBranches[y].puGetProbability(w));
    	    						pvBranches[pvNumBranches-1].puAddRule(pvBranches[y].puGetStartNucleus(w),pvBranches[y].puGetEndNucleus(w),pvBranches[y].puGetType(w),pvBranches[y].puGetEnergy(w),pvBranches[y].puGetHalfLife(w),pvBranches[y].puGetProbability(w));
    	    					}
    	    					pvBranches[pvNumBranches-2].puAddRule(prStartNuclei[x+1],prEndNuclei[x+1],prType[x+1],prEnergy[x+1],prHalfLife[x+1],prProbability[x+1]);
    	    					pvBranches[pvNumBranches-1].puAddRule(prStartNuclei[x+2],prEndNuclei[x+2],prType[x+2],prEnergy[x+2],prHalfLife[x+2],prProbability[x+2]);
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
    	for(int x = 0; x<prNumRule; x++){
    		r = 0;
    		for (int y = 0; y<x; y++){
    			if(prStartNuclei[x].compareTo(prStartNuclei[y])==0){
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
    	for(int x = 0; x<prNumRule; x++){
    		r = 0;
    		for (int y = 0; y<x; y++){
    			if(prStartNuclei[x].compareTo(prStartNuclei[y])==0){
    				r++;
    			}
    		}
    		if (r==0) {
    			pvNuclei[n] = prStartNuclei[x];
    			n++;

    		}
    	}
	}
	public String[] puGetNuclei() {
		//Returns a string array containing the independent nuclei addressed in this (DecayChainRuleSet)
		return pvNuclei;
	}
	public int puGetNumBranches() {
		//Returns the number of (DecayRuleBranch) objects in this (DecayChainRuleSet)
		return pvNumBranches;
	}
	public DecayRuleBranch puGetDecayRuleBranch(int index){
		//Return the (DecayRuleBranch) from this (DecayChainRuleSet) at the supplied (index)
		if(index < 0) {
			String errString = "(puGetDecayRuleBranch) failed because the supplied (index) is less than zero!";
			System.out.println(errString);
			return new DecayRuleBranch();
		} else if (index >= pvNumBranches) {
			String errString = "(puGetDecayRuleBranch) failed because the supplied (index) is greater than the number of (DecayRuleBranches) in this (DecayChainRuleSet)!";
			System.out.println(errString);
			return new DecayRuleBranch();
		} else {
			return pvBranches[index];
		}
	}
	public String puOutputDecayRuleBranch(int index) {
		//Return a string containing the (DecayRuleBranch) at the supplied (index)
		if(index < 0) {
			String errString = "(puGetDecayRuleBranch) failed because the supplied (index) is less than zero!";
			System.out.println(errString);
			return errString;
		} else if (index >= pvNumBranches) {
			String errString = "(puGetDecayRuleBranch) failed because the supplied (index) is greater than the number of (DecayRuleBranches) in this (DecayChainRuleSet)!";
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
	public String puOutputDecayChainRuleSet() {
		//Return a string containing this (DecayChainRuleSet)
		StringBuilder text = new StringBuilder();
       	text.append("StartingNucleus,EndingNucleus,Type,Energy(MeV),Halflife(s),Probability" + System.getProperty("line.separator"));
       	for (int x = 0;x<prNumRule;x++) {
       		text.append(prStartNuclei[x] + "," + prEndNuclei[x] + "," + prType[x] + "," + prEnergy[x] + "," + prHalfLife[x] + "," + prProbability[x] + System.getProperty("line.separator"));
       	}
       	return text.toString();
	}
	private void pvAddDecayRuleBranch(){
		//Adds an empty DecayRuleBranch to pvDecayRuleBranches
		DecayRuleBranch[] newBranches = new DecayRuleBranch[pvNumBranches+1];
		for (int x = 0; x < pvNumBranches;x++){
			newBranches[x] = pvBranches[x];
		}
		newBranches[pvNumBranches] = new DecayRuleBranch();
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

}
