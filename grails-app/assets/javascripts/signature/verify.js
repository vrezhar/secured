var clicked = false;

function submit()
{
    var signature = document.getElementById('signature').value;
    console.log(signature);
    var xhr = new XMLHttpRequest();
    var url = "/user/company/sign";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);
            if(json.companyId != null && json.address != null) {
                document.getElementById("companyId").innerHTML = json.companyId;
                document.getElementById("address").innerHTML = json.address;
                document.getElementById("companyId").style.color = "#1e7e34";
                document.getElementById("address").style.color = "#1e7e34";
                document.getElementById("company").style.visibility = "visible";
                document.getElementById("company").style.display = "block";
                document.getElementById("cancel").style.visibility = "visible";
                document.getElementById("cancel").style.display = "block";
                document.getElementById("confirm").style.visibility = "visible";
                document.getElementById("confirm").style.display = "block";
            }
            else{
                document.getElementById("company").style.visibility = "visible";
                document.getElementById("company").style.display = "block";
                document.getElementById("cancel").style.visibility = "visible";
                document.getElementById("cancel").style.display = "block";
                document.getElementById("companyId").style.color = "#a60000";
                document.getElementById("address").style.color = "#a60000";
                document.getElementById("companyId").innerHTML = "Not Found";
                document.getElementById("address").innerHTML = "Not Found";
                document.getElementById("confirm").style.visibility = "hidden";
                document.getElementById("confirm").style.display = "none";
            }
        }
    };
    var data = JSON.stringify({"body": signature});
    xhr.send(data);
}

function cancel() {
    document.getElementById('signature').value = "";
    document.getElementById("companyId").innerHTML = "";
    document.getElementById("address").innerHTML = "";
    document.getElementById("company").style.visibility = "hidden";
    document.getElementById("company").style.display = "none";
    document.getElementById('signature').innerHTML = "";
    document.getElementById('signature').innerText = "";
    document.getElementById("cancel").style.visibility = "hidden";
    document.getElementById("cancel").style.display = "none";
    document.getElementById("confirm").style.visibility = "hidden";
    document.getElementById("confirm").style.display = "none";
}

function confirm()
{
    var signature = document.getElementById('signature').value;
    console.log("intercepted a submit");
    var xhr = new XMLHttpRequest();
    var url_save = "/user/company/confirm";
    xhr.open("POST", url_save, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
                window.location.replace("/user/show");
        }
    };
    var data = JSON.stringify({"body": signature});
    xhr.send(data);
}
