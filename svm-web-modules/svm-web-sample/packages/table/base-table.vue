<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-button class="button" @click="doCreate" type="primary" icon="el-icon-edit">新增</el-button>
                <el-button class="button" @click="doDelete" icon="el-icon-delete">删除</el-button>
            </div>
            <el-table :data="data" border style="width: 100%" ref="userTable" @selection-change="doSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="birthday" label="出生日期" sortable width="150"></el-table-column>
                <el-table-column prop="name" label="姓名" width="150"></el-table-column>
                <el-table-column prop="province" label="省" :formatter="decodeProvince" width="120"></el-table-column>
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                        <el-button size="text" icon="el-icon-edit" @click="doEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="text" icon="el-icon-edit-outline" @click="doOpenTab(scope.$index, scope.row)">新tab页编辑</el-button>
                        <el-button size="text" icon="el-icon-edit-outline" @click="doOpenDialog(scope.$index, scope.row)">对话框选择</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination 
                    :page-size="pageSize" 
                    :total="totalRecords" 
                    layout="prev, pager, next" 
                    @current-change="doCurrentChange">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="userForm" :model="currentRow" label-width="80px">
                <el-form-item label="出生日期">
                    <el-date-picker type="date" placeholder="选择日期" v-model="currentRow.birthday" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="currentRow.name"></el-input>
                </el-form-item>
                <el-form-item label="省">
                    <el-select v-model="currentRow.province" clearable placeholder="请选择">
                        <el-option v-for="item in sample_province" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveEdit">确 定</el-button>
                <el-button @click="editVisible = false">取 消</el-button>
            </span>
        </el-dialog>

        <!--如果弹窗内容较多，出现了滚动条，需要每次打开还原到顶部，则需要添加v-if指令，因为这个指令是动态渲染内容的-->
        <dept-tree-dialog v-if="deptVisible" @close="doCloseDialog" :filter="dialogFilter"></dept-tree-dialog>
    </div>
</template>

<script src="./base-table-def.js"></script>