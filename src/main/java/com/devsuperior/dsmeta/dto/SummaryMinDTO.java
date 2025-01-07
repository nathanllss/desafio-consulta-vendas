package com.devsuperior.dsmeta.dto;

public class SummaryMinDTO {

    private String sellerName;
    private Double total;

    public SummaryMinDTO() {
    }

    public SummaryMinDTO(String name, Double total) {
        this.sellerName = name;
        this.total = total;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String name) {
        this.sellerName = name;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
