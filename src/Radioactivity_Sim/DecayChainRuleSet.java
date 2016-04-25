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

public class DecayChainRuleSet extends DecayChainRuleBranch {
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

	/*
	 * Inherited variables and functions:
	 * protected static final double prsfDoubleZero = 0.0;
	 * protected static final double prsfDoubleOne = 1.0;
	 * protected static final double prsfDoubleTwo = 2.0;
	 * protected static final double prsfDoubleLN2 = 0.69314718;
	 * protected static final double prsfDoubleSQRT2 = 1.414213562;
	 * protected static final double prsfDoubleMinusOne = -1.0;
	 * protected static final double prsfDoubleThousand = 1000.0;
	 * protected static final double prsfDoubleHundred = 100.0;
	 * protected static final double prsfDoubleFiveHundredThousand = 500000;
	 * protected static final double prsfDouble3600 = 3600.0;
	 * protected static final double prsfDoubleThreeHalves = 1.5;
	 * protected static final double prsfDoubleNineTenths = 0.9;
	 * protected static final double prsfDoubleElevenTenths = 1.1;
	 * protected static final int prsfInt8760 = 8760;
	 * protected static final int prsfIntEighty = 80;
	 * protected static final int prsfInt365 = 365;
	 * protected static final int prsfInt24 = 24;
	 * protected static final int prsfInt3600 = 3600;
	 * protected static final int prsfIntZero = 0;
	 * protected static final int prsfIntOne = 0;
	 * protected static final int prsfIntTwo = 2;
	 * protected static final int prsfIntThree = 3;
	 * protected static final int[] prsfDetailedTest = ...
	 * protected DecayChainRule[] prRules; //The rules contained within the rule branch
	 * protected int prNumRule = 0; //The total number of rules
	 * public int puAddRule(String start, String end, String type, double energy, double halflife, double probability)
	 * public int puAddRule(DecayChainRule rule)
	 * public void puAddGammaToRule(int index, String name, double energy, double intensity)
	 * public void puAddBetaToRule(int index, String name, double energy, double intensity)
	 * public void puDelRule(int index)
	 * overridden public void puReorderProbabilities()
	 * public String puGetStartNucleus(int index)
	 * public String puGetEndNucleus(int index)
	 * public String puGetType(int index)
	 * public double puGetEnergy(int index)
	 * public double puGetHalfLife(int index)
	 * public double puGetProbability(int index)
	 * public int puGetNumRules()
	 * public DecayChainRule puGetRule(int index)
	 * public int puGetNumGammasFromRule(int index)
	 * public String[] puGetGammaNamesFromRule(int index)
	 * public double[] puGetGammaEnergiesFromRule(int index)
	 * public double[] puGetGammaIntensitiesFromRule(int index)
	 * public int puGetNumBetasFromRule(int index)
	 * public String[] puGetBetaNamesFromRule(int index)
	 * public double[] puGetBetaEnergiesFromRule(int index)
	 * public double[] puGetBetaIntensitiesFromRule(int index)
	 */
	private String[] pvNuclei; //All independent nuclei in the rule set
	private int pvNumBranches = prsfIntZero; //number of DecayChainRuleBranch objects
	private DecayChainRuleBranch[] pvBranches; //Stores the rule branches for predictive computation
	private int pvNumNuclei = prsfIntZero; //The total number of independent nuclei

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
    	int z = prsfIntZero;
    	String ruleLine = "";
    	while (noLineEnd) {
    		if((z+1)>inputData.length()){
    			noLineEnd = false;
    			break;
    		}
    		if (inputData.substring(z,z+1).compareTo(System.getProperty("line.separator"))==prsfIntZero) {
    			ruleLine = inputData.substring(prsfIntZero,z) + "       ";
    			inputData = inputData.substring(z+1,inputData.length());
    			z = prsfIntZero;
    			int y = prsfIntZero;
    			int ruleNum = prsfIntZero;
    			String startNucleus = "";
    			String endNucleus = "";
    			String type = "";
    			double energy = prsfIntZero;
    			double halfLife = prsfIntZero;
    			double probability = prsfIntZero;
    			if (ruleLine.substring(prsfIntZero,prsfIntOne).compareTo("#")==prsfIntZero){
    				stillRuleParsing = false;
    			} else if (ruleLine.substring(prsfIntZero,prsfIntOne).compareTo(" ")==prsfIntZero){
    				stillRuleParsing = false;
    			} else if (ruleLine.substring(prsfIntZero,prsfIntTwo).compareTo("//")==prsfIntZero){
    				stillRuleParsing = false;
    			}
    			while (stillRuleParsing) {
    				if (ruleLine.substring(y,y+prsfIntOne).compareTo(" ")==prsfIntZero) {
    					ruleNum++;
    					switch (ruleNum) {
    					case 1:
    						startNucleus = ruleLine.substring(prsfIntZero,y);
    						ruleLine = ruleLine.substring(y+prsfIntOne,ruleLine.length());
    						y = prsfIntZero;
    						break;
    					case 2:
    						endNucleus = ruleLine.substring(prsfIntZero,y);
    						ruleLine = ruleLine.substring(y+prsfIntOne,ruleLine.length());
    						y = prsfIntZero;
    						break;
    					case 3:
    						type = ruleLine.substring(prsfIntZero,y);
    						ruleLine = ruleLine.substring(y+prsfIntOne,ruleLine.length());
    						y = prsfIntZero;
    						break;
    					case 4:
    						energy = Double.valueOf(ruleLine.substring(prsfIntZero,y));
    						ruleLine = ruleLine.substring(y+prsfIntOne,ruleLine.length());
    						y = prsfIntZero;
    						break;
    					case 5:
    						halfLife = Double.valueOf(ruleLine.substring(prsfIntZero,y));
    						ruleLine = ruleLine.substring(y+prsfIntOne,ruleLine.length());
    						y = prsfIntZero;
    						break;
    					case 6:
    						probability = Double.valueOf(ruleLine.substring(prsfIntZero,y));
    						ruleLine = ruleLine.substring(y+prsfIntOne,ruleLine.length());
    						stillRuleParsing = false;
    						puAddRule(startNucleus,endNucleus,type,energy,halfLife,probability);
    						if (startNucleus.compareTo("")==prsfIntZero){
    							System.out.println("Parsing of the input file, " + input + " failed at the StartNucleus string!");
    						}
    						if (endNucleus.compareTo("")==prsfIntZero){
    							System.out.println("Parsing of the input file, " + input + " failed at the EndNucleus string!");
    						}
    						if (type.compareTo("alpha")!=prsfIntZero&type.compareTo("beta-")!=prsfIntZero&type.compareTo("beta+")!=prsfIntZero&type.compareTo("gamma")!=prsfIntZero&type.compareTo("neutron&beta-")!=prsfIntZero){
    							System.out.println("Parsing of the input file, " + input + " failed at the type string!");
    							System.out.println("type should be one of the following: alpha, beta-, beta+, gamma, neutron&beta-");
    						}
    						if (energy<prsfIntZero){
    							System.out.println("Parsing of the input file, " + input + " failed because the energy was less than zero!");
    						}
    						if (halfLife<prsfIntZero){
    							System.out.println("Parsing of the input file, " + input + " failed because the halflife was less than zero!");
    						}
    						if (probability<prsfIntZero|probability>prsfIntOne){
    							System.out.println("Parsing of the input file, " + input + " failed because the probability was less than zero or greater than one!");
    						}
    						break;
    					case 7:
    						stillRuleParsing = false;
    						break;
    					}
    				}
    				y++;
    				if ((y+prsfIntOne)>ruleLine.length()){
    					stillRuleParsing = false;
    				}
    			}
    			stillRuleParsing = true;
    		}
    		z++;
    	}
    	pvAddDecayChainRuleBranch();
    	for(int x = prsfIntZero; x<prNumRule; x++){
    		if(x == prsfIntZero) {
    			pvBranches[prsfIntZero].puAddRule(prRules[prsfIntZero].puGetStartNucleus(),prRules[prsfIntZero].puGetEndNucleus(),prRules[prsfIntZero].puGetType(),prRules[prsfIntZero].puGetEnergy(),prRules[prsfIntZero].puGetHalfLife(),prRules[prsfIntZero].puGetProbability());
    			if(x+1<prNumRule){
    				if(prRules[prsfIntZero].puGetProbability()+prRules[prsfIntOne].puGetProbability()==prsfIntOne) {
    					pvAddDecayChainRuleBranch();
    					pvBranches[prsfIntOne].puAddRule(prRules[prsfIntOne].puGetStartNucleus(),prRules[prsfIntOne].puGetEndNucleus(),prRules[prsfIntOne].puGetType(),prRules[prsfIntOne].puGetEnergy(),prRules[prsfIntOne].puGetHalfLife(),prRules[prsfIntOne].puGetProbability());
    				}
    			}
    		} else {
    			for(int y = prsfIntZero; y<pvNumBranches; y++){
    				if(prRules[x].puGetStartNucleus().compareTo(pvBranches[y].puGetEndNucleus(pvBranches[y].puGetNumRules()-1))==0) {
    	    			pvBranches[y].puAddRule(prRules[x].puGetStartNucleus(),prRules[x].puGetEndNucleus(),prRules[x].puGetType(),prRules[x].puGetEnergy(),prRules[x].puGetHalfLife(),prRules[x].puGetProbability());
    					if (x<prNumRule-prsfIntOne){
    						if(prRules[x].puGetProbability()+prRules[x+prsfIntOne].puGetProbability()==prsfIntOne) {
    							pvAddDecayChainRuleBranch();
    	    					for (int w = prsfIntZero; w<pvBranches[y].puGetNumRules()-prsfIntOne; w++) {
    	    						pvBranches[pvNumBranches-prsfIntOne].puAddRule(pvBranches[y].puGetStartNucleus(w),pvBranches[y].puGetEndNucleus(w),pvBranches[y].puGetType(w),pvBranches[y].puGetEnergy(w),pvBranches[y].puGetHalfLife(w),pvBranches[y].puGetProbability(w));
    	    					}
    	    					pvBranches[pvNumBranches-prsfIntOne].puAddRule(prRules[x+prsfIntOne].puGetStartNucleus(),prRules[x+prsfIntOne].puGetEndNucleus(),prRules[x+prsfIntOne].puGetType(),prRules[x+prsfIntOne].puGetEnergy(),prRules[x+prsfIntOne].puGetHalfLife(),prRules[x+prsfIntOne].puGetProbability());
    						}
    	    			}
    					if (x<prNumRule-prsfIntTwo){
    	    				if(prRules[x].puGetProbability()+prRules[x+prsfIntOne].puGetProbability()+prRules[x+prsfIntTwo].puGetProbability()==prsfIntOne){
    	    					pvAddDecayChainRuleBranch();
    	    					pvAddDecayChainRuleBranch();
    	    					for (int w = prsfIntZero; w<pvBranches[y].puGetNumRules()-prsfIntOne; w++) {
    	    						pvBranches[pvNumBranches-prsfIntTwo].puAddRule(pvBranches[y].puGetStartNucleus(w),pvBranches[y].puGetEndNucleus(w),pvBranches[y].puGetType(w),pvBranches[y].puGetEnergy(w),pvBranches[y].puGetHalfLife(w),pvBranches[y].puGetProbability(w));
    	    						pvBranches[pvNumBranches-prsfIntOne].puAddRule(pvBranches[y].puGetStartNucleus(w),pvBranches[y].puGetEndNucleus(w),pvBranches[y].puGetType(w),pvBranches[y].puGetEnergy(w),pvBranches[y].puGetHalfLife(w),pvBranches[y].puGetProbability(w));
    	    					}
    	    					pvBranches[pvNumBranches-prsfIntTwo].puAddRule(prRules[x+prsfIntOne].puGetStartNucleus(),prRules[x+prsfIntOne].puGetEndNucleus(),prRules[x+prsfIntOne].puGetType(),prRules[x+prsfIntOne].puGetEnergy(),prRules[x+prsfIntOne].puGetHalfLife(),prRules[x+prsfIntOne].puGetProbability());
    	    					pvBranches[pvNumBranches-prsfIntOne].puAddRule(prRules[x+prsfIntTwo].puGetStartNucleus(),prRules[x+prsfIntTwo].puGetEndNucleus(),prRules[x+prsfIntTwo].puGetType(),prRules[x+prsfIntTwo].puGetEnergy(),prRules[x+prsfIntTwo].puGetHalfLife(),prRules[x+prsfIntTwo].puGetProbability());
    	    				}
    	    			}
    				}
    			}
     		}
    	}
    	for (int x = prsfIntZero; x<pvBranches.length;x++) {
    		pvBranches[x].puReorderProbabilities();
    	}
    	int n = prsfIntZero, r = prsfIntZero;
    	for(int x = prsfIntZero; x<prNumRule; x++){
    		r = prsfIntZero;
    		for (int y = prsfIntZero; y<x; y++){
    			if(prRules[x].puGetStartNucleus().compareTo(prRules[y].puGetStartNucleus())==prsfIntZero){
    				r++;
    			}
    		}
    		if (r==prsfIntZero) {
    			n++;
    		}
    	}
    	pvNumNuclei = n;
    	pvNuclei = new String[n];
    	n = prsfIntZero;
    	r = prsfIntZero;
    	for(int x = prsfIntZero; x<prNumRule; x++){
    		r = prsfIntZero;
    		for (int y = prsfIntZero; y<x; y++){
    			if(prRules[x].puGetStartNucleus().compareTo(prRules[y].puGetStartNucleus())==prsfIntZero){
    				r++;
    			}
    		}
    		if (r==prsfIntZero) {
    			pvNuclei[n] = prRules[x].puGetStartNucleus();
    			n++;

    		}
    	}
	}

	public String[] puGetNuclei() {
		//Returns a string array containing the independent nuclei addressed in this (DecayChainRuleSet)
		return pvNuclei;
	}

	public int puGetNumBranches() {
		//Returns the number of (DecayChainRuleBranch) objects in this (DecayChainRuleSet)
		return pvNumBranches;
	}

	public DecayChainRuleBranch puGetDecayChainRuleBranch(int index){
		//Return the (DecayChainRuleBranch) from this (DecayChainRuleSet) at the supplied (index)
		if(index < prsfIntZero) {
			String errString = "(puGetDecayChainRuleBranch) failed because the supplied (index) is less than zero!";
			System.out.println(errString);
			return new DecayChainRuleBranch();
		} else if (index >= pvNumBranches) {
			String errString = "(puGetDecayChainRuleBranch) failed because the supplied (index) is greater than the number of (DecayChainRuleBranches) in this (DecayChainRuleSet)!";
			System.out.println(errString);
			return new DecayChainRuleBranch();
		} else {
			return pvBranches[index];
		}
	}

	public String puOutputDecayChainRuleBranch(int index) {
		//Return a string containing the (DecayChainRuleBranch) at the supplied (index)
		if(index < prsfIntZero) {
			String errString = "(puGetDecayChainRuleBranch) failed because the supplied (index) is less than zero!";
			System.out.println(errString);
			return errString;
		} else if (index >= pvNumBranches) {
			String errString = "(puGetDecayChainRuleBranch) failed because the supplied (index) is greater than the number of (DecayChainRuleBranches) in this (DecayChainRuleSet)!";
			System.out.println(errString);
			return errString;
		} else {
			StringBuilder text = new StringBuilder();
        	text.append("StartingNucleus,EndingNucleus,Type,Energy(MeV),Halflife(s),Probability" + System.getProperty("line.separator"));
        	for (int x = prsfIntZero;x<pvBranches[index].puGetNumRules();x++) {
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
       	for (int x = prsfIntZero;x<prNumRule;x++) {
       		text.append(prRules[x].puGetStartNucleus() + "," + prRules[x].puGetEndNucleus() + "," + prRules[x].puGetType() + "," + prRules[x].puGetEnergy() + "," + prRules[x].puGetHalfLife() + "," + prRules[x].puGetProbability() + System.getProperty("line.separator"));
       	}
       	return text.toString();
	}

	private void pvAddDecayChainRuleBranch(){
		//Adds an empty DecayChainRuleBranch to pvDecayChainRuleBranches
		DecayChainRuleBranch[] newBranches = new DecayChainRuleBranch[pvNumBranches+1];
		for (int x = prsfIntZero; x < pvNumBranches;x++){
			newBranches[x] = pvBranches[x];
		}
		newBranches[pvNumBranches] = new DecayChainRuleBranch();
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
