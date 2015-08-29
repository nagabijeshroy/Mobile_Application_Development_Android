package com.example.inclass08;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.example.inclass08.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link TodoFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link TodoFragment#newInstance} factory method to create an instance of this
 * fragment.
 *
 */
public class TodoFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	ListView todoListView;

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private IntfTodoFragment mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment TodoFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static TodoFragment newInstance(String param1, String param2) {
		TodoFragment fragment = new TodoFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public TodoFragment() {
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
		
		setHasOptionsMenu(true);
		
		View view =  inflater.inflate(R.layout.fragment_todo, container, false);
		todoListView = (ListView) view.findViewById(R.id.listView1);
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
			query.whereEqualTo("username", currentUser.getUsername());
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> todoList, ParseException e) {
					if (e == null) {
						ArrayList<String> todoArrayList = new ArrayList<String>();
						 for(ParseObject listItem : todoList){ 
						 todoArrayList.add((String) listItem.get("item"));
						 }
						 ArrayAdapter<String> adapter = new	ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,todoArrayList);
						 adapter.setNotifyOnChange(true);
						 todoListView.setAdapter(adapter);
						 
					} else {

					}
				}
			});
		}
		return view;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.menuitemclick(uri);;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (IntfTodoFragment) activity;
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
	public interface IntfTodoFragment {
		// TODO: Update argument type and name
		public void menuitemclick(Uri uri);
	}

}