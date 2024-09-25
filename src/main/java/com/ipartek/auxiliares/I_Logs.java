package com.ipartek.auxiliares;

public interface I_Logs {
	
	String PRIORIDAD_INFO  ="Info";
	String PRIORIDAD_ADVERTENCIA  ="Advertencia";
	String PRIORIDAD_ERROR  ="Error";
	String PRIORIDAD_ALARMA  ="Alarma";
	
	String LOG_ACCESOS  ="accesos.txt";
	int LOG_ACCESOS_ACCESO_CORRECTO=1;
	int LOG_ACCESOS_ACCESO_FALLIDO=2;
	int LOG_ACCESOS_ACCESO_CIERRE_SESION=3;
	int LOG_ACCESOS_ACCESO_BANEADO=4;
	int LOG_ACCESOS_HONEYPOT=5;
	
	String LOG_PRODUCTOS  ="productos.txt";
	int LOG_PRODUCTOS_AGREGADO =1;
	int LOG_PRODUCTOS_BORRADO =2;
	int LOG_PRODUCTOS_MODIFICADO =3;
	int LOG_PRODUCTOS_BORRADA_IMAGEN =4;
	
	String LOG_BACKUPS  ="backups.txt";
	int LOG_BACKUPS_CREADO =1;
	int LOG_BACKUPS_RESTAURADO =2;
	
	String LOG_RUTA="src/main/logs/";

}
