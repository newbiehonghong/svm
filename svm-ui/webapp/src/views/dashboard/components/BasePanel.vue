<template>
    <div class="dashboard-panel">
        <el-card :style="style">
            <div slot="header">
                <el-row v-if="fullscreen">
                    <el-col :span="22" class="title">{{title}}</el-col>
                    <el-col :span="2" style="text-align:right">
                        <i class="el-icon-remove-outline" @click="doCancelFullScreen" title="退出全屏"></i>
                    </el-col>
                </el-row>
                <div
                    class="title"
                    @mouseover="DropdownVisible=true"
                    @mouseout="DropdownVisible=false"
                    v-else
                >
                    {{title}}
                    <el-dropdown trigger="click" @command="doMenuClick" v-show="DropdownVisible">
                        <span class="el-dropdown-link">
                            <i class="el-icon-caret-bottom"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-setting" command="edit">编辑</el-dropdown-item>
                            <el-dropdown-item icon="el-icon-circle-close" command="remove">删除</el-dropdown-item>
                            <el-dropdown-item icon="el-icon-full-screen" command="fullscreen">全屏</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
            <component :is="component" :config="config" :fullscreen="fullscreen"></component>
        </el-card>
    </div>
</template>

<script>
import { removeConfig } from "@/api/dashboard";
import Health from "./Health";
import MaxRtTop from "./MaxRtTop";
import AvgRtTop from "./AvgRtTop";
import QpsTop from "./QpsTop";
import BlockTop from "./BlockTop";
import AvgRtChart from "./AvgRtChart";
import QpsChart from "./QpsChart";

export default {
	components: {
		Health,
		MaxRtTop,
		AvgRtTop,
		QpsTop,
		BlockTop,
		AvgRtChart,
		QpsChart
	},
	data() {
		return {
			cmps: {
				"1": { name: "Health", title: "服务列表" },
				"11": { name: "MaxRtTop", title: "最大响应时间统计" },
				"12": { name: "AvgRtTop", title: "平均响应时间统计" },
				"21": { name: "QpsTop", title: "QPS统计" },
				"22": { name: "BlockTop", title: "限流次数统计" },
				"31": { name: "AvgRtChart", title: "平均响应时间时序图" },
				"32": { name: "QpsChart", title: "QPS时序图" }
			},
			DropdownVisible: false,
			menuVisible: false
		};
	},
	props: {
		config: {
			type: Object,
			required: true
		},
		fullscreen: {
			type: Boolean,
			default: false
		}
	},
	computed: {
		title: function() {
			return this.cmps[this.config.type + ""]["title"];
		},
		component: function() {
			return this.cmps[this.config.type + ""]["name"];
		},
		style: function() {
			if (this.fullscreen) {
				return "height:auto";
			}
			return (
				"width:" +
				(this.config.width || 500) +
				"px;height:" +
				(this.config.height || 280) +
				"px"
			);
		}
	},
	methods: {
		doEdit() {
			this.bus.$emit("dashboard.edit", this.config);
		},
		doRemove() {
			this.$confirm("删除不可恢复，确定要删除吗？", "警告", {
				type: "warning"
			}).then(async () => {
				await removeConfig(this.config);
				this.bus.$emit("dashboard.update", "remove", this.config);
			});
		},
		doFullScreen() {
			this.bus.$emit("dashboard.fullscreen", this.config);
		},
		doCancelFullScreen() {
			this.bus.$emit("dashboard.fullscreen");
		},
		doMenuClick(command) {
			switch (command) {
				case "edit":
					this.doEdit();
					break;
				case "remove":
					this.doRemove();
					break;
				case "fullscreen":
					this.doFullScreen();
					break;
			}
		}
	}
};
</script>

<style lang="scss" scoped>
.dashboard-panel {
	.panel-container {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		font-size: 36px;
		background-color: #1f1f20;
		border-radius: 4px;
	}
	.title {
		font-size: 14px;
		padding-left: 45%;
		cursor: move;
	}
	i {
		cursor: pointer;
	}
	i:hover {
		color: teal;
	}
}
</style>

<style lang="scss">
.dashboard-panel {
	.el-card__header {
		border-bottom: 1px solid #262628;
		padding: 5px;
	}
	.el-card__body {
		padding: 5px;
	}
}
</style>