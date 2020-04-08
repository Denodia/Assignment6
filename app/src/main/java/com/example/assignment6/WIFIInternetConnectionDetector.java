package com.example.assignment6;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WIFIInternetConnectionDetector {
    private Context _context;

    public WIFIInternetConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean checkMobileInternetConn() {
        ConnectivityManager conMgr = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {
            return true;
            // notify user you are online

        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {
            return false;
            // notify user you are not online
        }
        return false;
    }
}
