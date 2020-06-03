package com.jacky.launcher.config;

public enum Game {
    VPSP("VPSP", ".iso", "PSP游戏","com.vrt.vpsp"),
    VSFC("VSFC", ".smc", "SFC游戏","com.androidemu.snes"),
    VGEN("VGEN", ".bin", "MD游戏", "com.androidemu.gens"),
    VGBA("VGBA", ".gba", "GBA游戏","com.moba.gba"),
    VNES("VNES", ".nes", "FC游戏","com.androidemu.nes"),
    VARC("VARC", ".zip", "街机游戏", "com.vrt.varc"),
    VN64("VN64", ".n64", "N64游戏", "com.vrt.vn64");

    private String name;//名字
    private String suffix;//后缀
    private String gameName;
    private String boot;

    private Game(String name, String suffix, String gameName, String boot) {
        this.name = name;
        this.suffix = suffix;
        this.gameName = gameName;
        this.boot = boot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }


    public String getBoot() {
        return boot;
    }

    public void setBoot(String boot) {
        this.boot = boot;
    }
}
