myApp.onPageInit('resume.edit', function(page) {
	$$('form.ajax-submit.resume-edit-form').on('beforeSubmit', function(e) {
			});
	$$('form.ajax-submit.resume-edit-delete').on('beforeSubmit', function(e) {
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
	$$('form.ajax-submit.resume-edit-delete').on('submitted', function(e) {
		myApp.hideIndicator();
		var xhr = e.detail.xhr;

		$$(".list-block.resume-edit-resumeDetail-"
				+ resume_edit_delete_detailId).remove();
	});

	$$('form.ajax-submit.resume-edit-form').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});
	$$('form.ajax-submit.resume-edit-delete').on('submitError', function(e) {
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

var resume_edit_delete_detailId;

function resume_edit_delete(flag, detailId) {
	myApp.confirm('确定删除？', '简历管理', function() {
				myApp.showIndicator();

				if (flag == 'Y') {
					resume_edit_delete_detailId = detailId;

					$$('#resume_edit_delete_detailId').val(detailId);
					$$('#resume/edit/delete').trigger("submit");
				} else {
					$$(".list-block.resume-edit-0-" + detailId).remove();
					myApp.hideIndicator();
				}
			});
}