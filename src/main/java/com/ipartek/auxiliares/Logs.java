package com.ipartek.auxiliares;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ipartek.model.Usuario;

import jakarta.servlet.http.HttpSession;

public class Logs implements I_Logs {
	
	

	
	public static void registrarLogAcceso(int tipoAcceso, HttpSession session) {
		LocalDateTime localDT = LocalDateTime.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String fechaHora = localDT.format(formato);
		
		Path rutaArchivo = Paths.get(LOG_RUTA, LOG_ACCESOS);
		String mensaje="";
		
		Usuario user=((Usuario)session.getAttribute("s_usuario_Log"));
			
		switch (tipoAcceso) {
			case LOG_ACCESOS_ACCESO_CORRECTO: 	
				mensaje=fechaHora+": "+PRIORIDAD_INFO+" -> Entrada del usuario: "+user.getUsuario();
				escribirArchivo(rutaArchivo, mensaje);
				break;
			case LOG_ACCESOS_ACCESO_FALLIDO: 
				String pass= "****"+user.getPass().substring(user.getPass().length()-4, user.getPass().length());
				mensaje=fechaHora+": "+PRIORIDAD_ADVERTENCIA+" -> Intento de entrada del usuario: "+user.getUsuario()
						+"(password= "+pass+")";
				escribirArchivo(rutaArchivo, mensaje);
				break;
			case LOG_ACCESOS_ACCESO_CIERRE_SESION:
				mensaje=fechaHora+": "+PRIORIDAD_INFO+" -> El usuario "+user.getUsuario()+" cerro sesion";
				escribirArchivo(rutaArchivo, mensaje);
				break;
				
			case LOG_ACCESOS_ACCESO_BANEADO: 
				mensaje= fechaHora+": "+PRIORIDAD_ALARMA+" -> USUARIO "+user.getUsuario()+" BANEADO";
				escribirArchivo(rutaArchivo, mensaje);
				break;
				
			case LOG_ACCESOS_HONEYPOT: 
				mensaje= fechaHora+": "+PRIORIDAD_ALARMA+" -> USUARIO "+user.getUsuario()+" ENVIADO AL HONEYPOT";
				escribirArchivo(rutaArchivo, mensaje);
				break;
			
			default:
				escribirArchivo(rutaArchivo, fechaHora+": Algo salio mal, codigo de tipo de acceso no reconocido");
				break;

			}
	}
	
	public static void registrarLogCrudProductos(int tipoAcceso, HttpSession session) {
		LocalDateTime localDT = LocalDateTime.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String fechaHora = localDT.format(formato);
		
		Path rutaArchivo = Paths.get(LOG_RUTA, LOG_ACCESOS);
		String mensaje="";
		
		Usuario user=((Usuario)session.getAttribute("s_usuario_Log"));
		
		switch (tipoAcceso) {
		case 1:
			mensaje= fechaHora+": "+PRIORIDAD_ALARMA+" -> USUARIO "+user.getUsuario()+" BANEADO";

			break;

		default:
			break;
		}
	}
	
	
	private static void escribirArchivo(Path rutaArchivo, String mensaje) {
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo.toString(), true));

			escritor.write(mensaje);
			escritor.newLine();
			escritor.newLine();

			escritor.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
