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

public class DecayRuleBranch extends PRSFNUM{
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
	 * protected static final double prsfDoubleNineTenths = 0.9;
	 * protected static final double prsfDoubleElevenTenths = 1.1;
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

	protected String[] prStartNuclei; //The starting nucleus of each rule
	protected String[] prEndNuclei; //The ending nucleus of each rule
	protected String[] prType; //The radiation type of each rule
	protected double[] prEnergy; //The energy quantity for each rule
	protected double[] prHalfLife; //The half-life for each rule
	protected double[] prProbability; //The probability of each rule
	protected int prNumRule = prsfIntZero; //The total number of rules

	public DecayRuleBranch(){
		//Basic Constructor
	}

	public void puAddRule(String start, String end, String type, double energy, double halflife, double probability) {
		//Adds a rule to the branch
		if (prNumRule==prsfIntZero) {
			prStartNuclei = new String[prsfIntOne];
			prStartNuclei[prsfIntZero] = start;
			prEndNuclei = new String[prsfIntOne];
			prEndNuclei[prsfIntZero] = end;
			prType = new String[prsfIntOne];
			prType[prsfIntZero] = type;
			prEnergy = new double[prsfIntOne];
			prEnergy[prsfIntZero] = energy;
			prHalfLife = new double[prsfIntOne];
			prHalfLife[prsfIntZero] = halflife;
			prProbability = new double[prsfIntOne];
			prProbability[prsfIntZero] = probability;
			prNumRule++;
		} else {
			String[] s,e,t;
			double[] en,hl,p;
			s = new String[prNumRule+prsfIntOne];
			e = new String[prNumRule+prsfIntOne];
			t = new String[prNumRule+prsfIntOne];
			en = new double[prNumRule+prsfIntOne];
			hl = new double[prNumRule+prsfIntOne];
			p = new double[prNumRule+prsfIntOne];
			for(int x = prsfIntZero; x < prNumRule; x++){
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
		if (index < prsfIntZero){
			System.out.println("(puDelRule) failed because the supplied (index) was less than zero");
		} else if (index <= ((prNumRule-prsfIntOne))) {
			if (prNumRule > prsfIntOne) {
				String[] s,e,t;
				double[] en,hl,p;
				s = new String[prNumRule-prsfIntOne];
				e = new String[prNumRule-prsfIntOne];
				t = new String[prNumRule-prsfIntOne];
				en = new double[prNumRule-prsfIntOne];
				hl = new double[prNumRule-prsfIntOne];
				p = new double[prNumRule-prsfIntOne];
				for (int x = prsfIntZero; x<index; x++) {
					s[x] = prStartNuclei[x];
					e[x] = prEndNuclei[x];
					t[x] = prType[x];
					en[x] = prEnergy[x];
					hl[x] = prHalfLife[x];
					p[x] = prProbability[x];
				}
				for (int x = index+1; x<prNumRule; x++) {
					s[x-prsfIntOne] = prStartNuclei[x];
					e[x-prsfIntOne] = prEndNuclei[x];
					t[x-prsfIntOne] = prType[x];
					en[x-prsfIntOne] = prEnergy[x];
					hl[x-prsfIntOne] = prHalfLife[x];
					p[x-prsfIntOne] = prProbability[x];
				}
				prStartNuclei = s;
				prEndNuclei = e;
				prType = t;
				prEnergy = en;
				prHalfLife = hl;
				prProbability = p;
				prNumRule++;
			} else {
				System.out.println("(puDelRule) failed because there are no rules to delete!");
			}
		} else {
			System.out.println("(puDelRule) failed because the supplied (index) was out of bounds");
		}
	}

	public void puReorderProbabilities() {
		//to be called after this (DecayRuleBranch) has been completely populated
		for (int y = prsfIntOne; y < prNumRule; y++) {
			if (prProbability[y]<prsfIntOne){
				prProbability[prsfIntZero] = prProbability[prsfIntZero]*prProbability[y];
				prProbability[y] = prsfIntOne;
			}
		}
	}

	public String puGetStartNucleus(int index) {
		//returns the start nucleus for the specified rule number
		if (index < prsfIntZero) {
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
		if (index < prsfIntZero) {
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
		if (index < prsfIntZero) {
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
		if (index < prsfIntZero) {
			String errString = "(puGetEnergy) failed because the supplied index was less than zero";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else if (index >= prNumRule) {
			String errString = "(puGetEnergy) failed because the supplied index is greater than the number of rules in this (DecayRuleBranch)";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else {
			return prEnergy[index];
		}
	}

	public double puGetHalfLife(int index) {
		//returns the event half life for the specified rule number
		if (index < prsfIntZero) {
			String errString = "(puGetHalfLife) failed because the supplied index was less than zero";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else if (index >= prNumRule) {
			String errString = "(puGetHalfLife) failed because the supplied index is greater than the number of rules in this (DecayRuleBranch)";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else {
			return prHalfLife[index];
		}
	}

	public double puGetProbability(int index) {
		//returns the probability coefficient for the specified rule number
		if (index < prsfIntZero) {
			String errString = "(puGetProbability) failed because the supplied index was less than zero";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else if (index >= prNumRule) {
			String errString = "(puGetProbability) failed because the supplied index is greater than the number of rules in this (DecayRuleBranch)";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else {
			return prProbability[index];
		}
	}

	public int puGetNumRules() {
		//returns the total number of rules
		return prNumRule;
	}
}
