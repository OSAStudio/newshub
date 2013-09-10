package com.osastudio.newshub.widgets;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.utils.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * 鍒嗗壊杈撳叆妗�
 * 
 * @author Administrator
 * 
 */
public class DivisionEditText extends EditText {

	/* 鍐呭鏁扮粍 */
	private String[] text;
	/* 鏁扮粍瀹為檯闀垮害 (鍐呭+鍒嗛殧绗� */
	private Integer length;
	/* 鍏佽杈撳叆鐨勯暱搴�*/
	private Integer totalLength;
	/* 姣忕粍鐨勯暱搴�*/
	private Integer eachLength;
	/* 鍒嗛殧绗�*/
	private String delimiter;
	/* 鍗犱綅绗�*/
	private String placeHolder;

	public DivisionEditText(Context context) {
		super(context);
	}

	public DivisionEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			// 鍒濆鍖栧睘鎬�
			TypedArray typedArray = context.obtainStyledAttributes(attrs,
					R.styleable.EditText);
			this.totalLength = typedArray.getInteger(
					R.styleable.EditText_totalLength, 0);
			this.eachLength = typedArray.getInteger(
					R.styleable.EditText_eachLength, 0);
			this.delimiter = typedArray
					.getString(R.styleable.EditText_delimiter);
			if (this.delimiter == null || this.delimiter.length() == 0) {
				this.delimiter = "-";
			}
			this.placeHolder = typedArray
					.getString(R.styleable.EditText_placeHolder);
			if (this.placeHolder == null || this.placeHolder.length() == 0) {
				this.placeHolder = " ";
			}
			typedArray.recycle();

			// 鍒濆鍖�
			init();

			// 鍐呭鍙樺寲鐩戝惉
			this.addTextChangedListener(new DivisionTextWatcher());
			// 鑾峰彇鐒︾偣鐩戝惉
			this.setOnFocusChangeListener(new DivisionFocusChangeListener());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DivisionEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 鍒濆鍖�
	 */
	public void init() {
		// 鎬诲叡鍒嗗嚑缁�
		int groupNum = 0;
		// 濡傛灉姣忕粍闀垮害(闄ゆ暟)涓嶄负0,璁＄畻
		if (this.eachLength != 0) {
			groupNum = this.totalLength / this.eachLength;
			if (this.totalLength % this.eachLength != 0) {
				groupNum++;
			}
		}
		// 瀹為檯闀垮害
		length = this.totalLength + this.eachLength != 0 ? this.totalLength
				+ groupNum - 1 : 0;
		// 鍒濆鍖栨暟缁�
		text = new String[this.length];
		// 濡傛灉鏁扮粍澶у皬澶т簬0,鍒濆鍖栭噷闈㈠唴瀹�
		// 绌烘牸鍗犱綅,鍒嗛殧绗﹀崰浣�
		if (length > 0) {
			for (int i = 0; i < length; i++) {
//			for (int i = length - 1; i >= 0; i--) {
				if (i != 0 && (length - i)%(this.eachLength + 1) == 0) {//(i + 1) % (this.eachLength + 1) == 0) {
					text[i] = this.delimiter;
				} else {
					text[i] = placeHolder;
				}
			}
			// 璁剧疆鏂囨湰
			mySetText();
			// 璁剧疆鐒︾偣
			mySetSelection();
		}
	}

	/**
	 * 鑾峰彇缁撴灉
	 * 
	 * @return
	 */
	public String getResult() {
		StringBuffer buffer = new StringBuffer();
		for (String item : text) {
			if (!delimiter.equals(item)) {
				buffer.append(item);
			}
		}
		return buffer.toString();
	}

	/**
	 * 鏂囨湰鐩戝惉
	 * 
	 * @author Administrator
	 * 
	 */
	private class DivisionTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// 濡傛灉褰撳墠闀垮害灏忎簬鏁扮粍闀垮害,璁や负浣跨敤閫�牸
			if (s.length() < length) {
				// 鍏夋爣鎵�湪浣嶇疆
				int index = DivisionEditText.this.getSelectionStart();
				// 鍒犻櫎鐨勫瓧绗�
				String deleteStr = text[index];
				// 濡傛灉鏄垎闅旂,鍒犻櫎鍒嗛殧绗﹀墠涓�釜
				if (delimiter.equals(deleteStr)) {
					index--;
				}
				// 缃┖
				text[index] = placeHolder;
				// 鏌ョ湅鍓嶄竴涓槸鍚︿负鍒嗛殧绗�
				if (index - 1 >= 0) {
					if (delimiter.equals(text[index - 1])) {
						index--;
					}
				}
				// 璁剧疆鏂囨湰
				mySetText();
				// 璁剧疆鐒︾偣
				mySetSelection(index);
			}
			// 鍙兘涓�釜涓�釜瀛楃杈撳叆
			if (count == 1) {
				// 浠庡厜鏍囪捣濮�鏄惁杩樻湁绌虹殑浣嶇疆
//				int index = isBlank(DivisionEditText.this.getSelectionStart());
			   int index = getEnableIndex(DivisionEditText.this.getSelectionStart());
				// 濡傛灉杩樻湁
				if (index != -1) {
					// 杈撳叆妗嗗唴鐨勫瓧绗︿覆
					String allStr = s.toString();
					// 杈撳叆鐨勫瓧绗︿覆
					String inputStr = allStr.substring(start, start + count);
					// 鏇挎崲鍗犱綅绗�
					text[index] = inputStr;
				}
				// 璁剧疆鏂囨湰
				mySetText();
				// 璁剧疆鐒︾偣
				if (index < 0 || index >= length) {
				mySetSelection();
				} else {
				   index++;
				   mySetSelection(index);
				}
			}
		}
	}
	
	private int getEnableIndex(int selection) {
//	   Utils.log("getEnableIndex", "selection="+selection);
	   int index = selection-1;
	   if (index < length && delimiter.equals(text[index])) {
         index++;
      }
	   if (index >= length) {
	      index = -1;
	   }
	   return index;
	}

	/**
	 * 鑾峰彇鐒︾偣鐩戝惉
	 * 
	 * @author Administrator
	 * 
	 */
	private class DivisionFocusChangeListener implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				// 璁剧疆鐒︾偣
				mySetSelection(0);
			}
		}
	}

	/**
	 * 璁剧疆鏂囨湰
	 * 
	 * @param text
	 */
	private void mySetText() {
		StringBuffer buffer = new StringBuffer();
		for (String item : text) {
			buffer.append(item);
		}
		// 璁剧疆鏂囨湰
		setText(buffer);
	}

	/**
	 * 璁剧疆鐒︾偣
	 * 
	 * @param text
	 */
	private void mySetSelection() {
		mySetSelection(fullSelection());
	}

	/**
	 * 璁剧疆鐒︾偣
	 * 
	 * @param text
	 */
	private void mySetSelection(int index) {
//	   Utils.log("mySetSelection", "index="+index);
		DivisionEditText.this.setSelection(index);
	}

	/**
	 * 浠庡厜鏍囦綅缃捣濮�妫�煡鍚庨潰鏄惁杩樻湁绌虹殑鍗犱綅绗�
	 * 
	 * @param text
	 * @param selection
	 * @return
	 */
	private int isBlank(int selection) {
		int index = -1;
		for (int i = selection - 1; i < length; i++) {
			if (placeHolder.equals(text[i])) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * 鏈�悗涓�釜涓嶇┖鐨勫瓧绗﹀悗鐨勫厜鏍囦綅缃�
	 * 
	 * @param text
	 * @return
	 */
	private int fullSelection() {
		int index = 0;
		for (int i = 0; i < length; i++) {
			if (!placeHolder.equals(text[i]) && !delimiter.equals(text[i])) {
				index = i + 1;
			}
		}
		return index;
	}

	public Integer getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}

	public Integer getEachLength() {
		return eachLength;
	}

	public void setEachLength(Integer eachLength) {
		this.eachLength = eachLength;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getPlaceHolder() {
		return placeHolder;
	}

	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}

}
