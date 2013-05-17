/* ===================================================
 * xlseasy.js v1.0.0
 * http://adorsys.github.com/xlseasy/
 * ===================================================
 * Copyright 2013 adorsys GmbH & Co. KG.
 * info@adorsys.de
 * 
 * Author: Marius Guede
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

$(document).ready(function() {
	
	// event: click on adorsys-logo
	$('img.adorsys-logo').click(function() {
		window.open('http://www.adorsys.de', '_blank');
	});
	
	// event: click on a.btn from example.html
	$('a.btn[href="#example-details"]').click(function() {
		// get id of clicked element
		var id = $(this).attr('id');
		
		// get targeted div to toggle
		var targetedId = $('div.example-details[id="' + id + '"]');

		// toggle the targeted div
		$(targetedId).slideToggle("slow");
		
		// loop to hide all div.example-details except the targeted one
		for (var i = 0; i < 3; i++) {
			var loopID = 'exp-' + i;
			if (loopID != id) $('div.example-details[id="' + loopID + '"]').css('display', 'none');
		}
		return true;
	});
});
