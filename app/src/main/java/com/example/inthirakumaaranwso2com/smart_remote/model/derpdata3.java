package com.example.inthirakumaaranwso2com.smart_remote.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-PC on 4/23/2017.
 */

public class derpdata3 {



    private static List<Integer> reID= new ArrayList<>();
    private static List<String> name = new ArrayList<>();
    private static List<String>  power = new ArrayList<>();
    private static List<String> chup= new ArrayList<>();
    private static List<String> chdwn= new ArrayList<>();
    private static List<String> volup= new ArrayList<>();
    private static List<String> voldwn= new ArrayList<>();


    public static void clearderp(){
        reID= new ArrayList<>();
        name = new ArrayList<>();
        power=new ArrayList<>();
        chup= new ArrayList<>();
        chdwn= new ArrayList<>();
        volup= new ArrayList<>();
        voldwn= new ArrayList<>();
    }
    public static int getderpsize() {
        return name.size();
    }
    public static void addderpID(Integer priority){
        reID.add(priority);
    }
    public static void addderpname(String s){
        name.add(s);
    }
    public static void addderppower(String s){
        power.add(s);
    }
    public static void addderpchup(String s){
        chup.add(s);
    }
    public static void addderpchdwn(String s){
        chdwn.add(s);
    }
    public static void addderpvoldwn(String s){
        voldwn.add(s);
    }
    public static void addderpvolup(String s){
        volup.add(s);
    }




    public static List<remote> getListdata(){    //remote data
        List<remote> data = new ArrayList<>();
        for(int i=0;i<name.size()  ;i++){
            remote item = new remote();
//                item.setImageid(icons[i]);
//            boolean x =true;
            item.setChadwn(chdwn.get(i));
            item.setChaup(chup.get(i));
            item.setVoldwn(voldwn.get(i));
            item.setVolUp(volup.get(i));
            item.setName(name.get(i));
            item.setPower(power.get(i));
            item.setReID(reID.get(i));
            data.add(item);
        }

        return data;
    }

}
