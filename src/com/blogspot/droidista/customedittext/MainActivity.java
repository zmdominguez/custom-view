package com.blogspot.droidista.customedittext;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		@InjectView(R.id.form_text_required) RequiredEditText mRequiredText;
		@InjectView(R.id.form_text_not_required) RequiredEditText mNotRequiredText;
		@InjectView(R.id.button_go) Button mGoButton;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			ButterKnife.inject(this, rootView);
			return rootView;
		}
		
		@OnClick(R.id.button_go)
		public void onClick(View view){
			validateForm();
		}

		private void validateForm() {
			
			boolean isFormValid = mRequiredText.isFieldFilled(true);
			
			if(!isFormValid) {
				Toast.makeText(getActivity(), "Please fill up all required fields.", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getActivity(), "Valid form, do something else!", Toast.LENGTH_LONG).show();
			}
		}
	}

}
