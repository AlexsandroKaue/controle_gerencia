var valorAtualCpu = 0;

/*function success(count) {
	alert(count);
}*/

function atualizarValorCpu(novoValor) {
	valorAtualCpu = novoValor;
}

$(function() {
	/*
	 * Flot Interactive Chart
	 * -----------------------
	 */
	// We use an inline data source in the example, usually data would
	// be fetched from a server
	var data = [], totalPoints = 50;

	function getData() {
		solicitarNovoValorCpuJS();

		if (data.length > 0)
			data = data.slice(1);

		while (data.length < totalPoints) {
			y = valorAtualCpu;

			if (y < 0) {
				y = 0;
			} else if (y > 100) {
				y = 100;
			}

			data.push(y);
		}

		// Zip the generated y values with the x values
		var res = [];
		for (var i = 0; i < data.length; ++i) {
			res.push([ i, data[i] ]);
		}

		return res;
	}

	var cpu_plot = $.plot('#cpu', [ getData() ], {
		grid : {
			borderColor : '#f3f3f3',
			borderWidth : 1,
			tickColor : '#f3f3f3'
		},
		series : {
			shadowSize : 0, // Drawing is faster without shadows
			color : '#3c8dbc'
		},
		lines : {
			fill : true, //Converts the line chart to area chart
			color : '#3c8dbc'
		},
		yaxis : {
			min : 0,
			max : 100,
			show : true
		},
		xaxis : {
			show : false
		}
	});

	var updateInterval = 1000; //Fetch data ever x milliseconds
	var realtime = 'on'; //If == to on then fetch data every x seconds. else stop fetching
	function update() {

		cpu_plot.setData([ getData() ]);

		// Since the axes don't change, we don't need to call plot.setupGrid()
		cpu_plot.draw();
		if (realtime === 'on')
			setTimeout(update, updateInterval);
	}

	/*//INITIALIZE REALTIME DATA FETCHING
	if (realtime === 'on') {
		update();
	}
	//REALTIME TOGGLE
	$('#realtime .btn').click(function() {
		if ($(this).data('toggle') === 'on') {
			realtime = 'on';
		} else {
			realtime = 'off';
		}
		update();
	});*/
	/*
	 * END INTERACTIVE CHART
	 */

});

/*
 * Custom Label formatter
 * ----------------------
 */
function labelFormatter(label, series) {
	return '<div style="font-size:13px; text-align:center; padding:2px; color: #fff; font-weight: 600;">'
			+ label + '<br>' + Math.round(series.percent) + '%</div>';
}