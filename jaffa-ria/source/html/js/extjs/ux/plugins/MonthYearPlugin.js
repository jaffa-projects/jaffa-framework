/*!
 * Copyright(c) 2009 Costin Bereveanu, KEYPOINT SOLUTIONS
 * costin@keypoint.ro
 * http://www.keypoint.ro/ext/extensions/license.txt
 * 
 * Copyright (c) 2009, Costin Bereveanu (costin@keypoint.ro)
 * All rights reserved.
  
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
  
      * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
      * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
      * Neither the name of KEYPOINT SOLUTIONS nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
  
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Obtained from http://www.sencha.com/forum/showthread.php?74002-3.x-Ext.ux.MonthMenu&p=356860&viewfull=1#post356860
 * 
 * @Usage: create a regular datefield, set the format to something like 'm/Y', and set the plugins property to 'monthYearPickerPlugin'
 */

Ext.ux.MonthYearPickerPlugin = function() {
  var picker;
  var oldDateDefaults;

  this.init = function(pk) {
    picker = pk;
    picker.onTriggerClick = picker.onTriggerClick.createSequence(onClick);
    picker.getValue = picker.getValue.createInterceptor(setDefaultMonthDay).createSequence(restoreDefaultMonthDay);
    picker.beforeBlur = picker.beforeBlur.createInterceptor(setDefaultMonthDay).createSequence(restoreDefaultMonthDay);
  };

  function setDefaultMonthDay() {
    oldDateDefaults = Date.defaults.d;
    Date.defaults.d = 1;
    return true;
  }

  function restoreDefaultMonthDay(ret) {
    Date.defaults.d = oldDateDefaults;
    return ret;
  }

  function onClick(e, el, opt) {
    var p = picker.menu.picker;
    p.activeDate = p.activeDate.getFirstDateOfMonth();
    if (p.value) {
      p.value = p.value.getFirstDateOfMonth();
    }

    p.showMonthPicker();

    if (!p.disabled) {
      p.monthPicker.stopFx();
      p.monthPicker.show();

      p.mun(p.monthPicker, 'click', p.onMonthClick, p);
      p.mun(p.monthPicker, 'dblclick', p.onMonthDblClick, p);
      p.onMonthClick = p.onMonthClick.createSequence(pickerClick);
      p.onMonthDblClick = p.onMonthDblClick.createSequence(pickerDblclick);
      p.mon(p.monthPicker, 'click', p.onMonthClick, p);
      p.mon(p.monthPicker, 'dblclick', p.onMonthDblClick, p);
    }
  }

  function pickerClick(e, t) {
    var el = new Ext.Element(t);
    if (el.is('button.x-date-mp-cancel')) {
      picker.menu.hide();
    } else if (el.is('button.x-date-mp-ok')) {
      var p = picker.menu.picker;
      p.setValue(p.activeDate);
      p.fireEvent('select', p, p.value);
    }
  }

  function pickerDblclick(e, t) {
    var el = new Ext.Element(t);
    if (el.parent() && (el.parent().is('td.x-date-mp-month') || el.parent().is('td.x-date-mp-year'))) {

      var p = picker.menu.picker;
      p.setValue(p.activeDate);
      p.fireEvent('select', p, p.value);
    }
  }

};

Ext.preg('monthYearPickerPlugin', Ext.ux.MonthYearPickerPlugin);
