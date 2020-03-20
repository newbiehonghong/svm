<template>
    <div class="container security-role-container-p">
        <el-transfer
            filterable
            v-model="selectedPermissions"
            :data="permissions"
            :titles="['未分配权限项', '已分配权限项']"
        ></el-transfer>
        <div class="footer-box">
            <el-button type="primary" @click="doSave">提 交</el-button>
            <el-button @click="goBack">返 回</el-button>
        </div>
    </div>
</template>

<script>
import { saveRolePermissions } from "@/api/security/role";
import { queryPermissions, queryPermissionsByRoleId } from "@/api/security/permission";
export default {
	data() {
		return {
			id: "",
			permissionType: "",
			permissions: [],
			selectedPermissions: []
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
			this.permissionType = this.params.type;

			queryPermissions(this.permissionType).then(res => {
				var rows = [];
				res.forEach(row => {
					rows.push({ key: row.id, label: row.name });
				});
				this.permissions = rows;
			});
			queryPermissionsByRoleId(this.permissionType, this.id).then(res => {
				var rows = [];
				res.forEach(id => rows.push(id));
				this.selectedPermissions = rows;
			});
		},
		doSave() {
			saveRolePermissions(this.permissionType, this.id, this.selectedPermissions).then(
				res => {
					this.goBack();
				}
			);
		},
		goBack() {
			this.$emit("backward", "list");
		}
	}
};
</script>
<style>
.security-role-container-p .el-transfer-panel {
	width: 300px;
}
.security-role-container-p .el-transfer-panel__body {
	height: 352px;
}
.security-role-container-p .el-transfer-panel__list {
	height: 300px;
}
</style>
