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

public class NucleiSamplePredictiveSim extends PRSFNUM {
	// a container for a group of user defined unstable nuclei and all of the radioactive DecayEvents that they transition through to reach stability
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
	 * Inherited Variables and Functions:
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
	 * protected static final int[] prsfDetailedTest = ...
	 */

    private DecayEventSet[] pvDecayEventSets;
    private DecayChainRuleSet[] pvRules;
    private int pvNumDecayChainRuleSets = prsfIntZero;
    private int pvNumDecayEventSets = prsfIntZero;
    private double pvStartTime = prsfIntZero;
    private double pvEndTime = prsfIntZero;
    private int pvResolution = prsfIntZero;
    private double[] pvFinalPartNum = new double[prsfIntOne];
    private String[] pvNuclei = new String[prsfIntOne];
    private double pvNucleiStarter = prsfIntZero;

    public NucleiSamplePredictiveSim(int num,String input, double start, double end, int resolution) {
    	//Constructor for a sample containing a number of nuclei of a single type
    	pvNucleiStarter = Math.random();
        pvNuclei[prsfIntZero] = Double.toString(pvNucleiStarter);
    	if (num > prsfIntZero) {
            pvRules = new DecayChainRuleSet[prsfIntOne];
            pvRules[prsfIntZero] = new DecayChainRuleSet(input);
            pvNumDecayChainRuleSets = prsfIntOne;
            pvStartTime = start;
            pvEndTime = end;
            pvResolution = resolution;
            pvPredictNumCalcParticles(num,pvRules[prsfIntZero]);
        } else {
            System.out.println("Construction of this (NucleiSamplePredictiveSim) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSamplePredictiveSim(double num,String input, double start, double end, int resolution) {
    	//Constructor for a sample containing a number of nuclei of a single type
    	pvNucleiStarter = Math.random();
        pvNuclei[prsfIntZero] = Double.toString(pvNucleiStarter);
        if (num > prsfIntZero) {
            pvRules = new DecayChainRuleSet[prsfIntOne];
            pvRules[prsfIntZero] = new DecayChainRuleSet(input);
            pvNumDecayChainRuleSets = prsfIntOne;
            pvStartTime = start;
            pvEndTime = end;
            pvResolution = resolution;
            pvPredictNumCalcParticles(num, pvRules[prsfIntZero]);
        } else {
            System.out.println("Construction of this (NucleiSamplePredictiveSim) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSamplePredictiveSim() {
    	//Constructor for a sample to be user-defined with the (puAddSpecies) function
    	pvNucleiStarter = Math.random();
        pvNuclei[prsfIntZero] = Double.toString(pvNucleiStarter);
    }

    public int puGetNumDecayChainRuleSets() {
    	//Returns the total number of (DecayChainRuleSet) objects
    	return pvNumDecayChainRuleSets;
    }

    public DecayChainRuleSet puGetDecayChainRuleSet(int index) {
    	//Returns the (DecayChainRuleSet) at the specified (index)
    	if(index < prsfIntZero) {
    		String errString = "(puGetDecayChainRuleSet) failed because the supplied (index) is less than zero!";
    		System.out.println(errString);
    		return new DecayChainRuleSet();
    	} else if (index >= pvNumDecayChainRuleSets) {
    		String errString = "(puGetDecayChainRuleSet) failed because the supplied (index) is greater than the total of number of (DecayChainRuleSet) objects in this (NucleiSamplePredictiveSim)!";
    		System.out.println(errString);
    		return new DecayChainRuleSet();
    	} else {
    		return pvRules[index];
    	}
    }

    private void pvPredictNumCalcParticles(double num, DecayChainRuleSet rules) {
    	//Method to predict the number of DecayEvents of each type
    	double dividend = prsfIntOne, subproduct = prsfIntOne, subsum = prsfIntZero, subsum2 = prsfIntZero;
    	int numBranches = rules.puGetNumBranches();
    	double[] eventNum = new double[prsfIntOne];
    	double[] finalPartNum = new double[prsfIntOne];
    	double[][] startNumTime = new double[prsfIntOne][prsfIntOne];
    	double delta = (pvEndTime-pvStartTime)/pvResolution;
    	String[] nuclei = new String[prsfIntOne];
    	DecayRuleBranch branch = new DecayRuleBranch();
    	for (int x = prsfIntZero; x<numBranches ;x++) {
    		branch = rules.puGetDecayRuleBranch(x);
    		int numRules = branch.puGetNumRules();
    		eventNum = new double[numRules];
    		finalPartNum = new double[numRules];
    		startNumTime = new double[pvResolution][numRules];
    		for (int n = prsfIntZero; n < numRules; n++) {
    			boolean newNucleus = true;
    			for(int q = prsfIntZero;q < pvNuclei.length; q++) {
    				if(pvNuclei.length ==prsfIntOne){
    					if(Double.toString(pvNucleiStarter).compareTo(pvNuclei[prsfIntZero])==prsfIntZero){
    						newNucleus = false;
    						pvNuclei[prsfIntZero] = branch.puGetStartNucleus(n);
    						break;
    					}
    				}
    				if(pvNuclei[q].compareTo(branch.puGetStartNucleus(n))==prsfIntZero){
    					newNucleus = false;
    				}
    			}
    			if(newNucleus){
    				nuclei = new String[pvNuclei.length+prsfIntOne];
    				for(int q = prsfIntZero;q < pvNuclei.length; q++) {
        				nuclei[q] = pvNuclei[q];
        			}
    				nuclei[pvNuclei.length] = branch.puGetStartNucleus(n);
    				pvNuclei = nuclei;
    			}

    			subsum2 = prsfIntZero;
    			eventNum[n] = prsfIntZero;
    			if(n == prsfIntZero) {
    			//Calculate particle numbers at pvStartTime using the normal Bateman Solution
    				for (int w = prsfIntZero; w<pvResolution;w++) {
        				double newTime = w*delta + pvStartTime;
        				for (int i = prsfIntZero; i <= n; i++){
        					subsum = prsfIntZero;
        					subproduct = prsfIntOne;
        					for (int j = i; j <= n; j++){
        						dividend = prsfIntOne;
        						for (int p = i; p <= n; p++){
        							if (p != j) {
        								dividend = dividend * ((prsfDoubleLN2/branch.puGetHalfLife(p)-prsfDoubleLN2/branch.puGetHalfLife(j)));
        							}
        						}
        						subsum = subsum + (Math.exp(-newTime*prsfDoubleLN2/branch.puGetHalfLife(j)))/dividend;
        						if (j <= (n-prsfIntOne)) {
        							subproduct = subproduct * prsfDoubleLN2/branch.puGetHalfLife(j);
        						}
        						subproduct = subproduct * branch.puGetProbability(j);
        					}
        					if (i==prsfIntZero) {
        						subsum2 = num*subsum*subproduct;
        					} else {
        						subsum2 = subsum2 + prsfIntZero*subsum*subproduct;
        					}
        				}
        				startNumTime[w][n] = subsum2;
    				}
    				//Calculates the number of DecayEvents for n = 0 between pvStartTime and pvEndTime
    				eventNum[n] = startNumTime[prsfIntZero][prsfIntZero]*(1-Math.exp(-(pvEndTime-pvStartTime)*prsfDoubleLN2/branch.puGetHalfLife(n)));
    				//Store the predicted events in a (DecayEventSet)
					if(eventNum[n]>prsfIntZero){
    					DecayEventSet instance = new DecayEventSet(eventNum[n],false,pvStartTime,pvEndTime,branch.puGetStartNucleus(n),branch.puGetEndNucleus(n),branch.puGetHalfLife(n),branch.puGetEnergy(n),branch.puGetType(n));
    					pvAddDecayEventSet(instance);
					}

    			} else if (n>prsfIntZero) {
    				//Calculates DecayEvents for the child nuclei
    				double newTime = prsfIntZero;
    				for (int w = prsfIntZero; w<pvResolution;w++) {
    					newTime = w*delta + pvStartTime;
    					for (int i = prsfIntZero; i <= n; i++){
    						subsum = prsfIntZero;
    						subproduct = prsfIntOne;
    						for (int j = i; j <= n; j++){
    							dividend = prsfIntOne;
    							for (int p = i; p <= n; p++){
    								if (p != j) {
    									dividend = dividend * ((prsfDoubleLN2/branch.puGetHalfLife(p)-prsfDoubleLN2/branch.puGetHalfLife(j)));
    								}
    							}
    							subsum = subsum + (Math.exp(-newTime*prsfDoubleLN2/branch.puGetHalfLife(j)))/dividend;
    							if (j <= (n-prsfIntOne)) {
    								subproduct = subproduct * prsfDoubleLN2/branch.puGetHalfLife(j);
    							}
    							subproduct = subproduct * branch.puGetProbability(j);
    						}
    						if (i==prsfIntZero) {
    							subsum2 = num*subsum*subproduct;
    						} else {
    							subsum2 = subsum2 + prsfIntZero*subsum*subproduct;
    						}
    					}
    					startNumTime[w][n] = subsum2;
    				}

    				double subsum3 = prsfIntZero;
    				subsum2 = prsfIntZero;
        			for (int i = n-prsfIntOne; i >= prsfIntZero; i--){
        				subsum = prsfIntZero;
        				for (int j = prsfIntZero; j < pvResolution; j++){
        					subproduct = prsfIntOne;
        					for (int k = n; k >= i; k--){
        						if(k>i){
        							subproduct = subproduct * (prsfIntOne-Math.exp(-(delta)/(prsfDoubleTwo*(n-k))*prsfDoubleLN2/branch.puGetHalfLife(k)));
        						} else {
        							subproduct = subproduct * (prsfIntOne-Math.exp(-(delta)*prsfDoubleLN2/branch.puGetHalfLife(k)));
        						}
        					}
        					subsum3 = startNumTime[j][i]*subproduct*Math.pow((1-Math.exp(-(pvEndTime-pvStartTime)*prsfDoubleLN2/branch.puGetHalfLife(n))),2.5);
        					subsum += subsum3;
        					//Store the predicted events in a (DecayEventSet)
        					if(subsum3>prsfIntZero){
        						DecayEventSet instance = new DecayEventSet(subsum3,true,(pvStartTime+j*delta),(pvStartTime+(j+prsfIntOne)*delta),branch.puGetStartNucleus(n),branch.puGetEndNucleus(n),branch.puGetHalfLife(n),branch.puGetEnergy(n),branch.puGetType(n));
        						pvAddDecayEventSet(instance);
        					}
        				}
        				subsum2 = subsum2 + subsum;
        			}
//       				double subsum4 = startNumTime[prsfIntZero][n]*(prsfIntOne-Math.exp(-(pvEndTime-pvStartTime)*prsfDoubleLN2/branch.puGetHalfLife(n)));
//       				eventNum[n] = subsum2 + subsum4;
//       				if(subsum4>prsfIntZero){
//						DecayEventSet instance = new DecayEventSet(subsum4,true,(pvStartTime),(pvEndTime),branch.puGetStartNucleus(n),branch.puGetEndNucleus(n),branch.puGetHalfLife(n),branch.puGetEnergy(n),branch.puGetType(n));
//						pvAddDecayEventSet(instance);
//       				}

//        			double subsum3 = prsfIntZero;
//    				subsum2 = prsfIntZero;
//        			for (int i = n-prsfIntOne; i >= prsfIntZero; i--){
//        				subsum = prsfIntZero;
//        				for (int j = prsfIntZero; j < pvResolution; j++){
//        					subproduct = prsfIntOne;
//        					for (int k = n; k >= i; k--){
//        						subproduct = subproduct * (Math.exp(-(pvStartTime+j*delta)*prsfDoubleLN2/branch.puGetHalfLife(k))-Math.exp(-(pvStartTime+(j+prsfDoubleOne)*delta)*prsfDoubleLN2/branch.puGetHalfLife(k)));
//        	        		}
//        					subsum3 = startNumTime[j][i]*subproduct*Math.pow((Math.exp(-(pvStartTime)*prsfDoubleLN2/branch.puGetHalfLife(n))-Math.exp(-(pvEndTime)*prsfDoubleLN2/branch.puGetHalfLife(n))),prsfDoubleThreeHalves)*pvResolution;
//        					subsum += subsum3;
//        					//Store the predicted events in a (DecayEventSet)
//        					if(subsum3>prsfIntZero){
//        						DecayEventSet instance = new DecayEventSet(subsum3,true,(pvStartTime+j*delta),(pvStartTime+(j+prsfIntOne)*delta),branch.puGetStartNucleus(n),branch.puGetEndNucleus(n),branch.puGetHalfLife(n),branch.puGetEnergy(n),branch.puGetType(n));
//        						pvAddDecayEventSet(instance);
//        					}
//        				}
//        				subsum2 = subsum2 + subsum;
//        			}
//        			eventNum[n] = subsum2;
    			}

    			//Calculates final quantities for all particles
    			for (int i = prsfIntZero; i <= n; i++){
					subsum = prsfIntZero;
					subproduct = prsfIntOne;
					for (int j = i; j <= n; j++){
						dividend = prsfIntOne;
						for (int p = i; p <= n; p++){
							if (p != j) {
								dividend = dividend * ((prsfDoubleLN2/branch.puGetHalfLife(p)-prsfDoubleLN2/branch.puGetHalfLife(j)));
							}
						}
						subsum = subsum + (Math.exp(-pvEndTime*prsfDoubleLN2/branch.puGetHalfLife(j)))/dividend;
						if (j <= (n-prsfIntOne)) {
							subproduct = subproduct * prsfDoubleLN2/branch.puGetHalfLife(j);
						}
						subproduct = subproduct * branch.puGetProbability(j);
					}
					if (i==prsfIntZero) {
						subsum2 = num*subsum*subproduct;
					} else {
						subsum2 = subsum2 + prsfIntZero*subsum*subproduct;
					}
				}
				finalPartNum[n] = subsum2;
				//debug line to verify bateman calcs
				//System.out.println("Branch No. = " + x + ", Start nucleus = " + branch.puGetStartNucleus(n) + ", Final count = " + finalPartNum[n]);
				if(pvNuclei.length>pvFinalPartNum.length){
					double[] tempPartNum = new double[pvNuclei.length];
					for(int q = prsfIntZero;q < pvNuclei.length; q++) {
						if(q<pvFinalPartNum.length){
							tempPartNum[q] = pvFinalPartNum[q];
						} else {
							tempPartNum[q] = prsfIntZero;
						}
    				}
					pvFinalPartNum = tempPartNum;
    			}
				for(int q = prsfIntZero; q < pvNuclei.length; q++) {
					if(pvNuclei[q].compareTo(branch.puGetStartNucleus(n))==prsfIntZero) {
						pvFinalPartNum[q] += finalPartNum[n];
					}
				}
    		}
    	}
    	pvCleanDecayEventSets();
    }

    public void puAddSpecies (int num, String input, double start, double end) {
    	//Adds an integer (num) of nuclei of the type described in data file whose path is the String (input)
    	if (num > prsfIntZero) {
    		pvStartTime = start;
            pvEndTime = end;
    		pvNumDecayChainRuleSets++;
            DecayChainRuleSet[] newRules = new DecayChainRuleSet[pvNumDecayChainRuleSets];
            for(int x = prsfIntZero; x<pvNumDecayChainRuleSets-prsfIntOne;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumDecayChainRuleSets-prsfIntOne] = new DecayChainRuleSet(input);
    		pvRules = newRules;
    		pvPredictNumCalcParticles(num, pvRules[pvNumDecayChainRuleSets-prsfIntOne]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    public void puAddSpecies (double num, String input, double start, double end) {
    	//Adds an double (num) of nuclei of the type described in data file whose path is the String (input)
    	if (num > prsfIntZero) {
    		pvStartTime = start;
            pvEndTime = end;
    		pvNumDecayChainRuleSets++;
            DecayChainRuleSet[] newRules = new DecayChainRuleSet[pvNumDecayChainRuleSets];
            for(int x = prsfIntZero; x<pvNumDecayChainRuleSets-prsfIntOne;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumDecayChainRuleSets-prsfIntOne] = new DecayChainRuleSet(input);
    		pvRules = newRules;
    		pvPredictNumCalcParticles(num, pvRules[pvNumDecayChainRuleSets-prsfIntOne]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    public void puAddSpecies (long num, String input, double start, double end) {
    	//Adds an integer (num) of nuclei of the type described in data file whose path is the String (input)
    	if (num > prsfIntZero) {
    		pvStartTime = start;
            pvEndTime = end;
    		pvNumDecayChainRuleSets++;
            DecayChainRuleSet[] newRules = new DecayChainRuleSet[pvNumDecayChainRuleSets];
            for(int x = prsfIntZero; x<pvNumDecayChainRuleSets-prsfIntOne;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumDecayChainRuleSets-prsfIntOne] = new DecayChainRuleSet(input);
    		pvRules = newRules;
    		pvPredictNumCalcParticles(num, pvRules[pvNumDecayChainRuleSets-prsfIntOne]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    private void pvAddDecayEventSet(DecayEventSet instance) {
    	//Adds a (DecayEventSet) to this (NucleiSamplePredictiveSim)
    	DecayEventSet[] DecayEventSets = new DecayEventSet[pvNumDecayEventSets+prsfIntOne];
    	for(int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    		DecayEventSets[x]=pvDecayEventSets[x];
    	}
    	DecayEventSets[pvNumDecayEventSets] = instance;
    	pvDecayEventSets = DecayEventSets;
    	pvNumDecayEventSets++;
    }

    private void pvCleanDecayEventSets() {
    	//Cleans out the empty (DecayEventSet)s from this (NucleiSamplePredictiveSim)
    	int numCleanDecayEventSets = prsfIntZero;
    	for(int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    		if((pvDecayEventSets[x].puGetTotalNum() > prsfIntZero)&(pvDecayEventSets[x].puGetEnergy() > prsfIntZero)){
    			numCleanDecayEventSets++;
    		}
    	}
    	DecayEventSet[] DecayEventSets = new DecayEventSet[numCleanDecayEventSets];
    	numCleanDecayEventSets = prsfIntZero;
    	for(int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    		if((pvDecayEventSets[x].puGetTotalNum() > prsfIntZero)&(pvDecayEventSets[x].puGetEnergy() > prsfIntZero)){
    			DecayEventSets[numCleanDecayEventSets] = pvDecayEventSets[x];
    			numCleanDecayEventSets++;
    		}
    	}
    	pvDecayEventSets = DecayEventSets;
    	pvNumDecayEventSets = numCleanDecayEventSets;
    }

    public String puGetAllDecayEventSetData() {
    	//Returns a complete set of this (NucleiSamplePredictiveSim)'s (DecayEventSet) data
    	if (pvNumDecayEventSets > prsfIntZero) {
        	StringBuilder text = new StringBuilder();
        	text.append("StartTime,EndTime,NumParticles,StartingNucleus,EndingNucleus,Type,Energy(MeV)" + System.getProperty("line.separator"));
        	for (int x = prsfIntZero;x<pvNumDecayEventSets;x++) {
        		text.append(pvDecayEventSets[x].puGetStartTime() + "," + pvDecayEventSets[x].puGetEndTime() + "," + pvDecayEventSets[x].puGetTotalNum() + "," + pvDecayEventSets[x].puGetStartNucleus() + "," + pvDecayEventSets[x].puGetEndNucleus() + "," + pvDecayEventSets[x].puGetType() + "," + pvDecayEventSets[x].puGetEnergy() + System.getProperty("line.separator"));
        	}
        	return text.toString();
    	} else {
    		System.out.println("(puGetAllDecayEventSetData) failed because this (NucleiSamplePredictiveSim) contain no DecayEventSets");
    		return "";
    	}
    }

    public double puGetFinalNucleusCount(String nucleus){
    	//Returns the number of remaining (nucleus) at (pvEndTime)
    	for(int q = prsfIntZero; q < pvFinalPartNum.length; q++) {
			if(pvNuclei[q].compareTo(nucleus)==prsfIntZero) {
				return pvFinalPartNum[q];
			}
		}
    	System.out.println("(puGetFinalNucleusCount) failed because the requested (nucleus) never occurred in this (NucleiSamplePredictiveSim)s rules");
    	return prsfDoubleMinusOne;
    }

    public String puGetAllFinalNucleiCounts() {
    	//Returns a string containing all of the final counts for all nuclei at (pvEndTime) within this (NucleiSamplePredictiveSim)
    	StringBuilder text = new StringBuilder();
    	text.append("The final nuclei counts are: " + System.getProperty("line.separator"));
    	for(int q = prsfIntZero; q < pvNuclei.length; q++) {
			text.append("At t = " + pvEndTime + ", the number of remaining " + pvNuclei[q] + " = " + pvFinalPartNum[q] + System.getProperty("line.separator"));
		}
    	return text.toString();
    }

    public String puGetAllEventCountsOverTimeRangeByNuclei(double start, double end) {
    	//Returns a string containing all of the event counts for all nuclei over the specified time rante within this (NucleiSamplePredictiveSim)
    	StringBuilder text = new StringBuilder();
    	text.append("The final decay counts are: " + System.getProperty("line.separator"));
    	for(int q = prsfIntZero; q < pvNuclei.length; q++) {
			text.append("The number of decays of " + pvNuclei[q] + " = " + puGetEventNumForStartNucleusOverTimeRange(start,end,pvNuclei[q]) + System.getProperty("line.separator"));
		}
    	return text.toString();
    }

    public double puGetEventNumForTypeOverTimeRange(double start, double end, String type) {
    	//Returns the number of events with the supplied (type) that occurred between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEventNumForTypeOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEventNumForTypeOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEventNumForTypeOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEventNumForTypeOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				if (type.compareTo(pvDecayEventSets[x].puGetType())==prsfIntZero) {
    					sum += pvDecayEventSets[x].puGetNumWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEventNumForTypeOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEventNumForTypeOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEventNumForStartNucleusOverTimeRange(double start, double end, String startN) {
    	//Returns the number of events with the supplied (startN) that occurred between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEventNumForStartNucleusOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEventNumForStartNucleusOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEventNumForStartNucleusOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEventNumForStartNucleusOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				if (startN.compareTo(pvDecayEventSets[x].puGetStartNucleus())==prsfIntZero) {
    					sum += pvDecayEventSets[x].puGetNumWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEventNumForStartNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEventNumForStartNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEventNumForEndNucleusOverTimeRange(double start, double end, String endN) {
    	//Returns the number of events with the supplied (endN) that occurred between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEventNumForEndNucleusOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEventNumForEndNucleusOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEventNumForEndNucleusOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEventNumForEndNucleusOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				if (endN.compareTo(pvDecayEventSets[x].puGetEndNucleus())==prsfIntZero) {
    					sum += pvDecayEventSets[x].puGetNumWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEventNumForEndNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEventNumForEndNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEventNumOverTimeRange(double start, double end) {
    	//Returns the number of events that occurred between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEventNumForEndNucleusOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEventNumForEndNucleusOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEventNumForEndNucleusOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEventNumForEndNucleusOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				sum += pvDecayEventSets[x].puGetNumWithinTimeBounds(start, end);
    			}
    		} else {
    			System.out.println("(puGetEventNumForEndNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEventNumForEndNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }


    public double puGetEnergySumForTypeOverTimeRange(double start, double end, String type) {
    	//Returns the total energy in MeV for the supplied (type) that was radiated between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEnergySumForTypeOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEnergySumForTypeOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEnergySumForTypeOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEnergySumForTypeOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				if (type.compareTo(pvDecayEventSets[x].puGetType())==prsfIntZero) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEnergySumForTypeOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEnergySumForTypeOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEnergySumForStartingNucleusOverTimeRange(double start, double end, String startN) {
    	//Returns the total energy in MeV for the supplied (startN) that was radiated between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEnergySumForStartingNucleusOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEnergySumForStartingNucleusOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEnergySumForStartingNucleusOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEnergySumForStartingNucleusOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				if (startN.compareTo(pvDecayEventSets[x].puGetStartNucleus())==prsfIntZero) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEnergySumForStartingNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEnergySumForStartingNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEnergySumForEndingNucleusOverTimeRange(double start, double end, String endN) {
    	//Returns the total energy in MeV for the supplied (endN) that was radiated between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEnergySumForEndingNucleusOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEnergySumForEndingNucleusOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEnergySumForEndingNucleusOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEnergySumForEndingNucleusOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				if (endN.compareTo(pvDecayEventSets[x].puGetEndNucleus())==prsfIntZero) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEnergySumForEndingNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEnergySumForEndingNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEnergySumOverTimeRange(double start, double end) {
    	//Returns the total energy in MeV radiated between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEnergySumOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEnergySumOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEnergySumOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEnergySumOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    			}
    		} else {
    			System.out.println("(puGetEnergySumOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEnergySumOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }


    public double puGetRadiatedPowerOverTimeRange(double start, double end) {
    	//Returns the radiated power in MeV/s between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    			}
    		}  else {
    			System.out.println("(puGetRadiatedPowerOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no particles");
    			return prsfIntZero;
    		}

    	} else {
    		System.out.println("(puGetRadiatedPowerOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return prsfIntZero;
    	}
    	return (sum/(end-start));
    }

    public double puGetRadiatedPowerForTypeOverTimeRange(double start, double end, String type) {
    	//Returns the radiated power in MeV/s for the supplied (type) between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForTypeOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForTypeOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForTypeOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForTypeOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				if (type.compareTo(pvDecayEventSets[x].puGetType())==prsfIntZero) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		}  else {
    			System.out.println("(puGetRadiatedPowerForTypeOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no particles");
    			return prsfIntZero;
    		}

    	} else {
    		System.out.println("(puGetRadiatedPowerForTypeOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return prsfIntZero;
    	}
    	return (sum/(end-start));
    }

    public double puGetRadiatedPowerForStartNucleusOverTimeRange(double start, double end, String startN) {
    	//Returns the radiated power in MeV/s for the supplied (startN) between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForStartNucleusOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForStartNucleusOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForStartNucleusOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForStartNucleusOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				if (startN.compareTo(pvDecayEventSets[x].puGetStartNucleus())==prsfIntZero) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		}  else {
    			System.out.println("(puGetRadiatedPowerForStartNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no particles");
    			return prsfIntZero;
    		}

    	} else {
    		System.out.println("(puGetRadiatedPowerForStartNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return prsfIntZero;
    	}
    	return (sum/(end-start));
    }

    public double puGetRadiatedPowerForEndNucleusOverTimeRange(double start, double end, String endN) {
    	//Returns the radiated power in MeV/s for the supplied (endN) between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForEndNucleusOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForEndNucleusOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForEndNucleusOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForEndNucleusOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				if (endN.compareTo(pvDecayEventSets[x].puGetEndNucleus())==prsfIntZero) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		}  else {
    			System.out.println("(puGetRadiatedPowerForEndNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no particles");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetRadiatedPowerForEndNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return prsfIntZero;
    	}
    	return (sum/(end-start));
    }

    public String puGetRadiatedPowerForOneYear(double start) {
    	//Returns a comma delimited String with a years worth of radiated power MeV/s (one per hour) calculations starting at time (start)
    	double[] aveBucket = new double[prsfInt8760];
    	for (int x = prsfIntZero; x<prsfInt8760; x++) {
    		aveBucket[x] = prsfIntZero;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYear) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYear) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetRadiatedPowerForOneYear) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= prsfIntZero) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				for (int y = prsfIntZero; y < prsfInt8760; y++) {
    					aveBucket[y] = aveBucket[y] + pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start+prsfDouble3600*y,start+prsfDouble3600*(y+prsfIntOne));
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEventSet)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = prsfIntZero;x<prsfInt8760;x++) {
    				aveBucket[x] = aveBucket[x]/prsfDouble3600;
    				text.append((start+prsfDouble3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
    			}
    			return text.toString();
    		}  else {
    			System.out.println("(puGetRadiatedPowerForOneYear) failed because this (NucleiSamplePredictiveSim) contains no (DecayEventSet)s");
    			return "(puGetRadiatedPowerForOneYear) failed because this (NucleiSamplePredictiveSim) contains no (DecayEventSet)s";
    		}
    	} else {
    		System.out.println ("(puGetRadiatedPowerForOneYear) failed because the supplied start time is less than zero");
    		return "(puGetRadiatedPowerForOneYear) failed because the supplied start time is less than zero";
    	}
    }

    public String puGetRadiatedPowerForOneYearForType(double start, String type) {
    	//Returns a comma delimited String with a years worth of radiated power MeV/s (one per hour) calculations starting at time (start) of only the specified (type)
    	double[] aveBucket = new double[prsfInt8760];
    	for (int x = prsfIntZero; x<prsfInt8760; x++) {
    		aveBucket[x] = prsfIntZero;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForType) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForType) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetRadiatedPowerForOneYearForType) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= prsfIntZero) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				for (int y = prsfIntZero; y < prsfInt8760; y++) {
    					if (type.compareTo(pvDecayEventSets[x].puGetType())==prsfIntZero) {
    						aveBucket[y] = aveBucket[y] + pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start+prsfDouble3600*y,start+prsfDouble3600*(y+prsfIntOne));
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEventSet)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = prsfIntZero;x<prsfInt8760;x++) {
    				aveBucket[x] = aveBucket[x]/prsfDouble3600;
    				text.append((start+prsfDouble3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
    			}
    			return text.toString();
    		}  else {
    			System.out.println("(puGetRadiatedPowerForOneYearForType) failed because this (NucleiSamplePredictiveSim) contains no (DecayEventSet)s");
    			return "(puGetRadiatedPowerForOneYearForType) failed because this (NucleiSamplePredictiveSim) contains no (DecayEventSet)s";
    		}
    	} else {
    		System.out.println ("(puGetRadiatedPowerForOneYearForType) failed because the supplied start time is less than zero");
    		return "(puGetRadiatedPowerForOneYearForType) failed because the supplied start time is less than zero";
    	}
    }

    public String puGetRadiatedPowerForOneYearForStartNucleus(double start, String startN) {
    	//Returns a comma delimited String with a years worth of radiated power MeV/s (one per hour) calculations starting at time (start) of only the specified (startN)
    	double[] aveBucket = new double[prsfInt8760];
    	for (int x = prsfIntZero; x<prsfInt8760; x++) {
    		aveBucket[x] = prsfIntZero;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForStartNucleus) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForStartNucleus) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetRadiatedPowerForOneYearForStartNucleus) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= prsfIntZero) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				for (int y = prsfIntZero; y < prsfInt8760; y++) {
    					if (startN.compareTo(pvDecayEventSets[x].puGetStartNucleus())==prsfIntZero) {
    						aveBucket[y] = aveBucket[y] + pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start+prsfDouble3600*y,start+prsfDouble3600*(y+prsfIntOne));
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEventSet)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = prsfIntZero;x<prsfInt8760;x++) {
    				aveBucket[x] = aveBucket[x]/prsfDouble3600;
    				text.append((start+prsfDouble3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
    			}
    			return text.toString();
    		}  else {
    			System.out.println("(puGetRadiatedPowerForOneYearForStartNucleus) failed because this (NucleiSamplePredictiveSim) contains no (DecayEventSet)s");
    			return "(puGetRadiatedPowerForOneYearForStartNucleus) failed because this (NucleiSamplePredictiveSim) contains no (DecayEventSet)s";
    		}
    	} else {
    		System.out.println ("(puGetRadiatedPowerForOneYearForStartNucleus) failed because the supplied start time is less than zero");
    		return "(puGetRadiatedPowerForOneYearForStartNucleus) failed because the supplied start time is less than zero";
    	}
    }

    public String puGetRadiatedPowerForOneYearForEndNucleus(double start, String endN) {
    	//Returns a comma delimited String with a years worth of radiated power MeV/s (one per hour) calculations starting at time (start) of only the specified (endN)
    	double[] aveBucket = new double[prsfInt8760];
    	for (int x = prsfIntZero; x<prsfInt8760; x++) {
    		aveBucket[x] = prsfIntZero;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForEndNucleus) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForEndNucleus) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetRadiatedPowerForOneYearForEndNucleus) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= prsfIntZero) {
    		if (pvNumDecayEventSets > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEventSets; x++) {
    				for (int y = prsfIntZero; y < prsfInt8760; y++) {
    					if (endN.compareTo(pvDecayEventSets[x].puGetEndNucleus())==prsfIntZero) {
    						aveBucket[y] = aveBucket[y] + pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start+prsfDouble3600*y,start+prsfDouble3600*(y+prsfIntOne));
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEventSet)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = prsfIntZero;x<prsfInt8760;x++) {
    				aveBucket[x] = aveBucket[x]/prsfDouble3600;
    				text.append((start+prsfDouble3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
    			}
    			return text.toString();
    		}  else {
    			System.out.println("(puGetRadiatedPowerForOneYearForEndNucleus) failed because this (NucleiSamplePredictiveSim) contains no (DecayEventSet)s");
    			return "(puGetRadiatedPowerForOneYearForEndNucleus) failed because this (NucleiSamplePredictiveSim) contains no (DecayEventSet)s";
    		}
    	} else {
    		System.out.println ("(puGetRadiatedPowerForOneYearForEndNucleus) failed because the supplied start time is less than zero");
    		return "(puGetRadiatedPowerForOneYearForEndNucleus) failed because the supplied start time is less than zero";
    	}
    }

}
