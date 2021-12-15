$(function(){
	// 메인팝업 스와아퍼
	if($('.main_pop .swiper-slide').length > 1){
		var mainPopSwiper = new CustomSwiper('.main_pop', {
			slidesPerView: '1',
			loop: true,
			speed: 500,
			navigation: {
				nextEl: '.main_pop .swiper-button-next',
				prevEl: '.main_pop .swiper-button-prev',
			},
			pagination: {
				el: '.main_pop .swiper-pagination',
				type: 'fraction',
				renderFraction: function(currentClass, totalClass){
					return '<span class="' + currentClass + '"></span>' + '<span class="' + totalClass + '"></span>';
				},
				formatFractionCurrent: function (number) {
					return KyoboBookPub.ink.setPrependZero(number, 2);
				},
				formatFractionTotal: function (number) {
					return KyoboBookPub.ink.setPrependZero(number, 2);
				},
			},
		});		
	}else{
		$('.main_pop .swiper-button-next').remove();
		$('.main_pop .swiper-button-prev').remove();
		$('.main_pop .swiper-pagination').remove();
	}
	

	//이미지 크게보기 스와이퍼
	var galleryThumbs = new CustomSwiper('.gallery_thumbs', {
		slidesPerView: 'auto',
		watchSlidesVisibility: true,
		watchSlidesProgress: true,
		navigation: {
			nextEl: '.gallery_thumbs_wrap .swiper-button-next',
			prevEl: '.gallery_thumbs_wrap .swiper-button-prev',
		}
	});

	if($('.gallery_thumbs .swiper-slide').length > 7){
		$('.gallery_thumbs .swiper-wrapper').removeClass('align_center');
	}else{
		$('.gallery_thumbs_wrap .swiper-button-next').remove();
		$('.gallery_thumbs_wrap .swiper-button-prev').remove();
		$('.gallery_thumbs .swiper-wrapper').addClass('align_center');
	}
	
	if($('.gallery_top .swiper-slide').length > 1){
		var galleryTop = new CustomSwiper('.gallery_top', {  
			navigation: {
				nextEl: '.gallery_top_wrap .swiper-button-next',  
				prevEl: '.gallery_top_wrap .swiper-button-prev',  
			},  
			thumbs: {  
				swiper: galleryThumbs,  
			}, 
	
			pagination: {
				el: '.gallery_top .swiper-pagination',
				type: 'fraction',
				renderFraction: function(currentClass, totalClass){
					return '<span class="' + currentClass + '"></span>' + '<span class="' + totalClass + '"></span>';
				},
				formatFractionCurrent: function (number) {
					return KyoboBookPub.ink.setPrependZero(number, 2);
				},
				formatFractionTotal: function (number) {
					return KyoboBookPub.ink.setPrependZero(number, 2);
				},
			},
		});
		
	}else{
		$('.gallery_top_wrap .swiper-button-next').remove();
		$('.gallery_top_wrap .swiper-button-prev').remove();
		$('.gallery_top .swiper-pagination').remove();
	}
	


	// 이미지 크게보기 팝업 > 이미지 가로형/세로형 구분
	$('#popImgZoom').on('dialogopen', function(){
		$('.portrait_img_box').each(function(){
			var imgTg = $(this);
			KyoboBookPub.ink.checkImagePortrait(imgTg);
		});
	});
});