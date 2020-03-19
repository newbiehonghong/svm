<template>
    <div class="container">
        <el-form ref="userForm" :model="row" :rules="rules" label-width="80px">
            <el-form-item label="账号" prop="name">
                <el-input v-model="row.name"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="nickname">
                <el-input v-model="row.nickname"></el-input>
            </el-form-item>
            <el-form-item label="密码" v-if="added" prop="password">
                <el-input type="password" v-model="row.password"></el-input>
            </el-form-item>

            <div class="footer-box">
                <el-button type="primary" @click="doSave">提 交</el-button>
                <el-button @click="goBack">返 回</el-button>
            </div>
        </el-form>
    </div>
</template>

<script>
import { saveUser, updateUser } from "@/api/security/user";

export default {
	data() {
		return {
			row: {},
			added: false,
			rules: {
				name: [{ required: true, message: "请输入账号", trigger: "blur" }],
				nickname: [{ required: true, message: "请输入姓名", trigger: "blur" }]
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
				this.rules["password"] = [
					{ required: true, message: "请输入密码", trigger: "blur" }
				];
			}
		},
		resetRow() {
			this.row = {
				name: null,
				nickname: null,
				password: null
			};
		},
		doSave() {
			this.$refs.userForm.validate(valid => {
				if (!valid) {
					return false;
				}

				if (this.added) {
					saveUser(this.row).then(res => {
						this.$message({ message: "保存成功", type: "success" });
						this.goBack(res, true);
					});
				} else {
					updateUser(this.row).then(res => {
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
