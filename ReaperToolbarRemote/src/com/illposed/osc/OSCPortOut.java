/*
 * Copyright (C) 2004-2006, C. Ramakrishnan / Illposed Software.
 * All rights reserved.
 *
 * This code is licensed under the BSD 3-Clause license.
 * See file LICENSE (or LICENSE.html) for more information.
 */

package com.illposed.osc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * OSCPortOut is the class that sends OSC messages
 * to a specific address and port.
 *
 * To send an OSC message, call send().
 *
 * An example based on
 * {@link com.illposed.osc.OSCPortTest#testMessageWithArgs()}:
 * <pre>
	OSCPort sender = new OSCPort();
	Object args[] = new Object[2];
	args[0] = new Integer(3);
	args[1] = "hello";
	OSCMessage msg = new OSCMessage("/sayhello", args);
	 try {
		sender.send(msg);
	 } catch (Exception e) {
		 showError("Couldn't send");
	 }
 * </pre>
 *
 * @author Chandrasekhar Ramakrishnan
 */
public class OSCPortOut extends OSCPort implements Runnable {

	

	/**
	 * Create an OSCPort that sends to address:port.
	 * @param address the UDP address to send to
	 * @param port the UDP port to send to
	 */
	public OSCPortOut(InetAddress address, int port)
		throws SocketException
	{
		super(new DatagramSocket(), port);
		this.address = address;
	}
	public void startSender(){
		Thread t = new Thread(this);
		t.start();
	}
	public void stopSending(){
		isSending=false;
		
		ArrayList<Object> args = new ArrayList<Object>();
        args.add("");
        OSCMessage msg = new OSCMessage("/", args); // Dummymessage to quit run loop
        try {
			send(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Send an OSC packet (message or bundle) to the receiver we are bound to.
	 * @param aPacket the bundle or message to send
	 */
	public void send(OSCPacket aPacket) throws IOException {
		oscMessageQueue.add(aPacket);
	}

	@Override
	public void run() {
		oscMessageQueue = new ArrayBlockingQueue<OSCPacket>(25);
		isSending = true;
		OSCPacket aPacket;
		while (isSending) {
			try {
				aPacket = oscMessageQueue.take();
				
				byte[] byteArray = aPacket.getByteArray();  /////Send OSC message//////
				DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, address, getPort());
				getSocket().send(packet);                   ///// End send       /////
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	public void stop(){
		isSending = false;
		oscMessageQueue.add(new OSCMessage("localhost")); //Empty message to get out of sendloop
		this.close();
	}
	private InetAddress address;
	private ArrayBlockingQueue<OSCPacket> oscMessageQueue;
	private boolean isSending;
}
