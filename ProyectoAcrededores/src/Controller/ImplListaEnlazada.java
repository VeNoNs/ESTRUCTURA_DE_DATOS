/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Models.Nodo;
import Interfaces.IListaEnlazada;

/**
 *
 * @author Frank
 */
public class ImplListaEnlazada<T> implements IListaEnlazada<T> {

  private Nodo<T> cabeza;
    private int tamaño;

    public ImplListaEnlazada() {
        this.cabeza = null;
        this.tamaño = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    @Override
    public void insertar(T dato, int posicion) {
        if (posicion < 0 || posicion > tamaño) {
            throw new IndexOutOfBoundsException("Posición fuera de rango");
        }

        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (posicion == 0) {
            nuevoNodo.setSiguiente(cabeza);
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            for (int i = 0; i < posicion - 1; i++) {
                actual = actual.getSiguiente();
            }
            nuevoNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevoNodo);
        }
        tamaño++;
    }

    @Override
    public T retirar(int posicion) {
        if (posicion < 0 || posicion >= tamaño) {
            throw new IndexOutOfBoundsException("Posición fuera de rango");
        }

        T datoRetirado;
        if (posicion == 0) {
            datoRetirado = cabeza.getDato();
            cabeza = cabeza.getSiguiente();
        } else {
            Nodo<T> actual = cabeza;
            for (int i = 0; i < posicion - 1; i++) {
                actual = actual.getSiguiente();
            }
            Nodo<T> nodoARetirar = actual.getSiguiente();
            datoRetirado = nodoARetirar.getDato();
            actual.setSiguiente(nodoARetirar.getSiguiente());
        }
        tamaño--;
        return datoRetirado;
    }

    @Override
    public void imprimirLista() {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            System.out.print(actual.getDato() + " ");
            actual = actual.getSiguiente();
        }
        System.out.println();
    }

    @Override
    public Nodo<T> buscar(T dato) {
        Nodo<T> iterador = cabeza;
        while (iterador != null) {
            if (iterador.getDato().equals(dato)) {
                return iterador;
            }
            iterador = iterador.getSiguiente();
        }
        return null;
    }
    
    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public int getTamaño() {
        return tamaño;
    }

    public Nodo<T> getCabeza() {
        return cabeza;
    }

    public void setCabeza(Nodo<T> cabeza) {
        this.cabeza = cabeza;
    }
    
    
}


