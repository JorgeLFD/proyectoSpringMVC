$(document).ready(function() {
	/* Cuando se muestre el modal, cargaremos el dato recibido por
	el th:data-id en la variable targetid del modal*/
	$('#delete-modal').on('show.bs.modal', function(event) {
		$(this).data("targetid", ""); /* Primero limpiamos si hubiese algún valor previo. */
		var targetid = $(event.relatedTarget).data("id");	/* Ahora capturamos el dato del th:data */	
		$(this).data("targetid", targetid);	 /* Damos el valor del dato al targetid del modal */
	});

	/* Cuando pulsemos el botón Sí, se construirá el enlace con el
	id del producto, que teníamos guardado en el targetid del modal */
	$('#borrarItem').on('click', function(event) {
		var id = $('#delete-modal').data("targetid");
		window.location.href += `borrar/${id}`;
		console.log(href); //Por si se quiere comprobar desde el inspector del navegador
	});
});