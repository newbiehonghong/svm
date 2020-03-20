<template>
    <div class="container">
        <el-form ref="roleForm" :model="row" :rules="rules" label-width="80px">
            <el-form-item label="角色标识" prop="id" v-if="added">
                <el-input v-model="row.id"></el-input>
            </el-form-item>
            <el-form-item label="角色名称" prop="name">
                <el-input v-model="row.name"></el-input>
            </el-form-item>

            <div class="footer-box">
                <el-button type="primary" @click="doSave">提 交</el-button>
                <el-button @click="goBack">返 回</el-button>
            </div>
        </el-form>
    </div>
</template>           
     
<script>
import { saveRole, updateRole } from "@/api/security/role";
export default {
	data() {
		return {
			row: {},
			added: false,
			rules: {
				id: [{ required: true, message: "请输入角色标识", trigger: "blur" }],
				name: [{ required: true, message: "请输入角色名称", trigger: "blur" }]
			}
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
				name: null
			};
		},
		doSave() {
			this.$refs.roleForm.validate(valid => {
				if (!valid) {
					return false;
				}

				if (this.added) {
					saveRole(this.row).then(res => {
						this.$message({ message: "保存成功", type: "success" });
						this.goBack(res, true);
					});
				} else {
					updateRole(this.row).then(res => {
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
