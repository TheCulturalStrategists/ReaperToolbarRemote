package com.example.reapertoolbarremote.gui;

import java.util.Date;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.ImageButton;

public class OnOffButton extends ImageButton implements OSCListener{


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
	



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return super.onKeyUp(keyCode, event);
	}




	private boolean isOn=false;
	private OSCPacket onPacket,offPacket;



	@Override
	public void acceptMessage(Date time, OSCMessage message) {
		// TODO Auto-generated method stub
		
	}
}
