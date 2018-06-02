package chulo.paisa.com.appadministradora.modelo;

/**
 * Created by USUARIO on 11/05/2018.
 */

public class Producto {

    private String nombre;
    private int precio;
    private String foto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}