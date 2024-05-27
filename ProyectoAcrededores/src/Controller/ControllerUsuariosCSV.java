/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Models.Usuario;
import Models.Nodo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ControllerUsuariosCSV {

    private final String filePath;

    public ControllerUsuariosCSV(String filePath) {
        this.filePath = filePath;
    }

    public ImplListaEnlazada<Usuario> leerUsuarios() {
        ImplListaEnlazada<Usuario> usuariosList = new ImplListaEnlazada<>();
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
                return usuariosList;
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
                if (values.length < 3) {
                    System.err.println("Línea con datos insuficientes: " + line);
                    continue;  // Saltar esta línea y continuar con la siguiente
                }
                try {
                    Usuario usuario = new Usuario(
                            Integer.parseInt(values[0]), // ID
                            values[1], // Nombre de usuario
                            values[2] // Contraseña
                    );
                    usuariosList.insertar(usuario, usuariosList.getTamaño());
                } catch (NumberFormatException e) {
                    System.err.println("Error de formato numérico en la línea: " + line);
                    e.printStackTrace();
                    continue; // Saltar esta línea y continuar con la siguiente
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuariosList;
    }

    public void escribirUsuarios(ImplListaEnlazada<Usuario> usuariosList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Escribir cabecera
            bw.write("ID,NOMBRE_USUARIO,CONTRASEÑA\n");

            Nodo<Usuario> actual = usuariosList.getCabeza();
            while (actual != null) {
                Usuario usuario = actual.getDato();
                bw.write(usuario.getIdUsuario() + "," + usuario.getNombreUsuario() + "," + usuario.getContraseña() + "\n");
                actual = actual.getSiguiente();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
