package com.truongsinh.projectrtos.ThongTinUser;

public class Den {
    boolean isLight;
    int soTT;
    public static int TT = 1;
    public Den(boolean isLight) {
        this.isLight = isLight;
        this.soTT = TT;
        TT++;
    }
    public boolean isLight() {
        return isLight;
    }
    public void setLight(boolean light) {
        isLight = light;
    }
    public int getSoTT() {
        return soTT;
    }

}
