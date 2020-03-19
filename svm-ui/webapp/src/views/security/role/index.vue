<template>
    <div>
        <div>
            <role-list v-show="visible.list" :params="params.list" @forward="doForward"></role-list>
            <role-edit v-if="visible.edit" :params="params.edit" @backward="doBackward"></role-edit>
            <role-user v-if="visible.user" :params="params.user" @backward="doBackward"></role-user>
            <role-permission
                v-if="visible.permission"
                :params="params.permission"
                @backward="doBackward"
            ></role-permission>
        </div>
    </div>
</template>

<script>
import base from "@/components/BaseTablePage";
import RoleList from "./RoleList";
import RoleEdit from "./RoleEdit";
import RoleUser from "./RoleUser";
import RolePermission from "./RolePermission";

export default {
	name: "RoleManage",
	mixins: [base],
	components: { RoleList, RoleEdit, RoleUser, RolePermission },
	data() {
		return {
			params: {
				user: {},
				permission: {}
			},
			visible: {
				user: false,
				permission: false
			}
		};
	}
};
</script>
