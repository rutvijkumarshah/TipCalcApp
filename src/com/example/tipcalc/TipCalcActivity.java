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

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tipcalc.TipCalcValues.CalculationResult;

/***
 * 
 * #6a8eda
 * Launcher Main activity for Tip Calculator
 * 
 * 
 * @author Rutvijkumar Shah
 *
 *
 */
public class TipCalcActivity extends RoboActivity {

	 @InjectView(R.id.et_bill_amount)      EditText billAmountEditText;
	 @InjectView(R.id.label_valTipPercentage)      TextView percentageTextView;
	 @InjectView(R.id.label_ppl)              TextView splitWays;
	 @InjectView(R.id.seekBar_ppl)            SeekBar seekBarSplits;
	 
	 @InjectView(R.id.label_valPerPerson)              TextView valPerPerson;
	 @InjectView(R.id.label_valTipAmount)              TextView valTipAmount;
	 @InjectView(R.id.label_valTotalAmount)              TextView valTotalAmount;
	 
	 @InjectView(R.id.rdg_roundings)  		RadioGroup roundingOptions;
	 @InjectView(R.id.btn_up)              ImageButton incPercentageButton;
	 @InjectView(R.id.btn_down)              ImageButton decPercentageButton;
	 
	 @InjectView(R.id.rd_tipUp)              RadioButton roundingUp;
	 @InjectView(R.id.rd_tipdown)              RadioButton roundingDown;
	 @InjectView(R.id.rd_tipExact)              RadioButton roundingExact;
	 
	 
	 private static String FONT="OpenSans-Bold.ttf";
	 
	 private String splitTextTemplate=null;
	 private String amountFormatTemplate=null;
	 private String percentageTempalte=null;
	 private String msgSelectExactOption=null;
	 private float selectedTipPercentageWithExactRounding=15.00f;
	 
	 private TipCalcValues tipCalcValues=new TipCalcValues();
	 
	 
	
	 private static void applyTypeFace(Context ctx,ViewGroup layout,List<Integer> ids){
		
		 Typeface face = Typeface.createFromAsset(ctx.getAssets(), "fonts/"+FONT);
		 int childcount = layout.getChildCount();
		 for (int i=0; i < childcount; i++){
		       View v = layout.getChildAt(i);
		       if(v instanceof TextView ){
		    	   ((TextView) v).setTypeface(face);
		       }else if ( v instanceof  RadioButton){
		    	   ((RadioButton) v).setTypeface(face);
		       }
		 }
		    
	 }
	 private void resetResultView(){
		 
	 }
	 
	 private void showVariableInputs(){
		 
		 incPercentageButton.setVisibility(View.VISIBLE);
		 decPercentageButton.setVisibility(View.VISIBLE);
		 seekBarSplits.setVisibility(View.VISIBLE);
	 }
	 
	 private void hideVariableInputs(){
		 incPercentageButton.setVisibility(View.INVISIBLE);
		 decPercentageButton.setVisibility(View.INVISIBLE);
		 seekBarSplits.setVisibility(View.INVISIBLE);
	 }
	 private void updateResultView(){
		 try{
			 CalculationResult result = tipCalcValues.calculate();
			 
			 valPerPerson.setText(String.format(amountFormatTemplate, result.getPerPersonShare()));
			 valTipAmount.setText(String.format(amountFormatTemplate, result.getTotalTipAmount()));
			 valTotalAmount.setText(String.format(amountFormatTemplate,result.getTotalWithTip()));
			 percentageTextView.setText(String.format(percentageTempalte,result.getTipPercentage())+" %");
			 
			 
		 }catch(Exception e){
			 //Excpeption invalid values being processed.
			e.printStackTrace(); //TODO Remove this and implement reseet view
			resetResultView();
		 }
		 
	 }
	 
	 public void updateTipPercentage(View view){
		 int viewId=view.getId();
		 float newPercentage=0f;
		 String tipPercentageStr=percentageTextView.getText().toString();
		 float tipPercentage=0f;
		 
		 if(!roundingExact.isChecked()){
			 Toast.makeText(this,msgSelectExactOption, Toast.LENGTH_SHORT).show();
			 return;
		 }
		 if(tipPercentageStr != null && !tipPercentageStr.isEmpty()){
			 tipPercentageStr=tipPercentageStr.split(" %")[0];
			 tipPercentage=Float.parseFloat(tipPercentageStr);
		 }
		 if(viewId == R.id.btn_up){		 
			 newPercentage = (float)Math.floor(tipPercentage) +1;
			 if(newPercentage >100 ){
				 newPercentage=100;
			 }
			 
		 }else if (viewId == R.id.btn_down){
			 newPercentage = (float)Math.floor(tipPercentage) -1;
			 if(newPercentage ==0 ){
				 newPercentage=1;
			 }
		 }
		 tipCalcValues.setTipPercentage(newPercentage);
		 percentageTextView.setText(""+newPercentage);
		 updateResultView();
		 
	 }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calc);
	
		applyTypeFace(this, (RelativeLayout)findViewById(R.id.top_layout), new ArrayList<Integer>());
		
		roundingOptions.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group,final int checkedId) {
				tipCalcValues.setTipPercentage(selectedTipPercentageWithExactRounding);
				switch (checkedId) {
				case R.id.rd_tipUp:
					tipCalcValues.setRoundingOption(TipCalcValues.ROUNDING_TIP_UP);
					hideVariableInputs();
					break;
				case R.id.rd_tipdown:
					tipCalcValues.setRoundingOption(TipCalcValues.ROUNDING_TIP_DOWN);
					hideVariableInputs();
					break;
				case R.id.rd_tipExact:
					tipCalcValues.setRoundingOption(TipCalcValues.ROUNDING_TIP_EXACT);
					showVariableInputs();
				default:
					break;
				}
				updateResultView();
			}
		});
		percentageTextView.setKeyListener(null);//NOT ALLOWED TO ENTER Text
		percentageTextView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(roundingExact.isSelected()){
					selectedTipPercentageWithExactRounding=Float.parseFloat(percentageTextView.getText().toString().split(" %")[0]);
				}
				
			}
		});
		billAmountEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
					
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String billAmountStr=s.toString();
				float billAmount=0F;
				if(billAmountStr !=null && !billAmountStr.isEmpty()){
					billAmount=Float.parseFloat(s.toString());
				}
				tipCalcValues.setBillAmount(billAmount);
				updateResultView();
			}
		});
		this.splitTextTemplate=getResources().getString(R.string.TXT_SPLITS_TEMPLATE);
		this.amountFormatTemplate=getResources().getString(R.string.TXT_AMOUNT_TEMPLATE);
		this.percentageTempalte=getResources().getString(R.string.TXT_PERCENTAGE_TEMPLATE);
		this.msgSelectExactOption=getResources().getString(R.string.MSG_PLEASE_SELECT_EXACT_TEMPLATE);
		
		seekBarSplits.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if(progress == 0){
					progress=1;
					seekBar.setProgress(1);
				}
				splitWays.setText(String.format(splitTextTemplate, progress));
				tipCalcValues.setSplits(progress);
				updateResultView();
			}
		});
				

	}

}
