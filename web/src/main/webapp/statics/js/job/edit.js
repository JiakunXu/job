myApp.onPageInit('job.edit', function(page) {
	$$('form.ajax-submit.job-edit-form').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.job-edit-form').on('submitted', function(e) {
		myApp.hideIndicator();
		var xhr = e.detail.xhr;
		myApp.getCurrentView().router.back({
			url : myApp.getCurrentView().history[myApp.getCurrentView().history.length
					- 2],
			force : true,
			ignoreCache : true
		});
	});

	$$('form.ajax-submit.job-edit-form').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});
});

function job_edit_update() {
	myApp.showIndicator();

	$$('#job/edit/update').trigger("submit");
}