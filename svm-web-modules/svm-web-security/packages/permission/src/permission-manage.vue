<template>
    <div>
        <div class="container">
            <div class="filter-box">
                <el-select v-model="filters.type" class="filter-item" clearable style="width:150px" placeholder="权限项类型">
                    <el-option v-for="item in permission_type" :key="item.value" :label="item.label" :value="item.value"></el-option>
                </el-select>
                <el-button class="button" icon="el-icon-search" @click="doFilter">过滤</el-button>
            </div>
            <div class="handle-box">
                <el-button class="button" @click="doCreate" type="primary" icon="el-icon-edit">新增</el-button>
            </div>
            <el-table :data="data" border style="width: 100%" ref="permissionTable">
                <el-table-column prop="id" label="权限项标识" width="200"></el-table-column>
                <el-table-column prop="name" label="权限项名称" width="300"></el-table-column>
                <el-table-column prop="type" label="权限项类型" :formatter="decodeType" width="100"></el-table-column>
                <el-table-column label="操作" width="260">
                    <template slot-scope="scope">
                        <el-button size="text" icon="el-icon-edit" @click="doEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="text" @click="doRole(scope.$index, scope.row)">分配角色</el-button>
                        <el-button size="text" icon="el-icon-delete" @click="doDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="pageSize" :total="totalRecords" layout="prev, pager, next" @current-change="doCurrentChange"></el-pagination>
            </div>
        </div>

        <el-dialog title="权限项信息" :visible.sync="editVisible" width="40%">
            <el-form ref="permissionForm" :model="currentRow" label-width="100px">
                <el-form-item label="权限项标识" v-if="isCreate">
                    <el-input v-model="currentRow.id"></el-input>
                </el-form-item>
                <el-form-item label="权限项名称">
                    <el-input v-model="currentRow.name"></el-input>
                </el-form-item>
                <el-form-item label="权限项类型">
                    <el-select v-model="currentRow.type" clearable placeholder="请选择">
                        <el-option v-for="item in permission_type" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveEdit">确 定</el-button>
                <el-button @click="editVisible = false">取 消</el-button>
            </span>
        </el-dialog>

        <el-dialog title="分配角色" :visible.sync="roleVisible" width="50%">
            <el-transfer filterable v-model="selectedRoles" :data="roles" 
                :titles="['未分配角色', '已分配角色']"></el-transfer>>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveRole">确 定</el-button>
                <el-button @click="roleVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import dict from "svm-web-security/dict";
    import { queryPermissions, savePermission, updatePermission, deletePermission, savePermissionRoles } from 'svm-web-security/api/permission';
    import { queryAllRoles, queryRolesByPermissionId } from 'svm-web-security/api/role';

    export default {
        name: 'PermissionManage',
        data() {
            return {
                data: [],
                currentPage: 1,
                currentRow: {},
                currentRowIndex: -1,
                pageSize: 10,
                totalRecords: 0,
                editVisible: false,
                isCreate: false,
                roleVisible: false,
                roles: [],
                selectedRoles: [],
                filters: {
                    type: ''
                },
                permission_type: dict.security_permission_type
            }
        },
        created() {
            this.getData();
        },
        methods: {
            doCurrentChange(val) {
                this.currentPage = val;
                this.getData();
            },
            getData() {
                queryPermissions(this.filters.type, this.currentPage, this.pageSize).then((res) => {
                    this.data = res.data.list;
                    this.totalRecords = res.data.pages * this.pageSize;
                })
            },
            doFilter() {
                this.doCurrentChange(1);
            },
            decodeType(row, column) {
                var value = row.type;
                var item = this.permission_type.find(element => element.value == value);
                return item && item.label || value;
            },
            resetCurrentRow() {
                this.currentRow = {
                    "id": null,
                    "name": null,
                    "type": "service"
                };
            },
            doCreate() {
                this.resetCurrentRow();
                this.isCreate = true;
                this.editVisible = true;
            },
            doEdit(index, row) {
                this.currentRowIndex = index;
                this.currentRow = Object.assign({}, row);
                this.isCreate = false;
                this.editVisible = true;
            },
            doDelete(index, row) {
                this.$confirm('删除不可恢复，是否确定删除？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deletePermission(row.id).then((res) => {
                        this.data.splice(index, 1);
                    })
                });
            },
            saveEdit() {
                if(this.isCreate) {
                    savePermission(this.currentRow).then((res) => {
                        this.data.unshift(this.currentRow);
                        this.editVisible = false;
                    });
                } else {
                    updatePermission(this.currentRow).then((res) => {
                        this.$set(this.data, this.currentRowIndex, this.currentRow);
                        this.editVisible = false;
                    });
                }
            },
            doRole(index, row) {
                if(this.roles.length == 0) {
                    this.getRoles(row.id);
                }
                this.currentRow = row;

                queryRolesByPermissionId(row.id).then((res) => {
                    this.selectedRoles.splice(0, this.selectedRoles.length);
                    res.data.forEach(id => this.selectedRoles.push(id));
                });
                this.roleVisible = true;
            },
            getRoles(id) {
                queryAllRoles(1, 1000).then((res) => {
                    var rows = res.data.list;
                    rows.forEach(row => {
                        this.roles.push({key: row.id, label: row.name});
                    });
                });
            },
            saveRole() {
                savePermissionRoles(this.currentRow.id, this.selectedRoles).then((res) => {
                    this.roleVisible = false;
                });
            }
        }
    }

</script>
