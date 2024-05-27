/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interfaces.IControllerAcrededores;
import Models.Acrededores;

/**
 *
 * @author Frank
 */
import Controller.ImplListaEnlazada;
import Models.Acrededores;
import Models.Nodo;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ControllerAcrededores implements IControllerAcrededores {

    private ImplListaEnlazada<Acrededores> listaAcrededores;
    private ControllerCSV controllerCSV;
    private String filePath="D:\\ACREEDORES.csv";
    
    public ControllerAcrededores() {
        this.listaAcrededores = new ImplListaEnlazada<>();
        this.controllerCSV = new ControllerCSV(filePath);
        cargarAcrededores();
    }
    

    @Override
    public void cargarAcrededores() {
     this.listaAcrededores = controllerCSV.leerAcrededores();
    }

    @Override
    public void guardarAcrededores() {
     controllerCSV.escribirAcrededores(listaAcrededores);
    }

    @Override
    public void crearAcredor(Acrededores acredor) {
      listaAcrededores.insertar(acredor, listaAcrededores.getTamaño());
        guardarAcrededores();
    }

    @Override
    public void eliminarAcredor(int posicion) {
        listaAcrededores.retirar(posicion);
        guardarAcrededores();
    }

    @Override
    public void actualizarAcredor(int posicion, Acrededores acredorActualizado) {
       listaAcrededores.retirar(posicion);
        listaAcrededores.insertar(acredorActualizado, posicion);
        guardarAcrededores();
    }

    @Override
    public void imprimirAcrededores() {
        listaAcrededores.imprimirLista();
    }
    
public ImplListaEnlazada<Acrededores> buscar(String campo, String valor) {
        ImplListaEnlazada<Acrededores> resultados = new ImplListaEnlazada<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            boolean coincide = false;

            switch (campo) {
                case "RUC":
                    coincide = acredor.getRuc().startsWith(valor);
                    break;
                case "Razón Social":
                    coincide = acredor.getRazonSocial().startsWith(valor);
                    break;
                case "Pliego":
                    coincide = acredor.getDescPliego().startsWith(valor);
                    break;
            }

            if (coincide) {
                resultados.insertar(acredor, resultados.getTamaño());
            }
            actual = actual.getSiguiente();
        }
        return resultados;
    }


    // Método para filtrar la lista de resultados
    public ImplListaEnlazada<Acrededores> filtrar(ImplListaEnlazada<Acrededores> lista, 
                                                  boolean departamento, boolean provincia, boolean distrito, 
                                                  boolean pliego, boolean ejecutora, 
                                                  String valorDepartamento, String valorProvincia, 
                                                  String valorDistrito, String valorPliego, 
                                                  String valorEjecutora) {
        ImplListaEnlazada<Acrededores> resultados = new ImplListaEnlazada<>();
        Nodo<Acrededores> actual = lista.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            boolean coincide = true;

            if (departamento && !acredor.getRemypeDepartamento().equalsIgnoreCase(valorDepartamento)) {
                coincide = false;
            }
            if (provincia && !acredor.getRemypeProvincia().equalsIgnoreCase(valorProvincia)) {
                coincide = false;
            }
            if (distrito && !acredor.getRemypeDistrito().equalsIgnoreCase(valorDistrito)) {
                coincide = false;
            }
            if (pliego && !acredor.getDescPliego().equalsIgnoreCase(valorPliego)) {
                coincide = false;
            }
            if (ejecutora && !acredor.getDescEjecutora().equalsIgnoreCase(valorEjecutora)) {
                coincide = false;
            }

            if (coincide) {
                resultados.insertar(acredor, resultados.getTamaño());
            }
            actual = actual.getSiguiente();
        }
        return resultados;
    }
 
    // Método para exportar la lista de acreedores a CSV
    public void exportarACSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            Nodo<Acrededores> actual = listaAcrededores.getCabeza();
            while (actual != null) {
                Acrededores acredor = actual.getDato();
                writer.append(acredor.toCSV());
                writer.append("\n");
                actual = actual.getSiguiente();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Método para exportar la lista de acreedores a PDF
    public void exportarAPDF(String filePath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Nodo<Acrededores> actual = listaAcrededores.getCabeza();
            while (actual != null) {
                Acrededores acredor = actual.getDato();
                document.add(new Paragraph(acredor.toPDF()));
                actual = actual.getSiguiente();
            }
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Método para actualizar la tabla según los criterios establecidos
    public ImplListaEnlazada<Acrededores> actualizarTabla(String campoBusqueda, String valorBusqueda,
                                                          boolean departamento, boolean provincia, boolean distrito,
                                                          boolean pliego, boolean ejecutora,
                                                          String valorDepartamento, String valorProvincia,
                                                          String valorDistrito, String valorPliego,
                                                          String valorEjecutora) {
        ImplListaEnlazada<Acrededores> resultadosBusqueda = buscar(campoBusqueda, valorBusqueda);
        return filtrar(resultadosBusqueda, departamento, provincia, distrito, pliego, ejecutora,
                       valorDepartamento, valorProvincia, valorDistrito, valorPliego, valorEjecutora);
    }
    
    public ImplListaEnlazada<String> obtenerDepartamentos() {
        ImplListaEnlazada<String> departamentos = new ImplListaEnlazada<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            String departamento = acredor.getRemypeDepartamento();
            if (!departamentos.contiene(departamento)) {
                departamentos.insertar(departamento, departamentos.getTamaño());
            }
            actual = actual.getSiguiente();
        }

        return departamentos;
    }

    public ImplListaEnlazada<String> obtenerProvinciasPorDepartamento(String departamento) {
        ImplListaEnlazada<String> provincias = new ImplListaEnlazada<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            if (acredor.getRemypeDepartamento().equalsIgnoreCase(departamento)) {
                String provincia = acredor.getRemypeProvincia();
                if (!provincias.contiene(provincia)) {
                    provincias.insertar(provincia, provincias.getTamaño());
                }
            }
            actual = actual.getSiguiente();
        }

        return provincias;
    }

    public ImplListaEnlazada<String> obtenerDistritosPorProvincia(String provincia) {
        ImplListaEnlazada<String> distritos = new ImplListaEnlazada<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            if (acredor.getRemypeProvincia().equalsIgnoreCase(provincia)) {
                String distrito = acredor.getRemypeDistrito();
                if (!distritos.contiene(distrito)) {
                    distritos.insertar(distrito, distritos.getTamaño());
                }
            }
            actual = actual.getSiguiente();
        }

        return distritos;
    }

    public ImplListaEnlazada<String> obtenerPliegos() {
        ImplListaEnlazada<String> pliegos = new ImplListaEnlazada<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            String pliego = acredor.getDescPliego();
            if (!pliegos.contiene(pliego)) {
                pliegos.insertar(pliego, pliegos.getTamaño());
            }
            actual = actual.getSiguiente();
        }

        return pliegos;
    }

    public ImplListaEnlazada<String> obtenerEjecutoras() {
        ImplListaEnlazada<String> ejecutoras = new ImplListaEnlazada<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            String ejecutora = acredor.getDescEjecutora();
            if (!ejecutoras.contiene(ejecutora)) {
                ejecutoras.insertar(ejecutora, ejecutoras.getTamaño());
            }
            actual = actual.getSiguiente();
        }

        return ejecutoras;
    }
}
