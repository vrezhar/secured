// window.onpopstate = function(event)
// {
//     if(!event.state){
//         window.history.go(-1);
//     }
//     switch (event.state.page) {
//         case "create":
//             toggleOff("settings");
//             toggleOff("show");
//             toggleOn("create");
//             break;
//         case "show":
//             toggleOff("settings");
//             toggleOff("create");
//             toggleOn("show");
//             break;
//         case "main":
//             toggleOff("show");
//             toggleOff("create");
//             toggleOn("settings");
//             break;
//     }
// };

$("#create").on("click",function (e) {
    e.preventDefault();
    toggleOff("settings");
    toggleOff("show");
    toggleOn("create");
//    window.history.pushState({page: "create"},"Register a company","/user/companies/create");
    return false;
});

$("#settings").on("click",function (e) {
    toggleOff("show");
    toggleOff("create");
    toggleOn("settings");
    e.preventDefault();
  //  window.history.pushState({page: "main"},"Settings","/");
    return false;
});

$("#show").on("click",function (e) {
    e.preventDefault();
    toggleOff("settings");
    toggleOff("create");
    toggleOn("show");
    //window.history.pushState({page: "show"},"My companies","/user/companies");
    return false;
});

function toggleOn(id)
{
    let div = document.getElementById(id + "_wrapper");
    // console.log(id);
    let button = document.getElementById(id);
    button.classList.add("btn-green");
    div.style.visibility = "visible";
    div.style.display = "block";
}

function toggleOff(id)
{
    let div = document.getElementById(id + "_wrapper");
    // console.log(id);
    let button = document.getElementById(id);
    button.classList.remove("btn-green");
    div.style.visiblity = "hidden";
    div.style.display = "none";
}

