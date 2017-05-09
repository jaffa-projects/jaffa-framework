/**
 * @class Jaffa.state.PageLoader
 * This class is used to render a mask while a page is being loaded.
 */
Jaffa.state.PageLoader = {
  /** id for the mask div element. */
  maskId: 'jaffa-page-loading-mask',
  
  /** id for the container div element. */
  containerId: 'jaffa-page-loading-container',
  
  /** class to be specified for the div element that displays the message. */
  indicatorClass: 'jaffa-page-loading-indicator',
  
  /**
   * Masks the entire page, displaying the input message in the center.
   * If the page is already masked, then the earlier message will be replaced by the new input message.
   */
  mask: function (msg) {
    var element = Ext.get(this.containerId);
    if (element) {
      element.child('div.' + this.indicatorClass, true).innerHTML = msg;
    } else {
      document.write("<div id='" + this.maskId + "'></div>");
      document.write("<div id='" + this.containerId + "'><div class='" + this.indicatorClass + "'>" + msg + "</div></div>");
    }
  },
  
  /** 
   * Removes the mask, that was applied earlier.
   * If a mask does not exist, then nothing will be done.
   */
  unmask: function () {
    var element;
    
    element = Ext.get(this.containerId)
    if (element)
      element.remove();
    
    element = Ext.get(this.maskId)
    if (element)
      element.fadeOut({remove: true, duration: 1, easing: 'easeIn'});
  },
  
  /** Returns true if a mask is currently applied on the page. */
  isActive: function () {
    return Ext.get(this.containerId) != null;
  }
};
