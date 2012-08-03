// Get progress
function getProgress() {
    if(pipelineId == "")
        return;
    // Ajax call
    //renderProgress(progress)
    $.ajax({
              url: '/pipeline/'+pipelineId+'/progress',
              type: 'GET',
              async: true,
              dataType: 'text',
              cache: false,
              success: function(data, textStatus, xhr) {
                  if(!renderAllProgress(JSON.parse(data))) {
                    setTimeout('getProgress()', 1000);
                  }
              },
              error: function() {
                  setTimeout('getProgress()', 1000);
              }
            });
}

function calculateProgress(item) {
    return item.expected > 0 ? 
            (100.0 * item.progress / item.expected) : 0;
}

function renderProgress(name, percentage) {
    // Calculate progress
    $('#'+name+'progress').css('width', percentage+'%');
    $('#'+name+'number').text(percentage.toFixed(1)+'%');
    if(percentage >= 100) {
        $('#'+name+'bar').removeClass('active');
        $('#'+name+'bar').addClass('progress-success');
        return true;
    } else {
        return false;
    }
}



function renderAllProgress(progress) {
    var completed = false;
    for(key in progress) {
        if(key == 'classifier'){
            if(progress[key].classified) {
                renderProgress('classifier', 100);
            }
            if(progress[key].done) {
                renderProgress('uploader', 100);
                completed = true;
            }
        } else {
            var percentage = calculateProgress(progress[key]);
            renderProgress(key, percentage);
        }
    }
    if(completed) {
        $('#results').slideToggle('fast');
    }
    return completed;
}

getProgress();