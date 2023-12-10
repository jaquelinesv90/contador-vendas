package br.edu.utfpr;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Sale {

    @CsvBindByName(column = "num_venda")
    private String number;
    @CsvCustomBindByName(column = "data_venda", converter = LocalDateConverter.class)
    private LocalDate saleDate;
    @CsvCustomBindByName(column = "data_entrega", converter = LocalDateConverter.class)
    private LocalDate deliveryDate;
    @CsvBindByName(column = "regiao")
    private String region;
    @CsvBindByName(column = "uf")
    private String estate;
    @CsvBindByName(column = "gestor")
    private String manager;
    @CsvBindByName(column = "vendedor")
    private String seller;
    @CsvBindByName(column = "departamento")
    private String department;
    @CsvBindByName(column = "forma_pagamento")
    private String paymentMethod;
    @CsvBindByName(column = "valor_venda")
    private BigDecimal value;
    @CsvCustomBindByName(column = "status", converter = StatusConverter.class)
    private Status status;

    public enum Status {

        COMPLETED, CANCELLED;

        public static Status statusByValue(String value) {
            return switch (value) {
                case "Concluída" -> COMPLETED;
                case "Cancelada" -> CANCELLED;
                default -> throw new IllegalArgumentException("Can't parse " + value);
            };
        }
    }

    public boolean isCompleted() {
        return this.status == Status.COMPLETED;
    }

    public boolean isCancelled() {
        return this.status == Status.CANCELLED;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "number='" + number + '\'' +
                ", saleDate=" + saleDate +
                ", deliveryDate=" + deliveryDate +
                ", region='" + region + '\'' +
                ", estate='" + estate + '\'' +
                ", manager='" + manager + '\'' +
                ", seller='" + seller + '\'' +
                ", department='" + department + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", value=" + value +
                ", status=" + status +
                '}';
    }
}
