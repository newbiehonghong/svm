<template>
    <div class="header">
        <el-row align="middle" type="flex">
            <el-col style="width:20px">
                <div class="page_header_icon">
                    <i class="icon-nav"></i>
                </div>
            </el-col>
            <el-col style="padding-left: 10px">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item v-for="item in items" :key="item.name">
                        <span v-if="item.visible">{{item.title}}</span>
                        <a v-else @click="toggleComponent(item.name)">{{item.title}}</a>
                    </el-breadcrumb-item>
                </el-breadcrumb>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
	data() {
		return {
			items: [
				{
					name: "/dashboard",
					title: "系统首页",
					visible: true
				}
			]
		};
	},
	watch: {
		$route(to, from) {
			this.items = [];
			this.items.push({
				name: to.path,
				title: to.meta.title,
				visible: true
			});
		}
	},
	created() {
		this.bus.$on("nav.forward", item => {
			var index = this.items.length - 1;
			var lastItem = this.items[index];
			lastItem.visible = false;
			this.$set(this.items, index, lastItem);

			item.visible = true;
			this.items.push(item);
		});
		this.bus.$on("nav.backward", () => {
			this.items.splice(this.items.length - 1);

			var index = this.items.length - 1;
			var lastItem = this.items[index];
			lastItem.visible = true;
			this.$set(this.items, index, lastItem);
		});
	},
	methods: {
		toggleComponent(name) {
			var i = 0;
			for (let end = this.items.length; i < end; i++) {
				if (this.items[i].name == name) {
					break;
				}
			}
			this.items.splice(i + 1);
			this.bus.$emit("nav.jump", name);
		}
	}
};
</script>
