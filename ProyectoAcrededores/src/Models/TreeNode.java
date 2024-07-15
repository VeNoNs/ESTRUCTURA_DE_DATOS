/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Models;

/**
 *
 * @author Andre H
 */
public class TreeNode<T> {
    // ATRIBUTOS
    private T valor;
    private TreeNode<T> hojaIzquierda;
    private TreeNode<T> hojaDerecha;

    // CONSTRUCTOR
    public TreeNode(T valor) {
        this.valor = valor;
    }

    // MÉTODOS GETTER & SETTER
    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public TreeNode<T> getHojaIzquierda() {
        return hojaIzquierda;
    }

    public void setHojaIzquierda(TreeNode<T> hojaIzquierda) {
        this.hojaIzquierda = hojaIzquierda;
    }

    public TreeNode<T> getHojaDerecha() {
        return hojaDerecha;
    }

    public void setHojaDerecha(TreeNode<T> hojaDerecha) {
        this.hojaDerecha = hojaDerecha;
    }
}

