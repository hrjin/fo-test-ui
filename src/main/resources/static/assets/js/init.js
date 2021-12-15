/* eslint-disable no-var */
/* globals KbbJS */
console.log('** [product] /js/init.js')

// IIFE
;(function () {
  function titleHandle (data) {
    var $titleWrap
    $(function () {
      $titleWrap = $('.prod_title_area>.prod_title_box')
      var $inner = $titleWrap.find('.auto_overflow_contents>.auto_overflow_inner')
      // var labels = _.get(data, 'headTitle.title.labels')
      // console.log(labels)
      var nLabelCnt = 0
      var aLabels = _.get(data, 'headTitle.title.labels')
        .map(function (str, ndx) {
          var arr = []
          if (nLabelCnt % 2) {
            arr.push($('<span/>', {
              class: 'gap',
              text: '·'
            }))
          }
          arr.push($('<span/>', {
            class: 'prod_label',
            text: str
          }))
          nLabelCnt += 1
          return arr
        })
      // console.log($labels)
      var aChild = _.concat(
        aLabels.flat(),
        $('<span/>', {
          class: 'prod_alias',
          text: _.get(data, 'headTitle.title.alias')
        }),
        $('<span/>', {
          class: 'prod_title',
          text: _.get(data, 'headTitle.title.value')
        })
      )
      $inner.empty().append(aChild)

      // <span class="prod_label">해외주문</span>
      // <!--<span class="gap">·</span>-->
      // <!--<span class="prod_label">POD</span>-->
      // <!--<span class="gap">·</span>-->
      // <!--<span class="prod_label">예약판매</span>-->
      // <span class="prod_alias">알기쉬운</span>
      // <span class="prod_title">작별하지 않는다</span>
    })
  }

  KbbJS.ready('util', function () {
    this.fetch('/assets/data/dummy/SCR-BIZ05-01-P001.json5').then(titleHandle)
  })
}())
