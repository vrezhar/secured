$("#create").on("click",async function (e) {
    e.preventDefault();
    toggleOff("settings");
    toggleOff("show");
    toggleOn("create");
    return false;
});

$("#settings").on("click",async function (e) {
    toggleOff("show");
    toggleOff("create");
    toggleOn("settings");
    e.preventDefault();
    return false;
});

$("#show").on("click",async function (e) {
    e.preventDefault();
    toggleOff("settings");
    toggleOff("create");
    toggleOn("show");
    return false;
});

function toggleOn(id)
{
    let div = document.getElementById(id + "_wrapper");
    let button = document.getElementById(id);
    button.classList.add("btn-green");
    div.style.visibility = "visible";
    div.style.display = "block";
}

function toggleOff(id)
{
    let div = document.getElementById(id + "_wrapper");
    let button = document.getElementById(id);
    button.classList.remove("btn-green");
    div.style.visiblity = "hidden";
    div.style.display = "none";
}

