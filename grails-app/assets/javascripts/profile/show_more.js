document.onclick = function(e)
{
    e.preventDefault();
    let link = e.target;
    const detail_id = link.parentElement.id + "_details";
    let detail_row = document.getElementById(detail_id);
    if(!detail_row) {
        return false;
    }
    if(link.innerText === "Show more"){
        link.innerText = "hide";
        detail_row.style.width = "100%";
        detail_row.style.padding = "1px 1px 1px 1px";
        detail_row.style.textAlign = "left";
        detail_row.style.visibility = "visible";
        detail_row.style.display = "table-row";
        return false;
    }
    if(link.innerText === "hide"){
        link.innerText = "Show more";
        detail_row.style.visibility = "hidden";
        detail_row.style.display = "none";
        return false;
    }
};