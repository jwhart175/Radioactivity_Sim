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

public class DecayEventSet extends DecayEvent {
	//An extension of the (DecayEvent) class meant to contain the effects (energy output, etc) of a set of (DecayEvent)
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

	public DecayEventSet(boolean isChild, double startTime, double endTime,
			String start, String end, double halfLife, double energy,
			String type) {
		super(isChild, startTime, endTime, start, end, halfLife, energy, type);
		// TODO Auto-generated constructor stub
	}

	public DecayEventSet(double startTime, double endTime, String start,
			String end, double halfLife, double energy, String type) {
		super(startTime, endTime, start, end, halfLife, energy, type);
		// TODO Auto-generated constructor stub
	}

	public DecayEventSet(String start, String end, double halfLife,
			double energy, String type) {
		super(start, end, halfLife, energy, type);
		// TODO Auto-generated constructor stub
	}

	public DecayEventSet(String start, String end, double halfLife,
			double energy, String type, double timeOffset) {
		super(start, end, halfLife, energy, type, timeOffset);
		// TODO Auto-generated constructor stub
	}

	public DecayEventSet(String start, String end, double halfLife,
			double energy, String type, int maxHalfLives) {
		super(start, end, halfLife, energy, type, maxHalfLives);
		// TODO Auto-generated constructor stub
	}

	public DecayEventSet(String start, String end, double halfLife,
			double energy, String type, double timeOffset, int maxHalfLives) {
		super(start, end, halfLife, energy, type, timeOffset, maxHalfLives);
		// TODO Auto-generated constructor stub
	}

	 public void puRecalculateTime(boolean bound, boolean isChild){
		//Hides the (puRecalculateTime) function from the super class
	 }

}
