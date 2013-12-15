package com.example.reapertoolbarremote.osc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import android.view.View;
import android.widget.TextView;

import com.example.reapertoolbarremote.R;
import com.example.reapertoolbarremote.R.id;
import com.illposed.osc.*;



public class OscCentral implements Runnable {

	public OscCentral() {
		super();
		queue = new ArrayBlockingQueue<OSCMessage>(25);
        oscListeners = new ArrayList<OscListener>();
	}
	private void startOsc() {
		
		OSCListener listener;
		try {
        	InetAddress addr = InetAddress.getByName(ipAddress);
			sender = new OSCPortOut(addr,8026);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		if (receiver==null) {
			try {
				receiver = new OSCPortIn(8025);
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			listener = new OSCListener() {
				public void acceptMessage(java.util.Date time,
						OSCMessage message) {
					// TODO Fix messagehandler
				}
			};
			receiver.addListener("/play", listener);
		}
        
        receiver.startListening();
        
		
	}
	public void sendOsc(View view){
		String addr = (String) view.getTag();
		Object args[] = new Object[1];
		if (view.getId()==R.id.end_button){
			args[0]= Integer.valueOf(40043);
		} else if (view.getId()==R.id.home_button){
			args[0]= Integer.valueOf(40042);
		} else {
			args[0]= Float.valueOf(1.0f);
		}
		OSCMessage msg = new OSCMessage(addr, args);
		queue.add(msg);
	}
	
	@Override
	public void run() {
		OSCMessage msg;
		while(true){
			try {
				msg = queue.take();
				sender.send(msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public void start(){
		
	}
	public void stop(){
		
	}
	public void closeOscPorts(){
		sender.close();
	}
	public void openOscPorts(){
		
	}
	public void addOscListener(OscListener oscl){
		oscListeners.add(oscl);
	}
	private List<OscListener> oscListeners;
	private OSCPortOut sender;
	private OSCPortIn receiver;
	private ArrayBlockingQueue<OSCMessage> queue;
	private String ipAddress="192.168.0.98";
	
}
