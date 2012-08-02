// Get progress
function getProgress() {
    // Ajax call

    //renderProgress(progress)
    $.ajax({
              url: '/pipeline/'+pipelineId+'/progress',
              type: 'GET',
              async: true,
              dataType: 'application/json',
              cache: false,
              success: function(data, textStatus, xhr) {
                  renderProgress(data);
                  setTimeout('getProgress()', 1000);
              }
            });
}

function renderProgress(progress) {
}

getProgress();