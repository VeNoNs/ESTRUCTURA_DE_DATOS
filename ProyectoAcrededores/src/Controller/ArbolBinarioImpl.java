/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interfaces.ArbolBinarioInterface;
import Models.TreeNode;

/**
 *
 * @author Andre H
 */
public class ArbolBinarioImpl<T extends Comparable<T>> implements ArbolBinarioInterface<T> {
    private TreeNode<T> raiz;

    public ArbolBinarioImpl(T valor) {
        this.raiz = new TreeNode<>(valor);
    }

    public ArbolBinarioImpl(TreeNode<T> raiz) {
        this.raiz = raiz;
    }

    @Override
    public TreeNode<T> getRaiz() {
        return raiz;
    }

    @Override
    public void setRaiz(TreeNode<T> raiz) {
        this.raiz = raiz;
    }

    @Override
    public void preOrden(TreeNode<T> raiz) {
        if (raiz != null) {
            System.out.print(raiz.getValor().toString() + " => ");
            preOrden(raiz.getHojaIzquierda());
            preOrden(raiz.getHojaDerecha());
        }
    }

    @Override
    public void inOrden(TreeNode<T> raiz) {
        if (raiz != null) {
            inOrden(raiz.getHojaIzquierda());
            System.out.print(raiz.getValor().toString() + " => ");
            inOrden(raiz.getHojaDerecha());
        }
    }

    @Override
    public void postOrden(TreeNode<T> raiz) {
        if (raiz != null) {
            postOrden(raiz.getHojaIzquierda());
            postOrden(raiz.getHojaDerecha());
            System.out.print(raiz.getValor().toString() + " => ");
        }
    }

    @Override
    public void insertar(TreeNode<T> nodo) {
        insertarNodo(nodo, this.raiz);
    }

    private void insertarNodo(TreeNode<T> nodo, TreeNode<T> raiz) {
        if (raiz == null) {
            this.setRaiz(nodo);
        } else {
            if (nodo.getValor().compareTo(raiz.getValor()) <= 0) {
                if (raiz.getHojaIzquierda() == null) {
                    raiz.setHojaIzquierda(nodo);
                } else {
                    insertarNodo(nodo, raiz.getHojaIzquierda());
                }
            } else {
                if (raiz.getHojaDerecha() == null) {
                    raiz.setHojaDerecha(nodo);
                } else {
                    insertarNodo(nodo, raiz.getHojaDerecha());
                }
            }
        }
    }

    @Override
    public TreeNode<T> buscar(T valorBuscado) {
        return buscarNodo(this.raiz, valorBuscado);
    }

    private TreeNode<T> buscarNodo(TreeNode<T> raizSub, T buscado) {
        if (raizSub == null) {
            return null;
        } else {
            int comparacion = buscado.compareTo(raizSub.getValor());
            if (comparacion == 0) {
                return raizSub;
            } else if (comparacion < 0) {
                return buscarNodo(raizSub.getHojaIzquierda(), buscado);
            } else {
                return buscarNodo(raizSub.getHojaDerecha(), buscado);
            }
        }
    }
}
