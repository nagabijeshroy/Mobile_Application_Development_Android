package com.example.inclass08;

import com.example.inclass08.R;
import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link SignupFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link SignupFragment#newInstance} factory method to create an instance of
 * this fragment.
 * 
 */
public class SignupFragment extends Fragment {
	
	Button cancelButton;
	EditText firstNameText;
	EditText emailText;
	EditText passwordText;
	EditText confirmPasswordText;
	Button signupButton;
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private IntfSignupFragment mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment SignupFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static SignupFragment newInstance(String param1, String param2) {
		SignupFragment fragment = new SignupFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public SignupFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		View view = inflater.inflate(R.layout.fragment_signup, container, false);
		setHasOptionsMenu(false);
		firstNameText = (EditText)view.findViewById(R.id.editTextUserName);
		emailText = (EditText)view.findViewById(R.id.editTextEmail);
		passwordText = (EditText)view.findViewById(R.id.editTextPassword);
		confirmPasswordText = (EditText)view.findViewById(R.id.editTextPasswordConfirm);
		signupButton = (Button)view.findViewById(R.id.buttonSignup);
		signupButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(firstNameText.getText().toString().trim().equals("")||emailText.getText().toString().trim().equals("")
						||passwordText.getText().toString().trim().equals("")||confirmPasswordText.getText().toString().trim().equals("")){
					Toast.makeText(getActivity(), "Form fields cannot be empty", Toast.LENGTH_LONG).show();
				}else{
					if(passwordText.getText().toString().trim().length()!=0&&passwordText.getText().toString().equals(confirmPasswordText.getText().toString())){
						final ParseUser user = new ParseUser();
						user.setUsername(emailText.getText().toString());
						user.setPassword(passwordText.getText().toString());
						user.setEmail(emailText.getText().toString());
						// other fields can be set just like with ParseObject
						user.signUpInBackground(new SignUpCallback() {
							@Override
							public void done(ParseException e) {
								if(e==null){
									Toast.makeText(getActivity(), "Sign Up Successful", Toast.LENGTH_LONG).show();
									ParseUser.logInInBackground(emailText.getText().toString(), passwordText.getText().toString(),new LogInCallback() {
										@Override
										public void done(ParseUser user, ParseException e) {
											mListener.onSignup();
										}
									});
								}else{
									Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_LONG).show();
								}
							}
						});
					}else{
						Toast.makeText(getActivity(), "Password and confirm password didn't match", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
		cancelButton = (Button)view.findViewById(R.id.buttonCancel);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Intent intent = new Intent(getActivity(), LoginActivity.class);
	        	startActivity(intent);
	        	finish();*/
				mListener.onCancelClick();
			}
		});
		
		return view;
		
		
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			//mListener.onSignup(uri);;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (IntfSignupFragment) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface IntfSignupFragment {
		// TODO: Update argument type and name
		public void onSignup();
		public void onCancelClick();
	}

}
