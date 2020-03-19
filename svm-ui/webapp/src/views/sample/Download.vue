<template>
    <div>
        <div class="container">
            <el-table :data="data" border style="width: 100%" ref="userTable">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="birthday" label="出生日期" sortable width="150"></el-table-column>
                <el-table-column prop="name" label="姓名" width="150"></el-table-column>
                <el-table-column prop="province" label="省" :formatter="decodeProvince" width="120"></el-table-column>
                <el-table-column label="照片">
                    <template slot-scope="scope" v-if="hasPicture(scope)">
                        <img :src="getImageUrl(scope.row.attachmentId)" class="picture"/>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope" v-if="hasPicture(scope)">
                        <el-button size="small" @click="doDownload(scope.$index, scope.row)">下载</el-button>
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
    </div>
</template>

<script>
    import dict from "@/dict/sample";
    import { apiURL, getToken } from '@/utils/request';
    import { queryDownloadUsers, downloadPicture } from '@/api/sample/user';

    export default {
        name: 'DownloadTable',
        data() {
            return {
                data: [],
                currentPage: 1,
                pageSize: 10,
                totalRecords: 0,
                sample_province: dict.sample_province
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
                queryDownloadUsers(this.currentPage).then((res) => {
                    this.data = res.list;
                    this.totalRecords = res.pages * this.pageSize;
                })
            },
            decodeProvince(row, column) {
                var value = row.province;
                var item = this.sample_province.find(element => element.value == value);
                return item && item.label || value;
            },
            doDownload(index, row) {
                downloadPicture(row.attachmentId).then((res) => {
                    let blob = res;
                    let downloadElement = this.$global.getDownloadElement();
                    let url = window.URL.createObjectURL(new Blob([blob]));
                    downloadElement.href = url;
                    downloadElement.setAttribute('download', row.fileName);
                    downloadElement.click();
                })
            },
            hasPicture(scope) {
                return scope.row.attachmentId != null;
            },
            getImageUrl(attachmentId) {
                return apiURL + "/sample/user/download/" + attachmentId + "?" + getToken();
            }
        }
    }

</script>

<style scoped>
    .picture {
        width: 80px;
        height: 60px;
    }
</style>
