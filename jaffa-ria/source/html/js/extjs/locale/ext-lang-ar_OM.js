/*
This file is part of Ext JS 3.4

Copyright (c) 2011-2014 Sencha Inc

Contact:  http://www.sencha.com/contact

Commercial Usage
Licensees holding valid commercial licenses may use this file in accordance with the Commercial
Software License Agreement provided with the Software or, alternatively, in accordance with the
terms contained in a written agreement between you and Sencha.

If you are unsure which license is appropriate for your use, please contact the sales department
at http://www.sencha.com/contact.

Build date: 2014-10-27 12:52:39
*/
/**
 * List compiled by mystix on the extjs.com forums.
 * Thank you Mystix!
 *
 * Arabic Translations
 * updated to 2.2 by Condor (8 Aug 2008)
 */

Ext.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">جارٍ التحميل...</div>';

if(Ext.DataView){
  Ext.DataView.prototype.emptyText = "";
}

if(Ext.grid.GridPanel){
  Ext.grid.GridPanel.prototype.ddText = "{0} الصف المحدد{1}";
}

if(Ext.LoadMask){
  Ext.LoadMask.prototype.msg = "تحميل...";
}

Date.monthNames = [
  "يناير",
  "فبراير",
  "مارس",
  "أبريل",
  "مايو",
  "يونيو",
  "يوليو",
  "أغسطس",
  "سبتمبر",
  "أكتوبر",
  "نوفمبر",
  "ديسمبر"
];

Date.getShortMonthName = function(month) {
  return Date.monthNames[month].substring(0, 3);
};

Date.monthNumbers = {
  Jan : 0,
  Feb : 1,
  Mar : 2,
  Apr : 3,
  May : 4,
  Jun : 5,
  Jul : 6,
  Aug : 7,
  Sep : 8,
  Oct : 9,
  Nov : 10,
  Dec : 11
};

Date.getMonthNumber = function(name) {
  return Date.monthNumbers[name.substring(0, 1).toUpperCase() + name.substring(1, 3).toLowerCase()];
};

Date.dayNames = [
  "الأحد",
  "الاثنين",
  "الثلاثاء",
  "الأربعاء",
  "الخميس",
  "الجمعة",
  "السبت"
];

Date.dayChars = [
  "S",
  "M",
  "T",
  "W",
  "T",
  "F",
  "S"
];

Date.getShortDayName = function(day) {
  return Date.dayNames[day].substring(0, 3);
};

Date.parseCodes.S.s = "(?:st|nd|rd|th)";

if(Ext.MessageBox){
  Ext.MessageBox.buttonText = {
    ok     : "موافق",
    cancel : "إلغاء",
    yes    : "نعم",
    no     : "لا"
  };
}

if(Ext.util.Format){
  Ext.util.Format.date = function(v, format){
    if(!v) return "";
    format = format || "d/m/Y";
    if(!(v instanceof Date)) v = Date.parseDate(v, format);
    return v.dateFormat(format);
  };
}

if(Ext.DatePicker){
  Ext.apply(Ext.DatePicker.prototype, {
    todayText         : "اليوم",
    minText           : "هذا التاريخ قبل أقل تاريخ مسموح به",
    maxText           : "هذا التاريخ بعد أقصى تاريخ مسموح به",
    disabledDaysText  : "",
    disabledDatesText : "",
    monthNames        : Date.monthNames,
    dayNames          : Date.dayChars,
    nextText          : 'الشهر التالي (Control+Right)',
    prevText          : 'الشهر السابق (Control+Left)',
    monthYearText     : 'اختيار شهر (Control+سهم لأعلى/لأسفل لتغيير السنوات)',
    todayTip          : "{0} (مفتاح المسافة)",
    format            : "d/m/Y",
    okText            : "&#160;موافق&#160;",
    cancelText        : "إلغاء",
    startDay          : 0
  });
}

if(Ext.PagingToolbar){
  Ext.apply(Ext.PagingToolbar.prototype, {
    beforePageText : "صفحة",
    afterPageText  : "من {0}",
    firstText      : "الصفحة الأولى",
    prevText       : "الصفحة السابقة",
    nextText       : "الصفحة التالية",
    lastText       : "الصفحة الأخيرة",
    refreshText    : "تحديث",
    displayMsg     : "عرض {0} - {1} من {2}",
    emptyMsg       : 'لا توجد معلومات للعرض'
  });
}

if(Ext.form.BasicForm){
    Ext.form.BasicForm.prototype.waitTitle = "يُرجى الانتظار..."
}

if(Ext.form.Field){
  Ext.form.Field.prototype.invalidText = "القيمة في هذا الحقل غير صالحة";
}

if(Ext.form.TextField){
  Ext.apply(Ext.form.TextField.prototype, {
    minLengthText : "أدنى طول لهذا الحقل هو {0}",
    maxLengthText : "أقصى طول لهذا الحقل هو {0}",
    blankText     : "هذا الحقل مطلوب",
    regexText     : "",
    emptyText     : null
  });
}

if(Ext.form.NumberField){
  Ext.apply(Ext.form.NumberField.prototype, {
    decimalSeparator : ".",
    decimalPrecision : 2,
    minText : "أدنى قيمة لهذا الحقل هي {0}",
    maxText : "أقصى قيمة لهذا الحقل هي {0}",
    nanText : "{0} ليس رقمًا صالحًا"
  });
}

if(Ext.form.DateField){
  Ext.apply(Ext.form.DateField.prototype, {
    disabledDaysText  : "تعطيل",
    disabledDatesText : "تعطيل",
    minText           : "يجب أن يكون التاريخ في هذا الحقل بعد {0}",
    maxText           : "يجب أن يكون التاريخ في هذا الحقل قبل {0}",
    invalidText       : "{0} ليس تاريخاً صحيحاً - يجب أن يكون بالتنسيق {1}",
    format            : "d/m/y",
    altFormats        : "d/m/Y|d/m/y|d-m-y|d-m-Y|d/m|d-m|dm|dmy|dmY|d|Y-m-d",
    startDay          : 0
  });
}

if(Ext.form.ComboBox){
  Ext.apply(Ext.form.ComboBox.prototype, {
    loadingText       : "تحميل...",
    valueNotFoundText : undefined
  });
}

if(Ext.form.VTypes){
  Ext.apply(Ext.form.VTypes, {
    emailText    : 'يجب أن يتضمن هذا الحقل عنوان بريد إلكتروني بالتنسيق "user@example.com"',
    urlText      : 'يجب أن يتضمن هذا الحقل رابط URL بالتنسيق "user@example.com"',
    alphaText    : 'يجب أن يتضمن هذا الحقل فقط أحرف وشرطة سفلية (_)',
    alphanumText : 'يجب أن يتضمن هذا الحقل فقط أحرف وأرقام وشرطة سفلية (_)'
  });
}

if(Ext.form.HtmlEditor){
  Ext.apply(Ext.form.HtmlEditor.prototype, {
    createLinkText : 'يرجى إدخال عنوان URL للارتباط:',
    buttonTips : {
      bold : {
        title: 'غامق (Ctrl+B)',
        text: 'جعل النص المحدد غامقًا.',
        cls: 'x-html-editor-tip'
      },
      italic : {
        title: 'مائل (Ctrl+I)',
        text: 'جعل النص المحدد مائلاً.',
        cls: 'x-html-editor-tip'
      },
      underline : {
        title: 'تسطير (Ctrl+U)',
        text: 'تسطير النص المحدد.',
        cls: 'x-html-editor-tip'
      },
      increasefontsize : {
        title: 'تكبير النص',
        text: 'زيادة حجم الخط.',
        cls: 'x-html-editor-tip'
      },
      decreasefontsize : {
        title: 'نص العمل المنجز',
        text: 'إنقاص حجم الخط.',
        cls: 'x-html-editor-tip'
      },
      backcolor : {
        title: 'لون تمييز النص',
        text: 'تغيير لون خلفية النص المحدد.',
        cls: 'x-html-editor-tip'
      },
      forecolor : {
        title: 'لون الخط',
        text: 'تغيير لون النص المحدد.',
        cls: 'x-html-editor-tip'
      },
      justifyleft : {
        title: 'محاذاة النص لليسار',
        text: 'محاذاة النص جهة اليسار.',
        cls: 'x-html-editor-tip'
      },
      justifycenter : {
        title: 'توسيط النص',
        text: 'توسيط النص في المحرر.',
        cls: 'x-html-editor-tip'
      },
      justifyright : {
        title: 'محاذاة النص لليمين',
        text: 'محاذاة النص جهة اليمين.',
        cls: 'x-html-editor-tip'
      },
      insertunorderedlist : {
        title: 'قائمة ذات تعداد نقطي',
        text: 'بدء قائمة ذات تعداد نقطي.',
        cls: 'x-html-editor-tip'
      },
      insertorderedlist : {
        title: 'قائمة ذات تعداد رقمي',
        text: 'بدء قائمة ذات تعداد رقمي.',
        cls: 'x-html-editor-tip'
      },
      createlink : {
        title: 'الارتباطات التشعُبِيّة',
        text: 'جعل النص المحدد كارتباط تشعبي.',
        cls: 'x-html-editor-tip'
      },
      sourceedit : {
        title: 'تحرير المصدر',
        text: 'التبديل إلى وضع تحرير المصدر.',
        cls: 'x-html-editor-tip'
      }
    }
  });
}

if(Ext.grid.GridView){
  Ext.apply(Ext.grid.GridView.prototype, {
    sortAscText  : "فرز تصاعدي",
    sortDescText : "فرز تنازلي",
    columnsText  : "أعمدة"
  });
}

if (Ext.ux.grid.GridFilters) {
  Ext.override(Ext.ux.grid.GridFilters, {
    menuFilterText: "عوامل التصفية"
  });
}

if (Ext.ux.grid.filter.BooleanFilter) {
  Ext.override(Ext.ux.grid.filter.BooleanFilter, {
    yesText : "نعم",
	noText : "لا"
  });
}

if (Ext.ux.grid.RowEditor) {
  Ext.override(Ext.ux.grid.RowEditor, {
    saveText: "حفظ",
	cancelText: "إلغاء"
  });
}

if(Ext.grid.GroupingView){
  Ext.apply(Ext.grid.GroupingView.prototype, {
    emptyGroupText : '(لا يوجد)',
    groupByText    : 'تجميع حسب هذا الحقل',
    showGroupsText : 'إظهار في مجموعات'
  });
}

if(Ext.grid.PropertyColumnModel){
  Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
    nameText   : "الاسم",
    valueText  : "القيمة",
    dateFormat : "j/m/Y",
    trueText: "صواب",
    falseText: "خطأ"
  });
}

if(Ext.layout.BorderLayout && Ext.layout.BorderLayout.SplitRegion){
  Ext.apply(Ext.layout.BorderLayout.SplitRegion.prototype, {
    splitTip            : "اسحب لتغيير حجم.",
    collapsibleSplitTip : "اسحب لتغيير حجم. انقر نقراً مزدوجاً لإخفاء."
  });
}

if(Ext.form.TimeField){
  Ext.apply(Ext.form.TimeField.prototype, {
    minText : "يجب أن يكون الوقت في هذا الحقل مساويًا لـ {0} أو بعده",
    maxText : "يجب أن يكون الوقت في هذا الحقل مساويًا لـ {0} أو قبله",
    invalidText : "{0} هو وقت غير صالح",
    format : "g:i A",
    altFormats : "g:ia|g:iA|g:i a|g:i A|h:i|g:i|H:i|ga|ha|gA|h a|g a|g A|gi|hi|gia|hia|g|H"
  });
}

if(Ext.form.CheckboxGroup){
  Ext.apply(Ext.form.CheckboxGroup.prototype, {
    blankText : "يجب تحديد عنصر واحد على الأقل في هذه المجموعة"
  });
}

if(Ext.form.RadioGroup){
  Ext.apply(Ext.form.RadioGroup.prototype, {
    blankText : "يجب تحديد عنصر واحد في هذه المجموعة"
  });
}