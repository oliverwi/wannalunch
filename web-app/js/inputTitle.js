function prepareFieldsWithTitle() {
  prepareElementsWithTitle($("input[title]"));
  prepareElementsWithTitle($("textarea[title]"));
}

function prepareElementsWithTitle(elements) {
  elements
    .each(function() {
      copyTitleToVal($(this));
    })
    .focus(function() {
      $(this).val("");
    })
    .blur(function() {
      text = jQuery.trim($(this).val());
      if (text == "" || text == $(this).attr("title")) {
        copyTitleToVal($(this));
      }
    });
}

function copyTitleToVal(element) {
  element.val(element.attr("title")).css("color", "#ccc");
}