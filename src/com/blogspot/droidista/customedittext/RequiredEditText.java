package com.blogspot.droidista.customedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

public class RequiredEditText extends EditText {

	private boolean mRequired;
	private String mErrorMessage;

	public RequiredEditText(Context context) {
		super(context);
	}

	public RequiredEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public RequiredEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	private void init(AttributeSet attrs) { 
		TypedArray a=getContext().obtainStyledAttributes(
				attrs,
				R.styleable.RequiredEditText);

		try {
			setRequired(a.getBoolean(R.styleable.RequiredEditText_required, false), 
					a.getString(R.styleable.RequiredEditText_errorMessage));
		} finally {
			//Don't forget this, we need to recycle
			a.recycle();
		}
	}

	
	/**
	 * Set this EditText's requirement validation. The error message
	 * will be set to <code>null</code> by default if not provided.
	 * 
	 * @param required
	 * @param errorMessage (optional)
	 */
	public void setRequired(boolean required, String errorMessage) {
		this.mRequired = required;
		this.mErrorMessage = errorMessage;
		
		manageRequiredField(required);
		
		invalidate();
		requestLayout();
	}
	
	public void setRequired(boolean required) {
		setRequired(required, null);
	}

	private void manageRequiredField(boolean required) {
		// If we are required, set the listeners
		if(required) {
			setOnFocusChangeListener(mFocusChangeListener);
			addTextChangedListener(mTextWatcher);
		} else {
			// In case there is an error message already, clear it
			setError(null);
			
			// Remove the listeners
			setOnFocusChangeListener(null);
			removeTextChangedListener(mTextWatcher);
		}
	}

	/**
	 * Lets you know if this field is set as required or not
	 * @return
	 */
	public boolean isRequiredField() {
		return mRequired;
	}

	OnFocusChangeListener mFocusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus && mRequired){
				isRequiredFieldFilled();
			}
		}
	};
	
	TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			setError(null);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* do nothing */ }

		@Override
		public void afterTextChanged(Editable s) { /* do nothing */ }			
	};

	private boolean isRequiredFieldFilled() {
		if(TextUtils.isEmpty(getText().toString().trim())){
			showRequiredErrorDrawable();
			return false;
		}
		return true;
	}

	private void showRequiredErrorDrawable() {
		setError(mErrorMessage);
	}

}
