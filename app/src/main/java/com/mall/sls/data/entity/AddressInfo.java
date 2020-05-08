package com.mall.sls.data.entity;

/**
 * @author jwc on 2020/5/8.
 * 描述：
 */
public class AddressInfo {
    private String name;
    private String phone;
    private String address;
    private String status;
    private String label;

    public AddressInfo(String name, String phone, String address, String status, String label) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
