package com.doitandroid.chazayo.util;

import com.doitandroid.chazayo.rest.APIInterface;

public class SingletonHolder {

    APIInterface FamilyAPIInterface;

    private SingletonHolder() {
    }


    private static class Singleton {
        private static final SingletonHolder instance = new SingletonHolder();
    }

    public static SingletonHolder getInstance() {
        System.out.println("create instance");
        return Singleton.instance;
    }


    public APIInterface getFamilyAPIInterface() {
        return FamilyAPIInterface;
    }

    public void setFamilyAPIInterface(APIInterface familyAPIInterface) {
        FamilyAPIInterface = familyAPIInterface;
    }
}