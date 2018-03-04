package com.citywindowpro.Model;

/**
 * Created by raju on 24-08-2017.
 */

public class LiveOfferModel {
    String product_image;

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    String pro_name;

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getPro_price() {
        return pro_price;
    }

    public void setPro_price(String pro_price) {
        this.pro_price = pro_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOff_price() {
        return off_price;
    }

    public void setOff_price(String off_price) {
        this.off_price = off_price;
    }

    public String getLv_id() {
        return lv_id;
    }

    public void setLv_id(String lv_id) {
        this.lv_id = lv_id;
    }

    String pro_price;
    String description;
    String off_price;
    String lv_id;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getL_id() {
        return l_id;
    }

    public void setL_id(String l_id) {
        this.l_id = l_id;
    }

    String cat_id;
    String l_id;

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    String p_id;

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    String sub_id;
    String child_id;
}
