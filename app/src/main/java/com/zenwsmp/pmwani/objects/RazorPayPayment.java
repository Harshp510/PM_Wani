package com.zenwsmp.pmwani.objects;

public class RazorPayPayment {

    private String title = "";
    private int icon;
    private String walletIcon;
    private String walletName = "";

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public String getWalletIcon() {
        return walletIcon;
    }

    public void setWalletIcon(String walletIcon) {
        this.walletIcon = walletIcon;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    @Override
    public String toString() {
        return "RazorPayPayment{" +
                "title='" + title + '\'' +
                ", icon=" + icon +
                ", walletIcon='" + walletIcon + '\'' +
                '}';
    }
}
