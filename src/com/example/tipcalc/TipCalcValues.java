/***

The MIT License (MIT)
Copyright © 2014 Rutvijkumar Shah
 
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the “Software”), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
 
The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.
 
THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 ***/

package com.example.tipcalc;
/***
 * 
 * Represents Tip Calculator Values. Implements Logic to calculate Tip
 * @author Rutvijkumar Shah
 *
 */
public class TipCalcValues {

	public static final int ROUNDING_TIP_UP = 1;
	public static final int ROUNDING_TIP_DOWN = 2;
	public static final int ROUNDING_TIP_EXACT = 3;

	private float billAmount;
	private float tipPercentage = 15;
	private int splits = 1;

	private int roundingOption = ROUNDING_TIP_EXACT;//Default Option is to calculate exact
	
	//Result object will be cached and updated with new results to avoid new object creations.
	//Result object will be used by UI to show Tip Calculation results
	private CalculationResult resultInstance = new CalculationResult();

	/***
	 * Reset results when invalid conditions are entered by user.
	 */
	private void resetTipCalcValues() {
		resultInstance.perPersonShare = 0;
		resultInstance.totalTipAmount = 0;
		resultInstance.totalWithTip = 0;
	}

	public CalculationResult calculate() {
		recalculateResult();
		return resultInstance;
	}
	

	/********
	 * 
	 * Logic for calculation of rounding tip amount and per person contribution.
	 * 
	 */
	private void recalculateResult() {

		if (billAmount == 0) {
			resetTipCalcValues();
			return;
		}
		final float totalTip = (float) ((getBillAmount() * getTipPercentage()) / 100.0);
		final float exactPerPersonShare = (totalTip + getBillAmount())
				/ getSplits();

		float roundDiff;
		float new_totalTip = totalTip;
		float new_percentage = getTipPercentage();
		float new_perPerson = exactPerPersonShare;

		if (roundingOption == ROUNDING_TIP_UP) {
			roundDiff = (float) Math.ceil(exactPerPersonShare)
					- exactPerPersonShare;
			new_totalTip = totalTip + (roundDiff * getSplits());
			new_percentage = (float) (new_totalTip * 100.0) / getBillAmount();
			new_perPerson = exactPerPersonShare + roundDiff;
			tipPercentage = new_percentage;
		} else if (roundingOption == ROUNDING_TIP_DOWN) {
			roundDiff = exactPerPersonShare
					- (float) Math.floor(exactPerPersonShare);
			new_totalTip = totalTip - (roundDiff * getSplits());
			new_percentage = (float) (new_totalTip * 100.0) / getBillAmount();
			new_perPerson = exactPerPersonShare - roundDiff;
			tipPercentage = new_percentage;
		}
		float total_final_bill = new_totalTip + getBillAmount();

		resultInstance.setTotalTipAmount(new_totalTip);
		resultInstance.setTipPercentage(new_percentage);
		resultInstance.setPerPersonShare(new_perPerson);
		resultInstance.setTotalWithTip(total_final_bill);

	}



	/***
	 * Represents Calculated result values
	 * 
	 * @author Rutvijkumar Shah
	 *
	 */
	class CalculationResult {
		private float perPersonShare;
		private float totalWithTip;
		private float tipPercentage;
		private float totalTipAmount;

		public float getPerPersonShare() {
			return perPersonShare;
		}

		public CalculationResult setPerPersonShare(float perPersonShare) {
			this.perPersonShare = perPersonShare;
			return this;
		}

		public float getTotalWithTip() {
			return totalWithTip;
		}

		public CalculationResult setTotalWithTip(float totalWithTip) {
			this.totalWithTip = totalWithTip;
			return this;
		}

		public float getTipPercentage() {
			return tipPercentage;
		}

		public CalculationResult setTipPercentage(float tipPercentage) {
			this.tipPercentage = tipPercentage;
			return this;
		}

		public float getTotalTipAmount() {
			return totalTipAmount;
		}

		public CalculationResult setTotalTipAmount(float totalTipAmount) {
			this.totalTipAmount = totalTipAmount;
			return this;
		}

	}
	
	public int getRoundingOption() {
		return roundingOption;
	}

	public void setRoundingOption(int roundingOption) {
		if (roundingOption > 0 && roundingOption < 4) {
			this.roundingOption = roundingOption;
		} else {
			this.roundingOption = ROUNDING_TIP_EXACT;
		}

	}
	float getBillAmount() {
		return billAmount;
	}

	void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}

	float getTipPercentage() {
		return tipPercentage;
	}

	void setTipPercentage(float tipPercentage) {
		this.tipPercentage = tipPercentage;
	}

	int getSplits() {
		return splits;
	}

	void setSplits(int splits) {
		if (splits < 1) {
			splits = 1;
		}
		this.splits = splits;
	}
}
