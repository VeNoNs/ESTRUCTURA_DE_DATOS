/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;
import Models.TreeNode;
/**
 *
 * @author Andre H
 */
public interface ArbolBinarioInterface<T extends Comparable<T>> {
    TreeNode<T> getRaiz();
    void setRaiz(TreeNode<T> raiz);
    void preOrden(TreeNode<T> raiz);
    void inOrden(TreeNode<T> raiz);
    void postOrden(TreeNode<T> raiz);
    void insertar(TreeNode<T> nodo);
    TreeNode<T> buscar(T valorBuscado);
}
