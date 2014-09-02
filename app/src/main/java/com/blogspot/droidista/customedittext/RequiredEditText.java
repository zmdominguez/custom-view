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
					a.getString(R.styleable.RequiredEditText_error_message));
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
		
		resetView();
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

	/**
	 * Set the error message to be shown during validation.
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.mErrorMessage = errorMessage;
		
		resetView();
	}
	

	public String getErrorMessage() {
		return mErrorMessage;
	}
	
	/**
	 * Invalidate and ask to re-draw.
	 */
	private void resetView() {
		invalidate();
		requestLayout();
	}
	
	OnFocusChangeListener mFocusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus && mRequired){
				isFieldFilled(true);
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

	private void showRequiredErrorDrawable() {
		setError(mErrorMessage);
	}
	
	/**
	 * Checks if this field is filled up. This method is useful during form validation.
	 * 
	 * <p>Iterate through the required fields, calling this method in turn.</p>
	 * 
	 * @param showError if this is true AND the field is required, the error message is shown.
	 * Set the error message via {@link RequiredEditText#setMessage} or via XML.
	 * @return
	 */
	public boolean isFieldFilled(boolean showError) {
		if(TextUtils.isEmpty(getText().toString().trim())){
			if(mRequired && showError) {
				showRequiredErrorDrawable();
			}
			return false;
		}
		
		return true;
	}

}
