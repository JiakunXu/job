myApp.onPageInit('member.index', function(page) {
			member_index_stats();
		});

function member_index_stats() {
	$$.get(appUrl + '/job/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#member/index/publish').html(stats[0]);
				$$('#member/index/finish').html(stats[1]);
				$$('#member/index/revoke').html(stats[2]);
			});
}