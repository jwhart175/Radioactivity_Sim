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

public class DecayEventSet extends DecayEvent {
	//An extension of the (DecayEvent) class meant to contain the effects (energy output, etc) of a set of (DecayEvent)
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
	 * protected double prEnergy //user supplied (DecayEvent) energy in MeV
     * protected String prType; //user supplied (DecayEvent) type (alpha, beta, gamma, neutron)
     * protected double prHalfLife //user supplied (DecayEvent) half-life in seconds
     * protected String prStartNucleus //the nucleus before the DecayEvent
     * protected String prEndNucleus //the nucleus after the DecayEvent
     * protected double prStartTime //lower bound of the time in which the (DecayEvent) may occur
     * protected double prEndTime //upper bound of the time in which the (DecayEvent) may occur
     * protected int prNumGammas = prsfIntZero; //The number of coincident gamma radiation events for this rule
	 * protected String[] prGammaName = new String[prsfIntOne]; //The names for any coincident gamma radiation events
	 * protected double[] prGammaEnergy = new double[prsfIntOne]; //The energies for any coincident gamma radiation events
	 * protected double[] prGammaIntensity = new double[prsfIntOne]; //The intensities for any coincident gamma radiation events
	 * protected int prNumBetas = prsfIntZero; //The number of coincident beta radiation events for this rule
	 * protected String[] prBetaName = new String[prsfIntOne]; //The names of the possible beta emission energies
	 * protected double[] prBetaEnergy = new double[prsfIntOne]; //The possible beta emission energies
	 * protected double[] prBetaIntensity = new double[prsfIntOne]; //The intensities for each beta emission energy
	 * protected int prNumAlphas = prsfIntZero; //The number of coincident alpha radiation events for this rule
	 * protected String[] prAlphaName = new String[prsfIntOne]; //The names of the possible alpha emission energies
	 * protected double[] prAlphaEnergy = new double[prsfIntOne]; //The possible alpha emission energies
	 * protected double[] prAlphaIntensity = new double[prsfIntOne]; //The intensities for each alpha emission energy
	 * protected int prNumNeutrons = prsfIntZero; //The number of coincident Neutron radiation events for this rule
	 * protected String[] prNeutronName = new String[prsfIntOne]; //The names of the possible Neutron emission energies
	 * protected double[] prNeutronEnergy = new double[prsfIntOne]; //The possible Neutron emission energies
	 * protected double[] prNeutronIntensity = new double[prsfIntOne]; //The intensities for each Neutron emission energy
     * public String puGetStartNucleus()
     * public void puSetRule(DecayChainRule rule)
     * public String puGetEndNucleus()
     * Overridden public double puGetTime()
     * public double puGetHalfLife()
     * public String puGetType()
     * public double puGetEnergy()
     * public void puSetStartNucleus(String start)
     * public void puSetEndNucleus(String end)
     * public void puSetHalfLife(double halflife)
     * Overridden public void puSetHalfLife(double halflife, int maxhalflives)
     * public void puSetTimeBounds(boolean isChild, double startTime, double endTime, double halflife)
     * public void puSetType(String newtype)
     * public void puSetEnergy(double energy)
     * Overridden public void puRecalculateTime(boolean bound, boolean isChild)
	 */
	private double pvNum = prsfDoubleMinusOne; //The number of particles being represented by this (EventSet)
	private boolean pvIsChild = false; //Whether or not the particles in this EventSet are child nuclei

	public DecayEventSet(){
		//empty constructor
	}

	public DecayEventSet(double num, boolean isChild, double startTime, double endTime, String start, String end, double halfLife, double energy, String type) {
		//Constructor which immediately defines all of the pertinent variables
		prStartNucleus = start;
        prEndNucleus = end;
        pvIsChild = isChild;
        if (startTime >= prsfIntZero & startTime < endTime) {
        	prStartTime = startTime;
        } else {
            System.out.println("(DecayEventSet) construction failed because (DecayEventSet) input (startTime) must be greater than or equal to zero and less than (endTime)");
        }
        if (endTime >= prsfIntZero & endTime > startTime) {
        	prEndTime = endTime;
        } else {
            System.out.println("(DecayEventSet) construction failed because (DecayEventSet) input (endTime) must be greater than or equal to zero and greaterh than (startTime)");
        }
    	if (energy > prsfIntZero) {
            prEnergy = energy;
        } else {
            System.out.println("(DecayEventSet) construction failed because (DecayEventSet) input (energy) must be a positive number and greater than zero");
        }
        prType = type;
        if (halfLife > prsfIntZero) {
            prHalfLife = halfLife;
        } else {
            System.out.println("(DecayEventSet) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (num > prsfIntZero) {
            pvNum = num;
        } else {
            System.out.println("(DecayEventSet) construction failed because input (num) must be a positive number and greater than zero");
        }
	}

	public DecayEventSet(double num, boolean isChild, double startTime, double endTime, DecayChainRule rule) {
		//Constructor which immediately defines all of the pertinent variables
		puSetRule(rule);
        pvIsChild = isChild;
        if (startTime >= prsfIntZero & startTime < endTime) {
        	prStartTime = startTime;
        } else {
            System.out.println("(DecayEventSet) construction failed because (DecayEventSet) input (startTime) must be greater than or equal to zero and less than (endTime)");
        }
        if (endTime >= prsfIntZero & endTime > startTime) {
        	prEndTime = endTime;
        } else {
            System.out.println("(DecayEventSet) construction failed because (DecayEventSet) input (endTime) must be greater than or equal to zero and greaterh than (startTime)");
        }
        if (num > prsfIntZero) {
            pvNum = num;
        } else {
            System.out.println("(DecayEventSet) construction failed because input (num) must be a positive number and greater than zero");
        }
    }

	public double puGetNumWithinTimeBounds(double startTime, double endTime){
		//returns the number of events contained in this (DecayEventSet) that are within the specified time bounds
		if (pvNum>= prsfIntZero) {
			if (startTime >= prsfIntZero & startTime < endTime & endTime >= prsfIntZero ) {
	        	if(pvIsChild){
	        		if(prStartTime < startTime) {
	        			if(prEndTime < endTime & prEndTime >= startTime) {
	        				return (pvNum*(prEndTime-startTime)/(prEndTime-prStartTime));
	        			} else if (prEndTime < startTime) {
	        				return prsfIntZero;
	        			} else if (prEndTime >= endTime) {
	        				return (pvNum*(endTime-startTime)/(prEndTime-prStartTime));
	        			}
	        			System.out.println("(puGetNumWithinTimeBounds) failed because something unexpected happened!");
	        			return prsfDoubleMinusOne;
	        		} else {
	        			if(prEndTime < endTime & startTime <= prStartTime) {
	        				return pvNum;
	        			} else if (prStartTime >= endTime) {
	        				return prsfIntZero;
	        			} else if (prEndTime >= endTime) {
	        				return pvNum*(endTime-prStartTime)/(prEndTime-prStartTime);
	        			}
	        			System.out.println("(puGetNumWithinTimeBounds) failed because something unexpected happened!");
	        			return prsfDoubleMinusOne;
	        		}
	        	} else {
	        		//N_between_two_bounds = N(0)/lambda*(e^(-upperbound*lambda)-e^(-lowerbound*lambda))
	        		//N(0) here is = pvNum
	        		//lambda is ln(2)/half-life
	        		//upperbound = 0 if prStartTime is the upper bound
	        		double lambda = prsfDoubleLN2/prHalfLife;
	        		double var = pvNum/(Math.exp(-(prStartTime)*lambda)-Math.exp(-(prEndTime)*lambda));
	        		if(prStartTime < startTime) {
	        			if(prEndTime < endTime & prEndTime >= startTime) {
	        				return (var*(Math.exp(-(startTime)*lambda)-Math.exp(-(prEndTime)*lambda)));
	        			} else if (prEndTime < startTime) {
	        				return 0;
	        			} else if (prEndTime >= endTime) {
	        				return (var*(Math.exp(-(startTime)*lambda)-Math.exp(-(endTime)*lambda)));
	        			}
	        			System.out.println("(puGetEnergyWithinTimeBounds) failed because something unexpected happened!");
	        			return prsfDoubleMinusOne;
	        		} else {
	        			if(prEndTime < endTime & startTime <= prStartTime) {
	        				return (pvNum);
	        			} else if (prStartTime >= endTime) {
	        				return prsfIntZero;
	        			} else if (prEndTime >= endTime) {
	        				return (var*(Math.exp(-(prStartTime)*lambda)-Math.exp(-(endTime)*lambda)));
	        			}
	        			System.out.println("(puGetNumWithinTimeBounds) failed because something unexpected happened!");
	        			return prsfDoubleMinusOne;
	        		}
	        	}
	        } else {
	            System.out.println("(puGetNumWithinTimeBounds) failed because the inputs (startTime) and (endTime) must be greater than or equal to zero and (startTime) must be less than (endTime)");
	            return prsfDoubleMinusOne;
	        }
		} else {
			System.out.println("(puGetNumWithinTimeBounds) failed because either (pvNum) or (prEnergy) is less than or equal to zero!");
			return prsfDoubleMinusOne;
		}
	}

	public double puGetEnergyWithinTimeBounds(double startTime, double endTime){
		//returns any energy contained in this (DecayEventSet) in MeV within the specified time bounds
		return (prEnergy*puGetNumWithinTimeBounds(startTime,endTime));
	}

	public double puGetStartTime(){
		//returns the (prStartTime) of this (DecayEventSet)
		if (prStartTime > prsfIntZero){
			return prStartTime;
		} else {
			System.out.println("(puGetStartTime) failed because (prStartTime) is less than or equal to zero!");
			return prsfDoubleMinusOne;
		}
	}

	public double puGetEndTime(){
		//returns the (prEndTime) of this (DecayEventSet)
		if (prEndTime > prsfIntZero){
			return prEndTime;
		} else {
			System.out.println("(puGetEndTime) failed because (prEndTime) is less than or equal to zero!");
			return prsfDoubleMinusOne;
		}
	}

	public double puGetTotalEnergy(){
		//returns the total amount of energy contained in this (DecayEventSet) in MeV
		if (pvNum*prEnergy >= prsfIntZero) {
			return (pvNum*prEnergy);
		} else {
			System.out.println("(puGetTotalEnergy) failed because either (pvNum) or (prEnergy) is less than or equal to zero!");
			return prsfDoubleMinusOne;
		}
	}

	public void puSetNum(double num){
		//sets the number of particles represented by this (DecayEventSet)
		if (num > prsfIntZero) {
            pvNum = num;
        } else {
            System.out.println("(puSetNum) construction failed because input (num) must be a positive number and greater than zero");
        }
	}

	public double puGetTotalNum(){
		//returns the number of particles represented by this (DecayEventSet)
		if (pvNum > prsfIntZero) {
			return pvNum;
		} else {
			System.out.println("(puGetNum) failed because the (pvNum) has not been set.");
			return prsfDoubleMinusOne;
		}
	}

	public void puSetIsChild(boolean isChild){
		//sets the child or not child state of this (DecayEventSet)
		pvIsChild = isChild;
	}

	public boolean puGetIsChild(){
		//returns the answer to the question "is this DecayEventSet for a child nucleus"
		return pvIsChild;
	}

	public void puRecalculateTime(){
		//Hides the (puRecalculateTime) function from the super class
	}
	public void puSetHalfLife(double halflife, int maxhalflives) {
    	//Hides the (puSetHalfLife) function from the super class
	}
    public double puGetTime() {
    	//Hides the (puGetTime) function from the super class
    	return prsfDoubleMinusOne;
    }
}
