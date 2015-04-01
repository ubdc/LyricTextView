package com.example.lyrictextview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnSeekBarChangeListener {
	Handler h = new Handler();
	Scroller s;
	Runnable r = new Runnable() {
		
		@Override
		public void run() {
			if (s.computeScrollOffset()) {
				ltv.setProgress(s.getCurrX() / 100f);
				h.post(this);
			} else {
				ltv.setProgress(1);
			}
		}
	};
	
	LyricTextView ltv;
	SeekBar seekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ltv = (LyricTextView) findViewById(R.id.ltv);
		seekBar = (SeekBar) findViewById(R.id.progressBar);
		seekBar.setOnSeekBarChangeListener(this);
		s = new Scroller(this, new LinearInterpolator());
	}
	
	public void go(View v) {
		s.abortAnimation();
		s.startScroll(0, 0, 100, 0, 2000);
		h.post(r);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		ltv.setProgress(progress / 100f);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}
}
