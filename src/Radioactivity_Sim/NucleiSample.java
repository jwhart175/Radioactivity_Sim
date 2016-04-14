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

public class NucleiSample {
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

    private DecayEvent[] pvDecayEvents;
    private DecayChainRuleSet[] pvRules;
    private int pvNumDecayChainRuleSets = 0;
    private int pvNumDecayEvents = 0;
    private double pvStartTime = 0;
    private double pvEndTime = 0;
    private int pvResolution = 0;
    private double[] pvFinalPartNum = new double[1];
    private String[] pvNuclei = new String[1];

    public NucleiSample(int num,String input, double start, double end, int resolution) {
    	//Constructor for a sample containing a number of nuclei of a single type
        if (num > 0) {
            pvRules = new DecayChainRuleSet[1];
            pvRules[0] = new DecayChainRuleSet(input);
            pvNumDecayChainRuleSets = 1;
            pvStartTime = start;
            pvEndTime = end;
            pvResolution = resolution;
            pvPredictNumCalcParticles(num,pvRules[0]);
        } else {
            System.out.println("Construction of this (NucleiSample) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSample(double num,String input, double start, double end, int resolution) {
    	//Constructor for a sample containing a number of nuclei of a single type
        if (num > 0) {
            pvRules = new DecayChainRuleSet[1];
            pvRules[0] = new DecayChainRuleSet(input);
            pvNumDecayChainRuleSets = 1;
            pvStartTime = start;
            pvEndTime = end;
            pvResolution = resolution;
            pvPredictNumCalcParticles(num, pvRules[0]);
        } else {
            System.out.println("Construction of this (NucleiSample) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSample() {
    	//Constructor for a sample to be user-defined with the (puAddSpecies) function
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
    		String errString = "(puGetDecayChainRuleSet) failed because the supplied (index) is greater than the total of number of (DecayChainRuleSet) objects in this (NucleiSample)!";
    		System.out.println(errString);
    		return new DecayChainRuleSet();
    	} else {
    		return pvRules[index];
    	}
    }

    private void pvPredictNumCalcParticles(double num, DecayChainRuleSet rules) {
    	double dividend = 1, subproduct = 1, subsum = 0, subsum2 = 0;
    	int numBranches = rules.puGetNumBranches();
    	double[] endPartNum = new double[1];
    	double[] finalPartNum = new double[1];
    	double[][] startNumTime = new double[1][1];
    	double delta = (pvEndTime-pvStartTime)/pvResolution;
    	DecayRuleBranch branch = new DecayRuleBranch();
    	for (int x = 0; x<numBranches ;x++) {
    		branch = rules.puGetDecayRuleBranch(x);
    		int numRules = branch.puGetNumRules();
    		endPartNum = new double[numRules];
    		finalPartNum = new double[numRules];
    		startNumTime = new double[pvResolution][numRules];
    		for (int n = 0; n < numRules; n++) {
    			subsum2 = 0;
    			endPartNum[n] = 0;
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
    				endPartNum[n] = startNumTime[0][0]*(Math.exp(-pvStartTime*Math.log(2)/branch.puGetHalfLife(n))-Math.exp(-pvEndTime*Math.log(2)/branch.puGetHalfLife(n)));

    				//Calculate DecayEvent times for DecayEvents occurring between pvStartTime and pvEndTime
					for (long g = 0; g<(endPartNum[n]); g++) {
						DecayEvent instance = new DecayEvent(pvStartTime,pvEndTime,branch.puGetStartNucleus(n),branch.puGetEndNucleus(n),branch.puGetHalfLife(n),branch.puGetEnergy(n),branch.puGetType(n));
						pvAddDecayEvent(instance);
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

    				subsum2 = 0;
        			for (int i = n-1; i >= 0; i--){
        				subsum = 0;

        				for (int j = 0; j < pvResolution; j++){
        					subproduct = 1;
        					for (int k = n; k >= i; k--){
        						subproduct = subproduct * (Math.exp(-(pvStartTime+j*delta)*Math.log(2)/branch.puGetHalfLife(k))-Math.exp(-(pvStartTime+(j+1)*delta)*Math.log(2)/branch.puGetHalfLife(k)));
        	        					}
        					subsum += startNumTime[j][i]*subproduct;
        				}
        				subsum2 = subsum2 + subsum;
        			}
        			endPartNum[n] = subsum2;

        			//Calculate DecayEvent times for DecayEvents occurring between pvStartTime and pvEndTime
					for (long g = 0; g<(endPartNum[n]); g++) {
						DecayEvent instance = new DecayEvent(true,pvStartTime,pvEndTime,branch.puGetStartNucleus(n),branch.puGetEndNucleus(n),branch.puGetHalfLife(n),branch.puGetEnergy(n),branch.puGetType(n));
						pvAddDecayEvent(instance);
					}
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
				System.out.println("Branch No. = " + x + ", Start nucleus = " + branch.puGetStartNucleus(n) + ", Final count = " + finalPartNum[n]);
    		}
    	}
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

    private void pvDecayEventCalc(int num, DecayChainRuleSet rules, String startN) {
    	//Function that calculates an integer (num) DecayEvents based on the DecayChainRuleSet (rules)
    	int numRules = rules.puGetNumRules();
    	//Calculate Number of DecayEvents
        String startNucleus = startN;
    	double timeOffset = 0;
    	for(int x = 0; x<num; x++) {
    		timeOffset = 0;
    		startNucleus = startN;
    		for(int y = 0; y<numRules; y++) {
    			if (rules.puGetStartNucleus(y).compareTo(startNucleus)==0) {
    				if (rules.puGetProbability(y) == 1) {
    					startNucleus = rules.puGetEndNucleus(y);
    					DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    						pvAddDecayEvent(instance);
    					}
    					timeOffset += instance.puGetTime();
    				} else {
    					double seed = Math.random();
    					if ((rules.puGetProbability(y) + rules.puGetProbability(y+1)) == 1) {
    						if (seed <= rules.puGetProbability(y)){
    							startNucleus = rules.puGetEndNucleus(y);
    							DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y++;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+1);
    							DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+1),rules.puGetEnergy(y+1),rules.puGetType(y+1),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y++;
    						}

    					} else if ((rules.puGetProbability(y) + rules.puGetProbability(y+1) + rules.puGetProbability(y+2)) == 1) {
    						if (seed <= rules.puGetProbability(y)) {
    							startNucleus = rules.puGetEndNucleus(y);
    							DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y += 2;
    						} else if (seed <= (rules.puGetProbability(y) + rules.puGetProbability(y+1))) {
    							startNucleus = rules.puGetEndNucleus(y+1);
    							DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+1),rules.puGetEnergy(y+1),rules.puGetType(y+1),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += 2;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+2);
    							DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+2),rules.puGetEnergy(y+2),rules.puGetType(y+2),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += 2;
    						}

    					} else {
    						System.out.println("(pvDecayEventCalc) failed because the input file contained a bad set of probability rules");
    					}
    				}
    			}
    		}
    	}
    }

    private void pvDecayEventCalcLong(long num, DecayChainRuleSet rules, String startN) {
    	//Function that calculates an integer (num) DecayEvents based on the DecayChainRuleSet (rules)
    	int numRules = rules.puGetNumRules();
    	//Calculate Number of DecayEvents
    	String startNucleus = startN;
    	double timeOffset = 0;
    	for(long x = 0; x<num; x++) {
    		timeOffset = 0;
    		startNucleus = startN;
    		for(int y = 0; y<numRules; y++) {
    			if (rules.puGetStartNucleus(y).compareTo(startNucleus)==0) {
    				if (rules.puGetProbability(y) == 1) {
    					startNucleus = rules.puGetEndNucleus(y);
    					DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    						pvAddDecayEvent(instance);
    					}
    					timeOffset += instance.puGetTime();
    				} else {
    					double seed = Math.random();
    					if ((rules.puGetProbability(y) + rules.puGetProbability(y+1)) == 1) {
    						if (seed <= rules.puGetProbability(y)){
    							startNucleus = rules.puGetEndNucleus(y);
    							DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y++;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+1);
    							DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+1),rules.puGetEnergy(y+1),rules.puGetType(y+1),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y++;
    						}

    					} else if ((rules.puGetProbability(y) + rules.puGetProbability(y+1) + rules.puGetProbability(y+2)) == 1) {
    						if (seed <= rules.puGetProbability(y)) {
    							startNucleus = rules.puGetEndNucleus(y);
    							DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y += 2;
    						} else if (seed <= (rules.puGetProbability(y) + rules.puGetProbability(y+1))) {
    							startNucleus = rules.puGetEndNucleus(y+1);
    							DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+1),rules.puGetEnergy(y+1),rules.puGetType(y+1),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += 2;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+2);
    							DecayEvent instance = new DecayEvent(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+2),rules.puGetEnergy(y+2),rules.puGetType(y+2),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += 2;
    						}

    					} else {
    						System.out.println("(pvDecayEventCalc) failed because the input file contained a bad set of probability rules");
    					}
    				}
    			}
    		}
    	}
    }

    private void pvAddDecayEvent(DecayEvent instance) {
    	DecayEvent[] DecayEvents = new DecayEvent[pvNumDecayEvents+1];
    	for(int x = 0; x < pvNumDecayEvents; x++) {
    		DecayEvents[x]=pvDecayEvents[x];
    	}
    	DecayEvents[pvNumDecayEvents] = instance;
    	pvDecayEvents = DecayEvents;
    	pvNumDecayEvents++;
    }

    public double[][] puGetAllEnergyAndTime() {
    	if (pvNumDecayEvents > 0) {
        	double returned[][] = new double[pvNumDecayEvents][2];
        	for (int x = 0;x<pvNumDecayEvents;x++) {
        		returned[x][0]=pvDecayEvents[x].puGetTime();
        		returned[x][1]=pvDecayEvents[x].puGetEnergy();
        	}
        	return returned;
    	} else {
    		System.out.println("(puGetEnergyAndTime) failed because this (NucleiSample) contain no DecayEvents");
    		double returned[][] = new double[1][1];
    		return returned;
    	}
    }

    public String puGetAllDecayEventData() {
    	if (pvNumDecayEvents > 0) {
        	StringBuilder text = new StringBuilder();
        	text.append("Occurence_Time(s),StartingNucleus,EndingNucleus,Type,Energy(MeV)" + System.getProperty("line.separator"));
        	for (int x = 0;x<pvNumDecayEvents;x++) {
        		text.append(pvDecayEvents[x].puGetTime() + "," + pvDecayEvents[x].puGetStartNucleus() + "," + pvDecayEvents[x].puGetEndNucleus() + "," + pvDecayEvents[x].puGetType() + "," + pvDecayEvents[x].puGetEnergy() + System.getProperty("line.separator"));
        	}
        	return text.toString();
    	} else {
    		System.out.println("(puGetAllDecayEventData) failed because this (NucleiSample) contain no DecayEvents");
    		return "";
    	}
    }

    public double puGetEnergySumOverTimeRange(double start, double end) {
    	//Returns the total energy in MeV radiated between the user supplied (start) and (end) times
    	double sum = 0;
    	double test = 0;
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}

    			}
    		} else {
    			System.out.println("(puGetEnergyAveOverTimeRange) failed because this (NucleiSample) contain no DecayEvents");
    			return 0;
    		}
    	} else {
    		System.out.println("(puGetEnergySumOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEnergyAveOverTimeRange(double start, double end) {
    	//Returns the average energy in MeV/s between the user supplied (start) and (end) times
    	double sum = 0;
    	double test = 0;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEnergyAveOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEnergyAveOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEnergyAveOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEnergyAveOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			}
    		} else {
    			System.out.println("(puGetEnergyAveOverTimeRange) failed because this (NucleiSample) contain no particles");
    			return 0;
    		}

    	} else {
    		System.out.println("(puGetEnergyAveOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return 0;
    	}
    	return (sum/(end-start));
    }

    public String puGetPerSecondAveEnergyForOneYear(double start) {
    	//Returns a comma delimited String with a years worth of MeV/s (one per hour) calculations starting at time (start)
    	double[] aveBucket = new double[365*24];
    	for (int x = 0; x<365*24; x++) {
    		aveBucket[x] = 0;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetPerSecondAveEnergyForOneYear) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetPerSecondAveEnergyForOneYear) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetPerSecondAveEnergyForOneYear) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= 0) {
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				for (int y = 0; y < 365*24; y++) {
    					if(((pvDecayEvents[x].puGetTime())>=(start+3600*y))&((pvDecayEvents[x].puGetTime())<(start+3600*(y+1)))){
    						aveBucket[y] = aveBucket[y] + pvDecayEvents[x].puGetEnergy();
    					}
    				}
    			}
    			for (int x = 0; x < 365*24; x++) {
    				aveBucket[x] = aveBucket[x]/3600;
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEvent)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = 0;x<365*24;x++) {
    				text.append((start+3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
    			}
    			return text.toString();
    		} else {
    			System.out.println("(puGetPerSecondAveEnergyForOneYear) failed because this (NucleiSample) contains no (DecayEvent)s");
    			return "(puGetPerSecondAveEnergyForOneYear) failed because this (NucleiSample) contains no (DecayEvent)s";
    		}
    	} else {
    		System.out.println ("(puGetPerSecondAveEnergyForOneYear) failed because the supplied start time is less than zero");
    		return "(puGetPerSecondAveEnergyForOneYear) failed because the supplied start time is less than zero";
    	}
    }

}
