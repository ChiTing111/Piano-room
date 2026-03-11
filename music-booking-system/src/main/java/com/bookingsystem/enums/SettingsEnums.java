package com.bookingsystem.enums;

import com.bookingsystem.pojo.BasicSetting;

import java.util.Arrays;
import java.util.List;

public enum SettingsEnums {


    BASE_SETTING("baseSetting",new BasicSetting(),BasicSetting.class),
    BASE_SETTING2("baseSetting2", new BasicSetting(),BasicSetting.class),;

    private String name;
    private Object value;
    private  Class type;

    SettingsEnums(String name, Object value,  Class type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Class getType() {
        return type;
    }
    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public static SettingsEnums getByName(String name) {
        for (SettingsEnums setting : SettingsEnums.values()) {
            if (setting.getName().equals(name)) {
                return setting;
            }
        }
        return null;
    }

    public static SettingsEnums getByValue(Class<?> value) {
        for (SettingsEnums setting : SettingsEnums.values()) {
            if (setting.getValue().equals(value)) {
                return setting;
            }
        }
        return null;
    }


    public static List<SettingsEnums>  getAll(){
        List<SettingsEnums> list = Arrays.asList(SettingsEnums.values());
        return list;
    }


}
