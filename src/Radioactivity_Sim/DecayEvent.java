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
    private static final int pvDetailedTest[] = {
    	693,1385,2077,2769,3460,4150,4840,5530,6219,6908,
    	7596,8283,8970,9657,10343,11029,11714,12399,13083,13767,
    	14451,15134,15816,16498,17179,17860,18541,19221,19901,20580,
    	21258,21937,22614,23291,23968,24645,25320,25996,26671,27345,
    	28019,28693,29366,30038,30710,31382,32053,32724,33394,34064,
    	34733,35402,36070,36738,37406,38073,38739,39405,40071,40736,
    	41401,42065,42729,43392,44055,44717,45379,46040,46701,47362,
    	48022,48682,49341,50000,50658,51316,51973,52630,53286,53942,
    	54598,55253,55908,56562,57215,57869,58522,59174,59826,60477,
    	61128,61779,62429,63079,63728,64377,65025,65673,66320,66967,
    	67614,68260,68905,69550,70195,70839,71483,72127,72769,73412,
    	74054,74696,75337,75977,76618,77258,77897,78536,79174,79812,
    	80450,81087,81724,82360,82996,83631,84266,84901,85535,86169,
    	86802,87435,88067,88699,89330,89961,90592,91222,91852,92481,
    	93110,93738,94366,94994,95621,96247,96873,97499,98125,98750,
    	99374,99998,100622,101245,101868,102490,103112,103733,104354,104975,
    	105595,106215,106834,107453,108071,108690,109307,109924,110541,111157,
    	111773,112389,113004,113618,114232,114846,115460,116072,116685,117297,
    	117909,118520,119131,119741,120351,120960,121570,122178,122786,123394,
    	124002,124609,125215,125821,126427,127032,127637,128242,128846,129449,
    	130053,130655,131258,131860,132461,133062,133663,134263,134863,135463,
    	136062,136660,137259,137856,138454,139051,139647,140244,140839,141435,
    	142029,142624,143218,143812,144405,144998,145590,146182,146774,147365,
    	147956,148546,149136,149726,150315,150904,151492,152080,152668,153255,
    	153841,154428,155014,155599,156184,156769,157353,157937,158521,159104,
    	159686,160269,160850,161432,162013,162594,163174,163754,164333,164912,
    	165491,166069,166647,167224,167801,168378,168954,169530,170105,170680,
    	171255,171829,172403,172977,173550,174122,174695,175266,175838,176409,
    	176980,177550,178120,178689,179258,179827,180395,180963,181531,182098,
    	182665,183231,183797,184363,184928,185492,186057,186621,187184,187748,
    	188310,188873,189435,189997,190558,191119,191679,192239,192799,193358,
    	193917,194476,195034,195592,196149,196706,197263,197819,198375,198930,
    	199485,200040,200594,201148,201702,202255,202808,203360,203912,204464,
    	205015,205566,206116,206666,207216,207765,208314,208863,209411,209959,
    	210506,211053,211600,212146,212692,213238,213783,214327,214872,215416,
    	215960,216503,217046,217588,218130,218672,219214,219755,220295,220835,
    	221375,221915,222454,222993,223531,224069,224607,225144,225681,226218,
    	226754,227289,227825,228360,228895,229429,229963,230496,231030,231562,
    	232095,232627,233159,233690,234221,234752,235282,235812,236341,236870,
    	237399,237928,238456,238983,239511,240038,240564,241090,241616,242142,
    	242667,243192,243716,244240,244764,245287,245810,246333,246855,247377,
    	247898,248419,248940,249460,249981,250500,251020,251539,252057,252575,
    	253093,253611,254128,254645,255161,255677,256193,256709,257224,257738,
    	258253,258766,259280,259793,260306,260819,261331,261843,262354,262865,
    	263376,263887,264397,264906,265416,265925,266433,266942,267450,267957,
    	268464,268971,269478,269984,270490,270995,271500,272005,272510,273014,
    	273517,274021,274524,275027,275529,276031,276533,277034,277535,278035,
    	278536,279036,279535,280034,280533,281032,281530,282028,282525,283022,
    	283519,284016,284512,285008,285503,285998,286493,286987,287481,287975,
    	288468,288961,289454,289946,290438,290930,291421,291912,292403,292893,
    	293383,293873,294362,294851,295340,295828,296316,296803,297291,297778,
    	298264,298750,299236,299722,300207,300692,301177,301661,302145,302628,
    	303111,303594,304077,304559,305041,305522,306004,306485,306965,307445,
    	307925,308405,308884,309363,309841,310320,310797,311275,311752,312229,
    	312706,313182,313658,314133,314609,315084,315558,316032,316506,316980,
    	317453,317926,318399,318871,319343,319815,320286,320757,321228,321698,
    	322168,322638,323107,323576,324045,324513,324981,325449,325916,326383,
    	326850,327316,327783,328248,328714,329179,329644,330108,330572,331036,
    	331500,331963,332426,332888,333351,333813,334274,334735,335196,335657,
    	336117,336577,337037,337496,337956,338414,338873,339331,339789,340246,
    	340703,341160,341617,342073,342529,342984,343439,343894,344349,344803,
    	345257,345711,346164,346617,347070,347523,347975,348426,348878,349329,
    	349780,350230,350681,351131,351580,352030,352479,352927,353376,353824,
    	354271,354719,355166,355613,356059,356505,356951,357397,357842,358287,
    	358732,359176,359620,360064,360507,360950,361393,361836,362278,362720,
    	363161,363603,364043,364484,364925,365365,365804,366244,366683,367122,
    	367560,367998,368436,368874,369311,369748,370185,370621,371058,371493,
    	371929,372364,372799,373233,373668,374102,374535,374969,375402,375835,
    	376267,376699,377131,377563,377994,378425,378856,379286,379716,380146,
    	380576,381005,381434,381862,382291,382719,383146,383574,384001,384428,
    	384854,385281,385707,386132,386558,386983,387407,387832,388256,388680,
    	389103,389527,389950,390372,390795,391217,391639,392060,392482,392903,
    	393323,393744,394164,394583,395003,395422,395841,396260,396678,397096,
    	397514,397931,398348,398765,399182,399598,400014,400430,400845,401261,
    	401676,402090,402504,402918,403332,403746,404159,404572,404984,405396,
    	405808,406220,406632,407043,407454,407864,408274,408684,409094,409504,
    	409913,410322,410730,411139,411547,411954,412362,412769,413176,413583,
    	413989,414395,414801,415206,415611,416016,416421,416825,417229,417633,
    	418037,418440,418843,419246,419648,420050,420452,420854,421255,421656,
    	422057,422457,422857,423257,423657,424056,424455,424854,425253,425651,
    	426049,426446,426844,427241,427638,428035,428431,428827,429223,429618,
    	430013,430408,430803,431197,431592,431985,432379,432772,433165,433558,
    	433951,434343,434735,435126,435518,435909,436300,436690,437081,437471,
    	437861,438250,438639,439028,439417,439805,440194,440581,440969,441356,
    	441744,442130,442517,442903,443289,443675,444060,444446,444831,445215,
    	445600,445984,446368,446751,447135,447518,447901,448283,448665,449047,
    	449429,449811,450192,450573,450954,451334,451714,452094,452474,452853,
    	453232,453611,453990,454368,454746,455124,455501,455879,456256,456633,
    	457009,457385,457761,458137,458512,458888,459263,459637,460012,460386,
    	460760,461133,461507,461880,462253,462625,462998,463370,463742,464113,
    	464485,464856,465226,465597,465967,466337,466707,467077,467446,467815,
    	468184,468552,468920,469288,469656,470024,470391,470758,471125,471491,
    	471857,472223,472589,472954,473319,473684,474049,474414,474778,475142,
    	475505,475869,476232,476595,476958,477320,477682,478044,478406,478767,
    	479128,479489,479850,480210,480570,480930,481290,481649,482009,482368,
    	482726,483085,483443,483801,484158,484516,484873,485230,485587,485943,
    	486299,486655,487011,487366,487722,488077,488431,488786,489140,489494,
    	489848,490201,490554,490907,491260,491613,491965,492317,492669,493020,
    	493372,493723,494073,494424,494774,495124,495474,495824,496173,496522,
    	496871,497220,497568,497916,498264,498612,498959,499306,499653,500000};

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
    	SecureRandom tree = new SecureRandom();
        double seed1 = tree.nextDouble();
        double seed2 = tree.nextDouble();
        double seed3 = tree.nextDouble();
        double weightedLife = 100.0;
        double testWeightedLife = seed1 * weightedLife;
        double testDetailedLife = seed2 * 500000.0;
        double t = 0;
        double x = 0;
        while (t == 0) {
            if (x == (pvMaxHalfLives-1)) {
                for (int y = 0; y<1000;y++) {
                    if (testDetailedLife <= pvDetailedTest[y]){
                        t = (x+(y+seed3)/1000.0)*prHalfLife;
                        break;
                    }
                }
            } else if (testWeightedLife <= weightedLife*(1.0+(x*2.0))/Math.pow(2,x+1)){
                for (int y = 0; y<1000;y++) {
                    if (testDetailedLife <= pvDetailedTest[y]){
                        t = (x+(y+seed3)/1000.0)*prHalfLife;
                        break;
                    }
                }
            }
            x++;
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