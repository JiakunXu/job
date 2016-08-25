myApp.onPageInit('job.detail', function(page) {
	$$('form.ajax-submit.job-detail-delete').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.job-detail-copy').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.job-detail-delete').on('submitted', function(e) {
		myApp.hideIndicator();
		var xhr = e.detail.xhr;
		myApp.getCurrentView().router.back({
			url : myApp.getCurrentView().history[myApp.getCurrentView().history.length
					- 2],
			force : true,
			ignoreCache : true
		});
	});

	$$('form.ajax-submit.job-detail-copy').on('submitted', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.getCurrentView().router.load({
							url : appUrl + "/item/list.htm?shopId="
									+ xhr.responseText,
							ignoreCache : true
						});
			});

	$$('form.ajax-submit.job-detail-delete').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});

	$$('form.ajax-submit.job-detail-copy').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});

});

function job_detail_delete() {
	myApp.confirm('确定删除项目？', '项目管理', function() {
				myApp.showIndicator();

				$$('#job/detail/delete').trigger("submit");
			});
}

function job_detail_copy() {
	myApp.showIndicator();

	$$('#job/detail/copy').trigger("submit");
}