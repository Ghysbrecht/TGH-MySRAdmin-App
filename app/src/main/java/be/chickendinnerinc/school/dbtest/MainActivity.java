package be.chickendinnerinc.school.dbtest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private HighScoresFragment highScoresFragment = new HighScoresFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();
    private UserFragment userFragment = new UserFragment();
    private NfcAdapter  nfcAdapter;

    private android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.contentFrame, highScoresFragment ).commit();
                    return true;
                case R.id.navigation_dashboard:
                    transaction.replace(R.id.contentFrame, userFragment ).commit();
                    return true;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.contentFrame, settingsFragment ).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("SRADM", "Activity Started");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter != null && nfcAdapter.isEnabled()){
            Toast.makeText(this, "NFC is available!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "No NFC, or not enabled!", Toast.LENGTH_SHORT).show();
        }


        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contentFrame, highScoresFragment).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.e("SRADM", "Intent received! -> " + intent.getAction());
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String uid = bytesToHexString(tag.getId()).toUpperCase();
            Log.e("SRADM", "tag ID = " + uid);
            userFragment.uidRfidHandler(uid);
        }
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);

        super.onResume();
    }

    @Override
    protected void onPause() {
        nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }

        return stringBuilder.toString();
    }
}
