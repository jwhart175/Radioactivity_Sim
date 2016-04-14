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

public class DecayRuleBranch {
	//A container for a branch of rules
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
	protected String[] prStartNuclei; //The starting nucleus of each rule
	protected String[] prEndNuclei; //The ending nucleus of each rule
	protected String[] prType; //The radiation type of each rule
	protected double[] prEnergy; //The energy quantity for each rule
	protected double[] prHalfLife; //The half-life for each rule
	protected double[] prProbability; //The probability of each rule
	protected int prNumRule = 0; //The total number of rules
	public DecayRuleBranch(){
		//Basic Constructor
	}
	public void puAddRule(String start, String end, String type, double energy, double halflife, double probability) {
		//Adds a rule to the branch
		if (prNumRule==0) {
			prStartNuclei = new String[1];
			prStartNuclei[0] = start;
			prEndNuclei = new String[1];
			prEndNuclei[0] = end;
			prType = new String[1];
			prType[0] = type;
			prEnergy = new double[1];
			prEnergy[0] = energy;
			prHalfLife = new double[1];
			prHalfLife[0] = halflife;
			prProbability = new double[1];
			prProbability[0] = probability;
			prNumRule++;
		} else {
			String[] s,e,t;
			double[] en,hl,p;
			s = new String[prNumRule+1];
			e = new String[prNumRule+1];
			t = new String[prNumRule+1];
			en = new double[prNumRule+1];
			hl = new double[prNumRule+1];
			p = new double[prNumRule+1];
			for(int x = 0; x < prNumRule; x++){
				s[x] = prStartNuclei[x];
				e[x] = prEndNuclei[x];
				t[x] = prType[x];
				en[x] = prEnergy[x];
				hl[x] = prHalfLife[x];
				p[x] = prProbability[x];
			}
			s[prNumRule] = start;
			e[prNumRule] = end;
			t[prNumRule] = type;
			en[prNumRule] = energy;
			hl[prNumRule] = halflife;
			p[prNumRule] = probability;
			prStartNuclei = s;
			prEndNuclei = e;
			prType = t;
			prEnergy = en;
			prHalfLife = hl;
			prProbability = p;
			prNumRule++;
		}
	}
	public void puDelRule(int index) {
		//Remove a rule from the (DecayRuleBranch) at the supplied index
		if (index < 0){
			System.out.println("(puDelRule) failed because the supplied (index) was less than zero");
		} else if (index <= ((prNumRule-1))) {
			if (prNumRule > 1) {
				String[] s,e,t;
				double[] en,hl,p;
				s = new String[prNumRule-1];
				e = new String[prNumRule-1];
				t = new String[prNumRule-1];
				en = new double[prNumRule-1];
				hl = new double[prNumRule-1];
				p = new double[prNumRule-1];
				for (int x = 0; x<index; x++) {
					s[x] = prStartNuclei[x];
					e[x] = prEndNuclei[x];
					t[x] = prType[x];
					en[x] = prEnergy[x];
					hl[x] = prHalfLife[x];
					p[x] = prProbability[x];
				}
				for (int x = index+1; x<prNumRule; x++) {
					s[x-1] = prStartNuclei[x];
					e[x-1] = prEndNuclei[x];
					t[x-1] = prType[x];
					en[x-1] = prEnergy[x];
					hl[x-1] = prHalfLife[x];
					p[x-1] = prProbability[x];
				}
				prStartNuclei = s;
				prEndNuclei = e;
				prType = t;
				prEnergy = en;
				prHalfLife = hl;
				prProbability = p;
				prNumRule+=1;
			} else {
				System.out.println("(puDelRule) failed because there are no rules to delete!");
			}
		} else {
			System.out.println("(puDelRule) failed because the supplied (index) was out of bounds");
		}
	}
	public void puReorderProbabilities() {
		//to be called after this (DecayRuleBranch) has been completely populated
		for (int y = 1; y < prNumRule; y++) {
			if (prProbability[y]<1){
				prProbability[0] = prProbability[0]*prProbability[y];
				prProbability[y] = 1;
			}
		}
	}
	public String puGetStartNucleus(int index) {
		//returns the start nucleus for the specified rule number
		if (index < 0) {
			String errString = "(puGetStartNucleus) failed because the supplied index was less than zero";
			System.out.println(errString);
			return errString;
		} else if (index >= prNumRule) {
			String errString = "(puGetStartNucleus) failed because the supplied index is greater than the number of rules in this (DecayRuleBranch)";
			System.out.println(errString);
			return errString;
		} else {
			return prStartNuclei[index];
		}
	}
	public String puGetEndNucleus(int index) {
		//returns the end nucleus for the specified rule number
		if (index < 0) {
			String errString = "(puGetEndNucleus) failed because the supplied index was less than zero";
			System.out.println(errString);
			return errString;
		} else if (index >= prNumRule) {
			String errString = "(puGetEndNucleus) failed because the supplied index is greater than the number of rules in this (DecayRuleBranch)";
			System.out.println(errString);
			return errString;
		} else {
			return prEndNuclei[index];
		}
	}
	public String puGetType(int index) {
		//returns the event type for the specified rule number
		if (index < 0) {
			String errString = "(puGetType) failed because the supplied index was less than zero";
			System.out.println(errString);
			return errString;
		} else if (index >= prNumRule) {
			String errString = "(puGetType) failed because the supplied index is greater than the number of rules in this (DecayRuleBranch)";
			System.out.println(errString);
			return errString;
		} else {
			return prType[index];
		}
	}
	public double puGetEnergy(int index) {
		//returns the event energy for the specified rule number
		if (index < 0) {
			String errString = "(puGetEnergy) failed because the supplied index was less than zero";
			System.out.println(errString);
			return -1;
		} else if (index >= prNumRule) {
			String errString = "(puGetEnergy) failed because the supplied index is greater than the number of rules in this (DecayRuleBranch)";
			System.out.println(errString);
			return -1;
		} else {
			return prEnergy[index];
		}
	}
	public double puGetHalfLife(int index) {
		//returns the event half life for the specified rule number
		if (index < 0) {
			String errString = "(puGetHalfLife) failed because the supplied index was less than zero";
			System.out.println(errString);
			return -1;
		} else if (index >= prNumRule) {
			String errString = "(puGetHalfLife) failed because the supplied index is greater than the number of rules in this (DecayRuleBranch)";
			System.out.println(errString);
			return -1;
		} else {
			return prHalfLife[index];
		}
	}
	public double puGetProbability(int index) {
		//returns the probability coefficient for the specified rule number
		if (index < 0) {
			String errString = "(puGetProbability) failed because the supplied index was less than zero";
			System.out.println(errString);
			return -1;
		} else if (index >= prNumRule) {
			String errString = "(puGetProbability) failed because the supplied index is greater than the number of rules in this (DecayRuleBranch)";
			System.out.println(errString);
			return -1;
		} else {
			return prProbability[index];
		}
	}
	public int puGetNumRules() {
		//returns the total number of rules
		return prNumRule;
	}
}
