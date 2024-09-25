package com.ipartek.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ipartek.auxiliares.Auxiliares;
import com.ipartek.auxiliares.I_Logs;
import com.ipartek.auxiliares.Logs;
import com.ipartek.auxiliares.Mensajeria;
import com.ipartek.model.Producto;
import com.ipartek.model.Rol;
import com.ipartek.model.Usuario;
import com.ipartek.repository.CategoriaRepository;
import com.ipartek.repository.GeneroRepository;
import com.ipartek.repository.ProductoRepository;
import com.ipartek.repository.TallaRepository;
import com.ipartek.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private GeneroRepository generoRepo;

	@Autowired
	private TallaRepository tallaRepo;

	@Autowired
	private CategoriaRepository categoriaRepo;

	@Autowired
	private ProductoRepository productoRepo;

	@Autowired
	private UsuarioRepository usuarioRepo;

	@RequestMapping("/superuser")
	public String cargarAdmin(Model model, HttpSession session) {

		model.addAttribute("atr_lista_generos", generoRepo.findAll());
		model.addAttribute("atr_lista_categorias", categoriaRepo.findAll());
		model.addAttribute("atr_lista_productos", productoRepo.findAll());

		model.addAttribute("obj_producto", new Producto());

		Usuario usu = new Usuario();
		if (session.getAttribute("s_usuario") != null) {
			usu = (Usuario) (session.getAttribute("s_usuario"));
		}

		String ruta = "";
		switch (usu.getRol().getId()) {
		case 1:
			ruta = "admin";
			break;
		case 2:
			ruta = "admin";
			break;
		default:
			ruta = "redirect:https://www.google.es/";
			break;
		}

		return ruta;
	}

	@RequestMapping("/adminBorrarImagen")
	public String borrarImagenAdmin(Model model, @RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "id", required = false) int id, HttpSession session) {

		Usuario usu = new Usuario();
		if (session.getAttribute("s_usuario") != null) {
			usu = (Usuario) (session.getAttribute("s_usuario"));
		}

		String ruta = "";
		switch (usu.getRol().getId()) {
		case 1:
			ruta = "redirect:/superuser";
			break;
		case 2:
			ruta = "redirect:/superuser";
			break;
		default:
			ruta = "redirect:https://www.google.es/";
			break;
		}

		Mensajeria.limpiarMensaje(session);

		Auxiliares.borrarImagenFisica(nombre);

		Optional<Producto> prod = productoRepo.findById(id);
		prod.get().setFoto("default.jpg");
		Producto productoModificado = prod.get();

		productoRepo.save(productoModificado);

		Mensajeria.registrarMensaje(session, 2);

		return "redirect:/superuser";
	}

	@RequestMapping("/adminFrmBorrarProducto")
	public String borrarProductoAdmin(Model model, @RequestParam(value = "id", required = false) Integer id,
			HttpSession session) {

		Usuario usu = new Usuario();
		if (session.getAttribute("s_usuario") != null) {
			usu = (Usuario) (session.getAttribute("s_usuario"));
		}

		String ruta = "";
		switch (usu.getRol().getId()) {
		case 1:
			ruta = "redirect:/superuser";
			break;
		case 2:
			ruta = "redirect:/superuser";
			break;
		default:
			ruta = "redirect:https://www.google.es/";
			break;
		}

		Mensajeria.limpiarMensaje(session);

		Optional<Producto> prod = productoRepo.findById(id);
		Producto productoABorrar = prod.get();

		if (!(productoABorrar.getFoto().equals("default.jpg"))) {
			Auxiliares.borrarImagenFisica(productoABorrar.getFoto());
		}

		productoRepo.deleteById(id);

		Mensajeria.registrarMensaje(session, 3);

		return ruta;
	}

	@RequestMapping("/login")
	public String loginAdmin(Model model, HttpSession session) {

		Mensajeria.limpiarMensaje(session);

		model.addAttribute("obj_usuario", new Usuario());
		if (session.getAttribute("s_intentos") == null) {
			session.setAttribute("s_intentos", 0);
		}

		return "login";
	}

	@RequestMapping("/revisarLogin")
	public String revisarLoginAdmin(Model model, @ModelAttribute("obj_usuario") Usuario usuario, HttpSession session) {

		Mensajeria.limpiarMensaje(session);

		Usuario usuValidado = usuarioRepo.validarUsuario(usuario.getUsuario(), usuario.getPass());

		if (usuValidado != null) {
			session.setAttribute("s_usuario", usuValidado);
			session.setAttribute("s_usuario_Log", usuValidado);
			// en caso que el login sea correcto, no hara falta mandar mensaje de error

			session.removeAttribute("s_intentos");// limpiamos el numero de intentos

			String ruta = "";
			switch (usuValidado.getRol().getId()) {
			case 1:
				ruta = "redirect:/superuser";
				break;
			case 2:
				ruta = "redirect:/superuser";
				break;

			default:
				ruta = "redirect:https://www.google.es/";
				break;
			}

			Logs.registrarLogAcceso(I_Logs.LOG_ACCESOS_ACCESO_CORRECTO, session);

			return ruta;
		} else {
			session.setAttribute("s_usuario_Log", usuario);
			Mensajeria.registrarMensaje(session, 5);

			session.setAttribute("s_intentos", (int) session.getAttribute("s_intentos") + 1);

			System.out.println("Intento" + (int) session.getAttribute("s_intentos"));

			if ((int) session.getAttribute("s_intentos") < 3) {

				Logs.registrarLogAcceso(I_Logs.LOG_ACCESOS_ACCESO_FALLIDO, session);
				return "login";
			} else if ((int) session.getAttribute("s_intentos") == 3) {
				Usuario usuBlock = usuarioRepo.validarUsuario(usuario.getUsuario());
				if (usuBlock != null) {
					usuBlock.setRol(new Rol(3, "baneado"));
					usuarioRepo.save(usuBlock);

					Logs.registrarLogAcceso(I_Logs.LOG_ACCESOS_ACCESO_BANEADO, session);
				}

				return "login";
			} else {

				Logs.registrarLogAcceso(I_Logs.LOG_ACCESOS_HONEYPOT, session);

				return "redirect:https://www.google.es/";
			}
		}
	}

	@RequestMapping("/cerrarSesion")
	public String cerrarSesionAdmin(Model model, HttpSession session) {

		Usuario usu = new Usuario();
		if (session.getAttribute("s_usuario") != null) {
			usu = (Usuario) (session.getAttribute("s_usuario"));
		}

		String ruta = "";
		switch (usu.getRol().getId()) {
		case 1:
			ruta = "redirect:/";
			break;
		case 2:
			ruta = "redirect:/";
			break;
		default:
			ruta = "redirect:https://www.google.es/";
			break;
		}

		session.setAttribute("s_usuario_Log", usu);
		Logs.registrarLogAcceso(I_Logs.LOG_ACCESOS_ACCESO_CIERRE_SESION, session);

		session.invalidate();

		return ruta;
	}

	@RequestMapping("/adminBorrarProducto")
	public String borrarProducto(Model model, @RequestParam(value = "id", required = false) Integer id) {
		productoRepo.deleteById(id);
		return "redirect:/superuser";
	}

	@RequestMapping("/adminFrmModificarProducto")
	public String frmModificarProductoAdmin(Model model, @RequestParam(value = "id", required = false) Integer id,
			HttpSession session) {

		Usuario usu = new Usuario();
		if (session.getAttribute("s_usuario") != null) {
			usu = (Usuario) (session.getAttribute("s_usuario"));
		}

		String ruta = "";
		switch (usu.getRol().getId()) {
		case 1:
			ruta = "frm_modificar_productos";
			break;
		case 2:
			ruta = "frm_modificar_productos";
			break;
		default:
			ruta = "redirect:https://www.google.es/";
			break;
		}

		model.addAttribute("atr_lista_generos", generoRepo.findAll());
		model.addAttribute("atr_lista_categorias", categoriaRepo.findAll());

		Producto prod = new Producto();
		if (id != null) {
			prod = productoRepo.findById(id).orElse(prod = new Producto());
		}

		model.addAttribute("obj_producto", prod);

		return ruta;
	}

	@RequestMapping("/adminModificarProducto")
	public String modificarProductoAdmin(Model model, @ModelAttribute("obj_producto") Producto producto,
			@RequestParam("param_foto") MultipartFile archivo, HttpSession session) {

		Usuario usu = new Usuario();
		if (session.getAttribute("s_usuario") != null) {
			usu = (Usuario) (session.getAttribute("s_usuario"));
		}

		String ruta = "";
		switch (usu.getRol().getId()) {
		case 1:
			ruta = "redirect:/superuser";
			break;
		case 2:
			ruta = "redirect:/superuser";
			break;
		default:
			ruta = "redirect:https://www.google.es/";
			break;
		}

		Mensajeria.limpiarMensaje(session);

		Auxiliares.guardarImagen(producto, archivo);

		productoRepo.save(producto);

		Mensajeria.registrarMensaje(session, 4);

		return "redirect:/superuser";
	}

	@RequestMapping("/adminGuardarProducto")
	public String guardarProductoAdmin(Model model, @ModelAttribute("obj_producto") Producto producto,
			@RequestParam("param_foto") MultipartFile archivo, HttpSession session) {

		Usuario usu = new Usuario();
		if (session.getAttribute("s_usuario") != null) {
			usu = (Usuario) (session.getAttribute("s_usuario"));
		}

		String ruta = "";
		switch (usu.getRol().getId()) {
		case 1:
			ruta = "redirect:/superuser";
			break;
		case 2:
			ruta = "redirect:/superuser";
			break;
		default:
			return "redirect:https://www.google.es/";
		}

		Mensajeria.limpiarMensaje(session);

		Auxiliares.guardarImagen(producto, archivo);

		productoRepo.save(producto);

		Mensajeria.registrarMensaje(session, 1);

		return ruta;
	}

}
