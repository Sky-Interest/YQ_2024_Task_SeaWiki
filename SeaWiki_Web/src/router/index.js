import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Index from '../views/Index.vue'
import UserMananger from '../views/UserManager.vue'
import Main from '../views/Main.vue'
import UpdateUser from "@/views/UpdateUser.vue";

Vue.use(VueRouter)

const routes = [

  {
    path: '/',

    component: Main,
    meta: { requiresAuth: true },
    children: [

      {
        path: '/userlist',
        name: '用户管理',
        component: UserMananger,
        meta: { requiresAuth: true }, // 标记需要登录的路由

      },

    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    show: false
  },
  {
    path: '/updateUser',
    name: 'updateUser',
    component: UpdateUser,
    props: route => ({ id: route.query.id })
  },
  {
    path: '/index',
    name: '用户管理',
    component: Index,
    show: true,
    redirect: "/userlist",
    children:[
      {
        path:"/userMananger",
        name:"查询用户",
        component:UserMananger,

      },
      {
        path:"/register",
        name:"添加用户",
        component:Register,
      }
    ]
  }
]

const router = new VueRouter({
  routes
})


router.beforeEach((to, from, next) => {
  // 检查是否需要登录的路由
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 这里可以实现登录状态的检查，例如检查localStorage或vuex状态
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    if (!isLoggedIn) {
      alert('没有登录')
      // 如果未登录，则重定向到登录页面
      next('/login');
    } else {
      next(); // 如果已登录，继续前往目标路由
    }
  } else {
    next(); // 如果不需要登录，继续前往目标路由
  }
});

export default router
