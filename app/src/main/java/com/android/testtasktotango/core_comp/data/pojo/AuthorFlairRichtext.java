
package com.android.testtasktotango.core_comp.data.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorFlairRichtext {

    @SerializedName("a")
    @Expose
    private String a;
    @SerializedName("e")
    @Expose
    private String e;
    @SerializedName("u")
    @Expose
    private String u;
    @SerializedName("t")
    @Expose
    private String t;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

}
