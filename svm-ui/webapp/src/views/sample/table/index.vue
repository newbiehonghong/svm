<template>
    <div>
        <div>
            <user-list v-show="visible.list" :params="params.list" @forward="doForward"></user-list>
            <user-edit v-if="visible.edit" :params="params.edit" @backward="doBackward"></user-edit>
        </div>
    </div>
</template>

<script>
import UserList from "./UserList";
import UserEdit from "./UserEdit";

export default {
	name: "SampleUser",
	components: { UserList, UserEdit },
	data() {
		return {
			params: {
				list: {},
				edit: {}
			},
			visible: {
				list: false,
				edit: false
			}
		};
	},
	created() {
		this.init();
	},
	methods: {
		init() {
			this.visible.list = true;
			this.bus.$on("nav.jump", name => {
				if (name.indexOf("/") == 0) {
					name = "list";
				}
				for (let key in this.visible) {
					this.visible[key] = key == name;
				}
			});
		},
		toggleComponent(name, params) {
			this.params[name] = params;
			for (let key in this.visible) {
				this.visible[key] = key == name;
			}
		},
		doForward(name, title, params) {
			this.toggleComponent(name, params);
			this.bus.$emit("nav.forward", { name, title });
		},
		doBackward(name, params) {
			this.toggleComponent(name, params);
			this.bus.$emit("nav.backward");
		}
	}
};
</script>
