function loadExistingParams(params) {
	for(field in params) {
		// Try to find the field
		$('#'+field).val(params[field]);
		// Set its value
	}
}