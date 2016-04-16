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
	 * protected double prEnergy //user supplied (DecayEvent) energy in MeV
     * protected String prType; //user supplied (DecayEvent) type (alpha, beta, gamma, neutron)
     * protected double prHalfLife //user supplied (DecayEvent) half-life in seconds
     * protected String prStartNucleus //the nucleus before the DecayEvent
     * protected String prEndNucleus //the nucleus after the DecayEvent
     * protected double prStartTime //lower bound of the time in which the (DecayEvent) may occur
     * protected double prEndTime //upper bound of the time in which the (DecayEvent) may occur
     * public String puGetStartNucleus()
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
	private double pvNum = -1.0; //The number of particles being represented by this (EventSet)
	private boolean pvIsChild = false; //Whether or not the particles in this EventSet are child nuclei

	public DecayEventSet(){
		//empty constructor
	}

	public DecayEventSet(double num, boolean isChild, double startTime, double endTime, String start, String end, double halfLife, double energy, String type) {
		//Constructor which immediately defines all of the pertinent variables
		prStartNucleus = start;
        prEndNucleus = end;
        pvIsChild = isChild;
        if (startTime >= 0 & startTime < endTime) {
        	prStartTime = startTime;
        } else {
            System.out.println("(DecayEventSet) construction failed because (DecayEventSet) input (startTime) must be greater than or equal to zero and less than (endTime)");
        }
        if (endTime >= 0 & endTime > startTime) {
        	prEndTime = endTime;
        } else {
            System.out.println("(DecayEventSet) construction failed because (DecayEventSet) input (endTime) must be greater than or equal to zero and greaterh than (startTime)");
        }
    	if (energy > 0) {
            prEnergy = energy;
        } else {
            System.out.println("(DecayEventSet) construction failed because (DecayEventSet) input (energy) must be a positive number and greater than zero");
        }
        prType = type;
        if (halfLife > 0) {
            prHalfLife = halfLife;
        } else {
            System.out.println("(DecayEventSet) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (num > 0) {
            pvNum = num;
        } else {
            System.out.println("(DecayEventSet) construction failed because input (num) must be a positive number and greater than zero");
        }

	}

	public double puGetNumWithinTimeBounds(double startTime, double endTime){
		//returns the number of events contained in this (DecayEventSet) that are within the specified time bounds
		if (pvNum>= 0) {
			if (startTime >= 0 & startTime < endTime & endTime >= 0 ) {
	        	if(pvIsChild){
	        		if(prStartTime < startTime) {
	        			if(prEndTime < endTime & prEndTime >= startTime) {
	        				return (pvNum*(prEndTime-startTime)/(prEndTime-prStartTime));
	        			} else if (prEndTime < startTime) {
	        				return 0;
	        			} else if (prEndTime >= endTime) {
	        				return (pvNum*(endTime-startTime)/(prEndTime-prStartTime));
	        			}
	        			System.out.println("(puGetNumWithinTimeBounds) failed because something unexpected happened!");
	        			return -1.0;
	        		} else {
	        			if(prEndTime < endTime & prEndTime >= prStartTime) {
	        				return pvNum;
	        			} else if (prStartTime >= endTime) {
	        				return 0;
	        			} else if (prEndTime >= endTime) {
	        				return pvNum*(endTime-prStartTime)/(prEndTime-prStartTime);
	        			}
	        			System.out.println("(puGetNumWithinTimeBounds) failed because something unexpected happened!");
	        			return -1.0;
	        		}
	        	} else {
	        		//N_between_two_bounds = N(0)/lambda*(e^(-upperbound*lambda)-e^(-lowerbound*lambda))
	        		//N(0) here is = pvNum
	        		//lambda is ln(2)/half-life
	        		//upperbound = 0 if prStartTime is the upper bound
	        		double lambda = Math.log(2)/prHalfLife;
	        		if(prStartTime < startTime) {
	        			if(prEndTime < endTime & prEndTime >= startTime) {
	        				return pvNum/lambda*(Math.exp(-(startTime-prStartTime)*lambda)-Math.exp(-(prEndTime-prStartTime)*lambda));
	        			} else if (prEndTime < startTime) {
	        				return 0;
	        			} else if (prEndTime >= endTime) {
	        				return pvNum/lambda*(Math.exp(-(startTime-prStartTime)*lambda)-Math.exp(-(endTime-prStartTime)*lambda));
	        			}
	        			System.out.println("(puGetNumWithinTimeBounds) failed because something unexpected happened!");
	        			return -1.0;
	        		} else {
	        			if(prEndTime < endTime & prEndTime >= prStartTime) {
	        				return pvNum;
	        			} else if (prStartTime >= endTime) {
	        				return 0;
	        			} else if (prEndTime >= endTime) {
	        				return pvNum/lambda*(1-Math.exp(-(endTime-prStartTime)*lambda));
	        			}
	        			System.out.println("(puGetNumWithinTimeBounds) failed because something unexpected happened!");
	        			return -1.0;
	        		}
	        	}
	        } else {
	            System.out.println("(puGetNumWithinTimeBounds) failed because the inputs (startTime) and (endTime) must be greater than or equal to zero and (startTime) must be less than (endTime)");
	            return -1.0;
	        }
		} else {
			System.out.println("(puGetNumWithinTimeBounds) failed because either (pvNum) or (prEnergy) is less than or equal to zero!");
			return -1.0;
		}
	}

	public double puGetEnergyWithinTimeBounds(double startTime, double endTime){
		//returns any energy contained in this (DecayEventSet) in MeV within the specified time bounds
		if (pvNum*prEnergy >= 0) {
			if (startTime >= 0 & startTime < endTime & endTime >= 0 ) {
	        	if(pvIsChild){
	        		if(prStartTime < startTime) {
	        			if(prEndTime < endTime & prEndTime >= startTime) {
	        				return (puGetTotalEnergy()*(prEndTime-startTime)/(prEndTime-prStartTime));
	        			} else if (prEndTime < startTime) {
	        				return 0;
	        			} else if (prEndTime >= endTime) {
	        				return (puGetTotalEnergy()*(endTime-startTime)/(prEndTime-prStartTime));
	        			}
	        		} else {
	        			if(prEndTime < endTime & prEndTime >= prStartTime) {
	        				return (puGetTotalEnergy());
	        			} else if (prStartTime >= endTime) {
	        				return 0;
	        			} else if (prEndTime >= endTime) {
	        				return (puGetTotalEnergy()*(endTime-prStartTime)/(prEndTime-prStartTime));
	        			}
	        		}
	        	} else {
	        		//N_between_two_bounds = N(0)/lambda*(e^(-upperbound*lambda)-e^(-lowerbound*lambda))
	        		//N(0) here is = pvNum
	        		//lambda is ln(2)/half-life
	        		//upperbound = 0 if prStartTime is the upper bound
	        		double lambda = Math.log(2)/prHalfLife;
	        		double var = pvNum/(Math.exp(-(prStartTime)*lambda)-Math.exp(-(prEndTime)*lambda));
	        		if(prStartTime < startTime) {
	        			if(prEndTime < endTime & prEndTime >= startTime) {
	        				return (prEnergy*var*(Math.exp(-(startTime-prStartTime)*lambda)-Math.exp(-(prEndTime-prStartTime)*lambda)));
	        			} else if (prEndTime < startTime) {
	        				return 0;
	        			} else if (prEndTime >= endTime) {
	        				return (prEnergy*var*(Math.exp(-(startTime-prStartTime)*lambda)-Math.exp(-(endTime-prStartTime)*lambda)));
	        			}
	        			System.out.println("(puGetEnergyWithinTimeBounds) failed because something unexpected happened!");
	        			return -1.0;
	        		} else {
	        			if(prEndTime <= endTime & prEndTime >= prStartTime) {
	        				return (puGetTotalEnergy());
	        			} else if (prStartTime >= endTime) {
	        				return 0;
	        			} else if (prEndTime > endTime) {
	        				return (prEnergy*var*(1-Math.exp(-(endTime-prStartTime)*lambda)));
	        			}
	        			System.out.println("(puGetEnergyWithinTimeBounds) failed because something unexpected happened!");
	        			return -1.0;
	        		}
	        	}
	        } else {
	            System.out.println("(puGetEnergyWithinTimeBounds) failed because the inputs (startTime) and (endTime) must be greater than or equal to zero and (startTime) must be less than (endTime)");
	            return -1.0;
	        }
		} else {
			System.out.println("(puGetEnergyWithinTimeBounds) failed because either (pvNum) or (prEnergy) is less than or equal to zero!");
			return -1.0;
		}
		System.out.println("(puGetEnergyWithinTimeBounds) failed because something unexpected happened!");
		return -1.0;
	}

	public double puGetStartTime(){
		//returns the (prStartTime) of this (DecayEventSet)
		if (prStartTime > 0){
			return prStartTime;
		} else {
			System.out.println("(puGetStartTime) failed because (prStartTime) is less than or equal to zero!");
			return -1.0;
		}
	}

	public double puGetEndTime(){
		//returns the (prEndTime) of this (DecayEventSet)
		if (prEndTime > 0){
			return prEndTime;
		} else {
			System.out.println("(puGetEndTime) failed because (prEndTime) is less than or equal to zero!");
			return -1.0;
		}
	}

	public double puGetTotalEnergy(){
		//returns the total amount of energy contained in this (DecayEventSet) in MeV
		if (pvNum*prEnergy >= 0) {
			return (pvNum*prEnergy);
		} else {
			System.out.println("(puGetTotalEnergy) failed because either (pvNum) or (prEnergy) is less than or equal to zero!");
			return -1.0;
		}
	}

	public void puSetNum(double num){
		//sets the number of particles represented by this (DecayEventSet)
		if (num > 0) {
            pvNum = num;
        } else {
            System.out.println("(puSetNum) construction failed because input (num) must be a positive number and greater than zero");
        }
	}

	public double puGetTotalNum(){
		//returns the number of particles represented by this (DecayEventSet)
		if (pvNum > 0) {
			return pvNum;
		} else {
			System.out.println("(puGetNum) failed because the (pvNum) has not been set.");
			return -1.0;
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

	public void puRecalculateTime(boolean bound, boolean isChild){
		//Hides the (puRecalculateTime) function from the super class
	}
	public void puSetHalfLife(double halflife, int maxhalflives) {
    	//Hides the (puSetHalfLife) function from the super class
	}
    public double puGetTime() {
    	//Hides the (puGetTime) function from the super class
    	return -1.0;
    }
}
