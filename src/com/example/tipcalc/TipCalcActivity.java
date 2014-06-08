package com.example.tipcalc;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/***
 * 
 * #6a8eda
 * Launcher Main activity for Tip Calculator
 * 
 * 
 * @author Rutvijkumar Shah
 *
 */
public class TipCalcActivity extends RoboActivity {


	 @InjectView(R.id.et_bill_amount)      EditText billAmountEditText;
	 @InjectView(R.id.label_valTipPercentage)      EditText percentageEditBox;
	 @InjectView(R.id.label_ppl)              TextView splitWays;
	 @InjectView(R.id.seekBar_ppl)            SeekBar seekBarSplits;
	 @InjectView(R.id.label_valPerPerson)              TextView valPerPerson;
	 @InjectView(R.id.label_valTipAmount)              TextView valTipAmount;
	 @InjectView(R.id.label_valTotalAmount)              TextView valTotalAmount;
	 
	 private static String FONT="font_3838.ttf";
	 
	 private String splitTextTemplate=null;
	 
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
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calc);
	
		applyTypeFace(this, (RelativeLayout)findViewById(R.id.top_layout), new ArrayList<Integer>());
		
		this.splitTextTemplate=getResources().getString(R.string.TXT_SPLITS_TEMPLATE);
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
				splitWays.setText(String.format(splitTextTemplate, progress));
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
