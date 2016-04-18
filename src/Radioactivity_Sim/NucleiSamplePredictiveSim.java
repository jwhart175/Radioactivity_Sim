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

public class NucleiSamplePredictiveSim {
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

    private DecayEventSet[] pvDecayEventSets;
    private DecayChainRuleSet[] pvRules;
    private int pvNumDecayChainRuleSets = 0;
    private int pvNumDecayEventSets = 0;
    private double pvStartTime = 0;
    private double pvEndTime = 0;
    private int pvResolution = 0;
    private double[] pvFinalPartNum = new double[1];
    private String[] pvNuclei = new String[1];
    private double pvNucleiStarter = 0.0;

    public NucleiSamplePredictiveSim(int num,String input, double start, double end, int resolution) {
    	//Constructor for a sample containing a number of nuclei of a single type
    	pvNucleiStarter = Math.random();
        pvNuclei[0] = Double.toString(pvNucleiStarter);
    	if (num > 0) {
            pvRules = new DecayChainRuleSet[1];
            pvRules[0] = new DecayChainRuleSet(input);
            pvNumDecayChainRuleSets = 1;
            pvStartTime = start;
            pvEndTime = end;
            pvResolution = resolution;
            pvPredictNumCalcParticles(num,pvRules[0]);
        } else {
            System.out.println("Construction of this (NucleiSamplePredictiveSim) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSamplePredictiveSim(double num,String input, double start, double end, int resolution) {
    	//Constructor for a sample containing a number of nuclei of a single type
    	pvNucleiStarter = Math.random();
        pvNuclei[0] = Double.toString(pvNucleiStarter);
        if (num > 0) {
            pvRules = new DecayChainRuleSet[1];
            pvRules[0] = new DecayChainRuleSet(input);
            pvNumDecayChainRuleSets = 1;
            pvStartTime = start;
            pvEndTime = end;
            pvResolution = resolution;
            pvPredictNumCalcParticles(num, pvRules[0]);
        } else {
            System.out.println("Construction of this (NucleiSamplePredictiveSim) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSamplePredictiveSim() {
    	//Constructor for a sample to be user-defined with the (puAddSpecies) function
    	pvNucleiStarter = Math.random();
        pvNuclei[0] = Double.toString(pvNucleiStarter);
    }

    public int puGetNumDecayChainRuleSets() {
    	//Returns the total number of (DecayChainRuleSet) objects
    	return pvNumDecayChainRuleSets;
    }

    public DecayChainRuleSet puGetDecayChainRuleSet(int index) {
    	//Returns the (DecayChainRuleSet) at the specified (index)
    	if(index < 0) {
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
    	double dividend = 1, subproduct = 1, subsum = 0, subsum2 = 0;
    	int numBranches = rules.puGetNumBranches();
    	double[] eventNum = new double[1];
    	double[] finalPartNum = new double[1];
    	double[][] startNumTime = new double[1][1];
    	double delta = (pvEndTime-pvStartTime)/pvResolution;
    	String[] nuclei = new String[1];
    	DecayRuleBranch branch = new DecayRuleBranch();
    	for (int x = 0; x<numBranches ;x++) {
    		branch = rules.puGetDecayRuleBranch(x);
    		int numRules = branch.puGetNumRules();
    		eventNum = new double[numRules];
    		finalPartNum = new double[numRules];
    		startNumTime = new double[pvResolution][numRules];
    		for (int n = 0; n < numRules; n++) {
    			boolean newNucleus = true;
    			for(int q = 0;q < pvNuclei.length; q++) {
    				if(pvNuclei.length ==1){
    					if(Double.toString(pvNucleiStarter).compareTo(pvNuclei[0])==0){
    						newNucleus = false;
    						pvNuclei[0] = branch.puGetStartNucleus(n);
    						break;
    					}
    				}
    				if(pvNuclei[q].compareTo(branch.puGetStartNucleus(n))==0){
    					newNucleus = false;
    				}
    			}
    			if(newNucleus){
    				nuclei = new String[pvNuclei.length+1];
    				for(int q = 0;q < pvNuclei.length; q++) {
        				nuclei[q] = pvNuclei[q];
        			}
    				nuclei[pvNuclei.length] = branch.puGetStartNucleus(n);
    				pvNuclei = nuclei;
    			}

    			subsum2 = 0;
    			eventNum[n] = 0;
    			if(n == 0) {
    			//Calculate particle numbers at pvStartTime using the normal Bateman Solution
    				for (int w = 0; w<pvResolution;w++) {
        				double newTime = w*delta + pvStartTime;
        				for (int i = 0; i <= n; i++){
        					subsum = 0;
        					subproduct = 1;
        					for (int j = i; j <= n; j++){
        						dividend = 1;
        						for (int p = i; p <= n; p++){
        							if (p != j) {
        								dividend = dividend * ((Math.log(2)/branch.puGetHalfLife(p)-Math.log(2)/branch.puGetHalfLife(j)));
        							}
        						}
        						subsum = subsum + (Math.exp(-newTime*Math.log(2)/branch.puGetHalfLife(j)))/dividend;
        						if (j <= (n-1)) {
        							subproduct = subproduct * Math.log(2)/branch.puGetHalfLife(j);
        						}
        						subproduct = subproduct * branch.puGetProbability(0);
        					}
        					if (i==0) {
        						subsum2 = num*subsum*subproduct;
        					} else {
        						subsum2 = subsum2 + 0*subsum*subproduct;
        					}
        				}
        				startNumTime[w][n] = subsum2;
    				}
    				//Calculates the number of DecayEvents for n = 0 between pvStartTime and pvEndTime
    				eventNum[n] = startNumTime[0][0]*(Math.exp(-pvStartTime*Math.log(2)/branch.puGetHalfLife(n))-Math.exp(-pvEndTime*Math.log(2)/branch.puGetHalfLife(n)));
    				//Store the predicted events in a (DecayEventSet)
					if(eventNum[n]>0){
    					DecayEventSet instance = new DecayEventSet(eventNum[n],false,pvStartTime,pvEndTime,branch.puGetStartNucleus(n),branch.puGetEndNucleus(n),branch.puGetHalfLife(n),branch.puGetEnergy(n),branch.puGetType(n));
    					pvAddDecayEventSet(instance);
					}

    			} else if (n>0) {
    				//Calculates DecayEvents for the child nuclei

    				for (int w = 0; w<pvResolution;w++) {
    					double newTime = w*delta + pvStartTime;
    					for (int i = 0; i <= n; i++){
    						subsum = 0;
    						subproduct = 1;
    						for (int j = i; j <= n; j++){
    							dividend = 1;
    							for (int p = i; p <= n; p++){
    								if (p != j) {
    									dividend = dividend * ((Math.log(2)/branch.puGetHalfLife(p)-Math.log(2)/branch.puGetHalfLife(j)));
    								}
    							}
    							subsum = subsum + (Math.exp(-newTime*Math.log(2)/branch.puGetHalfLife(j)))/dividend;
    							if (j <= (n-1)) {
    								subproduct = subproduct * Math.log(2)/branch.puGetHalfLife(j);
    							}
    							subproduct = subproduct * branch.puGetProbability(0);
    						}
    						if (i==0) {
    							subsum2 = num*subsum*subproduct;
    						} else {
    							subsum2 = subsum2 + 0*subsum*subproduct;
    						}
    					}
    					startNumTime[w][n] = subsum2;
       				}
    				double subsum3 = 0;
    				subsum2 = 0;
        			for (int i = n-1; i >= 0; i--){

        				subsum = 0;
        				for (int j = 0; j < pvResolution; j++){
        					subproduct = 1;
        					for (int k = n; k >= i; k--){
        						subproduct = subproduct * (Math.exp(-(pvStartTime+j*delta)*Math.log(2.0)/branch.puGetHalfLife(k))-Math.exp(-(pvStartTime+(j+1.0)*delta)*Math.log(2.0)/branch.puGetHalfLife(k)));
        	        		}
        					subsum3 = startNumTime[j][i]*subproduct*Math.pow((Math.exp(-(pvStartTime)*Math.log(2.0)/branch.puGetHalfLife(n))-Math.exp(-(pvEndTime)*Math.log(2.0)/branch.puGetHalfLife(n))),1.5)*pvResolution;
        					subsum += subsum3;
        					//Store the predicted events in a (DecayEventSet)
        					if(subsum3>0){
        						DecayEventSet instance = new DecayEventSet(subsum3,true,(pvStartTime+j*delta),(pvStartTime+(j+1)*delta),branch.puGetStartNucleus(n),branch.puGetEndNucleus(n),branch.puGetHalfLife(n),branch.puGetEnergy(n),branch.puGetType(n));
        						pvAddDecayEventSet(instance);
        					}
        				}
        				subsum2 = subsum2 + subsum;
        			}
        			eventNum[n] = subsum2;
    			}

    			//Calculates final quantities for all particles
    			for (int i = 0; i <= n; i++){
					subsum = 0;
					subproduct = 1;
					for (int j = i; j <= n; j++){
						dividend = 1;
						for (int p = i; p <= n; p++){
							if (p != j) {
								dividend = dividend * ((Math.log(2)/branch.puGetHalfLife(p)-Math.log(2)/branch.puGetHalfLife(j)));
							}
						}
						subsum = subsum + (Math.exp(-pvEndTime*Math.log(2)/branch.puGetHalfLife(j)))/dividend;
						if (j <= (n-1)) {
							subproduct = subproduct * Math.log(2)/branch.puGetHalfLife(j);
						}
						subproduct = subproduct * branch.puGetProbability(0);
					}
					if (i==0) {
						subsum2 = num*subsum*subproduct;
					} else {
						subsum2 = subsum2 + 0*subsum*subproduct;
					}
				}
				finalPartNum[n] = subsum2;
				//debug line to verify bateman calcs
				//System.out.println("Branch No. = " + x + ", Start nucleus = " + branch.puGetStartNucleus(n) + ", Final count = " + finalPartNum[n]);
				if(pvNuclei.length>pvFinalPartNum.length){
					double[] tempPartNum = new double[pvNuclei.length];
					for(int q = 0;q < pvNuclei.length; q++) {
						if(q<pvFinalPartNum.length){
							tempPartNum[q] = pvFinalPartNum[q];
						} else {
							tempPartNum[q] = 0;
						}
    				}
					pvFinalPartNum = tempPartNum;
    			}
				for(int q = 0; q < pvNuclei.length; q++) {
					if(pvNuclei[q].compareTo(branch.puGetStartNucleus(n))==0) {
						pvFinalPartNum[q] += finalPartNum[n];
					}
				}
    		}
    	}
    	pvCleanDecayEventSets();
    }

    public void puAddSpecies (int num, String input, double start, double end) {
    	//Adds an integer (num) of nuclei of the type described in data file whose path is the String (input)
    	if (num > 0) {
    		pvStartTime = start;
            pvEndTime = end;
    		pvNumDecayChainRuleSets += 1;
            DecayChainRuleSet[] newRules = new DecayChainRuleSet[pvNumDecayChainRuleSets];
            for(int x = 0; x<pvNumDecayChainRuleSets-1;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumDecayChainRuleSets-1] = new DecayChainRuleSet(input);
    		pvRules = newRules;
    		pvPredictNumCalcParticles(num, pvRules[pvNumDecayChainRuleSets-1]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    public void puAddSpecies (double num, String input, double start, double end) {
    	//Adds an double (num) of nuclei of the type described in data file whose path is the String (input)
    	if (num > 0) {
    		pvStartTime = start;
            pvEndTime = end;
    		pvNumDecayChainRuleSets += 1;
            DecayChainRuleSet[] newRules = new DecayChainRuleSet[pvNumDecayChainRuleSets];
            for(int x = 0; x<pvNumDecayChainRuleSets-1;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumDecayChainRuleSets-1] = new DecayChainRuleSet(input);
    		pvRules = newRules;
    		pvPredictNumCalcParticles(num, pvRules[pvNumDecayChainRuleSets-1]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    public void puAddSpecies (long num, String input, double start, double end) {
    	//Adds an integer (num) of nuclei of the type described in data file whose path is the String (input)
    	if (num > 0) {
    		pvStartTime = start;
            pvEndTime = end;
    		pvNumDecayChainRuleSets += 1;
            DecayChainRuleSet[] newRules = new DecayChainRuleSet[pvNumDecayChainRuleSets];
            for(int x = 0; x<pvNumDecayChainRuleSets-1;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumDecayChainRuleSets-1] = new DecayChainRuleSet(input);
    		pvRules = newRules;
    		pvPredictNumCalcParticles(num, pvRules[pvNumDecayChainRuleSets-1]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    private void pvAddDecayEventSet(DecayEventSet instance) {
    	//Adds a (DecayEventSet) to this (NucleiSamplePredictiveSim)
    	DecayEventSet[] DecayEventSets = new DecayEventSet[pvNumDecayEventSets+1];
    	for(int x = 0; x < pvNumDecayEventSets; x++) {
    		DecayEventSets[x]=pvDecayEventSets[x];
    	}
    	DecayEventSets[pvNumDecayEventSets] = instance;
    	pvDecayEventSets = DecayEventSets;
    	pvNumDecayEventSets++;
    }

    private void pvCleanDecayEventSets() {
    	//Cleans out the empty (DecayEventSet)s from this (NucleiSamplePredictiveSim)
    	int numCleanDecayEventSets = 0;
    	for(int x = 0; x < pvNumDecayEventSets; x++) {
    		if((pvDecayEventSets[x].puGetTotalNum() > 0)&(pvDecayEventSets[x].puGetEnergy() > 0)){
    			numCleanDecayEventSets++;
    		}
    	}
    	DecayEventSet[] DecayEventSets = new DecayEventSet[numCleanDecayEventSets];
    	numCleanDecayEventSets = 0;
    	for(int x = 0; x < pvNumDecayEventSets; x++) {
    		if((pvDecayEventSets[x].puGetTotalNum() > 0)&(pvDecayEventSets[x].puGetEnergy() > 0)){
    			DecayEventSets[numCleanDecayEventSets] = pvDecayEventSets[x];
    			numCleanDecayEventSets++;
    		}
    	}
    	pvDecayEventSets = DecayEventSets;
    	pvNumDecayEventSets = numCleanDecayEventSets;
    }

    public String puGetAllDecayEventSetData() {
    	//Returns a complete set of this (NucleiSamplePredictiveSim)'s (DecayEventSet) data
    	if (pvNumDecayEventSets > 0) {
        	StringBuilder text = new StringBuilder();
        	text.append("StartTime,EndTime,NumParticles,StartingNucleus,EndingNucleus,Type,Energy(MeV)" + System.getProperty("line.separator"));
        	for (int x = 0;x<pvNumDecayEventSets;x++) {
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
    	for(int q = 0; q < pvFinalPartNum.length; q++) {
			if(pvNuclei[q].compareTo(nucleus)==0) {
				return pvFinalPartNum[q];
			}
		}
    	System.out.println("(puGetFinalNucleusCount) failed because the requested (nucleus) never occurred in this (NucleiSamplePredictiveSim)s rules");
    	return -1;
    }

    public String puGetAllFinalNucleiCounts() {
    	//Returns a string containing all of the final counts for all nuclei at (pvEndTime) within this (NucleiSamplePredictiveSim)
    	StringBuilder text = new StringBuilder();
    	text.append("The final nuclei counts are: " + System.getProperty("line.separator"));
    	for(int q = 0; q < pvNuclei.length; q++) {
			text.append("At t = " + pvEndTime + ", the number of remaining " + pvNuclei[q] + " = " + pvFinalPartNum[q] + System.getProperty("line.separator"));
		}
    	return text.toString();
    }

    public String puGetAllEventCountsOverTimeRangeByNuclei(double start, double end) {
    	//Returns a string containing all of the event counts for all nuclei over the specified time rante within this (NucleiSamplePredictiveSim)
    	StringBuilder text = new StringBuilder();
    	text.append("The final decay counts are: " + System.getProperty("line.separator"));
    	for(int q = 0; q < pvNuclei.length; q++) {
			text.append("The number of decays of " + pvNuclei[q] + " = " + puGetEventNumForStartNucleusOverTimeRange(start,end,pvNuclei[q]) + System.getProperty("line.separator"));
		}
    	return text.toString();
    }

    public double puGetEventNumForTypeOverTimeRange(double start, double end, String type) {
    	//Returns the number of events with the supplied (type) that occurred between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				if (type.compareTo(pvDecayEventSets[x].puGetType())==0) {
    					sum += pvDecayEventSets[x].puGetNumWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEventNumForTypeOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return 0;
    		}
    	} else {
    		System.out.println("(puGetEventNumForTypeOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEventNumForStartNucleusOverTimeRange(double start, double end, String startN) {
    	//Returns the number of events with the supplied (startN) that occurred between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				if (startN.compareTo(pvDecayEventSets[x].puGetStartNucleus())==0) {
    					sum += pvDecayEventSets[x].puGetNumWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEventNumForStartNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return 0;
    		}
    	} else {
    		System.out.println("(puGetEventNumForStartNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEventNumForEndNucleusOverTimeRange(double start, double end, String endN) {
    	//Returns the number of events with the supplied (endN) that occurred between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				if (endN.compareTo(pvDecayEventSets[x].puGetEndNucleus())==0) {
    					sum += pvDecayEventSets[x].puGetNumWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEventNumForEndNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return 0;
    		}
    	} else {
    		System.out.println("(puGetEventNumForEndNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEventNumOverTimeRange(double start, double end) {
    	//Returns the number of events that occurred between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				sum += pvDecayEventSets[x].puGetNumWithinTimeBounds(start, end);

    			}
    		} else {
    			System.out.println("(puGetEventNumForEndNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return 0;
    		}
    	} else {
    		System.out.println("(puGetEventNumForEndNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }


    public double puGetEnergySumForTypeOverTimeRange(double start, double end, String type) {
    	//Returns the total energy in MeV for the supplied (type) that was radiated between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				if (type.compareTo(pvDecayEventSets[x].puGetType())==0) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEnergySumForTypeOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return 0;
    		}
    	} else {
    		System.out.println("(puGetEnergySumForTypeOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEnergySumForStartingNucleusOverTimeRange(double start, double end, String startN) {
    	//Returns the total energy in MeV for the supplied (startN) that was radiated between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				if (startN.compareTo(pvDecayEventSets[x].puGetStartNucleus())==0) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEnergySumForStartingNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return 0;
    		}
    	} else {
    		System.out.println("(puGetEnergySumForStartingNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEnergySumForEndingNucleusOverTimeRange(double start, double end, String endN) {
    	//Returns the total energy in MeV for the supplied (endN) that was radiated between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				if (endN.compareTo(pvDecayEventSets[x].puGetEndNucleus())==0) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		} else {
    			System.out.println("(puGetEnergySumForEndingNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return 0;
    		}
    	} else {
    		System.out.println("(puGetEnergySumForEndingNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEnergySumOverTimeRange(double start, double end) {
    	//Returns the total energy in MeV radiated between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    			}
    		} else {
    			System.out.println("(puGetEnergySumOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no (DecayEventSet)s");
    			return 0;
    		}
    	} else {
    		System.out.println("(puGetEnergySumOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }


    public double puGetRadiatedPowerOverTimeRange(double start, double end) {
    	//Returns the radiated power in MeV/s between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    			}
    		}  else {
    			System.out.println("(puGetRadiatedPowerOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no particles");
    			return 0;
    		}

    	} else {
    		System.out.println("(puGetRadiatedPowerOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return 0;
    	}
    	return (sum/(end-start));
    }

    public double puGetRadiatedPowerForTypeOverTimeRange(double start, double end, String type) {
    	//Returns the radiated power in MeV/s for the supplied (type) between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				if (type.compareTo(pvDecayEventSets[x].puGetType())==0) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		}  else {
    			System.out.println("(puGetRadiatedPowerForTypeOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no particles");
    			return 0;
    		}

    	} else {
    		System.out.println("(puGetRadiatedPowerForTypeOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return 0;
    	}
    	return (sum/(end-start));
    }

    public double puGetRadiatedPowerForStartNucleusOverTimeRange(double start, double end, String startN) {
    	//Returns the radiated power in MeV/s for the supplied (startN) between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				if (startN.compareTo(pvDecayEventSets[x].puGetStartNucleus())==0) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		}  else {
    			System.out.println("(puGetRadiatedPowerForStartNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no particles");
    			return 0;
    		}

    	} else {
    		System.out.println("(puGetRadiatedPowerForStartNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return 0;
    	}
    	return (sum/(end-start));
    }

    public double puGetRadiatedPowerForEndNucleusOverTimeRange(double start, double end, String endN) {
    	//Returns the radiated power in MeV/s for the supplied (endN) between the user supplied (start) and (end) times
    	double sum = 0;
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
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				if (endN.compareTo(pvDecayEventSets[x].puGetEndNucleus())==0) {
    					sum += pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start, end);
    				}
    			}
    		}  else {
    			System.out.println("(puGetRadiatedPowerForEndNucleusOverTimeRange) failed because this (NucleiSamplePredictiveSim) contain no particles");
    			return 0;
    		}

    	} else {
    		System.out.println("(puGetRadiatedPowerForEndNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return 0;
    	}
    	return (sum/(end-start));
    }

    public String puGetRadiatedPowerForOneYear(double start) {
    	//Returns a comma delimited String with a years worth of radiated power MeV/s (one per hour) calculations starting at time (start)
    	double[] aveBucket = new double[365*24];
    	for (int x = 0; x<365*24; x++) {
    		aveBucket[x] = 0;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYear) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYear) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetRadiatedPowerForOneYear) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= 0) {
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				for (int y = 0; y < 365*24; y++) {
    					aveBucket[y] = aveBucket[y] + pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start+3600*y,start+3600*(y+1));
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEventSet)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = 0;x<365*24;x++) {
    				aveBucket[x] = aveBucket[x]/3600;
    				text.append((start+3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
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
    	double[] aveBucket = new double[365*24];
    	for (int x = 0; x<365*24; x++) {
    		aveBucket[x] = 0;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForType) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForType) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetRadiatedPowerForOneYearForType) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= 0) {
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				for (int y = 0; y < 365*24; y++) {
    					if (type.compareTo(pvDecayEventSets[x].puGetType())==0) {
    						aveBucket[y] = aveBucket[y] + pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start+3600*y,start+3600*(y+1));
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEventSet)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = 0;x<365*24;x++) {
    				aveBucket[x] = aveBucket[x]/3600;
    				text.append((start+3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
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
    	double[] aveBucket = new double[365*24];
    	for (int x = 0; x<365*24; x++) {
    		aveBucket[x] = 0;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForStartNucleus) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForStartNucleus) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetRadiatedPowerForOneYearForStartNucleus) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= 0) {
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				for (int y = 0; y < 365*24; y++) {
    					if (startN.compareTo(pvDecayEventSets[x].puGetStartNucleus())==0) {
    						aveBucket[y] = aveBucket[y] + pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start+3600*y,start+3600*(y+1));
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEventSet)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = 0;x<365*24;x++) {
    				aveBucket[x] = aveBucket[x]/3600;
    				text.append((start+3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
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
    	double[] aveBucket = new double[365*24];
    	for (int x = 0; x<365*24; x++) {
    		aveBucket[x] = 0;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForEndNucleus) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForEndNucleus) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetRadiatedPowerForOneYearForEndNucleus) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= 0) {
    		if (pvNumDecayEventSets > 0) {
    			for (int x = 0; x < pvNumDecayEventSets; x++) {
    				for (int y = 0; y < 365*24; y++) {
    					if (endN.compareTo(pvDecayEventSets[x].puGetEndNucleus())==0) {
    						aveBucket[y] = aveBucket[y] + pvDecayEventSets[x].puGetEnergyWithinTimeBounds(start+3600*y,start+3600*(y+1));
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEventSet)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = 0;x<365*24;x++) {
    				aveBucket[x] = aveBucket[x]/3600;
    				text.append((start+3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
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
