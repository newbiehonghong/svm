export default {
    data() {
        return {
            option: null,
            chart: null,
            backgroundColor: "#1f1f20",
            fontColor: "#F1F1F3",
            lineColor: "#57617B",
            colors: ['#db3233', '#89bd1b', '#0088d4', '#ffffff', '#ff1493', '#ffd700', '#00bfff', '#7cfc00', '#ffa500']
        };
    },
    computed: {
        style: function () {
            if (this.fullscreen) {
                return "height:400px";
            }
            return "width:" + (this.config.width || 500) + "px;height:" + ((this.config.height || 280) - 60) + "px";
        }
    },
    mounted() {
        this.initChart();
    },
    beforeDestroy() {
        if (this.chart) {
            this.chart.dispose();
            this.chart = null;
        }
    },
    methods: {
        initChart() {
            this.option = {
                backgroundColor: this.backgroundColor,
                tooltip: {
                    trigger: "axis",
                    formatter: (params) => {
                        var ret = [];
                        ret.push(params[0].axisValueLabel);
                        for (let i = 0; i < params.length; i++) {
                            ret.push("<span style='color:" + this.colors[i] + "'>" + params[i].seriesName + "</span>&nbsp;:&nbsp;" + params[i].value[1]);
                        }
                        return ret.join("<br/>");
                    }
                },
                legend: {
                    icon: "circle",
                    textStyle: { color: "#F1F1F3" },
                    data: []
                },
                xAxis: [
                    {
                        type: "time",
                        splitLine: { show: false },
                        axisLine: { lineStyle: { color: this.lineColor } }
                    }
                ],
                yAxis: [
                    {
                        type: "value",
                        name: this.yAxisName,
                        nameTextStyle: {
                            padding: [0, 0, -5, 10]
                        },
                        axisTick: { show: false },
                        axisLine: { lineStyle: { color: this.lineColor } },
                        splitLine: { lineStyle: { color: this.lineColor } }
                    }
                ],
                series: []
            };

            this.chart = echarts.init(this.$refs.chartDiv, "dark");
        },
        refreshChart(data) {
            if (!data || data.length == 0 || data.length > 7) {
                return;
            }

            var _legends = [];
            var _series = [];

            var name = this.config.mode == "service" ? "service" : "fullpath";
            for (let i = 0; i < data.length; i++) {
                var _name = data[i].metric[name];
                var _data = this.makeChartData(data[i].values);
                _legends.push(_name);
                _series.push(this.getSeriesOption(_name, _data, this.colors[i]));
            }

            this.option.legend.data = _legends;
            this.option.series = _series;
            //console.log(this.option)
            this.chart.setOption(this.option, true);
        },
        makeChartData(data) {
            var rows = [];
            for (let i = 0; i < data.length; i++) {
                rows.push([data[i][0] * 1000, parseFloat(data[i][1])]);
            }
            return rows;
        },
        getSeriesOption(name, data, color, other) {
            var gradient = [
                { offset: 0, color: this.getColor(color, 0.3) },
                { offset: 0.8, color: this.getColor(color, 0.8) }
            ];
            var option = {
                name: name,
                type: "line",
                data: data,
                smooth: true,
                showSymbol: false,
                lineStyle: { normal: { width: 1 } },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, gradient, false),
                        shadowColor: this.getColor("#000000", 0.1),
                        shadowBlur: 10
                    }
                },
                itemStyle: {
                    normal: {
                        color: this.getColor(color, 1),
                        borderColor: this.getColor(color, 0.2),
                        borderWidth: 12
                    }
                }
            };

            if (other) {
                for (let key in other) {
                    option[key] = other[key];
                }
            }

            return option;
        },
        getColor(color, a) {
            var r = parseInt(color.substring(1, 3), 16);
            var g = parseInt(color.substring(3, 5), 16);
            var b = parseInt(color.substring(5, 7), 16);
            return "rgba(" + r + "," + g + "," + b + "," + a + ")";
        }
    }
}