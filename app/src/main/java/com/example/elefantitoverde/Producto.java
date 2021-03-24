package com.example.elefantitoverde;

import java.io.Serializable;

public class Producto implements Serializable {
    Integer id;
    String nombre;
    Integer cantidad;
    double precio;
    double precioConIva;
    double precioDolar;
    Integer idCat;

    public Producto() {
    }

    public Producto(Integer id, String nombre, Integer cantidad, double precio, double precioConIva, double precioDolar, Integer idCat) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.precioConIva = precioConIva;
        this.precioDolar = precioDolar;
        this.idCat = idCat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecioConIva() {
        return precioConIva;
    }

    public void setPrecioConIva(double precioConIva) {
        this.precioConIva = precioConIva;
    }

    public double getPrecioDolar() {
        return precioDolar;
    }

    public void setPrecioDolar(double precioDolar) {
        this.precioDolar = precioDolar;
    }

    public Integer getIdCat() {
        return idCat;
    }

    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }
}
