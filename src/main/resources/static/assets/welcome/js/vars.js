/* eslint-disable no-var */
/**
 * [ES5] Welcome 공통변수 정의
 */
(function () {
  console.log('* [loaded] /assets/welcome/js/welcome-vars.js')

  var swiperOptions = {
    // ink: { swiperDemo: {} },
    // mok: { swiperDemo: {} }
  }

  /**
   * [INK] Swiper Demo Options
   * https://codesandbox.io/s/reie4?file=/index.html
   */
  _.set(swiperOptions, 'ink.demo', {
    effect: 'coverflow',
    grabCursor: true,
    centeredSlides: true,
    slidesPerView: 'auto',
    coverflowEffect: {
      rotate: 50,
      stretch: 0,
      depth: 100,
      modifier: 1,
      slideShadows: true
    },
    pagination: {
      el: '.swiper-pagination'
    }
  })

  /**
   * [MOK] Swiper Demo Options
   * https://codesandbox.io/s/f2iew?file=/index.html
   */
  _.set(swiperOptions, 'mok.demo', {
    effect: 'flip',
    grabCursor: true,
    pagination: {
      el: '.swiper-pagination'
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev'
    }
  })

  KbbJS.setOption('service.welcome.swiper', swiperOptions)
}())
