/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interfaces.IListaEnlazadaDoble;
import Models.Nodo;

/**
 *
 * @author Frank
 */
public class ImplListaEnlazada<T> implements IListaEnlazadaDoble<T> {

    private Nodo<T> cabeza;
    private Nodo<T> cola;

    public ImplListaEnlazada() {
        this.cabeza = null;
        this.cola = null;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    @Override
    public void insertar(T dato, int posicion) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (posicion < 0) {
            throw new IndexOutOfBoundsException("Posición negativa");
        }

        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else if (posicion == 0) {
            nuevoNodo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevoNodo);
            cabeza = nuevoNodo;
        } else {
            Nodo<T> iterador = cabeza;
            int contador = 0;

            while (iterador != null && contador < posicion - 1) {
                iterador = iterador.getSiguiente();
                contador++;
            }

            if (iterador == null) {
                throw new IndexOutOfBoundsException("Posición fuera de rango");
            }

            nuevoNodo.setSiguiente(iterador.getSiguiente());
            if (iterador.getSiguiente() != null) {
                iterador.getSiguiente().setAnterior(nuevoNodo);
            } else {
                cola = nuevoNodo;
            }
            iterador.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(iterador);
        }
    }

    @Override
    public T retirar(int posicion) {
        if (posicion < 0 || estaVacia()) {
            throw new IndexOutOfBoundsException("Posición negativa o lista vacía");
        }

        T datoRetirado;
        if (posicion == 0) {
            datoRetirado = cabeza.getDato();
            cabeza = cabeza.getSiguiente();
            if (cabeza != null) {
                cabeza.setAnterior(null);
            } else {
                cola = null;
            }
        } else {
            Nodo<T> iterador = cabeza;
            int contador = 0;

            while (iterador != null && contador < posicion - 1) {
                iterador = iterador.getSiguiente();
                contador++;
            }

            if (iterador == null || iterador.getSiguiente() == null) {
                throw new IndexOutOfBoundsException("Posición fuera de rango");
            }

            Nodo<T> nodoARetirar = iterador.getSiguiente();
            datoRetirado = nodoARetirar.getDato();
            iterador.setSiguiente(nodoARetirar.getSiguiente());
            if (nodoARetirar.getSiguiente() != null) {
                nodoARetirar.getSiguiente().setAnterior(iterador);
            } else {
                cola = iterador;
            }
        }

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

}
