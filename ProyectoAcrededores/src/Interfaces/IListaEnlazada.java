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
    public void insertar(T dato, int posicion);
    public T retirar(int posicion);
    public Nodo<T> buscar(T dato);
    public void imprimirLista();
}
