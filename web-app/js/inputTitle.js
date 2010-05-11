function prepareFieldsWithTitle() {

  function prepareElementsWithTitle(elements) {
    elements
      .each(function() {
        copyTitleToValueIfEmpty($(this));
        var element = $(this);
        $(element.parents("form:first")).submit(function() {
          clearIfHasTitle(element)
        })
      })
      .change(function() {
        copyTitleToValueIfEmpty($(this));
      })
      .blur(function() {
        copyTitleToValueIfEmpty($(this));
      })
      .focus(function() {
        clearIfHasTitle($(this))
      });
  }
  
  function copyTitleToValueIfEmpty(element) {
    text = jQuery.trim(element.val());
    if (text == "" || text == element.attr("title")) {
      element.val(element.attr("title")).addClass("fadeFieldColor");
    } else {
      element.removeClass("fadeFieldColor");
    }
  }
  
  function clearIfHasTitle(element) {
    if (element.val() == element.attr("title")) {
      element.val("").removeClass("fadeFieldColor");
    }
  }
  
  prepareElementsWithTitle($("input[title]"));
  prepareElementsWithTitle($("textarea[title]"));
}