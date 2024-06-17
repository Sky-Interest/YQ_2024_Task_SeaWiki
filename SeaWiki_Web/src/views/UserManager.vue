<template>
  <div>
    <el-table :data="tableData">
      <el-table-column prop="id" label="id" width="140">
      </el-table-column>
      <el-table-column prop="username" label="姓名" width="120">
      </el-table-column>
      <el-table-column prop="password" label="密码" width="120">
      </el-table-column>

      <!--操作列-->
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <!--scope.row 当前行-->
          <el-link type="warning" size="mini" @click="showUpdate(scope.row)">编辑</el-link> | &nbsp;&nbsp;
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
    return {
      pageSize: 5,
      total: 0,
      tableData: []
    }
  },
  methods: {
    page(currentPage) {
      var _this = this
      axios.get('http://localhost:8088/userlist/' + (currentPage-1)+"/2").then(function (resp) {
        _this.tableData = resp.data.content
        _this.pageSize = resp.data.size
        _this.total = resp.data.totalElements
      }).catch(function (error) {
        console.error(error);
        _this.$message.error('获取用户列表失败');
      });
    },
    showUpdate(row) {
      console.log(row.id);
      this.$router.push({
        path: '/updateUser',
        query: {
          id: row.id
        }
      });
    },
    deleteUser(rowId) {
      const _this = this;

      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.get('http://localhost:8088/deleteUser?userId=' + rowId).then(function (resp) {
          console.log(resp.data.data);
          if (resp.data.data === 'success') {
            console.log('delete success....')
            _this.page(1); // 刷新列表，从第一页开始
          }
        }).catch(function (error) {
          console.error(error);
          _this.$message.error('删除用户失败');
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
    console.log('this:', this);
    this.page(1);
  }
};
</script>
