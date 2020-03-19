<template>
    <div>
        <div>
            <permission-list v-show="visible.list" :params="params.list" @forward="doForward"></permission-list>
            <permission-edit v-if="visible.edit" :params="params.edit" @backward="doBackward"></permission-edit>
            <permission-role v-if="visible.role" :params="params.role" @backward="doBackward"></permission-role>
        </div>
    </div>
</template>

<script>
import base from "@/components/BaseTablePage";
import PermissionList from "./PermissionList";
import PermissionEdit from "./PermissionEdit";
import PermissionRole from "./PermissionRole";

export default {
	name: "PermissionManage",
	mixins: [base],
	components: { PermissionList, PermissionEdit, PermissionRole },
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
