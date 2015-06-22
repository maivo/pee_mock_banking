package pee.mockbanking.uimain;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pee.mockbanking.R;
import pee.mockbanking.mb.Account;
import pee.mockbanking.ui.AppSession;
import pee.mockbanking.uiaccountsummary.AccountHistoryActivity;
import pee.mockbanking.uiaccountsummary.AccountSummaryBinderData;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    ListView lvWeatherDataCollection;
    AccountSummaryBinderData adapter = null;
    List<Account> accountCollection;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "inside onCreate");

        accountCollection = new ArrayList<Account>();


        Account account = null;
        for (int i = 0; i < 100; i++) {


            account = new Account();
            account.setDesc("Associate Chequing Account 780-***6290-"+i);
            account.setAvailBalFormatted("$18.0" + i + " CAD");
            account.setBalanceFormatted("$.19.0"+i + " CAD");
            account.setStatus("good");
            //Add to the Arraylist
            accountCollection.add(account);

        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "inside onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


       HomeBinderData bindingData = new HomeBinderData(inflater, accountCollection);


        lvWeatherDataCollection = (ListView) rootView.findViewById(R.id.list);

        Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

        lvWeatherDataCollection.setAdapter(bindingData);

        Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

        // Click event for single list row
        lvWeatherDataCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.i(TAG, "inside onItemClick");
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
