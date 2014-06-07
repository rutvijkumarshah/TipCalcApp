package com.example.tipcalc;

import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class TipCalcActivity extends Activity {

	private TextView tipResultView=null;
	private EditText billAmountEt=null;
	private float tipPercentage=10f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calc);
		tipResultView=(TextView)findViewById(R.id.resultTextView);
		billAmountEt=(EditText)findViewById(R.id.bill_amount_et);
		billAmountEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
					if(s == null || s.length() ==0){
						billAmountEt.setText("0");
					}
					updateTipView(tipPercentage);
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void calcuateTip(final View v){
    	final int viewId=v.getId();
    	float tipPercentage=10f;
    	switch(viewId){
	    	case R.id.fifteen_percent:
	    		tipPercentage=15F;
	    		break;
	    	case R.id.twenty_percent:
	    		tipPercentage=20F;
	    		break;
    	}
    	this.tipPercentage=tipPercentage;
    	updateTipView(tipPercentage);
    }

	private void updateTipView(float tipPercentage) {
		tipResultView.setText("");
		String billAmountStr=billAmountEt.getText().toString();
    	float billAmount=Float.parseFloat(billAmountStr);
    	float tip=tipCalc(billAmount, tipPercentage);
    	tipResultView.setText(String.format("Tip is:  $%s", tip));
	}
	

	private static float tipCalc(float bill,float tipPercentage){
		return (float) ((bill*tipPercentage)/100.0);
	}
}
