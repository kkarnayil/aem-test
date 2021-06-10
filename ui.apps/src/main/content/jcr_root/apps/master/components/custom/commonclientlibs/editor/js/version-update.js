(function($, Coral) {
	"use strict";
	var selectors = {
		dialogContent: ".version_update",
		submitButton: "button.cq-dialog-submit",
		dialogForm: "form.cq-dialog"
	};
	/**
	 * Executes when the dialog is loaded and is an versionUpdate dialog.
	 */
	$(document).on("dialog-loaded", function(e) {
		var $dialog = e.dialog;
		if($dialog.length) {
			var $dialogContent = $dialog.find(selectors.dialogContent);
			if($dialogContent) {
				var versionUpdateEditor = $dialogContent.length > 0 ? $dialogContent[0] : undefined;
                var $submitBtn = $dialog.find(selectors.submitButton);

				if(versionUpdateEditor) {
					var $form = $(document).find(selectors.dialogForm);
					if($form) {
						var resourcePath = $form.attr("action");
						console.debug("Resource Path:", resourcePath);
						registerOnSubmitClick($form, $submitBtn, resourcePath)
					}
				}
			}
		}
	});

	function registerOnSubmitClick(form, submitBtn, resourcePath) {
        var servletUrl = resourcePath + ".update.html";

		$(submitBtn).unbind().click(function(e) {
                window.setTimeout(function(){
                  $.ajax({
                    method: "POST",
                    url: servletUrl,
                    data: $(selectors.dialogForm).serialize(),
                    success: function(results) {
                        console.log(results);
                        location.reload();
                    },
                    error: function(results) {
                        console.error(results);
                    }
                });

            }, 500);
		});
	}
})(jQuery, Coral);