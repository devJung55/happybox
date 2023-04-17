$(".triangle-button").each((i, e) => {
		
    var index = i;
    var $dom = $(e);
    var $menus = $($(".menus")[index]);   
    let checkSlide;
    
    $dom.on("click", function(e) {
       e.preventDefault();
       console.log($menus);
       
       if(checkSlide){
          $dom.removeClass("triangle-acitve");
          $menus.slideUp();
          checkSlide = false;
       } else{
          $dom.addClass("triangle-acitve");
          $menus.slideDown();
          checkSlide = true;
       }
    });
 });