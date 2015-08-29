package com.example.inclass08;

import com.example.inclass08.R;
import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link LoginFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link LoginFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class LoginFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	EditText emailText;
	EditText passwordText;
	Button loginButton;
	Button createAccountButton;

	private IntfonLoginFragment mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment LoginFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static LoginFragment newInstance(String param1, String param2) {
		LoginFragment fragment = new LoginFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public LoginFragment() {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser==null){
		setHasOptionsMenu(false);
		emailText = (EditText) view.findViewById(R.id.editTextEmail);
		passwordText = (EditText) view.findViewById(R.id.editTextPassword);
		loginButton = (Button) view.findViewById(R.id.buttonLogin);
		
		createAccountButton = (Button) view.findViewById(R.id.buttonCreateNewAccount);

		createAccountButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.onCreateAccountClick();

			}
		});

		view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (emailText.getText().toString().length() != 0 && !emailText.getText().toString().trim().equals("")
						&& passwordText.getText().toString().length() != 0 && !passwordText.getText().toString().trim().equals("")) {
					ParseUser.logInInBackground(emailText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {

						@Override
						public void done(ParseUser user, ParseException e) {
							if (user != null) {
								/*
								 * Intent intent = new
								 * Intent(LoginActivity.this,
								 * ToDoActivity.class); startActivity(intent);
								 * finish();
								 */
								Toast.makeText(getActivity(), "going to todo", Toast.LENGTH_LONG).show();

								mListener.onLoginClick();

							} else {
								Toast.makeText(getActivity(), "Incorrect Email or Password", Toast.LENGTH_LONG).show();
							}
						}
					});
				} else {
					Toast.makeText(getActivity(), "Email and password cannot be empty", Toast.LENGTH_LONG).show();
				}
			}
			// mListener.onLoginClick();

		});
		}else{
			mListener.onLoginClick();
		}
		return view;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onLoginClick();
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (IntfonLoginFragment) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
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
	public interface IntfonLoginFragment {
		// TODO: Update argument type and name
		public void onLoginClick();
		public void onCreateAccountClick();
	}

}
