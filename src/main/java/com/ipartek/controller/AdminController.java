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

		Usuario usu = (Usuario) session.getAttribute("s_usuario");

		if (usu == null || usu.getRol().getId() != 1) {
			return "redirect:https://www.google.es/";
		}

		model.addAttribute("atr_lista_generos", generoRepo.findAll());
		model.addAttribute("atr_lista_categorias", categoriaRepo.findAll());
		model.addAttribute("atr_lista_productos", productoRepo.findAll());
		model.addAttribute("atr_lista_tallas", tallaRepo.findAll());
		model.addAttribute("obj_producto", new Producto());

		return "admin";
	}

	@RequestMapping("/adminBorrarImagen")
	public String borrarImagenAdmin(Model model, @RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "id", required = false) int id) {

		Auxiliares.borrarImagenFisica(nombre);

		Optional<Producto> prod = productoRepo.findById(id);

		prod.get().setFoto("default.jpg");

		Producto productoModificado = prod.get();

		productoRepo.save(productoModificado);

		return "redirect:/superuser";
	}

	@RequestMapping("/adminFrmBorrarProducto")
	public String borrarProductoAdmin(Model model, @RequestParam(value = "id", required = false) Integer id) {

		Optional<Producto> prod = productoRepo.findById(id);
		Producto productoABorrar = prod.get();

		if (!(productoABorrar.getFoto().equals("default.jpg"))) {
			Auxiliares.borrarImagenFisica(productoABorrar.getFoto());
		}

		productoRepo.deleteById(id);

		return "redirect:/superuser";
	}

	@RequestMapping("/login")
	public String loginAdmin(Model model, HttpSession session) {

		model.addAttribute("obj_usuario", new Usuario());
		if (session.getAttribute("s_intentos") == null) {
			session.setAttribute("s_intentos", 0);
		}

		return "login";
	}

	@RequestMapping("/revisarLogin")
	public String revisarLoginAdmin(Model model, @ModelAttribute("obj_usuario") Usuario usuario, HttpSession session) {

		Usuario usuValidado = usuarioRepo.validarUsuario(usuario.getUsuario(), usuario.getPass());

		if (usuValidado != null) {
			session.setAttribute("s_usuario", usuValidado);

			String ruta = "";

			if (usuValidado.getRol().getId() == 1) {
				ruta = "redirect:/superuser";
			} else {

				ruta = "redirect:https://www.google.es/";
			}

			return ruta;

		} else {

			session.setAttribute("s_intentos", (int) session.getAttribute("s_intentos") + 1);

			System.out.println("Intento " + (int) session.getAttribute("s_intentos"));

			if ((int) session.getAttribute("s_intentos") < 3) {
				Mensajeria.registrarMensaje(session, 1);
				return "login";
			} else if ((int) session.getAttribute("s_intentos") == 3) {
				Usuario usuBlock = usuarioRepo.validarUsuario(usuario.getUsuario());
				if (usuBlock != null) {
					usuBlock.setRol(new Rol(3, "baneado"));
					usuarioRepo.save(usuBlock);
				}
				return "login";
			} else {
				return "redirect:https://www.google.es/";
			}
		}
	}

	@RequestMapping("/cerrarSesion")
	public String cerrarSesionAdmin(Model model, HttpSession session) {

		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping("/adminBorrarProducto")
	public String borrarProducto(Model model, @RequestParam(value = "id", required = false) Integer id) {
		productoRepo.deleteById(id);
		return "redirect:/superuser";
	}

	@RequestMapping("/adminFrmModificarProducto")
	public String modificarProducto(Model model, @RequestParam(value = "id", required = false) Integer id,
			@ModelAttribute("obj_producto") Producto producto) {
		model.addAttribute("atr_lista_generos", generoRepo.findAll());
		model.addAttribute("atr_lista_categorias", categoriaRepo.findAll());
		model.addAttribute("atr_lista_tallas", tallaRepo.findAll());

		if (id != null) {
			producto = productoRepo.findById(id).orElse(producto = new Producto());
		}

		model.addAttribute("obj_producto", producto);

		return "/formModificarProducto";
	}

	@RequestMapping("/adminModificarProducto")
	public String modificarProductoAdmin(Model model, @ModelAttribute("obj_producto") Producto producto,
			@RequestParam("param_foto") MultipartFile archivo) {

		// HACER
		if (!archivo.isEmpty()) {
			Auxiliares.guardarImagen(producto, archivo);
		}

		productoRepo.save(producto);
		return "redirect:/superuser";

	}

	@RequestMapping("/adminGuardarProducto")
	public String guardarProducto(Model model, @ModelAttribute("obj_producto") Producto producto,
			@RequestParam("param_foto") MultipartFile archivo) {

		Auxiliares.guardarImagen(producto, archivo);

		productoRepo.save(producto);
		return "redirect:/superuser";
	}

}
