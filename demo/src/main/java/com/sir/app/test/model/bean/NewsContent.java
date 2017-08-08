package com.sir.app.test.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 新闻内容
 * Created by zhuyinan on 2017/4/7.
 */

public class NewsContent implements Serializable{

    private int ret_code;

    private Pagebean pagebean;

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public int getRet_code() {
        return this.ret_code;
    }

    public void setPagebean(Pagebean pagebean) {
        this.pagebean = pagebean;
    }

    public Pagebean getPagebean() {
        return this.pagebean;
    }

    public class Pagebean implements Serializable{
        private int allPages;

        private List<Contentlist> contentlist;

        private int currentPage;

        private int allNum;

        private int maxResult;

        public void setAllPages(int allPages) {
            this.allPages = allPages;
        }

        public int getAllPages() {
            return this.allPages;
        }

        public void setContentlist(List<Contentlist> contentlist) {
            this.contentlist = contentlist;
        }

        public List<Contentlist> getContentlist() {
            return this.contentlist;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getCurrentPage() {
            return this.currentPage;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public int getAllNum() {
            return this.allNum;
        }

        public void setMaxResult(int maxResult) {
            this.maxResult = maxResult;
        }

        public int getMaxResult() {
            return this.maxResult;
        }

        public class Contentlist implements Serializable{

            private List<Object> allList;

            private String pubDate;

            private boolean havePic;

            private String title;

            private String channelName;

            private List<Imageurls> imageurls;

            private String desc;

            private String source;

            private String channelId;

            private String link;

            public void setString(List<Object> allList) {
                this.allList = allList;
            }

            public List<Object> getString() {
                return this.allList;
            }

            public void setPubDate(String pubDate) {
                this.pubDate = pubDate;
            }

            public String getPubDate() {
                return this.pubDate;
            }

            public void setHavePic(boolean havePic) {
                this.havePic = havePic;
            }

            public boolean getHavePic() {
                return this.havePic;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle() {
                return this.title;
            }

            public void setChannelName(String channelName) {
                this.channelName = channelName;
            }

            public String getChannelName() {
                return this.channelName;
            }

            public void setImageurls(List<Imageurls> imageurls) {
                this.imageurls = imageurls;
            }

            public List<Imageurls> getImageurls() {
                return this.imageurls;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getDesc() {
                return this.desc;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getSource() {
                return this.source;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public String getChannelId() {
                return this.channelId;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getLink() {
                return this.link;
            }

        }

        public class AllList extends Object implements Serializable{
            private int height;

            private int width;

            private String url;

            public void setHeight(int height) {
                this.height = height;
            }

            public int getHeight() {
                return this.height;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getWidth() {
                return this.width;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUrl() {
                return this.url;
            }

        }

        public class Imageurls implements Serializable{

            private int height;
            private int width;
            private String url;

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

    }


}
