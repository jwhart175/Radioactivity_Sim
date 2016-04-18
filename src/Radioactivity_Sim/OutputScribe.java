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

public class OutputScribe extends PRSFNUM{
	// A class to open and write multiple output files
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
	 * protected static final int prsfInt8760 = 8760;
	 * protected static final int prsfIntEighty = 80;
	 * protected static final int prsfInt365 = 365;
	 * protected static final int prsfInt24 = 24;
	 * protected static final int prsfInt3600 = 3600;
	 * protected static final int prsfIntZero = 0;
	 * protected static final int prsfIntOne = 0;
	 * protected static final int prsfIntTwo = 2;
	 * protected static final int[] prsfDetailedTest = ...
	 */

	protected PrintWriter[] prPrintWriters;
	protected BufferedWriter[] prBufferedWriters;
	protected FileWriter[] prFileWriters;
	protected boolean[] prStatus;
	protected int prNumFiles = prsfIntZero;

	public OutputScribe() {
		//(OutputScribe) basic constructor
	}

	public OutputScribe(String file) {
		//(OutputScribe) constructor that opens a file immediately
		puOpenNewFile(file);
	}

	public OutputScribe(String file, String output) {
		//(OutputScribe) constructor that opens a file, writes a string, and closes it
		puOpenNewFile(file);
		puAppendStringToFile(prsfIntZero,output);
		puCloseFile(prsfIntZero);
	}

	public int puGetNumFiles(){
		//returns the number of files in this (OutputScribe)
		return prNumFiles;
	}

	public boolean puGetStatus(int index){
		//returns the status of the file at the supplied index
		if (index>=prsfIntZero){
			if (index<=(prNumFiles-prsfIntOne)){
				return prStatus[index];
			} else {
				System.out.println("(puGetStatus) failed because the supplied index is greater than the number of files!");
				return false;
			}
		} else {
			System.out.println("(puGetStatus) failed because the supplied index is less than zero!");
			return false;
		}
	}

	public void puAppendStringToFile(int index, String output){
		//Attempts to append the String (output) to the file at (index)
		if (index>=prsfIntZero){
			if (index<=(prNumFiles-prsfIntOne)){
				if (prStatus[index]){
					try {
						prPrintWriters[index].println(output);
					} catch (Exception ex) {
						prStatus[index] = false;
			            System.out.println(ex);
			            System.out.println("(puAppendStringToFile) failed because there was an error while trying to write to the file! Index = " + index);
			        }
				} else {
					System.out.println("(puAppendStringToFile) failed because the file at the supplied index is not open for writing!");
				}
			} else {
				System.out.println("(puAppendStringToFile) failed because the supplied index is greater than the number of files!");
			}
		} else {
			System.out.println("(puAppendStringToFile) failed because the supplied index is less than zero!");
		}
	}

	public void puCloseFile(int index) {
		//Attempts to close the file at (index)
		if (index>=prsfIntZero){
			if (index<=(prNumFiles-prsfIntOne)){
				if (prStatus[index]){
					try {
						prPrintWriters[index].close();
					} catch (Exception ex) {
						prStatus[index] = false;
					    System.out.println(ex);
					    System.out.println("(puCloseFile) failed because there was an error while trying to write to the file! Index = " + index);
					}
				} else {
					System.out.println("(puCloseFile) failed because the file at the supplied index is not open for writing!");
				}
			} else {
				System.out.println("(puCloseFile) failed because the supplied index is greater than the number of files!");
			}
		} else {
			System.out.println("(puCloseFile) failed because the supplied index is less than zero!");
		}
	}

	public void puOpenNewFile(String file){
		//opens a new file
		PrintWriter pw = null;
        BufferedWriter bw = null;
        FileWriter fw = null;
		try {
			fw = new FileWriter(file,true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            PrintWriter[] pws = new PrintWriter[prNumFiles+prsfIntOne];
            BufferedWriter[] bws = new BufferedWriter[prNumFiles+prsfIntOne];
            FileWriter[] fws = new FileWriter[prNumFiles+prsfIntOne];
            boolean[] s = new boolean[prNumFiles+prsfIntOne];
            for (int x = prsfIntZero; x<prNumFiles; x++){
            	pws[x] = prPrintWriters[x];
            	bws[x] = prBufferedWriters[x];
            	fws[x] = prFileWriters[x];
            	s[x] = prStatus[x];
            }
            pws[prNumFiles] = pw;
            bws[prNumFiles] = bw;
            fws[prNumFiles] = fw;
            s[prNumFiles] = true;
            prFileWriters = fws;
    		prBufferedWriters = bws;
    		prPrintWriters = pws;
    		prStatus = s;
        	prNumFiles++;
		} catch (IOException ex) {
            System.out.println(ex);
            System.out.println("(puOpenNewFile) failed because " + file + " could not be opened!");
        }
	}

}
