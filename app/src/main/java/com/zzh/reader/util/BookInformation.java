package com.zzh.reader.util;

/**
 * Created by Administrator on 2015/12/17.
 */
public class BookInformation {
    int id,now_page,over_flag;
    String full_path,file_name;

    public void setId(int id){
        this.id=id;
    }

    public void setNow_page(int now_page){
        this.now_page=now_page;
    }

    public void setOver_flag(int over_flag){
        this.over_flag=over_flag;
    }

    public void setFull_path(String full_path){
        this.full_path=full_path;
    }

    public void setFile_name(String file_name){
        this.file_name=file_name;
    }


    public int getId(){
        return this.id;
    }

    public int  getNow_page(){
        return this.now_page;
    }

    public int getOver_flag(){
        return this.over_flag;
    }

    public String getFull_path(){
        return this.full_path;
    }

    public String   getFile_name(){
        return this.file_name;
    }
}
