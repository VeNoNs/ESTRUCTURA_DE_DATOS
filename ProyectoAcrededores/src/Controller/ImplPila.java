package Controller;

import Models.PilaNodo;

public class ImplPila<T> {
    private PilaNodo<T> tope;

    public ImplPila() {
        this.tope = null;
    }

    // Método para agregar un elemento a la pila
    public void push(T dato) {
        PilaNodo<T> nuevoNodo = new PilaNodo<>(dato);
        nuevoNodo.setSiguiente(tope);
        tope = nuevoNodo;
    }

    // Método para eliminar y devolver el elemento en el tope de la pila
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía");
        }
        T dato = tope.getDato();
        tope = tope.getSiguiente();
        return dato;
    }

    // Método para devolver el elemento en el tope de la pila sin eliminarlo
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía");
        }
        return tope.getDato();
    }

    // Método para verificar si la pila está vacía
    public boolean isEmpty() {
        return tope == null;
    }

    // Método para buscar un elemento en la pila
    public boolean buscar(T dato) {
        PilaNodo<T> actual = tope;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
}
