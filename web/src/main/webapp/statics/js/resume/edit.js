myApp.onPageInit('resume.edit', function(page) {
	$$('form.ajax-submit.resume-edit-form').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.resume-edit-form').on('submitted', function(e) {
		myApp.hideIndicator();
		var xhr = e.detail.xhr;
		myApp.getCurrentView().router.back({
			url : myApp.getCurrentView().history[myApp.getCurrentView().history.length
					- 2],
			force : true,
			ignoreCache : true
		});
	});

	$$('form.ajax-submit.resume-edit-form').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});

	myApp.calendar({
				input : '#resume_edit_birthday',
				closeOnSelect : true
			});

});

function resume_edit_saveOrUpdate() {
	myApp.showIndicator();

	$$('#resume/edit/saveOrUpdate').trigger("submit");
}