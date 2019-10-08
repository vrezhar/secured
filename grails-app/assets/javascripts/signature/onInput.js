$("#signature").on("input change keyup paste propertychange",
    function() {
        var signature = document.getElementById("signature").value;
        if(signature === null || signature === ""){
            document.getElementById("company").style.visibility = "hidden";
            document.getElementById("company").style.display = "none";
            document.getElementById("confirm").style.visibility = "visible";
            document.getElementById("confirm").style.display = "none";
            document.getElementById("cancel").style.visibility = "hidden";
            document.getElementById("cancel").style.display = "none";
            return;
        }
        var xhr = new XMLHttpRequest();
        var url = "/user/company/sign";
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);
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
        var data = JSON.stringify({"body": signature});
        xhr.send(data);
    }
);

