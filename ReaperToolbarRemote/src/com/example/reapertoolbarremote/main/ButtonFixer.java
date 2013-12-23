package com.example.reapertoolbarremote.main;

import java.util.ArrayList;

import com.example.reapertoolbarremote.R;
import com.example.reapertoolbarremote.gui.OnOffButton;
import com.illposed.osc.OSCMessage;

import android.app.Activity;

public class ButtonFixer {
	public ButtonFixer(Activity act) {
		super();
		this.act = act;
	}
	public void fixButtons(){
		int pressActiveToolbarButton1 = 41085;
		
		/// Fix toolbar buttons ///
		
		for (int offset = 0; offset < 8; offset++) {
			OnOffButton button = (OnOffButton) act.findViewById(R.id.but1+offset);
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(Integer.valueOf(pressActiveToolbarButton1+offset));
			OSCMessage msg = new OSCMessage("/action", args);
			button.setOscPacket(msg);
		}
		
		/// Fix transportbar buttons ///
		
		OnOffButton button = (OnOffButton) act.findViewById(R.id.end_button);
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(Integer.valueOf(40043));
		OSCMessage msg = new OSCMessage("/action", args);
		button.setOscPacket(msg);
		
		button = (OnOffButton) act.findViewById(R.id.home_button);
		args.clear();
		args.add(Integer.valueOf(40042));
		msg = new OSCMessage("/action", args);
		button.setOscPacket(msg);
		
		button = (OnOffButton) act.findViewById(R.id.play_button);
		args.clear();
		args.add(Integer.valueOf(1));
		msg = new OSCMessage("/play", args);
		button.setOscPacket(msg);
		
		button = (OnOffButton) act.findViewById(R.id.stop_button);
		args.clear();
		args.add(Integer.valueOf(1));
		msg = new OSCMessage("/stop", args);
		button.setOscPacket(msg);
		
		button = (OnOffButton) act.findViewById(R.id.pause_button);
		args.clear();
		args.add(Integer.valueOf(1));
		msg = new OSCMessage("/pause", args);
		button.setOscPacket(msg);
		
		button = (OnOffButton) act.findViewById(R.id.record_button);
		args.clear();
		args.add(Integer.valueOf(1));
		msg = new OSCMessage("/record", args);
		button.setOscPacket(msg);
		
		button = (OnOffButton) act.findViewById(R.id.repeat_button);
		args.clear();
		args.add(Integer.valueOf(1));
		msg = new OSCMessage("/repeat", args);
		button.setOscPacket(msg);
		
		act=null;
	}
	private Activity act;
}
