<template>
    <div class="container dashboard">
        <div v-show="viewMode">
            <div class="tools">
                <el-tooltip effect="dark" content="新增面板">
                    <el-button icon="el-icon-plus" @click="doAdd"></el-button>
                </el-tooltip>
                <el-tooltip effect="dark" content="保存面板配置">
                    <el-button icon="icon-save" @click="doSave" style="padding: 2.8px 4px"></el-button>
                </el-tooltip>
                <el-dropdown split-button trigger="click" @command="doTimeWindowChange">
                    <i class="el-icon-time"></i>
                    <span class="time-window">{{timeWindowName}}</span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item
                            v-for="(value,key) in timeWindowNames"
                            :key="key"
                            :command="key"
                        >{{value}}</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <el-dropdown
                    split-button
                    trigger="click"
                    @click="doRefresh"
                    @command="doRefreshTimeChange"
                >
                    <i class="el-icon-refresh"></i>
                    <span class="refresh-time">{{refreshTime}}</span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="5s">5s</el-dropdown-item>
                        <el-dropdown-item command="10s">10s</el-dropdown-item>
                        <el-dropdown-item command="30s">30s</el-dropdown-item>
                        <el-dropdown-item command="1m">1m</el-dropdown-item>
                        <el-dropdown-item command="5m">5m</el-dropdown-item>
                        <el-dropdown-item command="15m">15m</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
            <div class="panel-group" v-dragula="items" service="panel" drake="panel">
                <div class="panel-box" v-for="(item,index) in items" :key="index">
                    <base-panel :config="item"></base-panel>
                </div>
            </div>
        </div>

        <edit-panel v-if="editMode" :config="currentConfig"></edit-panel>
        <base-panel v-if="fullscreenMode" :config="currentConfig" :fullscreen="true"></base-panel>
    </div>
</template>

<script>
import { queryConfig, saveLayout } from "@/api/dashboard";
import BasePanel from "./components/BasePanel";
import EditPanel from "./components/EditPanel";
import util from "./util";
export default {
	name: "Dashboard",
	components: {
		BasePanel,
		EditPanel
	},
	data() {
		return {
			items: [],
			updateItems: [],
			timeWindow: "hour",
			timeWindowNames: {
				hour: "一小时",
				day: "一天",
				week: "一周",
				month: "一月"
			},
			refreshTime: "30s",
			timer: null,
			viewMode: true,
			editMode: false,
			fullscreenMode: false,
			currentConfig: {}
		};
	},
	computed: {
		timeWindowName: function() {
			return this.timeWindowNames[this.timeWindow];
		}
	},
	mounted() {
		this.initLayout();
		this.bus.$on("dashboard.edit", this.edit);
		this.bus.$on("dashboard.update", this.update);
		this.bus.$on("dashboard.fullscreen", this.fullscreen);
		this.addTimer();
	},
	beforeDestroy() {
		this.bus.$off("dashboard.edit", this.edit);
		this.bus.$off("dashboard.update", this.update);
		this.bus.$off("dashboard.fullscreen", this.fullscreen);
		window.clearTimeout(this.timer);
	},
	created() {
		var service = this.$dragula.createService({
			name: "panel",
			drakes: {
				panel: {
					copy: false
				}
			}
		});
		service.on({
			dropModel: ({ dragIndex, dropIndex }) => {
				if (dragIndex == dropIndex) {
					return;
				}
				//console.log("drop--" + dragIndex + "--" + dropIndex);
				if (this.updateItems.length == 0) {
					this.items.forEach(item => {
						this.updateItems.push(item.id);
					});
				}
				var item = this.updateItems.splice(dragIndex, 1)[0];
				this.updateItems.splice(dropIndex, 0, item);
			}
		});
	},
	methods: {
		initLayout() {
			queryConfig().then(res => {
				var items = res.length ? res : [];
				var _items = [];
				for (let i = 0; i < items.length; i++) {
					this.addConfig(items[i], _items);
				}
				this.items = _items;
				this.$nextTick(this.doRefresh);
			});
		},
		newBlankPanel() {
			return {
				type: 0,
				width: 560,
				height: 280,
				count: 0,
				mode: "",
				services: "",
				apis: ""
			};
		},
		addConfig(item, items) {
			var config = util.parseConfig(item);
			items.push(config);

			this.$nextTick(function() {
				this.doRefreshItem(item.id);
			});
		},
		updateConfig(item, items) {
			for (let i = 0; i < items.length; i++) {
				if (items[i].id == item.id) {
					items.splice(i, 1, util.parseConfig(item));
					this.$nextTick(function() {
						this.doRefreshItem(item.id);
					});
					return;
				}
			}
		},
		removeConfig(item, items) {
			for (let i = 0; i < items.length; i++) {
				if (items[i].id == item.id) {
					items.splice(i, 1);
					this.$nextTick(function() {
						this.doRefresh();
					});
					return;
				}
			}
		},
		update(operation, config) {
			this.viewMode = true;
			this.editMode = false;

			switch (operation) {
				case "add":
					this.addConfig(config, this.items);
					break;
				case "edit":
					this.updateConfig(config, this.items);
					break;
				case "remove":
					this.removeConfig(config, this.items);
					break;
				default:
					break;
			}
		},
		refresh() {
			if (this.viewMode) {
				this.doRefresh();
			}
			this.addTimer();
		},
		doRefresh() {
			this.bus.$emit("dashboard.refresh", this.timeWindow);
		},
		doRefreshItem(id) {
			this.bus.$emit("dashboard.refresh", this.timeWindow, id);
		},
		edit(config) {
			this.currentConfig = config;
			this.viewMode = false;
			this.editMode = true;
		},
		doAdd() {
			this.edit(this.newBlankPanel());
		},
		fullscreen(config) {
			if (config) {
				this.currentConfig = config;
				this.viewMode = false;
				this.fullscreenMode = true;
				this.$nextTick(function() {
					this.doRefreshItem(config.id);
				});
			} else {
				this.fullscreenMode = false;
				this.viewMode = true;
			}
		},
		doTimeWindowChange(command) {
			this.timeWindow = command;
		},
		doRefreshTimeChange(command) {
			this.refreshTime = command;
			window.clearTimeout(this.timer);
			this.addTimer();
		},
		addTimer() {
			var time = this.refreshTime.substring(0, this.refreshTime.length - 1);
			var unit = this.refreshTime.substring(this.refreshTime.length - 1);
			time = parseInt(time) * (unit == "s" ? 1000 : 60000);
			this.timer = window.setTimeout(this.refresh, time);
		},
		doSave() {
			if (this.updateItems.length > 0) {
				this.items.forEach(item => {
					if (!this.updateItems.includes(item.id)) {
						this.updateItems.push(item.id);
					}
				});
				saveLayout(this.updateItems).then(res => {
					this.$message("保存成功");
				});
			}
		}
	}
};
</script>

<style lang="scss" scoped>
.tools {
	text-align: right;
}
.refresh-time {
	font-size: 13px;
}
.panel-group {
	display: flex;
	justify-content: space-between;
	flex-wrap: wrap;
	.panel-box {
		margin: 7px 0;
	}
}
</style>
<style lang="scss">
.dashboard {
	.tools {
		button {
			margin-left: 4px;
		}
		.el-button {
			border-radius: 3px;
			font-size: 15px;
			padding: 4px 6px;
		}
	}
}
</style>