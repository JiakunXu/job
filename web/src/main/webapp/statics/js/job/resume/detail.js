myApp.onPageInit('job.resume.detail', function(page) {
	$$('form.ajax-submit.job-resume-detail-form').on('beforeSubmit',
			function(e) {
			});

	$$('form.ajax-submit.job-resume-detail-form').on('submitted', function(e) {
		myApp.hideIndicator();
		var xhr = e.detail.xhr;
		myApp.getCurrentView().router.back({
			url : myApp.getCurrentView().history[myApp.getCurrentView().history.length
					- 2],
			force : true,
			ignoreCache : true
		});
	});

	$$('form.ajax-submit.job-resume-detail-form').on('submitError',
			function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});
});

function job_resume_detail_ignore() {
	myApp.confirm('确定忽略？', '简历管理', function() {
				myApp.showIndicator();

				$$('#job/resume/detail/ignore').trigger("submit");
			});
}