let intercepted = false;

function onFocus(field)
{
    let errorList = document.getElementById( field + "_errors");
    errorList.style.display = "none";
    errorList.style.visibility = "hidden";
    errorList.innerHTML = "";
}

function alertError(message,field)
{
    let errorList = document.getElementById(field + "_errors");
    errorList.style.display = "block";
    errorList.style.width = "250px";
    errorList.style.visibility = "visible";
    console.log(errorList.childNodes[1]);
    let messageholder = errorList.childNodes[1];
    if(!messageholder){
        let listItem = document.createElement("li");
        listItem.appendChild(document.createTextNode(message));
        errorList.appendChild(listItem);
        return;
    }
    if(messageholder.childNodes[0].data !== message)
    {
        messageholder.childNodes[0].data = message;
    }
    console.log(messageholder.value);
    console.log(message);
}

function removeOnChange(field)
{
    let errorList = document.getElementById(field + "_errors");
    errorList.style.display = "none";
    errorList.style.visibility = "hidden";
}

function alertEmptyInputFor(message,field)
{
    let errorList = document.getElementById(field + "_errors");
    errorList.style.display = "block";
    errorList.style.width = "250px";
    errorList.style.visibility = "visible";
    let messageholder = errorList.childNodes[1];
    if(!messageholder){
        var listItem = document.createElement("li");
        listItem.appendChild(document.createTextNode(message));
        errorList.appendChild(listItem);
        return;
    }
    if(messageholder.childNodes[0].data !== message)
    {
        messageholder.childNodes[0].data = message;
    }
}

function alertMismatch()
{

    var errorList = document.getElementById("confirm_errors");
    var message = "passwords don't match";
    errorList.style.display = "block";
    errorList.style.width = "250px";
    errorList.style.visibility = "visible";
    var messageholder = errorList.childNodes[1];
    if(!messageholder){
        var listItem = document.createElement("li");
        listItem.appendChild(document.createTextNode(message));
        errorList.appendChild(listItem);
        return;
    }
    if(messageholder.childNodes[0].data !== message)
    {
        messageholder.childNodes[0].data = message;
    }
}
$("#username").on("input change keyup paste propertychange", function(){
   removeOnChange("username");
   if(!document.getElementById("username").value){
       alertEmptyInputFor("username");
   }
});
$("#password").on("input change keyup paste propertychange", function(){
    removeOnChange("password");
    if(!document.getElementById("password").value){
        alertEmptyInputFor("password");
    }
    if(document.getElementById("confirm").value !== document.getElementById("password").value ){
        alertMismatch();
    }
});
$("#firstName").on("input change keyup paste propertychange", function(){
    removeOnChange("firstName");
    if(!document.getElementById("firstName").value){
        alertEmptyInputFor("firstName");
    }
});
$("#lastName").on("input change keyup paste propertychange", function(){
    removeOnChange("lastName");
    if(!document.getElementById("lastName").value){
        alertEmptyInputFor("lastName");
    }
});
$("#confirm").on("input change keyup paste propertychange", function(){
    removeOnChange("confirm");
    if(document.getElementById("confirm").value !== document.getElementById("password").value ){
        alertMismatch();
    }
});

$("#register_form").submit(function (e) {
    if(intercepted){
        return true
    }
    e.preventDefault();
    var xhr = new XMLHttpRequest();
    var url = "/register/validate";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            intercepted = true;
            $("#register_form input").click();
            return true;
        }
        if(xhr.readyState === 4 && xhr.status === 400){
            var json = JSON.parse(xhr.responseText);
            for(i = 0; i < json.errors.errors.length; ++i){
                alertError(json.errors.errors[i].message,json.errors.errors[i].field);
            }
            return false;
        }
    };
    var data = JSON.stringify(
        {
            "firstName": document.getElementById("firstName").value,
            "lastName" : document.getElementById("lastName").value,
            "username" : document.getElementById("username").value,
            "password" : document.getElementById("password").value,
            "confirm"  : document.getElementById("confirm").value
        });
    console.log(data);
    xhr.send(data);
});

