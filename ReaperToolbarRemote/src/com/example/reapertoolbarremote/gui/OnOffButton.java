package com.example.reapertoolbarremote.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class OnOffButton extends ImageButton {


	public OnOffButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public OnOffButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public OnOffButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	



	private boolean isOn=false;
}
