 package com.zenwsmp.pmwani;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

 public class connectionDector {

	

		private Context _context;

		public connectionDector(Context context){
		    this._context = context;
		}

		public boolean isConnectingToInternet(){
		    ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		      if (connectivity != null) 
		      {
		          NetworkInfo[] info = connectivity.getAllNetworkInfo();
		          if (info != null) 
		              for (int i = 0; i < info.length; i++) 
		                  if (info[i].getState() == NetworkInfo.State.CONNECTED)
		                  {
		                      return true;
		                  }

		      }
		      return false;
		   }
		public boolean checkNetwork() {
			 boolean wifiDataAvailable = false;
			 boolean mobileDataAvailable = false;
			ConnectivityManager conManager = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
			for (NetworkInfo netInfo : networkInfo) {
			  if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
			    if (netInfo.isConnected())
			        wifiDataAvailable = true;
			  
			    if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
			        if (netInfo.isConnected())
			        mobileDataAvailable = true;
			    }
			    return wifiDataAvailable || mobileDataAvailable;
			}

	 public boolean isOnline() {
		 Runtime runtime = Runtime.getRuntime();
		 try {
			 Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
			 int     exitValue = ipProcess.waitFor();
			 return (exitValue == 0);
		 }
		 catch (IOException e)          { e.printStackTrace(); }
		 catch (InterruptedException e) { e.printStackTrace(); }

		 return false;
	 }
	 public boolean isOnline(String ip) {
		 Runtime runtime = Runtime.getRuntime();
		 try {
			 Process ipProcess = runtime.exec("/system/bin/ping -c "+ip);
			 int     exitValue = ipProcess.waitFor();
			 return (exitValue == 0);
		 }
		 catch (IOException e)          { e.printStackTrace(); }
		 catch (InterruptedException e) { e.printStackTrace(); }

		 return false;
	 }
	/*public boolean checkOnlineState() {
		ConnectivityManager CManager =
				(ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo NInfo = CManager.getActiveNetworkInfo();
		if (NInfo != null && NInfo.isConnectedOrConnecting()) {
			try {
				if (InetAddress.getByName("www.xy.com").isReachable(2000))
                {
                    // host reachable
					return  true;
                }
                else
                {
                    return  false;
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}*/
		}

