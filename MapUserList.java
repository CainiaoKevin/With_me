package com.example.kefeng.withyou;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
/**
 * Created by kefeng on 7/20/17.
 */

public class MapUserList extends ArrayAdapter<MapUser> {
    private Activity context;
    private List<MapUser> userList;

    public MapUserList(Activity context, List<MapUser> userList) {
        super(context, R.layout.mapuser_fields,userList);
        this.context = context;
        this.userList = userList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.mapuser_fields, null, true);

        TextView btnuserName = (TextView) listViewItem.findViewById(R.id.nametextView);
        TextView textViewEmai = (TextView) listViewItem.findViewById(R.id.emailtextView);
        TextView textViewLatitude = (TextView) listViewItem.findViewById(R.id.latitudetextView);
        TextView textViewLongitude = (TextView) listViewItem.findViewById(R.id.longitudetextView);
        TextView textViewTime = (TextView) listViewItem.findViewById(R.id.timetextView);

        MapUser mapUser = userList.get(position);

        btnuserName.setText(mapUser.getUserName());
        textViewEmai.setText(mapUser.getEmail());

        textViewLatitude.setText(String.valueOf(mapUser.getLatitude()));
        textViewLongitude.setText(String.valueOf(mapUser.getLongitude()));
        textViewTime.setText(mapUser.getCurrentTime());

        return listViewItem;
    }
}
