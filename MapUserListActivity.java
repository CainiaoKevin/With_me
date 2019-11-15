package com.example.kefeng.withyou;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;



public class MapUserListActivity extends AppCompatActivity {
    DatabaseReference dref;
    ListView listView;
    List<MapUser> mapUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_user_list);
        listView = (ListView)findViewById(R.id.listView);

        mapUsers = new ArrayList<>();

        MapUserList userAdapter = new MapUserList(MapUserListActivity.this,mapUsers);

        dref = FirebaseDatabase.getInstance().getReference("users");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapUsers.clear();
                for(DataSnapshot postSnapShot: dataSnapshot.getChildren()){
                    MapUser mapUser = postSnapShot.getValue(MapUser.class);
                    mapUsers.add(mapUser);

                }
                //MapUserList userAdapter = new MapUserList(MapUserListActivity.this,mapUsers);
                //listView.setAdapter(userAdapter);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("MapUserListActivity", String.valueOf(position));

                Intent intent = new Intent(MapUserListActivity.this, ShowLocatioMapsActivity.class);
                String lat = ((TextView)view.findViewById(R.id.latitudetextView)).getText().toString();
                String lon = ((TextView)view.findViewById(R.id.longitudetextView)).getText().toString();
                String[] location = {lat,lon};

                intent.putExtra("location", location);
                startActivity(intent);

            }
        });

        listView.setAdapter(userAdapter);

    }



}
