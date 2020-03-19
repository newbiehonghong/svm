<template>
    <div class="container security-permission-container">
        <el-transfer filterable v-model="selectedRoles" :data="roles" :titles="['未分配角色', '已分配角色']"></el-transfer>
        <div class="footer-box">
            <el-button type="primary" @click="doSave">提 交</el-button>
            <el-button @click="goBack">返 回</el-button>
        </div>
    </div>
</template>

<script>
import { savePermissionRoles } from "@/api/security/permission";
import { queryAllRoles, queryRolesByPermissionId } from "@/api/security/role";

export default {
	data() {
		return {
			id: "",
			roles: [],
			selectedRoles: []
		};
	},
	props: {
		params: { type: Object }
	},
	created() {
		this.init();
	},
	methods: {
		init() {
			this.id = this.params.id;

			queryAllRoles().then(res => {
				var rows = [];
				res.forEach(row => {
					rows.push({ key: row.id, label: row.name });
				});
				this.roles = rows;
			});
			queryRolesByPermissionId(this.id).then(res => {
				var rows = [];
				res.forEach(id => rows.push(id));
				this.selectedRoles = rows;
			});
		},
		doSave() {
			savePermissionRoles(this.id, this.selectedRoles).then(res => {
				this.goBack();
			});
		},
		goBack() {
			this.$emit("backward", "list");
		}
	}
};
</script>
<style>
.security-permission-container .el-transfer-panel {
	width: 300px;
}
.security-permission-container .el-transfer-panel__body {
	height: 352px;
}
.security-permission-container .el-transfer-panel__list {
	height: 300px;
}
</style>