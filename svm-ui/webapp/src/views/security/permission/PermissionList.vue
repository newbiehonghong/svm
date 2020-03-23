<template>
    <div class="container">
        <el-row class="header-box">
            <el-col :span="12">
                <el-select
                    v-model="filters.type"
                    class="filter-item"
                    clearable
                    style="width:200px"
                    placeholder="选择权限类型过滤"
                >
                    <template slot="prefix">
                        <i class="el-input__icon el-icon-search"></i>
                    </template>
                    <el-option
                        v-for="item in permission_type"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
                <el-input
                    v-model="filters.name"
                    placeholder="输入权限名称过滤"
                    prefix-icon="el-icon-search"
                    style="width:200px"
                ></el-input>
            </el-col>
            <el-col :span="12" class="right">
                <el-button @click="doCreate" type="primary" icon="el-icon-edit">新增</el-button>
            </el-col>
        </el-row>

        <el-table :data="rows" stripe>
            <el-table-column prop="id" label="权限标识" width="240"></el-table-column>
            <el-table-column prop="name" label="权限名称" width="auto"></el-table-column>
            <el-table-column prop="type" label="权限类型" :formatter="decodeType" width="140"></el-table-column>
            <el-table-column label="操作" width="260">
                <template slot-scope="scope">
                    <el-tooltip content="分配角色" placement="bottom">
                        <el-button size="text" icon="icon-group" @click="doRole(scope)"></el-button>
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
import dict from "@/dict/security";
import { queryPermissions, deletePermission } from "@/api/security/permission";
export default {
	data() {
		return {
			data: [],
			filters: {
				type: "",
				name: ""
			},
			permission_type: dict.security_permission_type
		};
	},
	props: {
		params: { type: Object }
	},
	computed: {
		rows: function() {
			if (this.filters.type == "" && this.filters.name == "") {
				return this.data;
			}

			return this.data.filter(item => {
				var filtered = true;
				if (this.filters.type != "") {
					filtered = item.type.indexOf(this.filters.type) == 0;
				}
				if (filtered && this.filters.name != "") {
					filtered = item.name.indexOf(this.filters.name) >= 0;
				}
				return filtered;
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
			queryPermissions("all").then(res => {
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
		decodeType(row, column) {
			return dict.decode("security_permission_type", row["type"]);
		},
		doCreate() {
			this.$emit("forward", "edit", "新增权限");
		},
		doEdit({ $index, row }) {
			var params = { row: Object.assign({}, row) };
			this.$emit("forward", "edit", "编辑权限", params);
		},
		doDelete({ $index, row }) {
			this.$confirm("删除不可恢复，是否确定删除？", "警告", {
				type: "warning"
			}).then(() => {
				deletePermission(row.id).then(res => {
					this.data.splice($index, 1);
				});
			});
		},
		doRole({ $index, row }) {
			var params = { id: row.id };
			this.$emit("forward", "role", "分配角色", params);
		}
	}
};
</script>
