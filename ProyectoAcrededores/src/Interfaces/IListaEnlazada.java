/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Models.Nodo;

/**
 *
 * @author Frank
 */
public interface IListaEnlazada<T> {
    void insertar(T dato, int posicion);
    T retirar(int posicion);
    void imprimirLista();
    Nodo<T> buscar(T dato);
    boolean contiene(T dato);
    T obtener(int posicion);
    void modificar(int posicion, T nuevoDato);
    int getTamaño();
}
