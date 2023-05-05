// Значки для сортировки :
var sortAsc1 = document.createElement("SPAN"),
    sortDesc1 = document.createElement("SPAN"),
    sortRules = [
        null,
        null,
        ["number"],     // Сортировать по первому числу строки (целое или десятичная дробь)
        null            // Здесь будет использоваться алгоритм по-умолчанию (правило string)
    ];

// Значки сортировки для заголовка таблицы:
sortAsc1.innerHTML = "&#9660;";
sortDesc1.innerHTML = "&#9650;";

// Вешаем сортировку на таблицу:
//console.log($("#tab").get(0));

sortTable($("#tab").get(0), sortAsc1, sortDesc1, sortRules);

$("th span span").each(function () {
    $(this).addClass("btn");
});