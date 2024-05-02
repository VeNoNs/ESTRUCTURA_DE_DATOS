/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Models.Usuario;

/**
 *
 * @author Frank
 */
public interface IAccesoSistema {

    Usuario autenticar(String username, String password);

    void registrarNuevoUsuario(Usuario usuario);

    void cambiarContraseña(Usuario usuario, String nuevaContraseña);

    void cerrarSesion(Usuario usuario);
}
