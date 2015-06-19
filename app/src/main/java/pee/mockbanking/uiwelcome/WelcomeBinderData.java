package pee.mockbanking.uiwelcome;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import pee.mockbanking.R;

/**
 * Created by pvu_asus on 18/06/2015.
 */
public class WelcomeBinderData extends BaseAdapter {
    LayoutInflater inflater;

    List<String> titleCollection;
    ViewHolder holder;


    public WelcomeBinderData(Activity act, List<String> titleCollection) {

        this.titleCollection = titleCollection;

        inflater = (LayoutInflater) act
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCount() {
        // TODO Auto-generated method stub
//		return idlist.size();
        return titleCollection.size();
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

            vi = inflater.inflate(R.layout.welcome_list_row, null);
            holder = new ViewHolder();

            holder.tvTitle = (TextView)vi.findViewById(R.id.tvTitle); // city name



            vi.setTag(holder);
        }
        else{

            holder = (ViewHolder)vi.getTag();
        }

        // Setting all values in listview
        String title = titleCollection.get(position);
        holder.tvTitle.setText(title);
        return vi;
    }

    /*
     *
     * */
    static class ViewHolder{

        TextView tvTitle;
    }
}
