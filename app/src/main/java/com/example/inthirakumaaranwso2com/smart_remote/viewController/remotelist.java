package com.example.inthirakumaaranwso2com.smart_remote.viewController;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.inthirakumaaranwso2com.smart_remote.R;
import com.example.inthirakumaaranwso2com.smart_remote.controller.DBremote;
import com.example.inthirakumaaranwso2com.smart_remote.model.derpAdapter;
import com.example.inthirakumaaranwso2com.smart_remote.model.derpdata3;
import com.example.inthirakumaaranwso2com.smart_remote.model.remote;

import java.util.ArrayList;

public class remotelist extends AppCompatActivity implements derpAdapter.ItemClickCallback {
    DBremote DB;
    private RecyclerView recyclerView;
    private derpAdapter adapter;
    private ArrayList listData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remotelist);
        DB = new DBremote(this);
        if(!DB.checkremotename("SONY")) {
            DB.addremote("haha", "lala", "mm", "cha", "ra", "ku");
            DB.addremote("SONY", "lala", "mm", "cha", "ra", "ku");
        }
        recyclerView =(RecyclerView)findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Cursor data = DB.getcontent();
        data.moveToFirst();
        if ( derpdata3.getderpsize() >=0){

            derpdata3.clearderp();
        }
        if (data.getCount()==0){
            displaytoast("No content in dB :(");
        }
        else{
            while (data.moveToNext()){
                derpdata3.addderpID(Integer.parseInt(data.getString(0)));
                derpdata3.addderpname(data.getString(1));
                derpdata3.addderppower(data.getString(2));
                derpdata3.addderpchdwn(data.getString(3));
                derpdata3.addderpchup(data.getString(4));
                derpdata3.addderpvoldwn(data.getString(5));
                derpdata3.addderpvolup(data.getString(6));
            }
            listData = (ArrayList) derpdata3.getListdata();
            adapter =new derpAdapter(derpdata3.getListdata(),this);
            recyclerView.setAdapter(adapter);
            adapter.setItemClickCallback(this);
        }

    }

    @Override
    public void onItemClick(int p) {
        Log.d("myTag", "This is secondry click");
        remote item = (remote) listData.get(p);
        Intent intent = new Intent(this, selectedRemote.class);
        intent.putExtra("reID",item.getReID());
        intent.putExtra("name",item.getName());
        intent.putExtra("power",item.getPower());
        intent.putExtra("chdwn",item.getChadwn());
        intent.putExtra("chup",item.getChaup());
        intent.putExtra("voldwn",item.getVoldwn());
        intent.putExtra("volup",item.getVolUp());
        startActivity(intent);
    }

    @Override
    public void onSecondaryIconClick(int p) {
        Log.d("myTag", "This is secondry click 2");
        remote item = (remote) listData.get(p);
        DB.deleteRemote(item.getReID());
        displaytoastt(item.getName()+" deleted");
        listData.remove(item);
        //pass new data to adapter and update
        adapter.setListData(listData);
        adapter.notifyDataSetChanged();


    }

    public void back(View v){
        finish();
    }

    private void displaytoast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }   //display toast
    private void displaytoastt(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
