$(function() {
	/*
	 * Flot Interactive Chart -----------------------
	 */

	/*
	 * DONUT CHART -----------
	 */

	var donutData = [ {
		label : 'Series2',
		data : 30,
		color : '#f08080'
	}, {
		label : 'Series3',
		data : 20,
		color : '#3c8dbc'
	} ];
	$.plot('#donut-chart', donutData, {
		series : {
			pie : {
				show : true,
				radius : 1,
				innerRadius : 0.5,
				label : {
					show : true,
					radius : 2 / 3,
					formatter : labelFormatter,
					threshold : 0.1
				}

			}
		},
		legend : {
			show : false
		}
	});
	
	$.plot('#donut-chart2', donutData, {
		series : {
			pie : {
				show : true,
				radius : 1,
				innerRadius : 0.5,
				label : {
					show : true,
					radius : 2 / 3,
					formatter : labelFormatter,
					threshold : 0.1
				}

			}
		},
		legend : {
			show : false
		}
	});
	/*
	 * END DONUT CHART
	 */

});

/*
 * Custom Label formatter ----------------------
 */
function labelFormatter(label, series) {
	return '<div style="font-size:13px; text-align:center; padding:2px; color: #fff; font-weight: 600;">'
			+ label + '<br>' + Math.round(series.percent) + '%</div>';
}
