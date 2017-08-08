package com.sir.app.test.model.bean;

import java.util.List;

/**
 * 新闻城市
 * Created by zhuyinan on 2017/4/6.
 */

public class NewsCityList {

    private List<City> cityList;

    private int ret_code;

    private int totalNum;

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public class City {

        private String areaId;

        private String areaName;

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaId() {
            return this.areaId;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAreaName() {
            return this.areaName;
        }
    }

}
