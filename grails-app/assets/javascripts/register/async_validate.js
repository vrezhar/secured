class Interceptor {
    constructor(intercepted = false){
        this.intercepted = intercepted;
    };

    intercept(e)
    {
        if(this.intercepted)
        {
            $("#register_form").off("submit");
            $("#submit").click();
            return true;
        }
        e.preventDefault();
        let xhr = new XMLHttpRequest();
        let url = "/register/validate";
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                return new Interceptor(true).intercept(e);
            }
            if(xhr.readyState === 4 && xhr.status === 400){
                let json = JSON.parse(xhr.responseText);
                for(let i = 0; i < json.errors.errors.length; ++i){
                    alertError(json.errors.errors[i].message,json.errors.errors[i].field);
                }
                return false;
            }
        };
        let data = JSON.stringify(
            {
                "firstName": document.getElementById("firstName").value,
                "lastName" : document.getElementById("lastName").value,
                "username" : document.getElementById("username").value,
                "password" : document.getElementById("password").value,
                "confirm"  : document.getElementById("confirm").value
            });
        xhr.send(data);
    }
}

function onFocus(field)
{
    let errorList = document.getElementById( field + "_errors");
    errorList.value = "";
}

function alertError(message,field)
{
    let errorList = document.getElementById(field + "_errors");
    errorList.style.display = "block";
    errorList.style.width = "250px";
    errorList.style.visibility = "visible";
    let messageholder = errorList;
    // if(!messageholder){
    //     let listItem = document.createElement("li");
    //     listItem.appendChild(document.createTextNode(message));
    //     errorList.appendChild(listItem);
    //     return;
    // }
    if(!messageholder || messageholder.innerText !== message)
    {
        messageholder.innerText = message;
    }
}

function removeOnChange(field)
{
    let errorList = document.getElementById(field + "_errors");
    errorList.innerHTML = "";
}

function alertEmptyInputFor(field)
{
    alertError("Please fill this up",field);
}

function alertMismatch()
{
    alertError("passwords don't match","confirm");
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
        return;
    }
    if(document.getElementById("confirm").value !== document.getElementById("password").value && document.getElementById("confirm").value){
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
    if(!document.getElementById("confirm").value){
        alertEmptyInputFor("confirm");
        return;
    }
    if(document.getElementById("confirm").value !== document.getElementById("password").value && document.getElementById("password").value){
        alertMismatch();
    }
});

$("#register_form").on("submit",function (e) {
    new Interceptor(false).intercept(e);
});

