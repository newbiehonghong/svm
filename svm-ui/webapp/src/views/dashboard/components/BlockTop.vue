<template>
    <div>
        <el-table :data="rows" stripe v-if="config.mode=='service'">
            <el-table-column prop="service" label="服务名称" width="auto"></el-table-column>
            <el-table-column prop="status" label="限流规则" :formatter="decode" width="100"></el-table-column>
            <el-table-column prop="value" label="次数" width="100"></el-table-column>
        </el-table>
        <el-table :data="rows" stripe v-else>
            <el-table-column prop="service" label="服务名称" width="140"></el-table-column>
            <el-table-column prop="path" label="API路径" width="auto"></el-table-column>
            <el-table-column prop="status" label="限流规则" :formatter="decode" width="100"></el-table-column>
            <el-table-column prop="value" label="次数" width="100"></el-table-column>
        </el-table>
    </div>
</template>

<script>
import dict from "@/dict/dashboard";
import { queryBlockTop } from "@/api/dashboard";
import base from "./BaseComponent";
export default {
	extends: base,
	data() {
		return {
			rows: []
		};
	},
	methods: {
		refresh(window) {
			queryBlockTop(window, this.config.mode, this.config.count).then(res => {
				this.rows = res;
			});
		},
		decode(row, column) {
			return dict.decode(column.property, row[column.property]);
		}
	}
};
</script>