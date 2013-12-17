package com.example.reapertoolbarremote.main;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

import com.example.reapertoolbarremote.R;
import com.example.reapertoolbarremote.R.id;
import com.example.reapertoolbarremote.R.layout;
import com.example.reapertoolbarremote.R.menu;
import com.example.reapertoolbarremote.osc.OscServer;
import com.example.reapertoolbarremote.osc.OscListener;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OscListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startOsc();
		osc.addOscListener(this);
	}
	private void startOsc() {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void sendOsc(View view) {
		osc.sendOsc(view);
	}

	@Override
	protected void onDestroy() {
		osc.closeOscPorts();
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		osc.stop();
		super.onStop();
	}

	@Override
	protected void onPause() {
		osc.stop();
		super.onPause();
	}

	@Override
	protected void onStart() {
		osc.openOscPorts();
		super.onStart();
	}

	@Override
	protected void onRestart() {
		osc.start();
		super.onRestart();
	}

	@Override
	protected void onResume() {
		osc.start();
		super.onResume();
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
		String ipNumber = this.wifiIpAddress(this);
		int fest=0;
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

	private OscServer osc;

	@Override
	public void messageReceived(OSCMessage oscm) {
		// TODO Auto-generated method stub
		
	}
}
