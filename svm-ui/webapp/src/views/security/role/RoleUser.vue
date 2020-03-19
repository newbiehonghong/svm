<template>
    <div class="container security-role-container">
        <el-transfer filterable v-model="selectedUsers" :data="users" :titles="['未分配用户', '已分配用户']"></el-transfer>
        <div class="footer-box">
            <el-button type="primary" @click="doSave">提 交</el-button>
            <el-button @click="goBack">返 回</el-button>
        </div>
    </div>
</template>

<script>
import { saveRoleUsers } from "@/api/security/role";
import { queryAllUsers, queryUsersByRoleId } from "@/api/security/user";
export default {
	data() {
		return {
			id: "",
			users: [],
			selectedUsers: []
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

			queryAllUsers().then(res => {
				var rows = [];
				res.forEach(row => {
					rows.push({ key: row.id, label: row.nickname });
				});
				this.users = rows;
			});
			queryUsersByRoleId(this.id).then(res => {
				var rows = [];
				res.forEach(id => rows.push(id));
				this.selectedUsers = rows;
			});
		},
		doSave() {
			saveRoleUsers(this.id, this.selectedUsers).then(res => {
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
.security-role-container .el-transfer-panel {
	width: 300px;
}
.security-role-container .el-transfer-panel__body {
	height: 352px;
}
.security-role-container .el-transfer-panel__list {
	height: 300px;
}
</style>