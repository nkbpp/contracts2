$(document).ready(function () {
    let itbody = $("body");
    itbody.on('click', 'a', function () {

        if ($(this).attr('id') === "menuitcontract") { //Кнопка ИТ контракты
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/it/vievTable", "", function () {
                let tableContainer = $("#tableItContainer")
                tableContainer.html(getSpinner());
                tableContainer.load("/contract/it/getTable", "", function () {
                    /*Для скролов*/
                    $('.div1').width($('table').width());
                    $('.div2').width($('table').width());
                });
            });
            return false;
        }

        if ($(this).attr('id') === "menuitaddcontract") { //Кнопка Добавить ИТ контракт TODO
            $("#mainContainer").load("/contract/it/add", "", function () {
            });
            return false;
        }

        if ($(this).attr('id') === "deleteItContract") { //Удалить контракт
            if (confirm("Вы точно хотите удалить заявление c порядковым номером = " +
                $(this).parents("#" + $(this).attr('name')).children().eq(0).text())){

                let param = "id="+$(this).attr('name');
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/it/deleteContract',
                    method: 'post',
                    data: param,
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function(data){
                        param = "param=" + activeList("#paginationItContract");//чтобы при удалении осталась текущая страница
                        $("#tableItContainer").load("/contract/it/getTable", param, function () {
                        });
                        initialToats("Удаление прошло успешно",data,"success").show();
                    },
                    error: function(textStatus){
                        initialToats("Ошибка!!!",textStatus,"err").show();
                    }
                });
            }
            return false;
        }


        if ($(this).attr('id') === "updateItContract") { //перейти на вкладку изменения ItContract
            let param = $(this).attr('name');

            $("#mainContainer").load("/contract/it/updateViev/" + param, "", function () {

                    let token = $('#_csrf').attr('content');
                    let header = $('#_csrf_header').attr('content');
                    $.ajax({
                        url: '/contract/it/getContract/' + param,
                        method: 'post',
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function(data){
                            $("#addContractIt").attr("data-id-contract",data.contract.id)
                            $('textarea[name=nomGK]').val(data.contract.nomGK);
                            $('textarea[name=kontragent]').val(data.contract.kontragent);
                            $('input[name=dateGK]').val(data.contract.dateGK);
                            $('input[name=dateGKs]').val(data.contract.dateGKs);
                            $('input[name=dateGKpo]').val(data.contract.dateGKpo);
                            $('input[name=sum]').val(data.contract.sum.replace(',','.'));
                            $('input[name=January]').val(data.contract.January);
                            $('input[name=February]').val(data.contract.February);
                            $('input[name=March]').val(data.contract.March);
                            $('input[name=April]').val(data.contract.April);
                            $('input[name=May]').val(data.contract.May);
                            $('input[name=June]').val(data.contract.June);
                            $('input[name=July]').val(data.contract.July);
                            $('input[name=August]').val(data.contract.August);
                            $('input[name=September]').val(data.contract.September);
                            $('input[name=October]').val(data.contract.October);
                            $('input[name=November]').val(data.contract.November);
                            $('input[name=December]').val(data.contract.December);
                            $('#statusGK').val(data.contract.statusGK);
                            $('#notificationsSelect').val(data.contract.idzirot);

                            $('input[name=sumNaturalIndicators]').val(data.contract.sumNaturalIndicators);
                            $('#range').val(data.contract.naturalIndicatorsSize);
                            //$('#spanVal').text(data.contract.naturalIndicatorsSize);
                            inputAddClassDnone(data.contract.naturalIndicatorsSize);
                            let vvv = data.contract.naturalIndicators.split(";");
                            let inputs = $('#nt').find('input');
                            for (let i = 0; i < vvv.length; i++) {
                                inputs.eq(i).val(vvv[i]);
                            }

                            $('textarea[name=doc]').val(data.contract.doc);

                            for (let doc of data.documents) {
                                let dv = $("#documentsItViev");
                                dv.html(
                                    dv.html() +
                                    "<div class='mb-1' data-id-doc='" + doc.id + "'>"+doc.nameFile + " " +
                                    "<button class='btn btn-secondary btn-sm' " +
                                    "data-id-doc='" + doc.id + "' data-name-doc='delItDoc'>X</button>" +
                                    "</div>"
                                );
                            }

                            $('#addContractIt').text("Изменить");

                        },
                        error: function(textStatus){
                            initialToats("Ошибка!!!",textStatus,"err").show();
                            console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                        }
                    });
            });
            return false;
        }

        //переключатели страниц pagination
        if ($(this).parents("#paginationItContract").attr("id") === "paginationItContract") {
            let list = clickPagination($(this),"#paginationItContract");
            fpagination()
        }

    })

    itbody.on('change','#col',function(){
        fpagination()
    });

    itbody.on('click', 'button', function () {

        if ($(this).attr('name') === "findContractIt") { //поиск
            let param = "poleFindByNomGK=" + $("[data-name=poleFindByNomGK]").val() +
                "&poleFindByKontragent=" + $("[data-name=poleFindByKontragent]").val() +
                "&dateGK=" + $("#poleFindDateGK").val() +
                "&idot=" + $("#poleFindnotificationsSelect").val()
                /*"&poleFindByIspolneno=" + $('[data-name=poleFindByIspolneno]').prop("checked") +
                "&poleFindByNotIspolneno=" + $('[data-name=poleFindByNotIspolneno]').prop("checked")*/;
            console.log("findContract param2 = ", param);
            $("#tableItContainer").load("/contract/it/findTable", param, function (data) {
                console.log("sfsdf");
                clearPagination1();
            });
            return false;
        }

        if ($(this).attr('data-name-doc') === "delItDoc") {
            let id = $(this).attr('data-id-doc');
            let param = "id="+id;
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/it/delItDoc',
                method: 'post',
                data: param,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    $("div[data-id-doc="+id+"]").remove();
                    $("a[data-id-doc="+id+"]").remove();
                    initialToats("Удаление прошло успешно",data,"success").show();
                },
                error: function(textStatus){
                    initialToats("Ошибка при удалении документа!!!",textStatus,"err").show();
                    console.log('ОШИБКИ AJAX запроса при удалении документа: ' + textStatus);
                }
            });
            return false;
        }

        if ($(this).attr('id') === "addContractIt") {
            // проверка заполнения основных полей
            if(
                !$("#nomGK").val().trim()
            ){
                alert("Не все обязательные поля (отмеченные *) заполнены!")
            } else {
                $(this).prop("disabled",true);//делаем кнопку не активной
                $(this).prepend(getSpinnerButton());// крутилкa

                /*let param = $('#formItContract').serialize();
                param += "&id=" + encodeURIComponent($('#addContractIt').attr("data-id-contract"));
                alert(param);*/

                let param = new FormData($('#formItContract')[0]);
                param.append('id', encodeURIComponent($('#addContractIt').attr("data-id-contract")));
                param.append('statusGK', $('#statusGK').val());
                param.append('idzirot', $('#notificationsSelect').val());

                let ntinput = $('#nt').find('.col-3:not(.d-none) input');
                let strntinput = "";
                for (let i = 0; i < ntinput.length; i++) {
                    strntinput += (ntinput.eq(i).val() + (((i+1)!=ntinput.length)?';':''));
                }
                param.append('naturalIndicators', strntinput);

                // Отправляем запрос
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.post({
                    url: "/contract/it/upload",
                    data: param,
                    cache: false,
                    processData: false, // Не обрабатываем файлы (Don't process the files)
                    contentType: false, // Так jQuery скажет серверу что это строковой запрос
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        //после добавления показать таблицу
                        $("#mainContainer").load("/contract/it/vievTable", "", function () {
                            $("#tableItContainer").load("/contract/it/getTable", "", function () {                    /*Для скролов*/
                                $('.div1').width($('table').width());
                                $('.div2').width($('table').width());
                            });
                        });
                        initialToats("Добавление прошло успешно",data,"success").show();
                    },
                    error: function (jqXHR, textStatus) {
                        initialToats("Ошибка при добавлении!!!",jqXHR.responseText,"err").show();
                    }
                });
            }
            return false;
        }
    })



    itbody.on('dblclick', 'td', function () { //Двойной клик в табле

        if($(this).closest('#tableContractIt').length >0){
            //TODO
            let bool = $("#flexCheckChecked").prop("checked");
            if(!bool) { //отключить функционал для натуральных показателей

                let tek = $(this);

                let td = tek.closest('td');

                let span = td.find('span').first();
                let input = td.find('input').first();
                let textarea = td.find('textarea').first();

                let text = span.text();
                if (input.attr('type') === "date") {
                    text = text.split(".").reverse().join("-");
                }

                input.val(text);
                textarea.val(text);
                span.addClass('d-none');
                input.removeClass('d-none');
                textarea.removeClass('d-none');

                input.select();
                textarea.select();
            }
        }

    });

    itbody.on('keypress', 'textarea', function (e) { //изменение на событие клик по enter
        if($(this).closest('#tableContractIt').length >0){
            if(e.keyCode === 13) {
                if($(this).closest($('#tableItContainer')).length>0){
                    $(this).blur();
                }
            }
        }

    });

    //checkBox в таблице
/*    itbody.on('click', 'input', function () {
        if ($(this).attr('id') === "flexCheckChecked") {
            let bool = $(this).prop("checked");
            let list = clickPagination($("#paginationItContract li.active"),"#paginationItContract");
            let param = "param=" + list;
            if(bool){
                $("#tableItContainer").load("/contract/it/getTable2", param, function (data) {
                });
            }else{
                $("#tableItContainer").load("/contract/it/getTable", param, function (data,f) {
                });
            }
            activePagination($("#paginationItContract a"), list)
        }
    });*/

    itbody.on('focus', 'input', function () { //выбор всего текста при получении фокуса
        if($("#formItContract").find($("#"+$(this).attr('id')+"[type=number]")).length>0 &&
            !$(this).closest($('caption')).length>0){
            this.select();
        }
    })

    itbody.on('keypress', 'input', function (e) { //изменение на событие клик по enter
        if(e.keyCode === 13) {
            if($(this).closest($('#tableItContainer')).length>0){
                $(this).blur();
            }
        }
    });

    itbody.on('blur', 'input', function () { //изменение на событие потери фокуса input
        if($(this).closest($('#tableItContainer')).length>0 &&
            !$(this).closest($('caption')).length>0){

            let tek = $(this);

            let tr = tek.closest('tr');
            let td = tek.closest('td');

            let span = td.find('span').first();
            let input = td.find('input').first();

            let text = input.val();
            if(input.attr('type')==="date"){
                text = text.split("-").reverse().join(".");
            } else if(input.attr('type')==="number"){
                let regExp = '[.]+';
                if(text.match(regExp) == null) {
                    text += '.00';
                }
            }
            span.text(text);
            input.addClass('d-none');
            span.removeClass('d-none');

            // Отправляем запрос

            let param = getParamItContractFormData(tr);

            updateItContract(param);

            let val = 0; //подсчет остатка
            let tdNumbers = tr.find('td[name]:has(input[type=number])');
            let maxIndex = tdNumbers.length;
            tdNumbers
                .each(function (index, el){
                    let t = $(el).text().trim();
                    if(+index===+0){
                        val+=t;
                    } else {
                        val-=t;
                    }
                    if (+index===+(maxIndex-1)){ //запись в поле остаток
                        let regExp = '[.]+';
                        if((val+'').match(regExp) == null) {
                            val += '.00';
                        }
                        ($(el).closest('td')).next().text(val.toFixed(2));//2 знака после запятой
                    }
                });
            return false;
        }
    });

    itbody.on('input','input', function() { //range
        if($(this).attr('id')==='range'){
            let value = +$(this).val();
            inputAddClassDnone(value);
        }
    });

    itbody.on('blur', 'textarea', function () { //изменение на событие потери фокуса textarea
        if($(this).closest($('#tableItContainer')).length>0){

            let tek = $(this);

            let tr = tek.closest('tr');
            let td = tek.closest('td');

            let span = td.find('span').first();
            let textarea = td.find('textarea').first();

            let text = textarea.val();

            span.text(text);
            textarea.addClass('d-none');
            span.removeClass('d-none');

            // Отправляем запрос
            let param = getParamItContractFormData(tr);

            updateItContract(param);

            return false;
        }
    });

})

function updateItContract(param){

    // Отправляем запрос
    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');
    $.post({
        url: "/contract/it/upload",
        data: param,
        cache: false,
        processData: false, // Не обрабатываем файлы (Don't process the files)
        contentType: false, // Так jQuery скажет серверу что это строковой запрос
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            initialToats("Изменение прошло успешно",data,"success").show();
        },
        error: function (jqXHR, textStatus) {
            initialToats("Ошибка при изменении!!!",jqXHR.responseText,"err").show();
            console.log(jqXHR.responseText);
        }
    });
}

function getParamItContract(tr){
    let id = tr.attr('name');
    let param = "id="+id;
    let tds = tr.find('td[name]');
    tds.each(function (index, el){
        let e = $(el);
        let val = encodeURIComponent(e.text().trim());

        param+=("&"+e.attr('name')+"="+val);
    });
    return param;
}

function getParamItContractFormData(tr){
    let id = tr.attr('name');

    // создание объекта FormData
    let formData = new FormData();
    formData.append('id',id);

    let tds = tr.find('td[name]');
    tds.each(function (index, el){
        let e = $(el);
        let val = e.text().trim();

        formData.append(e.attr('name'),val);
    });

    formData.append('itDocuments',new File([], ""));

    return formData;
}

function inputAddClassDnone(value) {
    $('#spanVal').text(value)
    let inputs = $('#nt').find('.col-3');
    for (let i = 0; i < inputs.length; i++) {
        if(i<value)
            inputs.eq(i).removeClass('d-none');
        else
            inputs.eq(i).addClass('d-none');
    }
}

function fpagination(){

    let param = "param=" + activeList("#paginationItContract") + "&col=" + $("#col").val();

    let bool = $("#flexCheckChecked").prop("checked");
    if(bool){
        $("#tableItContainer").load("/contract/it/getTable2", param, function (data) {
        });

    }else{
        $("#tableItContainer").load("/contract/it/getTable", param, function (data,f) {
        });
    }

}