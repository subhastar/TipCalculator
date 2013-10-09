package com.example.tipcalculator;

import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TipActivity extends Activity {
	private EditText etBillAmount;
	private double mostRecentTipPercent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		
		this.etBillAmount = (EditText) findViewById(R.id.etBillAmount);

		setButtonListeners();
		setEditTextListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip, menu);
		return true;
	}
	
	private void setEditTextListener() {
		final double tipPercent = this.mostRecentTipPercent;
		System.out.println("in here. " + this.mostRecentTipPercent);
		this.etBillAmount.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				if (tipPercent != 0) {
					updateTip(tipPercent);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});
	}
	
	private void setButtonListeners() {
		Button btTen = (Button) findViewById(R.id.btTen);
		Button btFifteen = (Button) findViewById(R.id.btFifteen);
		Button btTwenty = (Button) findViewById(R.id.btTwenty);
		
		setButtonListener(btTen, 0.1);
		setButtonListener(btFifteen, 0.15);
		setButtonListener(btTwenty, 0.2);
		
		setButtonListenerCustom();
	}
	
	private void setButtonListenerCustom() {
		Button btCustom = (Button) findViewById(R.id.btCustom);
		btCustom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText etCustomTipAmount = (EditText) findViewById(R.id.etCustomTip);
				double tipPercent = Double.parseDouble(etCustomTipAmount.getText().toString());
				updateTip(tipPercent);
			}
		});
	}
	
	private void setButtonListener(Button b, final double tipPercent) {
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateTip(tipPercent);
			}
		});
	}
	
	private void updateTip(double tipPercent) {
		this.mostRecentTipPercent = tipPercent;
		double billAmount = Double.parseDouble(this.etBillAmount.getText().toString());
		TextView tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
		TextView tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
		double tipAmount = Math.round(tipPercent * billAmount * 100.0)/100.0;
		double totalAmount = billAmount + tipAmount;
		tvTipAmount.setText(formatCurrency(tipAmount));
		tvTotalAmount.setText(formatCurrency(totalAmount));
	}
	
	private String formatCurrency(double dollarAmount) {
		Locale enUSLocale = new Locale("en", "US");
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(enUSLocale);
		return currencyFormatter.format(dollarAmount);
	}

}
