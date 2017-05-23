/**
 *  依赖于jQuery、imageloaded、wookmark
 */
var waterfall = {
	
	init: function(params) {
		var imageParentDiv = params['imageParent'];
		var containerDiv = params['container'];
		var imageCount = params['imageCount'];
		
		$('#'+imageParentDiv).imagesLoaded().done( function() {
			 var align = 'left';
			 if(imageCount > 5) align = 'center';
			 setTimeout(function(){
				 $('#'+imageParentDiv+' div.waterfall').wookmark({
				  container: $('#'+containerDiv),
				  direction :'left',
				  align:align,
				  offset: 20,
				  verticalOffset:15,
				  itemWidth: 225,
				  outerOffset:15,
				  autoResize:true
				});
			},1000)
			setTimeout(function(){
				var height = $('#'+containerDiv).height();
				$('#'+containerDiv).height(height+50);
				$('#'+imageParentDiv).height(height+50);
				$('.inav').show();
			},1500);
			setTimeout(function(){
				maskHide();
			},1500);
		});
	}
}