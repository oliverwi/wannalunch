function prepareFieldsWithTitle() {
  prepareElementsWithTitle($("input[title]"));
  prepareElementsWithTitle($("textarea[title]"));
}

function prepareElementsWithTitle(elements) {
  elements
    .each(function() {
      copyTitleToValueIfEmpty($(this));
    })
    .blur(function() {
    	copyTitleToValueIfEmpty($(this));
    })
    .focus(function() {
      $(this).val("").removeClass("fadeFieldColor");
    });
}

function copyTitleToValueIfEmpty(element) {
  text = jQuery.trim(element.val());
  if (text == "" || text == element.attr("title")) {
    element.val(element.attr("title")).addClass("fadeFieldColor");
  }
}