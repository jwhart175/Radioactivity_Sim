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

public class OutputScribe {
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
	protected PrintWriter[] prPrintWriters;
	protected BufferedWriter[] prBufferedWriters;
	protected FileWriter[] prFileWriters;
	protected boolean[] prStatus;
	protected int prNumFiles = 0;

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
		puAppendStringToFile(0,output);
		puCloseFile(0);
	}

	public int puGetNumFiles(){
		//returns the number of files in this (OutputScribe)
		return prNumFiles;
	}

	public boolean puGetStatus(int index){
		//returns the status of the file at the supplied index
		if (index>=0){
			if (index<=(prNumFiles-1)){
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
		if (index>=0){
			if (index<=(prNumFiles-1)){
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
		if (index>=0){
			if (index<=(prNumFiles-1)){
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
            PrintWriter[] pws = new PrintWriter[prNumFiles+1];
            BufferedWriter[] bws = new BufferedWriter[prNumFiles+1];
            FileWriter[] fws = new FileWriter[prNumFiles+1];
            boolean[] s = new boolean[prNumFiles+1];
            for (int x = 0; x<prNumFiles; x++){
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
