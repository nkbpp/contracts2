/*
$(window).on('load', function (e) {
    $('.div1').width($('table').width());
    $('.div2').width($('table').width());
});*/
document.addEventListener('scroll', function (event) {
    if (event.target.id === 'wrapper1') {
        $('.wrapper2').scrollLeft($('.wrapper1').scrollLeft());
    }
    if (event.target.id === 'wrapper2') {
        $('.wrapper1').scrollLeft($('.wrapper2').scrollLeft());
    }
    }, true /*Capture event*/);