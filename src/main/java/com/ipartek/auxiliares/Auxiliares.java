package com.ipartek.auxiliares;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ipartek.model.Producto;

public class Auxiliares {

	public static void guardarImagen(Producto producto, MultipartFile archivo) {
		if (!archivo.isEmpty()) {
			try {
				// Ruta donde se guardará el archivo
				Date d = new Date();

				String nombreArchivo = archivo.getOriginalFilename();
				Path ruta = Paths.get("src/main/resources/static/images/", d.getTime() + nombreArchivo);

				// Guardar el archivo físicamente
				Files.write(ruta, archivo.getBytes());

				// Asignar el nombre del archivo al objeto Producto
				producto.setFoto(d.getTime() + nombreArchivo);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} else {
			// Si no se subió archivo, asignar un valor por defecto
			producto.setFoto("default.jpg");
		}
	}

	public static void borrarImagenFisica(String nombre) {
		String ruta = "src/main/resources/static/images/" + nombre;

		File archivoFoto = new File(ruta);

		if (archivoFoto.exists()) {
			if (archivoFoto.delete()) {
				System.out.println("FOTO BORRADA");
			} else {
				System.out.println("FOTO NO BORRADA");
			}
		} else {
			System.out.println("FOTO NO ENCONTRADA");
		}
	}

	public static void crearCSV(String nombreArchivo, List<Object> lista) {
		try {
			Path ruta = Paths.get("src/main/backups/", nombreArchivo);
			BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta.toString()));
			for (Object obj : lista) {

				escritor.write(obj.toString());
				escritor.newLine();

				System.out.println("El archivo " + nombreArchivo + " se ha creado correctamente.");
			}
			escritor.close();

		} catch (IOException e) {
			System.out.println("Ocurrió un error al crear el archivo.");
			e.printStackTrace();
		}
	}

	public static List leerCSV(String nombreArchivo) {
		List lista = new ArrayList<>();
		Path ruta = Paths.get("src/main/backups/", nombreArchivo);

		try (BufferedReader lector = new BufferedReader(new FileReader(ruta.toString()))) {
			String linea;
			while ((linea = lector.readLine()) != null) {
				lista.add(linea);
			}
			System.out.println("El archivo " + nombreArchivo + " se ha leído correctamente.");
		} catch (IOException e) {
			System.out.println("Ocurrió un error al leer el archivo.");
			e.printStackTrace();
		}

		return lista;
	}
}
