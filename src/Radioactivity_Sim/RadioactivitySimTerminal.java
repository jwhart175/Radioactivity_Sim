/*
 MIT License

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

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;


public class RadioactivitySimTerminal extends JFrame {
	//The program interface and console
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

    private JTextPane prTextPane;
    private JTextField prTextField;
    private OutputScribe prScrivener = new OutputScribe();
    private NucleiSamplePredictiveSim prPredictiveSample = new NucleiSamplePredictiveSim();
    private NucleiSampleBruteForceSim prBruteForceSample = new NucleiSampleBruteForceSim();


    public RadioactivitySimTerminal() {
        super("Radioactivity_Sim Terminal");
        //creates a simple console
        prTextPane = new JTextPane();
        prTextPane.setCaretPosition(0);
        prTextPane.setMargin(new Insets(8,8,8,8));
        JScrollPane scrollPane = new JScrollPane(prTextPane);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        prTextPane.setEditable(false);
        prTextField = new JTextField();
        prTextField.setMargin(new Insets(8,8,8,8));
        prTextField.setEditable(true);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(prTextField, BorderLayout.PAGE_START);
        String initString = "Welcome to Radioactivity_Sim Terminal" + System.getProperty("line.separator") + "Enter help for command list" +System.getProperty("line.separator");
        prTextPane.setText(initString);
        prTextPane.setCaretPosition(0);
        prTextField.addKeyListener(new prTerminalKeyListener());;
    }

    private class prTerminalKeyListener
                    implements KeyListener {
        public void keyTyped(KeyEvent e){
        	if(e.getKeyChar() == KeyEvent.VK_ENTER) {
        		//check for and execute commands
        		prEnterCommand();
        	}
        }
        public void keyPressed(KeyEvent e){
        	//no action
        }
        public void keyReleased(KeyEvent e){
        	//no action
        }
    }

    private void prEnterCommand(){
    	//read text in from prTextField
    	String commandString = prTextField.getText();
    	prTextField.setText("");
    	String currentText = prTextPane.getText();
    	currentText = currentText + "\n" + ">: " + commandString;
    	prTextPane.setText(currentText);
    	//interpret command and print response or error
    	if(commandString.contains("help")){
    		currentText = currentText + System.getProperty("line.separator");
    		currentText = currentText + "The program commands are: " + System.getProperty("line.separator");
    		currentText = currentText + "verification1 <outfile>  ->  This command runs the verification1 test script and outputs it to <file>." + System.getProperty("line.separator");
    		currentText = currentText + "verification2 <outfile>  ->  This command runs the verification2 test script and outputs it to <file>." + System.getProperty("line.separator");
    		currentText = currentText + "verification3 <outfile>  ->  This command runs the verification3 test script and outputs it to <file>." + System.getProperty("line.separator");
    		currentText = currentText + "verification4 <outfile>  ->  This command runs the verification4 test script and outputs it to <file>." + System.getProperty("line.separator");
    		currentText = currentText + "clear                    ->  This command clears the console." + System.getProperty("line.separator");
    		prTextPane.setText(currentText);
    	}
    	if(commandString.contains("verification1")){
    		String[] snippets = commandString.split("verification1");
    		String file = snippets[snippets.length-1];
    		file = file.substring(1,file.length());
    		try{
    			prVerification1(file);
    		} catch(Exception e) {
    			currentText = currentText + System.getProperty("line.separator") + e.getMessage() + System.getProperty("line.separator") + "Error input file: " + file + " could not be read!" + System.getProperty("line.separator") + ">:";
    			prTextPane.setText(currentText);
    		}
    	}
    	if(commandString.contains("verification2")){
    		String[] snippets = commandString.split("verification2");
    		String file = snippets[snippets.length-1];
    		file = file.substring(1,file.length());
    		try{
    			prVerification2(file);
    		} catch(Exception e) {
    			currentText = currentText + System.getProperty("line.separator") + e.getMessage() + System.getProperty("line.separator") + "Error input file: " + file + " could not be read!" + System.getProperty("line.separator") + ">:";
    			prTextPane.setText(currentText);
    		}
    	}
    	if(commandString.contains("verification3")){
    		String[] snippets = commandString.split("verification3");
    		String file = snippets[snippets.length-1];
    		file = file.substring(1,file.length());
    		try{
    			prVerification3(file);
    		} catch(Exception e) {
    			currentText = currentText + System.getProperty("line.separator") + e.getMessage() + System.getProperty("line.separator") + "Error input file: " + file + " could not be read!" + System.getProperty("line.separator") + ">:";
    			prTextPane.setText(currentText);
    		}
    	}
    	if(commandString.contains("verification4")){
    		String[] snippets = commandString.split("verification4");
    		String file = snippets[snippets.length-1];
    		file = file.substring(1,file.length());
    		try{
    			prVerification4(file);
    		} catch(Exception e) {
    			currentText = currentText + System.getProperty("line.separator") + e.getMessage() + System.getProperty("line.separator") + "Error input file: " + file + " could not be read!" + System.getProperty("line.separator") + ">:";
    			prTextPane.setText(currentText);
    		}
    	}
    	if(commandString.contains("clear")){
    		currentText = ">:" + System.getProperty("line.separator");
    		prTextPane.setText(currentText);
    	}
    }

    private static void prsShowGUI() {
        //Create and set up the window.
        final RadioactivitySimTerminal frame = new RadioactivitySimTerminal();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	prsShowGUI();
            }
        });
    }

    private void prVerification1(String file){
    	//Runs the verification1 test script and outputs it to (file)

    	double numRA224 = 0, numRN220 = 0, startTime = 0, endTime = 0;
    	int fileNum = 0;
    	//initialize time tracking
    	Calendar cal = Calendar.getInstance();
    	Date date = cal.getTime();
    	long now = date.getTime();
    	//read text in from prTextField
    	String commandString = prTextField.getText();
    	prTextField.setText("");
    	//print command in prTextPane
    	String currentText = prTextPane.getText();
    	currentText = currentText + "\n" + commandString;
    	prTextPane.setText(currentText);

    	int resolution = 1;
    	numRA224 = Math.pow(10, 5);
    	numRN220 = 17.58;
    	startTime = 0;
    	endTime = 100;
    	prPredictiveSample = new NucleiSamplePredictiveSim(numRA224,"/home/user/git/Radioactivity_Sim/input/RA224test",startTime,endTime,resolution);
    	prPredictiveSample.puAddSpecies(numRN220, "/home/user/git/Radioactivity_Sim/input/RN220test", startTime, endTime);
    	StringBuilder data = new StringBuilder();
    	data.append("verification1 for NucleiSamplePredictiveSim.java calculations:                       " + System.getProperty("line.separator"));
    	data.append("Secular Equilibrium occurs between RA224 and RN220 because the decay constant, lambda" + System.getProperty("line.separator"));
    	data.append("of RN220 is much greater than that of RA224.  The decay constants are defined as:    " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("   lambda_RA224 = ln(2) / HALFLIFE_RA224  &  lambda_RN220 = ln(2) / HALFLIFE_RN220   " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("For these nuclei: lambda_RA224 = 2.19195E-6 & lambda_RN220 = 1.24667E-2              " + System.getProperty("line.separator"));
    	data.append("In the case of secular equilibrium, the number of child nuclei (in this case RN220)  " + System.getProperty("line.separator"));
    	data.append("remains at a fixed ratio to the number of parent nuclei.  This relationship is       " + System.getProperty("line.separator"));
    	data.append("governed by the equation: N_RN220 = N_RA224 x lambda_RA224/lambda_RN220              " + System.getProperty("line.separator"));
    	data.append("where N is the total number of nuclei of each type.  In order for this relationship  " + System.getProperty("line.separator"));
    	data.append("to remain true, the number of decay events of the parent nuclei must equal the number" + System.getProperty("line.separator"));
    	data.append("of decay events of the child nuclei such that:  NDECAYS_RA224 = NDECAYS_RN220        " + System.getProperty("line.separator"));
    	data.append("To prove this program's agreement with these theories we select a starting quantity  " + System.getProperty("line.separator"));
    	data.append("of RA224 nuclei:                                                                     " + System.getProperty("line.separator"));
    	data.append("N_RA224 = " + numRA224 + System.getProperty("line.separator"));
    	data.append("Then from theory, N_RN220 = " + numRA224 + "x(2.19195E-6/1.24667E-2) = " + (numRA224*2.19195*Math.pow(10, -6)/0.0124667) + System.getProperty("line.separator"));
    	data.append("Next, a sufficiently small time period is selected such that N_RA224 doesn't decrease" + System.getProperty("line.separator"));
    	data.append("by much.  Let us choose a time period of 100 seconds.                                " + System.getProperty("line.separator"));
    	data.append("The number of decays of RA224 which occur in 100 seconds from a sample of 1E5 is     " + System.getProperty("line.separator"));
    	data.append("given by:  NDECAYS_RA224 = "+numRA224+" x(1 - e^(-100*lambda_RA224) = 21.92               " + System.getProperty("line.separator"));
    	data.append("Of course, in reality the number of decay events has to be a whole number, but for   " + System.getProperty("line.separator"));
    	data.append("theory, it is acceptable to use the fraction, because it is an average amount that   " + System.getProperty("line.separator"));
    	data.append("would occur during this time period.  Since NDECAY_RA224 = NDECAY_RN220, the number  " + System.getProperty("line.separator"));
    	data.append("of RN220 events is also 21.92.  The energy released by each RA224 decay is 5.789 MeV " + System.getProperty("line.separator"));
    	data.append("and the energy released by each RN220 decay is 6.404 MeV.  Therefore the total energy" + System.getProperty("line.separator"));
    	data.append("released is: NDECAY_RA224 x 5.789 + NDECAY_RN220 x 6.404 = 267.27 MeV                " + System.getProperty("line.separator"));
    	data.append("and the average radiated power is: Energy/time = 267.27/100 = 2.6727 MeV/s           " + System.getProperty("line.separator"));
    	data.append("Now let us see if the program can produce the same values:                            " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("For a standard resolution of 10 we will get: " + System.getProperty("line.separator"));
    	data.append("Radiated Power = " + prPredictiveSample.puGetRadiatedPowerOverTimeRange(startTime, endTime)+ " MeV/s" + System.getProperty("line.separator"));
    	data.append("Total Energy = " + prPredictiveSample.puGetEnergySumOverTimeRange(startTime, endTime) + " MeV" + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append(prPredictiveSample.puGetAllEndTimeNucleiCounts());
    	data.append(System.getProperty("line.separator"));
    	data.append(prPredictiveSample.puGetAllEventCountsOverTimeRangeByNuclei(startTime, endTime));
    	data.append(System.getProperty("line.separator"));
    	data.append("Next we see how the program is distributing the event energies into smaller groups as defined by the (resolution)" + System.getProperty("line.separator"));
    	double sum = 0;
    	for(int x = 1; x <= 1000 ;x = x*10) {
    		sum = 0;
    		data.append("For resolution set to " + x + " we get:" + System.getProperty("line.separator"));
    		prPredictiveSample = new NucleiSamplePredictiveSim(numRA224,"/home/user/git/Radioactivity_Sim/input/RA224test",startTime,endTime,x);
    		prPredictiveSample.puAddSpecies(17.58, "/home/user/git/Radioactivity_Sim/input/RN220test", startTime, endTime);
    		for(int y = 0; y < x; y++) {
    			data.append("Energy (t = "+(startTime+(y+1)*(endTime-startTime)/x)+") = " + prPredictiveSample.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x)+ " MeV" + System.getProperty("line.separator"));
    			sum += prPredictiveSample.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x);
    		}
    		data.append("Which all adds up to: " + sum + " MeV" + System.getProperty("line.separator"));
    		data.append(System.getProperty("line.separator"));
    	}
    	//retrieve the calculation time
    	data.append("Calculation Time = " + (Calendar.getInstance().getTime().getTime() - now) + System.getProperty("line.separator"));
    	data.append(">: " + System.getProperty("line.separator"));
    	prScrivener.puOpenNewFile(file);
    	fileNum = prScrivener.puGetNumFiles();
    	prScrivener.puAppendStringToFile(fileNum-1, data.toString());
    	prScrivener.puCloseFile(fileNum-1);
    	//write the output to prTextPane
    	currentText = currentText + "\n" + data.toString();
    	prTextPane.setText(currentText);

    }

    private void prVerification2(String file){
    	//Runs the verification2 test script and outputs it to (file)

    	double numU238 = Math.pow(10, 26);
    	double numTH234 = 1.4776*Math.pow(10, 14);
    	double numPA234 = 1.7116*Math.pow(10, 12);
    	double startTime = 0, endTime = Math.pow(10,13);
    	int fileNum = 0;
    	//initialize time tracking
    	Calendar cal = Calendar.getInstance();
    	Date date = cal.getTime();
    	long now = date.getTime();
    	//read text in from prTextField
    	String commandString = prTextField.getText();
    	prTextField.setText("");
    	//print command in prTextPane
    	String currentText = prTextPane.getText();
    	currentText = currentText + "\n" + commandString;
    	prTextPane.setText(currentText);

    	int resolution = 10;
    	prPredictiveSample = new NucleiSamplePredictiveSim(numU238,"/home/user/git/Radioactivity_Sim/input/U238test",startTime,endTime,resolution);
    	prPredictiveSample.puAddSpecies(numTH234, "/home/user/git/Radioactivity_Sim/input/TH234test", startTime, endTime);
    	prPredictiveSample.puAddSpecies(numPA234, "/home/user/git/Radioactivity_Sim/input/PA234test", startTime, endTime);
    	StringBuilder data = new StringBuilder();
    	data.append("verification2 for NucleiSamplePredictiveSim.java calculations:                       " + System.getProperty("line.separator"));
    	data.append("This verification is intended to show that the Radioactive_Sim program does not      " + System.getProperty("line.separator"));
    	data.append("create or propagate error in decay products that are further down a chain.           " + System.getProperty("line.separator"));
    	data.append("Secular Equilibrium occurs between U238 and the next two products on its decay chain " + System.getProperty("line.separator"));
    	data.append("TH234 and PA234 decay constant, lambda of PA234 is much greater than that of TH234   " + System.getProperty("line.separator"));
    	data.append("and the decay constant of TH234 is much greater than that of U238.                   " + System.getProperty("line.separator"));
    	data.append("The decay constants for these elements are defined as:                               " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("lambda_RA224 = ln(2) / HALFLIFE_RA224 = 4.919 x 10^-19                               " + System.getProperty("line.separator"));
    	data.append("lambda_RN220 = ln(2) / HALFLIFE_RN220 = 3.329 x 10^-7                                " + System.getProperty("line.separator"));
    	data.append("lambda_PA234 = ln(2) / HALFLIFE_PA234 = 2.874 x 10^-5                                " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("In the case of secular equilibrium, the number of child nuclei (TH234 & PA234)       " + System.getProperty("line.separator"));
    	data.append("remains at a fixed ratio to the number of parent nuclei.  This relationship is       " + System.getProperty("line.separator"));
    	data.append("governed by the equations:                                                           " + System.getProperty("line.separator"));
    	data.append("N_TH234 = N_U238 x lambda_U238/lambda_TH234                                          " + System.getProperty("line.separator"));
    	data.append("N_PA234 = N_TH234 x lambda_TH234/lambda_PA234                                        " + System.getProperty("line.separator"));
    	data.append("where N is the total number of nuclei of each type.  In order for this relationship  " + System.getProperty("line.separator"));
    	data.append("to remain true, the number of decay events of the parent nuclei must equal the number" + System.getProperty("line.separator"));
    	data.append("of decay events of the child nuclei such that:  ND_U238 = ND_TH234 = ND_PA234        " + System.getProperty("line.separator"));
    	data.append("To prove this program's agreement with these theories we select a starting quantity  " + System.getProperty("line.separator"));
    	data.append("of U238 nuclei:                                                                     " + System.getProperty("line.separator"));
    	data.append("N_U238 = " + numU238 + System.getProperty("line.separator"));
    	data.append("Then: N_TH234 = " + numU238 + "x(4.919E-19/3.329E-7) = 1.4776 x 10^14                " + System.getProperty("line.separator"));
    	data.append("And: N_PA234 = " + numU238 + "x(4.919E-19/2.874E-5) = 1.7116 x 10^12                 " + System.getProperty("line.separator"));
    	data.append("Next, a sufficiently small time period is selected such that N_U238 doesn't decrease " + System.getProperty("line.separator"));
    	data.append("by much.  Let us choose a time period of 10^13 seconds (317,100 years).              " + System.getProperty("line.separator"));
    	data.append("The number of decays of U238 which occur in 10^13 seconds from a sample of 1E26 is   " + System.getProperty("line.separator"));
    	data.append("given by:  ND_U238 = "+numU238+" x(1 - e^(-10^-13*lambda_U238) = 4.919 x 10^20       " + System.getProperty("line.separator"));
    	data.append("Since ND_U238 = ND_TH234 = ND_PA234, the number of the other events is also 4.919E20." + System.getProperty("line.separator"));
    	data.append("The energy released by each U238 decay is 4.2697 MeV, TH234 decays release 0.273 MeV " + System.getProperty("line.separator"));
    	data.append("and the energy released by each PA234 decay is 2.195 MeV.  Therefore the total energy" + System.getProperty("line.separator"));
    	data.append("released is: ND_U238 x (4.2697 + 0.273 + 2.195) = 3.314 x 10^21 MeV                  " + System.getProperty("line.separator"));
    	data.append("and the average radiated power is: Energy/time = 3.314E21/1E13 = 3.3143E8 MeV/s      " + System.getProperty("line.separator"));
    	data.append("Now let us see if this program can produce the same values:                          " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
        data.append("For a standard resolution of 10 we will get: " + System.getProperty("line.separator"));
        data.append("Radiated Power = " + prPredictiveSample.puGetRadiatedPowerOverTimeRange(startTime, endTime)+ " MeV/s" + System.getProperty("line.separator"));
    	data.append("Total Energy = " + prPredictiveSample.puGetEnergySumOverTimeRange(startTime, endTime) + " MeV" + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append(prPredictiveSample.puGetAllEndTimeNucleiCounts());
    	data.append(System.getProperty("line.separator"));
    	data.append(prPredictiveSample.puGetAllEventCountsOverTimeRangeByNuclei(startTime, endTime));
    	data.append(System.getProperty("line.separator"));
    	data.append("Next we see how the program is distributing the event energies into smaller groups as defined by the (resolution)" + System.getProperty("line.separator"));
    	double sum = 0;
    	for(int x = 1; x <= 1000;x = 10*x) {
    		sum = 0;
    		data.append("For resolution set to " + x + " we get:" + System.getProperty("line.separator"));
    		prPredictiveSample = new NucleiSamplePredictiveSim(numU238,"/home/user/git/Radioactivity_Sim/input/U238test",startTime,endTime,x);
    		prPredictiveSample.puAddSpecies(numTH234, "/home/user/git/Radioactivity_Sim/input/TH234test", startTime, endTime);
    		prPredictiveSample.puAddSpecies(numPA234, "/home/user/git/Radioactivity_Sim/input/PA234test", startTime, endTime);
    		for(int y = 0; y < x; y++) {
    			data.append("Energy (t = "+(startTime+(y+1)*(endTime-startTime)/x)+") = " + prPredictiveSample.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x)+ " MeV" + System.getProperty("line.separator"));
    			sum += prPredictiveSample.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x);
    		}
    		data.append("Which all adds up to: " + sum + " MeV" + System.getProperty("line.separator"));
    		data.append(System.getProperty("line.separator"));
    	}
    	//retrieve the calculation time
    	data.append("Calculation Time = " + (Calendar.getInstance().getTime().getTime() - now) + System.getProperty("line.separator"));
    	data.append(">: " + System.getProperty("line.separator"));
    	prScrivener.puOpenNewFile(file);
    	fileNum = prScrivener.puGetNumFiles();
    	prScrivener.puAppendStringToFile(fileNum-1, data.toString());
    	prScrivener.puCloseFile(fileNum-1);
    	//write the output to prTextPane
    	currentText = currentText + "\n" + data.toString();
    	prTextPane.setText(currentText);
    }

    private void prVerification3(String file){
    	//Runs the verification3 test script and outputs it to (file)

    	double startTime = 0, endTime = 0;
    	int fileNum = 0;
    	//initialize time tracking
    	Calendar cal = Calendar.getInstance();
    	Date date = cal.getTime();
    	long now = date.getTime();
    	//read text in from prTextField
    	String commandString = prTextField.getText();
    	prTextField.setText("");
    	//print command in prTextPane
    	String currentText = prTextPane.getText();
    	currentText = currentText + "\n" + commandString;
    	prTextPane.setText(currentText);

    	int resolution = 10;
    	startTime = 0; endTime = 100;
    	prBruteForceSample = new NucleiSampleBruteForceSim(100000,"/home/user/git/Radioactivity_Sim/input/RA224test",startTime,endTime);
    	prBruteForceSample.puAddSpecies(18, "/home/user/git/Radioactivity_Sim/input/RN220test", startTime, endTime);
    	StringBuilder data = new StringBuilder();
    	data.append("verification3 for NucleiSampleBruteForceSim.java calculations:                       " + System.getProperty("line.separator"));
    	data.append("Secular Equilibrium occurs between RA224 and RN220 because the decay constant, lambda" + System.getProperty("line.separator"));
    	data.append("of RN220 is much greater than that of RA224.  The decay constants are defined as:    " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("   lambda_RA224 = ln(2) / HALFLIFE_RA224  &  lambda_RN220 = ln(2) / HALFLIFE_RN220   " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("For these nuclei: lambda_RA224 = 2.19195E-6 & lambda_RN220 = 1.24667E-2              " + System.getProperty("line.separator"));
    	data.append("In the case of secular equilibrium, the number of child nuclei (in this case RN220)  " + System.getProperty("line.separator"));
    	data.append("remains at a fixed ratio to the number of parent nuclei.  This relationship is       " + System.getProperty("line.separator"));
    	data.append("governed by the equation: N_RN220 = N_RA224 x lambda_RA224/lambda_RN220              " + System.getProperty("line.separator"));
    	data.append("where N is the total number of nuclei of each type.  In order for this relationship  " + System.getProperty("line.separator"));
    	data.append("to remain true, the number of decay events of the parent nuclei must equal the number" + System.getProperty("line.separator"));
    	data.append("of decay events of the child nuclei such that:  NDECAYS_RA224 = NDECAYS_RN220        " + System.getProperty("line.separator"));
    	data.append("To prove this program's agreement with these theories we select a starting quantity  " + System.getProperty("line.separator"));
    	data.append("of RA224 nuclei:                                                                     " + System.getProperty("line.separator"));
    	data.append("N_RA224 = " + 100000 + System.getProperty("line.separator"));
    	data.append("Then from theory, N_RN220 = " + 100000 + "x(2.19195E-6/1.24667E-2) = " + (100000*2.19195*Math.pow(10, -6)/0.0124667) + System.getProperty("line.separator"));
    	data.append("Next, a sufficiently small time period is selected such that N_RA224 doesn't decrease" + System.getProperty("line.separator"));
    	data.append("by much.  Let us choose a time period of 100 seconds.                                " + System.getProperty("line.separator"));
    	data.append("The number of decays of RA224 which occur in 100 seconds from a sample of 1E5 is     " + System.getProperty("line.separator"));
    	data.append("given by:  NDECAYS_RA224 = "+100000+" x(1 - e^(-100*lambda_RA224) = 21.92               " + System.getProperty("line.separator"));
    	data.append("Of course, in reality the number of decay events has to be a whole number, but for   " + System.getProperty("line.separator"));
    	data.append("theory, it is acceptable to use the fraction, because it is an average amount that   " + System.getProperty("line.separator"));
    	data.append("would occur during this time period.  Since NDECAY_RA224 = NDECAY_RN220, the number  " + System.getProperty("line.separator"));
    	data.append("of RN220 events is also 21.92.  The energy released by each RA224 decay is 5.789 MeV " + System.getProperty("line.separator"));
    	data.append("and the energy released by each RN220 decay is 6.404 MeV.  Therefore the total energy" + System.getProperty("line.separator"));
    	data.append("released is: NDECAY_RA224 x 5.789 + NDECAY_RN220 x 6.404 = 267.27 MeV                " + System.getProperty("line.separator"));
    	data.append("and the average radiated power is: Energy/time = 267.27/100 = 2.6727 MeV/s           " + System.getProperty("line.separator"));
    	data.append("Now let us see if the program can produce the same values:                            " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	prScrivener.puOpenNewFile(file);
    	fileNum = prScrivener.puGetNumFiles();
    	data.append("From the program we will get: " + System.getProperty("line.separator"));
    	double sumEnergy8 = 0;
    	double sumPower8 = 0;
    	for (int x = 0; x < 20; x++){
    		prBruteForceSample = new NucleiSampleBruteForceSim(100000,"/home/user/git/Radioactivity_Sim/input/RA224test",startTime,endTime);
    		prBruteForceSample.puAddSpecies(18, "/home/user/git/Radioactivity_Sim/input/RN220test", startTime, endTime);
    		data.append("Attempt No. " + (x+1) + System.getProperty("line.separator"));
    		data.append("Radiated Power = " + prBruteForceSample.puGetRadiatedPowerOverTimeRange(startTime, endTime) + " MeV/s" + System.getProperty("line.separator"));
    		data.append("Total Energy = " + prBruteForceSample.puGetEnergySumOverTimeRange(startTime, endTime) + " MeV" + System.getProperty("line.separator"));
    		data.append(System.getProperty("line.separator"));
    		sumEnergy8 += prBruteForceSample.puGetEnergySumOverTimeRange(startTime, endTime);
    		sumPower8 += prBruteForceSample.puGetRadiatedPowerOverTimeRange(startTime, endTime);
    	}
    	data.append("Average:" + System.getProperty("line.separator"));
		data.append("Ave. Radiated Power = " + (sumPower8/20.0) + " MeV/s" + System.getProperty("line.separator"));
		data.append("Ave. Total Energy = " + (sumEnergy8/20.0) + " MeV" + System.getProperty("line.separator"));
		data.append(System.getProperty("line.separator"));
    	//retrieve the calculation time
    	data.append("Calculation Time = " + (Calendar.getInstance().getTime().getTime() - now) + System.getProperty("line.separator"));
    	data.append(">: " + System.getProperty("line.separator"));
    	prScrivener.puOpenNewFile(file);
    	fileNum = prScrivener.puGetNumFiles();
    	prScrivener.puAppendStringToFile(fileNum-1, data.toString());
    	prScrivener.puCloseFile(fileNum-1);
    	//write the output to prTextPane
    	currentText = currentText + "\n" + data.toString();
    	prTextPane.setText(currentText);
    }

    private void prVerification4(String file){
    	//Runs the verification4 test script and outputs it to (file)

    	double startTime = 0, endTime = 0;
    	int fileNum = 0;
    	//initialize time tracking
    	Calendar cal = Calendar.getInstance();
    	Date date = cal.getTime();
    	long now = date.getTime();
    	//read text in from prTextField
    	String commandString = prTextField.getText();
    	prTextField.setText("");
    	//print command in prTextPane
    	String currentText = prTextPane.getText();
    	currentText = currentText + "\n" + commandString;
    	prTextPane.setText(currentText);

    	int resolution = 10;
    	startTime = 3*1.409*Math.pow(10,18)-Math.pow(10,13); endTime = 3*1.409*Math.pow(10,18);
    	prPredictiveSample = new NucleiSamplePredictiveSim(Math.pow(10, 26),"/home/user/git/Radioactivity_Sim/input/U238",startTime,endTime,1);
    	StringBuilder data = new StringBuilder();
    	data.append("verification4 for NucleiSamplePredictiveSim.java calculations:                       " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("In this verification a single pure sample of 10^26 U238 nuclei are allowed to decay  " + System.getProperty("line.separator"));
    	data.append("for three half-lives and the decays which occur between t =" + startTime + " and " + endTime + System.getProperty("line.separator"));
    	data.append("are examined and verified for accuracy with the following hand calcs:" + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("Nuclei Parent       ParentDecays   EndTimeCount    StartTimeCount   PredictedDecays  " + System.getProperty("line.separator"));
    	data.append("U238   N/A          N/A            1.250000002E25  1.250006151E25   6.149298E19      " + System.getProperty("line.separator"));
    	data.append("TH234  U238         6.149298E19    1.847267569E13  1.847276656E13   6.149298E19      " + System.getProperty("line.separator"));
    	data.append("PA234  TH234        6.149298E19    2.139815476E11  2.139826002E11   6.149298E19      " + System.getProperty("line.separator"));
    	data.append("U234   PA234        6.149298E19    6.868472812E19  6.868506601E19   6.149331E19      " + System.getProperty("line.separator"));
    	data.append("TH230  U234         6.149331E19    2.108957662E19  2.108968037E19   6.149342E19      " + System.getProperty("line.separator"));
    	data.append("RA226  TH230        6.149342E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
    	data.append("RN222  RA226        6.149342E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
    	data.append("PO218  RN222        6.149342E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
    	data.append("PB214  PO218        6.149342E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
    	data.append("BI214  PB214        6.149342E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
    	data.append("TI210  BI214        1.291362E16    1.250000002E25  1.250006151E25   1.291362E16      " + System.getProperty("line.separator"));
    	data.append("PB210  PO214,TI210  6.148051E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
    	data.append("PO214  BI214        6.148051E19    1.250000002E25  1.250006151E25   6.148051E19      " + System.getProperty("line.separator"));
    	data.append("PB209  TI210        1.162226E12    1.250000002E25  1.250006151E25   1.162226E12      " + System.getProperty("line.separator"));
    	data.append("BI209  PB209        1.162226E12    1.250000002E25  1.250006151E25   1.213845E12      " + System.getProperty("line.separator"));
    	data.append("BI210  PB210        6.148530E19    1.250000002E25  1.250006151E25   6.148530E19      " + System.getProperty("line.separator"));
    	data.append("TI206  BI210        8.116060E15    1.250000002E25  1.250006151E25   8.116060E15      " + System.getProperty("line.separator"));
    	data.append("PO210  BI210        6.147718E19    1.250000002E25  1.250006151E25   6.147718E19      " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("Energy of BI214 decays is 6.149342E19x(0.99979x3.27[MeV]+0.00021x5.6213[MeV]) = " + (6.149342*Math.pow(10, 19)*(0.99979*3.27+0.00021*5.6213)) + " [MeV]" + System.getProperty("line.separator"));
    	data.append("and the energy of PO214 decays is 6148051E19x7.83346[MeV] = " + (6.148051*Math.pow(10,19)*7.83346) + " [MeV]" + System.getProperty("line.separator"));
    	data.append("Which adds up to: " + ((6.148051*Math.pow(10,19)*7.83346)+(6.149342*Math.pow(10, 19)*(0.99979*3.27+0.00021*5.6213))) + " [MeV]" + System.getProperty("line.separator"));
    	data.append("Now let's see what the program comes up with: " + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append("From the program we will get: " + System.getProperty("line.separator"));
    	data.append("Radiated Power (for BI214 and PO214) = " + (prPredictiveSample.puGetRadiatedPowerForStartNucleusOverTimeRange(startTime, endTime,"BI214")+prPredictiveSample.puGetRadiatedPowerForStartNucleusOverTimeRange(startTime, endTime, "PO214"))+ " MeV/s" + System.getProperty("line.separator"));
    	data.append("Total Energy (for BI214 and PO214) = " + (prPredictiveSample.puGetEnergySumForStartingNucleusOverTimeRange(startTime, endTime, "BI214")+prPredictiveSample.puGetEnergySumForStartingNucleusOverTimeRange(startTime, endTime, "PO214")) + " MeV" + System.getProperty("line.separator"));
    	data.append(System.getProperty("line.separator"));
    	data.append(prPredictiveSample.puGetAllStartTimeNucleiCounts());
    	data.append(System.getProperty("line.separator"));
    	data.append(prPredictiveSample.puGetAllEndTimeNucleiCounts());
    	data.append(System.getProperty("line.separator"));
    	data.append(prPredictiveSample.puGetAllEventCountsOverTimeRangeByNuclei(startTime, endTime));
    	data.append(System.getProperty("line.separator"));
    	double sum = 0;
    	for(int x = 1; x <= 1000;x = 10*x) {
    		sum = 0;
    		data.append("For resolution set to " + x + " we get:" + System.getProperty("line.separator"));
    		prPredictiveSample = new NucleiSamplePredictiveSim(Math.pow(10, 26),"/home/user/git/Radioactivity_Sim/input/U238",startTime,endTime,x);
    		for(double y = 0; y < x; y++) {
    			data.append("Energy (BI214 & PO214)(t = "+(startTime+(y+1)*(endTime-startTime)/x)+") = " + (prPredictiveSample.puGetEnergySumForStartingNucleusOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x,"BI214")+prPredictiveSample.puGetEnergySumForStartingNucleusOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x, "PO214")) + " MeV" + System.getProperty("line.separator"));
    			sum += (prPredictiveSample.puGetEnergySumForStartingNucleusOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x,"BI214")+prPredictiveSample.puGetEnergySumForStartingNucleusOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x, "PO214"));
    		}
    		data.append("Which all adds up to: " + sum + " MeV" + System.getProperty("line.separator"));
    		data.append(System.getProperty("line.separator"));
    	}
    	//retrieve the calculation time
    	data.append("Calculation Time = " + (Calendar.getInstance().getTime().getTime() - now) + System.getProperty("line.separator"));
    	data.append(">: " + System.getProperty("line.separator"));
    	prScrivener.puOpenNewFile(file);
    	fileNum = prScrivener.puGetNumFiles();
    	prScrivener.puAppendStringToFile(fileNum-1, data.toString());
    	prScrivener.puCloseFile(fileNum-1);
    	//write the output to prTextPane
    	currentText = currentText + "\n" + data.toString();
    	prTextPane.setText(currentText);
    }

}



//private void prWriteAllEventData(String file) {
////Writes all event data (Warning! limited by maximum size of java String's!)
//
//String data = prPredictiveSample.puGetAllEventData();
//scrivener.puOpenNewFile(file);
//fileNum = scrivener.puGetNumFiles();
//scrivener.puAppendStringToFile(fileNum-1, data);
//scrivener.puCloseFile(fileNum-1);
//}


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

        //Writes the first parsed (RuleSet) for the (NucleiSamplePredictiveSim) to file
//    	String file5 = "/home/user/git/Radioactivity_Sim/output/RuleSet";
//    	DecayChainRuleSet rules5 = test.puGetDecayChainRuleSet(0);
//    	scrivener.puOpenNewFile(file5);
//      fileNum = scrivener.puGetNumFiles();
//      scrivener.puAppendStringToFile(fileNum-1, rules5.puOutputDecayChainRuleSet());
//      scrivener.puCloseFile(fileNum-1);

//    	//Verify if the calculations agree with theory:
//    	num = Math.pow(10, 5);
//    	startTime = 0; endTime = 100; resolution = 1;
//    	NucleiSamplePredictiveSim test6 = new NucleiSamplePredictiveSim(num,"/home/user/git/Radioactivity_Sim/input/RA224test",startTime,endTime,resolution);
//    	test6.puAddSpecies(17.58, "/home/user/git/Radioactivity_Sim/input/RN220test", startTime, endTime);
//    	String file6 = "/home/user/git/Radioactivity_Sim/proofs/verification1";
//    	StringBuilder data6 = new StringBuilder();
//    	data6.append("verification1 for NucleiSamplePredictiveSim.java calculations:                       " + System.getProperty("line.separator"));
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
//    	fileNum = scrivener.puGetNumFiles();
//    	data6.append("For a standard resolution of 10 we will get: " + System.getProperty("line.separator"));
//    	data6.append("Radiated Power = " + test6.puGetRadiatedPowerOverTimeRange(startTime, endTime)+ " MeV/s" + System.getProperty("line.separator"));
//    	data6.append("Total Energy = " + test6.puGetEnergySumOverTimeRange(startTime, endTime) + " MeV" + System.getProperty("line.separator"));
//    	data6.append(System.getProperty("line.separator"));
//    	data6.append(test6.puGetAllEndTimeNucleiCounts());
//    	data6.append(System.getProperty("line.separator"));
//    	data6.append(test6.puGetAllEventCountsOverTimeRangeByNuclei(startTime, endTime));
//    	data6.append(System.getProperty("line.separator"));
//    	data6.append("Next we see how the program is distributing the event energies into smaller groups as defined by the (resolution)" + System.getProperty("line.separator"));
//    	double sum6 = 0;
//    	for(int x = 1; x <= 1000 ;x = x*10) {
//    		sum6 = 0;
//    		data6.append("For resolution set to " + x + " we get:" + System.getProperty("line.separator"));
//    		test6 = new NucleiSamplePredictiveSim(num,"/home/user/git/Radioactivity_Sim/input/RA224test",startTime,endTime,x);
//        	test6.puAddSpecies(17.58, "/home/user/git/Radioactivity_Sim/input/RN220test", startTime, endTime);
//    		for(int y = 0; y < x; y++) {
//    			data6.append("Energy (t = "+(startTime+(y+1)*(endTime-startTime)/x)+") = " + test6.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x)+ " MeV" + System.getProperty("line.separator"));
//    			sum6 += test6.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x);
//    		}
//    		data6.append("Which all adds up to: " + sum6 + " MeV" + System.getProperty("line.separator"));
//    		data6.append(System.getProperty("line.separator"));
//    	}
//    	scrivener.puAppendStringToFile(fileNum-1, data6.toString());
//    	scrivener.puCloseFile(fileNum-1);

//    	//Verify if the calculations agree with theory:
//    	double numU238 = Math.pow(10, 26);
//    	double numTH234 = 1.4776*Math.pow(10, 14);
//    	double numPA234 = 1.7116*Math.pow(10, 12);
//    	startTime = 0; endTime = Math.pow(10,13); resolution = 10;
//    	NucleiSamplePredictiveSim test7 = new NucleiSamplePredictiveSim(numU238,"/home/user/git/Radioactivity_Sim/input/U238test",startTime,endTime,resolution);
//    	test7.puAddSpecies(numTH234, "/home/user/git/Radioactivity_Sim/input/TH234test", startTime, endTime);
//    	test7.puAddSpecies(numPA234, "/home/user/git/Radioactivity_Sim/input/PA234test", startTime, endTime);
//    	String file7 = "/home/user/git/Radioactivity_Sim/proofs/verification2";
//    	StringBuilder data7 = new StringBuilder();
//    	data7.append("verification2 for NucleiSamplePredictiveSim.java calculations:                       " + System.getProperty("line.separator"));
//    	data7.append("This verification is intended to show that the Radioactive_Sim program does not      " + System.getProperty("line.separator"));
//    	data7.append("create or propagate error in decay products that are further down a chain.           " + System.getProperty("line.separator"));
//    	data7.append("Secular Equilibrium occurs between U238 and the next two products on its decay chain " + System.getProperty("line.separator"));
//    	data7.append("TH234 and PA234 decay constant, lambda of PA234 is much greater than that of TH234   " + System.getProperty("line.separator"));
//    	data7.append("and the decay constant of TH234 is much greater than that of U238.                   " + System.getProperty("line.separator"));
//    	data7.append("The decay constants for these elements are defined as:                               " + System.getProperty("line.separator"));
//    	data7.append(System.getProperty("line.separator"));
//    	data7.append("lambda_RA224 = ln(2) / HALFLIFE_RA224 = 4.919 x 10^-19                               " + System.getProperty("line.separator"));
//    	data7.append("lambda_RN220 = ln(2) / HALFLIFE_RN220 = 3.329 x 10^-7                                " + System.getProperty("line.separator"));
//    	data7.append("lambda_PA234 = ln(2) / HALFLIFE_PA234 = 2.874 x 10^-5                                " + System.getProperty("line.separator"));
//    	data7.append(System.getProperty("line.separator"));
//    	data7.append("In the case of secular equilibrium, the number of child nuclei (TH234 & PA234)       " + System.getProperty("line.separator"));
//    	data7.append("remains at a fixed ratio to the number of parent nuclei.  This relationship is       " + System.getProperty("line.separator"));
//    	data7.append("governed by the equations:                                                           " + System.getProperty("line.separator"));
//    	data7.append("N_TH234 = N_U238 x lambda_U238/lambda_TH234                                          " + System.getProperty("line.separator"));
//    	data7.append("N_PA234 = N_TH234 x lambda_TH234/lambda_PA234                                        " + System.getProperty("line.separator"));
//    	data7.append("where N is the total number of nuclei of each type.  In order for this relationship  " + System.getProperty("line.separator"));
//    	data7.append("to remain true, the number of decay events of the parent nuclei must equal the number" + System.getProperty("line.separator"));
//    	data7.append("of decay events of the child nuclei such that:  ND_U238 = ND_TH234 = ND_PA234        " + System.getProperty("line.separator"));
//    	data7.append("To prove this program's agreement with these theories we select a starting quantity  " + System.getProperty("line.separator"));
//    	data7.append("of U238 nuclei:                                                                     " + System.getProperty("line.separator"));
//    	data7.append("N_U238 = " + numU238 + System.getProperty("line.separator"));
//    	data7.append("Then: N_TH234 = " + numU238 + "x(4.919E-19/3.329E-7) = 1.4776 x 10^14                " + System.getProperty("line.separator"));
//    	data7.append("And: N_PA234 = " + numU238 + "x(4.919E-19/2.874E-5) = 1.7116 x 10^12                 " + System.getProperty("line.separator"));
//    	data7.append("Next, a sufficiently small time period is selected such that N_U238 doesn't decrease " + System.getProperty("line.separator"));
//    	data7.append("by much.  Let us choose a time period of 10^13 seconds (317,100 years).              " + System.getProperty("line.separator"));
//    	data7.append("The number of decays of U238 which occur in 10^13 seconds from a sample of 1E26 is   " + System.getProperty("line.separator"));
//    	data7.append("given by:  ND_U238 = "+numU238+" x(1 - e^(-10^-13*lambda_U238) = 4.919 x 10^20       " + System.getProperty("line.separator"));
//    	data7.append("Since ND_U238 = ND_TH234 = ND_PA234, the number of the other events is also 4.919E20." + System.getProperty("line.separator"));
//    	data7.append("The energy released by each U238 decay is 4.2697 MeV, TH234 decays release 0.273 MeV " + System.getProperty("line.separator"));
//    	data7.append("and the energy released by each PA234 decay is 2.195 MeV.  Therefore the total energy" + System.getProperty("line.separator"));
//    	data7.append("released is: ND_U238 x (4.2697 + 0.273 + 2.195) = 3.314 x 10^21 MeV                  " + System.getProperty("line.separator"));
//    	data7.append("and the average radiated power is: Energy/time = 3.314E21/1E13 = 3.3143E8 MeV/s      " + System.getProperty("line.separator"));
//    	data7.append("Now let us see if this program can produce the same values:                          " + System.getProperty("line.separator"));
//    	data7.append(System.getProperty("line.separator"));
//    	scrivener.puOpenNewFile(file7);
//        fileNum = scrivener.puGetNumFiles();
//        data7.append("For a standard resolution of 10 we will get: " + System.getProperty("line.separator"));
//        data7.append("Radiated Power = " + test7.puGetRadiatedPowerOverTimeRange(startTime, endTime)+ " MeV/s" + System.getProperty("line.separator"));
//    	data7.append("Total Energy = " + test7.puGetEnergySumOverTimeRange(startTime, endTime) + " MeV" + System.getProperty("line.separator"));
//    	data7.append(System.getProperty("line.separator"));
//    	data7.append(test7.puGetAllEndTimeNucleiCounts());
//    	data7.append(System.getProperty("line.separator"));
//    	data7.append(test7.puGetAllEventCountsOverTimeRangeByNuclei(startTime, endTime));
//    	data7.append(System.getProperty("line.separator"));
//    	data7.append("Next we see how the program is distributing the event energies into smaller groups as defined by the (resolution)" + System.getProperty("line.separator"));
//    	double sum7 = 0;
//    	for(int x = 1; x <= 1000;x = 10*x) {
//    		sum7 = 0;
//    		data7.append("For resolution set to " + x + " we get:" + System.getProperty("line.separator"));
//    		test7 = new NucleiSamplePredictiveSim(numU238,"/home/user/git/Radioactivity_Sim/input/U238test",startTime,endTime,x);
//        	test7.puAddSpecies(numTH234, "/home/user/git/Radioactivity_Sim/input/TH234test", startTime, endTime);
//        	test7.puAddSpecies(numPA234, "/home/user/git/Radioactivity_Sim/input/PA234test", startTime, endTime);
//    		for(int y = 0; y < x; y++) {
//    			data7.append("Energy (t = "+(startTime+(y+1)*(endTime-startTime)/x)+") = " + test7.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x)+ " MeV" + System.getProperty("line.separator"));
//    			sum7 += test7.puGetEnergySumOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x);
//    		}
//    		data7.append("Which all adds up to: " + sum7 + " MeV" + System.getProperty("line.separator"));
//    		data7.append(System.getProperty("line.separator"));
//    	}
//    	scrivener.puAppendStringToFile(fileNum-1, data7.toString());
//    	scrivener.puCloseFile(fileNum-1);

//    	//Verify if the calculations agree with theory:
//    	startTime = 0; endTime = 100;
//    	NucleiSampleBruteForceSim test8 = new NucleiSampleBruteForceSim(100000,"/home/user/git/Radioactivity_Sim/input/RA224test",startTime,endTime);
//    	test8.puAddSpecies(18, "/home/user/git/Radioactivity_Sim/input/RN220test", startTime, endTime);
//    	String file8 = "/home/user/git/Radioactivity_Sim/proofs/verification3";
//    	StringBuilder data8 = new StringBuilder();
//    	data8.append("verification3 for NucleiSampleBruteForceSim.java calculations:                       " + System.getProperty("line.separator"));
//    	data8.append("Secular Equilibrium occurs between RA224 and RN220 because the decay constant, lambda" + System.getProperty("line.separator"));
//    	data8.append("of RN220 is much greater than that of RA224.  The decay constants are defined as:    " + System.getProperty("line.separator"));
//    	data8.append(System.getProperty("line.separator"));
//    	data8.append("   lambda_RA224 = ln(2) / HALFLIFE_RA224  &  lambda_RN220 = ln(2) / HALFLIFE_RN220   " + System.getProperty("line.separator"));
//    	data8.append(System.getProperty("line.separator"));
//    	data8.append("For these nuclei: lambda_RA224 = 2.19195E-6 & lambda_RN220 = 1.24667E-2              " + System.getProperty("line.separator"));
//    	data8.append("In the case of secular equilibrium, the number of child nuclei (in this case RN220)  " + System.getProperty("line.separator"));
//    	data8.append("remains at a fixed ratio to the number of parent nuclei.  This relationship is       " + System.getProperty("line.separator"));
//    	data8.append("governed by the equation: N_RN220 = N_RA224 x lambda_RA224/lambda_RN220              " + System.getProperty("line.separator"));
//    	data8.append("where N is the total number of nuclei of each type.  In order for this relationship  " + System.getProperty("line.separator"));
//    	data8.append("to remain true, the number of decay events of the parent nuclei must equal the number" + System.getProperty("line.separator"));
//    	data8.append("of decay events of the child nuclei such that:  NDECAYS_RA224 = NDECAYS_RN220        " + System.getProperty("line.separator"));
//    	data8.append("To prove this program's agreement with these theories we select a starting quantity  " + System.getProperty("line.separator"));
//    	data8.append("of RA224 nuclei:                                                                     " + System.getProperty("line.separator"));
//    	data8.append("N_RA224 = " + 100000 + System.getProperty("line.separator"));
//    	data8.append("Then from theory, N_RN220 = " + 100000 + "x(2.19195E-6/1.24667E-2) = " + (100000*2.19195*Math.pow(10, -6)/0.0124667) + System.getProperty("line.separator"));
//    	data8.append("Next, a sufficiently small time period is selected such that N_RA224 doesn't decrease" + System.getProperty("line.separator"));
//    	data8.append("by much.  Let us choose a time period of 100 seconds.                                " + System.getProperty("line.separator"));
//    	data8.append("The number of decays of RA224 which occur in 100 seconds from a sample of 1E5 is     " + System.getProperty("line.separator"));
//    	data8.append("given by:  NDECAYS_RA224 = "+100000+" x(1 - e^(-100*lambda_RA224) = 21.92               " + System.getProperty("line.separator"));
//    	data8.append("Of course, in reality the number of decay events has to be a whole number, but for   " + System.getProperty("line.separator"));
//    	data8.append("theory, it is acceptable to use the fraction, because it is an average amount that   " + System.getProperty("line.separator"));
//    	data8.append("would occur during this time period.  Since NDECAY_RA224 = NDECAY_RN220, the number  " + System.getProperty("line.separator"));
//    	data8.append("of RN220 events is also 21.92.  The energy released by each RA224 decay is 5.789 MeV " + System.getProperty("line.separator"));
//    	data8.append("and the energy released by each RN220 decay is 6.404 MeV.  Therefore the total energy" + System.getProperty("line.separator"));
//    	data8.append("released is: NDECAY_RA224 x 5.789 + NDECAY_RN220 x 6.404 = 267.27 MeV                " + System.getProperty("line.separator"));
//    	data8.append("and the average radiated power is: Energy/time = 267.27/100 = 2.6727 MeV/s           " + System.getProperty("line.separator"));
//    	data8.append("Now let us see if the program can produce the same values:                            " + System.getProperty("line.separator"));
//    	data8.append(System.getProperty("line.separator"));
//    	scrivener.puOpenNewFile(file8);
//    	fileNum = scrivener.puGetNumFiles();
//    	data8.append("From the program we will get: " + System.getProperty("line.separator"));
//    	double sumEnergy8 = 0;
//    	double sumPower8 = 0;
//    	for (int x = 0; x < 20; x++){
//    		test8 = new NucleiSampleBruteForceSim(100000,"/home/user/git/Radioactivity_Sim/input/RA224test",startTime,endTime);
//        	test8.puAddSpecies(18, "/home/user/git/Radioactivity_Sim/input/RN220test", startTime, endTime);
//    		data8.append("Attempt No. " + (x+1) + System.getProperty("line.separator"));
//    		data8.append("Radiated Power = " + test8.puGetRadiatedPowerOverTimeRange(startTime, endTime) + " MeV/s" + System.getProperty("line.separator"));
//    		data8.append("Total Energy = " + test8.puGetEnergySumOverTimeRange(startTime, endTime) + " MeV" + System.getProperty("line.separator"));
//    		data8.append(System.getProperty("line.separator"));
//    		sumEnergy8 += test8.puGetEnergySumOverTimeRange(startTime, endTime);
//    		sumPower8 += test8.puGetRadiatedPowerOverTimeRange(startTime, endTime);
//    	}
//    	data8.append("Average:" + System.getProperty("line.separator"));
//		data8.append("Ave. Radiated Power = " + (sumPower8/20.0) + " MeV/s" + System.getProperty("line.separator"));
//		data8.append("Ave. Total Energy = " + (sumEnergy8/20.0) + " MeV" + System.getProperty("line.separator"));
//		data8.append(System.getProperty("line.separator"));
//    	scrivener.puAppendStringToFile(fileNum-1, data8.toString());
//    	scrivener.puCloseFile(fileNum-1);
//
////    	//Writes a new set of Detailed Sieve code for the (DecayEvent) Class to increase the accuracy of the (NucleiSampleBruteForceSim) calculations
////		//Using if statements rather than a loop improves the speed of the calculations
////    	int count9 = 0;
////    	int test = 0;
////    	String file9 = "/home/user/git/Radioactivity_Sim/output/newSieve";
////    	StringBuilder data9 = new StringBuilder();
////    	for(int x = 0; x < 600;x++){
////    		test = Integer.valueOf(Long.toString(Math.round(1000000.0*(1-Math.exp(-(x+1)*Math.log(2.0)/600.0)))));
////    		count9++;
////    		if(x == 0){
////    			data9.append("if (testDetailedLife <= "+ test + ") {" + System.getProperty("line.separator"));
////    		} else {
////    			data9.append("} else if (testDetailedLife <= "+ test + ") {" + System.getProperty("line.separator"));
////    		}
////    		data9.append("    t = (x+("+x+"+seed3)/1000.0)*prHalfLife;" + System.getProperty("line.separator"));
////    	}
////    	data9.append("}" + System.getProperty("line.separator"));
////    	data9.append(count9);
////    	scrivener.puOpenNewFile(file9);
////    	fileNum = scrivener.puGetNumFiles();
////    	scrivener.puAppendStringToFile(fileNum-1, data9.toString());
////    	scrivener.puCloseFile(fileNum-1);
//
//    	//Verify if the calculations agree with theory:
//    	startTime = 3*1.409*Math.pow(10,18)-Math.pow(10,13); endTime = 3*1.409*Math.pow(10,18);
//    	NucleiSamplePredictiveSim test10 = new NucleiSamplePredictiveSim(Math.pow(10, 26),"/home/user/git/Radioactivity_Sim/input/U238",startTime,endTime,1);
//    	String file10 = "/home/user/git/Radioactivity_Sim/proofs/verification4";
//    	StringBuilder data10 = new StringBuilder();
//    	data10.append("verification4 for NucleiSamplePredictiveSim.java calculations:                       " + System.getProperty("line.separator"));
//    	data10.append(System.getProperty("line.separator"));
//    	data10.append("In this verification a single pure sample of 10^26 U238 nuclei are allowed to decay  " + System.getProperty("line.separator"));
//    	data10.append("for three half-lives and the decays which occur between t =" + startTime + " and " + endTime + System.getProperty("line.separator"));
//    	data10.append("are examined and verified for accuracy with the following hand calcs:" + System.getProperty("line.separator"));
//    	data10.append(System.getProperty("line.separator"));
//    	data10.append("Nuclei Parent       ParentDecays   EndTimeCount    StartTimeCount   PredictedDecays  " + System.getProperty("line.separator"));
//    	data10.append("U238   N/A          N/A            1.250000002E25  1.250006151E25   6.149298E19      " + System.getProperty("line.separator"));
//    	data10.append("TH234  U238         6.149298E19    1.847267569E13  1.847276656E13   6.149298E19      " + System.getProperty("line.separator"));
//    	data10.append("PA234  TH234        6.149298E19    2.139815476E11  2.139826002E11   6.149298E19      " + System.getProperty("line.separator"));
//    	data10.append("U234   PA234        6.149298E19    6.868472812E19  6.868506601E19   6.149331E19      " + System.getProperty("line.separator"));
//    	data10.append("TH230  U234         6.149331E19    2.108957662E19  2.108968037E19   6.149342E19      " + System.getProperty("line.separator"));
//    	data10.append("RA226  TH230        6.149342E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
//    	data10.append("RN222  RA226        6.149342E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
//    	data10.append("PO218  RN222        6.149342E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
//    	data10.append("PB214  PO218        6.149342E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
//    	data10.append("BI214  PB214        6.149342E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
//    	data10.append("TI210  BI214        1.291362E16    1.250000002E25  1.250006151E25   1.291362E16      " + System.getProperty("line.separator"));
//    	data10.append("PB210  PO214,TI210  6.148051E19    1.250000002E25  1.250006151E25   6.149342E19      " + System.getProperty("line.separator"));
//    	data10.append("PO214  BI214        6.148051E19    1.250000002E25  1.250006151E25   6.148051E19      " + System.getProperty("line.separator"));
//    	data10.append("PB209  TI210        1.162226E12    1.250000002E25  1.250006151E25   1.162226E12      " + System.getProperty("line.separator"));
//    	data10.append("BI209  PB209        1.162226E12    1.250000002E25  1.250006151E25   1.213845E12      " + System.getProperty("line.separator"));
//    	data10.append("BI210  PB210        6.148530E19    1.250000002E25  1.250006151E25   6.148530E19      " + System.getProperty("line.separator"));
//    	data10.append("TI206  BI210        8.116060E15    1.250000002E25  1.250006151E25   8.116060E15      " + System.getProperty("line.separator"));
//    	data10.append("PO210  BI210        6.147718E19    1.250000002E25  1.250006151E25   6.147718E19      " + System.getProperty("line.separator"));
//    	data10.append(System.getProperty("line.separator"));
//    	data10.append("Energy of BI214 decays is 6.149342E19x(0.99979x3.27[MeV]+0.00021x5.6213[MeV]) = " + (6.149342*Math.pow(10, 19)*(0.99979*3.27+0.00021*5.6213)) + " [MeV]" + System.getProperty("line.separator"));
//    	data10.append("and the energy of PO214 decays is 6148051E19x7.83346[MeV] = " + (6.148051*Math.pow(10,19)*7.83346) + " [MeV]" + System.getProperty("line.separator"));
//    	data10.append("Which adds up to: " + ((6.148051*Math.pow(10,19)*7.83346)+(6.149342*Math.pow(10, 19)*(0.99979*3.27+0.00021*5.6213))) + " [MeV]" + System.getProperty("line.separator"));
//    	data10.append("Now let's see what the program comes up with: " + System.getProperty("line.separator"));
//    	data10.append(System.getProperty("line.separator"));
//    	scrivener.puOpenNewFile(file10);
//    	fileNum = scrivener.puGetNumFiles();
//    	data10.append("From the program we will get: " + System.getProperty("line.separator"));
//    	data10.append("Radiated Power (for BI214 and PO214) = " + (test10.puGetRadiatedPowerForStartNucleusOverTimeRange(startTime, endTime,"BI214")+test10.puGetRadiatedPowerForStartNucleusOverTimeRange(startTime, endTime, "PO214"))+ " MeV/s" + System.getProperty("line.separator"));
//    	data10.append("Total Energy (for BI214 and PO214) = " + (test10.puGetEnergySumForStartingNucleusOverTimeRange(startTime, endTime, "BI214")+test10.puGetEnergySumForStartingNucleusOverTimeRange(startTime, endTime, "PO214")) + " MeV" + System.getProperty("line.separator"));
//    	data10.append(System.getProperty("line.separator"));
//    	data10.append(test10.puGetAllStartTimeNucleiCounts());
//    	data10.append(System.getProperty("line.separator"));
//    	data10.append(test10.puGetAllEndTimeNucleiCounts());
//    	data10.append(System.getProperty("line.separator"));
//    	data10.append(test10.puGetAllEventCountsOverTimeRangeByNuclei(startTime, endTime));
//    	data10.append(System.getProperty("line.separator"));
//    	double sum10 = 0;
//    	for(int x = 1; x <= 1000;x = 10*x) {
//    		sum10 = 0;
//    		data10.append("For resolution set to " + x + " we get:" + System.getProperty("line.separator"));
//    		test10 = new NucleiSamplePredictiveSim(Math.pow(10, 26),"/home/user/git/Radioactivity_Sim/input/U238",startTime,endTime,x);
//    		for(double y = 0; y < x; y++) {
//    			data10.append("Energy (BI214 & PO214)(t = "+(startTime+(y+1)*(endTime-startTime)/x)+") = " + (test10.puGetEnergySumForStartingNucleusOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x,"BI214")+test10.puGetEnergySumForStartingNucleusOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x, "PO214")) + " MeV" + System.getProperty("line.separator"));
//    			sum10 += (test10.puGetEnergySumForStartingNucleusOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x,"BI214")+test10.puGetEnergySumForStartingNucleusOverTimeRange(startTime+y*(endTime-startTime)/x, startTime+(y+1)*(endTime-startTime)/x, "PO214"));
//    		}
//    		data10.append("Which all adds up to: " + sum10 + " MeV" + System.getProperty("line.separator"));
//    		data10.append(System.getProperty("line.separator"));
//    	}
//    	scrivener.puAppendStringToFile(fileNum-1, data10.toString());
//    	scrivener.puCloseFile(fileNum-1);
//
//        //Prints the program runtime to the console in milliseconds
//        System.out.println(Calendar.getInstance().getTime().getTime() - now);
//
//    }
//}
//
//
