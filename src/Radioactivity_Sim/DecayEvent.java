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


public class DecayEvent {
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
    private double pvTime = -1.0; //calculated time of the (DecayEvent) referenced from arbitrary start point in seconds
    private int pvMaxHalfLives = 80;
    private double pvTimeOffset = 0.0; //user supplied offset time in seconds
    protected double prEnergy = -1.0; //user supplied (DecayEvent) energy in MeV
    protected String prType; //user supplied (DecayEvent) type (alpha, beta, gamma, neutron)
    protected double prHalfLife = -1.0; //user supplied (DecayEvent) half-life in seconds
    protected String prStartNucleus = ""; //the nucleus before the DecayEvent
    protected String prEndNucleus = ""; //the nucleus after the DecayEvent
    protected double prStartTime = -1.0; //lower bound of the time in which the (DecayEvent) may occur
    protected double prEndTime = -1.0; //upper bound of the time in which the (DecayEvent) may occur
    //pvDetailedTest is a predefined sieve to reduce error in the unbound time calculation
    private static final double pvDetailedTest[] = {
            1077,2143,3197,4240,
            5271,6292,7301,8300,
            9287,10265,11231,12187,
            13133,14069,14995,15910,
            16816,17712,18599,19475,
            20343,21201,22050,22889,
            23720,24542,25355,26159,
            26954,27741,28519,29289,
            30051,30805,31550,32287,
            33017,33738,34452,35158,
            35856,36547,37231,37907,
            38576,39238,39892,40540,
            41180,41814,42441,43061,
            43674,44281,44881,45475,
            46062,46643,47218,47786,
            48349,48905,49456,50000};

    public DecayEvent(){
    	//empty constructor
    }

    public DecayEvent(String start, String end, double halfLife, double energy, String type) {
    	//Simple (DecayEvent) constructor that calculates the time that the event occurs based on the supplied half-life
        prStartNucleus = start;
        prEndNucleus = end;
    	if (energy > 0) {
            prEnergy = energy;
        } else {
            System.out.println("(DecayEvent) construction failed because (DecayEvent) input (energy) must be a positive number and greater than zero");
        }
        prType = type;
        if (halfLife > 0) {
            prHalfLife = halfLife;
        } else {
            System.out.println("(DecayEvent) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (prHalfLife > 0) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(DecayEvent) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvMaxHalfLives = 80;
        pvTimeOffset = 0.0;
    }
    public DecayEvent(String start, String end, double halfLife, double energy, String type,double timeOffset) {
    	//Simple (DecayEvent) constructor that calculates the time that the event occurs based on the supplied half-life offset with the supplied time
    	prStartNucleus = start;
        prEndNucleus = end;
    	if (timeOffset >= 0) {
            pvTimeOffset = timeOffset;
        } else {
            System.out.println("(DecayEvent) construction failed because (timeOffset) input must be a positive number and greater than or equal to zero");
        }
        if (energy > 0) {
            prEnergy = energy;
        } else {
            System.out.println("(DecayEvent) construction failed because (DecayEvent) input (energy) must be a positive number and greater than zero");
        }
        prType = type;
        if (halfLife > 0) {
            prHalfLife = halfLife;
        } else {
            System.out.println("(DecayEvent) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (prHalfLife > 0) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(DecayEvent) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvMaxHalfLives = 80;
    }
    public DecayEvent(String start, String end, double halfLife, double energy, String type, int maxHalfLives) {
    	//Simple (DecayEvent) constructor that calculates the time that the event occurs based on the supplied half-life that includes a variable to modify the maximum number of half lives to search through (default is 20)
    	prStartNucleus = start;
        prEndNucleus = end;
    	if (energy > 0) {
            prEnergy = energy;
        } else {
            System.out.println("(DecayEvent) construction failed because (DecayEvent) input (energy) must be a positive number and greater than zero");
        }
        prType = type;
        if (halfLife > 0) {
            prHalfLife = halfLife;
        } else {
            System.out.println("(DecayEvent) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (maxHalfLives > 0) {
            pvMaxHalfLives = maxHalfLives;
        } else {
            System.out.println("Using default (pvMaxHalfLives) of 20 because the (maxHalfLives) supplied to the constructor was not a positive number and greater than zero");
            pvMaxHalfLives = 80;
        }
        if (prHalfLife > 0) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(DecayEvent) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvTimeOffset = 0.0;

    }
    public DecayEvent(String start, String end, double halfLife, double energy, String type,double timeOffset, int maxHalfLives) {
    	//Simple (DecayEvent) constructor that calculates the time that the event occurs based on the supplied half-life that includes a variable to modify the maximum number of half lives to search through (default is 20) and add a time offset
    	prStartNucleus = start;
        prEndNucleus = end;
    	if (timeOffset >= 0) {
            pvTimeOffset = timeOffset;
        } else {
            System.out.println("(DecayEvent) construction failed because (timeOffset) input must be a positive number and greater than or equal to zero");
        }
        if (energy > 0) {
            prEnergy = energy;
        } else {
            System.out.println("(DecayEvent) construction failed because (DecayEvent) input (energy) must be a positive number and greater than zero");
        }
        prType = type;
        if (halfLife > 0) {
            prHalfLife = halfLife;
        } else {
            System.out.println("(DecayEvent) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (maxHalfLives > 0) {
            pvMaxHalfLives = maxHalfLives;
        } else {
            System.out.println("Using default (pvMaxHalfLives) of 20 because the (maxHalfLives) supplied to the constructor was not a positive number and greater than zero");
            pvMaxHalfLives = 80;
        }
        if (prHalfLife > 0) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(DecayEvent) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
    }

    private double pvCalcTime() {
    	//Calculates the (DecayEvent) time with a time offset for a maximum number of half-lives
        double seed1 = Math.random();
        double seed2 = Math.random();
        double seed3 = Math.random();
        double weightedLife = 100.0;
        double testWeightedLife = seed1 * weightedLife;
        double testDetailedLife = seed2 * 50000.0;
        double t = 0;
        double x = 0;
        while (t == 0) {
            if (x == (pvMaxHalfLives-1)) {
                for (int y = 0; y<64;y++) {
                    if (testDetailedLife <= pvDetailedTest[y]){
                        t = (x+y/64.0+seed3/64.0)*prHalfLife;
                    }
                }
            } else if (testWeightedLife <= weightedLife*(1.0+(x*2.0))/Math.pow(2,x+1)){
                for (int y = 0; y<64;y++) {
                    if (testDetailedLife <= pvDetailedTest[y]){
                        t = (x+y/64.0+seed3/64.0)*prHalfLife;
                    }
                }
            }
            x+=1.0;
        }
        return (t+pvTimeOffset);
    }

    public String puGetStartNucleus() {
    	//returns the start nucleus of this (DecayEvent)
    	return prStartNucleus;
    }

    public String puGetEndNucleus() {
    	//returns the end nucleus of this (DecayEvent)
    	return prEndNucleus;
    }

    public double puGetTime() {
    	//returns the occurrence time of this (DecayEvent)
        if (pvTime > 0) {
            return pvTime;
        } else {
            System.out.println("(puGetTime) failed because this (DecayEvent) does not have a calculated (pvTime)");
            return -1;
        }
    }

    public double puGetHalfLife() {
    	//returns the half-life of this (DecayEvent)
        if (prHalfLife > 0) {
            return prHalfLife;
        } else {
            System.out.println("(puGetHalfLife) failed because this (DecayEvent) does not have a proper (prHalfLife) assigned");
            return -1;
        }
    }

    public String puGetType() {
    	//returns the radiation type of this (DecayEvent)
        return prType;
    }

    public double puGetEnergy() {
    	//returns the energy of this (DecayEvent)
        if (prEnergy > 0) {
            return prEnergy;
        } else {
            System.out.println("(puGetEnergy) failed because this (DecayEvent) does not have a proper (prEnergy) assigned");
            return -1;
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
        if (halflife > 0) {
            prHalfLife = halflife;
        } else {
            System.out.println("(puSetHalfLife) failed because the (halflife) provided is not greater than zero");
            prHalfLife = -1;
        }
    }

    public void puSetHalfLife(double halflife, int maxhalflives) {
    	//sets the half-life of this (DecayEvent) and adjusts the maximum number of half-lives for the unbound time calculation
        if (halflife > 0) {
            prHalfLife = halflife;
            pvTime = -1.0;
            if (maxhalflives > 0) {
                pvMaxHalfLives = maxhalflives;
            } else {
                System.out.println("(puSetHalfLife) failed because the (maxhalflives) provided is not greater than zero");
                pvMaxHalfLives = 0;
            }
        } else {
            System.out.println("(puSetHalfLife) failed because the (halflife) provided is not greater than zero");
            prHalfLife = -1;
        }
    }

    public void puSetTimeBounds(boolean isChild, double startTime, double endTime, double halflife) {
    	//sets the time bounds and half-life of this (DecayEvent)
        if (halflife > 0) {
            prHalfLife = halflife;
        } else {
            System.out.println("(puSetTimeBounds) failed because the (halflife) provided is not greater than zero");
            prHalfLife = -1;
        }
        if (startTime >= 0 & startTime < endTime) {
        	prStartTime = startTime;
        } else {
        	prStartTime = -1;
            System.out.println("(puSetTimeBounds) failed because (DecayEvent) input (startTime) must be greater than or equal to zero and less than (endTime)");
        }
        if (endTime >= 0 & endTime > startTime) {
        	prEndTime = endTime;
        } else {
        	prEndTime = -1;
            System.out.println("(puSetTimeBounds) failed because (DecayEvent) input (endTime) must be greater than or equal to zero and greaterh than (startTime)");
        }
    }

    public void puSetType(String newtype) {
    	//sets the radiation type of this (DecayEvent)
        prType = newtype;
    }

    public void puSetEnergy(double energy) {
    	//sets the energy of this (DecayEvent)
        if (energy > 0) {
            prEnergy = energy;
        } else {
            System.out.println("(puSetEnergy) failed because the (energy) provided is not greater than zero");
            prEnergy = -1;
        }
    }

}