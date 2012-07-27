function toggleAdvanced(projectId) {
    if( $('#advancedPicking').is(':visible') ) {
        $('#more').removeClass('active')
    } else {
        $('#more').addClass('active');
    }
    $('#advancedPicking').slideToggle('fast');
    return false;
}