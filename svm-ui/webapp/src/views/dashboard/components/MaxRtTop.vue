<template>
    <div>
        <el-table :data="rows" stripe>
            <el-table-column prop="service" label="服务名称" width="140"></el-table-column>
            <el-table-column prop="path" label="API路径" width="auto"></el-table-column>
            <el-table-column prop="value" label="响应时间" width="100"></el-table-column>
        </el-table>
    </div>
</template>

<script>
import { queryMaxRtTop } from "@/api/dashboard";
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
			//console.log("in refresh");
			queryMaxRtTop(window, this.config.count).then(res => {
				this.rows = res;
			});
		}
	}
};
</script>