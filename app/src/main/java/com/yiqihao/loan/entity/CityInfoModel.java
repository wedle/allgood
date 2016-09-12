package com.yiqihao.loan.entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 武昌丶鱼 on 2016/5/17.
 * Description:
 */
public class CityInfoModel {
    private String empty = "";

    private String id= empty;
    private String name= empty;
    private String provinceId= empty;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProvinceId() {
        return provinceId;
    }
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public CityInfoModel getCity(JSONObject json){
        this.id = json.optString("id");
        this.provinceId = json.optString("provinceId");
        this.name = json.optString("name");
        return this;
    }

    public List<CityInfoModel> getCities(JSONArray json){
        List<CityInfoModel> list = new ArrayList<CityInfoModel>();
        for (int i = 0; i < json.length(); i++) {
            list.add(new CityInfoModel().getCity(json.optJSONObject(i)));
        }
        return list;

    }

    @Override
    public String toString() {
        return "CityInfoModel [id=" + id + ", name=" + name + ", provinceId="
                + provinceId + "]";
    }


}