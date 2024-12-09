console.log("admin user");

// Event listener to handle the change event when the user selects a file for upload
document.querySelector("#image_file_input").addEventListener('change', function(event){
    let file = event.target.files[0];

    // Creates a new FileReader object to read the file
    let reader = new FileReader();
    
    reader.onload = function() {

        // Sets the src attribute of the image preview element to the file's data URL
        document.querySelector("#upload_image_preview").setAttribute("src", reader.result);
    };
    reader.readAsDataURL(file);

}
)