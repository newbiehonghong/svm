<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-button class="button" @click="doCreate" type="primary" icon="el-icon-edit">新增</el-button>
            </div>
            <el-table :data="data" border style="width: 100%" ref="roleTable">
                <el-table-column prop="id" label="角色标识" width="150"></el-table-column>
                <el-table-column prop="name" label="角色名称" width="150"></el-table-column>
                <el-table-column label="操作" width="360">
                    <template slot-scope="scope">
                        <el-button size="small" @click="doEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" @click="doUser(scope.$index, scope.row)">分配用户</el-button>
                        <el-button size="small" @click="doPermission(scope.$index, scope.row)">分配权限项</el-button>
                        <el-button size="small" type="danger" @click="doDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="pageSize" :total="totalRecords" layout="prev, pager, next" @current-change="doCurrentChange"></el-pagination>
            </div>
        </div>

        <el-dialog title="角色信息" :visible.sync="editVisible" width="30%">
            <el-form ref="roleForm" :model="currentRow" label-width="80px">
                <el-form-item label="角色标识" v-if="isCreate">
                    <el-input v-model="currentRow.id"></el-input>
                </el-form-item>
                <el-form-item label="角色名称">
                    <el-input v-model="currentRow.name"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveEdit">确 定</el-button>
                <el-button @click="editVisible = false">取 消</el-button>
            </span>
        </el-dialog>

        <el-dialog title="分配用户" :visible.sync="userVisible" width="50%">
            <el-transfer filterable v-model="selectedUsers" :data="users" 
                :titles="['未分配用户', '已分配用户']"></el-transfer>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveUser">确 定</el-button>
                <el-button @click="userVisible = false">取 消</el-button>
            </span>
        </el-dialog>

        <el-dialog title="分配权限项" :visible.sync="permissionVisible" width="50%">
            <el-transfer filterable v-model="selectedPermissions" :data="permissions" 
                :titles="['未分配权限项', '已分配权限项']"></el-transfer>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="savePermission">确 定</el-button>
                <el-button @click="permissionVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import { queryAllRoles, saveRole, updateRole, deleteRole, queryAllUsers, queryUsersByRoleId, saveRoleUsers, queryPermissions, queryPermissionsByRoleId, saveRolePermissions } from '@/api/security';

    export default {
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
                userVisible: false,
                users: [],
                selectedUsers: [],
                permissionVisible: false,
                permissions: [],
                selectedPermissions: []
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
                queryAllRoles(this.currentPage, this.pageSize).then((res) => {
                    this.data = res.data.list;
                    this.totalRecords = res.data.pages * this.pageSize;
                })
            },
            resetCurrentRow() {
                this.currentRow = {
                    "id": null,
                    "name": null
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
                    deleteRole(row.id).then((res) => {
                        this.data.splice(index, 1);
                    })
                });
            },
            saveEdit() {
                if(this.isCreate) {
                    saveRole(this.currentRow).then((res) => {
                        this.data.unshift(this.currentRow);
                        this.editVisible = false;
                    });
                } else {
                    updateRole(this.currentRow).then((res) => {
                        this.$set(this.data, this.currentRowIndex, this.currentRow);
                        this.editVisible = false;
                    });
                }
            },
            doUser(index, row) {
                if(this.users.length == 0) {
                    this.getUsers(row.id);
                }
                this.currentRow = row;

                queryUsersByRoleId(row.id).then((res) => {
                    this.selectedUsers.splice(0, this.selectedUsers.length);
                    res.data.forEach(id => this.selectedUsers.push(id));
                });
                this.userVisible = true;
            },
            getUsers(id) {
                queryAllUsers(1, 1000).then((res) => {
                    var rows = res.data.list;
                    rows.forEach(row => {
                        this.users.push({key: row.id, label: row.name});
                    });
                });
            },
            saveUser() {
                saveRoleUsers(this.currentRow.id, this.selectedUsers).then((res) => {
                    this.userVisible = false;
                });
            },
            doPermission(index, row) {
                if(this.permissions.length == 0) {
                    this.getPermissions(row.id);
                }
                this.currentRow = row;

                queryPermissionsByRoleId(row.id).then((res) => {
                    this.selectedPermissions.splice(0, this.selectedPermissions.length);
                    res.data.forEach(id => this.selectedPermissions.push(id));
                });
                this.permissionVisible = true;
            },
            getPermissions(id) {
                queryPermissions("", 1, 1000).then((res) => {
                    var rows = res.data.list;
                    rows.forEach(row => {
                        this.permissions.push({key: row.id, label: row.type + '/' + row.name});
                    });
                });
            },
            savePermission() {
                saveRolePermissions(this.currentRow.id, this.selectedPermissions).then((res) => {
                    this.permissionVisible = false;
                });
            }
        }
    }

</script>
