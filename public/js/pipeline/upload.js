filepicker.setKey('A72JUMKgYSuu1_ZPRtTbPz');

function uploadImages() {
    console.log("Opening Filepicker");
    filepicker.getFile('*', {
        'multiple': true, 
        'modal': true,
        'persist': false,
        }, function(response) {
            // Let the app know about the files
            console.log("Filepicker upload complete");
            for(id in response) {
                console.log("Registering File "+JSON.stringify(response[id]));
                var file = response[id];
                $.ajax({
                  url: '/project/'+projectId+'/uploadImage',
                  type: 'POST',
                  dataType: 'text',
                  contentType: 'application/json',
                  cache: false,
                  data: JSON.stringify({'url': file.url, 'filename': file.data.filename}),
                  success: function(data, textStatus, xhr) {
                    // Attach a new image line to the list
                    console.log("Registered "+data);
                  }
                });
                
            }
        }
    );
}