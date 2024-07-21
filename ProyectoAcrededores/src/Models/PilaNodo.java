package Models;

public class PilaNodo<T> {
    private T dato;
    private PilaNodo<T> siguiente;

    public PilaNodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public PilaNodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(PilaNodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
