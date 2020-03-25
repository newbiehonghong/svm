<template>
    <div>
        <div>
            <user-list v-show="visible.list" :params="params.list" @forward="doForward"></user-list>
            <user-edit v-if="visible.edit" :params="params.edit" @backward="doBackward"></user-edit>
            <user-role v-if="visible.role" :params="params.role" @backward="doBackward"></user-role>
            <user-pwd v-if="visible.pwd" :params="params.pwd" @leave="toggleComponent"></user-pwd>
        </div>
    </div>
</template>

<script>
import base from "@/components/BaseTablePage";
import UserList from "./UserList";
import UserEdit from "./UserEdit";
import UserRole from "./UserRole";
import UserPwd from "./UserPwd";

export default {
	name: "UserManage",
	mixins: [base],
	components: { UserList, UserEdit, UserRole, UserPwd },
	data() {
		return {
			params: {
				role: {},
				pwd: {}
			},
			visible: {
				role: false,
				pwd: false
			}
		};
	}
};
</script>
