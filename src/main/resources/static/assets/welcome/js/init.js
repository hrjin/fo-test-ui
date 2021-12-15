/* eslint-disable no-var */
/**
 * [ES5] welcome/index.html 초기화 스크립트
 */
$(function () {
  console.log('* [loaded] /assets/welcome/js/init.js')

  // override init event callback
  KbbJS.setOption('events.core.init', function () {
    console.log('* [welcome/js/init.js] call - events.core.init')

    var bMobile = window.location.href.indexOf('/mok') > -1

    var SWIPER_DEMO_SLT = '.mySwiper'
    if ($(SWIPER_DEMO_SLT).length) {
      var type = bMobile ? 'mok' : 'ink'
      var prop = ['service.welcome.swiper', type, 'demo'].join('.')
      // console.log(type, prop)
      var opts = this.getGlobalOption(prop)

      /* eslint-disable-next-line no-unused-vars */
      var swiper = new Swiper(SWIPER_DEMO_SLT, opts)
    }
  })
}())
