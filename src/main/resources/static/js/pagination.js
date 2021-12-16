function clickPagination(selA, idpag) {
    let list;
    let pag = $(idpag + " a");
    //кнопка назад
    if (selA.attr('name') === "backPagination") {
        clearPagination(pag);
        list = 1;
    } else //кнопка далее
    if (selA.attr('name') === "nextPagination") {
        list = +pag.eq(5).text() + 1;
        pag.first().parent().removeClass("disabled");
        pag.eq(1).text(list).parent().removeClass("active").addClass("active");
        pag.eq(2).text(+list + 1).parent().removeClass("active");
        pag.eq(3).text(+list + 2).parent().removeClass("active");
        pag.eq(4).text(+list + 3).parent().removeClass("active");
        pag.eq(5).text(+list + 4).parent().removeClass("active");
    } else { //кнопки с цифрами
        $(idpag + " li.active").removeClass("active");
        selA.parent().addClass("active");
        list = selA.text();
        if (list == 1) {
            pag.first().parent().removeClass("disabled").addClass("disabled");
        } else {
            $(idpag + " li.disabled").removeClass("disabled");
        }
    }
    return list;
}

function clearPagination(pag) {
    pag.first().parent().removeClass("disabled").addClass("disabled");
    pag.eq(1).text("1").parent().removeClass("active").addClass("active");
    pag.eq(2).text("2").parent().removeClass("active");
    pag.eq(3).text("3").parent().removeClass("active");
    pag.eq(4).text("4").parent().removeClass("active");
    pag.eq(5).text("5").parent().removeClass("active");
}

function activeList(idpag) {
/*    let list;
    let text = $(idpag+" li.active").text();
    //console.log("text=",text)
    if (text === "Назад") {
        list = 1;
    } else //кнопка далее
    if (text === "Далее") {
        list = +$(idpag+" a").eq(5).text() + 1;
    } else {
        list=text
    }*/
    //console.log("activeList= " + list);
    return $(idpag+" li.active").text();
}