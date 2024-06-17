<template>
    <div>
        <el-table :data="tableData">
            <el-table-column prop="id" label="id" width="140">
            </el-table-column>
            <!-- <el-table-column prop="age" label="年龄" width="140">
            </el-table-column> -->
            <el-table-column prop="username" label="姓名" width="120">
            </el-table-column>
            <el-table-column prop="password" label="密码" width="120">
            </el-table-column>

            <!--操作列-->
            <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                    <!--scope.row 当前行-->
                    <el-link type="warning" size="mini" @click="showUpdate(scope.row.id)">编辑</el-link> | &nbsp;&nbsp;
                    <el-link @click="deleteUser(scope.row.id)" type="danger" size="mini">删除</el-link>

                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <el-pagination background layout="prev, pager, next" :page-size="pageSize" :total="total"
            @current-change="page">
        </el-pagination>
    </div>
</template>
<style>
.el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
}

.el-aside {
    color: #333;
}
</style>

<script>
import axios from 'axios'
export default {
    data() {
        const item = {
            date: '2016-05-02',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
        };
        return {
            pageSize: 5,
            total: 5,
            tableData: Array(5).fill(item)
        }
    },
    methods: {
        page(currentPage) {
            var _this = this
            axios.get('http://localhost:8088/userlist/' + (currentPage-1)+"/2").then(function (resp) {
                _this.tableData = resp.data.content
                _this.pageSize = resp.data.size
                _this.total = resp.data.totalElements
            })
        },
        showUpdate(rowId) {
            console.log(rowId);
            //this.$router.push('/update')//这样id就没有带过去 了
            this.$router.push({
                path: '/updateUser',
                query: {
                    id: rowId
                }
            })
        },
        deleteUser(rowId) {
            const _this = this

            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios.get('http://localhost:8088/deleteUser?userId=' + rowId).then(function (resp) {
                        if (resp.data === 'success') {
                            console.log('delete  success....')
                            window.location.reload()//刷新界面
                        }
                    });
                _this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
            }).catch(() => {
                _this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

           
        }
    },
    created() {
        console.log('this:', this)
        var _this = this
        // http://localhost:8080/
        axios.get('http://localhost:8088/userlist/0/2').then(function (resp) {
            console.log(resp)
            console.log('axios-->this:', this)
            //把响应的数据赋值给绑定的tableData
            // this.tableData = resp.data  这里的this不能用
            _this.tableData = resp.data.content
            _this.pageSize = resp.data.size
            _this.total = resp.data.totalElements

            console.log('resp.data.data:'+resp.data.data);
        })
    }
};
</script>