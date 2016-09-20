myApp.onPageInit('expert.index', function(page) {
	$$('form.ajax-submit.expert-index-form').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.expert-index-form').on('submitted', function(e) {
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

	$$('form.ajax-submit.expert-index-form').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});
});

function expert_index_saveOrUpdate() {
	myApp.showIndicator();

	$$('#expert/index/saveOrUpdate').trigger("submit");
}