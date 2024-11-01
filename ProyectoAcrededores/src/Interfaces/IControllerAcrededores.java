package Interfaces;

import Controller.ArbolBinarioImpl;
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

    public ImplListaEnlazada<String> obtenerPliegos(String departamento, String provincia, String distrito);

    public ImplListaEnlazada<String> obtenerEjecutoras(String departamento, String provincia, String distrito);

    public ImplListaEnlazada<String> obtenerDepartamentos();

    public ImplListaEnlazada<String> obtenerProvinciasPorDepartamento(String departamento);

    public ImplListaEnlazada<Acrededores> filtrar(ImplListaEnlazada<Acrededores> listaAcrededoresFiltrada, String valorDepartamento, String valorProvincia, String valorDistrito, String valorPliego, String valorEjecutora);

    public void exportarACSV(ImplListaEnlazada<Acrededores> listaAcrededoresFiltrada, String filePath);

    public void exportarAPDF(ImplListaEnlazada<Acrededores> listaAcrededoresFiltrada, String filePath);
    
    public ArbolBinarioImpl<Acrededores> generarArbolDesdeListaPorRUC(ImplListaEnlazada<Acrededores> lista);
    
    public ImplListaEnlazada<Acrededores> buscarEnArbolPorRUC(ArbolBinarioImpl<Acrededores> arbol, String ruc);
}
