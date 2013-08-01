$(document).ready(function() { 
	$(".list-services .tooltips").easyTooltip();
}); 

$(window).load(function() {
	$('#bgSlider').bgSlider({
		duration:1000,
		pagination:'.pagination',
		preload:true,
		slideshow:30000,
		spinner:'.bg_spinner'
	});
});


//main menu toggle
$(document).ready(function(){	
    $("ul.menu li.father").hover(function() {
        $(this).find(".submenu").slideDown(200);
    }, function() {
        $(this).find(".submenu").hide();
    });
});
