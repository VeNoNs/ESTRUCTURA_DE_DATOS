/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Frank
 */
import Controller.ImplListaEnlazada;
import Models.Acrededores;
import Models.Nodo;

import java.io.*;

public class ControllerCSV {

    private final String filePath;

    public ControllerCSV(String filePath) {
        this.filePath = filePath;
    }

    public ImplListaEnlazada<Acrededores> leerAcrededores() {
    ImplListaEnlazada<Acrededores> acrededoresList = new ImplListaEnlazada<>();
    File file = new File(filePath);

    if (!file.exists()) {
        System.err.println("El archivo no se encuentra: " + filePath);
        try {
            if (file.createNewFile()) {
                System.out.println("Archivo creado: " + filePath);
            } else {
                System.err.println("No se pudo crear el archivo: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return acrededoresList;
        }
    }

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        // Leer y descartar la cabecera
        if ((line = br.readLine()) != null) {
            System.out.println("Cabecera: " + line); // Opcional, para confirmar que la cabecera se leyó y descartó
        }
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length < 18) {
                System.err.println("Línea con datos insuficientes: " + line);
                continue;  // Saltar esta línea y continuar con la siguiente
            }
            try {
                Acrededores acredor = new Acrededores(
                        values[0], values[1], values[2], values[3], values[4],
                        values[5], values[6], values[7], values[8], values[9],
                        values[10], Double.parseDouble(values[11]), values[12],
                        values[13], values[14], Long.parseLong(values[15]), values[16],
                        values[17]
                );
                acrededoresList.insertar(acredor, acrededoresList.getTamaño());
            } catch (NumberFormatException e) {
                System.err.println("Error de formato numérico en la línea: " + line);
                e.printStackTrace();
                continue; // Saltar esta línea y continuar con la siguiente
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return acrededoresList;
}

    public void escribirAcrededores(ImplListaEnlazada<Acrededores> acrededoresList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Escribir cabecera
            bw.write("RUC,RAZON_SOCIAL,REMYPE_DEPARTAMENTO,REMYPE_PROVINCIA,REMYPE_DISTRITO,DOMICILIO_FISCAL,APP_INFORMATICO_DEMANDAS,SIAF,ESTADO_DEUDA,TIPO_DOCUMENTO,DOC_DEVEN_O_SENTEN_JUDI,MONTO_DEUDA,DESC_NIVEL_GOBIERNO,DESC_SECTOR,DESC_PLIEGO,SEC_EJEC,DESC_EJECUTORA,OBSERVACION_GLOSA\n");

            Nodo<Acrededores> actual = acrededoresList.getCabeza();
            while (actual != null) {
                Acrededores acredor = actual.getDato();
                 bw.write(acredor.getRuc() + "," + acredor.getRazonSocial() + "," +
                         acredor.getRemypeDepartamento() + "," + acredor.getRemypeProvincia() + "," +
                         acredor.getRemypeDistrito() + "," + acredor.getDomicilioFiscal() + "," +
                         acredor.getAppInformaticoDemandas() + "," + acredor.getSiaf() + "," +
                         acredor.getEstadoDeuda() + "," + acredor.getTipoDocumento() + "," +
                         acredor.getDocDevenOSentenJudi() + "," + acredor.getMontoDeuda() + "," +
                         acredor.getDescNivelGobierno() + "," + acredor.getDescSector() + "," +
                         acredor.getDescPliego() + "," + acredor.getSecEjec() + "," +
                         acredor.getDescEjecutora() + "," + acredor.getObservacionGlosa() + "\n");
                actual = actual.getSiguiente();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

