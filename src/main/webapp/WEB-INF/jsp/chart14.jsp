
<html xmlns:th="http://www.thymeleaf.org">

<head>

    <html xmlns:th="http://www.thymeleaf.org">

    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">


        <script src="highstock.js"></script>

        <script src="exporting.js"></script>
        <script src="jquery.min.js"></script>
        <script src="ema.js"></script>
        <script src="rsi.js"></script>
        <script src="indicators.js"></script>


        <script type="text/javascript">
            $(function() {
                var seriesOptions = [],
                    seriesOptions20d = [],
                    seriesOptions75d = [],
                    seriesOptions150d = [],
                    seriesCounter = 0,
                    chart
                var date = new Date();



                /**
                 * Create the chart when all data is loaded
                 * @returns {undefined}
                 */
                function createChart(name) {

                    // create the chart
                   // chart = new  Highcharts.StockChart('container', {
                    chart = new  Highcharts.StockChart(name, {

                        chart: {
                            width: 400,
                            height: 300
                        },


                        legend: {
                            enabled: false
                        },

                        xAxis: {
                            min: Date.UTC(date.getFullYear() - 1 , 1, 1, 16, 00), //previous day  at 16.00
                            max: new Date().getTime() //get actual time
                        },

                        rangeSelector: {
                            enabled: false
                        },

                        navigator: {
                            enabled: false
                        },

                        tooltip: {
                            split: true
                        },

                        series: [{
                            type: 'ohlc',
                            id: 'aapl',
                            data: seriesOptions,

                            dataGrouping: {
                                enabled: true,
                                units: [ ['month', [1]] ]

                            }

                        },
                            {
                                type: 'sma',
                                name: 'sma50',
                                linkedTo: 'aapl',

                                params: {
                                    period: 50
                                }
                            },
                            {
                                type: 'sma',
                                name: 'sma20',
                                linkedTo: 'aapl',

                                params: {
                                    period: 20
                                }
                            }


                        ]
                    });

                };

                $(document).ready(function() {

                    var year =date.getFullYear()-5;
                    var mm = (date.getMonth()+1).toString();
                    var dd  = date.getDate().toString();

                    var url =   "data?code=${code}&date="+year+"-"+ (mm[1]?mm:"0"+mm[0])+"-"+ (dd[1]?dd:"0"+dd[0]);
                    var link = new URL("../"+url, window.location.href).href;
                    console.log('xxx urlxxx: '+link);

                $.getJSON(url, function(data) {
                    console.log('xx');
                    //         data.map(function(p) {
                    //             var ymd = p.date.split("-");
                    //             seriesOptions.push([ Date.UTC(ymd[0], ymd[2] - 1, ymd[1]) , p.price ] );
                    //
                    //         })
                    // console.log('xx 1' +seriesOptions );


                    for(var i = 0; i < data.length; i++){



                        seriesOptions.push([
                            new Date(data[i].date).getTime(), // the date
                            data[i].open_price , // open
                            data[i].day_high_price, // high
                            data[i].day_low_price, // low
                            data[i].last_price // close
                        ]);


                        seriesOptions20d.push([ new Date(data[i].date).getTime(), data[i].twenty ]);
                        seriesOptions75d.push([ new Date(data[i].date).getTime(), data[i].sevenfive ]);
                        seriesOptions150d.push([ new Date(data[i].date).getTime(), data[i].onehundredfifty ]);
                    }
                    createChart('container') ;
                    chart.series.forEach(function(ser) {
                        console.log('SELECTED -1')
                        ser.update({
                            dataGrouping: {
                                units: [ ['day', [1]] ],
                                groupPixelWidth: 10
                            }
                        }, false);
                    });
                    chart.xAxis[0].setTitle({ text: "1yrs - day" });
                    chart.redraw();

                    createChart('container4') ;
                    chart.series.forEach(function(ser) {
                        console.log('SELECTED -1')
                        ser.update({
                            dataGrouping: {
                                units: [ ['day', [1]] ],
                                groupPixelWidth: 10
                            }
                        }, false);
                    });
                    chart.series[1].setVisible(false);
                    chart.series[2].setVisible(false);

                    chart.xAxis[0].setExtremes(Date.UTC(date.getFullYear() - 2, 1, 1, 16, 00), new Date().getTime());
                    chart.xAxis[0].setTitle({ text: "2yrs - day" });

                    chart.redraw();







                    createChart('container1') ;
                    chart.series.forEach(function(ser) {
                        console.log('SELECTED -1')
                        ser.update({
                            dataGrouping: {
                                units: [ ['month', [1]] ],
                                groupPixelWidth: 10
                            }
                        }, false);
                    });
                    chart.series[1].setVisible(false);
                    chart.series[2].setVisible(false);

                    chart.xAxis[0].setExtremes(Date.UTC(date.getFullYear() - 3, 1, 1, 16, 00), new Date().getTime());
                    chart.xAxis[0].setTitle({ text: "3yrs - month" });

                    chart.redraw();







                    createChart('container2') ;
                    chart.series.forEach(function(ser) {
                        console.log('SELECTED -1')
                        ser.update({
                            dataGrouping: {
                                units: [ ['week', [1]] ],
                                groupPixelWidth: 10
                            }
                        }, false);
                    });
                    chart.series[1].setVisible(false);
                    chart.series[2].setVisible(false);

                    chart.xAxis[0].setExtremes(Date.UTC(date.getFullYear() - 2, 1, 1, 16, 00), new Date().getTime());

                    chart.xAxis[0].setTitle({ text: "2yrs - week" });

                    chart.redraw();



                    createChart('container3') ;
                    chart.series.forEach(function(ser) {
                        console.log('SELECTED -1')
                        ser.update({
                            dataGrouping: {
                                units: [ ['month', [1]] ],
                                groupPixelWidth: 10
                            }
                        }, false);
                    });
                    chart.series[1].setVisible(false);
                    chart.series[2].setVisible(false);

                    chart.xAxis[0].setExtremes(Date.UTC(date.getFullYear() - 5, 1, 1, 16, 00), new Date().getTime());

                    chart.xAxis[0].setTitle({ text: "5yrs - month" });

                    chart.redraw();

                    createChart('container5') ;
                    chart.series.forEach(function(ser) {
                        console.log('SELECTED -1')
                        ser.update({
                            dataGrouping: {
                                units: [ ['week', [1]] ],
                                groupPixelWidth: 10
                            }
                        }, false);
                    });
                    chart.series[1].setVisible(false);
                    chart.series[2].setVisible(false);

                    chart.xAxis[0].setExtremes(Date.UTC(date.getFullYear() - 3, 1, 1, 16, 00), new Date().getTime());

                    chart.xAxis[0].setTitle({ text: "3yrs - week" });

                    chart.redraw();

                    createChart('container6') ;
                    chart.series.forEach(function(ser) {
                        console.log('SELECTED -1')
                        ser.update({
                            dataGrouping: {
                                units: [ ['month', [1]] ],
                                groupPixelWidth: 10
                            }
                        }, false);
                    });
                    chart.series[1].setVisible(false);
                    chart.series[2].setVisible(false);

                    chart.xAxis[0].setExtremes(Date.UTC(date.getFullYear() - 4, 1, 1, 16, 00), new Date().getTime());

                    chart.xAxis[0].setTitle({ text: "4yrs - month" });

                    chart.redraw();

                });








                });

            });


        </script>
        <style>
            input[type=text] {
                width: 3em

            }

            font-size: 5px;
            label {
                font-weight: bold;
            }

        </style>

    </head>
    <html>
<body>
<label>
    <span th:text="${code}" />
</label>



<div class="container">



    <div class="row">
        <div class="col-md-6">
            <div id="container"  ></div>
        </div>
        <div class="col-md-6" >
            <div id="container4"  ></div>
        </div>

    </div>
    <div class="row">
        <div class="col-md-6" >
            <div id="container2"  ></div>
        </div>
        <div class="col-md-6" >
            <div id="container5"  ></div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6" >
            <div id="container1"  ></div>
        </div>
        <div class="col-md-6" >
            <div id="container6"  ></div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6" >
            <div id="container3"  ></div>
        </div>
    </div>

</div>

<h4> v3</h4>

</body>
</html>


