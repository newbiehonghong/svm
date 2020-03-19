<template>
    <div class="edit-panel">
        <el-form ref="configForm" :model="row">
            <el-form-item label="显示类型">
                <el-radio-group v-model="row.type" size="small">
                    <el-radio
                        v-for="item in cache.type"
                        :key="item.value"
                        :label="item.value"
                    >{{item.label}}</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="面板宽度">
                <el-input-number v-model="row.width" :min="300" :max="1200" :step="10"></el-input-number>
            </el-form-item>
            <el-form-item label="面板高度">
                <el-input-number v-model="row.height" :min="200" :max="400" :step="40"></el-input-number>
            </el-form-item>
            <el-form-item label="记录数" v-if="visible_recordCount">
                <el-input-number v-model="row.count" :min="5" :max="10"></el-input-number>
            </el-form-item>
            <el-form-item label="显示内容" v-if="visible_mode">
                <el-radio-group v-model="row.mode" size="small">
                    <el-radio label="service">服务</el-radio>
                    <el-radio label="api">API</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="服务列表" v-if="visible_service">
                <el-cascader
                    v-model="row.services"
                    :options="serviceList"
                    :props="{ multiple: true, value:'value', label:'value' }"
                    style="width:400px"
                ></el-cascader>
            </el-form-item>
            <el-form-item label="API列表" v-if="visible_api">
                <el-cascader
                    v-model="row.apis"
                    :options="apiList"
                    :props="{ multiple: true, value:'value', label:'value' }"
                    style="width:400px"
                ></el-cascader>
            </el-form-item>

            <div class="footer-box">
                <el-button type="primary" @click="doSave">提 交</el-button>
                <el-button type="info" @click="goBack">返 回</el-button>
            </div>
        </el-form>
    </div>
</template>

<script>
import dict from "@/dict/dashboard";
import { saveConfig, updateConfig, queryApis } from "@/api/dashboard";
import util from "../util";
export default {
	data() {
		return {
			cache: dict,
			row: {},
			serviceList: [],
			apiList: []
		};
	},
	props: ["config"],
	computed: {
		visible_recordCount: function() {
			return this.row.type && this.row.type > 10 && this.row.type < 30;
		},
		visible_mode: function() {
			return this.row.type && this.row.type > 20;
		},
		visible_service: function() {
			return this.row.type > 30 && this.row.mode == "service";
		},
		visible_api: function() {
			return this.row.type > 30 && this.row.mode == "api";
		}
	},
	created() {
		this.init();
	},
	methods: {
		init() {
			this.row = Object.assign({}, this.config);
			if (dict.service_list) {
				this.serviceList = dict.service_list;
				this.apiList = dict.api_list;
			} else {
				queryApis().then(res => {
					var list = [];
					for (let i = 0; i < res.length; i++) {
						list.push({ value: res[i].value });
					}
					this.serviceList = dict.service_list = list;
					this.apiList = dict.api_list = res;
				});
			}
		},
		doSave() {
			if (!this.validate()) {
				return;
			}

			var config = Object.assign({}, this.row);
			if (config.mode == "service") {
				config.services = util.formatServices(config.services);
			}
			if (config.mode == "api") {
				config.apis = util.formatApis(config.apis);
			}

			if (config.id) {
				updateConfig(config).then(res => {
					this.bus.$emit("dashboard.update", "edit", config);
				});
			} else {
				saveConfig(config).then(res => {
					config.id = res;
					this.bus.$emit("dashboard.update", "add", config);
				});
			}
		},
		goBack() {
			this.bus.$emit("dashboard.update", "cancel");
		},
		validate() {
			if (this.row.type == 0) {
				this.$message({
					message: "请选择显示类型",
					type: "warn"
				});
				return false;
			}
			return true;
		}
	}
};
</script>

<style lang="scss" scoped>
.edit-panel {
	margin-top: 40px;
}
</style>
