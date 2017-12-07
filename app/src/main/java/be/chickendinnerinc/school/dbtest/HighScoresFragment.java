package be.chickendinnerinc.school.dbtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class HighScoresFragment extends Fragment implements IScoreListener {


    private OnFragmentInteractionListener mListener;
    private View mView;
    private Database database;
    private ListView listView;

    public HighScoresFragment() {}


    public static HighScoresFragment newInstance(String param1, String param2) {
        HighScoresFragment fragment = new HighScoresFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_high_scores, container, false);
        listView = (ListView)mView.findViewById(R.id.listView);

        SharedPreferences settings = this.getActivity().getSharedPreferences("MyPrefsFile", 0);
        String serverAddress  = settings.getString("serverAddress", "http://localhost:3000/");
        //userId = settings.getInt("currentUserId", 0);

        database = new Database(serverAddress);

        refreshList();
        return mView;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void populate(List<Score> scores) {
        final StableArrayAdapter adapter = new StableArrayAdapter(getContext(), scores);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Score item = (Score) parent.getItemAtPosition(position);

            }
        });
    }

    public void refreshList(){
        database.getAllHigschores(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }


}
