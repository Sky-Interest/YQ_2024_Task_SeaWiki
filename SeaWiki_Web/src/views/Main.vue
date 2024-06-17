<template>
  <div class="main">
    <div class="rt">
        <el-button @click="logout" type="danger" icon="el-icon-error">退出</el-button>
    </div>
    <div class="aside">
      <!--el的容器  布局容器-->
      <el-container style="height: 500px; border: 1px solid #eee">
        <!--el-aside 显示侧边栏-->
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <!--正式菜单 -->
          <!--default-openeds 默认展开的菜单 -->
          <!-- router路由属性-->
          <el-menu router :default-openeds="['0', '1']">
            <el-submenu
              v-for="(item, index) in routes"
              :index="index + ''"
              :key="item.name"
            >
              <template slot="title">{{ item.name }}</template>
              <el-menu-item
                v-for="item2 in item.children"
                :index="item2.path"
                :key="item2.name"
                >{{ item2.name }}</el-menu-item
              >
            </el-submenu>
          </el-menu>
        </el-aside>

        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </div>

    <!-- 内容展示 -->
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
    methods:{
        logout(){
            localStorage.removeItem('isLoggedIn')
            this.$router.push('/login')
        }
    },
  data() {
    
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    };

    return {
      tableData: Array(20).fill(item)
    }
  },
  computed: {//通过计算属性过滤掉列表中不需要显示的项目
    routes: function () {
      return this.$router.options.routes.filter(function (route) {
        return route.show;//返回show=true的项，添加到routes数组
      })
    }
  },
  created(){

  }
};
</script>

<style scoped>
.aside {
  width: 200px;
  /* height: 100vh; */
  background: pink;
  position: fixed;
  left: 0;
  top: 0;
  overflow: scroll;
}

.rt{
    top:0;
    right:0;
    position: fixed;
}

.aside::-webkit-scrollbar {
    display: none;
}

.content {
  margin-left: 200px;
}
</style>