package com.example.homework5;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends Activity {

	private WebView mWebview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url = getIntent().getExtras().getString("url");
		super.onCreate(savedInstanceState);

		mWebview = new WebView(this);

		mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

		final Activity activity = this;

		mWebview.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
			}
		});

		mWebview.loadUrl(url);
		setContentView(mWebview);

	}

}
