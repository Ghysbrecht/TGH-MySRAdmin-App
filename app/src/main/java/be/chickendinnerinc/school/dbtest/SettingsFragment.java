package be.chickendinnerinc.school.dbtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Set;


public class SettingsFragment extends Fragment implements View.OnClickListener{
    private SettingsFragment.OnFragmentInteractionListener mListener;
    private View mView;
    private EditText ipText;
    private String serverAddress;

    public SettingsFragment() {}


    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_settings, container, false);

        ipText = (EditText)mView.findViewById(R.id.ipTextField);
        Button saveChangesButton = mView.findViewById(R.id.saveButton);
        saveChangesButton.setOnClickListener(this);

        SharedPreferences settings = this.getActivity().getSharedPreferences("MyPrefsFile", 0);
        serverAddress  = settings.getString("serverAddress", "http://localhost:3000/");

        ipText.setText(serverAddress);

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SettingsFragment.OnFragmentInteractionListener) {
            mListener = (SettingsFragment.OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        serverAddress = ipText.getText().toString();
        SharedPreferences settings = this.getActivity().getSharedPreferences("MyPrefsFile", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("serverAddress", serverAddress);
        editor.commit();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



}
