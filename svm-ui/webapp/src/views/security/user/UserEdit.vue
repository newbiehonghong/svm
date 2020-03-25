<template>
    <div class="container">
        <el-form ref="userForm" :model="row" :rules="rules" label-width="80px">
            <el-form-item label="账号" prop="name">
                <el-input v-model="row.name" style="width:220px"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="nickname">
                <el-input v-model="row.nickname" style="width:220px"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" v-if="added">
                <el-input type="password" v-model="row.password" style="width:220px"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPwd" v-if="added">
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
				this.rules["confirmPwd"] = [
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
				];
			}
		},
		resetRow() {
			this.row = {
				name: "",
				nickname: "",
				password: "",
				confirmPwd: "" //不把这个属性放在row里，校验函数取不到值
			};
		},
		doSave() {
			this.$refs.userForm.validate(valid => {
				if (!valid) {
					return false;
				}

				if (this.added) {
					delete this.row.confirmPwd;
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
