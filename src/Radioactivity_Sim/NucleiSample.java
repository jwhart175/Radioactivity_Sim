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

	// a container for a group of user defined unstable nuclei and all of the radioactive events that they transition through to reach stability
    /* Variable and Function Nomenclature Prescipts:
     * pv = private
     * pu = public
     * pvs = private static
     * pus = public static
     * pvsf = private static final
     * pusf = public static final
     */

    private Event[] pvEvents;
    private RuleSet[] pvRules;
    private int pvNumRuleSets = 0;
    private int pvNumEvents = 0;
    private double pvStartTime = 0;
    private double pvEndTime = 0;
    private double pvResolution = 0;
    private double[] pvFinalPartNum = new double[1];
    private String[] pvNuclei = new String[1];

    public NucleiSample(int num,String input, double start, double end, double resolution) {
    	//Constructor for a sample containing a number of nuclei of a single type
        if (num > 0) {
            pvRules = new RuleSet[1];
            pvRules[0] = new RuleSet(input);
            pvNumRuleSets = 1;
            pvStartTime = start;
            pvEndTime = end;
            pvResolution = resolution;
            pvPredictNumCalcParticles(num,pvRules[0]);
        } else {
            System.out.println("Construction of this (NucleiSample) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSample(double num,String input, double start, double end, double resolution) {
    	//Constructor for a sample containing a number of nuclei of a single type
        if (num > 0) {
            pvRules = new RuleSet[1];
            pvRules[0] = new RuleSet(input);
            pvNumRuleSets = 1;
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

    public int puGetNumRuleSets() {
    	//Returns the total number of (RuleSet) objects
    	return pvNumRuleSets;
    }

    public RuleSet puGetRuleSet(int index) {
    	//Returns the (RuleSet) at the specified (index)
    	if(index < 0) {
    		String errString = "(puGetRuleSet) failed because the supplied (index) is less than zero!";
    		System.out.println(errString);
    		return new RuleSet();
    	} else if (index >= pvNumRuleSets) {
    		String errString = "(puGetRuleSet) failed because the supplied (index) is greater than the total of number of (RuleSet) objects in this (NucleiSample)!";
    		System.out.println(errString);
    		return new RuleSet();
    	} else {
    		return pvRules[index];
    	}
    }

    private void pvPredictNumCalcParticles(double num, RuleSet rules) {
    	double dividend = 1, subproduct = 1, subsum = 0, subsum2 = 0;
    	int numBranches = rules.puGetNumBranches();
    	double[] endPartNum = new double[1];
    	double[] startPartNum = new double[1];
    	double[] finalPartNum = new double[1];
    	RuleBranch branch = new RuleBranch();
    	for (int x = 0; x<numBranches ;x++) {
    		branch = rules.puGetRuleBranch(x);
    		int numRules = branch.puGetNumRules();
    		startPartNum = new double[numRules];
    		endPartNum = new double[numRules];
    		finalPartNum = new double[numRules];
    		for (int n = 0; n < numRules; n++) {
    			subsum2 = 0;
    			startPartNum[n] = 0;
    			endPartNum[n] = 0;
    			if(n == 1) {
    				//Calculate particle numbers at pvStartTime using the normal Bateman Solution
    				if (pvStartTime > 0) {
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
    							subsum = subsum + (Math.exp(-pvStartTime*Math.log(2)/branch.puGetHalfLife(j)))/dividend;
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
    					startPartNum[n] = subsum2;
    				} else if (pvStartTime ==0) {
    					for(int i = 0; i <= n;i++){
    						if (i == 0){
    							startPartNum[i] = num;
    						} else {
    							startPartNum[i] = 0;
    						}
    					}
    				}

    				/*
    				 * Calculates the number of events for each particle type from pvStartTime to pvEndTime
    				 * uses a modified Bateman equation in which negative terms (which represent transitions
    				 * to the next nuclei type are thrown out of the calculation for each nuclei type.  By
    				 * then subtracting these numbers from the initial counts, one obtains the total number
    				 * of transitions to that nuclei type, and hence the number of events.
    				 */
    				subsum2 = 0;
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
    						if (dividend > 0 & dividend != 1){
    							subsum = subsum + (Math.exp(-(pvEndTime-pvStartTime)*Math.log(2)/branch.puGetHalfLife(j)))/dividend;
    						}
    						if (j <= (n-1)) {
    							subproduct = subproduct * Math.log(2)/branch.puGetHalfLife(j);
    						}
    						subproduct = subproduct * branch.puGetProbability(0);
    					}
    					subsum2 = subsum2 + (startPartNum[i]*subproduct*subsum);
    				}
    				endPartNum[n] = subsum2;
    				//Calculate event times for events occurring between pvStartTime and pvEndTime
					for (long g = 0; g<(endPartNum[n]); g++) {
						Event instance = new Event(pvStartTime,pvEndTime,branch.puGetStartNucleus(n-1),branch.puGetEndNucleus(n-1),branch.puGetHalfLife(n-1),branch.puGetEnergy(n-1),branch.puGetType(n-1));
						pvAddEvent(instance);
					}
    			} else if (n>1) {
    				//Calculates events for child nuclei
    				double delta = (pvEndTime-pvStartTime)/pvResolution;
    				for (int w = 0; w<pvResolution; w++) {
    					double startTime = pvStartTime+w*delta;
    					double endTime = pvStartTime+(w+1)*delta;
    					if (startTime > 0) {
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
        							subsum = subsum + (Math.exp(-startTime*Math.log(2)/branch.puGetHalfLife(j)))/dividend;
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
        					startPartNum[n] = subsum2;
        				} else if (startTime ==0) {
        					for(int i = 0; i <= n;i++){
        						if (i == 0){
        							startPartNum[i] = num;
        						} else {
        							startPartNum[i] = 0;
        						}
        					}
        				}

    					subsum2 = 0;
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
        						if (dividend > 0 & dividend != 1){
        							subsum = subsum + (Math.exp(-(endTime-startTime)*Math.log(2)/branch.puGetHalfLife(j)))/dividend;
        						}
        						if (j <= (n-1)) {
        							subproduct = subproduct * Math.log(2)/branch.puGetHalfLife(j);
        						}
        						subproduct = subproduct * branch.puGetProbability(0);
        					}
        					subsum2 = subsum2 + (startPartNum[i]*subproduct*subsum);
        				}
        				endPartNum[n] = subsum2;
        				//Calculate event times for events occurring between pvStartTime and pvEndTime
						for (long g = 0; g<(endPartNum[n]); g++) {
							Event instance = new Event(true,pvStartTime,pvEndTime,branch.puGetStartNucleus(n-1),branch.puGetEndNucleus(n-1),branch.puGetHalfLife(n-1),branch.puGetEnergy(n-1),branch.puGetType(n-1));
							pvAddEvent(instance);
						}
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
    		pvNumRuleSets += 1;
            RuleSet[] newRules = new RuleSet[pvNumRuleSets];
            for(int x = 0; x<pvNumRuleSets-1;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumRuleSets-1] = new RuleSet(input);
    		pvRules = newRules;
    		pvPredictNumCalcParticles(num, pvRules[pvNumRuleSets-1]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    public void puAddSpecies (double num, String input, double start, double end) {
    	//Adds an double (num) of nuclei of the type described in data file whose path is the String (input)
    	if (num > 0) {
    		pvStartTime = start;
            pvEndTime = end;
    		pvNumRuleSets += 1;
            RuleSet[] newRules = new RuleSet[pvNumRuleSets];
            for(int x = 0; x<pvNumRuleSets-1;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumRuleSets-1] = new RuleSet(input);
    		pvRules = newRules;
    		pvPredictNumCalcParticles(num, pvRules[pvNumRuleSets-1]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    public void puAddSpecies (long num, String input, double start, double end) {
    	//Adds an integer (num) of nuclei of the type described in data file whose path is the String (input)
    	if (num > 0) {
    		pvStartTime = start;
            pvEndTime = end;
    		pvNumRuleSets += 1;
            RuleSet[] newRules = new RuleSet[pvNumRuleSets];
            for(int x = 0; x<pvNumRuleSets-1;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumRuleSets-1] = new RuleSet(input);
    		pvRules = newRules;
    		pvPredictNumCalcParticles(num, pvRules[pvNumRuleSets-1]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    private void pvEventCalc(int num, RuleSet rules, String startN) {
    	//Function that calculates an integer (num) events based on the RuleSet (rules)
    	int numRules = rules.puGetNumberOfRules();
    	//Calculate Number of Events
        String startNucleus = startN;
    	double timeOffset = 0;
    	for(int x = 0; x<num; x++) {
    		timeOffset = 0;
    		startNucleus = startN;
    		for(int y = 0; y<numRules; y++) {
    			if (rules.puGetStartNucleus(y).compareTo(startNucleus)==0) {
    				if (rules.puGetProbability(y) == 1) {
    					startNucleus = rules.puGetEndNucleus(y);
    					Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    						pvAddEvent(instance);
    					}
    					timeOffset += instance.puGetTime();
    				} else {
    					double seed = Math.random();
    					if ((rules.puGetProbability(y) + rules.puGetProbability(y+1)) == 1) {
    						if (seed <= rules.puGetProbability(y)){
    							startNucleus = rules.puGetEndNucleus(y);
    							Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y++;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+1);
    							Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+1),rules.puGetEnergy(y+1),rules.puGetType(y+1),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y++;
    						}

    					} else if ((rules.puGetProbability(y) + rules.puGetProbability(y+1) + rules.puGetProbability(y+2)) == 1) {
    						if (seed <= rules.puGetProbability(y)) {
    							startNucleus = rules.puGetEndNucleus(y);
    							Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y += 2;
    						} else if (seed <= (rules.puGetProbability(y) + rules.puGetProbability(y+1))) {
    							startNucleus = rules.puGetEndNucleus(y+1);
    							Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+1),rules.puGetEnergy(y+1),rules.puGetType(y+1),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += 2;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+2);
    							Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+2),rules.puGetEnergy(y+2),rules.puGetType(y+2),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += 2;
    						}

    					} else {
    						System.out.println("(pvEventCalc) failed because the input file contained a bad set of probability rules");
    					}
    				}
    			}
    		}
    	}
    }

    private void pvEventCalcLong(long num, RuleSet rules, String startN) {
    	//Function that calculates an integer (num) events based on the RuleSet (rules)
    	int numRules = rules.puGetNumberOfRules();
    	//Calculate Number of Events
    	String startNucleus = startN;
    	double timeOffset = 0;
    	for(long x = 0; x<num; x++) {
    		timeOffset = 0;
    		startNucleus = startN;
    		for(int y = 0; y<numRules; y++) {
    			if (rules.puGetStartNucleus(y).compareTo(startNucleus)==0) {
    				if (rules.puGetProbability(y) == 1) {
    					startNucleus = rules.puGetEndNucleus(y);
    					Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    						pvAddEvent(instance);
    					}
    					timeOffset += instance.puGetTime();
    				} else {
    					double seed = Math.random();
    					if ((rules.puGetProbability(y) + rules.puGetProbability(y+1)) == 1) {
    						if (seed <= rules.puGetProbability(y)){
    							startNucleus = rules.puGetEndNucleus(y);
    							Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y++;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+1);
    							Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+1),rules.puGetEnergy(y+1),rules.puGetType(y+1),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y++;
    						}

    					} else if ((rules.puGetProbability(y) + rules.puGetProbability(y+1) + rules.puGetProbability(y+2)) == 1) {
    						if (seed <= rules.puGetProbability(y)) {
    							startNucleus = rules.puGetEndNucleus(y);
    							Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y),rules.puGetEnergy(y),rules.puGetType(y),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y += 2;
    						} else if (seed <= (rules.puGetProbability(y) + rules.puGetProbability(y+1))) {
    							startNucleus = rules.puGetEndNucleus(y+1);
    							Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+1),rules.puGetEnergy(y+1),rules.puGetType(y+1),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += 2;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+2);
    							Event instance = new Event(rules.puGetStartNucleus(y),rules.puGetEndNucleus(y),rules.puGetHalfLife(y+2),rules.puGetEnergy(y+2),rules.puGetType(y+2),timeOffset);
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += 2;
    						}

    					} else {
    						System.out.println("(pvEventCalc) failed because the input file contained a bad set of probability rules");
    					}
    				}
    			}
    		}
    	}
    }

    private void pvAddEvent(Event instance) {
    	Event[] events = new Event[pvNumEvents+1];
    	for(int x = 0; x < pvNumEvents; x++) {
    		events[x]=pvEvents[x];
    	}
    	events[pvNumEvents] = instance;
    	pvEvents = events;
    	pvNumEvents++;
    }

    public double[][] puGetAllEnergyAndTime() {
    	if (pvNumEvents > 0) {
        	double returned[][] = new double[pvNumEvents][2];
        	for (int x = 0;x<pvNumEvents;x++) {
        		returned[x][0]=pvEvents[x].puGetTime();
        		returned[x][1]=pvEvents[x].puGetEnergy();
        	}
        	return returned;
    	} else {
    		System.out.println("(puGetEnergyAndTime) failed because this (NucleiSample) contain no events");
    		double returned[][] = new double[1][1];
    		return returned;
    	}
    }

    public String puGetAllEventData() {
    	if (pvNumEvents > 0) {
        	StringBuilder text = new StringBuilder();
        	text.append("Occurence_Time(s),StartingNucleus,EndingNucleus,Type,Energy(MeV)" + System.getProperty("line.separator"));
        	for (int x = 0;x<pvNumEvents;x++) {
        		text.append(pvEvents[x].puGetTime() + "," + pvEvents[x].puGetStartNucleus() + "," + pvEvents[x].puGetEndNucleus() + "," + pvEvents[x].puGetType() + "," + pvEvents[x].puGetEnergy() + System.getProperty("line.separator"));
        	}
        	return text.toString();
    	} else {
    		System.out.println("(puGetAllEventData) failed because this (NucleiSample) contain no events");
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
    		if (pvNumEvents > 0) {
    			for (int x = 0; x < pvNumEvents; x++) {
    				test = pvEvents[x].puGetTime();
    				if (test>=start&test<end) {
    					sum += pvEvents[x].puGetEnergy();
    				}

    			}
    		} else {
    			System.out.println("(puGetEnergyAveOverTimeRange) failed because this (NucleiSample) contain no events");
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
    		if (pvNumEvents > 0) {
    			for (int x = 0; x < pvNumEvents; x++) {
    				test = pvEvents[x].puGetTime();
    				if (test>=start&test<end) {
    					sum += pvEvents[x].puGetEnergy();
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
    		if (pvNumEvents > 0) {
    			for (int x = 0; x < pvNumEvents; x++) {
    				for (int y = 0; y < 365*24; y++) {
    					if(((pvEvents[x].puGetTime())>=(start+3600*y))&((pvEvents[x].puGetTime())<(start+3600*(y+1)))){
    						aveBucket[y] = aveBucket[y] + pvEvents[x].puGetEnergy();
    					}
    				}
    			}
    			for (int x = 0; x < 365*24; x++) {
    				aveBucket[x] = aveBucket[x]/3600;
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the events occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = 0;x<365*24;x++) {
    				text.append((start+3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
    			}
    			return text.toString();
    		} else {
    			System.out.println("(puGetPerSecondAveEnergyForOneYear) failed because this (NucleiSample) contains no events");
    			return "(puGetPerSecondAveEnergyForOneYear) failed because this (NucleiSample) contains no events";
    		}
    	} else {
    		System.out.println ("(puGetPerSecondAveEnergyForOneYear) failed because the supplied start time is less than zero");
    		return "(puGetPerSecondAveEnergyForOneYear) failed because the supplied start time is less than zero";
    	}
    }

}
