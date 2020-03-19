<template>
    <div class="sidebar">
        <div class="logo-area" @click="showDashboard">
            <img src="@/assets/img/logo.png" />
        </div>
        <div class="main-menu">
            <el-menu
                :default-active="onRoutes"
                :collapse="true"
                unique-opened
                router
                :leftPadding="16"
            >
                <template v-for="item in menus">
                    <template v-if="item.subs">
                        <el-submenu :index="item.index" :key="item.index">
                            <template slot="title">
                                <i :class="item.icon"></i>
                                <span slot="title">{{ item.title }}</span>
                            </template>
                            <el-menu-item
                                v-for="(subItem,i) in item.subs"
                                :key="i"
                                :index="subItem.index"
                            >
                                <i :class="subItem.icon"></i>
                                <span>{{ subItem.title }}</span>
                            </el-menu-item>
                        </el-submenu>
                    </template>
                    <template v-else>
                        <el-menu-item :index="item.index" :key="item.index">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </el-menu-item>
                    </template>
                </template>
            </el-menu>
        </div>
        <div class="admin_menu">
            <el-menu :collapse="true">
                <el-submenu index="9" key="9">
                    <template slot="title">
                        <i class="icon-admin"></i>
                        <span slot="title">ADMIN</span>
                    </template>
                    <el-menu-item index="91" key="91" @click="handleFullScreen">
                        <i
                            :class="[!fullscreen? 'icon-admin-full-screen':'icon-admin-return-full-screen']"
                        ></i>
                        <span>{{!fullscreen ? '全屏' : '退出全屏'}}</span>
                    </el-menu-item>
                    <el-menu-item index="92" key="92" @click="changePwd">
                        <i class="icon-admin-change-password"></i>
                        <span>修改密码</span>
                    </el-menu-item>
                    <el-menu-item index="93" key="93" @click="logout">
                        <i class="icon-admin-logout"></i>
                        <span>退出登录</span>
                    </el-menu-item>
                </el-submenu>
            </el-menu>
        </div>
        <change-pwd v-if="dialogVisible" @close="closeDialog"></change-pwd>
    </div>
</template>

<script>
import menu from "./menu";
import ChangePwd from "./ChangePwd";
import screenfull from "screenfull";

export default {
	components: { ChangePwd },
	data() {
		return {
			items: menu,
			fullscreen: false,
			dialogVisible: false
		};
	},

	computed: {
		menus() {
			let routeList = [];
			// 将路由数据扁平化为一级
			function flatRoutes(routes) {
				for (let route of routes) {
					if (route.children && route.children.length > 0) {
						flatRoutes(route.children);
					} else {
						routeList.push(route.path.replace("/", ""));
					}
				}
			}
			flatRoutes(this.$store.getters.routes);

			function filterMenus(items) {
				return items.filter(item => {
					if (item.subs && item.subs.length > 0) {
						item.subs = filterMenus(item.subs);
						if (item.subs && item.subs.length > 0) {
							return true;
						}
						return false;
					}
					return routeList.indexOf(item.index) >= 0;
				});
			}

			return filterMenus(this.items);
		},
		onRoutes() {
			return this.$route.path.replace("/", "");
		}
	},
	methods: {
		showDashboard() {
			this.$router.push("/dashboard");
		},
		logout() {
			location.reload();
		},
		handleFullScreen() {
			if (!screenfull.enabled) {
				this.$message({
					message: "您的浏览器不支持全屏",
					type: "warning"
				});
				return false;
			}
			screenfull.toggle();
			this.fullscreen = !this.fullscreen;
		},
		changePwd() {
			this.dialogVisible = true;
		},
		closeDialog() {
			this.dialogVisible = false;
		}
	}
};
</script>

<style lang="scss" scoped>
.sidebar {
	display: flex;
	left: 0;
	top: 0;
	z-index: 1025;
	height: 100%;
	flex-direction: column;

	&::-webkit-scrollbar {
		width: 0;
	}

	.logo-area {
		height: 56px;
		min-height: 56px;
		padding: 6px 14px;
		cursor: pointer;
		img {
			left: 0;
			position: relative;
			top: 5px;
			width: 48px;
			height: 48px;
		}
	}

	.main-menu {
		margin-top: 20px;
		height: 380px;
		flex-grow: 10;
	}
	.admin_menu {
		flex-grow: 1;
		padding-bottom: 10px;
	}
}

.el-submenu__title i {
	color: #d8d9da;
}

.el-menu-item i {
	color: #d8d9da;
}
</style>
