package com.example.lyrictextview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader.TileMode;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class LyricTextView extends TextView {
	private float MIN_PROGRESS;
	private float MAX_PROGRESS;
	private Matrix matrix = new Matrix();
	private LinearGradient shader;
	private float progress = MIN_PROGRESS;
	private int coverColor, defaultColor;

	public LyricTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LyricTextView);
		coverColor = a.getColor(R.styleable.LyricTextView_coverColor, 0);
		
		ColorStateList textColors = getTextColors();
		defaultColor = textColors.getDefaultColor();
		if (coverColor == 0) {
			coverColor = (defaultColor & 0xff000000) | ((defaultColor & 0x00ffffff) ^ 0x00ffffff);
		}
		a.recycle();
	}

	public LyricTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LyricTextView(Context context) {
		this(context, null);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		if (w > 0) {
			TextPaint paint = getPaint();
			shader = new LinearGradient(0, 0, w, 0, new int[] {coverColor, defaultColor}, new float[] {1f * (w - 1) / w, 1}, TileMode.CLAMP);
			progress = MIN_PROGRESS = 1f / w;
			MAX_PROGRESS = 1 + MIN_PROGRESS;
			paint.setShader(shader);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (shader != null) {
			matrix.setScale(progress, 1);
			shader.setLocalMatrix(matrix);
		}
		super.onDraw(canvas);
	}
	
	public void setProgress(float progress) {
		if (progress < MIN_PROGRESS) progress = MIN_PROGRESS;
		if (progress > MAX_PROGRESS) progress = MAX_PROGRESS;
		this.progress = progress;
		invalidate();
	}
}
