/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Controller.ImplListaEnlazada;
import Models.Acrededores;

/**
 *
 * @author Frank
 */
public interface IControllerAcrededores {
    
    public void cargarAcrededores();
    
    public void guardarAcrededores();
    
    public void crearAcredor(Acrededores acredor);
    
    public void eliminarAcredor(int posicion);
    
    public void actualizarAcredor(int posicion, Acrededores acredorActualizado) ;
    
    public void imprimirAcrededores();
    
    public ImplListaEnlazada<Acrededores> buscar(String campo, String valor) ;

    public ImplListaEnlazada<String> obtenerDistritosPorProvincia(String provincia);

    public ImplListaEnlazada<String> obtenerPliegos();

    public ImplListaEnlazada<String> obtenerEjecutoras();

    public ImplListaEnlazada<String> obtenerDepartamentos();

    public ImplListaEnlazada<String> obtenerProvinciasPorDepartamento(String departamento);
}
