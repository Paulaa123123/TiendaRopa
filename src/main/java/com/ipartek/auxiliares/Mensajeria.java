package com.ipartek.auxiliares;

import jakarta.servlet.http.HttpSession;

public class Mensajeria {

	public static void registrarMensaje(HttpSession session,int codMensaje){
		
		
		session.setAttribute("s_mensaje", obtenerCodMensaje(codMensaje));
		
	}
	
	public static String obtenerCodMensaje(int codMensaje){
		
		String mensaje="";
		
		switch (codMensaje) {
			case 1:   mensaje="Intento fallido"    ;break;
			case 2:   mensaje="mens 2"    ;break;
			case 3:   mensaje="mens 3"    ;break;
			case 4:   mensaje="mens 4"    ;break;
			
			default:
				mensaje="";break;
		}
		
		return "";
	}
	
	
}
