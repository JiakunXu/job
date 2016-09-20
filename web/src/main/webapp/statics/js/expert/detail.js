myApp.onPageInit('expert.detail', function(page) {
	$$('form.ajax-submit.expert-detail-form').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.expert-detail-form').on('submitted', function(e) {
		myApp.hideIndicator();
		var xhr = e.detail.xhr;
		myApp.alert(xhr.responseText, '信息', function() {
			myApp.getCurrentView().router.back({
				url : myApp.getCurrentView().history[myApp.getCurrentView().history.length
						- 2],
				force : true,
				ignoreCache : true
			});
		});
	});

	$$('form.ajax-submit.expert-detail-form').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});
});

function expert_detail_refresh() {
	myApp.getCurrentView().router.refreshPage();
}

function expert_detail_submit() {
	myApp.showIndicator();

	$$('#expert/detail/submit').trigger("submit");
}