package pee.mockbanking.uimain;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pee.mockbanking.R;
import pee.mockbanking.mb.Account;

public class HomeBinderData extends BaseAdapter {



    LayoutInflater inflater;

    List<Account> accountCollection;
    ViewHolder holder;
    public HomeBinderData() {
        // TODO Auto-generated constructor stub
    }

    public HomeBinderData(LayoutInflater inflater, List<Account> accountCollection) {

        this.accountCollection = accountCollection;

        this.inflater = inflater;
    }


    public int getCount() {
        // TODO Auto-generated method stub
//		return idlist.size();
        return accountCollection.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        if(convertView==null){

            vi = inflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();

            holder.tvTitle = (TextView)vi.findViewById(R.id.tvTitle); // city name
            holder.tvBalance = (TextView)vi.findViewById(R.id.tvBalance); // city weather overview
            holder.tvAvailableBalance =  (TextView)vi.findViewById(R.id.tvAvailableBalance); // city temperature


            vi.setTag(holder);
        }
        else{

            holder = (ViewHolder)vi.getTag();
        }

        // Setting all values in listview
        Account account = accountCollection.get(position);
        holder.tvTitle.setText(account.getDesc());
        holder.tvBalance.setText("Balance: " + account.getBalanceFormatted());
        holder.tvAvailableBalance.setText("Available Balance: " + account.getAvailBalFormatted());



        return vi;
    }

    /*
     *
     * */
    static class ViewHolder{

        TextView tvTitle;
        TextView tvBalance;
        TextView tvAvailableBalance;

    }

}
