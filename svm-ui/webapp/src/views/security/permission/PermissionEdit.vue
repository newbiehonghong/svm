<template>
    <div class="container">
        <el-form ref="permissionForm" :model="row" :rules="rules" label-width="80px">
            <el-form-item label="权限标识" prop="id" v-if="added">
                <el-input v-model="row.id" style="width:220px"></el-input>
            </el-form-item>
            <el-form-item label="权限名称" prop="name">
                <el-input v-model="row.name" style="width:220px"></el-input>
            </el-form-item>
            <el-form-item label="权限类型" prop="type">
                <el-select v-model="row.type" placeholder="请选择">
                    <el-option
                        v-for="item in permission_type"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>

            <div class="footer-box">
                <el-button type="primary" @click="doSave">提 交</el-button>
                <el-button @click="goBack">返 回</el-button>
            </div>
        </el-form>
    </div>
</template>

<script>
import dict from "@/dict/security";
import { savePermission, updatePermission } from "@/api/security/permission";
export default {
	data() {
		return {
			row: {},
			added: false,
			rules: {
				id: [{ required: true, message: "请输入权限标识", trigger: "blur" }],
				name: [{ required: true, message: "请输入权限名称", trigger: "blur" }],
				type: [{ required: true, message: "请选择权限类型", trigger: "blur" }]
			},
			permission_type: dict.security_permission_type
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
			if (this.params && this.params.row) {
				this.row = this.params.row;
			} else {
				this.added = true;
				this.resetRow();
			}
		},
		resetRow() {
			this.row = {
				id: null,
				name: null,
				type: null
			};
		},
		doSave() {
			this.$refs.permissionForm.validate(valid => {
				if (!valid) {
					return false;
				}

				if (this.added) {
					savePermission(this.row).then(res => {
						this.$message({ message: "保存成功", type: "success" });
						this.goBack(res, true);
					});
				} else {
					updatePermission(this.row).then(res => {
						this.$message({ message: "保存成功", type: "success" });
						this.goBack(res, false);
					});
				}
			});
		},
		goBack(row, added) {
			this.$emit("backward", "list", { row, added });
		}
	}
};
</script>
