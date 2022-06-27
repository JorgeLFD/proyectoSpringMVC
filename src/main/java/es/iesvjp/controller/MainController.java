package es.iesvjp.controller;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import es.iesvjp.model.Categoria;
import es.iesvjp.model.Producto;
import es.iesvjp.service.ICategoriaService;
import es.iesvjp.service.IProductoService;

/**
 * Controlador para la página principal.
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	@Qualifier("categoriaService")
	private ICategoriaService categoriaService;
	
	@Autowired
	@Qualifier("productoService")
	private IProductoService productoService;
	
	
	/**
	 * Método del controlador que se mostrará en la página principal.
	 * Por defecto, si el id de la Categoría que se recibe por query string es nulo, se mostrarán
	 * los productos con mayor descuento, que serán obtenidos a través del servicio.
	 * Si no es nulo, buscaremos los productos que pertenezcan a esa categoría.
	 * @param idCategoria 
	 * @return
	 */
	//localhost:9000
	//localhost:9000/?idCategoria=id
	@GetMapping(value= {"","/"})
	private ModelAndView showIndex(@RequestParam(name = "idCategoria", required = false) Long idCategoria)	{
		ModelAndView mav= new ModelAndView("index");
		mav.addObject("categorias", categoriaService.listAllCategorias());
		Optional<Categoria> categoria;
		if(idCategoria == null) {
			mav.addObject("productos", productoService.listProductosMasDescuento());
		}
		else {
			categoria = categoriaService.findCategoriaById(idCategoria);
			mav.addObject("productos", productoService.listProductosByCategoria(categoria.get()));
		}
		return mav;
	}
	
	
	
	/**
	 * Busca el producto con la id que le llega como variable en el path. Lo añade al
	 * modelo para que se muestre en la página de detalle.
	 * @param id
	 * @param model
	 * @return
	 */
	//localhost:9000/product/id
	@GetMapping("/product/{id}")
	private String productDetail(@PathVariable("id") Long id, Model model) {
		Optional<Producto> producto = productoService.findProductoById(id);
		model.addAttribute("producto", producto.get());
		return "detail";
	}
	
}
