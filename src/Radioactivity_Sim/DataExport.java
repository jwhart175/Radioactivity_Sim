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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

public class DataExport {

	/* Variable and Function Nomenclature prescripts:
     * pv = private
     * pu = public
     * pvs = private static
     * pus = public static
     * pvsf = private static final
     * pusf = public static final
     */
    public static void main(String[] args) {
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
//      pvsScribe(data,file);

    	//Writes the average per minute energy for the first year (Warning! This is only useful if (startTime)<365*24*60*60!)
//    	String file2 = "/home/user/git/Radioactivity_Sim/output/FirstYear";
//    	String data2 = test.puGetPerSecondAveEnergyForOneYear(0);
//    	pvsScribe(data2,file2);

    	//Writes the average per second energy for a year starting at (startTime)
    	String file3 = "/home/user/git/Radioactivity_Sim/output/Output";
        String data3 = test.puGetPerSecondAveEnergyForOneYear(startTime);
        pvsScribe(data3,file3);

        //Writes the parsed (RuleBranches) to a file
    	String file4 = "/home/user/git/Radioactivity_Sim/output/RuleBranches";
    	RuleSet rules4 = test.puGetRuleSet(0);
    	int numRuleSets4 = rules4.puGetNumBranches();
    	StringBuilder data4 = new StringBuilder();
    	for (int x = 0; x<numRuleSets4; x++){
    		data4.append("Branch No: " + x + System.getProperty("line.separator"));
    		data4.append(rules4.puOutputRuleBranch(x) + System.getProperty("line.separator"));
    	}
        pvsScribe(data4.toString(),file4);

        //Writes the first parsed (RuleSet) for the (nucleiSample) to file
    	String file5 = "/home/user/git/Radioactivity_Sim/output/RuleSet";
    	RuleSet rules5 = test.puGetRuleSet(0);
    	pvsScribe(rules5.puOutputRuleSet(),file5);

    	//Writes the Bateman solution calculations for the particle numbers at (endTime) to file

        //Prints the program runtime to the console in milliseconds
        System.out.println(Calendar.getInstance().getTime().getTime() - now);

    }
    private static void pvsScribe(String textline,String file) {
        PrintWriter writer = null;
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(file,true);
            bw = new BufferedWriter(fw);
            writer = new PrintWriter(bw);
            writer.println(textline);

        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {writer.close();} catch (Exception ex) {}
        }
    }

}
