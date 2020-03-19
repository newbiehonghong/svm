<template>
    <div class="chart-box">
        <div ref="chartDiv" :style="style"></div>
    </div>
</template>

<script>
import { queryQpsChart } from "@/api/dashboard";
import base from "./BaseComponent";
import mixin from "./BaseChart.js";
import util from "../util";
export default {
	extends: base,
	mixins: [mixin],
	data() {
		return {
			yAxisName: "次/秒"
		};
	},
	watch: {
		config: function(value, oldValue) {
			if (value.height != oldValue.height) {
				this.$nextTick(() => {
					this.chart.resize();
				});
			}
		}
	},
	methods: {
		refresh(window) {
			var names =
				this.config.mode == "service"
					? util.formatServices(this.config.services)
					: util.formatApis(this.config.apis);
			queryQpsChart(window, this.config.mode, names).then(res => {
				this.refreshChart(res);
			});
		}
	}
};
</script>

<style scoped>
.chart-box {
	background: #1f1f20;
	margin: 10px 0;
	border-radius: 4px;
}
</style>