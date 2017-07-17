package com.example.inthirakumaaranwso2com.smart_remote.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.inthirakumaaranwso2com.smart_remote.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-PC on 4/23/2017.
 */

public class derpAdapter extends RecyclerView.Adapter<derpAdapter.DerpHolder>{


        private List<remote> listData;
        private LayoutInflater inflater;
//       private List<String> summa = new ArrayList<>();

        private ItemClickCallback itemClickCallback;

        public interface ItemClickCallback {
            void onItemClick(int p);
            void onSecondaryIconClick(int p);
        }

        public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
            this.itemClickCallback = itemClickCallback;
        }

        public derpAdapter(List<remote> listData, Context c){
            inflater = LayoutInflater.from(c);
            this.listData = listData;
//            this.setListData(listData);
            Log.d("myTag", "This is msg");
        }



//        public void toggleSelection(int pos) {
//            listItem item = listData.get(pos);
//
//            if (!item.isFavourite()&& countt.check(item)) {
//                countt.remove(item);
//                countt.add(item);
//                Log.d("myTag", "ada adapter poda");
//            }
//            else if(!countt.check(item)&&item.isFavourite()) {
//                countt.add(item);
//            }
//        }

//        public List<listItem> summareturn(){
//            return countt.give();
//        }
//
//        public int getsummasize(){
//            return countt.sizz();
//        }


        @Override
        public derpAdapter.DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.recycler_remote, parent, false);
            return new DerpHolder(view);
        }

        @Override
        public void onBindViewHolder(DerpHolder holder, int position) {
            remote item = listData.get(position);
            holder.title.setText(item.getName());



//            if (item.isFavourite()){
//                holder.secondaryIcon.setImageResource(R.drawable.ic_shortcut_check_box);
////                countt.add(item);
//            } else {
//                holder.secondaryIcon.setImageResource(R.drawable.ic_shortcut_check_box_outline_blank);
////                if(countt.check(item)){
////                    countt.remove(item);
////                }
//            }
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }

        public void setListData(ArrayList<remote> exerciseList) {
            this.listData.clear();
            this.listData.addAll(exerciseList);
        }


    class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


            ImageView secondaryIcon;
            TextView title;
            View container;


            public DerpHolder(View itemView) {
                super(itemView);
                secondaryIcon = (ImageView)itemView.findViewById(R.id.im_item_icon_secondary);
                secondaryIcon.setOnClickListener(this);
                title = (TextView)itemView.findViewById(R.id.lbl_item_text);
                container = (View)itemView.findViewById(R.id.cont_item_root);
                container.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.cont_item_root){
                    itemClickCallback.onItemClick(getAdapterPosition());

                } else {
//                    itemClickCallback.onSecondaryIconClick(getAdapterPosition());
                    itemClickCallback.onSecondaryIconClick(getAdapterPosition());
//                    Log.d("myTag", "This is adapter check 2");
                }
            }
        }

    }
