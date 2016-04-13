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


public class Event {

    // An Event calculates and contains one radiative event
    /* Variable and Function Nomenclature prescripts:
     * pv = private
     * pu = public
     * pvs = private static
     * pus = public static
     * pvsf = private static final
     * pusf = public static final
     */
    private double pvTime = 0.0; //calculated time of the event referenced from arbitrary start point in seconds
    private double pvEnergy = 0.0; //user supplied event energy in MeV
    private String pvType; //user supplied event type (alpha, beta, gamma, neutron)
    private double pvHalfLife =0.0; //user supplied event half-life in seconds
    private int pvMaxHalfLives = 20;
    private double pvTimeOffset = 0.0; //user supplied offset time in seconds
    private String pvStartNucleus = ""; //the nucleus before the event
    private String pvEndNucleus = ""; //the nucleus after the event
    private double pvStartTime = 0; //lower bound of the event time
    private double pvEndTime = 0; //upper bound of the event time
    //pvDetailedTest is a predefined sieve to reduce error in the time calculation
    private int pvDetailedTest[] = {
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
    public Event(boolean isChild, double startTime, double endTime, String start, String end, double halfLife, double energy, String type) {
        //constructor which checks to see if the event is a child before directing it to the occurrance time prediction algorithm
    	pvStartNucleus = start;
        pvEndNucleus = end;
        if (startTime >= 0 & startTime < endTime) {
        	pvStartTime = startTime;
        } else {
            System.out.println("(Event) construction failed because (Event) input (startTime) must be greater than or equal to zero and less than (endTime)");
        }
        if (endTime >= 0 & endTime > startTime) {
        	pvEndTime = endTime;
        } else {
            System.out.println("(Event) construction failed because (Event) input (endTime) must be greater than or equal to zero and greaterh than (startTime)");
        }

    	if (energy > 0) {
            pvEnergy = energy;
        } else {
            System.out.println("(Event) construction failed because (Event) input (energy) must be a positive number and greater than zero");
        }
        pvType = type;
        if (halfLife > 0) {
            pvHalfLife = halfLife;
        } else {
            System.out.println("(Event) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (pvHalfLife > 0) {
        	if (isChild) {
        		pvTime = pvCalcBoundTimeChild();
        	} else {
            	pvTime = pvCalcBoundTime();
        	}
        } else {
            System.out.println("(Event) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvMaxHalfLives = 20;
        pvTimeOffset = 0.0;
    }

    public Event(double startTime, double endTime, String start, String end, double halfLife, double energy, String type) {
        pvStartNucleus = start;
        pvEndNucleus = end;
        if (startTime >= 0 & startTime < endTime) {
        	pvStartTime = startTime;
        } else {
            System.out.println("(Event) construction failed because (Event) input (startTime) must be greater than or equal to zero and less than (endTime)");
        }
        if (endTime >= 0 & endTime > startTime) {
        	pvEndTime = endTime;
        } else {
            System.out.println("(Event) construction failed because (Event) input (endTime) must be greater than or equal to zero and greaterh than (startTime)");
        }

    	if (energy > 0) {
            pvEnergy = energy;
        } else {
            System.out.println("(Event) construction failed because (Event) input (energy) must be a positive number and greater than zero");
        }
        pvType = type;
        if (halfLife > 0) {
            pvHalfLife = halfLife;
        } else {
            System.out.println("(Event) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (pvHalfLife > 0) {
            pvTime = pvCalcBoundTime();
        } else {
            System.out.println("(Event) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvMaxHalfLives = 20;
        pvTimeOffset = 0.0;
    }
    public Event(String start, String end, double halfLife, double energy, String type) {
        pvStartNucleus = start;
        pvEndNucleus = end;
    	if (energy > 0) {
            pvEnergy = energy;
        } else {
            System.out.println("(Event) construction failed because (Event) input (energy) must be a positive number and greater than zero");
        }
        pvType = type;
        if (halfLife > 0) {
            pvHalfLife = halfLife;
        } else {
            System.out.println("(Event) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (pvHalfLife > 0) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(Event) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvMaxHalfLives = 20;
        pvTimeOffset = 0.0;
    }
    public Event(String start, String end, double halfLife, double energy, String type,double timeOffset) {
    	pvStartNucleus = start;
        pvEndNucleus = end;
    	if (timeOffset >= 0) {
            pvTimeOffset = timeOffset;
        } else {
            System.out.println("(Event) construction failed because (timeOffset) input must be a positive number and greater than or equal to zero");
        }
        if (energy > 0) {
            pvEnergy = energy;
        } else {
            System.out.println("(Event) construction failed because (Event) input (energy) must be a positive number and greater than zero");
        }
        pvType = type;
        if (halfLife > 0) {
            pvHalfLife = halfLife;
        } else {
            System.out.println("(Event) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (pvHalfLife > 0) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(Event) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvMaxHalfLives = 20;
    }
    public Event(String start, String end, double halfLife, double energy, String type, int maxHalfLives) {
    	pvStartNucleus = start;
        pvEndNucleus = end;
    	if (energy > 0) {
            pvEnergy = energy;
        } else {
            System.out.println("(Event) construction failed because (Event) input (energy) must be a positive number and greater than zero");
        }
        pvType = type;
        if (halfLife > 0) {
            pvHalfLife = halfLife;
        } else {
            System.out.println("(Event) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (maxHalfLives > 0) {
            pvMaxHalfLives = maxHalfLives;
        } else {
            System.out.println("Using default (pvMaxHalfLives) of 20 because the (maxHalfLives) supplied to the constructor was not a positive number and greater than zero");
            pvMaxHalfLives = 20;
        }
        if (pvHalfLife > 0) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(Event) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
        pvTimeOffset = 0.0;

    }
    public Event(String start, String end, double halfLife, double energy, String type,double timeOffset, int maxHalfLives) {
    	pvStartNucleus = start;
        pvEndNucleus = end;
    	if (timeOffset >= 0) {
            pvTimeOffset = timeOffset;
        } else {
            System.out.println("(Event) construction failed because (timeOffset) input must be a positive number and greater than or equal to zero");
        }
        if (energy > 0) {
            pvEnergy = energy;
        } else {
            System.out.println("(Event) construction failed because (Event) input (energy) must be a positive number and greater than zero");
        }
        pvType = type;
        if (halfLife > 0) {
            pvHalfLife = halfLife;
        } else {
            System.out.println("(Event) construction failed because input (halflife) must be a positive number and greater than zero");
        }
        if (maxHalfLives > 0) {
            pvMaxHalfLives = maxHalfLives;
        } else {
            System.out.println("Using default (pvMaxHalfLives) of 20 because the (maxHalfLives) supplied to the constructor was not a positive number and greater than zero");
            pvMaxHalfLives = 20;
        }
        if (pvHalfLife > 0) {
            pvTime = pvCalcTime();
        } else {
            System.out.println("(Event) construction failed because (pvTime) cannot be calculated without a defined (halflife)");
        }
    }

    private double pvCalcBoundTime(){
    	//Calculates the event time within a specified time range
    	double end = Math.exp(-pvEndTime*Math.log(2)/pvHalfLife);
    	double start = Math.exp(-pvStartTime*Math.log(2)/pvHalfLife);
    	double seed1 = Math.random()*(start-end)+end;
    	double t = 0;
    	double deltaT = (pvEndTime-pvStartTime)/10;
    	int x = 0;
        while (t == 0) {
        	if(Math.exp(-(pvStartTime+x*deltaT)*Math.log(2)/pvHalfLife)<=seed1){
            	t = pvStartTime + (Math.random()+x-1)*deltaT;
        	}
        	x++;
        }
        return t;
    }

    private double pvCalcBoundTimeChild(){
    	//Calculates the event time within a specified time range for a child nucleus
    	//Assumes equilibrium
    	//TODO make this algorithm capable of calculating accurate times when
    	//equilibrium should not be assumed
        return (pvStartTime + Math.random()*(pvEndTime-pvStartTime));
    }

    private double pvCalcTime() {
    	//Calculates the event time with a time offset
        double seed1 = Math.random();
        double seed2 = Math.random();
        double seed3 = Math.random();
        double weightedLife = Math.pow(2,pvMaxHalfLives);
        double testWeightedLife = seed1 * weightedLife;
        double testDetailedLife = seed2 * 50000;
        double t = 0;
        int x = 0;
        while (t == 0) {
            if (x == (pvMaxHalfLives-1)) {
                for (int y = 0; y<64;y++) {
                    if (testDetailedLife <= pvDetailedTest[y]){
                        t = (x+y/64+seed3/64)*pvHalfLife;
                    }
                }
            } else if (testWeightedLife <= weightedLife*(1+(x*2))/Math.pow(2,x+1)){
                for (int y = 0; y<64;y++) {
                    if (testDetailedLife <= pvDetailedTest[y]){
                        t = (x+y/64+seed3/64)*pvHalfLife;
                    }
                }
            }
            x+=1;
        }
        return (t+pvTimeOffset);
    }
    public String puGetStartNucleus() {
    	return pvStartNucleus;
    }
    public String puGetEndNucleus() {
    	return pvEndNucleus;
    }
    public double puGetTime() {
        if (pvTime > 0) {
            return pvTime;
        } else {
            System.out.println("(puGetTime) failed because this (Event) does not have a calculated (pvTime)");
            return 0;
        }
    }
    public double puGetHalfLife() {
        if (pvHalfLife > 0) {
            return pvHalfLife;
        } else {
            System.out.println("(puGetHalfLife) failed because this (Event) does not have a proper (pvHalflife) assigned");
            return 0;
        }
    }
    public String puGetType() {
        return pvType;
    }
    public double puGetEnergy() {
        if (pvEnergy > 0) {
            return pvEnergy;
        } else {
            System.out.println("(puGetEnergy) failed because this (Event) does not have a proper (pvEnergy) assigned");
            return 0;
        }
    }
    public void puSetStartNucleus(String start) {
    	pvStartNucleus = start;
    }
    public void puSetEndNucleus(String end) {
    	pvEndNucleus = end;
    }
    public void puSetHalfLife(double halflife) {
        if (halflife > 0) {
            pvHalfLife = halflife;
        } else {
            System.out.println("(puSetHalfLife) failed because the (halflife) provided is not greater than zero");
            pvHalfLife = 0;
        }
    }
    public void puSetHalfLife(double halflife, int maxhalflives) {
        if (halflife > 0) {
            pvHalfLife = halflife;
            if (maxhalflives > 0) {
                pvMaxHalfLives = maxhalflives;
                pvTime = pvCalcTime();
            } else {
                System.out.println("(puSetHalfLife) failed because the (maxhalflives) provided is not greater than zero");
                pvMaxHalfLives = 0;
                pvTime = 0.0;
            }
        } else {
            System.out.println("(puSetHalfLife) failed because the (halflife) provided is not greater than zero");
            pvHalfLife = 0;
        }
    }
    public void puSetType(String newtype) {
        pvType = newtype;
    }
    public void puSetEnergy(double energy) {
        if (energy > 0) {
            pvEnergy = energy;
        } else {
            System.out.println("(puSetEnergy) failed because the (energy) provided is not greater than zero");
            pvEnergy = 0;
        }
    }
}