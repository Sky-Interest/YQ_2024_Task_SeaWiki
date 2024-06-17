<template>
    <div id="app">
      <el-container style="height: 500px; border: 1px solid #eee">
  
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
  
          <el-menu router :default-openeds="['0', '1']">
            <el-submenu v-for="(item,index) in $router.options.routes" :index="index+''" v-if="item.show" :key="index">
              <template slot="title">{{item.name}}</template>
              <el-menu-item v-for="(item2,index2) in item.children" :index="item2.path"
                            :class="$route.path==item2.path?'is-active':''" :key="index2">{{item2.name}}</el-menu-item>
            </el-submenu>
          </el-menu>
  
        </el-aside>
  
        <el-main>
          <router-view></router-view>
        </el-main>
  
      </el-container>
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
  export default {
    data() {
      
      
      return {
        
      }
    },
    computed: {//通过计算属性过滤掉列表中不需要显示的项目
      routes: function () {
        return this.$router.options.routes.filter(function (route) {
          return route.show;//返回show=true的项，添加到routes数组
        })
      }
    }
  };
  </script>