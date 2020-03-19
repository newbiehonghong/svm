<template>
    <div>
        <div>
            <user-list v-show="visible.list" :params="params.list" @forward="doForward"></user-list>
            <user-edit v-if="visible.edit" :params="params.edit" @backward="doBackward"></user-edit>
            <user-role v-if="visible.role" :params="params.role" @backward="doBackward"></user-role>
        </div>
    </div>
</template>

<script>
import base from "@/components/BaseTablePage";
import UserList from "./UserList";
import UserEdit from "./UserEdit";
import UserRole from "./UserRole";

export default {
	name: "UserManage",
	mixins: [base],
	components: { UserList, UserEdit, UserRole },
	data() {
		return {
			params: {
				role: {}
			},
			visible: {
				role: false
			}
		};
	}
};
</script>
