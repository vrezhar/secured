document.onclick = function(e)
{
    let link = e.target;
    const detail_id = link.parentElement.id + "_details";
    const detail_description_id = link.parentElement.id + "_description";
    let detail_row = document.getElementById(detail_id);
    let description_row = document.getElementById(detail_description_id);
    if(!detail_row) {
        return true;
    }
    e.preventDefault();
    if(link.innerText === "Show more"){
        link.innerText = "hide";
        detail_row.style.width = "100%";
        detail_row.style.padding = "1px 1px 1px 1px";
        detail_row.style.textAlign = "left";
        detail_row.style.visibility = "visible";
        detail_row.style.display = "table-row";
        description_row.style.width = "100%";
        description_row.style.padding = "1px 1px 1px 1px";
        description_row.style.textAlign = "left";
        description_row.style.visibility = "visible";
        description_row.style.display = "table-row";
        return false;
    }
    if(link.innerText === "hide"){
        link.innerText = "Show more";
        detail_row.style.visibility = "hidden";
        detail_row.style.display = "none";
        description_row.style.visibility = "hidden";
        description_row.style.display = "none";
        return false;
    }
};