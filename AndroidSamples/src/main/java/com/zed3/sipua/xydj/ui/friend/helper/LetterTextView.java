package com.zed3.sipua.xydj.ui.friend.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class LetterTextView extends View {

	private static Context mContext;

	// ��ĸ����
	private static final String[] letters = { "#","A", "B", "C", "D", "E", "F",
			"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
			"T", "U", "V", "W", "X", "Y", "Z" };
	private static final String COLOR_NORMAL = "#666666";
	private static final String COLOR_SELECT = "#6699FF";
	private static final String COLOR_ONTOUCH = "#40000000";
	
	public static final int TYPE_DOWN = 0;
	public static final int TYPE_MOVE = 1;

	private static int selectItem = 0;

	private static boolean isOnTouch = false;

	private Paint paint;

	private static final String TAG = "LetterTextView";

	private OnLetterTextChangeListener mListener;

	public LetterTextView(Context context) {
		super(context);
		mContext = context;
	}

	public LetterTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public LetterTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.parseColor(COLOR_NORMAL));
		paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,15,getResources().getDisplayMetrics()));
		// �����
		paint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (isOnTouch) {
			canvas.drawColor(Color.parseColor(COLOR_ONTOUCH));
		}
		int lHeight = getHeight() / 27;
		int startY = 0;
		float startX = 0;
		for (int i = 0; i < letters.length; i++) {
			init();
			startY = lHeight * i + lHeight;
			String letter = letters[i];
			startX = getWidth() / 2 - paint.measureText(letter) / 2;
			if (selectItem == i) {
				paint.setColor(Color.parseColor(COLOR_SELECT));
				paint.setFakeBoldText(true);
			} else {
				paint.setColor(Color.parseColor(COLOR_NORMAL));
			}
			// ����X�����Ͼ��У�Ĭ����Paint.Align.LEFT�����
			// paint.setTextAlign(Paint.Align.CENTER);
			// ������startX �����������Ĳ����������õĲ���Ϊ����Ĭ�����������Ļ�ϵ�λ�ã� startY
			// baseLine��y�����ϵ�λ��
			canvas.drawText(letter, startX, startY, paint);
			paint.reset();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// ������Ļ���Y����ֵ
		float curY = event.getY();
		// �������ǰ�����λ�������ĸ���ĸ
		int curItem = (int) (curY / getHeight() * letters.length);
		Log.d(TAG, "curItem == " + curItem + "selectItem = " + selectItem);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isOnTouch = true;
			refreshSelect(TYPE_DOWN,curItem);
			break;
		case MotionEvent.ACTION_MOVE:
			refreshSelect(TYPE_MOVE,curItem);
			break;
		case MotionEvent.ACTION_UP:
			selectItem = curItem;
			isOnTouch = false;
			invalidate();
			break;
		}
		return true;
	}
	

	private void refreshSelect(int type,int curItem) {
		if (curItem < 0) {
			selectItem = 0;
		} else if (curItem >= letters.length) {
			selectItem = letters.length - 1;
		} else if (selectItem != curItem && null != mListener) {
			if (curItem >= 0 && curItem < letters.length) {
				selectItem = curItem;
				mListener.onLetterChage(type,curItem, letters[curItem]);
			}
		}
		invalidate();
	}

	/**
	 * �ṩһ����ڣ�����������ĸѡ��ı��¼�
	 * 
	 * @author zed
	 * 
	 */
	public interface OnLetterTextChangeListener {
		public void onLetterChage(int type, int item, String letter);
	}

	/**
	 * ���ü����¼�
	 * 
	 * @param listener
	 */
	public void setOnLetterTextChangeListener(
			OnLetterTextChangeListener listener) {
		this.mListener = listener;
	}

	public void changeSelectItem(String curletter) {
		for (int i = 0; i < letters.length; i++) {
			if (curletter.equals(letters[i])) {
				if (i < 0) {
					selectItem = 0;
				} else if (i >= letters.length) {
					selectItem = letters.length - 1;
				} else if (selectItem != i && null != mListener) {
					if (i >= 0 && i < letters.length) {
						selectItem = i;
					}
				}
				invalidate();
			}
		}

	}

}
