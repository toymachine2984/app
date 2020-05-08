$(window).scroll(function () {
    var scroll = $(window).scrollTop();
    if (scroll >= 10) {
        $(".bar").removeClass("scroll");
    } else {
        $(".bar").addClass("scroll");
    }
});