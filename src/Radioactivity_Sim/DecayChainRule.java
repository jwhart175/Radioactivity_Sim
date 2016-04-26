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

public class DecayChainRule extends PRSFNUM{
	//A container for a rule

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
	protected String prStartNucleus; //The starting nucleus of each rule
	protected String prEndNucleus; //The ending nucleus of each rule
	protected String prType; //The radiation type of each rule
	protected double prEnergy = prsfDoubleMinusOne;; //The energy quantity for each rule
	protected double prHalfLife = prsfDoubleMinusOne;; //The half-life for each rule
	protected double prProbability = prsfDoubleMinusOne; //The probability of each rule
	protected int prNumGammas = prsfIntZero; //The number of coincident gamma radiation events for this rule
	protected String[] prGammaName = new String[prsfIntOne]; //The names for any coincident gamma radiation events
	protected double[] prGammaEnergy = new double[prsfIntOne]; //The energies for any coincident gamma radiation events
	protected double[] prGammaIntensity = new double[prsfIntOne]; //The intensities for any coincident gamma radiation events
	protected int prNumBetas = prsfIntZero; //The number of coincident beta radiation events for this rule
	protected String[] prBetaName = new String[prsfIntOne]; //The names of the possible beta emission energies
	protected double[] prBetaEnergy = new double[prsfIntOne]; //The possible beta emission energies
	protected double[] prBetaIntensity = new double[prsfIntOne]; //The intensities for each beta emission energy
	protected int prNumAlphas = prsfIntZero; //The number of coincident alpha radiation events for this rule
	protected String[] prAlphaName = new String[prsfIntOne]; //The names of the possible alpha emission energies
	protected double[] prAlphaEnergy = new double[prsfIntOne]; //The possible alpha emission energies
	protected double[] prAlphaIntensity = new double[prsfIntOne]; //The intensities for each alpha emission energy

	public DecayChainRule(){
		//Basic Constructor
	}

	public DecayChainRule(String start, String end, String type, double energy, double halflife, double probability){
		//Constructs a rule with all data except gammas
		prStartNucleus = start;
		prEndNucleus = end;
		prType = type;
		if(energy>=0){
			prEnergy = energy;
		} else {
			System.out.println("(DecayChainRule) construction failed! The supplied energy must be greater than or equal to zero!");
		}
		if(halflife>0){
			prHalfLife = halflife;
		} else {
			System.out.println("(DecayChainRule) construction failed! The supplied halflife must be greater than zero!");
		}
		if(probability>0){
			prProbability = probability;
		} else {
			System.out.println("(DecayChainRule) construction failed! The supplied probability must be greater than zero!");
		}
		prNumGammas = prsfIntZero;
	}

	public DecayChainRule(DecayChainRule rule){
		//Constructs a rule with all data from another rule
		prNumGammas = rule.puGetNumGammas();
    	prGammaName = rule.puGetGammaName();
    	prGammaEnergy = rule.puGetGammaEnergy();
    	prGammaIntensity = rule.puGetGammaIntensity();
    	prNumBetas = rule.puGetNumBetas();
    	prBetaName = rule.puGetBetaName();
    	prBetaEnergy = rule.puGetBetaEnergy();
    	prBetaIntensity = rule.puGetBetaIntensity();
    	prNumAlphas = rule.puGetNumAlphas();
    	prAlphaName = rule.puGetAlphaName();
    	prAlphaEnergy = rule.puGetAlphaEnergy();
    	prAlphaIntensity = rule.puGetAlphaIntensity();
    	prStartNucleus = rule.puGetStartNucleus();
        prEndNucleus = rule.puGetEndNucleus();
        prEnergy = rule.puGetEnergy();
        prType = rule.puGetType();
        prHalfLife = rule.puGetHalfLife();
        prProbability = rule.puGetProbability();
	}

	public void puAddGamma(String name, double energy, double intensity){
		//Adds a coincident gamma to the rule
		if(prNumGammas==prsfIntZero){
			if(energy>0&intensity>0){
				prGammaName[prsfIntZero] = name;
				prGammaEnergy[prsfIntZero] = energy;
				prGammaIntensity[prsfIntZero] = intensity;
				prNumGammas++;
			} else {
				System.out.println("(puAddGamma) has failed! the supplied energy and intensity must be greater than zero!");
			}
		} else {
			if(energy>0&intensity>0){
				String[] names = new String[prNumGammas+1];
				double[] energies = new double[prNumGammas+1];
				double[] intensities = new double[prNumGammas+1];
				for(int x = 0; x < prNumGammas; x++){
					names[x] = prGammaName[x];
					energies[x] = prGammaEnergy[x];
					intensities[x] = prGammaIntensity[x];
				}
				names[prNumGammas] = name;
				energies[prNumGammas] = energy;
				intensities[prNumGammas] = intensity;
				prGammaName = names;
				prGammaEnergy = energies;
				prGammaIntensity = intensities;
				prNumGammas++;
			} else {
				System.out.println("(puAddGamma) has failed! the supplied energy and intensity must be greater than zero!");
			}
		}
	}

	public void puAddBeta(String name, double energy, double intensity){
		//Adds Beta emmision energy to the rule
		if(prNumBetas==prsfIntZero){
			if(energy>0&intensity>0){
				prBetaName[prsfIntZero] = name;
				prBetaEnergy[prsfIntZero] = energy;
				prBetaIntensity[prsfIntZero] = intensity;
				prNumBetas++;
			} else {
				System.out.println("(puAddBeta) has failed! the supplied energy and intensity must be greater than zero!");
			}
		} else {
			if(energy>0&intensity>0){
				String[] names = new String[prNumBetas+1];
				double[] energies = new double[prNumBetas+1];
				double[] intensities = new double[prNumBetas+1];
				for(int x = 0; x < prNumBetas; x++){
					names[x] = prBetaName[x];
					energies[x] = prBetaEnergy[x];
					intensities[x] = prBetaIntensity[x];
				}
				names[prNumBetas] = name;
				energies[prNumBetas] = energy;
				intensities[prNumBetas] = intensity;
				prBetaName = names;
				prBetaEnergy = energies;
				prBetaIntensity = intensities;
				prNumBetas++;
			} else {
				System.out.println("(puAddBeta) has failed! the supplied energy and intensity must be greater than zero!");
			}
		}
	}

	public void puAddAlpha(String name, double energy, double intensity){
		//Adds Alpha emmision energy to the rule
		if(prNumAlphas==prsfIntZero){
			if(energy>0&intensity>0){
				prAlphaName[prsfIntZero] = name;
				prAlphaEnergy[prsfIntZero] = energy;
				prAlphaIntensity[prsfIntZero] = intensity;
				prNumAlphas++;
			} else {
				System.out.println("(puAddAlpha) has failed! the supplied energy and intensity must be greater than zero!");
			}
		} else {
			if(energy>0&intensity>0){
				String[] names = new String[prNumAlphas+1];
				double[] energies = new double[prNumAlphas+1];
				double[] intensities = new double[prNumAlphas+1];
				for(int x = 0; x < prNumAlphas; x++){
					names[x] = prAlphaName[x];
					energies[x] = prAlphaEnergy[x];
					intensities[x] = prAlphaIntensity[x];
				}
				names[prNumAlphas] = name;
				energies[prNumAlphas] = energy;
				intensities[prNumAlphas] = intensity;
				prAlphaName = names;
				prAlphaEnergy = energies;
				prAlphaIntensity = intensities;
				prNumAlphas++;
			} else {
				System.out.println("(puAddAlpha) has failed! the supplied energy and intensity must be greater than zero!");
			}
		}
	}

	public void puClearRule(){
		//clears the rule as if it were new
		prStartNucleus = "";
		prEndNucleus = "";
		prType = "";
		prEnergy = prsfDoubleMinusOne;;
		prHalfLife = prsfDoubleMinusOne;;
		prProbability = prsfDoubleMinusOne;
		prNumGammas = prsfIntZero;
		prGammaName = new String[prsfIntOne];
		prGammaEnergy = new double[prsfIntOne];
		prGammaIntensity = new double[prsfIntOne];
		prNumBetas = prsfIntZero;
		prBetaName = new String[prsfIntOne];
		prBetaEnergy = new double[prsfIntOne];
		prBetaIntensity = new double[prsfIntOne];
		prNumAlphas = prsfIntZero;
		prAlphaName = new String[prsfIntOne];
		prAlphaEnergy = new double[prsfIntOne];
		prAlphaIntensity = new double[prsfIntOne];
	}

	public String puGetStartNucleus() {
		//returns the start nucleus for the rule
		return prStartNucleus;
	}

	public String puGetEndNucleus() {
		//returns the end nucleus for the specified rule number
		return prEndNucleus;
	}

	public String puGetType() {
		//returns the event type for the specified rule number
		return prType;
	}

	public double puGetEnergy() {
		//returns the rule energy
		if(prEnergy>prsfIntZero){
			return prEnergy;
		} else {
			System.out.println("(puGetEnergy) has failed because this (DecayChainRule)'s energy has not been set!");
			return prsfDoubleMinusOne;
		}
	}

	public double puGetHalfLife() {
		//returns the rule half life
		if(prHalfLife>prsfIntZero){
			return prHalfLife;
		} else {
			System.out.println("(puGetHalfLife) has failed because this (DecayChainRule)'s halflife has not been set!");
			return prsfDoubleMinusOne;
		}
	}

	public double puGetProbability() {
		//returns the probability coefficient for the rule
		if(prProbability>prsfIntZero){
			return prProbability;
		} else {
			System.out.println("(puGetProbability) has failed because this (DecayChainRule)'s probabilty has not been set!");
			return prsfDoubleMinusOne;
		}
	}

	public void puSetProbability(double prob) {
		//sets the probability coefficient for the rule
		if(prob>prsfIntZero){
			prProbability = prob;
		} else {
			System.out.println("(puSetProbability) has failed because the supplied probability is less than or equal to zero!");
			prProbability = prsfDoubleMinusOne;
		}
	}

	public int puGetNumGammas() {
		//returns the number of coincident gamma radiation events
		return prNumGammas;
	}

	public String[] puGetGammaName() {
		//returns all of the Gamma names for the rule
		return prGammaName;
	}

	public String puGetGammaName(int index){
		//returns the gamma name at the supplied index for the rule
		if(prNumGammas==prsfIntZero){
			String err = "(puGetGammaName) has failed! This rule has no conincident gammas!" + System.getProperty("line.separator");
			System.out.println(err);
			return err;
		} else {
			if(index>=0){
				if(index<prGammaName.length){
					return prGammaName[index];
				} else {
					String err = "(puGetGammaName) has failed! The supplied index is greater than the number of gammas less one!" + System.getProperty("line.separator");
					System.out.println(err);
					return err;
				}
			} else {
				String err = "(puGetGammaName) has failed! The supplied index must be greater then zero!" + System.getProperty("line.separator");
				System.out.println(err);
				return err;
			}
		}
	}

	public double[] puGetGammaEnergy() {
		//returns all of the Gamma energies for the rule
		return prGammaEnergy;
	}

	public double puGetGammaEnergy(int index){
		//returns the gamma energy at the supplied index for the rule
		if(prNumGammas==prsfIntZero){
			String err = "(puGetGammaEnergy) has failed! This rule has no conincident gammas!" + System.getProperty("line.separator");
			System.out.println(err);
			return prsfDoubleMinusOne;
		} else {
			if(index>=0){
				if(index<prGammaEnergy.length){
					return prGammaEnergy[index];
				} else {
					String err = "(puGetGammaEnergy) has failed! The supplied index is greater than the number of gammas less one!" + System.getProperty("line.separator");
					System.out.println(err);
					return prsfDoubleMinusOne;
				}
			} else {
				String err = "(puGetGammaEnergy) has failed! The supplied index must be greater then zero!" + System.getProperty("line.separator");
				System.out.println(err);
				return prsfDoubleMinusOne;
			}
		}
	}

	public double[] puGetGammaIntensity() {
		//returns all of the Gamma intensities for the rule
		return prGammaIntensity;
	}

	public double puGetGammaIntensity(int index){
		//returns the gamma intensity at the supplied index for the rule
		if(prNumGammas==prsfIntZero){
			String err = "(puGetGammaIntensity) has failed! This rule has no conincident gammas!" + System.getProperty("line.separator");
			System.out.println(err);
			return prsfDoubleMinusOne;
		} else {
			if(index>=0){
				if(index<prGammaIntensity.length){
					return prGammaIntensity[index];
				} else {
					String err = "(puGetGammaIntensity) has failed! The supplied index is greater than the number of gammas less one!" + System.getProperty("line.separator");
					System.out.println(err);
					return prsfDoubleMinusOne;
				}
			} else {
				String err = "(puGetGammaIntensity) has failed! The supplied index must be greater then zero!" + System.getProperty("line.separator");
				System.out.println(err);
				return prsfDoubleMinusOne;
			}
		}
	}

	public int puGetNumBetas() {
		//returns the number of alternate beta energies
		return prNumBetas;
	}

	public String[] puGetBetaName() {
		//returns all of the Beta names for the rule
		return prBetaName;
	}

	public String puGetBetaName(int index){
		//returns the Beta name at the supplied index for the rule
		if(prNumBetas==prsfIntZero){
			String err = "(puGetBetaName) has failed! This rule has no conincident Betas!" + System.getProperty("line.separator");
			System.out.println(err);
			return err;
		} else {
			if(index>=0){
				if(index<prBetaName.length){
					return prBetaName[index];
				} else {
					String err = "(puGetBetaName) has failed! The supplied index is greater than the number of Betas less one!" + System.getProperty("line.separator");
					System.out.println(err);
					return err;
				}
			} else {
				String err = "(puGetBetaName) has failed! The supplied index must be greater then zero!" + System.getProperty("line.separator");
				System.out.println(err);
				return err;
			}
		}
	}

	public double[] puGetBetaEnergy() {
		//returns all of the Beta energies for the rule
		return prBetaEnergy;
	}

	public double puGetBetaEnergy(int index){
		//returns the energy of the beta emission at the supplied index for the rule
		if(prNumBetas==prsfIntZero){
			String err = "(puGetBetaEnergy) has failed! This rule has no conincident Betas!" + System.getProperty("line.separator");
			System.out.println(err);
			return prsfDoubleMinusOne;
		} else {
			if(index>=0){
				if(index<prBetaEnergy.length){
					return prBetaEnergy[index];
				} else {
					String err = "(puGetBetaEnergy) has failed! The supplied index is greater than the number of Betas less one!" + System.getProperty("line.separator");
					System.out.println(err);
					return prsfDoubleMinusOne;
				}
			} else {
				String err = "(puGetBetaEnergy) has failed! The supplied index must be greater then zero!" + System.getProperty("line.separator");
				System.out.println(err);
				return prsfDoubleMinusOne;
			}
		}
	}

	public double[] puGetBetaIntensity() {
		//returns all beta emission intensities for the rule
		return prBetaIntensity;
	}

	public double puGetBetaIntensity(int index){
		//returns the intensity of the beta emission at the supplied index for the rule
		if(prNumBetas==prsfIntZero){
			String err = "(puGetBetaIntensity) has failed! This rule has no conincident Betas!" + System.getProperty("line.separator");
			System.out.println(err);
			return prsfDoubleMinusOne;
		} else {
			if(index>=0){
				if(index<prBetaIntensity.length){
					return prBetaIntensity[index];
				} else {
					String err = "(puGetBetaIntensity) has failed! The supplied index is greater than the number of Betas less one!" + System.getProperty("line.separator");
					System.out.println(err);
					return prsfDoubleMinusOne;
				}
			} else {
				String err = "(puGetBetaIntensity) has failed! The supplied index must be greater then zero!" + System.getProperty("line.separator");
				System.out.println(err);
				return prsfDoubleMinusOne;
			}
		}
	}

	public int puGetNumAlphas() {
		//returns the number of alternate Alpha energies
		return prNumAlphas;
	}

	public String[] puGetAlphaName() {
		//returns all of the Alpha names for the rule
		return prAlphaName;
	}

	public String puGetAlphaName(int index){
		//returns the Alpha name at the supplied index for the rule
		if(prNumAlphas==prsfIntZero){
			String err = "(puGetAlphaName) has failed! This rule has no conincident Alphas!" + System.getProperty("line.separator");
			System.out.println(err);
			return err;
		} else {
			if(index>=0){
				if(index<prAlphaName.length){
					return prAlphaName[index];
				} else {
					String err = "(puGetAlphaName) has failed! The supplied index is greater than the number of Alphas less one!" + System.getProperty("line.separator");
					System.out.println(err);
					return err;
				}
			} else {
				String err = "(puGetAlphaName) has failed! The supplied index must be greater then zero!" + System.getProperty("line.separator");
				System.out.println(err);
				return err;
			}
		}
	}

	public double[] puGetAlphaEnergy() {
		//returns all of the Alpha energies for the rule
		return prAlphaEnergy;
	}

	public double puGetAlphaEnergy(int index){
		//returns the energy of the Alpha emission at the supplied index for the rule
		if(prNumAlphas==prsfIntZero){
			String err = "(puGetAlphaEnergy) has failed! This rule has no conincident Alphas!" + System.getProperty("line.separator");
			System.out.println(err);
			return prsfDoubleMinusOne;
		} else {
			if(index>=0){
				if(index<prAlphaEnergy.length){
					return prAlphaEnergy[index];
				} else {
					String err = "(puGetAlphaEnergy) has failed! The supplied index is greater than the number of Alphas less one!" + System.getProperty("line.separator");
					System.out.println(err);
					return prsfDoubleMinusOne;
				}
			} else {
				String err = "(puGetAlphaEnergy) has failed! The supplied index must be greater then zero!" + System.getProperty("line.separator");
				System.out.println(err);
				return prsfDoubleMinusOne;
			}
		}
	}

	public double[] puGetAlphaIntensity() {
		//returns all Alpha emission intensities for the rule
		return prAlphaIntensity;
	}

	public double puGetAlphaIntensity(int index){
		//returns the intensity of the Alpha emission at the supplied index for the rule
		if(prNumAlphas==prsfIntZero){
			String err = "(puGetAlphaIntensity) has failed! This rule has no conincident Alphas!" + System.getProperty("line.separator");
			System.out.println(err);
			return prsfDoubleMinusOne;
		} else {
			if(index>=0){
				if(index<prAlphaIntensity.length){
					return prAlphaIntensity[index];
				} else {
					String err = "(puGetAlphaIntensity) has failed! The supplied index is greater than the number of Alphas less one!" + System.getProperty("line.separator");
					System.out.println(err);
					return prsfDoubleMinusOne;
				}
			} else {
				String err = "(puGetAlphaIntensity) has failed! The supplied index must be greater then zero!" + System.getProperty("line.separator");
				System.out.println(err);
				return prsfDoubleMinusOne;
			}
		}
	}

}
