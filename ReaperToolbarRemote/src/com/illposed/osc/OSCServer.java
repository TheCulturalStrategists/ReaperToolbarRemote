package com.illposed.osc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class OSCServer implements Runnable , OSCListener {
	
	
	public OSCServer(InetAddress ipAddress, int outPort, int inPort) {
		super();
		this.ipAddress = ipAddress;
		this.outPort = outPort;
		this.inPort = inPort;
		try {
			oscPortOut = new OSCPortOut(ipAddress,outPort);
			oscPortIn = new OSCPortIn(inPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	public void addListener(String anAddress, OSCListener listener) {
		oscPortIn.addListener(anAddress, listener);
	}
	@Override
	public void run() {
		oscPortOut.startSender();
		oscPortIn.startListening();
	}
	public void stop(){
		oscPortOut.stopSending();
		oscPortIn.stopListening();
	}
	public void kill(){
		oscPortOut.stopSending();
		oscPortIn.stopListening();
		oscPortIn.close();
		oscPortOut.close();
	}
	public void sendOsc(OSCPacket msg){
		try {
			oscPortOut.send(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private OSCPortOut oscPortOut;
	private OSCPortIn oscPortIn;
	private InetAddress ipAddress;
	private int outPort;
	private int inPort;
	@Override
	public void acceptMessage(Date time, OSCMessage message) {
		
	}
}
