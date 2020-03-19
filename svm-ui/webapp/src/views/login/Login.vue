<template>
    <div class="login-wrap">
        <div class="login-content">
            <div class="login-branding">
                <img src="@/assets/img/logo.png" class="logo" />
                <span class="title">管理系统</span>
            </div>
            <div class="login-outer-box">
                <div class="login-inner-box">
                    <el-form :model="loginInfo" :rules="rules" ref="loginForm">
                        <el-form-item prop="username">
                            <el-input
                                v-model="loginInfo.username"
                                placeholder="输入用户名"
                                v-focus
                                @keyup.enter.native="changeFocus"
                            ></el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input
                                type="password"
                                v-model="loginInfo.password"
                                ref="password"
                                placeholder="输入密码"
                                @keyup.enter.native="doLogin"
                            ></el-input>
                        </el-form-item>
                        <div class="btn-row">
                            <el-button
                                type="primary"
                                class="login-btn"
                                @click.native.prevent="doLogin"
                            >登录</el-button>
                        </div>
                    </el-form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { login } from "@/api/login";
import { asyncRouterMap, filterAsyncRouter } from "@/router";

export default {
	data: function() {
		return {
			loginInfo: {
				username: "admin",
				password: ""
			},
			rules: {
				username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
				password: [{ required: true, message: "请输入密码", trigger: "blur" }]
			}
		};
	},
	methods: {
		changeFocus() {
			this.$refs.password.focus();
		},
		doLogin() {
			this.$refs.loginForm.validate(valid => {
				if (!valid) {
					return false;
				}

				login(this.loginInfo.username, this.loginInfo.password).then(response => {
					var data = response;
					this.$store.commit("SET_TOKEN", data.token);
					this.$store.commit("SET_NICKNAME", data.nickname);
					let routes = filterAsyncRouter(asyncRouterMap, data.permissions);
					this.$store.commit("SET_ROUTES", routes); //Sidebar.vue里无法从this.$router上取路由数据
					this.$router.addRoutes(routes);
					this.$router.push("/");
				});
			});
		}
	}
};
</script>

<style lang="scss" scoped>
.login-wrap {
	align-items: center;
	background-color: #161719;
	background-image: url("~@/assets/img/bg.jpg");
	background-position: 50% center;
	background-repeat: no-repeat;
	background-size: cover;
	color: #d8d9da;
	display: flex;
	justify-content: center;
	margin-left: 0;
	min-height: 100vh;
	min-width: 100%;

	.login-content {
		flex: 1 0 100%;
		flex-direction: row;
		display: flex;
		justify-content: center;
		width: 100%;
		align-items: stretch;
		max-width: 700px;
		min-height: 230px;
		position: relative;
		z-index: 1;

		.login-branding {
			border-right: 1px solid #979797;
			padding-bottom: 10px;
			width: 25%;
			align-items: center;
			display: flex;
			justify-content: center;
			flex-direction: column;

			.logo {
				width: 128px;
				height: 128px;
			}
			.title {
				color: #000000;
				font-size: 32px;
				padding-top: 20px;
			}
		}
		.login-outer-box {
			align-items: center;
			display: flex;
			justify-content: center;
			overflow-y: hidden;

			.login-inner-box {
				padding: 26px 56px;
				width: 55%;
				flex-direction: column;
				flex-grow: 1;
				max-width: 415px;
				align-items: center;
				display: flex;
				justify-content: center;

				.el-form-item--medium {
					margin-bottom: 26px;
				}
				.btn-row {
					padding-top: 15px;
				}
				.login-btn {
					padding: 10px 20px;
				}
			}
		}
	}
}
</style>

<style lang="scss">
.login-wrap {
	.el-input__inner {
		border: 1px solid #6f6f6f;
		border-radius: 3px;
		background-color: #161719;
	}
}
</style>
