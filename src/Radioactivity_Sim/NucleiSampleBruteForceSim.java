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

public class NucleiSampleBruteForceSim extends PRSFNUM {
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
	 * protected static final int prsfIntOne = 1;
	 * protected static final int prsfIntTwo = 2;
	 * protected static final int prsfIntThree = 3
	 * protected static final int prsfIntFour = 4;
	 * protected static final int prsfIntFive = 5;
	 * protected static final int prsfIntSix = 6;
	 * protected static final int[] prsfDetailedTest = ...
	 */

    private DecayEvent[] pvDecayEvents;
    private DecayChainRuleSet[] pvRules;
    private int pvNumDecayChainRuleSets = prsfIntZero;
    private int pvNumDecayEvents = prsfIntZero;
    private double pvStartTime = prsfIntZero;
    private double pvEndTime = prsfIntZero;
    private double[] pvFinalPartNum = new double[prsfIntOne];
    private String[] pvNuclei = new String[prsfIntOne];
    private double pvNucleiStarter = prsfIntZero;

    public NucleiSampleBruteForceSim(int num,String input, double start, double end) {
    	//Constructor for a sample containing a number of nuclei of a single type
    	pvNucleiStarter = Math.random();
        pvNuclei[prsfIntZero] = Double.toString(pvNucleiStarter);
        if (num > prsfIntZero) {
            pvRules = new DecayChainRuleSet[prsfIntOne];
            pvRules[prsfIntZero] = new DecayChainRuleSet(input);
            pvNumDecayChainRuleSets = prsfIntOne;
            pvStartTime = start;
            pvEndTime = end;
            pvDecayEventCalc(num,pvRules[prsfIntZero]);
        } else {
            System.out.println("Construction of this (NucleiSampleBruteForceSim) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSampleBruteForceSim(long num,String input, double start, double end) {
    	//Constructor for a sample containing a number of nuclei of a single type
    	pvNucleiStarter = Math.random();
        pvNuclei[prsfIntZero] = Double.toString(pvNucleiStarter);
        if (num > prsfIntZero) {
            pvRules = new DecayChainRuleSet[prsfIntOne];
            pvRules[prsfIntZero] = new DecayChainRuleSet(input);
            pvNumDecayChainRuleSets = prsfIntOne;
            pvStartTime = start;
            pvEndTime = end;
            pvDecayEventCalcLong(num, pvRules[prsfIntZero]);
        } else {
            System.out.println("Construction of this (NucleiSampleBruteForceSim) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSampleBruteForceSim() {
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
    		String errString = "(puGetDecayChainRuleSet) failed because the supplied (index) is greater than the total of number of (DecayChainRuleSet) objects in this (NucleiSampleBruteForceSim)!";
    		System.out.println(errString);
    		return new DecayChainRuleSet();
    	} else {
    		return pvRules[index];
    	}
    }

    public void puAddSpecies (int num, String input) {
    	//Adds an integer (num) of nuclei of the type described in data file whose path is the String (input)
    	if (num > prsfIntZero) {
    		pvNumDecayChainRuleSets += prsfIntOne;
            DecayChainRuleSet[] newRules = new DecayChainRuleSet[pvNumDecayChainRuleSets];
            for(int x = prsfIntZero; x<pvNumDecayChainRuleSets-prsfIntOne;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumDecayChainRuleSets-prsfIntOne] = new DecayChainRuleSet(input);
    		pvRules = newRules;
    		pvDecayEventCalc(num, pvRules[pvNumDecayChainRuleSets-prsfIntOne]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    public void puAddSpecies (long num, String input) {
    	//Adds an integer (num) of nuclei of the type described in data file whose path is the String (input)
    	if (num > prsfIntZero) {
    		pvNumDecayChainRuleSets += prsfIntOne;
            DecayChainRuleSet[] newRules = new DecayChainRuleSet[pvNumDecayChainRuleSets];
            for(int x = prsfIntZero; x<pvNumDecayChainRuleSets-prsfIntOne;x++){
            	newRules[x] = pvRules[x];
            }
            newRules[pvNumDecayChainRuleSets-prsfIntOne] = new DecayChainRuleSet(input);
    		pvRules = newRules;
    		pvDecayEventCalcLong(num, pvRules[pvNumDecayChainRuleSets-prsfIntOne]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    private void pvDecayEventCalc(int num, DecayChainRuleSet rules) {
    	//Function that calculates an integer (num) DecayEvents based on the DecayChainRuleSet (rules)
    	int numRules = rules.puGetNumRules();
    	//Calculate Number of DecayEvents
        String startNucleus = "";
    	double timeOffset = prsfIntZero;
    	for(int x = prsfIntZero; x < rules.puGetNumRules(); x++){
	    	boolean newNucleus = true;
			for(int q = prsfIntZero;q < pvNuclei.length; q++) {
				if(pvNuclei.length ==prsfIntOne){
					if(Double.toString(pvNucleiStarter).compareTo(pvNuclei[prsfIntZero])==prsfIntZero){
						newNucleus = false;
						pvNuclei[prsfIntZero] = rules.puGetStartNucleus(x);
						break;
					}
				}
				if(pvNuclei[q].compareTo(rules.puGetStartNucleus(x))==prsfIntZero){
					newNucleus = false;
				}
			}
			if(newNucleus){
				String[] nuclei = new String[pvNuclei.length+prsfIntOne];
				for(int q = prsfIntZero;q < pvNuclei.length; q++) {
					nuclei[q] = pvNuclei[q];
				}
				nuclei[pvNuclei.length] = rules.puGetStartNucleus(x);
				pvNuclei = nuclei;
			}
    	}
    	for(int x = prsfIntZero; x<num; x++) {
    		timeOffset = prsfIntZero;
    		startNucleus = rules.puGetStartNucleus(prsfIntZero);
    		for(int y = prsfIntZero; y<numRules; y++) {
    			if (rules.puGetStartNucleus(y).compareTo(startNucleus)==prsfIntZero) {
    				if (rules.puGetProbability(y) == prsfIntOne) {
    					startNucleus = rules.puGetEndNucleus(y);
    					DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y));
    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    						pvAddDecayEvent(instance);
    					}
    					timeOffset += instance.puGetTime();
    				} else {
    					double seed = Math.random();
    					if ((rules.puGetProbability(y) + rules.puGetProbability(y+1)) == prsfIntOne) {
    						if (seed <= rules.puGetProbability(y)){
    							startNucleus = rules.puGetEndNucleus(y);
    							DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y));
    							if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y++;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+prsfIntOne);
    							DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y+prsfIntOne));
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y++;
    						}

    					} else if ((rules.puGetProbability(y) + rules.puGetProbability(y+prsfIntOne) + rules.puGetProbability(y+prsfIntTwo)) == prsfIntOne) {
    						if (seed <= rules.puGetProbability(y)) {
    							startNucleus = rules.puGetEndNucleus(y);
    							DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y));
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y += prsfIntTwo;
    						} else if (seed <= (rules.puGetProbability(y) + rules.puGetProbability(y+prsfIntOne))) {
    							startNucleus = rules.puGetEndNucleus(y+1);
    							DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y+prsfIntOne));
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += prsfIntTwo;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+prsfIntTwo);
    							DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y+prsfIntTwo));
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += prsfIntTwo;
    						}

    					} else {
    						System.out.println("(pvDecayEventCalc) failed because the input file contained a bad set of probability rules");
    					}
    				}
    			}
    		}
    	}
    }

    private void pvDecayEventCalcLong(long num, DecayChainRuleSet rules) {
    	//Function that calculates an integer (num) DecayEvents based on the DecayChainRuleSet (rules)
    	int numRules = rules.puGetNumRules();
    	//Calculate Number of DecayEvents
    	String startNucleus = "";
    	double timeOffset = prsfIntZero;
    	for(int x = prsfIntZero; x < rules.puGetNumRules(); x++){
	    	boolean newNucleus = true;
			for(int q = prsfIntZero;q < pvNuclei.length; q++) {
				if(pvNuclei.length ==prsfIntOne){
					if(Double.toString(pvNucleiStarter).compareTo(pvNuclei[prsfIntZero])==prsfIntZero){
						newNucleus = false;
						pvNuclei[prsfIntZero] = rules.puGetStartNucleus(x);
						break;
					}
				}
				if(pvNuclei[q].compareTo(rules.puGetStartNucleus(x))==prsfIntZero){
					newNucleus = false;
				}
			}
			if(newNucleus){
				String[] nuclei = new String[pvNuclei.length+prsfIntOne];
				for(int q = prsfIntZero;q < pvNuclei.length; q++) {
					nuclei[q] = pvNuclei[q];
				}
				nuclei[pvNuclei.length] = rules.puGetStartNucleus(x);
				pvNuclei = nuclei;
			}
    	}
    	for(long x = prsfIntZero; x<num; x++) {
    		timeOffset = prsfIntZero;
    		startNucleus = rules.puGetStartNucleus(prsfIntZero);
    		for(int y = prsfIntZero; y<numRules; y++) {
    			if (rules.puGetStartNucleus(y).compareTo(startNucleus)==prsfIntZero) {
    				if (rules.puGetProbability(y) == prsfIntOne) {
    					startNucleus = rules.puGetEndNucleus(y);
    					DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y));
    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    						pvAddDecayEvent(instance);
    					}
    					timeOffset += instance.puGetTime();
    				} else {
    					double seed = Math.random();
    					if ((rules.puGetProbability(y) + rules.puGetProbability(y+prsfIntOne)) == prsfIntOne) {
    						if (seed <= rules.puGetProbability(y)){
    							startNucleus = rules.puGetEndNucleus(y);
    							DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y));
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y++;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+prsfIntOne);
    							DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y+prsfIntOne));
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y++;
    						}

    					} else if ((rules.puGetProbability(y) + rules.puGetProbability(y+prsfIntOne) + rules.puGetProbability(y+prsfIntTwo)) == prsfIntOne) {
    						if (seed <= rules.puGetProbability(y)) {
    							startNucleus = rules.puGetEndNucleus(y);
    							DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y));
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    	    					y += 2;
    						} else if (seed <= (rules.puGetProbability(y) + rules.puGetProbability(y+prsfIntOne))) {
    							startNucleus = rules.puGetEndNucleus(y+prsfIntOne);
    							DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y+prsfIntOne));
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += prsfIntTwo;
    						} else {
    							startNucleus = rules.puGetEndNucleus(y+prsfIntTwo);
    							DecayEvent instance = new DecayEvent(timeOffset, rules.puGetRule(y+prsfIntTwo));
    	    					if (instance.puGetTime()>=pvStartTime&instance.puGetTime()<pvEndTime) {
    	    						pvAddDecayEvent(instance);
    	    					}
    	    					timeOffset += instance.puGetTime();
    							y += prsfIntTwo;
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
    	//Adds a (DecayEvent) to this (NucleiSampleBruteForceSim)
    	DecayEvent[] DecayEvents = new DecayEvent[pvNumDecayEvents+1];
    	for(int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    		DecayEvents[x]=pvDecayEvents[x];
    	}
    	DecayEvents[pvNumDecayEvents] = instance;
    	pvDecayEvents = DecayEvents;
    	pvNumDecayEvents++;
    }

    public double[][] puGetAllDecayEventEnergyAndTime() {
    	//returns all calculated (DecayEvent) energies and times
    	if (pvNumDecayEvents > prsfIntZero) {
        	double returned[][] = new double[pvNumDecayEvents][prsfIntTwo];
        	for (int x = prsfIntZero;x<pvNumDecayEvents;x++) {
        		returned[x][prsfIntZero]=pvDecayEvents[x].puGetTime();
        		returned[x][prsfIntOne]=pvDecayEvents[x].puGetEnergy();
        	}
        	return returned;
    	} else {
    		System.out.println("(puGetEnergyAndTime) failed because this (NucleiSampleBruteForceSim) contain no DecayEvents");
    		double returned[][] = new double[prsfIntOne][prsfIntOne];
    		return returned;
    	}
    }

    public int puGetNumDecayEvents(){
    	//returns the number of (DecayEvent)s contained in this (NucleiSampleBruteForceSim)
    	return pvNumDecayEvents;
    }

    public double puGetStartTime(){
    	//returns the (pvStartTime) of this (NucleiSampleBruteForceSim)
    	return pvStartTime;
    }

    public double puGetEndTime(){
    	//returns the (pvEndTime) of this (NucleiSampleBruteForceSim)
    	return pvEndTime;
    }

    public String puGetAllEndTimeNucleiCounts() {
    	//Returns a string containing all of the final counts for all nuclei at (pvEndTime) within this (NucleiSampleBruteForceSim)
    	StringBuilder text = new StringBuilder();
    	text.append("The remaining nuclei counts at "+pvEndTime+" are: " + System.getProperty("line.separator"));
    	text.append("Nucleus,NumberOfNuclei" + System.getProperty("line.separator"));
    	double[] endTimePartNum = new double[pvNuclei.length];
    	for(int x = prsfIntZero; x < pvNumDecayEvents; x++){
    		for(int y = prsfIntZero; y < pvNuclei.length; y++) {
    			if((pvEndTime<pvDecayEvents[x].puGetTime())&(pvEndTime>=pvDecayEvents[x].puGetTimeOffset())&(pvNuclei[y].compareTo(pvDecayEvents[x].puGetStartNucleus())==0)){
    				endTimePartNum[y]++;
    			}
    		}
    	}
    	for(int q = prsfIntZero; q < pvNuclei.length; q++) {
			text.append(pvNuclei[q] + "," + endTimePartNum[q] + System.getProperty("line.separator"));
		}
    	return text.toString();
    }

    public String puGetAllStartTimeNucleiCounts() {
    	//Returns a string containing all of the final counts for all nuclei at (pvEndTime) within this (NucleiSampleBruteForceSim)
    	StringBuilder text = new StringBuilder();
    	text.append("The initial nuclei counts at "+pvStartTime+" are: " + System.getProperty("line.separator"));
    	text.append("Nucleus,NumberOfNuclei" + System.getProperty("line.separator"));
    	double[] startTimePartNum = new double[pvNuclei.length];
    	for(int x = prsfIntZero; x < pvNumDecayEvents; x++){
    		for(int y = prsfIntZero; y < pvNuclei.length; y++) {
    			if((pvStartTime<pvDecayEvents[x].puGetTime())&(pvStartTime>=pvDecayEvents[x].puGetTimeOffset())&(pvNuclei[y].compareTo(pvDecayEvents[x].puGetStartNucleus())==0)){
    				startTimePartNum[y]++;
    			}
    		}
    	}
    	for(int q = prsfIntZero; q < pvNuclei.length; q++) {
			text.append(pvNuclei[q] + "," + startTimePartNum[q] + System.getProperty("line.separator"));
		}
    	return text.toString();
    }

    public String puGetAllEventCountsOverTimeRangeByNuclei(double start, double end) {
    	//Returns a string containing all of the event counts for all nuclei over the specified time range within this (NucleiSampleBruteForceSim)
    	StringBuilder text = new StringBuilder();
    	text.append("The final decay event counts are: " + System.getProperty("line.separator"));
    	text.append("Nucleus,NumberOfEvents" + System.getProperty("line.separator"));
    	for(int q = prsfIntZero; q < pvNuclei.length; q++) {
			text.append(pvNuclei[q] + "," + puGetEventNumForStartNucleusOverTimeRange(start,end,pvNuclei[q]) + System.getProperty("line.separator"));

		}
    	return text.toString();
    }

    public String puGetAllParticleAndEMCountsOverTimeRangeByEnergy(double start, double end){
    	//Returns the number of emissions listed by type and energy that occurred between the user supplied (start) and (end) times
    	String names[] = new String[prsfIntOne];
    	double energies[] = new double[prsfIntOne];
    	double counts[] = new double[prsfIntOne];
    	double test = prsfIntZero;
    	int length = prsfIntZero;
    	StringBuilder text = new StringBuilder();
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetAllParticleAndEMCountsOverTimeRangeByEnergy) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		String err = "Warning (puGetAllParticleAndEMCountsOverTimeRangeByEnergy) was supplied a start time that is past (pvEndTime)!";
    		System.out.println(err);
    		return err;
    	}
    	if (end < pvStartTime) {
    		String err = "Warning (puGetAllParticleAndEMCountsOverTimeRangeByEnergy) was supplied an end time that is earlier than (pvStartTime)!";
    		System.out.println(err);
    		return err;
    	} else if (end > pvEndTime) {
    		String err = "Warning (puGetAllParticleAndEMCountsOverTimeRangeByEnergy) was supplied an end time that is past (pvEndTime)!";
    		System.out.println(err);
    	}
    	if (start < end) {
    		if (pvNumDecayEvents > prsfIntZero) {
    			//Build counts for the independent name and energy pairs
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end) {
    					if(pvDecayEvents[x].puGetNumGammas()>0){
							for(int y = prsfIntZero; y < pvDecayEvents[x].puGetNumGammas(); y++){
								if(length==prsfIntZero){
	    							names[prsfIntZero]=pvDecayEvents[x].puGetGammaName(y);
	    							energies[prsfIntZero]=pvDecayEvents[x].puGetGammaEnergy(y);
	    							counts[prsfIntZero]=pvDecayEvents[x].puGetGammaIntensity(y);
	    							length++;
	    						} else {
	    							boolean isNew = true;
	    							for(int z = prsfIntZero; z < length;z++){
	    								if(pvDecayEvents[x].puGetGammaName(y).compareTo(names[z])==0&pvDecayEvents[x].puGetGammaEnergy(y)==energies[z]){
	    									isNew = false;
	    									counts[z]+=pvDecayEvents[x].puGetGammaIntensity(y);
	    								}
	    							}
	    							if(isNew){
	    								String[] newNames = new String[length+prsfIntOne];
	    								double[] newEnergies = new double[length+prsfIntOne];
	    								double[] newCounts = new double[length+prsfIntOne];
	    								for(int z = prsfIntZero; z < length; z++){
	    									newNames[z] = names[z];
	    									newEnergies[z] = energies[z];
	    									newCounts[z] = counts[z];
	    								}
	    								newNames[length] = pvDecayEvents[x].puGetGammaName(y);
	    								newEnergies[length] = pvDecayEvents[x].puGetGammaEnergy(y);
	    								newCounts[length] = pvDecayEvents[x].puGetGammaIntensity(y);
	    								names = newNames;
	    								energies = newEnergies;
	    								counts = newCounts;
	    								length++;
	    							}
	    						}
							}
						}
						if(pvDecayEvents[x].puGetNumAlphas()>0){
							for(int y = prsfIntZero; y < pvDecayEvents[x].puGetNumAlphas(); y++){
								if(length==prsfIntZero){
	    							names[prsfIntZero]=pvDecayEvents[x].puGetAlphaName(y);
	    							energies[prsfIntZero]=pvDecayEvents[x].puGetAlphaEnergy(y);
	    							counts[prsfIntZero]=pvDecayEvents[x].puGetAlphaIntensity(y);
	    							length++;
	    						} else {
	    							boolean isNew = true;
	    							for(int z = prsfIntZero; z < length;z++){
	    								if(pvDecayEvents[x].puGetAlphaName(y).compareTo(names[z])==0&pvDecayEvents[x].puGetAlphaEnergy(y)==energies[z]){
	    									isNew = false;
	    									counts[z]+=pvDecayEvents[x].puGetAlphaIntensity(y);
	    								}
	    							}
	    							if(isNew){
	    								String[] newNames = new String[length+prsfIntOne];
	    								double[] newEnergies = new double[length+prsfIntOne];
	    								double[] newCounts = new double[length+prsfIntOne];
	    								for(int z = prsfIntZero; z < length; z++){
	    									newNames[z] = names[z];
	    									newEnergies[z] = energies[z];
	    									newCounts[z] = counts[z];
	    								}
	    								newNames[length] = pvDecayEvents[x].puGetAlphaName(y);
	    								newEnergies[length] = pvDecayEvents[x].puGetAlphaEnergy(y);
	    								newCounts[length] = pvDecayEvents[x].puGetAlphaIntensity(y);
	    								names = newNames;
	    								energies = newEnergies;
	    								counts = newCounts;
	    								length++;
	    							}
	    						}
							}
						}
						if(pvDecayEvents[x].puGetNumNeutrons()>0){
							for(int y = prsfIntZero; y < pvDecayEvents[x].puGetNumNeutrons(); y++){
								if(length==prsfIntZero){
	    							names[prsfIntZero]=pvDecayEvents[x].puGetNeutronName(y);
	    							energies[prsfIntZero]=pvDecayEvents[x].puGetNeutronEnergy(y);
	    							counts[prsfIntZero]=pvDecayEvents[x].puGetNeutronIntensity(y);
	    							length++;
	    						} else {
	    							boolean isNew = true;
	    							for(int z = prsfIntZero; z < length;z++){
	    								if(pvDecayEvents[x].puGetNeutronName(y).compareTo(names[z])==0&pvDecayEvents[x].puGetNeutronEnergy(y)==energies[z]){
	    									isNew = false;
	    									counts[z]+=pvDecayEvents[x].puGetNeutronIntensity(y);
	    								}
	    							}
	    							if(isNew){
	    								String[] newNames = new String[length+prsfIntOne];
	    								double[] newEnergies = new double[length+prsfIntOne];
	    								double[] newCounts = new double[length+prsfIntOne];
	    								for(int z = prsfIntZero; z < length; z++){
	    									newNames[z] = names[z];
	    									newEnergies[z] = energies[z];
	    									newCounts[z] = counts[z];
	    								}
	    								newNames[length] = pvDecayEvents[x].puGetNeutronName(y);
	    								newEnergies[length] = pvDecayEvents[x].puGetNeutronEnergy(y);
	    								newCounts[length] = pvDecayEvents[x].puGetNeutronIntensity(y);
	    								names = newNames;
	    								energies = newEnergies;
	    								counts = newCounts;
	    								length++;
	    							}
	    						}
							}
						}
						if(pvDecayEvents[x].puGetNumBetas()>0){
							for(int y = prsfIntZero; y < pvDecayEvents[x].puGetNumBetas(); y++){
								if(length==prsfIntZero){
	    							names[prsfIntZero]=pvDecayEvents[x].puGetBetaName(y);
	    							energies[prsfIntZero]=pvDecayEvents[x].puGetBetaEnergy(y);
	    							counts[prsfIntZero]=pvDecayEvents[x].puGetBetaIntensity(y);
	    							length++;
	    						} else {
	    							boolean isNew = true;
	    							for(int z = prsfIntZero; z < length;z++){
	    								if(pvDecayEvents[x].puGetBetaName(y).compareTo(names[z])==0&pvDecayEvents[x].puGetBetaEnergy(y)==energies[z]){
	    									isNew = false;
	    									counts[z]+=pvDecayEvents[x].puGetBetaIntensity(y);
	    								}
	    							}
	    							if(isNew){
	    								String[] newNames = new String[length+prsfIntOne];
	    								double[] newEnergies = new double[length+prsfIntOne];
	    								double[] newCounts = new double[length+prsfIntOne];
	    								for(int z = prsfIntZero; z < length; z++){
	    									newNames[z] = names[z];
	    									newEnergies[z] = energies[z];
	    									newCounts[z] = counts[z];
	    								}
	    								newNames[length] = pvDecayEvents[x].puGetBetaName(y);
	    								newEnergies[length] = pvDecayEvents[x].puGetBetaEnergy(y);
	    								newCounts[length] = pvDecayEvents[x].puGetBetaIntensity(y);
	    								names = newNames;
	    								energies = newEnergies;
	    								counts = newCounts;
	    								length++;
	    							}
	    						}
							}
						}
    				}
    			}
    			text.append("From t = " + start + " to t = " + end + ", the following emissions occur:" + System.getProperty("line.separator"));
    			text.append("Names, Energy[MeV], Counts" + System.getProperty("line.separator"));
    			for(int x = prsfIntZero; x < length; x++){
    				text.append(names[x] + ", " + energies[x] + ", " + counts[x] + System.getProperty("line.separator"));
    			}
    		} else {
    			String err = "(puGetAllParticleAndEMCountsOverTimeRangeByEnergy) failed because this (NucleiSampleBruteForceSim) contain no (DecayEvent)s";
    			System.out.println(err);
    			return err;
    		}
    	} else {
    		String err = "(puGetAllParticleAndEMCountsOverTimeRangeByEnergy) failed because the supplied start time was greater than the supplied end time!";
    		System.out.println(err);
			return err;
    	}
    	return(text.toString());
    }

    public String puGetAllDecayEventData() {
    	//Returns a complete set of this (NucleiSampleBruteForceSims)'s (DecayEvent) data
    	if (pvNumDecayEvents > prsfIntZero) {
        	StringBuilder text = new StringBuilder();
        	text.append("Occurence_Time(s),StartingNucleus,EndingNucleus,Type,Energy(MeV)" + System.getProperty("line.separator"));
        	for (int x = prsfIntZero;x<pvNumDecayEvents;x++) {
        		text.append(pvDecayEvents[x].puGetTime() + "," + pvDecayEvents[x].puGetStartNucleus() + "," + pvDecayEvents[x].puGetEndNucleus() + "," + pvDecayEvents[x].puGetType() + "," + pvDecayEvents[x].puGetEnergy() + System.getProperty("line.separator"));
        	}
        	return text.toString();
    	} else {
    		System.out.println("(puGetAllDecayEventData) failed because this (NucleiSampleBruteForceSim) contain no DecayEvents");
    		return "";
    	}
    }

    public double puGetEventNumForTypeOverTimeRange(double start, double end, String type) {
    	//Returns the number of events with the supplied (type) that occurred between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&type.compareTo(pvDecayEvents[x].puGetType())==prsfIntZero) {
    					sum++;
    				}
    			}
    		} else {
    			System.out.println("(puGetEventNumForTypeOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEventNumForTypeOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEventNumForTypeAndStartNOverTimeRange(double start, double end, String type, String startN) {
    	//Returns the number of events with the supplied (type) and (startN) that occurred between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	double test = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEventNumForTypeAndStartNOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEventNumForTypeAndStartNOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEventNumForTypeAndStartNOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEventNumForTypeAndStartNOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&type.compareTo(pvDecayEvents[x].puGetType())==prsfIntZero&startN.compareTo(pvDecayEvents[x].puGetStartNucleus())==prsfIntZero) {
    					sum++;
    				}
    			}
    		} else {
    			System.out.println("(puGetEventNumForTypeAndStartNOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEventNumForTypeAndStartNOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEventNumForStartNucleusOverTimeRange(double start, double end, String startN) {
    	//Returns the number of events with the supplied (startN) that occurred between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&startN.compareTo(pvDecayEvents[x].puGetStartNucleus())==prsfIntZero) {
    					sum++;
    				}
    			}
    		} else {
    			System.out.println("(puGetEventNumForStartNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEventSet)s");
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
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&endN.compareTo(pvDecayEvents[x].puGetEndNucleus())==prsfIntZero) {
    					sum++;
    				}
    			}
    		} else {
    			System.out.println("(puGetEventNumForEndNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEventSet)s");
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
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end) {
    					sum++;
    				}
    			}
    		}  else {
    			System.out.println("(puGetEventNumForEndNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEventSet)s");
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
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&type.compareTo(pvDecayEvents[x].puGetType())==prsfIntZero) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}

    			}
    		} else {
    			System.out.println("(puGetEnergySumForTypeOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEvent)s");
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
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&startN.compareTo(pvDecayEvents[x].puGetStartNucleus())==prsfIntZero) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}

    			}
    		} else {
    			System.out.println("(puGetEnergySumForStartingNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEvent)s");
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
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&endN.compareTo(pvDecayEvents[x].puGetEndNucleus())==prsfIntZero) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}

    			}
    		} else {
    			System.out.println("(puGetEnergySumForEndingNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEvent)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEnergySumForEndingNucleusOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEnergySumForTypeAndStartNOverTimeRange(double start, double end, String type, String startN) {
    	//Returns the total energy in MeV for the supplied (startN) and (type) that was radiated between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetEnergySumForTypeAndStartNOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetEnergySumForTypeAndStartNOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetEnergySumForTypeAndStartNOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetEnergySumForTypeAndStartNOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				if (startN.compareTo(pvDecayEvents[x].puGetStartNucleus())==prsfIntZero&type.compareTo(pvDecayEvents[x].puGetType())==prsfIntZero) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			}
    		} else {
    			System.out.println("(puGetEnergySumForTypeAndStartNOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEventSet)s");
    			return prsfIntZero;
    		}
    	} else {
    		System.out.println("(puGetEnergySumForTypeAndStartNOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    	}
    	return sum;
    }

    public double puGetEnergySumOverTimeRange(double start, double end) {
    	//Returns the total energy in MeV radiated between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			}
    		} else {
    			System.out.println("(puGetEnergySumOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEvent)s");
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
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			 }
    		} else {
    			System.out.println("(puGetRadiatedPowerOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no particles");
    			return prsfIntZero;
    		}

    	} else {
    		System.out.println("(puGetRadiatedPowerOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return prsfIntZero;
    	}
    	return (sum/(end-start));
    }

    public double puGetRadiatedPowerForTypeAndStartNOverTimeRange(double start, double end, String type, String startN) {
    	//Returns the radiated power in MeV/s for the supplied (type) and (startN) between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	double test = prsfIntZero;
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForTypeAndStartNOverTimeRange) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForTypeAndStartNOverTimeRange) was supplied a start time that is past (pvEndTime)!");
    		return sum;
    	}
    	if (end < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForTypeAndStartNOverTimeRange) was supplied an end time that is earlier than (pvStartTime)!");
    		return sum;
    	} else if (end > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForTypeAndStartNOverTimeRange) was supplied an end time that is past (pvEndTime)!");
    	}
    	if (start < end) {
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&type.compareTo(pvDecayEvents[x].puGetType())==prsfIntZero&startN.compareTo(pvDecayEvents[x].puGetStartNucleus())==prsfIntZero) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			 }
    		} else {
    			System.out.println("(puGetRadiatedPowerForTypeAndStartNOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no particles");
    			return prsfIntZero;
    		}

    	} else {
    		System.out.println("(puGetRadiatedPowerForTypeAndStartNOverTimeRange) failed because the supplied start time was greater than the supplied end time!");
    		return prsfIntZero;
    	}
    	return (sum/(end-start));
    }

    public double puGetRadiatedPowerForTypeOverTimeRange(double start, double end, String type) {
    	//Returns the radiated power in MeV/s for the supplied (type) between the user supplied (start) and (end) times
    	double sum = prsfIntZero;
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&type.compareTo(pvDecayEvents[x].puGetType())==prsfIntZero) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			 }
    		} else {
    			System.out.println("(puGetRadiatedPowerForTypeOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no particles");
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
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&startN.compareTo(pvDecayEvents[x].puGetStartNucleus())==prsfIntZero) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			 }
    		} else {
    			System.out.println("(puGetRadiatedPowerForStartNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no particles");
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
    	double test = prsfIntZero;
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&endN.compareTo(pvDecayEvents[x].puGetEndNucleus())==prsfIntZero) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			 }
    		} else {
    			System.out.println("(puGetRadiatedPowerForEndNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no particles");
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				for (int y = prsfIntZero; y < prsfInt8760; y++) {
    					if(((pvDecayEvents[x].puGetTime())>=(start+prsfDouble3600*y))&((pvDecayEvents[x].puGetTime())<(start+prsfDouble3600*(y+prsfIntOne)))){
    						aveBucket[y] = aveBucket[y] + pvDecayEvents[x].puGetEnergy();
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEvent)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = prsfIntZero;x<prsfInt8760;x++) {
    				aveBucket[x] = aveBucket[x]/prsfDouble3600;
    				text.append((start+prsfDouble3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
    			}
    			return text.toString();
    		} else {
    			System.out.println("(puGetRadiatedPowerForOneYear) failed because this (NucleiSampleBruteForceSim) contains no (DecayEvent)s");
    			return "(puGetRadiatedPowerForOneYear) failed because this (NucleiSampleBruteForceSim) contains no (DecayEvent)s";
    		}
    	} else {
    		System.out.println ("(puGetRadiatedPowerForOneYear) failed because the supplied start time is less than zero");
    		return "(puGetRadiatedPowerForOneYear) failed because the supplied start time is less than zero";
    	}
    }

    public String puGetRadiatedPowerForOneYearForType(double start, String type) {
    	//Returns a comma delimited String with a years worth of radiated power MeV/s (one per hour) calculations starting at time (start) of only the specified (type)
    	double[] aveBucket = new double[prsfInt8760];
    	for (int x = 0; x<prsfInt8760; x++) {
    		aveBucket[x] = prsfIntZero;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForType) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForType) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetRadiatedPowerForOneYearForType) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= prsfIntZero) {
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				for (int y = prsfIntZero; y < prsfInt8760; y++) {
    					if((type.compareTo(pvDecayEvents[x].puGetType())==prsfIntZero)&((pvDecayEvents[x].puGetTime())>=(start+prsfDouble3600*y))&((pvDecayEvents[x].puGetTime())<(start+prsfDouble3600*(y+prsfIntOne)))){
    						aveBucket[y] = aveBucket[y] + pvDecayEvents[x].puGetEnergy();
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEvent)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = prsfIntZero;x<prsfInt8760;x++) {
    				aveBucket[x] = aveBucket[x]/prsfDouble3600;
    				text.append((start+prsfDouble3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
    			}
    			return text.toString();
    		} else {
    			System.out.println("(puGetRadiatedPowerForOneYearForType) failed because this (NucleiSampleBruteForceSim) contains no (DecayEvent)s");
    			return "(puGetRadiatedPowerForOneYearForType) failed because this (NucleiSampleBruteForceSim) contains no (DecayEvent)s";
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
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				for (int y = prsfIntZero; y < prsfInt8760; y++) {
    					if((startN.compareTo(pvDecayEvents[x].puGetStartNucleus())==prsfIntZero)&((pvDecayEvents[x].puGetTime())>=(start+prsfDouble3600*y))&((pvDecayEvents[x].puGetTime())<(start+prsfDouble3600*(y+prsfIntOne)))){
    						aveBucket[y] = aveBucket[y] + pvDecayEvents[x].puGetEnergy();
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEvent)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = prsfIntZero;x<prsfInt8760;x++) {
    				aveBucket[x] = aveBucket[x]/prsfDouble3600;
    				text.append((start+prsfDouble3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
    			}
    			return text.toString();
    		} else {
    			System.out.println("(puGetRadiatedPowerForOneYearForStartNucleus) failed because this (NucleiSampleBruteForceSim) contains no (DecayEvent)s");
    			return "(puGetRadiatedPowerForOneYearForStartNucleus) failed because this (NucleiSampleBruteForceSim) contains no (DecayEvent)s";
    		}
    	} else {
    		System.out.println ("(puGetRadiatedPowerForOneYearForStartNucleus) failed because the supplied start time is less than zero");
    		return "(puGetRadiatedPowerForOneYearForStartNucleus) failed because the supplied start time is less than zero";
    	}
    }

    public String puGetRadiatedPowerForOneYearForEndNucleus(double start, String endN) {
    	//Returns a comma delimited String with a years worth of radiated power MeV/s (one per hour) calculations starting at time (start) of only the specified (endN)
    	double[] aveBucket = new double[prsfInt8760];
    	for (int x = 0; x<prsfInt8760; x++) {
    		aveBucket[x] = prsfIntZero;
    	}
    	if (start < pvStartTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForEndNucleus) was supplied a start time that is earlier than (pvStartTime)!");
    	} else if (start > pvEndTime) {
    		System.out.println("Warning (puGetRadiatedPowerForOneYearForEndNucleus) was supplied a start time that is past (pvEndTime)!");
    		return "Warning (puGetRadiatedPowerForOneYearForEndNucleus) was supplied a start time that is past (pvEndTime)!";
    	}
    	if (start >= prsfIntZero) {
    		if (pvNumDecayEvents > prsfIntZero) {
    			for (int x = prsfIntZero; x < pvNumDecayEvents; x++) {
    				for (int y = prsfIntZero; y < prsfInt8760; y++) {
    					if((endN.compareTo(pvDecayEvents[x].puGetEndNucleus())==prsfIntZero)&((pvDecayEvents[x].puGetTime())>=(start+prsfDouble3600*y))&((pvDecayEvents[x].puGetTime())<(start+prsfDouble3600*(y+prsfIntOne)))){
    						aveBucket[y] = aveBucket[y] + pvDecayEvents[x].puGetEnergy();
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEvent)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = prsfIntZero;x<prsfInt8760;x++) {
    				aveBucket[x] = aveBucket[x]/prsfDouble3600;
    				text.append((start+prsfDouble3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
    			}
    			return text.toString();
    		} else {
    			System.out.println("(puGetRadiatedPowerForOneYearForEndNucleus) failed because this (NucleiSampleBruteForceSim) contains no (DecayEvent)s");
    			return "(puGetRadiatedPowerForOneYearForEndNucleus) failed because this (NucleiSampleBruteForceSim) contains no (DecayEvent)s";
    		}
    	} else {
    		System.out.println ("(puGetRadiatedPowerForOneYearForEndNucleus) failed because the supplied start time is less than zero");
    		return "(puGetRadiatedPowerForOneYearForEndNucleus) failed because the supplied start time is less than zero";
    	}
    }

}
