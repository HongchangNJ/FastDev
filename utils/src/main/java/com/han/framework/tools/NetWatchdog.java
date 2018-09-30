package com.han.framework.tools;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hongchang on 2017/12/7.
 */

public class NetWatchdog {
    Activity a;
    private NetWatchdog.NetChangeListener b;
    private IntentFilter c = new IntentFilter();
    private BroadcastReceiver d = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext().getSystemService("connectivity");
            NetworkInfo wifiNetworkInfo = cm.getNetworkInfo(1);
            NetworkInfo mobileNetworkInfo = cm.getNetworkInfo(0);
            NetworkInfo.State wifiState = NetworkInfo.State.UNKNOWN;
            NetworkInfo.State mobileState = NetworkInfo.State.UNKNOWN;
            if(wifiNetworkInfo != null) {
                wifiState = wifiNetworkInfo.getState();
            }

            if(mobileNetworkInfo != null) {
                mobileState = mobileNetworkInfo.getState();
            }

            if(NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {
                //VcPlayerLog.d("lfj1113", "onWifiTo4G()");
                if(NetWatchdog.this.b != null) {
                    NetWatchdog.this.b.onWifiTo4G();
                }
            } else if(NetworkInfo.State.CONNECTED == wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                if(NetWatchdog.this.b != null) {
                    NetWatchdog.this.b.on4GToWifi();
                }
            } else if(NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState && NetWatchdog.this.b != null) {
                NetWatchdog.this.b.onNetDisconnected();
            }

        }
    };

    public static boolean hasNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext().getSystemService("connectivity");
        NetworkInfo wifiNetworkInfo = cm.getNetworkInfo(1);
        NetworkInfo mobileNetworkInfo = cm.getNetworkInfo(0);
        NetworkInfo.State wifiState = NetworkInfo.State.UNKNOWN;
        NetworkInfo.State mobileState = NetworkInfo.State.UNKNOWN;
        if(wifiNetworkInfo != null) {
            wifiState = wifiNetworkInfo.getState();
        }

        if(mobileNetworkInfo != null) {
            mobileState = mobileNetworkInfo.getState();
        }

        return NetworkInfo.State.CONNECTED == wifiState || NetworkInfo.State.CONNECTED == mobileState;
    }

    public static boolean is4GConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext().getSystemService("connectivity");
        NetworkInfo mobileNetworkInfo = cm.getNetworkInfo(0);
        NetworkInfo.State mobileState = NetworkInfo.State.UNKNOWN;
        if(mobileNetworkInfo != null) {
            mobileState = mobileNetworkInfo.getState();
        }

        return NetworkInfo.State.CONNECTED == mobileState;
    }

    public NetWatchdog(Activity activity) {
        this.a = activity;
        this.c.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    }

    public void setNetChangeListener(NetWatchdog.NetChangeListener l) {
        this.b = l;
    }

    public void startWatch() {
        try {
            this.a.registerReceiver(this.d, this.c);
        } catch (Exception var2) {
            ;
        }

    }

    public void stopWatch() {
        try {
            this.a.unregisterReceiver(this.d);
        } catch (Exception var2) {
            ;
        }

    }

    public interface NetChangeListener {
        void onWifiTo4G();

        void on4GToWifi();

        void onNetDisconnected();
    }}
