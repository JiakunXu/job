myApp.onPageInit('job.resume.list', function(page) {
			$$('form.ajax-submit.job-resume-list-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.job-resume-list-form').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.getCurrentView().router.refreshPage();
					});

			$$('form.ajax-submit.job-resume-list-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function job_resume_list_ignore(userJobId) {
	myApp.confirm('确定忽略？', '简历管理', function() {
				myApp.showIndicator();

				$$('#job_resume_list_userJobId').val(userJobId);
				$$('#job/resume/list/ignore').trigger("submit");
			});
}