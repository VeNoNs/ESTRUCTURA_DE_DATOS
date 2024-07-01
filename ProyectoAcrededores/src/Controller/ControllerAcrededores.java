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
import java.util.Map;

public class ControllerAcrededores implements IControllerAcrededores {

    private ImplListaEnlazada<Acrededores> listaAcrededores;
    private ControllerCSV controllerCSV;
    private String filePath = "D:\\ACREEDORES.csv";
    private ImplTablaHash<String, Double> datos;

    public ControllerAcrededores() {
        this.listaAcrededores = new ImplListaEnlazada<>();
        datos = new ImplTablaHash<>();
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
        resultados.imprimirLista();
        return resultados;
    }

    // Método para filtrar la lista de resultados
    public ImplListaEnlazada<Acrededores> filtrar(ImplListaEnlazada<Acrededores> lista,
            String valorDepartamento,
            String valorProvincia,
            String valorDistrito,
            String valorPliego,
            String valorEjecutora) {
        ImplListaEnlazada<Acrededores> resultados = new ImplListaEnlazada<>();
        Nodo<Acrededores> actual = lista.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            boolean coincide = true;

            // Ajustar la lógica para ignorar valores "Seleccionar" o null
            if (valorDepartamento != null && !valorDepartamento.equals("Seleccionar")
                    && !acredor.getRemypeDepartamento().equalsIgnoreCase(valorDepartamento)) {
                coincide = false;
            }
            if (valorProvincia != null && !valorProvincia.equals("Seleccionar")
                    && !acredor.getRemypeProvincia().equalsIgnoreCase(valorProvincia)) {
                coincide = false;
            }
            if (valorDistrito != null && !valorDistrito.equals("Seleccionar")
                    && !acredor.getRemypeDistrito().equalsIgnoreCase(valorDistrito)) {
                coincide = false;
            }
            if (valorPliego != null && !valorPliego.equals("Seleccionar")
                    && !acredor.getDescPliego().equalsIgnoreCase(valorPliego)) {
                coincide = false;
            }
            if (valorEjecutora != null && !valorEjecutora.equals("Seleccionar")
                    && !acredor.getDescEjecutora().equalsIgnoreCase(valorEjecutora)) {
                coincide = false;
            }

            if (coincide) {
                resultados.insertar(acredor, resultados.getTamaño());
            }
            actual = actual.getSiguiente();
        }

        return resultados;
    }

    public void exportarACSV(ImplListaEnlazada<Acrededores> lista, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Escribir cabecera
            writer.append("RUC,RAZON_SOCIAL,REMYPE_DEPARTAMENTO,REMYPE_PROVINCIA,REMYPE_DISTRITO,DOMICILIO_FISCAL,APP_INFORMATICO_DEMANDAS,SIAF,ESTADO_DEUDA,TIPO_DOCUMENTO,DOC_DEVEN_O_SENTEN_JUDI,MONTO_DEUDA,DESC_NIVEL_GOBIERNO,DESC_SECTOR,DESC_PLIEGO,SEC_EJEC,DESC_EJECUTORA,OBSERVACION_GLOSA\n");

            // Escribir datos de la lista enlazada
            Nodo<Acrededores> actual = lista.getCabeza();
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

    // Método para exportar la tabla a PDF
    public void exportarAPDF(ImplListaEnlazada<Acrededores> lista, String filePath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            // Escribir cabecera
            document.add(new Paragraph("RUC,RAZON_SOCIAL,REMYPE_DEPARTAMENTO,REMYPE_PROVINCIA,REMYPE_DISTRITO,DOMICILIO_FISCAL,APP_INFORMATICO_DEMANDAS,SIAF,ESTADO_DEUDA,TIPO_DOCUMENTO,DOC_DEVEN_O_SENTEN_JUDI,MONTO_DEUDA,DESC_NIVEL_GOBIERNO,DESC_SECTOR,DESC_PLIEGO,SEC_EJEC,DESC_EJECUTORA,OBSERVACION_GLOSA\n"));

            // Escribir datos de la lista enlazada
            Nodo<Acrededores> actual = lista.getCabeza();
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
        quickSort(departamentos, 0, departamentos.getTamaño() - 1);
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
        quickSort(provincias, 0, provincias.getTamaño() - 1);
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
        quickSort(distritos, 0, distritos.getTamaño() - 1);
        return distritos;
    }

    public ImplListaEnlazada<String> obtenerPliegos(String departamento, String provincia, String distrito) {
        ImplListaEnlazada<String> pliegos = new ImplListaEnlazada<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            String pliego = acredor.getDescPliego();

            boolean matchDepartamento = (departamento == null || departamento.isEmpty() || acredor.getRemypeDepartamento().equals(departamento));
            boolean matchProvincia = (provincia == null || provincia.isEmpty() || acredor.getRemypeProvincia().equals(provincia));
            boolean matchDistrito = (distrito == null || distrito.isEmpty() || acredor.getRemypeDistrito().equals(distrito));

            if (matchDepartamento && matchProvincia && matchDistrito && !pliegos.contiene(pliego)) {
                pliegos.insertar(pliego, pliegos.getTamaño());
            }
            actual = actual.getSiguiente();
        }
        quickSort(pliegos, 0, pliegos.getTamaño() - 1);
        return pliegos;
    }

    public ImplListaEnlazada<String> obtenerEjecutoras(String departamento, String provincia, String distrito) {
        ImplListaEnlazada<String> ejecutoras = new ImplListaEnlazada<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            String ejecutora = acredor.getDescEjecutora();

            boolean matchDepartamento = (departamento == null || departamento.isEmpty() || acredor.getRemypeDepartamento().equals(departamento));
            boolean matchProvincia = (provincia == null || provincia.isEmpty() || acredor.getRemypeProvincia().equals(provincia));
            boolean matchDistrito = (distrito == null || distrito.isEmpty() || acredor.getRemypeDistrito().equals(distrito));

            if (matchDepartamento && matchProvincia && matchDistrito && !ejecutoras.contiene(ejecutora)) {
                ejecutoras.insertar(ejecutora, ejecutoras.getTamaño());
            }
            actual = actual.getSiguiente();
        }

        quickSort(ejecutoras, 0, ejecutoras.getTamaño() - 1);
        return ejecutoras;
    }

    private void quickSort(ImplListaEnlazada<String> lista, int izquierda, int derecha) {
        if (izquierda < derecha) {
            int indiceParticion = particion(lista, izquierda, derecha);
            quickSort(lista, izquierda, indiceParticion - 1);
            quickSort(lista, indiceParticion + 1, derecha);
        }
    }

    private int particion(ImplListaEnlazada<String> lista, int izquierda, int derecha) {
        String pivote = lista.obtener(derecha);
        int i = izquierda - 1;

        for (int j = izquierda; j < derecha; j++) {
            if (lista.obtener(j).compareTo(pivote) < 0) {
                i++;
                String temp = lista.obtener(i);
                lista.modificar(i, lista.obtener(j));
                lista.modificar(j, temp);
            }
        }

        String temp = lista.obtener(i + 1);
        lista.modificar(i + 1, lista.obtener(derecha));
        lista.modificar(derecha, temp);

        return i + 1;
    }
//METODOS PARA LA PARTE DE ESTADISTICAS

    public ImplTablaHash<String, Double> montosAcumuladosPorDepartamentoProvinciaDistrito() {
        ImplTablaHash<String, Double> resultado = new ImplTablaHash<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            String key = acredor.getRemypeDepartamento() + "-" + acredor.getRemypeProvincia() + "-" + acredor.getRemypeDistrito();
            Double montoActual = resultado.get(key);
            if (montoActual == null) {
                resultado.put(key, acredor.getMontoDeuda());
            } else {
                resultado.put(key, montoActual + acredor.getMontoDeuda());
            }
            actual = actual.getSiguiente();
        }
        return resultado;
    }

    public ImplTablaHash<String, Double> totalesAcumuladosPorNivelDeGobiernoDepartamentoYPliego() {
        ImplTablaHash<String, Double> resultado = new ImplTablaHash<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            String key = acredor.getDescNivelGobierno() + "-" + acredor.getRemypeDepartamento() + "-" + acredor.getDescPliego();
            Double montoActual = resultado.get(key);
            if (montoActual == null) {
                resultado.put(key, acredor.getMontoDeuda());
            } else {
                resultado.put(key, montoActual + acredor.getMontoDeuda());
            }
            actual = actual.getSiguiente();
        }
        return resultado;
    }

    public ImplTablaHash<String, Double> cantidadesYTotalesPorDepartamentoYTipoDeDocumento() {
        ImplTablaHash<String, Double> resultado = new ImplTablaHash<>();
        Nodo<Acrededores> actual = listaAcrededores.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            String key = acredor.getRemypeDepartamento() + "-" + acredor.getTipoDocumento();
            Double montoActual = resultado.get(key);
            if (montoActual == null) {
                resultado.put(key, acredor.getMontoDeuda());
            } else {
                resultado.put(key, montoActual + acredor.getMontoDeuda());
            }
            actual = actual.getSiguiente();
        }
        return resultado;
    }

    public ImplTablaHash<String, Double> filtrarPorDepartamento(ImplTablaHash<String, Double> datos, String departamento) {
        ImplTablaHash<String, Double> resultado = new ImplTablaHash<>();

        for (Map.Entry<String, Double> entry : datos.entrySet()) {
            if (entry.getKey().contains(departamento)) {
                resultado.put(entry.getKey(), entry.getValue());
            }
        }

        return resultado;
    }
}
