$(".ui-accordion-header").click(function() {
    var next = $(this).next();
    var sendMail = next.find(".sendMail");
    if (next.find(".mailable").html() === "true")
        sendMail.removeClass("hidden");
    else
        sendMail.addClass("hidden");
});