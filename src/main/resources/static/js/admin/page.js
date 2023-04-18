var $prev = $($(".page-button")[0]);
	
$(".page-button").each((i, e) => {
       var $pageNumber = $(e);
       
       $pageNumber.on("click", function(e) {
          e.preventDefault();
          if($prev == $pageNumber){
              return;
          }
          
          $prev.removeClass("page-button-active");
          $pageNumber.addClass("page-button-active");
          
          $prev = $pageNumber;
       });
    }); 
