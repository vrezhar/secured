$(".enable_toggler").on("click",function (e) {
    let id = e.target.id;
    disable(id);
});
$(".disable_toggler").on("click",function (e) {
    let id = e.target.id;
    enable(id);
});

function enable(id) {
    let xhr = new XMLHttpRequest();
    let url = "/main/enable";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            location.reload();
        }
    };
    let data = JSON.stringify(
        {
            "username":id
        });
    xhr.send(data);
}

function disable(id) {
    let xhr = new XMLHttpRequest();
    let url = "/main/disable";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
           location.reload();
        }
    };
    let data = JSON.stringify(
        {
            "username":id
        });
    xhr.send(data);
}