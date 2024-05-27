/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

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
}
