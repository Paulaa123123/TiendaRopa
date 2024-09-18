package com.ipartek.auxiliares;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.ipartek.model.Producto;

public class Auxiliares {

	
	public static void guardarImagen(Producto producto, MultipartFile archivo) {
		if (!archivo.isEmpty()) {
		        try {
		            // Ruta donde se guardará el archivo
		        	Date d= new Date();
		 
		            String nombreArchivo = archivo.getOriginalFilename();
		            Path ruta = Paths.get("src/main/resources/static/images/", d.getTime()+nombreArchivo);
	
		            // Guardar el archivo físicamente
		            Files.write(ruta, archivo.getBytes());
		            
		            // Asignar el nombre del archivo al objeto Producto
		            producto.setFoto(d.getTime()+nombreArchivo);
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
}
