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
    	double num = 6.022*Math.pow(10,2);
    	double startTime = 0.0*365*24*60*60;
    	double endTime = 1.0*365*24*60*60;
    	//test whether the chosen period is equilibrium sensitive by varying the (resolution) and seeing
    	//if more counts start to occur in the earlier portions of the time period
    	int resolution = 100;
    	NucleiSample test = new NucleiSample(num,"/home/user/git/Radioactivity_Sim/input/RA224",startTime,endTime,resolution);

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
    	String file3 = "/home/user/git/Radioactivity_Sim/output/Output";
        String data3 = test.puGetPerSecondAveEnergyForOneYear(startTime);
        scrivener.puOpenNewFile(file3);
        fileNum = scrivener.puGetNumFiles();
        scrivener.puAppendStringToFile(fileNum-1, data3);
        scrivener.puCloseFile(fileNum-1);

        //Writes the parsed (RuleBranches) to a file
    	String file4 = "/home/user/git/Radioactivity_Sim/output/RuleBranches";
    	DecayChainRuleSet rules4 = test.puGetDecayChainRuleSet(0);
    	int numRuleSets4 = rules4.puGetNumBranches();
    	StringBuilder data4 = new StringBuilder();
    	for (int x = 0; x<numRuleSets4; x++){
    		data4.append("Branch No: " + x + System.getProperty("line.separator"));
    		data4.append(rules4.puOutputDecayRuleBranch(x) + System.getProperty("line.separator"));
    	}
    	scrivener.puOpenNewFile(file4);
        fileNum = scrivener.puGetNumFiles();
        scrivener.puAppendStringToFile(fileNum-1, data4.toString());
        scrivener.puCloseFile(fileNum-1);

        //Writes the first parsed (RuleSet) for the (nucleiSample) to file
    	String file5 = "/home/user/git/Radioactivity_Sim/output/RuleSet";
    	DecayChainRuleSet rules5 = test.puGetDecayChainRuleSet(0);
    	scrivener.puOpenNewFile(file5);
        fileNum = scrivener.puGetNumFiles();
        scrivener.puAppendStringToFile(fileNum-1, rules5.puOutputDecayChainRuleSet());
        scrivener.puCloseFile(fileNum-1);

        //Prints the program runtime to the console in milliseconds
        System.out.println(Calendar.getInstance().getTime().getTime() - now);

    }
}
