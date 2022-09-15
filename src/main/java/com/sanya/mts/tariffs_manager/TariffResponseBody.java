package com.sanya.mts.tariffs_manager;

import com.sanya.mts.tariffs_manager.entities.Equipment;
import com.sanya.mts.tariffs_manager.entities.Extra;
import com.sanya.mts.tariffs_manager.entities.Service;

public class TariffResponseBody {

    private final Integer id;

    private final String name;
    private final String[] tags;
    private final String type;
    private final int price;
    private final String Short;

    private final Service[] services;
    private final Equipment[] equip;
    private final Extra[] extra;

    private TariffResponseBody(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.tags = builder.tags;
        this.type = builder.type;
        this.price = builder.price;
        this.services = builder.services;
        this.equip = builder.equip;
        this.extra = builder.extra;
        this.Short = builder.Short;
    }

    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getShort() {
        return Short;
    }

    public String[] getTags() {
        return tags;
    }


    public String getType() {
        return type;
    }


    public int getPrice() {
        return price;
    }


    public Service[] getServices() {
        return services;
    }


    public Equipment[] getEquip() {
        return equip;
    }


    public Extra[] getExtra() {
        return extra;
    }


    public static class Builder{
        private final Integer id;

        private final String name;
        private final String[] tags;
        private final String type;
        private final int price;

        private final String Short;


        private Service[] services;
        private Equipment[] equip;
        private Extra[] extra;


        public Builder(Integer id, String name, String[] tags, String type, int price, String Short){
            this.id = id;
            this.name = name;
            this.tags = tags;
            this.type = type;
            this.price = price;
            this.Short = Short;
        }

        public Builder services(Service[] services){
            this.services = services;
            return this;
        }

        public Builder equip(Equipment[] equip){
            this.equip = equip;
            return this;
        }

        public Builder extra(Extra[] extra){
            this.extra = extra;
            return this;
        }

        public TariffResponseBody build(){
            return new TariffResponseBody(this);
        }
    }

}
