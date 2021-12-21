$(document).ready(function () {
    $("#mainContainer").load("/contract/main/stat", "", function () {
    })
    $("#body").on('click', 'a', function () {
        if ($(this).attr('id') === "statAll") { //Статистика показать все
            loadMenuView();
            return false;
        }
        if ($(this).attr('id') === "statIspolneno") { //Статистика показать исполненые
            loadMenuView("statIspolneno=1");
            return false;
        }
        if ($(this).attr('id') === "statNotIspolneno") { //Статистика показать не исполненые
            loadMenuView("statNotIspolneno=1");
            return false;
        }

        if ($(this).attr('id') === "statNotIspolnenoSrok") { //Статистика показать не исполненые приближается срок исполнения
            loadMenuView("statNotIspolnenoSrok=1");
            return false;
        }
        if ($(this).attr('id') === "statNodate") { //Статистика показать без даты
            loadMenuView("statNodate=1");
            return false;
        }
        if ($(this).attr('id') === "statProsrocheno") { //Статистика показать просроченные
            loadMenuView("statProsrocheno=1");
            return false;
        }
    })
})


