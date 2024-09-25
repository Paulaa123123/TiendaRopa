package com.ipartek.auxiliares;

import jakarta.servlet.http.HttpSession;

public class Mensajeria implements I_Mensajeria{

	public static void limpiarMensaje(HttpSession session) {
		session.removeAttribute("s_mensaje");
	}

	public static void registrarMensaje(HttpSession session, int codMensaje) {
		session.setAttribute("s_mensaje", obtenerCodMensaje(codMensaje));
	}

	private static String obtenerCodMensaje(int codMensaje) {
		String mensaje = "";

		switch (codMensaje) {
		case 1:
			mensaje = MSG_PROD_ANYADIDO;
			break;
		case 2:
			mensaje = MSG_IMG_PROD_BORRADO;
			break;
		case 3:
			mensaje = MSG_PROD_BORRADO;
			break;
		case 4:
			mensaje = MSG_PROD_MODIFICADO;
			break;
		case 5:
			mensaje = MSG_LOGIN_ACCESO_DENEGADO;
			break;
		case 6:
			mensaje = MSG_NO_TIENE_ACCESO;
			break;
		case 7:
			mensaje = MSG_BACKUP_OK;
			break;
		default:
			mensaje = "";
			break;
		}

		return mensaje;
	}

}
