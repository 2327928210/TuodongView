package com.haoke.tuodong;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ColorView extends View {
	private int mColor;

	public ColorView(Context context) {
		super(context);
	}

	public ColorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.color_view);
		if (null != typedArray) {
			mColor = typedArray
					.getColor(R.styleable.color_view_bg_color, 0xFFFFFF);
			typedArray.recycle();
		}
	}

	public void setColor(int color) {
		mColor = color;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(mColor);
	}

}
