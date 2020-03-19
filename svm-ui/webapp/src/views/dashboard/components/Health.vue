<template>
    <div>
        <el-table :data="rows" stripe>
            <el-table-column prop="service" label="服务名称" width="auto"></el-table-column>
            <el-table-column prop="value" label="实例数" width="100"></el-table-column>
        </el-table>
    </div>
</template>

<script>
import { queryHealth } from "@/api/dashboard";
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
			queryHealth().then(res => {
				this.rows = res;
			});
		}
	}
};
</script>