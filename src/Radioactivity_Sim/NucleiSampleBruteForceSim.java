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

public class NucleiSampleBruteForceSim {
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
    private double[] pvFinalPartNum = new double[1];
    private String[] pvNuclei = new String[1];

    public NucleiSampleBruteForceSim(int num,String input, double start, double end) {
    	//Constructor for a sample containing a number of nuclei of a single type
        if (num > 0) {
            pvRules = new DecayChainRuleSet[1];
            pvRules[0] = new DecayChainRuleSet(input);
            pvNumDecayChainRuleSets = 1;
            pvStartTime = start;
            pvEndTime = end;
            pvDecayEventCalc(num,pvRules[0]);
        } else {
            System.out.println("Construction of this (NucleiSampleBruteForceSim) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSampleBruteForceSim(long num,String input, double start, double end) {
    	//Constructor for a sample containing a number of nuclei of a single type
        if (num > 0) {
            pvRules = new DecayChainRuleSet[1];
            pvRules[0] = new DecayChainRuleSet(input);
            pvNumDecayChainRuleSets = 1;
            pvStartTime = start;
            pvEndTime = end;
            pvDecayEventCalcLong(num, pvRules[0]);
        } else {
            System.out.println("Construction of this (NucleiSampleBruteForceSim) failed because the (num) supplied was not greater than zero");
        }
    }

    public NucleiSampleBruteForceSim() {
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
    		String errString = "(puGetDecayChainRuleSet) failed because the supplied (index) is greater than the total of number of (DecayChainRuleSet) objects in this (NucleiSampleBruteForceSim)!";
    		System.out.println(errString);
    		return new DecayChainRuleSet();
    	} else {
    		return pvRules[index];
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
    		pvDecayEventCalc(num, pvRules[pvNumDecayChainRuleSets-1]);
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
    		pvDecayEventCalcLong(num, pvRules[pvNumDecayChainRuleSets-1]);
        } else {
            System.out.println("(puAddSpecies) failed because the input (num) supplied was not greater than zero");
        }
    }

    private void pvDecayEventCalc(int num, DecayChainRuleSet rules) {
    	//Function that calculates an integer (num) DecayEvents based on the DecayChainRuleSet (rules)
    	int numRules = rules.puGetNumRules();
    	//Calculate Number of DecayEvents
        String startNucleus = "";
    	double timeOffset = 0;
    	for(int x = 0; x<num; x++) {
    		timeOffset = 0;
    		startNucleus = rules.puGetStartNucleus(0);
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

    private void pvDecayEventCalcLong(long num, DecayChainRuleSet rules) {
    	//Function that calculates an integer (num) DecayEvents based on the DecayChainRuleSet (rules)
    	int numRules = rules.puGetNumRules();
    	//Calculate Number of DecayEvents
    	String startNucleus = "";
    	double timeOffset = 0;
    	for(long x = 0; x<num; x++) {
    		timeOffset = 0;
    		startNucleus = rules.puGetStartNucleus(0);
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
    	//Adds a (DecayEvent) to this (NucleiSampleBruteForceSim)
    	DecayEvent[] DecayEvents = new DecayEvent[pvNumDecayEvents+1];
    	for(int x = 0; x < pvNumDecayEvents; x++) {
    		DecayEvents[x]=pvDecayEvents[x];
    	}
    	DecayEvents[pvNumDecayEvents] = instance;
    	pvDecayEvents = DecayEvents;
    	pvNumDecayEvents++;
    }

    public double[][] puGetAllDecayEventEnergyAndTime() {
    	//returns all calculated (DecayEvent) energies and times
    	if (pvNumDecayEvents > 0) {
        	double returned[][] = new double[pvNumDecayEvents][2];
        	for (int x = 0;x<pvNumDecayEvents;x++) {
        		returned[x][0]=pvDecayEvents[x].puGetTime();
        		returned[x][1]=pvDecayEvents[x].puGetEnergy();
        	}
        	return returned;
    	} else {
    		System.out.println("(puGetEnergyAndTime) failed because this (NucleiSampleBruteForceSim) contain no DecayEvents");
    		double returned[][] = new double[1][1];
    		return returned;
    	}
    }

    public String puGetAllDecayEventData() {
    	//Returns a complete set of this (NucleiSampleBruteForceSims)'s (DecayEvent) data
    	if (pvNumDecayEvents > 0) {
        	StringBuilder text = new StringBuilder();
        	text.append("Occurence_Time(s),StartingNucleus,EndingNucleus,Type,Energy(MeV)" + System.getProperty("line.separator"));
        	for (int x = 0;x<pvNumDecayEvents;x++) {
        		text.append(pvDecayEvents[x].puGetTime() + "," + pvDecayEvents[x].puGetStartNucleus() + "," + pvDecayEvents[x].puGetEndNucleus() + "," + pvDecayEvents[x].puGetType() + "," + pvDecayEvents[x].puGetEnergy() + System.getProperty("line.separator"));
        	}
        	return text.toString();
    	} else {
    		System.out.println("(puGetAllDecayEventData) failed because this (NucleiSampleBruteForceSim) contain no DecayEvents");
    		return "";
    	}
    }

    public double puGetEnergySumForTypeOverTimeRange(double start, double end, String type) {
    	//Returns the total energy in MeV for the supplied (type) that was radiated between the user supplied (start) and (end) times
    	double sum = 0;
    	double test = 0;
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&type.compareTo(pvDecayEvents[x].puGetType())==0) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}

    			}
    		} else {
    			System.out.println("(puGetEnergySumForTypeOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEvent)s");
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
    	double test = 0;
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&startN.compareTo(pvDecayEvents[x].puGetStartNucleus())==0) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}

    			}
    		} else {
    			System.out.println("(puGetEnergySumForStartingNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEvent)s");
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
    	double test = 0;
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&endN.compareTo(pvDecayEvents[x].puGetEndNucleus())==0) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}

    			}
    		} else {
    			System.out.println("(puGetEnergySumForEndingNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEvent)s");
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
    			System.out.println("(puGetEnergySumOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no (DecayEvent)s");
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
    	double test = 0;
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			 }
    		} else {
    			System.out.println("(puGetRadiatedPowerOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no particles");
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
    	double test = 0;
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&type.compareTo(pvDecayEvents[x].puGetType())==0) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			 }
    		} else {
    			System.out.println("(puGetRadiatedPowerForTypeOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no particles");
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
    	double test = 0;
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&startN.compareTo(pvDecayEvents[x].puGetStartNucleus())==0) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			 }
    		} else {
    			System.out.println("(puGetRadiatedPowerForStartNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no particles");
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
    	double test = 0;
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				test = pvDecayEvents[x].puGetTime();
    				if (test>=start&test<end&endN.compareTo(pvDecayEvents[x].puGetEndNucleus())==0) {
    					sum += pvDecayEvents[x].puGetEnergy();
    				}
    			 }
    		} else {
    			System.out.println("(puGetRadiatedPowerForEndNucleusOverTimeRange) failed because this (NucleiSampleBruteForceSim) contain no particles");
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				for (int y = 0; y < 365*24; y++) {
    					if(((pvDecayEvents[x].puGetTime())>=(start+3600*y))&((pvDecayEvents[x].puGetTime())<(start+3600*(y+1)))){
    						aveBucket[y] = aveBucket[y] + pvDecayEvents[x].puGetEnergy();
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEvent)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = 0;x<365*24;x++) {
    				aveBucket[x] = aveBucket[x]/3600;
    				text.append((start+3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				for (int y = 0; y < 365*24; y++) {
    					if((type.compareTo(pvDecayEvents[x].puGetType())==0)&((pvDecayEvents[x].puGetTime())>=(start+3600*y))&((pvDecayEvents[x].puGetTime())<(start+3600*(y+1)))){
    						aveBucket[y] = aveBucket[y] + pvDecayEvents[x].puGetEnergy();
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEvent)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = 0;x<365*24;x++) {
    				aveBucket[x] = aveBucket[x]/3600;
    				text.append((start+3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				for (int y = 0; y < 365*24; y++) {
    					if((startN.compareTo(pvDecayEvents[x].puGetStartNucleus())==0)&((pvDecayEvents[x].puGetTime())>=(start+3600*y))&((pvDecayEvents[x].puGetTime())<(start+3600*(y+1)))){
    						aveBucket[y] = aveBucket[y] + pvDecayEvents[x].puGetEnergy();
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEvent)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = 0;x<365*24;x++) {
    				aveBucket[x] = aveBucket[x]/3600;
    				text.append((start+3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
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
    		if (pvNumDecayEvents > 0) {
    			for (int x = 0; x < pvNumDecayEvents; x++) {
    				for (int y = 0; y < 365*24; y++) {
    					if((endN.compareTo(pvDecayEvents[x].puGetEndNucleus())==0)&((pvDecayEvents[x].puGetTime())>=(start+3600*y))&((pvDecayEvents[x].puGetTime())<(start+3600*(y+1)))){
    						aveBucket[y] = aveBucket[y] + pvDecayEvents[x].puGetEnergy();
    					}
    				}
    			}
    			StringBuilder text = new StringBuilder();
    			text.append("Averages are of the (DecayEvent)s occurring between each of the Times below and the next one" + System.getProperty("line.separator"));
    			text.append("Time,AverageEnergy(MeV/s)" + System.getProperty("line.separator"));
    			for (int x = 0;x<365*24;x++) {
    				aveBucket[x] = aveBucket[x]/3600;
    				text.append((start+3600*x) + "," + aveBucket[x] + System.getProperty("line.separator"));
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
