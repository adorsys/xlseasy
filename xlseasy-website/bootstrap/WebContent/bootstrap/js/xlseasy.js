$(document).ready(function() {
	
	// event: click on adorsys-logo
	$('img.adorsys-logo').click(function() {
		window.open('http://www.adorsys.de', '_blank');
	});
	
	// event: click on a.btn from example.html
	$('a.btn[href="#"]').click(function() {
		// get id of clicked element
		var id = $(this).attr('id');
		
		// get targeted div to toggle
		var targetedId = $('div.example-details[id="' + id + '"]');

		// toggle the targeted div
		$(targetedId).slideToggle("slow");
		
		// loop to hide all div.example-details except the targeted one
		for (var i = 1; i <= 3; i++) {
			var loopID = 'exp-' + i;
			if (loopID != id) $('div.example-details[id="' + loopID + '"]').css('display', 'none');
		}

		return false;
	});
});