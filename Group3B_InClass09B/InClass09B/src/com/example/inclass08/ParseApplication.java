package com.example.inclass08;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    
    ParseCrashReporting.enable(this);
    ParseUser.enableAutomaticUser();
    Parse.initialize(this, "rp5GsEzxzkg5jlRtbcJa1bFkXhLzprFMWICbZNwD", "cXpuYdo6t0ZG15a9bM00wa6r8udZhcDeFt7lRvJO");
   // Parse.initialize(this, "dDN031rVcAkHvbpyEqidvTbZq27l2ubQsR9jNo6B", "fMBql1xV83yeUrro1YPpIbEgMoWkshxu9A7RirN3");
    ParseACL defaultACL = new ParseACL();
    ParseACL.setDefaultACL(defaultACL, true);
    
  }
}
