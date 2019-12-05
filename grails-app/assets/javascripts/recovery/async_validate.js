const field_list = ['password', 'confirm'];
class Interceptor {
    constructor(intercepted = false){
        console.log('interceptor called');
        this.intercepted = intercepted;
    };

    intercept(e)
    {
        if(this.intercepted)
        {
            console.log("ok, submitting");
            $("#recovery_form").off("submit");
            $("#submit").click();
            return true;
        }
        e.preventDefault();
        let has_obvious_errors = false;
        for(let i = 0; i < field_list.length; ++i){
            let value = document.getElementById(field_list[i]).value;
            if(!value){
                alertError("Please fill this up",field_list[i])
            }
            if(value.length > 50){
                alertError("Entered value is too long",field_list[i]);
                has_obvious_errors = true;
            }

        }
        if(has_obvious_errors){
            console.log("has obvious errors, rejecting");
            return false;
        }
        let xhr = new XMLHttpRequest();
        let url = "/recover/validate";
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log('server validated the data');
                return new Interceptor(true).intercept(e);
            }
            if(xhr.readyState === 4 && xhr.status === 400){
                let json = JSON.parse(xhr.responseText);
                console.log('invalid data, server response: ');
                console.log(json.errors);
                for(let i = 0; i < json.errors.errors.length; ++i){
                    alertError(json.errors.errors[i].message,json.errors.errors[i].field);
                }
                document.getElementById('password').value = "";
                document.getElementById('confirm').value = "";
                return false;
            }
        };
        let data = JSON.stringify(
            {
                "password" : document.getElementById("password").value,
                "confirm"  : document.getElementById("confirm").value
            });
        xhr.send(data);
    }
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

$("#recovery_form").on("submit",function (e) {
    console.log("jquery selector for password recovery form");
    new Interceptor(false).intercept(e);
});

