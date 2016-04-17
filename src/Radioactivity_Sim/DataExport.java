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

import java.util.Calendar;
import java.util.Date;

public class DataExport {

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
    public static void main(String[] args) {
    	OutputScribe scrivener = new OutputScribe();
    	int fileNum = 0;
    	Calendar cal = Calendar.getInstance();
    	Date date = cal.getTime();
    	long now = date.getTime();
    	double num = Math.pow(10,5);
    	double startTime = 0.0*365*24*60*60;
    	double endTime = 0.0*365*24*60*60+100;
    	//test whether the chosen period is equilibrium sensitive by varying the (resolution) and seeing
    	//if more counts start to occur in the earlier portions of the time period
    	int resolution = 10;

    	//Writes all event data (Warning! limited by maximum size of java String's!)
//    	String file = "/home/user/git/Radioactivity_Sim/output/AllData";
//    	String data = test.puGetAllEventData();
//      scrivener.puOpenNewFile(file);
//      fileNum = scrivener.puGetNumFiles();
//      scrivener.puAppendStringToFile(fileNum-1, data);
//      scrivener.puCloseFile(fileNum-1);

    	//Writes the average per minute energy for the first year (Warning! This is only useful if (startTime)<365*24*60*60!)
//    	String file2 = "/home/user/git/Radioactivity_Sim/output/FirstYear";
//    	String data2 = test.puGetPerSecondAveEnergyForOneYear(0);
//    	scrivener.puOpenNewFile(file2);
//      fileNum = scrivener.puGetNumFiles();
//      scrivener.puAppendStringToFile(fileNum-1, data2);
//      scrivener.puCloseFile(fileNum-1);

    	//Writes the average per second energy for a year starting at (startTime)
//    	String file3 = "/home/user/git/Radioactivity_Sim/output/Output";
//      String data3 = test.puGetPerSecondAveEnergyForOneYear(startTime);
//      scrivener.puOpenNewFile(file3);
//      fileNum = scrivener.puGetNumFiles();
//      scrivener.puAppendStringToFile(fileNum-1, data3);
//      scrivener.puCloseFile(fileNum-1);

        //Writes the parsed (RuleBranches) to a file
//    	String file4 = "/home/user/git/Radioactivity_Sim/output/RuleBranches";
//    	DecayChainRuleSet rules4 = test.puGetDecayChainRuleSet(0);
//    	int numRuleSets4 = rules4.puGetNumBranches();
//    	StringBuilder data4 = new StringBuilder();
//    	for (int x = 0; x<numRuleSets4; x++){
//    		data4.append("Branch No: " + x + System.getProperty("line.separator"));
//    		data4.append(rules4.puOutputDecayRuleBranch(x) + System.getProperty("line.separator"));
//    	}
//    	scrivener.puOpenNewFile(file4);
//      fileNum = scrivener.puGetNumFiles();
//      scrivener.puAppendStringToFile(fileNum-1, data4.toString());
//      scrivener.puCloseFile(fileNum-1);

        //Writes the first parsed (RuleSet) for the (nucleiSample) to file
//    	String file5 = "/home/user/git/Radioactivity_Sim/output/RuleSet";
//    	DecayChainRuleSet rules5 = test.puGetDecayChainRuleSet(0);
//    	scrivener.puOpenNewFile(file5);
//      fileNum = scrivener.puGetNumFiles();
//      scrivener.puAppendStringToFile(fileNum-1, rules5.puOutputDecayChainRuleSet());
//      scrivener.puCloseFile(fileNum-1);

//    	//Verify if the calculations agree with theory:
//    	num = Math.pow(10, 5);
//    	startTime = 0; endTime = 100; resolution = 10;
//    	NucleiSample test = new NucleiSample(num,"/home/user/git/Radioactivity_Sim/input/RA224test",startTime,endTime,resolution);
//    	test.puAddSpecies(17.58, "/home/user/git/Radioactivity_Sim/input/RN220test", startTime, endTime);
//    	String file6 = "/home/user/git/Radioactivity_Sim/proofs/verification1";
//    	StringBuilder data6 = new StringBuilder();
//    	data6.append("Secular Equilibrium occurs between RA224 and RN220 because the decay constant, lambda" + System.getProperty("line.separator"));
//    	data6.append("of RN220 is much greater than that of RA224.  The decay constants are defined as:    " + System.getProperty("line.separator"));
//    	data6.append(System.getProperty("line.separator"));
//    	data6.append("   lambda_RA224 = ln(2) / HALFLIFE_RA224  &  lambda_RN220 = ln(2) / HALFLIFE_RN220   " + System.getProperty("line.separator"));
//    	data6.append(System.getProperty("line.separator"));
//    	data6.append("For these nuclei: lambda_RA224 = 2.19195E-6 & lambda_RN220 = 1.24667E-2              " + System.getProperty("line.separator"));
//    	data6.append("In the case of secular equilibrium, the number of child nuclei (in this case RN220)  " + System.getProperty("line.separator"));
//    	data6.append("remains at a fixed ratio to the number of parent nuclei.  This relationship is       " + System.getProperty("line.separator"));
//    	data6.append("governed by the equation: N_RN220 = N_RA224 x lambda_RA224/lambda_RN220              " + System.getProperty("line.separator"));
//    	data6.append("where N is the total number of nuclei of each type.  In order for this relationship  " + System.getProperty("line.separator"));
//    	data6.append("to remain true, the number of decay events of the parent nuclei must equal the number" + System.getProperty("line.separator"));
//    	data6.append("of decay events of the child nuclei such that:  NDECAYS_RA224 = NDECAYS_RN220        " + System.getProperty("line.separator"));
//    	data6.append("To prove this program's agreement with these theories we select a starting quantity  " + System.getProperty("line.separator"));
//    	data6.append("of RA224 nuclei:                                                                     " + System.getProperty("line.separator"));
//    	data6.append("N_RA224 = " + num + System.getProperty("line.separator"));
//    	data6.append("Then from theory, N_RN220 = " + num + "x(2.19195E-6/1.24667E-2) = " + (num*2.19195*Math.pow(10, -6)/0.0124667) + System.getProperty("line.separator"));
//    	data6.append("Next, a sufficiently small time period is selected such that N_RA224 doesn't decrease" + System.getProperty("line.separator"));
//    	data6.append("by much.  Let us choose a time period of 100 seconds.                                " + System.getProperty("line.separator"));
//    	data6.append("The number of decays of RA224 which occur in 100 seconds from a sample of 1E5 is     " + System.getProperty("line.separator"));
//    	data6.append("given by:  NDECAYS_RA224 = "+num+" x(1 - e^(-100*lambda_RA224) = 21.92               " + System.getProperty("line.separator"));
//    	data6.append("Of course, in reality the number of decay events has to be a whole number, but for   " + System.getProperty("line.separator"));
//    	data6.append("theory, it is acceptable to use the fraction, because it is an average amount that   " + System.getProperty("line.separator"));
//    	data6.append("would occur during this time period.  Since NDECAY_RA224 = NDECAY_RN220, the number  " + System.getProperty("line.separator"));
//    	data6.append("of RN220 events is also 21.92.  The energy released by each RA224 decay is 5.789 MeV " + System.getProperty("line.separator"));
//    	data6.append("and the energy released by each RN220 decay is 6.404 MeV.  Therefore the total energy" + System.getProperty("line.separator"));
//    	data6.append("released is: NDECAY_RA224 x 5.789 + NDECAY_RN220 x 6.404 = 267.27 MeV                " + System.getProperty("line.separator"));
//    	data6.append("and the average radiated power is: Energy/time = 267.27/100 = 2.6727 MeV/s           " + System.getProperty("line.separator"));
//    	data6.append("Now let us see if the program can produce the same values:                            " + System.getProperty("line.separator"));
//    	data6.append(System.getProperty("line.separator"));
//    	scrivener.puOpenNewFile(file6);
//        fileNum = scrivener.puGetNumFiles();
//        data6.append("For any user set (resolution) we will get: " + System.getProperty("line.separator"));
//        data6.append("Radiated Power = " + test.puGetRadiatedPowerOverTimeRange(startTime, endTime)+ " MeV/s" + System.getProperty("line.separator"));
//    	data6.append("Total Energy = " + test.puGetEnergySumOverTimeRange(startTime, endTime) + " MeV" + System.getProperty("line.separator"));
//    	data6.append(System.getProperty("line.separator"));
//    	data6.append("Next we see how the program is distributing the event energies into smaller groups as defined by the (resolution)" + System.getProperty("line.separator"));
//    	double sum = 0;
//    	for(int x = 1; x <= 1000 ;x = x*10) {
//    		sum = 0;
//    		data6.append("For resolution set to " + x + " we get:" + System.getProperty("line.separator"));
//    		for(int y = 0; y < x; y++) {
//    			data6.append("Energy (t = "+(startTime+(y+1)*(endTime-startTime)/x)+") = " + test.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x)+ " MeV" + System.getProperty("line.separator"));
//    			sum += test.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x);
//    		}
//    		data6.append("Which all adds up to: " + sum + " MeV" + System.getProperty("line.separator"));
//    		data6.append(System.getProperty("line.separator"));
//    	}
//    	scrivener.puAppendStringToFile(fileNum-1, data6.toString());
//    	scrivener.puCloseFile(fileNum-1);

    	//Verify if the calculations agree with theory:
    	double numU238 = Math.pow(10, 26);
    	double numTH234 = 1.4776*Math.pow(10, 14);
    	double numPA234 = 1.7116*Math.pow(10, 12);
    	startTime = 0; endTime = Math.pow(10,13); resolution = 10;
    	NucleiSample test = new NucleiSample(numU238,"/home/user/git/Radioactivity_Sim/input/U238test",startTime,endTime,resolution);
    	test.puAddSpecies(numTH234, "/home/user/git/Radioactivity_Sim/input/TH234test", startTime, endTime);
    	test.puAddSpecies(numPA234, "/home/user/git/Radioactivity_Sim/input/PA234test", startTime, endTime);
    	String file7 = "/home/user/git/Radioactivity_Sim/proofs/verification2";
    	StringBuilder data7 = new StringBuilder();
    	data7.append("This verification is intended to show that the Radioactive_Sim program does not      " + System.getProperty("line.separator"));
    	data7.append("create or propagate error in decay products that are further down a chain.           " + System.getProperty("line.separator"));
    	data7.append("Secular Equilibrium occurs between U238 and the next two products on its decay chain " + System.getProperty("line.separator"));
    	data7.append("TH234 and PA234 decay constant, lambda of PA234 is much greater than that of TH234   " + System.getProperty("line.separator"));
    	data7.append("and the decay constant of TH234 is much greater than that of U238.                   " + System.getProperty("line.separator"));
    	data7.append("The decay constants for these elements are defined as:                               " + System.getProperty("line.separator"));
    	data7.append(System.getProperty("line.separator"));
    	data7.append("lambda_RA224 = ln(2) / HALFLIFE_RA224 = 4.919 x 10^-19                               " + System.getProperty("line.separator"));
    	data7.append("lambda_RN220 = ln(2) / HALFLIFE_RN220 = 3.329 x 10^-7                                " + System.getProperty("line.separator"));
    	data7.append("lambda_PA234 = ln(2) / HALFLIFE_PA234 = 2.874 x 10^-5                                " + System.getProperty("line.separator"));
    	data7.append(System.getProperty("line.separator"));
    	data7.append("In the case of secular equilibrium, the number of child nuclei (TH234 & PA234)       " + System.getProperty("line.separator"));
    	data7.append("remains at a fixed ratio to the number of parent nuclei.  This relationship is       " + System.getProperty("line.separator"));
    	data7.append("governed by the equations:                                                           " + System.getProperty("line.separator"));
    	data7.append("N_TH234 = N_U238 x lambda_U238/lambda_TH234                                          " + System.getProperty("line.separator"));
    	data7.append("N_PA234 = N_TH234 x lambda_TH234/lambda_PA234                                        " + System.getProperty("line.separator"));
    	data7.append("where N is the total number of nuclei of each type.  In order for this relationship  " + System.getProperty("line.separator"));
    	data7.append("to remain true, the number of decay events of the parent nuclei must equal the number" + System.getProperty("line.separator"));
    	data7.append("of decay events of the child nuclei such that:  ND_U238 = ND_TH234 = ND_PA234        " + System.getProperty("line.separator"));
    	data7.append("To prove this program's agreement with these theories we select a starting quantity  " + System.getProperty("line.separator"));
    	data7.append("of U238 nuclei:                                                                     " + System.getProperty("line.separator"));
    	data7.append("N_U238 = " + numU238 + System.getProperty("line.separator"));
    	data7.append("Then: N_TH234 = " + numU238 + "x(4.919E-19/3.329E-7) = 1.4776 x 10^14                " + System.getProperty("line.separator"));
    	data7.append("And: N_PA234 = " + numU238 + "x(4.919E-19/2.874E-5) = 1.7116 x 10^12                 " + System.getProperty("line.separator"));
    	data7.append("Next, a sufficiently small time period is selected such that N_U238 doesn't decrease " + System.getProperty("line.separator"));
    	data7.append("by much.  Let us choose a time period of 10^13 seconds (317,100 years).              " + System.getProperty("line.separator"));
    	data7.append("The number of decays of U238 which occur in 10^13 seconds from a sample of 1E26 is   " + System.getProperty("line.separator"));
    	data7.append("given by:  ND_U238 = "+numU238+" x(1 - e^(-10^-13*lambda_U238) = 4.919 x 10^20       " + System.getProperty("line.separator"));
    	data7.append("Since ND_U238 = ND_TH234 = ND_PA234, the number of the other events is also 4.919E20." + System.getProperty("line.separator"));
    	data7.append("The energy released by each U238 decay is 4.2697 MeV, TH234 decays release 0.273 MeV " + System.getProperty("line.separator"));
    	data7.append("and the energy released by each PA234 decay is 2.195 MeV.  Therefore the total energy" + System.getProperty("line.separator"));
    	data7.append("released is: ND_U238 x (4.2697 + 0.273 + 2.195) = 3.314 x 10^21 MeV                  " + System.getProperty("line.separator"));
    	data7.append("and the average radiated power is: Energy/time = 3.314E21/1E13 = 3.3143E8 MeV/s      " + System.getProperty("line.separator"));
    	data7.append("Now let us see if this program can produce the same values:                          " + System.getProperty("line.separator"));
    	data7.append(System.getProperty("line.separator"));
    	scrivener.puOpenNewFile(file7);
        fileNum = scrivener.puGetNumFiles();
        data7.append("For any user set (resolution) we will get: " + System.getProperty("line.separator"));
        data7.append("Radiated Power = " + test.puGetRadiatedPowerOverTimeRange(startTime, endTime)+ " MeV/s" + System.getProperty("line.separator"));
    	data7.append("Total Energy = " + test.puGetEnergySumOverTimeRange(startTime, endTime) + " MeV" + System.getProperty("line.separator"));
    	data7.append(System.getProperty("line.separator"));
    	data7.append("Next we see how the program is distributing the event energies into smaller groups as defined by the (resolution)" + System.getProperty("line.separator"));
    	double sum = 0;
    	for(int x = 1; x <= 1000 ;x = x*10) {
    		sum = 0;
    		data7.append("For resolution set to " + x + " we get:" + System.getProperty("line.separator"));
    		for(int y = 0; y < x; y++) {
    			data7.append("Energy (t = "+(startTime+(y+1)*(endTime-startTime)/x)+") = " + test.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x)+ " MeV" + System.getProperty("line.separator"));
    			sum += test.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x);
    		}
    		data7.append("Which all adds up to: " + sum + " MeV" + System.getProperty("line.separator"));
    		data7.append(System.getProperty("line.separator"));
    	}
    	scrivener.puAppendStringToFile(fileNum-1, data7.toString());
    	scrivener.puCloseFile(fileNum-1);

        //Prints the program runtime to the console in milliseconds
        System.out.println(Calendar.getInstance().getTime().getTime() - now);

    }
}
