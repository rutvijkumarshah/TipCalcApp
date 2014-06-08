package com.example.tipcalc;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/***
 * 
 * #6a8eda
 * Launcher Main activity for Tip Calculator
 * @author Rutvijkumar Shah
 *
 */
public class TipCalcActivity extends Activity {

	private TextView tipResultView;
	private EditText billAmountEt;
	private Float tipPercentage=0F;
	private String tipResultTemplate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calc);
		tipResultTemplate=getResources().getString(R.string.TXT_TIP_TEMPLATE);
		tipResultView=(TextView)findViewById(R.id.resultTextView);
		billAmountEt=(EditText)findViewById(R.id.bill_amount_et);
		billAmountEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
					//Update text view based on bill amount changes
					//this will use last tip percentage selection
					updateTipView(tipPercentage);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				//DO NOTHING
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//DO NOTHING
			}
		});
	}

	/***
	 * Calback method invoked from tip selection buttions.
	 * 
	 * @param v Selected Button View
	 */
	public void calcuateTip(final View v){
    	final int viewId=v.getId();
    	float tipPercentage=Float.parseFloat(v.getTag().toString());
    	//Store selected percentage
    	this.tipPercentage=tipPercentage;
    	updateTipView(tipPercentage);
    }
	/***
	 * Update Tip View for given tip percentage
	 * @param tipPercentage
	 */
	private void updateTipView(float tipPercentage) {
		//Clears Tip text
		tipResultView.setText("");
		String billAmountStr=billAmountEt.getText().toString();
		
		//Do not show Tip calculated when there is no input or no tip button pressed
		if(billAmountStr != null && !billAmountStr.isEmpty() && tipPercentage !=0 ){
			float billAmount=Float.parseFloat(billAmountStr);
	    	float tip=tipCalc(billAmount, tipPercentage);
	    	tipResultView.setText(String.format(tipResultTemplate, tip));
		}
    
	}
	
	/***
	 * Calculates tip for given bill amount and tip percentage.
	 * 
	 * @param bill
	 * @param tipPercentage
	 * @return
	 */
	private static float tipCalc(float bill,float tipPercentage){
		return (float) ((bill*tipPercentage)/100.0);
	}
}
