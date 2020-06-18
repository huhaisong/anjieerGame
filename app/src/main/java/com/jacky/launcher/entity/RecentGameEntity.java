package com.jacky.launcher.entity;

import com.jacky.launcher.config.Game;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity()
public class RecentGameEntity {
    @Id(autoincrement = true)
    private Long id;

    private String name;

    private String chName;

    private String pinyin;

    private int visiable;

    private String type;

    @Generated(hash = 926392196)
    public RecentGameEntity(Long id, String name, String chName, String pinyin,
            int visiable, String type) {
        this.id = id;
        this.name = name;
        this.chName = chName;
        this.pinyin = pinyin;
        this.visiable = visiable;
        this.type = type;
    }

    @Generated(hash = 1044099213)
    public RecentGameEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChName() {
        return this.chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int getVisiable() {
        return this.visiable;
    }

    public void setVisiable(int visiable) {
        this.visiable = visiable;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Game getGame() {
        if (getType().equals(Game.VARC.getName())) {
            return Game.VARC;
        } else if (getType().equals(Game.VN64.getName())) {
            return Game.VN64;
        } else if (getType().equals(Game.VGBA.getName())) {
            return Game.VGBA;
        } else if (getType().equals(Game.VGEN.getName())) {
            return Game.VGEN;
        } else if (getType().equals(Game.VNES.getName())) {
            return Game.VNES;
        } else if (getType().equals(Game.VSFC.getName())) {
            return Game.VSFC;
        } else if (getType().equals(Game.VPSP.getName())) {
            return Game.VPSP;
        }
        return null;
    }

    @Override
    public String toString() {
        return "GameEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chName='" + chName + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", visiable=" + visiable +
                ", type='" + type + '\'' +
                '}';
    }
}
