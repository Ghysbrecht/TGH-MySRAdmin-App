package be.chickendinnerinc.school.dbtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UserFragment extends Fragment implements View.OnClickListener{


    private OnFragmentInteractionListener mListener;
    private View mView;
    private EditText nameText, uidText;
    private String serverAddress;
    Database database;


    public UserFragment() {}

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView =  inflater.inflate(R.layout.fragment_user, container, false);
        Log.e("SRADM","UserFragment created!" );

        nameText = (EditText)mView.findViewById(R.id.nameField);
        uidText = (EditText)mView.findViewById(R.id.uidField);

        Button createButton = mView.findViewById(R.id.createUserButton);
        createButton.setOnClickListener(this);

        Button rfidButton = mView.findViewById(R.id.rfidButton);
        rfidButton.setOnClickListener(this);

        SharedPreferences settings = this.getActivity().getSharedPreferences("MyPrefsFile", 0);
        serverAddress  = settings.getString("serverAddress", "http://localhost:3000/");

        database = new Database(serverAddress);

        return mView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
        switch(view.getId()){
            case R.id.rfidButton:
                //getUidViaRfid();
                break;
            case R.id.createUserButton:
                createUser();
                break;
        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void createUser(){
        String name = nameText.getText().toString();
        String uid = uidText.getText().toString();
        uid = uid.toUpperCase();
        uidText.setText("");
        nameText.setText("");
        int stringLength = 16;
        if(uid.length()<16) stringLength = uid.length();
        uid = uid.substring(0,stringLength);
        while(uid.length() < 16) uid = uid.concat("0");
        Log.e("SRADM","Creating user: " + name + " with UID: " + uid + " UID Length: " + uid.length() );

        database.createParticipant(new Participant(name, uid));
        Toast.makeText(getContext(), "User created!", Toast.LENGTH_SHORT).show();

    }

    public void uidRfidHandler(String uid){
        if(uidText != null) uidText.setText(uid);
    }



}
