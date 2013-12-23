package com.example.reapertoolbarremote.main;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Date;

import com.example.reapertoolbarremote.R;
import com.example.reapertoolbarremote.gui.OnOffButton;
import com.illposed.osc.*;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MainActivity extends Activity implements OSCListener {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		InetAddress addr=null;
		try {
			addr = InetAddress.getByName(ipNumber);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		
		createAndStartServer(addr);
		setButtonProperties();
	}

	private void createAndStartServer(InetAddress addr) {
		osc = new OSCServer(addr,outPort,inPort);
		
		serverThread = new Thread(osc);
		serverThread.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void sendOsc(View view) {
		OnOffButton button = (OnOffButton) view;
		osc.sendOsc(button.getOscPacket());
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		osc.kill();
	}

	@Override
	protected void onStop() {
		super.onStop();
		osc.stop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		osc.stop();
	}

	@Override
	protected void onStart() {
		super.onStart();
		
//		serverThread.start();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
//		serverThread.start();
	}

	@Override
	protected void onResume() {
		super.onResume();
//		serverThread.start();
	}

	public void showPopup(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.popup_menu, popup.getMenu());
		popup.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			setIp();
			return true;

		default:
			return false;
		}
	}

	private void setIp() {
		setContentView(R.layout.set_ip_layout);
//		ipNumber = wifiIpAddress(this);
		TextView ip = (TextView) findViewById(R.id.ipNumber);
		ip.setText(ipNumber);
		TextView outport = (TextView) findViewById(R.id.outPortNumber);
		outport.setText(outPort);
		TextView inport = (TextView) findViewById(R.id.inportNumber);
		inport.setText(inPort);
		
	}

	public void setIpNumberDone(View view){
		setContentView(R.layout.activity_main);
	}

	protected String wifiIpAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(WIFI_SERVICE);
		int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

		// Convert little-endian to big-endianif needed
		if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
			ipAddress = Integer.reverseBytes(ipAddress);
		}

		byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

		String ipAddressString;
		try {
			ipAddressString = InetAddress.getByAddress(ipByteArray)
					.getHostAddress();
		} catch (UnknownHostException ex) {
			ipAddressString = null;
		}

		return ipAddressString;
	}

	private OSCServer osc;
	private Thread serverThread;
	private int outPort = 8026;
	private int inPort = 8025;
	private String ipNumber = "192.168.0.98";
	@Override
	public void acceptMessage(Date time, OSCMessage message) {
		// TODO Auto-generated method stub
		
	}
	private void setButtonProperties(){
		ButtonFixer fix = new ButtonFixer(this);
		fix.fixButtons();
		fix=null;
	}
	
}
