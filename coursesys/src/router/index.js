import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'

//学生页面
import StudentHome from '../views/studentPage/StudentHome.vue';
import SelectCourses from '../views/studentPage/SelectCourses.vue';
import StudentInfo from '../views/studentPage/StudentInfo.vue';
import CourseHistory from '../views/studentPage/CourseHistory.vue';

//管理页面
import AdminHome from '../views/adminPage/AdminHome.vue'
import CourseManage from '../views/adminPage/CourseManage.vue'
import StudentManage from '../views/adminPage/StudentManage.vue'
import GradeTable from '../views/adminPage/GradeTable.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/studentHome',
    name: 'StudentHome',
    component: StudentHome,
    children: [
      {
        path: '/selectCourses',
        name: 'SelectCourses',
        component: SelectCourses
      },
      {
        path: '/studentInfo',
        name: 'StudentInfo',
        component: StudentInfo
      },
      {
        path: '/courseHistory',
        name: 'CourseHistory',
        component: CourseHistory
      }
    ]
  },
  {
    path: '/adminHome',
    name: 'AdminHome',
    component: AdminHome,
    children: [
      {
        path: '/courseManage',
        name: 'CourseManage',
        component: CourseManage,
      },
      {
        path: '/studentManage',
        name: 'StudentManage',
        component: StudentManage,
      },
      {
        path: '/gradeTable',
        name: 'GradeTable',
        component: GradeTable,
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
