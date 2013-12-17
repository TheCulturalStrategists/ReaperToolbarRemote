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

public class OscServer implements Runnable {

	public OscServer() {
		super();
		
		oscListeners = new ArrayList<OscListener>();
		outPort = 8026;
		inPort = 8025;
	}
	private void startOsc() {
		try {
			createListener();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		try {
			createSender();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	private void createSender() throws UnknownHostException, SocketException {
		queue = new ArrayBlockingQueue<OSCMessage>(25);
		InetAddress addr = InetAddress.getByName(ipAddress);
		sender = new OSCPortOut(addr, outPort);
		isSending = true;
	}
	private void createListener() throws SocketException {
		OSCListener listener;
		receiver = new OSCPortIn(inPort);
		listener = new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				for (OscListener oscl: oscListeners){
					oscl.messageReceived(message);
				}
			}
		};
		receiver.addListener("/play", listener);
	}
	public void sendOsc(View view) {
		String addr = (String) view.getTag();
		Object args[] = new Object[1];
		if (view.getId() == R.id.end_button) {
			args[0] = Integer.valueOf(40043);
		} else if (view.getId() == R.id.home_button) {
			args[0] = Integer.valueOf(40042);
		} else {
			args[0] = Float.valueOf(1.0f);
		}
		OSCMessage msg = new OSCMessage(addr, args);
		queue.add(msg);
	}

	@Override
	public void run() {
		OSCMessage msg;
		while (isSending) {
			try {
				msg = queue.take();
				sender.send(msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void start() {
		receiver.startListening();
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		receiver.stopListening();
		isSending = false;
	}

	public void closeOscPorts() {
		
		sender.close();
	}

	public void openOscPorts() {

	}

	public void addOscListener(OscListener oscl) {
		oscListeners.add(oscl);
	}

	public int getOutPort() {
		return outPort;
	}


	public void setOutPort(int outPort) {
		this.outPort = outPort;
	}


	public int getInPort() {
		return inPort;
	}


	public void setInPort(int inPort) {
		this.inPort = inPort;
	}

	private List<OscListener> oscListeners;
	private OSCPortOut sender;
	private OSCPortIn receiver;
	private ArrayBlockingQueue<OSCMessage> queue;
	private String ipAddress = "192.168.0.98";
	private int outPort;
	private int inPort;
	private Thread thread;
	private boolean isSending;
}
