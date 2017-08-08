package com.sir.app.test.model.bean;


import java.io.Serializable;
import java.util.List;

/**
 * 新闻频道列表
 * Created by zhuyinan on 2017/4/5.
 */

public class NewsChannelList {

    private int totalNum;

    private int ret_code;

    private List<Channel> channelList;

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalNum() {
        return this.totalNum;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public int getRet_code() {
        return this.ret_code;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }

    public List<Channel> getChannelList() {
        return this.channelList;
    }


    public class Channel implements Serializable{

        private String channelId;

        private String name;

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelId() {
            return this.channelId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }


}
