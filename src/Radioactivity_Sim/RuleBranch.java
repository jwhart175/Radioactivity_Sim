package Radioactivity_Sim;
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

public class RuleBranch {

	//A container for a branch of rules
	/* Variable and Function Nomenclature Prescipts:
     * pv = private
     * pu = public
     * pvs = private static
     * pus = public static
     * pvsf = private static final
     * pusf = public static final
     */
	private String[] pvStartNuclei; //The starting nucleus of each rule
	private String[] pvEndNuclei; //The ending nucleus of each rule
	private String[] pvType; //The emission type of each rule
	private double[] pvEnergy; //The energy quantity for each rule
	private double[] pvHalfLife; //The half-life for each rule
	private double[] pvProbability; //The probability of each rule
	private int pvLastProbIndex = 0;//Needed to track where probabilities need to be shifted
	private int pvNumRule = 0; //The total number of rules
	public RuleBranch(){
		//Basic Constructor
	}
	public void puAddRuleToBranch(String start, String end, String type, double energy, double halflife, double probability) {
		//Adds a rule to the branch
		if (pvNumRule==0) {
			pvStartNuclei = new String[1];
			pvStartNuclei[0] = start;
			pvEndNuclei = new String[1];
			pvEndNuclei[0] = end;
			pvType = new String[1];
			pvType[0] = type;
			pvEnergy = new double[1];
			pvEnergy[0] = energy;
			pvHalfLife = new double[1];
			pvHalfLife[0] = halflife;
			pvProbability = new double[1];
			pvProbability[0] = probability;
			pvNumRule++;
		} else {
			String[] s,e,t;
			double[] en,hl,p;
			s = new String[pvNumRule+1];
			e = new String[pvNumRule+1];
			t = new String[pvNumRule+1];
			en = new double[pvNumRule+1];
			hl = new double[pvNumRule+1];
			p = new double[pvNumRule+1];
			for(int x = 0; x < pvNumRule; x++){
				s[x] = pvStartNuclei[x];
				e[x] = pvEndNuclei[x];
				t[x] = pvType[x];
				en[x] = pvEnergy[x];
				hl[x] = pvHalfLife[x];
				p[x] = pvProbability[x];
			}
			s[pvNumRule] = start;
			e[pvNumRule] = end;
			t[pvNumRule] = type;
			en[pvNumRule] = energy;
			hl[pvNumRule] = halflife;
			p[pvNumRule] = probability;
			pvStartNuclei = s;
			pvEndNuclei = e;
			pvType = t;
			pvEnergy = en;
			pvHalfLife = hl;
			pvProbability = p;
			pvNumRule++;
		}
	}
	public void puReorderProbabilities() {
		for (int y = 1; y < pvNumRule; y++) {
			if (pvProbability[y]<1){
				pvProbability[0] = pvProbability[0]*pvProbability[y];
				pvProbability[y] = 1;
			}
		}
	}
	public String puGetStartNucleus(int index) {
		//returns the start nucleus for the specified rule number
		if (index < 0) {
			String errString = "(puGetStartNucleus) failed because the supplied index was less than zero";
			System.out.println(errString);
			return errString;
		} else if (index >= pvNumRule) {
			String errString = "(puGetStartNucleus) failed because the supplied index is greater than the number of rules in this (RuleBranch)";
			System.out.println(errString);
			return errString;
		} else {
			return pvStartNuclei[index];
		}
	}
	public String puGetEndNucleus(int index) {
		//returns the end nucleus for the specified rule number
		if (index < 0) {
			String errString = "(puGetEndNucleus) failed because the supplied index was less than zero";
			System.out.println(errString);
			return errString;
		} else if (index >= pvNumRule) {
			String errString = "(puGetEndNucleus) failed because the supplied index is greater than the number of rules in this (RuleBranch)";
			System.out.println(errString);
			return errString;
		} else {
			return pvEndNuclei[index];
		}
	}
	public String puGetType(int index) {
		//returns the event type for the specified rule number
		if (index < 0) {
			String errString = "(puGetType) failed because the supplied index was less than zero";
			System.out.println(errString);
			return errString;
		} else if (index >= pvNumRule) {
			String errString = "(puGetType) failed because the supplied index is greater than the number of rules in this (RuleBranch)";
			System.out.println(errString);
			return errString;
		} else {
			return pvType[index];
		}
	}
	public double puGetEnergy(int index) {
		//returns the event energy for the specified rule number
		if (index < 0) {
			String errString = "(puGetEnergy) failed because the supplied index was less than zero";
			System.out.println(errString);
			return -1;
		} else if (index >= pvNumRule) {
			String errString = "(puGetEnergy) failed because the supplied index is greater than the number of rules in this (RuleBranch)";
			System.out.println(errString);
			return -1;
		} else {
			return pvEnergy[index];
		}
	}
	public double puGetHalfLife(int index) {
		//returns the event half life for the specified rule number
		if (index < 0) {
			String errString = "(puGetHalfLife) failed because the supplied index was less than zero";
			System.out.println(errString);
			return -1;
		} else if (index >= pvNumRule) {
			String errString = "(puGetHalfLife) failed because the supplied index is greater than the number of rules in this (RuleBranch)";
			System.out.println(errString);
			return -1;
		} else {
			return pvHalfLife[index];
		}
	}
	public double puGetProbability(int index) {
		//returns the probability coefficient for the specified rule number
		if (index < 0) {
			String errString = "(puGetProbability) failed because the supplied index was less than zero";
			System.out.println(errString);
			return -1;
		} else if (index >= pvNumRule) {
			String errString = "(puGetProbability) failed because the supplied index is greater than the number of rules in this (RuleBranch)";
			System.out.println(errString);
			return -1;
		} else {
			return pvProbability[index];
		}
	}
	public int puGetNumRules() {
		//returns the total number of rules
		return pvNumRule;
	}
}
