/*
 * name : ui_product_ink.js
 * desc : 상품 공통 자바스크립트
 * writer : glim
 * create : 2021/11/30
 * update :
 * -
 */

$(function () {
    // 상품 정보 영역 swiper
    function prodInfoThumbSwiper() {
        var prodThumbContainer = $('.prod_thumb_swiper_wrap');

        $('.portrait_img_box').each(function () {
            var imgTg = $(this);
            var imgContainer = $(this).closest('.blur_img_wrap');
            KyoboBookPub.ink.checkImagePortrait(imgTg, imgContainer);
        });

        $('.blur_img_wrap').each(function () {
            if ($(this).hasClass('landscape')) {
                new blurify({
                    images: $(this).find('.blur_img_box img'),
                    blur: 6,
                    mode: 'auto',
                });
            }
        });

        var prodThumbSwiper;
        if ($('.swiper-slide', prodThumbContainer).length > 1) {
            prodThumbContainer.addClass('active');
            prodThumbSwiper = new CustomSwiper(prodThumbContainer.find('.swiper-container'), {
                init: false,
                slidesPerView: 1,
                effect: 'fade',
                navigation: {
                    nextEl: prodThumbContainer.find('.swiper_control_box .swiper-button-next')[0],
                    prevEl: prodThumbContainer.find('.swiper_control_box .swiper-button-prev')[0],
                },
                pagination: {
                    el: prodThumbContainer.find('.swiper_control_box .swiper-pagination')[0],
                    type: 'fraction',
                    formatFractionCurrent: function (number) {
                        return KyoboBookPub.ink.setPrependZero(number, 2);
                    },
                    formatFractionTotal: function (number) {
                        return KyoboBookPub.ink.setPrependZero(number, 2);
                    },
                },
            });

            prodThumbSwiper.init();

            prodThumbSwiper.on('slideChange', function () {
                if (prodThumbSwiper.activeIndex === 0) {
                    $('.btn_swiper_floating.prev', prodThumbContainer).addClass('disabled');
                    $('.btn_swiper_floating.next', prodThumbContainer).removeClass('disabled');
                } else if (prodThumbSwiper.activeIndex === $('.swiper-slide', prodThumbContainer).length - 1) {
                    $('.btn_swiper_floating.next', prodThumbContainer).addClass('disabled');
                    $('.btn_swiper_floating.prev', prodThumbContainer).removeClass('disabled');
                } else {
                    $('.btn_swiper_floating.next', prodThumbContainer).removeClass('disabled');
                    $('.btn_swiper_floating.prev', prodThumbContainer).removeClass('disabled');
                }
            })

            $('.btn_swiper_floating', prodThumbContainer).on('click', function () {
                if ($(this).hasClass('prev')) {
                    $(this).siblings().removeClass('disabled');

                    if ($(this).hasClass('disabled')) $(this).removeClass('disabled');
                    prodThumbSwiper.slideTo(prodThumbSwiper.activeIndex - 1);

                    if (prodThumbSwiper.activeIndex === 0) {
                        $(this).addClass('disabled');
                    }
                } else {
                    $(this).siblings().removeClass('disabled');

                    if ($(this).hasClass('disabled')) $(this).removeClass('disabled');
                    prodThumbSwiper.slideTo(prodThumbSwiper.activeIndex + 1);

                    if (prodThumbSwiper.activeIndex === $('.swiper-slide', prodThumbContainer).length - 1) {
                        $(this).addClass('disabled');
                    }
                }
            });
        } else {
            $('.btn_swiper_floating', prodThumbContainer).remove();
            $('.swiper_control_box', prodThumbContainer).remove();
        }

        prodThumbContainer.on({
            'mouseenter': function () {
                prodThumbContainer.addClass('hover');
            },
            'mouseleave': function () {
                prodThumbContainer.removeClass('hover');
            }
        });

        // 리뷰 내 리뷰 썸네일 swiper
        $(".review_swiper .swiper-container").each(function (index, element) {
            var reviewSwiper = new CustomSwiper(this, {
                observer: true,
                observeParents: true,
                slidesPerView: 'auto',
                loop: true,
                navigation: {
                    nextEl: $(this).siblings('.swiper-button-next'),
                    prevEl: $(this).siblings('.swiper-button-prev'),
                },
            });
        });

    }

    // 상품 상단 - 리뷰 영역 선택 시 하단 리뷰로 이동
    function reviewTabAnchor() {
        if ($('.prod_review_box .btn_go_review').length > 0) {
            var reviewOffsetTop;
            reviewOffsetTop = Math.floor($('.klover_review_wrap').offset().top) - 71;

            $('.prod_review_box .btn_go_review').on('click', function () {
                $('html, body').stop().animate({
                    scrollTop: reviewOffsetTop
                }, 300);
            });
        }
    }

    // 상품상세 앵커탭 기능
    function setProdDetailAnchor() {
        if ($('.tab_wrap.prod_detail_body').length > 0) {
            var _tabLinks;
            _tabLinks = $('.tab_wrap.prod_detail_body > .tab_list_wrap .tabs .tab_item .tab_link');

            // 옵션영역 펼치기
            $('.btn_option_more', '.prod_option_info_wrap').on('click', function () {
                var optionSelectBox = $(this).closest('.prod_option_info_wrap');

                if (optionSelectBox.hasClass('active')) {
                    optionSelectBox.removeClass('active');
                    $(this).find('.hidden').text('옵션 선택 영역 접기');
                } else {
                    optionSelectBox.addClass('active');
                    $(this).find('.hidden').text('옵션 선택 영역 펼치기');
                }
            });

            // 상품 상세 탭 링크 클릭시 해당 위치로 이동
            _tabLinks.on('click.product', function (event) {
                var targetId, offsetTop;
                event.preventDefault();

                targetId = event.currentTarget.getAttribute('href');
                offsetTop = $(targetId).offset().top - 71;
                $('html, body').stop().animate({
                    scrollTop: offsetTop
                }, 300);
            });

            // 상세 컨텐츠 블럭별 class 값 변경 Observer
            var observer = new MutationObserver(function (mutations) {
                mutations.forEach(function (mutation) {
                    if (mutation.attributeName === 'class') {
                        var target, currentClassList;
                        target = mutation.target;
                        currentClassList = target.classList.value;
                        if (target.dataset.prevClass !== currentClassList) {
                            target.dataset.prevClass = currentClassList;

                            setTabBtnActive();
                        }
                    }
                });
            });

            // 스크롤에 따라 탭 active 상태 변경
            function setTabBtnActive() {
                var activeIndex;
                activeIndex = $('.prod_detail_contents .tab_content.sps-blw').length - 1;

                _tabLinks.parent().removeClass('active');
                if (activeIndex !== -1) {
                    _tabLinks.eq(activeIndex).parent().addClass('active');
                }
            }

            document.querySelectorAll('.prod_detail_contents .tab_content.sps').forEach(function (target) {
                target.dataset.prevClass = target.classList;
                observer.observe(target, {
                    attributes: true
                });
            });
        }
    }

    setProdDetailAnchor();
    try {
        reviewTabAnchor()
    } catch (err) {}
    prodInfoThumbSwiper();
});
