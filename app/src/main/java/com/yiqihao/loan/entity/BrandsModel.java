package com.yiqihao.loan.entity;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public class BrandsModel implements Comparable<BrandsModel>{

    private String name;
    private String id;
    private String letter;
    private String series_id;
    private String series_name;
    private String series_group_name;
    private String model_id;
    private String model_name;
    private String maxlen;

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getMaxlen() {
        return maxlen;
    }

    public void setMaxlen(String maxlen) {
        this.maxlen = maxlen;
    }

    public String getSeries_id() {
        return series_id;
    }

    public void setSeries_id(String series_id) {
        this.series_id = series_id;
    }

    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public String getSeries_group_name() {
        return series_group_name;
    }

    public void setSeries_group_name(String series_group_name) {
        this.series_group_name = series_group_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public int compareTo(BrandsModel brandsModel) {
        if (this.getLetter().compareToIgnoreCase(brandsModel.getLetter()) > 0) {
            return 1;
        } else if (this.getLetter().compareToIgnoreCase(brandsModel.getLetter()) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
