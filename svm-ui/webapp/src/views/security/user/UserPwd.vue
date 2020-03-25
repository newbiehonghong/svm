<template>
    <div class="container">
        <el-form ref="userForm" :model="row" :rules="rules" label-width="80px">
            <el-form-item label="姓名" prop="nickname">
                <el-input v-model="row.nickname" style="width:220px" disabled></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="row.password" style="width:220px"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPwd">
                <el-input type="password" v-model="row.confirmPwd" style="width:220px"></el-input>
            </el-form-item>
            <div class="footer-box">
                <el-button type="primary" @click="doSave">提 交</el-button>
                <el-button @click="goBack">返 回</el-button>
            </div>
        </el-form>
    </div>
</template>

<script>
import { updatePassword } from "@/api/security/user";

export default {
	data() {
		return {
			row: {},
			rules: {
				password: [{ required: true, message: "请输入密码", trigger: "blur" }],
				confirmPwd: [
					{
						required: true,
						trigger: "blur",
						validator: (rule, value, callback) => {
							if (value == "") {
								callback(new Error("请输入确认密码"));
							} else if (value !== this.row.password) {
								callback(new Error("两次输入的密码不一致"));
							} else {
								callback();
							}
						}
					}
				]
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
				this.row = {
					id: this.params.row.id,
					nickname: this.params.row.nickname,
					password: "",
					confirmPwd: ""
				};
			}
		},
		doSave() {
			this.$refs.userForm.validate(valid => {
				if (!valid) {
					return false;
				}

				var formData = {
					id: this.row.id,
					password: this.row.password
				};
				updatePassword(formData).then(res => {
					this.$message({ message: "保存成功", type: "success" });
					this.goBack();
				});
			});
		},
		goBack() {
			this.$emit("leave", "list");
		}
	}
};
</script>
