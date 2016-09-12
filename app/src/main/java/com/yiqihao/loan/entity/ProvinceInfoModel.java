package com.yiqihao.loan.entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 武昌丶鱼 on 2016/5/17.
 * Description:
 */
public class ProvinceInfoModel {
    private String empty  = "";

    private String id= empty;
    private String name= empty;
    private List<CityInfoModel> cityList;
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
    public List<CityInfoModel> getCityList() {
        return cityList;
    }
    public void setCityList(List<CityInfoModel> cityList) {
        this.cityList = cityList;
    }
    public ProvinceInfoModel getProvince(JSONObject json){
        this.id = json.optString("id");
        this.name = json.optString("name");
        this.cityList = new CityInfoModel().getCities(json.optJSONArray("cityList"));
        return this;
    }

    public List<ProvinceInfoModel> getProvinces(JSONArray json){
        List<ProvinceInfoModel> list = new ArrayList<ProvinceInfoModel>();
        for (int i = 0; i < json.length(); i++) {
            list.add(new ProvinceInfoModel().getProvince(json.optJSONObject(i)));
        }
        return list;

    }

    @Override
    public String toString() {
        return "ProvinceInfoModel [id=" + id + ", name=" + name + ", cityList="
                + cityList + "]";
    }

}