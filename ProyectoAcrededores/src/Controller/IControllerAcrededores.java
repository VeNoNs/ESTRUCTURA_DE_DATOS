/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controller;

import Models.Acrededores;

/**
 *
 * @author Frank
 */
public interface IControllerAcrededores {

    void agregarAcreedor(Acrededores acreedor);

    void editarAcreedor(Acrededores acreedor);

    Acrededores buscarAcreedor(long ruc);

    void eliminarAcreedor(long ruc);
}
