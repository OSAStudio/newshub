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
 * 分割输入框
 * 
 * @author Administrator
 * 
 */
public class DivisionEditText extends EditText {

	/* 内容数组 */
	private String[] text;
	/* 数组实际长度 (内容+分隔符) */
	private Integer length;
	/* 允许输入的长度 */
	private Integer totalLength;
	/* 每组的长度 */
	private Integer eachLength;
	/* 分隔符 */
	private String delimiter;
	/* 占位符 */
	private String placeHolder;

	public DivisionEditText(Context context) {
		super(context);
	}

	public DivisionEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			// 初始化属性
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

			// 初始化
			init();

			// 内容变化监听
			this.addTextChangedListener(new DivisionTextWatcher());
			// 获取焦点监听
			this.setOnFocusChangeListener(new DivisionFocusChangeListener());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DivisionEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 初始化
	 */
	public void init() {
		// 总共分几组
		int groupNum = 0;
		// 如果每组长度(除数)不为0,计算
		if (this.eachLength != 0) {
			groupNum = this.totalLength / this.eachLength;
		}
		// 实际长度
		length = this.totalLength + this.eachLength != 0 ? this.totalLength
				+ groupNum - 1 : 0;
		// 初始化数组
		text = new String[this.length];
		// 如果数组大小大于0,初始化里面内容
		// 空格占位,分隔符占位
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				if (i != 0 && (i + 1) % (this.eachLength + 1) == 0) {
					text[i] = this.delimiter;
				} else {
					text[i] = placeHolder;
				}
			}
			// 设置文本
			mySetText();
			// 设置焦点
			mySetSelection();
		}
	}

	/**
	 * 获取结果
	 * 
	 * @return
	 */
	public String getResult() {
		StringBuffer buffer = new StringBuffer();
		for (String item : text) {
			if (!placeHolder.equals(item) && !delimiter.equals(item)) {
				buffer.append(item);
			}
		}
		return buffer.toString();
	}

	/**
	 * 文本监听
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
			// 如果当前长度小于数组长度,认为使用退格
			if (s.length() < length) {
				// 光标所在位置
				int index = DivisionEditText.this.getSelectionStart();
				// 删除的字符
				String deleteStr = text[index];
				// 如果是分隔符,删除分隔符前一个
				if (delimiter.equals(deleteStr)) {
					index--;
				}
				// 置空
				text[index] = placeHolder;
				// 查看前一个是否为分隔符
				if (index - 1 >= 0) {
					if (delimiter.equals(text[index - 1])) {
						index--;
					}
				}
				// 设置文本
				mySetText();
				// 设置焦点
				mySetSelection(index);
			}
			// 只能一个一个字符输入
			if (count == 1) {
				// 从光标起始,是否还有空的位置
//				int index = isBlank(DivisionEditText.this.getSelectionStart());
			   int index = getEnableIndex(DivisionEditText.this.getSelectionStart());
				// 如果还有
				if (index != -1) {
					// 输入框内的字符串
					String allStr = s.toString();
					// 输入的字符串
					String inputStr = allStr.substring(start, start + count);
					// 替换占位符
					text[index] = inputStr;
				}
				// 设置文本
				mySetText();
				// 设置焦点
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
	   Utils.logd("getEnableIndex", "selection="+selection);
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
	 * 获取焦点监听
	 * 
	 * @author Administrator
	 * 
	 */
	private class DivisionFocusChangeListener implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				// 设置焦点
				mySetSelection(0);
			}
		}
	}

	/**
	 * 设置文本
	 * 
	 * @param text
	 */
	private void mySetText() {
		StringBuffer buffer = new StringBuffer();
		for (String item : text) {
			buffer.append(item);
		}
		// 设置文本
		setText(buffer);
	}

	/**
	 * 设置焦点
	 * 
	 * @param text
	 */
	private void mySetSelection() {
		mySetSelection(fullSelection());
	}

	/**
	 * 设置焦点
	 * 
	 * @param text
	 */
	private void mySetSelection(int index) {
	   Utils.logd("mySetSelection", "index="+index);
		DivisionEditText.this.setSelection(index);
	}

	/**
	 * 从光标位置起始,检查后面是否还有空的占位符
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
	 * 最后一个不空的字符后的光标位置
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
