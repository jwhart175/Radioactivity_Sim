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

public class DecayChainRuleBranch extends PRSFNUM{
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
	 * protected static final int prsfIntMinusOne = -1;
	 * protected static final int prsfIntZero = 0;
	 * protected static final int prsfIntOne = 1;
	 * protected static final int prsfIntTwo = 2;
	 * protected static final int prsfIntThree = 3
	 * protected static final int prsfIntFour = 4;
	 * protected static final int prsfIntFive = 5;
	 * protected static final int prsfIntSix = 6;
	 * protected static final int[] prsfDetailedTest = ...
	 */

	protected DecayChainRule[] prRules; //The rules contained within the rule branch
	protected int prNumRule = prsfIntZero; //The total number of rules

	public DecayChainRuleBranch(){
		//Basic Constructor
	}

	public DecayChainRuleBranch(DecayChainRuleBranch branch){
		//Constructs a new, independent rule branch from an existing one
		prNumRule = new Integer(branch.puGetNumRules());
		DecayChainRule[] rules = new DecayChainRule[prNumRule];
		for (int x = prsfIntZero; x < prNumRule; x++){
			rules[x] = new DecayChainRule(branch.puGetRule(x));
		}
		prRules = rules;
	}

	public int puAddRule(String start, String end, String type, double energy, double halflife, double probability) {
		//Adds a rule to the branch, returns the index of that new rule
		if(prNumRule==prsfIntZero) {
			prRules = new DecayChainRule[prsfIntOne];
			prRules[prsfIntZero] = new DecayChainRule(start,end,type,energy,halflife,probability);
			prNumRule++;
		} else {
			DecayChainRule[] r = new DecayChainRule[prNumRule+prsfIntOne];
			for(int x = prsfIntZero; x<prNumRule; x++){
				r[x] = new DecayChainRule(prRules[x]);
			}
			r[prNumRule] = new DecayChainRule(start,end,type,energy,halflife,probability);
			prRules = r;
			prNumRule++;
		}
		return (prNumRule-prsfIntOne);
	}

	public int puAddRule(DecayChainRule rule) {
		//Adds a rule to the branch, returns the index of that new rule
		if(prNumRule==prsfIntZero) {
			prRules = new DecayChainRule[prsfIntOne];
			prRules[prsfIntZero] = new DecayChainRule(rule);
			prNumRule++;
		} else {
			DecayChainRule[] r = new DecayChainRule[prNumRule+prsfIntOne];
			for(int x = prsfIntZero; x<prNumRule; x++){
				r[x] = new DecayChainRule(prRules[x]);
			}
			r[prNumRule] = new DecayChainRule(rule);
			prRules = r;
			prNumRule++;
		}
		return (prNumRule-prsfIntOne);
	}

	public void puAddGammaToRule(int index, String name, double energy, double intensity){
		//Adds a gamma to the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					if(energy>0&intensity>0){
						prRules[index].puAddGamma(name, energy, intensity);
					} else {
						System.out.println("(puAddGammaToRule) has failed! The supplied energy and intensity must be greater than zero!");
					}
				} else {
					System.out.println("(puAddGammaToRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puAddGammaToRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puAddGammaToRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
	}

	public void puAddBetaToRule(int index, String name, double energy, double intensity){
		//Adds a beta to the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					if(energy>0&intensity>0){
						prRules[index].puAddBeta(name, energy, intensity);
					} else {
						System.out.println("(puAddBetaToRule) has failed! The supplied energy and intensity must be greater than zero!");
					}
				} else {
					System.out.println("(puAddBetaToRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puAddBetaToRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puAddBetaToRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
	}

	public void puAddAlphaToRule(int index, String name, double energy, double intensity){
		//Adds a Alpha to the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					if(energy>0&intensity>0){
						prRules[index].puAddAlpha(name, energy, intensity);
					} else {
						System.out.println("(puAddAlphaToRule) has failed! The supplied energy and intensity must be greater than zero!");
					}
				} else {
					System.out.println("(puAddAlphaToRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puAddAlphaToRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puAddAlphaToRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
	}

	public void puAddNeutronToRule(int index, String name, double energy, double intensity){
		//Adds a Neutron to the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					if(energy>0&intensity>0){
						prRules[index].puAddNeutron(name, energy, intensity);
					} else {
						System.out.println("(puAddNeutronToRule) has failed! The supplied energy and intensity must be greater than zero!");
					}
				} else {
					System.out.println("(puAddNeutronToRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puAddNeutronToRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puAddNeutronToRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
	}

	public void puDelRule(int index) {
		//Remove a rule from the (DecayChainRuleBranch) at the supplied index
		if (index < prsfIntZero){
			System.out.println("(puDelRule) failed because the supplied (index) was less than zero");
		} else if (index <= ((prNumRule-prsfIntOne))) {
			if (prNumRule > prsfIntOne) {
				DecayChainRule[] r = new DecayChainRule[prNumRule-prsfIntOne];
				for (int x = prsfIntZero; x<index; x++) {
					r[x] = prRules[x];
				}
				for (int x = index+1; x<prNumRule; x++) {
					r[x-prsfIntOne] = prRules[x];
				}
				prRules = r;
				prNumRule--;
			} else {
				System.out.println("(puDelRule) failed because there are no rules to delete!");
			}
		} else {
			System.out.println("(puDelRule) failed because the supplied (index) was out of bounds");
		}
	}

	public void puReorderProbabilities() {
		//to be called after this (DecayChainRuleBranch) has been completely populated
		for (int y = prsfIntOne; y < prNumRule; y++) {
			if(prRules[y].puGetProbability()<prsfDoubleOne){
				prRules[prsfIntZero].puSetProbability(prRules[prsfIntZero].puGetProbability()*prRules[y].puGetProbability());
				prRules[y].puSetProbability(prsfDoubleOne);
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
			String errString = "(puGetStartNucleus) failed because the supplied index is greater than the number of rules in this (DecayChainRuleBranch)";
			System.out.println(errString);
			return errString;
		} else {
			return prRules[index].puGetStartNucleus();
		}
	}

	public String puGetEndNucleus(int index) {
		//returns the end nucleus for the specified rule number
		if (index < prsfIntZero) {
			String errString = "(puGetEndNucleus) failed because the supplied index was less than zero";
			System.out.println(errString);
			return errString;
		} else if (index >= prNumRule) {
			String errString = "(puGetEndNucleus) failed because the supplied index is greater than the number of rules in this (DecayChainRuleBranch)";
			System.out.println(errString);
			return errString;
		} else {
			return prRules[index].puGetEndNucleus();
		}
	}

	public String puGetType(int index) {
		//returns the event type for the specified rule number
		if (index < prsfIntZero) {
			String errString = "(puGetType) failed because the supplied index was less than zero";
			System.out.println(errString);
			return errString;
		} else if (index >= prNumRule) {
			String errString = "(puGetType) failed because the supplied index is greater than the number of rules in this (DecayChainRuleBranch)";
			System.out.println(errString);
			return errString;
		} else {
			return prRules[index].puGetType();
		}
	}

	public double puGetEnergy(int index) {
		//returns the event energy for the specified rule number
		if (index < prsfIntZero) {
			String errString = "(puGetEnergy) failed because the supplied index was less than zero";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else if (index >= prNumRule) {
			String errString = "(puGetEnergy) failed because the supplied index is greater than the number of rules in this (DecayChainRuleBranch)";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else {
			return prRules[index].puGetEnergy();
		}
	}

	public double puGetHalfLife(int index) {
		//returns the event half life for the specified rule number
		if (index < prsfIntZero) {
			String errString = "(puGetHalfLife) failed because the supplied index was less than zero";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else if (index >= prNumRule) {
			String errString = "(puGetHalfLife) failed because the supplied index is greater than the number of rules in this (DecayChainRuleBranch)";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else {
			return prRules[index].puGetHalfLife();
		}
	}

	public double puGetProbability(int index) {
		//returns the probability coefficient for the specified rule number
		if (index < prsfIntZero) {
			String errString = "(puGetProbability) failed because the supplied index was less than zero";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else if (index >= prNumRule) {
			String errString = "(puGetProbability) failed because the supplied index is greater than the number of rules in this (DecayChainRuleBranch)";
			System.out.println(errString);
			return prsfDoubleMinusOne;
		} else {
			return prRules[index].puGetProbability();
		}
	}

	public int puGetNumRules() {
		//returns the total number of rules
		return prNumRule;
	}

	public DecayChainRule puGetRule(int index){
		//returns the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					return (new DecayChainRule(prRules[index]));
				} else {
					System.out.println("(puGetRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puGetRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puGetRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
		return (new DecayChainRule());
	}

	public int puGetNumGammasFromRule(int index) {
		//returns the number of coincident gamma radiation events in the specified rule
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					return prRules[index].puGetNumGammas();
				} else {
					System.out.println("(puGetNumGammasFromRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puGetNumGammasFromRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puGetNumGammasFromRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
		return prsfIntZero;
	}

	public String[] puGetGammaNamesFromRule(int index) {
		//returns all of the Gamma names for the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					return prRules[index].puGetGammaName();
				} else {
					System.out.println("(puGetGammaName) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puGetGammaName) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puGetGammaName) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
		return (new String[1]);
	}

	public double[] puGetGammaEnergiesFromRule(int index) {
		//returns all of the Gamma energies for the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					return prRules[index].puGetGammaEnergy();
				} else {
					System.out.println("(puGetGammaEnergiesFromRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puGetGammaEnergiesFromRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puGetGammaEnergiesFromRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
		return (new double[1]);
	}

	public double[] puGetGammaIntensitiesFromRule(int index) {
		//returns all of the Gamma intensities for the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					return prRules[index].puGetGammaIntensity();
				} else {
					System.out.println("(puGetGammaIntensitiesFromRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puGetGammaIntensitiesFromRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puGetGammaIntensitiesFromRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
		return (new double[1]);
	}

	public int puGetNumBetasFromRule(int index) {
		//returns the number of alternate beta energies from the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					return prRules[index].puGetNumBetas();
				} else {
					System.out.println("(puGetNumBetasFromRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puGetNumBetasFromRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puGetNumBetasFromRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
		return prsfIntZero;
	}

	public String[] puGetBetaNamesFromRule(int index) {
		//returns all of the Beta names for the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					return prRules[index].puGetBetaName();
				} else {
					System.out.println("(puGetBetaName) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puGetBetaName) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puGetBetaName) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
		return (new String[1]);
	}

	public double[] puGetBetaEnergiesFromRule(int index) {
		//returns all of the Beta energies for the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					return prRules[index].puGetBetaEnergy();
				} else {
					System.out.println("(puGetBetaEnergiesFromRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puGetBetaEnergiesFromRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puGetBetaEnergiesFromRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
		return (new double[1]);
	}

	public double[] puGetBetaIntensitiesFromRule(int index) {
		//returns all beta emission intensities for the rule at the specified index
		if(prNumRule>0) {
			if(index>=0) {
				if(index<prNumRule){
					return prRules[index].puGetBetaIntensity();
				} else {
					System.out.println("(puGetBetaIntensitiesFromRule) has failed! The supplied index is out of bounds!");
				}
			} else {
				System.out.println("(puGetBetaIntensitiesFromRule) has failed! The supplied index must be greater than zero!");
			}
		} else {
			System.out.println("(puGetBetaIntensitiesFromRule) has failed! There aren't any rules in this (DecayChainRuleBranch)!");
		}
		return (new double[1]);
	}

}
