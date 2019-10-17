let clicked = false;

function verify()
{
    let clearOnFetch = setTimeout(animate(),200);
    let experimental = setTimeout(stopAnimation,300);
    let signature = document.getElementById('signature').value;
    let xhr = new XMLHttpRequest();
    let url = "/user/company/sign";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById("verify").style.visibility = "hidden";
            document.getElementById("verify").style.display = "none";
            let json = JSON.parse(xhr.responseText);
            clearTimeout(clearOnFetch);
            clearTimeout(experimental);
            stopAnimation();
            document.getElementById("company").style.visibility = "visible";
            document.getElementById("company").style.display = "inline-block";
            document.getElementById("cancel").style.visibility = "visible";
            document.getElementById("cancel").style.display = "inline-block";
            if(json.companyId != null && json.address != null) {
                document.getElementById("companyId").innerHTML = json.companyId;
                document.getElementById("address").innerHTML = json.address;
                document.getElementById("companyId").style.color = "#1e7e34";
                document.getElementById("address").style.color = "#1e7e34";
                document.getElementById("confirm").style.visibility = "visible";
                document.getElementById("confirm").style.display = "inline-block";
            }
            else{
                document.getElementById("companyId").style.color = "#a60000";
                document.getElementById("address").style.color = "#a60000";
                document.getElementById("companyId").innerHTML = "Not Found";
                document.getElementById("address").innerHTML = "Not Found";
                document.getElementById("confirm").style.visibility = "visible";
                document.getElementById("confirm").style.display = "none";
                document.getElementById("confirm").style.visibility = "hidden";
                document.getElementById("confirm").style.display = "none";
            }
        }
    };
    let data = JSON.stringify({"body": signature});
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
    document.getElementById("verify").style.visibility = "visible";
    document.getElementById("verify").style.display = "inline-block";
}

function confirm()
{
    animate();
    let signature = document.getElementById('signature').value;
    let xhr = new XMLHttpRequest();
    let url_save = "/user/company/confirm";
    xhr.open("POST", url_save, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            stopAnimation();
            window.location.replace("/user/companies");
            document.getElementById('signature').value = "";
        }
    };
    let data = JSON.stringify({"body": signature});
    xhr.send(data);
}
