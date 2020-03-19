<template>
    <div class="container security-user-container">
        <el-transfer filterable v-model="selectedRoles" :data="roles" :titles="['未分配角色', '已分配角色']"></el-transfer>
        <div class="footer-box">
            <el-button type="primary" @click="doSave">提 交</el-button>
            <el-button @click="goBack">返 回</el-button>
        </div>
    </div>
</template>

<script>
import { saveUserRoles } from "@/api/security/user";
import { queryAllRoles, queryRolesByUserId } from "@/api/security/role";

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
			queryRolesByUserId(this.id).then(res => {
				var rows = [];
				res.forEach(id => rows.push(id));
				this.selectedRoles = rows;
			});
		},
		doSave() {
			saveUserRoles(this.id, this.selectedRoles).then(res => {
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
.security-user-container .el-transfer-panel {
	width: 300px;
}
.security-user-container .el-transfer-panel__body {
	height: 352px;
}
.security-user-container .el-transfer-panel__list {
	height: 300px;
}
</style>