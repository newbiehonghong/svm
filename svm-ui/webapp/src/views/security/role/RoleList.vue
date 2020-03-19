<template>
    <div class="container">
        <el-row class="header-box">
            <el-col :span="12">
                <el-input
                    v-model="filters.name"
                    placeholder="输入角色名称过滤"
                    prefix-icon="el-icon-search"
                    style="width:200px"
                ></el-input>
            </el-col>
            <el-col :span="12" class="right">
                <el-button @click="doCreate" type="primary" icon="el-icon-edit">新增</el-button>
            </el-col>
        </el-row>

        <el-table :data="rows" stripe>
            <el-table-column prop="id" label="角色标识" width="200"></el-table-column>
            <el-table-column prop="name" label="角色名称" width="auto"></el-table-column>
            <el-table-column label="操作" width="360">
                <template slot-scope="scope">
                    <el-tooltip content="分配用户" placement="bottom">
                        <el-button size="text" icon="el-icon-user" @click="doUser(scope)"></el-button>
                    </el-tooltip>
                    <el-tooltip content="分配页面权限" placement="bottom">
                        <el-button size="text" icon="el-icon-menu" @click="doPagePermission(scope)"></el-button>
                    </el-tooltip>
                    <el-tooltip content="分配服务权限" placement="bottom">
                        <el-button
                            size="text"
                            icon="el-icon-s-operation"
                            @click="doServicePermission(scope)"
                        ></el-button>
                    </el-tooltip>
                    <el-tooltip content="编辑" placement="bottom">
                        <el-button size="text" icon="el-icon-edit" @click="doEdit(scope)"></el-button>
                    </el-tooltip>
                    <el-tooltip content="删除" placement="bottom">
                        <el-button size="text" icon="el-icon-delete" @click="doDelete(scope)"></el-button>
                    </el-tooltip>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
import { queryAllRoles, deleteRole } from "@/api/security/role";
export default {
	data() {
		return {
			data: [],
			filters: {
				name: ""
			}
		};
	},
	props: {
		params: { type: Object }
	},
	computed: {
		rows: function() {
			if (this.filters.name == "") {
				return this.data;
			}
			return this.data.filter(item => {
				return item.name.indexOf(this.filters.name) == 0;
			});
		}
	},
	watch: {
		params(value) {
			if (value) {
				this.refreshRow(value);
			}
		}
	},
	created() {
		this.init();
	},
	methods: {
		init() {
			queryAllRoles().then(res => {
				this.data = res;
			});
		},
		refreshRow({ row, added }) {
			if (added) {
				this.data.unshift(row);
			} else {
				for (let i = 0, end = this.data.length; i < end; i++) {
					if (row.id == this.data[i].id) {
						this.$set(this.data, i, row);
						break;
					}
				}
			}
		},
		doCreate() {
			this.$emit("forward", "edit", "新增角色");
		},
		doEdit({ $index, row }) {
			var params = { row: Object.assign({}, row) };
			this.$emit("forward", "edit", "编辑角色", params);
		},
		doDelete({ $index, row }) {
			this.$confirm("删除不可恢复，是否确定删除？", "警告", {
				type: "warning"
			}).then(() => {
				deleteRole(row.id).then(res => {
					this.data.splice($index, 1);
				});
			});
		},
		doUser({ $index, row }) {
			var params = { id: row.id };
			this.$emit("forward", "user", "分配用户", params);
		},
		doPagePermission({ $index, row }) {
			var params = { id: row.id, type: "page" };
			this.$emit("forward", "permission", "分配页面权限", params);
		},
		doServicePermission({ $index, row }) {
			var params = { id: row.id, type: "service" };
			this.$emit("forward", "permission", "分配服务权限", params);
		}
	}
};
</script>
