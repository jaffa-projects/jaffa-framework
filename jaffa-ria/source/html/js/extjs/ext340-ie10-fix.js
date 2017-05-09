(function(){
  var ua = navigator.userAgent.toLowerCase(),
      check = function(r) {
          return r.test(ua);
      };
  Ext.isIE10 = Ext.isIE && check(/msie 10/);
  if (Ext.isIE10) {
    Ext.isIE6 = false;
    Ext.isIE9 = true;
  }
})();
