<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-button class="button" @click="doCreate" type="primary" icon="el-icon-edit">新增</el-button>
            </div>
            <el-table :data="data" border style="width: 100%" ref="userTable">
                <el-table-column prop="name" label="账号" width="150"></el-table-column>
                <el-table-column prop="nickname" label="姓名" width="150"></el-table-column>
                <el-table-column label="操作" width="260">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="doEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" @click="doRole(scope.$index, scope.row)">分配角色</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="doDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="pageSize" :total="totalRecords" layout="prev, pager, next" @current-change="doCurrentChange"></el-pagination>
            </div>
        </div>

        <el-dialog title="用户信息" :visible.sync="editVisible" width="30%">
            <el-form ref="userForm" :model="currentRow" label-width="80px">
                <el-form-item label="账号">
                    <el-input v-model="currentRow.name"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="currentRow.nickname"></el-input>
                </el-form-item>
                <el-form-item label="密码" v-if="isCreate">
                    <el-input type="password" v-model="currentRow.password"></el-input>
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
    import { queryAllUsers, saveUser, updateUser, deleteUser, saveUserRoles } from 'svm-web-security/api/user';
    import { queryAllRoles, queryRolesByUserId } from 'svm-web-security/api/role';

    export default {
        name: 'UserManage',
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
                selectedRoles: []
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
                queryAllUsers(this.currentPage, this.pageSize).then((res) => {
                    this.data = res.data.list;
                    this.totalRecords = res.data.pages * this.pageSize;
                })
            },
            resetCurrentRow() {
                this.currentRow = {
                    "name": null,
                    "nickname": null,
                    "password": null
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
                    deleteUser(row.id).then((res) => {
                        this.data.splice(index, 1);
                    })
                });
            },
            saveEdit() {
                if(this.isCreate) {
                    saveUser(this.currentRow).then((res) => {
                        this.currentRow.id = res.data; 
                        this.data.unshift(this.currentRow);
                        this.editVisible = false;
                    });
                } else {
                    updateUser(this.currentRow).then((res) => {
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

                queryRolesByUserId(row.id).then((res) => {
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
                saveUserRoles(this.currentRow.id, this.selectedRoles).then((res) => {
                    this.roleVisible = false;
                });
            }
        }
    }

</script>
