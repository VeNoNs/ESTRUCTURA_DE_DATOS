/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interfaces.IAccesoSistema;
import Models.Usuario;
import Controller.ImplListaEnlazada;
import Models.Nodo;

public class ServiciosAcceso implements IAccesoSistema {

    private ImplListaEnlazada<Usuario> listaUsuarios;
    private ControllerUsuariosCSV controllerUsuariosCSV;
    private final String filePath = "D:\\USUARIOS.csv";

    public ServiciosAcceso() {
        this.controllerUsuariosCSV = new ControllerUsuariosCSV(filePath);
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        this.listaUsuarios = controllerUsuariosCSV.leerUsuarios();
    }

    private void guardarUsuarios() {
        controllerUsuariosCSV.escribirUsuarios(listaUsuarios);
    }

    @Override
    public Usuario autenticar(String username, String password) {
        Nodo<Usuario> actual = listaUsuarios.getCabeza();
        while (actual != null) {
            Usuario usuario = actual.getDato();
            if (usuario.getNombreUsuario().equals(username) && usuario.getContraseña().equals(password)) {
                return usuario;
            }
            actual = actual.getSiguiente();
        }
        return null;  // Usuario no encontrado o credenciales incorrectas
    }

    @Override
    public void registrarNuevoUsuario(Usuario usuario) {
        Nodo<Usuario> actual = listaUsuarios.getCabeza();
        while (actual != null) {
            Usuario existingUser = actual.getDato();
            if (existingUser.getNombreUsuario().equals(usuario.getNombreUsuario())) {
                throw new IllegalArgumentException("El nombre de usuario ya existe.");
            }
            actual = actual.getSiguiente();
        }
        listaUsuarios.insertar(usuario, listaUsuarios.getTamaño());
        guardarUsuarios();
    }

    @Override
    public void cambiarContraseña(Usuario usuario, String nuevaContraseña) {
        Nodo<Usuario> actual = listaUsuarios.getCabeza();
        while (actual != null) {
            Usuario existingUser = actual.getDato();
            if (existingUser.getNombreUsuario().equals(usuario.getNombreUsuario())) {
                existingUser.setContraseña(nuevaContraseña);
                guardarUsuarios();
                return;
            }
            actual = actual.getSiguiente();
        }
        throw new IllegalArgumentException("Usuario no encontrado.");
    }

    @Override
    public void cerrarSesion(Usuario usuario) {
        // Este método puede gestionarse de diversas formas dependiendo de cómo
        // se maneje la sesión en la aplicación. Aquí, simplemente lo dejamos como
        // una operación que podría registrar el cierre de sesión o similar.
        System.out.println("Sesión cerrada para el usuario: " + usuario.getNombreUsuario());
    }
}
