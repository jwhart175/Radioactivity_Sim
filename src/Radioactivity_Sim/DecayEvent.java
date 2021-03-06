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

import java.security.SecureRandom;
import java.util.Random;


public class DecayEvent extends DecayChainRule {
    // A (DecayEvent) calculates and contains a radioactive event
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
	 * protected String prStartNucleus; //The starting nucleus of each rule
	 * protected String prEndNucleus; //The ending nucleus of each rule
	 * protected String prType; //The radiation type of each rule
	 * protected double prEnergy = prsfDoubleMinusOne;; //The energy quantity for each rule
	 * protected double prHalfLife = prsfDoubleMinusOne;; //The half-life for each rule
	 * protected double prProbability = prsfDoubleMinusOne; //The probability of each rule
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
	 * public void puSetRule(DecayChainRule rule)
	 */

    private double pvTime = prsfDoubleMinusOne; //calculated time of the (DecayEvent) referenced from arbitrary start point in seconds
    private int pvMaxHalfLives = prsfIntEighty;
    private double pvTimeOffset = prsfDoubleZero; //user supplied offset time in seconds
    protected double prStartTime = prsfDoubleMinusOne; //lower bound of the time in which the (DecayEvent) may occur
    protected double prEndTime = prsfDoubleMinusOne; //upper bound of the time in which the (DecayEvent) may occur

    public DecayEvent(){
    	//empty constructor
    }

    public DecayEvent(String start, String end, double halfLife, double energy, String type) {
    	//Simple (DecayEvent) constructor that calculates the time that the event occurs based on the supplied half-life
        prStartNucleus = start;
        prEndNucleus = end;
    	if (energy > prsfIntZero) {
            prEnergy = energy;
        } else {
            System.out.println("(DecayEvent) construction failed because (DecayEvent) input (energy) must be a positive number and greater than zero");
        }
        prType = type;
        if (halfLife > prsfIntZero) {
            prHalfLife = halfLife;
        } else {
            System.out.println("(DecayEvent) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (prHalfLife > prsfIntZero) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(DecayEvent) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvMaxHalfLives = prsfIntEighty;
        pvTimeOffset = prsfDoubleZero;
    }
    public DecayEvent(String start, String end, double halfLife, double energy, String type,double timeOffset) {
    	//Simple (DecayEvent) constructor that calculates the time that the event occurs based on the supplied half-life offset with the supplied time
    	prStartNucleus = start;
        prEndNucleus = end;
    	if (timeOffset >= prsfIntZero) {
            pvTimeOffset = timeOffset;
        } else {
            System.out.println("(DecayEvent) construction failed because (timeOffset) input must be a positive number and greater than or equal to zero");
        }
        if (energy > prsfIntZero) {
            prEnergy = energy;
        } else {
            System.out.println("(DecayEvent) construction failed because (DecayEvent) input (energy) must be a positive number and greater than zero");
        }
        prType = type;
        if (halfLife > prsfIntZero) {
            prHalfLife = halfLife;
        } else {
            System.out.println("(DecayEvent) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (prHalfLife > prsfIntZero) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(DecayEvent) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvMaxHalfLives = prsfIntEighty;
    }
    public DecayEvent(String start, String end, double halfLife, double energy, String type, int maxHalfLives) {
    	//Simple (DecayEvent) constructor that calculates the time that the event occurs based on the supplied half-life that includes a variable to modify the maximum number of half lives to search through (default is 20)
    	prStartNucleus = start;
        prEndNucleus = end;
    	if (energy > prsfIntZero) {
            prEnergy = energy;
        } else {
            System.out.println("(DecayEvent) construction failed because (DecayEvent) input (energy) must be a positive number and greater than zero");
        }
        prType = type;
        if (halfLife > prsfIntZero) {
            prHalfLife = halfLife;
        } else {
            System.out.println("(DecayEvent) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (maxHalfLives > prsfIntZero) {
            pvMaxHalfLives = maxHalfLives;
        } else {
            System.out.println("Using default (pvMaxHalfLives) of 20 because the (maxHalfLives) supplied to the constructor was not a positive number and greater than zero");
            pvMaxHalfLives = prsfIntEighty;
        }
        if (prHalfLife > prsfIntZero) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(DecayEvent) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvTimeOffset = prsfDoubleZero;

    }
    public DecayEvent(String start, String end, double halfLife, double energy, String type,double timeOffset, int maxHalfLives) {
    	//Simple (DecayEvent) constructor that calculates the time that the event occurs based on the supplied half-life that includes a variable to modify the maximum number of half lives to search through (default is 20) and add a time offset
    	prStartNucleus = start;
        prEndNucleus = end;
    	if (timeOffset >= prsfIntZero) {
            pvTimeOffset = timeOffset;
        } else {
            System.out.println("(DecayEvent) construction failed because (timeOffset) input must be a positive number and greater than or equal to zero");
        }
        if (energy > prsfIntZero) {
            prEnergy = energy;
        } else {
            System.out.println("(DecayEvent) construction failed because (DecayEvent) input (energy) must be a positive number and greater than zero");
        }
        prType = type;
        if (halfLife > prsfIntZero) {
            prHalfLife = halfLife;
        } else {
            System.out.println("(DecayEvent) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (maxHalfLives > prsfIntZero) {
            pvMaxHalfLives = maxHalfLives;
        } else {
            System.out.println("Using default (pvMaxHalfLives) of 20 because the (maxHalfLives) supplied to the constructor was not a positive number and greater than zero");
            pvMaxHalfLives = prsfIntEighty;
        }
        if (prHalfLife > prsfIntZero) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(DecayEvent) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
    }

    public DecayEvent(double timeOffset, DecayChainRule rule) {
    	//Simple (DecayEvent) constructor that calculates the time that the event occurs based on the supplied half-life that includes a variable to modify the maximum number of half lives to search through (default is 20) and add a time offset
        puSetRule(rule);
        if (timeOffset >= prsfIntZero) {
            pvTimeOffset = timeOffset;
        } else {
            System.out.println("(DecayEvent) construction failed because (timeOffset) input must be a positive number and greater than or equal to zero");
        }
        if (prHalfLife > prsfIntZero) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(DecayEvent) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
    }

    public void puRecalculateTime(){
    	//recalculates the (DecayEvent) time, provided in case the (DecayEvent) is initialized with the blank constructor
    	if(prHalfLife>prsfIntZero){
    		if(pvMaxHalfLives>prsfIntZero){
    			double t = pvCalcTime();
    			if(t>prsfIntZero){
    				pvTime = t;
    			} else {
    				System.out.println("(puRecalculateTime) failed because (pvTimeOffset) is less than zero!");
    				pvTime = prsfDoubleMinusOne;
    			}
    		} else {
				System.out.println("(puRecalculateTime) failed because (pvMaxHalfLives) is less than zero!");
				pvTime = prsfDoubleMinusOne;
			}
    	} else {
			System.out.println("(puRecalculateTime) failed because (prHalfLife) is less than zero!");
			pvTime = prsfDoubleMinusOne;
		}
   	}

    private double pvCalcTime() {
    	//Calculates the (DecayEvent) time with a time offset for a maximum number of half-lives
    	SecureRandom tree = new SecureRandom();
        double seed1 = tree.nextDouble();
        double seed2 = tree.nextDouble();
        double seed3 = tree.nextDouble();
        double weightedLife = prsfDoubleHundred;
        double testWeightedLife = seed1 * weightedLife;
        double testDetailedLife = seed2 * prsfDoubleFiveHundredThousand;
        double t = prsfDoubleZero;
        double x = prsfDoubleZero;
        while (t == prsfDoubleZero) {
            if (x == (pvMaxHalfLives-prsfIntOne)) {
                for (int y = prsfIntZero; y<prsfDoubleThousand;y++) {
                    if (testDetailedLife <= prsfDetailedTest[y]){
                        t = (x+(y+seed3)/prsfDoubleThousand)*prHalfLife;
                        break;
                    }
                }
            } else if (testWeightedLife <= weightedLife*(prsfDoubleOne+(x*prsfDoubleTwo))/Math.pow(prsfDoubleTwo,x+prsfDoubleOne)){
                for (int y = prsfIntZero; y<prsfDoubleThousand;y++) {
                    if (testDetailedLife <= prsfDetailedTest[y]){
                        t = (x+(y+seed3)/prsfDoubleThousand)*prHalfLife;
                        break;
                    }
                }
            }
            x++;
        }

        return (t+pvTimeOffset);
    }

    public double puGetTimeOffset(){
    	//returns the (pvTimeOffset) of this (DecayEvent)
    	return pvTimeOffset;
    }

    public double puGetTime() {
    	//returns the occurrence time of this (DecayEvent)
        if (pvTime > prsfIntZero) {
            return pvTime;
        } else {
            System.out.println("(puGetTime) failed because this (DecayEvent) does not have a calculated (pvTime)");
            return prsfDoubleMinusOne;
        }
    }

    public void puSetStartNucleus(String start) {
    	//sets the start nucleus of this (DecayEvent)
    	prStartNucleus = start;
    }

    public void puSetEndNucleus(String end) {
    	//sets the end nucleus of this (DecayEvent)
    	prEndNucleus = end;
    }

    public void puSetHalfLife(double halflife) {
    	//sets the half-life of this (DecayEvent)
        if (halflife > prsfIntZero) {
            prHalfLife = halflife;
        } else {
            System.out.println("(puSetHalfLife) failed because the (halflife) provided is not greater than zero");
            prHalfLife = prsfDoubleMinusOne;
        }
    }

    public void puSetHalfLife(double halflife, int maxhalflives) {
    	//sets the half-life of this (DecayEvent) and adjusts the maximum number of half-lives for the unbound time calculation
        if (halflife > prsfIntZero) {
            prHalfLife = halflife;
            pvTime = prsfDoubleMinusOne;
            if (maxhalflives > prsfIntZero) {
                pvMaxHalfLives = maxhalflives;
            } else {
                System.out.println("(puSetHalfLife) failed because the (maxhalflives) provided is not greater than zero");
                pvMaxHalfLives = prsfIntZero;
            }
        } else {
            System.out.println("(puSetHalfLife) failed because the (halflife) provided is not greater than zero");
            prHalfLife = prsfDoubleMinusOne;
        }
    }

    public void puSetTimeBounds(boolean isChild, double startTime, double endTime, double halflife) {
    	//sets the time bounds and half-life of this (DecayEvent)
        if (halflife > prsfIntZero) {
            prHalfLife = halflife;
        } else {
            System.out.println("(puSetTimeBounds) failed because the (halflife) provided is not greater than zero");
            prHalfLife = prsfDoubleMinusOne;
        }
        if (startTime >= prsfIntZero & startTime < endTime) {
        	prStartTime = startTime;
        } else {
        	prStartTime = prsfDoubleMinusOne;
            System.out.println("(puSetTimeBounds) failed because (DecayEvent) input (startTime) must be greater than or equal to zero and less than (endTime)");
        }
        if (endTime >= prsfIntZero & endTime > startTime) {
        	prEndTime = endTime;
        } else {
        	prEndTime = prsfDoubleMinusOne;
            System.out.println("(puSetTimeBounds) failed because (DecayEvent) input (endTime) must be greater than or equal to zero and greaterh than (startTime)");
        }
    }

    public void puSetType(String newtype) {
    	//sets the radiation type of this (DecayEvent)
        prType = newtype;
    }

    public void puSetEnergy(double energy) {
    	//sets the energy of this (DecayEvent)
        if (energy > prsfIntZero) {
            prEnergy = energy;
        } else {
            System.out.println("(puSetEnergy) failed because the (energy) provided is not greater than zero");
            prEnergy = prsfDoubleMinusOne;
        }
    }

}