package com.zenwsmp.pmwani.model;

public class LanguageBean {

    String languagename,languagecode;

    public LanguageBean() {

    }

    public LanguageBean(String languagename, String languagecode) {
        this.languagename = languagename;
        this.languagecode = languagecode;
    }

    public String getLanguagename() {
        return languagename;
    }

    public void setLanguagename(String languagename) {
        this.languagename = languagename;
    }

    public String getLanguagecode() {
        return languagecode;
    }

    public void setLanguagecode(String languagecode) {
        this.languagecode = languagecode;
    }
}
