package com.sir.app.test.mvvm.model.bean;

/**
 * Created by zhuyinan on 2019/6/25.
 */
public class MovieData {

    private String tvTitle;

    private String iconaddress;

    private String iconlinkUrl;

    private String m_iconlinkUrl;

    private String subHead;

    private String grade;

    private String gradeNum;

    public String getTvTitle() {
        return tvTitle == null ? "" : tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getIconaddress() {
        return iconaddress == null ? "" : iconaddress;
    }

    public void setIconaddress(String iconaddress) {
        this.iconaddress = iconaddress;
    }

    public String getIconlinkUrl() {
        return iconlinkUrl == null ? "" : iconlinkUrl;
    }

    public void setIconlinkUrl(String iconlinkUrl) {
        this.iconlinkUrl = iconlinkUrl;
    }

    public String getM_iconlinkUrl() {
        return m_iconlinkUrl == null ? "" : m_iconlinkUrl;
    }

    public void setM_iconlinkUrl(String m_iconlinkUrl) {
        this.m_iconlinkUrl = m_iconlinkUrl;
    }

    public String getSubHead() {
        return subHead == null ? "" : subHead;
    }

    public void setSubHead(String subHead) {
        this.subHead = subHead;
    }

    public String getGrade() {
        return grade == null ? "" : grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeNum() {
        return gradeNum == null ? "" : gradeNum;
    }

    public void setGradeNum(String gradeNum) {
        this.gradeNum = gradeNum;
    }
}
