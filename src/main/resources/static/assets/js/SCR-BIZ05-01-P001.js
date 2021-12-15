/*
 * name : SCR-BIZ05-01-P001.js
 * desc : 상품 상세(내서) 전용 실행 자바스크립트
 * writer : glim
 * create : 2021/11/30
 * update :
 * -
 */

$(function(){
	// 뷰 토글 버튼 이벤트 설정
	KyoboBookPub.ink.setSwitchListBtn(function(swiper, type) {
		console.log(swiper[0].swiper);
	});


	// 상품 상단 - 알림신청 영역 텍스트 swiper
	if($('.alarm_apply_swiper_wrap .swiper-container .swiper-slide').length > 1){
		var alarmApplySwiper = new CustomSwiper('.alarm_apply_swiper_wrap .swiper-container', {
			direction: "vertical",
			slidesPerView: 'auto',
			loop: true,
			speed: 500,
			autoplay: {
				delay: 2000,
			},
		});
	}

	// 패키지 swiper
	$('.slide_list_package').each(function() {
		var packageSwiper = new CustomSwiper(this, {
			slidesPerView: 'auto',
			speed: 500,
			freeMode: true,
			scrollbar: {
				el: $(this).find('.swiper-scrollbar')[0],
			},
		});
	})

	// 북카드
	var bookCardSwiper = new CustomSwiper('.book_card_swiper .swiper-container', {
		slidesPerView: 'auto',
		slidesPerGroup: 2,
		loop: true,
		speed: 500,
		navigation: {
			nextEl: '.book_card_swiper .swiper-button-next',
			prevEl: '.book_card_swiper .swiper-button-prev',
		},
	});

	// 함께 구매한 책
	var withThisSwiper = new CustomSwiper('.slide_list_with_this', {
		slidesPerView: 'auto',
		speed: 500,
		freeMode: true,
		scrollbar: {
			el: $('.slide_list_with_this').find('.swiper-scrollbar')[0],
		},
	});

	// 이 책의 시리즈
	var bookSeriesSwiper = new CustomSwiper('.slide_book_series', {
		slidesPerView: 'auto',
		speed: 500,
		freeMode: true,
		scrollbar: {
			el: $('.slide_book_series').find('.swiper-scrollbar')[0],
		},
	});
	// 이 책의 시리즈
	var bookEditionSwiper = new CustomSwiper('.slide_book_edition', {
		slidesPerView: 'auto',
		speed: 500,
		freeMode: true,
		scrollbar: {
			el: $('.slide_book_edition').find('.swiper-scrollbar')[0],
		},
	});

	// 소개된 책
	var introduceBookSwiper = new CustomSwiper('.slide_prod_introduced', {
		slidesPerView: '1',
		speed: 500,
		navigation: {
			nextEl: $('.control_prod_introduced').find('.swiper_control_box .swiper-button-next')[0],
			prevEl: $('.control_prod_introduced').find('.swiper_control_box .swiper-button-prev')[0],
		},
		pagination: {
			el: $('.slide_prod_introduced').siblings('.title_wrap').find('.swiper_control_box .swiper-pagination')[0],
			type: "fraction",
			formatFractionCurrent: function (number) {
				return KyoboBookPub.ink.setPrependZero(number, 2);
			},
			formatFractionTotal: function (number) {
				return KyoboBookPub.ink.setPrependZero(number, 2);
			},
		},
	});

	// 이 책의 연관상품
	var linkedProdSwiper = new CustomSwiper('.slide_prod_linked', {
		slidesPerView: '1',
		speed: 500,
		navigation: {
			nextEl: $('.control_prod_linked').find('.swiper_control_box .swiper-button-next')[0],
			prevEl: $('.control_prod_linked').find('.swiper_control_box .swiper-button-prev')[0],
		},
		pagination: {
			el: $('.control_prod_linked').find('.swiper_control_box .swiper-pagination')[0],
			type: "fraction",
			formatFractionCurrent: function (number) {
				return KyoboBookPub.ink.setPrependZero(number, 2);
			},
			formatFractionTotal: function (number) {
				return KyoboBookPub.ink.setPrependZero(number, 2);
			},
		},
	});

	// 이 책의 시리즈
	var writerBooksSwiper = new CustomSwiper('.slide_writer_books', {
		slidesPerView: 'auto',
		speed: 500,
		freeMode: true,
		scrollbar: {
			el: $('.slide_writer_books').find('.swiper-scrollbar')[0],
		},
	});

	// 문장수집
	var killingPartSwiper = new CustomSwiper('.killing_part_swiper .swiper-container', {
		slidesPerView: 'auto',
		loop: true,
		speed: 500,
		navigation: {
			nextEl: '.killing_part_swiper .swiper-button-next',
			prevEl: '.killing_part_swiper .swiper-button-prev',
		},
	});

	var recommendEventSwiper = new CustomSwiper('.recommend_event_list', {
		slidesPerView: 'auto',
		speed: 500,
		spaceBetween: 16,
		freeMode: true,
		scrollbar: {
			el: $('.recommend_event_list').find('.swiper-scrollbar')[0],
		},
	});
});


