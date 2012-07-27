function toggleAdvanced() {
    if( $('#advanced').is(':visible') ) {
        $('#more').removeClass('active')
    } else {
        $('#more').addClass('active');
    }
    $('#advanced').slideToggle('fast');
    return false;
}