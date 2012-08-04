currentThumb = 0;
thumbCount = 10;

function nextThumbnails() {
    if(! $('#next').hasClass('disabled')) {
        currentThumb += thumbCount;   
        updateThumbnails();
    }
}

function previousThumbnails() {
    if(! $('#prev').hasClass('disabled')) {
        currentThumb -= thumbCount;   
        updateThumbnails();
    }
}

function updateThumbnails() {
    if(currentThumb > 0) {
        // Enable prev
        $('#prev').removeClass('disabled');
    } else {
        // Disable prev
        $('#prev').addClass('disabled');
        currentThumb = 0;
    }
    $('#averageThumbnails').empty();
    console.log('Clearing');
    if(showThumbnails(currentThumb, thumbCount) || !results[currentThumb+thumbCount]) {
        // Disable next
        $('#next').addClass('disabled');
        console.log("No more thumbs");
    } else {
        $('#next').removeClass('disabled');
    }
    console.log('Updated '+currentThumb);
}

function showThumbnails(offset, count) {
    for(i = offset; i < count+offset; i++) {
        if(results[i]) {
            $('#averageThumbnails').append(buildThumbnail(results[i]));
            console.log("Displayed "+i);
        } else {
            return true;
        }
    }
    return false;
}

function buildThumbnail(result) {
    var average = result.average;
    var count = result.count;
    var particles = result.particles[0];
    return "<li><a class=\"thumbnail\" href=\""
            + particles
            + "\"><img src=\""
            + average
            + "\" alt=\"\"><h5 align=\"center\">"
            +count+" Particles</h5></a></li>";
}

showThumbnails(currentThumb, thumbCount);